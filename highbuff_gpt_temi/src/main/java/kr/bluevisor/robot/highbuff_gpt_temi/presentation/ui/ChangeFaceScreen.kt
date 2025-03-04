package kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kr.bluevisor.robot.highbuff_gpt_temi.R
import kr.bluevisor.robot.highbuff_gpt_temi.domain.entity.FaceExpression
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.SharedViewModel
import kr.bluevisor.robot.highbuff_gpt_temi.util.SharedPrefUtil

@Composable
fun ChangeFaceScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel()
) {
    val allFaceExpressions = viewModel.allFaceExpressions
    val showChangeFaceButton = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { offset ->
                        android.util.Log.e("onLongPress","onLongPress")
                        showChangeFaceButton.value = true
                    }
                )
            }

    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allFaceExpressions) { faceExpression ->
                // 각 아이템이 화면을 꽉 채우도록 설정
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                ) {
                    FaceExpressionTypeItem(
                        navController,
                        viewModel,
                        showChangeFaceButton = showChangeFaceButton,  // 상태 전달
                        faceExpression = faceExpression,
                        onClick = {
                            Log.e("ChangeFaceScreen.onclick", faceExpression.toString())
                            viewModel.onItemClicked(navController,faceExpression)

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FaceExpressionTypeItem(
    navController: NavController,
    viewModel: SharedViewModel,
    showChangeFaceButton: MutableState<Boolean>,
    faceExpression: FaceExpression, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .height(900.dp)
                .width(1000.dp)
                .padding(20.dp)
                .clickable(onClick = onClick)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(
                    modifier = Modifier
                        .height(900.dp)
                        .width(1000.dp)
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 20.dp),
                    factory = { context ->
                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                    },
                    update = { imageView ->
                        Glide.with(imageView.context)
                            .asGif()
                            .load(faceExpression.image)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(imageView)
                    }
                )
            }
        }
        if (showChangeFaceButton.value) {
            Button(
                onClick = {
                    viewModel.onDetailsItemClicked(navController, faceExpression)
                    showChangeFaceButton.value = false},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 400.dp, end = 50.dp)
            ) {
                Text("얼굴 표정 종류")
            }
        }
    }
}