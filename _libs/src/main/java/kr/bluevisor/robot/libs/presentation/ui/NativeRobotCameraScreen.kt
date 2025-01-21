package kr.bluevisor.robot.libs.presentation.ui

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kr.bluevisor.robot.libs.presentation.model.NativeRobotCameraViewModel
import kr.bluevisor.robot.libs.presentation.theme.SimpleTopAppBar

private val title = "Bluevisor Native Robot Camera"

@Composable
fun NativeRobotCameraScreen(
    navController: NavController,
    previewViewAssigner: (PreviewView) -> Unit,
    viewModel: NativeRobotCameraViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = { SimpleTopAppBar(title, navController = navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                update = previewViewAssigner
            )
        }
    }
}