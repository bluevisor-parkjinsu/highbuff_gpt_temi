package kr.bluevisor.robot.libs.domain.usecase

import androidx.compose.runtime.saveable.Saver
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.navigation.model.SpeedLevel
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.full.valueParameters

@Singleton
class TemiRobotGptToolFunctionUseCase @Inject constructor(
    private val temiRobot: TemiRobot
) : GptToolFunctionUseCase {
    override val functionList: List<GptToolFunction> = listOf(
        newSpeakFunction(),
        newAskQuestionFunction(),
        newWakeUpFunction(),
        newSaveLocationFunction(),
        newGoToLocationFunction(),
        newWalkAroundLocationsFunction(),
        newBeWithMeFunction(),
        newConstraintBeWithFunction(),
        newTurnByFunction(),
        newStopMovementFunction()
    )

    fun newSpeakFunction() = GptToolFunction(
        name = SPEAK__FUNCTION__NAME,
        description = SPEAK__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = SPEAK__FUNCTION_PARAMS__0__MESSAGE__NAME,
                description = SPEAK__FUNCTION_PARAMS__0__MESSAGE__DESCRIPTION,
                required = true
            ))
        }
    )

    fun interpretSpeakFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<TtsRequest> {
        val function = SPEAK__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = SPEAK__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)

        LLog.v("functionCall: $functionCall.")
        return function(temiRobot, getParameterArgumentFunction(0)!!)
    }

    fun makeSpeakFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newAskQuestionFunction() = GptToolFunction(
        name = ASK_QUESTION__FUNCTION__NAME,
        description = ASK_QUESTION__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = ASK_QUESTION__FUNCTION_PARAMS__0__QUESTION__NAME,
                description = ASK_QUESTION__FUNCTION_PARAMS__0__QUESTION__DESCRIPTION,
                required = true
            ))
        }
    )

    fun interpretAskQuestionFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Map<String, Any>> {
        val function = ASK_QUESTION__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = ASK_QUESTION__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)
        
        LLog.v("functionCall: $functionCall.")
        return function(temiRobot, getParameterArgumentFunction(0)!!)
    }

    fun makeAskQuestionFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newWakeUpFunction() = GptToolFunction(
        name = WAKE_UP__FUNCTION__NAME,
        description = WAKE_UP__FUNCTION__DESCRIPTION
    )

    fun interpretWakeUpFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Map<String, Any>> {
        val function = WAKE_UP__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        LLog.v("functionCall: $functionCall.")
        return function(temiRobot, true)
    }

    fun makeWakeUpFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newSaveLocationFunction() = GptToolFunction(
        name = SAVE_LOCATION__FUNCTION__NAME,
        description = SAVE_LOCATION__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = SAVE_LOCATION__FUNCTION_PARAMS__0__LOCATION__NAME,
                description = SAVE_LOCATION__FUNCTION_PARAMS__0__LOCATION__DESCRIPTION,
                required = true,
            ))
        }
    )

    fun interpretSaveLocationFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Boolean> {
        val function = SAVE_LOCATION__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = SAVE_LOCATION__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)

        LLog.v("functionCall: $functionCall.")
        return function(
            temiRobot,
            getParameterArgumentFunction(0)!!
        )
    }

    fun makeSaveLocationFunctionCallSignatureReturnValueText(callbackArgument: Boolean) =
        callbackArgument.toString()

    fun newGoToLocationFunction() = GptToolFunction(
        name = GO_TO_LOCATION__FUNCTION__NAME,
        description = GO_TO_LOCATION__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = GO_TO_LOCATION__FUNCTION_PARAMS__0__LOCATION__NAME,
                description = GO_TO_LOCATION__FUNCTION_PARAMS__0__LOCATION__DESCRIPTION,
                required = true,
                argumentRange = temiRobot.locationList
            ))
            add(GptToolFunction.Parameter(
                name = GO_TO_LOCATION__FUNCTION_PARAMS__1__BACKWARDS__NAME,
                description = GO_TO_LOCATION__FUNCTION_PARAMS__1__BACKWARDS__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__BOOLEAN
            ))
            add(GptToolFunction.Parameter(
                name = GO_TO_LOCATION__FUNCTION_PARAMS__2__NO_BYPASS__NAME,
                description = GO_TO_LOCATION__FUNCTION_PARAMS__2__NO_BYPASS__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__BOOLEAN
            ))
            add(GptToolFunction.Parameter(
                name = GO_TO_LOCATION__FUNCTION_PARAMS__3__SPEED_LEVEL__NAME,
                description = GO_TO_LOCATION__FUNCTION_PARAMS__3__SPEED_LEVEL__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__SPEED_LEVEL
            ))
        }
    )

    fun interpretGoToLocationFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Map<String, Any>> {
        val function = GO_TO_LOCATION__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = GO_TO_LOCATION__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)

        LLog.v("functionCall: $functionCall.")
        return function(
            temiRobot,
            getParameterArgumentFunction(0)!!,
            getParameterArgumentFunction(1)?.toBoolean(),
            getParameterArgumentFunction(2)?.toBoolean(),
            getParameterArgumentFunction(3)?.let { SpeedLevel.valueOf(it) }
        )
    }

    fun makeGoToLocationFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newWalkAroundLocationsFunction() = GptToolFunction(
        name = WALK_AROUND_LOCATIONS__FUNCTION__NAME,
        description = WALK_AROUND_LOCATIONS__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__0__LOCATION_NAME_LIST__NAME,
                description = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__0__LOCATION_NAME_LIST__DESCRIPTION,
                required = true,
                argumentRange = temiRobot.locationList
            ))
            add(GptToolFunction.Parameter(
                name = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__1__ALLOW_BACKWARDS__NAME,
                description = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__1__ALLOW_BACKWARDS__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__BOOLEAN
            ))
            add(GptToolFunction.Parameter(
                name = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__2__NO_BYPASS__NAME,
                description = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__2__NO_BYPASS__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__BOOLEAN
            ))
            add(GptToolFunction.Parameter(
                name = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__3__SPEED_LEVEL__NAME,
                description = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__3__SPEED_LEVEL__DESCRIPTION,
                required = false,
                argumentRange = FUNCTION_ARGUMENT_RANGE_LIST__SPEED_LEVEL
            ))
        }
    )

    fun interpretWalkAroundLocationsFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Map<String, Any>> {
        val function = WALK_AROUND_LOCATIONS__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = WALK_AROUND_LOCATIONS__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)

        LLog.v("functionCall: $functionCall.")
        return function(
            temiRobot,
            listOf(getParameterArgumentFunction(0)!!),
            getParameterArgumentFunction(1)?.toBoolean(),
            getParameterArgumentFunction(2)?.toBoolean(),
            getParameterArgumentFunction(3)?.let { SpeedLevel.valueOf(it) }
        )
    }

    fun makeWalkAroundLocaitonsFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED
    
    fun newBeWithMeFunction() = GptToolFunction(
        name = BE_WITH_ME__FUNCTION__NAME,
        description = BE_WITH_ME__FUNCTION__DESCRIPTION
    )
    
    fun interpretBeWithMeFunctionCall(functionCall: GptToolFunction.Call) = flow {
        val function = BE_WITH_ME__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)
        
        function(temiRobot)
        emit(Unit)
        LLog.v("functionCall: $functionCall.")
    }

    fun makeBeWithMeFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED
    
    fun newConstraintBeWithFunction() = GptToolFunction(
        name = CONSTRAINT_BE_WITH__FUNCTION__NAME,
        description = CONSTRAINT_BE_WITH__FUNCTION__DESCRIPTION
    )
    
    fun interpretConstraintBeWithFunctionCall(functionCall: GptToolFunction.Call) = flow {
        val function = CONSTRAINT_BE_WITH__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        function(temiRobot)
        emit(Unit)
        LLog.v("functionCall: $functionCall.")
    }

    fun makeConstraintBeWithFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newTurnByFunction() = GptToolFunction(
        name = TURN_BY__FUNCTION__NAME,
        description = TURN_BY__FUNCTION__DESCRIPTION,
        parameterList = mutableListOf<GptToolFunction.Parameter>().apply {
            add(GptToolFunction.Parameter(
                name = TURN_BY__FUNCTION_PARAMS__0__DEGREE__NAME,
                description = TURN_BY__FUNCTION_PARAMS__0__DEGREE__DESCRIPTION,
                required = true
            ))
            add(GptToolFunction.Parameter(
                name = TURN_BY__FUNCTION_PARAMS__1__SPEED__NAME,
                description =  TURN_BY__FUNCTION_PARAMS__1__SPEED__DESCRIPTION,
                required = false
            ))
        }
    )

    fun interpretTurnByFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Map<String, Any>> {
        val function = TURN_BY__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        val functionParameterList = TURN_BY__FUNCTION_PARAMS
        val getParameterArgumentFunction = newGetParameterArgumentFunction(
            functionParameterList, functionCall.parameterArgumentMap)

        LLog.v("functionCall: $functionCall.")
        return function(
            temiRobot,
            getParameterArgumentFunction(0)!!.toInt(),
            getParameterArgumentFunction(1)?.toFloat() ?: 1.0f
        )
    }

    fun makeTurnByFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    fun newStopMovementFunction() = GptToolFunction(
        name = STOP_MOVEMENT__FUNCTION__NAME,
        description = STOP_MOVEMENT__FUNCTION__DESCRIPTION
    )

    fun interpretStopMovementFunctionCall(functionCall: GptToolFunction.Call) = flow {
        val function = STOP_MOVEMENT__FUNCTION
        checkAndThrowDoesNotSameFunctionNameException(function, functionCall)

        function(temiRobot)
        emit(Unit)
        LLog.v("functionCall: $functionCall.")
    }

    fun makeStopMovementFunctionCallSignatureReturnValueText(callbackArgument: Any?) =
        RETURN_VALUE__COMPLETED

    override fun interpretFunctionCall(
        functionCall: GptToolFunction.Call,
        needToMakeSignatureReturnValueText: Boolean
    ): Flow<*>? {
        val resultFlow = when (functionCall.functionName) {
            SPEAK__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretSpeakFunctionCall(functionCall),
                    ::makeSpeakFunctionCallSignatureReturnValueText
                )
            ASK_QUESTION__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretAskQuestionFunctionCall(functionCall),
                    ::makeAskQuestionFunctionCallSignatureReturnValueText
                )
            WAKE_UP__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretWakeUpFunctionCall(functionCall),
                    ::makeWakeUpFunctionCallSignatureReturnValueText
                )
            SAVE_LOCATION__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretSaveLocationFunctionCall(functionCall),
                    ::makeSaveLocationFunctionCallSignatureReturnValueText
                )
            GO_TO_LOCATION__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretGoToLocationFunctionCall(functionCall),
                    ::makeGoToLocationFunctionCallSignatureReturnValueText
                )
            WALK_AROUND_LOCATIONS__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretWalkAroundLocationsFunctionCall(functionCall),
                    ::makeWalkAroundLocaitonsFunctionCallSignatureReturnValueText
                )
            BE_WITH_ME__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretBeWithMeFunctionCall(functionCall),
                    ::makeBeWithMeFunctionCallSignatureReturnValueText
                )
            CONSTRAINT_BE_WITH__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretConstraintBeWithFunctionCall(functionCall),
                    ::makeConstraintBeWithFunctionCallSignatureReturnValueText
                )
            TURN_BY__FUNCTION__NAME->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretTurnByFunctionCall(functionCall),
                    ::makeTurnByFunctionCallSignatureReturnValueText
                )
            STOP_MOVEMENT__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretStopMovementFunctionCall(functionCall),
                    ::makeStopMovementFunctionCallSignatureReturnValueText
                )
            else -> {
                LLog.v("Not found matched function name: functionCall: $functionCall.")
                return null
            }
        }
        LLog.v("functionCall: $functionCall.")
        return resultFlow
    }

    companion object : StaticGptToolFunctionUseCases() {
        // speak() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Speech#speak
        val SPEAK__FUNCTION = TemiRobot::speakSimpleFlow
        val SPEAK__FUNCTION__NAME = SPEAK__FUNCTION::name.get()
        const val SPEAK__FUNCTION__DESCRIPTION =
            "Use this method to let Temi speak something."
        val SPEAK__FUNCTION_PARAMS = SPEAK__FUNCTION::valueParameters.get()
        val SPEAK__FUNCTION_PARAMS__0__MESSAGE__NAME =
            getParameterName(SPEAK__FUNCTION_PARAMS, 0)
        const val SPEAK__FUNCTION_PARAMS__0__MESSAGE__DESCRIPTION =
            "The text to be spoken."

        // askQuestion() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Speech#askQuestion
        val ASK_QUESTION__FUNCTION = TemiRobot::askQuestionSimpleFlow
        val ASK_QUESTION__FUNCTION__NAME = ASK_QUESTION__FUNCTION::name.get()
        const val ASK_QUESTION__FUNCTION__DESCRIPTION =
            "Use this method to let Temi actively speak to the user and wait for the user to answer."
        val ASK_QUESTION__FUNCTION_PARAMS = ASK_QUESTION__FUNCTION::valueParameters.get()
        val ASK_QUESTION__FUNCTION_PARAMS__0__QUESTION__NAME =
            getParameterName(ASK_QUESTION__FUNCTION_PARAMS, 0)
        const val ASK_QUESTION__FUNCTION_PARAMS__0__QUESTION__DESCRIPTION =
            "The text to be spoken."

        // wakeUp() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Speech#wakeup
        val WAKE_UP__FUNCTION = TemiRobot::wakeUpFlow
        val WAKE_UP__FUNCTION__NAME = WAKE_UP__FUNCTION::name.get()
        const val WAKE_UP__FUNCTION__DESCRIPTION =
            "Use this method to trigger Temi's wakeup programmatically."

        // saveLocation() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Locations#savelocation
        val SAVE_LOCATION__FUNCTION = TemiRobot::saveLocationFlow
        val SAVE_LOCATION__FUNCTION__NAME = SAVE_LOCATION__FUNCTION::name.get()
        const val SAVE_LOCATION__FUNCTION__DESCRIPTION =
            "Use this method to save a new location for temi. Locate temi at the location you wish to save and give it a name, the location coordinates are extracted and passed automatically in the request."
        val SAVE_LOCATION__FUNCTION_PARAMS = SAVE_LOCATION__FUNCTION::valueParameters.get()
        val SAVE_LOCATION__FUNCTION_PARAMS__0__LOCATION__NAME =
            getParameterName(SAVE_LOCATION__FUNCTION_PARAMS, 0)
        const val SAVE_LOCATION__FUNCTION_PARAMS__0__LOCATION__DESCRIPTION =
            "Location name you wish to save."

        // goTo() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Locations#goto
        val GO_TO_LOCATION__FUNCTION = TemiRobot::goToLocationFlow
        val GO_TO_LOCATION__FUNCTION__NAME = GO_TO_LOCATION__FUNCTION::name.get()
        const val GO_TO_LOCATION__FUNCTION__DESCRIPTION =
            "Use this method to send Temi to one of your saved locations."
        val GO_TO_LOCATION__FUNCTION_PARAMS = GO_TO_LOCATION__FUNCTION::valueParameters.get()
        val GO_TO_LOCATION__FUNCTION_PARAMS__0__LOCATION__NAME =
            getParameterName(GO_TO_LOCATION__FUNCTION_PARAMS, 0)
        const val GO_TO_LOCATION__FUNCTION_PARAMS__0__LOCATION__DESCRIPTION =
            "Location name you wish the robot to navigate to. Use \'home base\' as location name when go back to charge."
        val GO_TO_LOCATION__FUNCTION_PARAMS__1__BACKWARDS__NAME =
            getParameterName(GO_TO_LOCATION__FUNCTION_PARAMS, 1)
        const val GO_TO_LOCATION__FUNCTION_PARAMS__1__BACKWARDS__DESCRIPTION =
            "If true will walk backwards to the destination. false by default."
        val GO_TO_LOCATION__FUNCTION_PARAMS__2__NO_BYPASS__NAME =
            getParameterName(GO_TO_LOCATION__FUNCTION_PARAMS, 2)
        const val GO_TO_LOCATION__FUNCTION_PARAMS__2__NO_BYPASS__DESCRIPTION =
            "If true will disallow bypass the obstacles during go to. Pass null to follow the Settings -> Navigation Settings."
        val GO_TO_LOCATION__FUNCTION_PARAMS__3__SPEED_LEVEL__NAME =
            getParameterName(GO_TO_LOCATION__FUNCTION_PARAMS, 3)
        const val GO_TO_LOCATION__FUNCTION_PARAMS__3__SPEED_LEVEL__DESCRIPTION =
            "The speed level of this single go to session. Pass null to start with the speed level in Settings -> Navigation Settings."

        val WALK_AROUND_LOCATIONS__FUNCTION = TemiRobot::walkAroundLocationsFlow
        val WALK_AROUND_LOCATIONS__FUNCTION__NAME = WALK_AROUND_LOCATIONS__FUNCTION::name.get()
        const val WALK_AROUND_LOCATIONS__FUNCTION__DESCRIPTION =
            "Use this method to come back Temi to current position after send him to your saved locations."
        val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS = WALK_AROUND_LOCATIONS__FUNCTION::valueParameters.get()
        val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__0__LOCATION_NAME_LIST__NAME =
            getParameterName(WALK_AROUND_LOCATIONS__FUNCTION_PARAMS, 0)
        const val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__0__LOCATION_NAME_LIST__DESCRIPTION =
            "List of location name you wish the robot to navigate to. Use \'home base\' as location name when go back to charge."
        val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__1__ALLOW_BACKWARDS__NAME =
            getParameterName(WALK_AROUND_LOCATIONS__FUNCTION_PARAMS, 1)
        const val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__1__ALLOW_BACKWARDS__DESCRIPTION =
            GO_TO_LOCATION__FUNCTION_PARAMS__1__BACKWARDS__DESCRIPTION
        val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__2__NO_BYPASS__NAME =
            getParameterName(WALK_AROUND_LOCATIONS__FUNCTION_PARAMS, 2)
        const val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__2__NO_BYPASS__DESCRIPTION =
            GO_TO_LOCATION__FUNCTION_PARAMS__2__NO_BYPASS__DESCRIPTION
        val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__3__SPEED_LEVEL__NAME =
            getParameterName(WALK_AROUND_LOCATIONS__FUNCTION_PARAMS, 3)
        const val WALK_AROUND_LOCATIONS__FUNCTION_PARAMS__3__SPEED_LEVEL__DESCRIPTION =
            GO_TO_LOCATION__FUNCTION_PARAMS__3__SPEED_LEVEL__DESCRIPTION

        // beWithMe() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Follow#bewithme
        val BE_WITH_ME__FUNCTION = TemiRobot::beWithMe
        val BE_WITH_ME__FUNCTION__NAME = BE_WITH_ME__FUNCTION::name.get()
        const val BE_WITH_ME__FUNCTION__DESCRIPTION =
            "Use this method to manually invoke the follow mode. Follow mode is the state where temi searches for a person standing in front of it and once found it locks on and follows their movement until told otherwise."

        // constraintBeWith() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Follow#constraintbewith
        val CONSTRAINT_BE_WITH__FUNCTION = TemiRobot::constraintBeWith
        val CONSTRAINT_BE_WITH__FUNCTION__NAME = CONSTRAINT_BE_WITH__FUNCTION::name.get()
        const val CONSTRAINT_BE_WITH__FUNCTION__DESCRIPTION =
            "Use this method to manually invoke the constraint follow mode. Constraint Follow mode is the state where temi searches for a person standing in front of it and once found it locks on to their movement until told otherwise. Unlike the regular follow in constraint mode temi only tilts and turns on its' axis it does not leave its' position."

        // turnBy() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Movement#turnby
        val TURN_BY__FUNCTION = TemiRobot::turnByFlow
        val TURN_BY__FUNCTION__NAME = TURN_BY__FUNCTION::name.get()
        const val TURN_BY__FUNCTION__DESCRIPTION =
            "Use this method to manually turn temi's body by a certain degree."
        val TURN_BY__FUNCTION_PARAMS = TURN_BY__FUNCTION::valueParameters.get()
        val TURN_BY__FUNCTION_PARAMS__0__DEGREE__NAME =
            getParameterName(TURN_BY__FUNCTION_PARAMS, 0)
        const val TURN_BY__FUNCTION_PARAMS__0__DEGREE__DESCRIPTION =
            "The degrees you want to temi's body to turn. For example, to turn to the right, you would enter -90 degrees."
        val TURN_BY__FUNCTION_PARAMS__1__SPEED__NAME =
            getParameterName(TURN_BY__FUNCTION_PARAMS, 1)
        const val TURN_BY__FUNCTION_PARAMS__1__SPEED__DESCRIPTION =
            "The coefficient of the maximum speed, the value range is 0~1."

        // stopMovement() - Temi SDK Guide
        // https://github.com/robotemi/sdk/wiki/Movement#stopmovement
        val STOP_MOVEMENT__FUNCTION = TemiRobot::stopMovement
        val STOP_MOVEMENT__FUNCTION__NAME = STOP_MOVEMENT__FUNCTION::name.get()
        const val STOP_MOVEMENT__FUNCTION__DESCRIPTION =
            "Use this method to manually stop temi from moving."

        val FUNCTION_ARGUMENT_RANGE_LIST__SPEED_LEVEL = listOf("HIGH, MEDIUM, SLOW")
    }
}