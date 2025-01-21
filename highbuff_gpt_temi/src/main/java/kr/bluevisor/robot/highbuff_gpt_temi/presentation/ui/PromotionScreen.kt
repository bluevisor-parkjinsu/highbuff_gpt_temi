package kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kr.bluevisor.robot.highbuff_gpt_temi.R
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.PromotionViewModel
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.SharedViewModel
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.theme.SimpleTopAppBar
import kr.bluevisor.robot.highbuff_gpt_temi.util.SharedPrefUtil


private const val FACE_EXPRESSION_MODE = "얼굴 표정 변경"

@Composable
fun PromotionScreen(
    navController: NavController,
    viewModel: PromotionViewModel = hiltViewModel(),
    sharedViewModel:SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val dynamicGreetingModeOn by viewModel.dynamicGreetingModeOnLiveData.observeAsState(false)
    val isTemiFunctionActive = remember { mutableStateOf(true) } // Temi 기능 활성화 여부 상태


    DisposableEffect(Unit) {
        var job: Job? = null

        // Temi 기능을 호출하는 메서드
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

        // LifecycleObserver 설정
        val dynamicGreetingModeLifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    // 화면이 활성화될 때 Temi 기능을 활성화
                    isTemiFunctionActive.value = true
                    if (!dynamicGreetingModeOn) {
                        Log.e("Event.ON_START","Event.ON_START")
                        viewModel.startTemiDynamicGreetingMode()
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    // 화면이 백그라운드로 갈 때 Temi 기능을 비활성화
                    isTemiFunctionActive.value = false
                    if (dynamicGreetingModeOn) {
                        Log.e("Event.ON_STOP","Event.ON_STOP")
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

    //저장된 face image Shared 값 가져 오기
    val imageSave = SharedPrefUtil(context).getInt("imageSave",R.drawable.face__cat__good)

    // FIXME : Remove me.
    // We covered UI with the face.
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { offset -> }
            )
        }
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .clickable {
                    viewModel.callTemiWakeUp()
                           },
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

        // ChangeFaceExpression을 이미지 위에 배치
        ChangeFaceExpression(
            navController,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 150.dp, end = 50.dp),
            viewModel = sharedViewModel
        )

    }
}

@Composable
private fun ChangeFaceExpression(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SharedViewModel
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(end = 50.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = {
            navController.navigate(MainActivity.SCREEN__CHANGE_FACE)
        }) {
            Text(text = FACE_EXPRESSION_MODE)
        }
    }
}
