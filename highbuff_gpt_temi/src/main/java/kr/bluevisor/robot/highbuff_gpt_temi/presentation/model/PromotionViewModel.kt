package kr.bluevisor.robot.highbuff_gpt_temi.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.robotemi.sdk.constants.Platform
import com.robotemi.sdk.telepresence.Participant
import dagger.hilt.android.lifecycle.HiltViewModel
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kr.bluevisor.robot.highbuff_gpt_temi.domain.usecase.GuideTemiRobotPromotionGptSequenceUseCase
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobotTelepresence
import kr.bluevisor.robot.libs.domain.usecase.TemiRobotGptToolFunctionUseCase
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val temiRobot: TemiRobot,
    private val guideTemiRobotPromotionGptSequenceUseCase: GuideTemiRobotPromotionGptSequenceUseCase,
    temiRobotGptToolFunctionUseCase: TemiRobotGptToolFunctionUseCase
) : ViewModel(){

    // Temi 깨우기 작업을 위한 Job 변수 선언
    private var _wakeUpJob: Job? = null

    private val gptToolFunctionList = listOf(
        // We do not use listening, speaking functions currently.
        // temiRobotGptToolFunctionUseCase.newSpeakFunction(),
        // temiRobotGptToolFunctionUseCase.newAskQuestionFunction(),
        // temiRobotGptToolFunctionUseCase.newWakeUpFunction(),
        temiRobotGptToolFunctionUseCase.newSaveLocationFunction(),
        temiRobotGptToolFunctionUseCase.newGoToLocationFunction(),
        temiRobotGptToolFunctionUseCase.newWalkAroundLocationsFunction(),
        temiRobotGptToolFunctionUseCase.newBeWithMeFunction(),
        temiRobotGptToolFunctionUseCase.newConstraintBeWithFunction(),
        temiRobotGptToolFunctionUseCase.newTurnByFunction(),
        temiRobotGptToolFunctionUseCase.newStopMovementFunction()
    )

    private val _dynamicGreetingModeOnLiveData = MutableLiveData(false)
    val dynamicGreetingModeOnLiveData: LiveData<Boolean> = _dynamicGreetingModeOnLiveData

    val temiDetectionStateLiveData =
        temiRobot.detectionStateFlow.asLiveData()

    fun startTemiDynamicGreetingMode() {
        if (_dynamicGreetingModeOnLiveData.value == true) {
            LLog.v(
                "_dynamicGreetingModeOn is already true." +
                        " The calling will be return without some doing."
            )
            return
        }
        _dynamicGreetingModeOnLiveData.value = true

        temiRobot.setDetectionModeOn(on = true, distance = 2.0f)
        temiRobot.constraintBeWith()
        LLog.v()
    }

    fun stopTemiDynamicGreetingMode() {
        if (_dynamicGreetingModeOnLiveData.value == false) {
            LLog.v(
                "_dynamicGreetingModeOn is already false." +
                        " The calling will be return without some doing."
            )
            return
        }
        _dynamicGreetingModeOnLiveData.value = false

        temiRobot.detectionModeOn = false
        temiRobot.stopMovement()
        LLog.v()
    }

    fun requestTemiSpeechToCommand(isOneShot: Boolean = true): Job {
        guideTemiRobotPromotionGptSequenceUseCase.initEnvironment(gptToolFunctionList)
        LLog.v("isOneShot: $isOneShot.")
        return guideTemiRobotPromotionGptSequenceUseCase.speechToOrder(isOneShot)
            .launchIn(viewModelScope)
    }

    fun callTemiCallStaffOnTelepresence(peerId: String) {
        val telepresenceStateListeningJob = viewModelScope.launch(Dispatchers.IO) {
            temiRobot.telepresenceStateFlow.withIndex().collect { indexedState ->
                if (indexedState.index == 0) return@collect
                when (indexedState.value) {
                    TemiRobotTelepresence.State.STARTED -> {
                    }
                    TemiRobotTelepresence.State.ENDED -> {
                        this@launch.cancel()
                    }
                }
                LLog.v("indexedState: $indexedState.")
            }
        }

        val resultCode = temiRobot.startMeeting(
            listOf(Participant(peerId = peerId, Platform.MOBILE)),
            firstParticipantJoinedAsHost = true,
            blockRobotInteraction = false
        )

        if (resultCode != TemiRobotTelepresence.START_MEETING__RESULT_CODE__OK) {
            telepresenceStateListeningJob.cancel()
            LLog.w(
                "resultCode is not normal. You may need to grant permission:" +
                        " resultCode: $resultCode."
            )
            return
        }
        LLog.v("resultCode: $resultCode.")
    }

    fun callTemiWakeUp() {
        _wakeUpJob = temiRobot.wakeUpFlow(isOneShot = true)
            .launchIn(viewModelScope) // 코루틴을 viewModelScope에서 실행
        LLog.v("Temi is waking up.")
    }

    fun cancelTemiWakeUp() {
        _wakeUpJob?.cancel()
        LLog.v("Temi wake up flow has been cancelled.")
    }

    override fun onCleared() {
        super.onCleared()
        LLog.v()
    }

    companion object {
        const val PEER_ID__DEBUGGER = "90304a15a579e11097fcfbb3e24eb7d8"
    }
}