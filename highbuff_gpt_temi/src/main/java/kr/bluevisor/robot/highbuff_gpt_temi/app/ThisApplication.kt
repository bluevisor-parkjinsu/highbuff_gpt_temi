package kr.bluevisor.robot.highbuff_gpt_temi.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class ThisApplication : Application() {
    val applicationScope = CoroutineScope(
        context = SupervisorJob() +
                Dispatchers.Default +
                CoroutineName("ApplicationScope") +
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    LLog.w("coroutineContext: $coroutineContext.", throwable)
                }
    )

    override fun onCreate() {
        super.onCreate()
        instance = this
        LLog.v()
    }

    companion object {
        lateinit var instance: ThisApplication
    }
}