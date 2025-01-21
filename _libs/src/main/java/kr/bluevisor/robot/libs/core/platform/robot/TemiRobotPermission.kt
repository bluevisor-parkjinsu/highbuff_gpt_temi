package kr.bluevisor.robot.libs.core.platform.robot

import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.permission.OnRequestPermissionResultListener
import com.robotemi.sdk.permission.Permission
import enn.libs.and.llog.LLog
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.WeakHashMap

interface TemiRobotPermission {
    fun checkSelfPermission(permission: Permission): Int
    fun checkSelfPermissions(vararg permissions: Permission): Boolean
    fun requestPermissions(permissions: List<Permission>, requestCode: Int)
    fun requestPermissionsFlow(vararg permissions: Permission): Flow<Map<Permission, Boolean>>

    fun registerPrivatePermissionObservers()
    fun unregisterPrivatePermissionObservers()
    fun clearAllPermissionListeners(parent: Any? = null)
    fun addOnRequestPermissionResultListener(
        parent: Any, listener: OnRequestPermissionResultListener)
    fun removeOnRequestPermissionResultListener(
        parent: Any, listener: OnRequestPermissionResultListener)
    fun clearAllOnRequestPermissionResultListeners(parent: Any)

    companion object {
        val ALL_PERMISSIONS = listOf(
            Permission.FACE_RECOGNITION,
            Permission.MAP,
            Permission.SETTINGS,
            Permission.SEQUENCE,
            Permission.MEETINGS
        )
    }
}

class TemiRobotPermissionDelegator(private val core: Robot) : TemiRobotPermission {
    private val privateRequestPermissionResultListener =
        newPrivateRequestPermissionResultListener()
    private val requestPermissionResultListenerMap =
        WeakHashMap<Any, MutableSet<OnRequestPermissionResultListener>>()

    private fun newPrivateRequestPermissionResultListener()
    = object : OnRequestPermissionResultListener {
        override fun onRequestPermissionResult(
            permission: Permission,
            grantResult: Int,
            requestCode: Int,
        ) {
            requestPermissionResultListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach {
                    it.onRequestPermissionResult(permission, grantResult, requestCode)
                }
            }
            LLog.print(
                if (grantResult == Permission.DENIED) Log.WARN else Log.VERBOSE,
                "permission: $permission, grantResult: $grantResult," +
                        " requestCode: $requestCode.")
        }
    }

    override fun checkSelfPermission(permission: Permission) =
        core.checkSelfPermission(permission)

    override fun checkSelfPermissions(vararg permissions: Permission) =
        permissions.all { core.checkSelfPermission(it) == Permission.GRANTED }

    override fun requestPermissions(permissions: List<Permission>, requestCode: Int) =
        core.requestPermissions(permissions, requestCode)

    override fun requestPermissionsFlow(vararg permissions: Permission) = callbackFlow {
        val permissionResultMap = mutableMapOf<Permission, Boolean>()
        val notAllowedPermissionList = permissions
            .filter {
                val granted = core.checkSelfPermission(it) == Permission.GRANTED
                if (granted) permissionResultMap[it] = true
                !granted
            }

        fun trySendPermissionResultMap(): Boolean {
            if (trySend(permissionResultMap.toMap()).isFailure) {
                val errorMessage = "call TrySend() is failed:" +
                        " permissions: $permissions, permissionResultMap: $permissionResultMap."
                close(IllegalStateException(errorMessage))
                LLog.w(errorMessage)
                return false
            }
            return true
        }

        if (notAllowedPermissionList.isEmpty()) {
            if (!trySendPermissionResultMap()) return@callbackFlow
            close()
            LLog.v("Already permissions are allowed: permissions: $permissions.")
            return@callbackFlow
        }

        val currentRequestCode = hashCode()
        val listener = object : OnRequestPermissionResultListener {
            override fun onRequestPermissionResult(
                permission: Permission,
                grantResult: Int,
                requestCode: Int,
            ) {
                if (requestCode != currentRequestCode) return
                permissionResultMap[permission] = grantResult == Permission.GRANTED

                if (permissionResultMap.size >= permissions.size) {
                    if (!trySendPermissionResultMap()) return
                    LLog.v("permissionResultMap: $permissionResultMap.")
                    close()
                }
                LLog.v("permission: $permission, grantResult: $grantResult," +
                        " requestCode: $requestCode.")
            }
        }
        core.addOnRequestPermissionResultListener(listener)
        core.requestPermissions(notAllowedPermissionList, currentRequestCode)

        LLog.v("permissions: $permissions, permissionResultMap: $permissionResultMap," +
                " notAllowedPermissionList: $notAllowedPermissionList," +
                " currentRequestCode: $currentRequestCode.")
        awaitClose { core.removeOnRequestPermissionResultListener(listener) }
    }

    override fun registerPrivatePermissionObservers() {
        core.addOnRequestPermissionResultListener(privateRequestPermissionResultListener)
        LLog.v()
    }

    override fun unregisterPrivatePermissionObservers() {
        core.removeOnRequestPermissionResultListener(privateRequestPermissionResultListener)
        LLog.v()
    }

    override fun clearAllPermissionListeners(parent: Any?) {
        if (parent == null) {
            requestPermissionResultListenerMap.clear()
            LLog.v("parent is null. It will be removed all.")
            return
        }

        clearAllOnRequestPermissionResultListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnRequestPermissionResultListener(
        parent: Any, listener: OnRequestPermissionResultListener
    ) = TemiRobotDelegators.addListener(requestPermissionResultListenerMap, parent, listener)

    override fun removeOnRequestPermissionResultListener(
        parent: Any, listener: OnRequestPermissionResultListener
    ) = TemiRobotDelegators.removeListener(requestPermissionResultListenerMap, parent, listener)

    override fun clearAllOnRequestPermissionResultListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(requestPermissionResultListenerMap, parent)
}