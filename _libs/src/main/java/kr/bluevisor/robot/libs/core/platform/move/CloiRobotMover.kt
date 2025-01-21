//package kr.bluevisor.robot.libs.core.platform.move
//
//import android.content.Context
//import android.os.Bundle
//import android.os.Handler
//import com.lge.thirdpartylib.ThirdPartyServiceConnector
//import com.lge.thirdpartylib.listener.ServiceConnectionListener
//import com.lge.thirdpartylib.model.ThirdPartyEvent
//import enn.libs.and.llog.LLog
//
//class CloiRobotMover(private val context: Context) {
//    init {
//        ThirdPartyServiceConnector.initService(context, object : ServiceConnectionListener {
//            override fun onConnected() {
//                LLog.v()
//            }
//
//            override fun onDisconnected() {
//                LLog.v()
//            }
//        })
//    }
//
//    private fun newThirdPartyServiceHandler() = Handler.Callback { messasge ->
//        val event = ThirdPartyEvent.fromValue(messasge.what)
//        val messageInfo = messasge.obj as Bundle
//        val result = messageInfo.getString("param")
//
//        when (event) {
//            ThirdPartyEvent.MODI_CONTENT_RESULT -> {}
//            ThirdPartyEvent.MODI_ACTION_RESULT -> {}
//            ThirdPartyEvent.REQUEST_CRUISE_RESUME -> {}
//            ThirdPartyEvent.REQUEST_CRUISE_PAUSE -> {}
//            ThirdPartyEvent.CRUISE_CONTROL_AVAILABLE -> {}
//            ThirdPartyEvent.REQUEST_ESCORT -> {}
//            ThirdPartyEvent.ESCORT_REQUEST_RESULT -> {}
//            ThirdPartyEvent.REQUEST_POI_LIST -> {}
//            ThirdPartyEvent.POI_LIST_UPDATED -> {}
//            ThirdPartyEvent.REQUEST_PHOTO -> {}
//            ThirdPartyEvent.USER_INTERACTION_TIMEOUT -> {}
//            ThirdPartyEvent.SEND_DF_RESULT -> {}
//            ThirdPartyEvent.REGISTER -> {}
//            ThirdPartyEvent.UNREGISTER -> {}
//            ThirdPartyEvent.SEND_TTS_RESULT -> {}
//            ThirdPartyEvent.SEND_STT_RESULT -> {}
//            ThirdPartyEvent.UNKNOWN -> { LLog.w() }
//        }
//
//        LLog.v("event: $event, messageInfo: $messageInfo, result: $result.")
//        true
//    }
//}