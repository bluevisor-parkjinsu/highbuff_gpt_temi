package kr.bluevisor.robot.libs.core.platform.robot

import android.os.Build
import androidx.annotation.RequiresApi
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import com.robotemi.sdk.map.LayerPose
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.navigation.model.SpeedLevel
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import java.util.WeakHashMap
import kotlin.coroutines.coroutineContext
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

interface TemiRobotNavigation {
    val locationList: List<String>
    val currentPositionFlow: StateFlow<Position>
    val locationDistanceMapFlow: StateFlow<Map<String, Float>>

    fun saveLocation(location: String): Boolean
    fun saveLocationFlow(location: String): Flow<Boolean>

    fun goToLocationFlow(
        locationName: String,
        allowBackwards: Boolean? = null,
        noBypass: Boolean? = null,
        speedLevel: SpeedLevel? = null
    ): Flow<Map<String, Any>>

    fun walkAroundLocationsFlow(
        targetLocationNameList: List<String>,
        allowBackwards: Boolean? = null,
        noBypass: Boolean? = null,
        speedLevel: SpeedLevel? = null
    ): Flow<Map<String, Any>>

    fun runTaskAndReturnCurrentPositionFlow(
        allowBackwards: Boolean? = null,
        noBypass: Boolean? = null,
        speedLevel: SpeedLevel? = null,
        task: suspend () -> Unit
    ): Flow<Map<String, Any>>

    fun getNavigationOptimizingSortedLocationList(
        vararg locationNames: String
    ): List<Pair<String, Float>>
    fun getNearestLocation(
        sourcePositionX: Float,
        sourcePositionY: Float,
        destinationLocationPositionMap: Map<String, Pair<Float, Float>>
    ): Triple<String, Pair<Float, Float>, Float>
    fun getLocationPosition(vararg locationNames: String): List<Pair<Float, Float>?>
    fun getLocationPoseMap(): Map<String, LayerPose?>

    fun calcDistanceFromCurrentPosition(destinationX: Float, destinationY: Float): Float
    fun calcDistanceBetweenLocation(
        sourceLocationName: String, destinationLocationName: String): Float

    fun registerPrivateNavigationObservers()
    fun unregisterPrivateNavigationObservers()
    fun clearAllNavigationListeners(parent: Any? = null)
    fun addOnGoToLocationStatusChangedListener(
        parent: Any, listener: OnGoToLocationStatusChangedListener)
    fun removeOnGoToLocationStatusChangedListener(
        parent: Any, listener: OnGoToLocationStatusChangedListener)
    fun clearAllOnGoToLocationStatusChangedListeners(parent: Any)
    fun addOnCurrentPositionChangedListener(
        parent: Any, listener: OnCurrentPositionChangedListener)
    fun removeOnCurrentPositionChangedListener(
        parent: Any, listener: OnCurrentPositionChangedListener)
    fun clearAllOnCurrentPositionChangedListeners(parent: Any)
    fun addOnDistanceToLocationChangedListener(
        parent: Any, listener: OnDistanceToLocationChangedListener)
    fun removeOnDistanceToLocationChangedListener(
        parent: Any, listener: OnDistanceToLocationChangedListener)
    fun clearAllOnDistanceToLocationChangedListeners(parent: Any)

    class NavigationAbortException : RuntimeException {
        constructor() : super()

        @Suppress("unused")
        constructor(message: String?) : super(message)

        @Suppress("unused")
        constructor(message: String?, cause: Throwable?) : super(message, cause)

        @Suppress("unused")
        constructor(cause: Throwable?) : super(cause)

        @Suppress("unused")
        @RequiresApi(Build.VERSION_CODES.N)
        constructor(
            message: String?,
            cause: Throwable?,
            enableSuppression: Boolean,
            writableStackTrace: Boolean,
        ) : super(message, cause, enableSuppression, writableStackTrace)
    }

    companion object {
        @Suppress("unused")
        const val LOCATION_NAME__NOT_NAMED_POSITION = "COORDINATES"
        val EMPTY_POSITION = Position()

        fun calcDistance(sourceX: Float, sourceY: Float, destinationX: Float, destinationY: Float): Float {
            val distanceX = abs(destinationX - sourceX)
            val distanceY = abs(destinationY - sourceY)
            return sqrt(
                distanceX.toDouble().pow(2.0) + distanceY.toDouble().pow(2.0)
            ).toFloat()
        }
    }
}

class TemiRobotNavigationDelegator(private val core: Robot) : TemiRobotNavigation {
    private val privateGoToLocationStatusChangedListener =
        newPrivateGoToLocationStatusChangedListener()
    private val goToLocationStatusChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnGoToLocationStatusChangedListener>>()

    private val privateCurrentPositionChangedListener =
        newPrivateCurrentPositionChangedListener()
    private val currentPositionChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnCurrentPositionChangedListener>>()

    private val privateDistanceToLocationChangedListener =
        newPrivateDistanceToLocationChangedListener()
    private val distanceToLocationChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnDistanceToLocationChangedListener>>()

    override val locationList
        get() = core.locations

    private val _currentPositionFlow = MutableStateFlow(TemiRobotNavigation.EMPTY_POSITION)
    override val currentPositionFlow = _currentPositionFlow.asStateFlow()

    private val _locationDistanceMapFlow = MutableStateFlow<Map<String, Float>>(mapOf())
    override val locationDistanceMapFlow = _locationDistanceMapFlow.asStateFlow()

    private fun newPrivateGoToLocationStatusChangedListener()
    = object : OnGoToLocationStatusChangedListener {
        override fun onGoToLocationStatusChanged(
            location: String,
            status: String,
            descriptionId: Int,
            description: String,
        ) {
            goToLocationStatusChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach {
                    it.onGoToLocationStatusChanged(location, status, descriptionId, description)
                }
            }
            LLog.v("location: $location, status: $status, descriptionId: $description," +
                    " description: $description.")
        }
    }

    private fun newPrivateCurrentPositionChangedListener()
    = object : OnCurrentPositionChangedListener {
        override fun onCurrentPositionChanged(position: Position) {
            _currentPositionFlow.value = position
            currentPositionChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onCurrentPositionChanged(position) }
            }
            LLog.v("position: $position.")
        }
    }

    private fun newPrivateDistanceToLocationChangedListener()
    = object : OnDistanceToLocationChangedListener {
        override fun onDistanceToLocationChanged(distances: Map<String, Float>) {
            _locationDistanceMapFlow.value = distances
            distanceToLocationChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onDistanceToLocationChanged(distances) }
            }
            LLog.v("distances: $distances.")
        }
    }

    override fun saveLocation(location: String): Boolean = core.saveLocation(location)

    override fun saveLocationFlow(location: String): Flow<Boolean> = flow {
        emit(core.saveLocation(location))
        LLog.v("location: $location.")
    }

    override fun goToLocationFlow(
        locationName: String,
        allowBackwards: Boolean?,
        noBypass: Boolean?,
        speedLevel: SpeedLevel?
    ) = callbackFlow {
        if (!locationList.contains(locationName)) {
            val errorMessage = "Invalid locationName: $locationName."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        val goToLocationStatusChangedListener = object : OnGoToLocationStatusChangedListener {
            override fun onGoToLocationStatusChanged(
                location: String, status: String, descriptionId: Int, description: String
            ) {
                val (parameterMap, isContinued) = trySendOnGoToLocationStatusChanged(
                    core, this, location, status, descriptionId, description)
                if (!isContinued) return

                when (status) {
                    OnGoToLocationStatusChangedListener.COMPLETE -> {
                        core.removeOnGoToLocationStatusChangedListener(this)
                        close()
                    }
                    OnGoToLocationStatusChangedListener.ABORT -> {
                        core.removeOnGoToLocationStatusChangedListener(this)
                        close(TemiRobotNavigation.NavigationAbortException())
                    }
                }
                LLog.v("parameterMap: $parameterMap.")
            }
        }
        core.addOnGoToLocationStatusChangedListener(goToLocationStatusChangedListener)
        core.goTo(locationName, allowBackwards, noBypass, speedLevel)

        LLog.v("locationName: $locationName, allowBackwards: $allowBackwards," +
                " noBypass: $noBypass, speedLevel: $speedLevel.")
        awaitClose {
            core.removeOnGoToLocationStatusChangedListener(goToLocationStatusChangedListener)
            LLog.v("awaitClose() called:" +
                    " locationName: $locationName, allowBackwards: $allowBackwards," +
                    " noBypass: $noBypass, speedLevel: $speedLevel.")
        }
    }

    override fun walkAroundLocationsFlow(
        targetLocationNameList: List<String>,
        allowBackwards: Boolean?,
        noBypass: Boolean?,
        speedLevel: SpeedLevel?,
    ): Flow<Map<String, Any>> {
        if (targetLocationNameList.any { !locationList.contains(it) }) {
            val errorMessage = "Invalid targetLocationNameList: $targetLocationNameList."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        LLog.v("locationNameList: $targetLocationNameList, allowBackwards: $allowBackwards," +
                " noBypass: $noBypass, speedLevel: $speedLevel.")
        return runTaskAndReturnCurrentPositionFlow {
            targetLocationNameList.forEach {
                goToLocationFlow(it, allowBackwards, noBypass, speedLevel)
                    .launchIn(CoroutineScope(coroutineContext))
                    .join()
            }
        }
    }

    override fun runTaskAndReturnCurrentPositionFlow(
        allowBackwards: Boolean?,
        noBypass: Boolean?,
        speedLevel: SpeedLevel?,
        task: suspend () -> Unit
    ) = callbackFlow {
        val startPosition = core.getPosition()
        LLog.v("allowBackwards: $allowBackwards, noBypass: $noBypass, speedLevel: $speedLevel," +
                " startPosition: $startPosition.")

        val taskingGoToLocationListener = object : OnGoToLocationStatusChangedListener {
            override fun onGoToLocationStatusChanged(
                location: String,
                status: String,
                descriptionId: Int,
                description: String,
            ) {
                trySendOnGoToLocationStatusChanged(
                    core, this, location, status, descriptionId, description)
            }
        }
        core.addOnGoToLocationStatusChangedListener(taskingGoToLocationListener)
        task()
        core.removeOnGoToLocationStatusChangedListener(taskingGoToLocationListener)

        val postTaskGoToLocationListener = object : OnGoToLocationStatusChangedListener {
            override fun onGoToLocationStatusChanged(
                location: String,
                status: String,
                descriptionId: Int,
                description: String,
            ) {
                val (parameterMap, isContinued) = trySendOnGoToLocationStatusChanged(
                    core, this, location, status, descriptionId, description)
                if (!isContinued) return

                when (status) {
                    OnGoToLocationStatusChangedListener.COMPLETE -> {
                        core.removeOnGoToLocationStatusChangedListener(this)
                        close()
                    }
                    OnGoToLocationStatusChangedListener.ABORT -> {
                        core.removeOnGoToLocationStatusChangedListener(this)
                        close(TemiRobotNavigation.NavigationAbortException())
                    }
                }
                LLog.v("parameterMap: $parameterMap.")
            }
        }
        core.addOnGoToLocationStatusChangedListener(postTaskGoToLocationListener)
        core.goToPosition(
            startPosition,
            backwards = allowBackwards,
            noBypass = noBypass,
            speedLevel = speedLevel)

        awaitClose {
            core.removeOnGoToLocationStatusChangedListener(taskingGoToLocationListener)
            core.removeOnGoToLocationStatusChangedListener(postTaskGoToLocationListener)
            LLog.v("awaitClose() called: allowBackwards: $allowBackwards noBypass: $noBypass," +
                    " speedLevel: $speedLevel, startPosition: $startPosition.")
        }
    }

    override fun getNavigationOptimizingSortedLocationList(
        vararg locationNames: String
    ): List<Pair<String, Float>> {
        val currentPosition = core.getPosition()
        val locationPoseMap = getLocationPoseMap()
        val targetLocationPositionMap = locationNames
            .asSequence()
            .map { locationName -> locationName to locationPoseMap[locationName] }
            .filterNot { (_, locationPose) -> locationPose == null }
            .fold(mutableMapOf<String, Pair<Float, Float>>()) { map, (locationName, locationPose) ->
                @Suppress("NAME_SHADOWING")
                val locationPose = locationPose!!
                map[locationName] = locationPose.x to locationPose.y
                map
            }

        val sortedLocationList = mutableListOf<Pair<String, Float>>()
        var sourceLocationX = currentPosition.x
        var sourceLocationY = currentPosition.y
        while (targetLocationPositionMap.isNotEmpty()) {
            val (nearestLocationName, nearestLocationPosition, distance) =
                getNearestLocation(sourceLocationX, sourceLocationY, targetLocationPositionMap)

            sortedLocationList.add(nearestLocationName to distance)
            targetLocationPositionMap.remove(nearestLocationName)
            sourceLocationX = nearestLocationPosition.first
            sourceLocationY = nearestLocationPosition.second
        }
        return sortedLocationList
    }

    override fun getNearestLocation(
        sourcePositionX: Float,
        sourcePositionY: Float,
        destinationLocationPositionMap: Map<String, Pair<Float, Float>>
    ): Triple<String, Pair<Float, Float>, Float> {
        return destinationLocationPositionMap.entries
            .asSequence()
            .map { entry ->
                val locationName = entry.key
                val (locationPositionX, locationPositionY) = entry.value
                val distance = TemiRobotNavigation.calcDistance(
                    sourcePositionX, sourcePositionY, locationPositionX, locationPositionY)
                Triple(locationName, locationPositionX to locationPositionY, distance)
            }
            .sortedBy { (_, _, distance) -> distance }
            .first()
    }

    override fun getLocationPosition(vararg locationNames: String): List<Pair<Float, Float>?> {
        val locationPoseMap = getLocationPoseMap()
        fun getPositionWithLog(locationName: String): Pair<Float, Float>? {
            if (!locationPoseMap.containsKey(locationName)) {
                LLog.w("The location does not exist: locationName: $locationName.")
                return null
            } else {
                return locationPoseMap[locationName]
                    ?.run { x to y }
                    ?: run {
                        LLog.w("The pose of the location does not exist:" +
                                " locationName: $locationName.")
                        return null
                    }
            }
        }
        return locationNames.map { getPositionWithLog(it) }
    }

    override fun getLocationPoseMap(): Map<String, LayerPose?> {
        return core.getMapData()?.locations
            ?.asSequence()
            ?.map { layer ->
                val locationName = layer.layerId
                val layerPosesSize = layer.layerPoses?.size ?: 0

                if (layerPosesSize != 1) {
                    LLog.w("$locationName has not pose or has more than 1:" +
                            " layerPosesSize: $layerPosesSize.")
                }
                locationName to layer.layerPoses?.get(0)
            }
            ?.fold(mutableMapOf<String, LayerPose?>()) { map, (locationName, pose) ->
                map.apply { put(locationName, pose) }
            }
            ?.toMap()
            ?: emptyMap()
    }

    override fun calcDistanceFromCurrentPosition(destinationX: Float, destinationY: Float): Float {
        val currentPosition = core.getPosition()
        LLog.v("currentPosition: $currentPosition," +
                " destinationX: $destinationX, destinationY: $destinationY.")
        return TemiRobotNavigation.calcDistance(
            currentPosition.x, currentPosition.y, destinationX, destinationY)
    }

    override fun calcDistanceBetweenLocation(
        sourceLocationName: String,
        destinationLocationName: String
    ): Float {
        val locationPositionList = getLocationPosition(sourceLocationName, destinationLocationName)
        val (sourceLocationX, sourceLocationY) = locationPositionList[0]
            ?: throw IllegalArgumentException("sourceLocation does not have a position:" +
                    " sourceLocationName: $sourceLocationName.")
        val (destinationLocationX, destinationLocationY) = locationPositionList[1]
            ?: throw IllegalArgumentException("destinationLocation does not have a position:" +
                    " destinationLocationName: $destinationLocationName.")

        LLog.v("sourceLocationX: $sourceLocationX," +
                " sourceLocationY: $sourceLocationY," +
                " destinationLocationX: $destinationLocationX," +
                " destinationLocationY: $destinationLocationY.")
        return TemiRobotNavigation.calcDistance(
            sourceLocationX, sourceLocationY, destinationLocationX, destinationLocationY)
    }

    override fun registerPrivateNavigationObservers() {
        core.addOnGoToLocationStatusChangedListener(privateGoToLocationStatusChangedListener)
        core.addOnCurrentPositionChangedListener(privateCurrentPositionChangedListener)
        core.addOnDistanceToLocationChangedListener(privateDistanceToLocationChangedListener)
        LLog.v()
    }

    override fun unregisterPrivateNavigationObservers() {
        core.removeOnGoToLocationStatusChangedListener(privateGoToLocationStatusChangedListener)
        core.removeOnCurrentPositionChangedListener(privateCurrentPositionChangedListener)
        core.removeOnDistanceToLocationChangedListener(privateDistanceToLocationChangedListener)
        LLog.v()
    }

    override fun clearAllNavigationListeners(parent: Any?) {
        if (parent == null) {
            goToLocationStatusChangedListenerMap.clear()
            currentPositionChangedListenerMap.clear()
            distanceToLocationChangedListenerMap.clear()
            LLog.v("parent is null. It will be removed all listeners.")
            return
        }

        clearAllOnGoToLocationStatusChangedListeners(parent)
        clearAllOnCurrentPositionChangedListeners(parent)
        clearAllOnDistanceToLocationChangedListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnGoToLocationStatusChangedListener(
        parent: Any, listener: OnGoToLocationStatusChangedListener,
    ) = TemiRobotDelegators.addListener(goToLocationStatusChangedListenerMap, parent, listener)

    override fun removeOnGoToLocationStatusChangedListener(
        parent: Any, listener: OnGoToLocationStatusChangedListener,
    ) = TemiRobotDelegators.removeListener(goToLocationStatusChangedListenerMap, parent, listener)

    override fun clearAllOnGoToLocationStatusChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(goToLocationStatusChangedListenerMap, parent)

    override fun addOnCurrentPositionChangedListener(
        parent: Any, listener: OnCurrentPositionChangedListener,
    ) = TemiRobotDelegators.addListener(currentPositionChangedListenerMap, parent, listener)

    override fun removeOnCurrentPositionChangedListener(
        parent: Any, listener: OnCurrentPositionChangedListener,
    ) = TemiRobotDelegators.removeListener(currentPositionChangedListenerMap, parent, listener)

    override fun clearAllOnCurrentPositionChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(currentPositionChangedListenerMap, parent)

    override fun addOnDistanceToLocationChangedListener(
        parent: Any, listener: OnDistanceToLocationChangedListener,
    ) = TemiRobotDelegators.addListener(distanceToLocationChangedListenerMap, parent, listener)

    override fun removeOnDistanceToLocationChangedListener(
        parent: Any, listener: OnDistanceToLocationChangedListener,
    ) = TemiRobotDelegators.removeListener(distanceToLocationChangedListenerMap, parent, listener)

    override fun clearAllOnDistanceToLocationChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(distanceToLocationChangedListenerMap, parent)

    companion object {
        private fun ProducerScope<Map<String, Any>>.trySendOnGoToLocationStatusChanged(
            core: Robot,
            thisListener: OnGoToLocationStatusChangedListener,
            location: String,
            status: String,
            descriptionId: Int,
            description: String
        ): Pair<Map<String, Any>, Boolean> {
            val parameterMap = mapOf(
                "location" to location,
                "status" to status,
                "descriptionId" to descriptionId,
                "description" to description
            )

            @OptIn(DelicateCoroutinesApi::class)
            val isClosed = isClosedForSend
            if (isClosed) {
                core.removeOnGoToLocationStatusChangedListener(thisListener)
                LLog.v("Closed callbackFlow try to emit: parameterMap: $parameterMap.")
                return parameterMap to false
            } else if (trySend(parameterMap).isFailure) {
                LLog.w("trySend() is failed.")
            }
            return parameterMap to true
        }
    }
}