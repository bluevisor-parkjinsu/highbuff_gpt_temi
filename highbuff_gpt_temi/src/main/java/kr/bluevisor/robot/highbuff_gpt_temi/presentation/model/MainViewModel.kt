package kr.bluevisor.robot.highbuff_gpt_temi.presentation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.bluevisor.robot.highbuff_gpt_temi.domain.usecase.GuideTemiRobotPromotionGptSequenceUseCase
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val temiRobot: TemiRobot,
    private val guideTemiRobotPromotionGptSequenceUseCase: GuideTemiRobotPromotionGptSequenceUseCase,
) : ViewModel() {

    private val _speechInput = MutableLiveData<Boolean>()
    val speechInput: LiveData<Boolean> get() = _speechInput

    private val _emotionInput = MutableLiveData<String>()
    val emotionInput: LiveData<String> get() = _emotionInput

    private val _emotionState = MutableLiveData<String>()
    val emotionState: LiveData<String> get() = _emotionState

    private lateinit var _wakeUpJob: Job
    private lateinit var textCommandJob: Job

    val emotionPrompt =
        """
         너는 다섯 가지의 감정을 가진 로봇 이야 내 질문을 듣고 질문의 텍스트 를 분석 해서 나의 감정을 good , normal , processing ,speaking , wait 으로만 반환 해 줘
        """.trimIndent()

    private val _dynamicGreetingModeOnLiveData = MutableLiveData(false)
    val dynamicGreetingModeOnLiveData: LiveData<Boolean> = _dynamicGreetingModeOnLiveData

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
        guideTemiRobotPromotionGptSequenceUseCase.initEnvironment(listOf())

        LLog.v("isOneShot: $isOneShot.")

        return guideTemiRobotPromotionGptSequenceUseCase.speechToOrder(isOneShot)
            .onEach { speechText ->
                val speech = speechText.toString()

                if (speech.isNotEmpty()) {
                    _speechInput.value = true
                    _emotionInput.value = speech
                    Log.e("Speech Input:", _speechInput.value.toString())
                }

                // 텍스트를 명령으로 변환
//                guideTemiRobotPromotionGptSequenceUseCase.textToOrder(speech)
            }
            .catch { throwable ->
                LLog.e("Error in speechToOrder: ", throwable)
            }
            .launchIn(viewModelScope)  // ViewModelScope에서 실행
    }

//
//    fun requestTemiTextToCommand(inputText: String, isOneShot: Boolean = true): Job {
//        textCommandJob = guideTemiRobotPromotionGptSequenceUseCase.textToOrder(inputText, isOneShot)
//            .onEach { speechText ->
//                if (speechText.isNotEmpty() && _speechInput.value == false) {
//                    LLog.v("requestTemiSpeechToCommand.speechText: $speechText")
//                    _emotionState.value = speechText
//                }
//            }
//            .onCompletion {
//                // 작업 완료 시 처리
//                _speechInput.value = false
//                Log.e("requestTemiTextToCommand", "requestTemiTextToCommand.onCompletion")
//            }
//            .launchIn(viewModelScope)
//
//        return textCommandJob ?: error("Job should not be null!")
//    }


    fun callTemiWakeUp() {
        _wakeUpJob = temiRobot.wakeUpFlow(isOneShot = true)
            .launchIn(viewModelScope)
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
}