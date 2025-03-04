package kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kr.bluevisor.robot.highbuff_gpt_temi.domain.entity.FaceExpression
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.model.SharedViewModel


@Composable
fun ChangeFaceDetailsScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel()
) {
    val selectedFace by viewModel.selectedFace.observeAsState()

    val faceList = selectedFace?.let { viewModel.getFaceList(it) } ?: emptyList()

    Box(modifier = Modifier.fillMaxSize()) {
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
            items(faceList) { faceExpression ->
                FaceExpressionItem(
                    faceExpression = faceExpression,
                    onClick = {
                        viewModel.onItemClicked(navController,faceExpression)
                    }
                )
            }
        }
    }
}

@Composable
fun FaceExpressionItem(
    faceExpression: FaceExpression, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                modifier = Modifier
                    .size(150.dp)
                    .padding(15.dp),
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
            Text(
                text = faceExpression.name,
                modifier = Modifier.weight(1f)
            )
        }
    }
}