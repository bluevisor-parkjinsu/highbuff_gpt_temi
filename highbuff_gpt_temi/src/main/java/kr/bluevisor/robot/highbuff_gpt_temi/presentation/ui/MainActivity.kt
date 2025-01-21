package kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import enn.libs.and.llog.LLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kr.bluevisor.robot.highbuff_gpt_temi.R
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.theme.MainTheme
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobotPermission
import javax.inject.Inject

@Composable
private fun MainNavigation(
    temiRobot: TemiRobot,
    startDestination: String = MainActivity.SCREEN__ALIAS__DEFAULT
) {
    val navGraphName = "navGraph"
    val navController = rememberNavController()

    NavHost(navController, startDestination = navGraphName) {
        navigation(route = navGraphName, startDestination = startDestination) {
            composable(MainActivity.SCREEN__CHANGE_FACE) { navBackStackEntry ->
                ChangeFaceScreen(navController, viewModel = hiltViewModel())
            }
            composable(MainActivity.SCREEN__PROMOTION) { navBackStackEntry ->
                PromotionScreen(navController)
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    internal lateinit var temiRobot: TemiRobot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra(KEY_INTENT_EXTRA__START_DESTINATION_SCREEN)
            ?: SCREEN__ALIAS__DEFAULT
        setContent {
            MainTheme {
                MainNavigation(temiRobot, startDestination)
            }
        }
        LLog.v("startDestination: $startDestination.")
    }

    override fun onStart() {
        super.onStart()
        requestTemiAllPermissions()
        LLog.v()
    }

    private fun requestTemiAllPermissions() {
        lifecycleScope.launch {
            temiRobot.requestPermissionsFlow(*TemiRobotPermission.ALL_PERMISSIONS.toTypedArray())
                .collect { permissionResultMap ->
                    val disallowedPermissionList = permissionResultMap.entries
                        .asSequence()
                        .filter { !it.value }
                        .map { it.key }
                        .toList()
                    if (disallowedPermissionList.isEmpty()) return@collect

                    val message = "The app requires permissions: $disallowedPermissionList."
                    Toast.makeText(
                        this@MainActivity, message, Toast.LENGTH_LONG
                    ).show()
                    finish()
                    LLog.v("disallowedPermissionList: $disallowedPermissionList.")
                }
        }
        LLog.v()
    }

    companion object {
        const val KEY_INTENT_EXTRA__START_DESTINATION_SCREEN = "startDestinationScreen"
        const val SCREEN__PROMOTION = "promotion"
        const val SCREEN__CHANGE_FACE = "change_face"
        const val SCREEN__ALIAS__DEFAULT = SCREEN__PROMOTION    // FIXME : Restore me.
    }
}