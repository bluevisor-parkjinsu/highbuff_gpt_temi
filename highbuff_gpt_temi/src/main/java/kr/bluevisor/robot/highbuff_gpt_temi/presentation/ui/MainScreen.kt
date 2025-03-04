package kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kr.bluevisor.robot.highbuff_gpt_temi.R
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.MainViewModel
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.SharedViewModel
import kr.bluevisor.robot.highbuff_gpt_temi.util.SharedPrefUtil

private const val FACE_EXPRESSION_MODE = "얼굴 표정 변경"

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    sharedViewModel:SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val image by sharedViewModel.image.observeAsState()
    val speechInput by viewModel.speechInput.observeAsState()
    val emotionInput by viewModel.emotionInput.observeAsState()
    val emotionState by viewModel.emotionState.observeAsState()

    image?.let { SharedPrefUtil(context).saveInt("imageSave", it) }

//    Log.e("MainScreen.speechInput", speechInput.toString())
//    LaunchedEffect(speechInput) {
//        if (speechInput == true) {
//            viewModel.requestTemiTextToCommand(viewModel.emotionPrompt)
//        }else if (speechInput == false){
//            Log.e("MainScreen.emotionInput", emotionInput.toString())
//            viewModel.requestTemiTextToCommand(emotionInput.toString())
//        }
//    }

    val dynamicGreetingModeOn by viewModel.dynamicGreetingModeOnLiveData.observeAsState(false)

    val isTemiFunctionActive = remember { mutableStateOf(true) }
    val showChangeFaceButton = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        var job: Job? = null

        fun requestToControlTemiTask() {
            if (isTemiFunctionActive.value) {
                job = viewModel.requestTemiSpeechToCommand(false)
                job?.invokeOnCompletion { cause ->
                    if (cause == null) return@invokeOnCompletion
                    LLog.w(cause)

                    if (cause is CancellationException) return@invokeOnCompletion
                    requestToControlTemiTask()
                }
            }
        }
        requestToControlTemiTask()

        val dynamicGreetingModeLifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    isTemiFunctionActive.value = true
                    if (!dynamicGreetingModeOn) {
                        Log.e("Event.ON_START","Event.ON_START")
                        viewModel.startTemiDynamicGreetingMode()
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    isTemiFunctionActive.value = false
                    if (dynamicGreetingModeOn) {
                        viewModel.stopTemiDynamicGreetingMode()
                        viewModel.cancelTemiWakeUp()
                    }
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(dynamicGreetingModeLifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(dynamicGreetingModeLifecycleObserver)
            viewModel.stopTemiDynamicGreetingMode()
            job?.cancel()
            LLog.v("onDispose() is called.")
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    viewModel.callTemiWakeUp()
                },
                onLongPress = { offset ->
                    Log.e("onLongPress","onLongPress")
                    showChangeFaceButton.value = true
                }
            )
        }
    ) {
        val imageSave = SharedPrefUtil(context).getInt("imageSave",R.drawable.face__blue__good)
        Log.e("AndroidView.imageSave", imageSave.toString())
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
            update = { imageView ->
                Glide.with(imageView.context)
                    .asGif()
                    .load(imageSave)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
            },
            onReset = { imageView ->
                Glide.with(imageView).clear(imageView)
            }
        )

        if (showChangeFaceButton.value) {
            ChangeFaceExpression(
                navController,
                showChangeFaceButton = showChangeFaceButton,  // 상태 전달
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 150.dp, end = 50.dp),
            )
        }
    }
}

@Composable
fun ChangeFaceExpression(
    navController: NavController,
    showChangeFaceButton: MutableState<Boolean>, // 상태를 전달받음
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(end = 50.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = {
            showChangeFaceButton.value = false
            navController.navigate(MainActivity.SCREEN__CHANGE_FACE)
        }) {
            Text(text = FACE_EXPRESSION_MODE)
        }
    }
}
