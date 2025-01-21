package kr.bluevisor.robot.highbuff_gpt_temi.presentation.theme

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import enn.libs.and.llog.LLog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String, navController: NavController) {
    val context = LocalContext.current
    TopAppBar(
        // FIXME : Restore me.
        modifier = /* if (BuildConfig.FOR_TEMI) Modifier.padding(top = 64.dp) else */ Modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {
                if (!navController.popBackStack()) {
                    if (context is Activity)    context.finish()
                    else                        LLog.w("context is not an activity.")
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun VerticalSpacer(magnification: Int = 1) {
    Spacer(modifier = Modifier.height((8 * magnification).dp))
}