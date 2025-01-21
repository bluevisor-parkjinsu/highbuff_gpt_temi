package kr.bluevisor.robot.libs.core.platform.robot

import enn.libs.and.llog.LLog
import kotlin.enums.EnumEntries

class TemiRobotDelegators {
    companion object {
        inline fun <reified T> addListener(
            listenerMap: MutableMap<Any, MutableSet<T>>, parent: Any, listener: T
        ) {
            listenerMap.getOrPut(parent, ::mutableSetOf).add(listener)
            LLog.v("listenerMap: $listenerMap, parent: $parent, listener: $listener.")
        }

        inline fun <reified T> removeListener(
            listenerMap: MutableMap<Any, MutableSet<T>>, parent: Any, listener: T
        ) {
            listenerMap[parent]?.remove(listener)
            LLog.v("listenerMap: $listenerMap, parent: $parent, listener: $listener.")
        }

        inline fun <reified T> clearAllListeners(
            listenerMap: MutableMap<Any, MutableSet<T>>, parent: Any
        ) {
            listenerMap.remove(parent)
            LLog.v("listenerMap: $listenerMap, parent: $parent.")
        }

        inline fun <T, reified E: Enum<E>> valueFrom(
            entries: EnumEntries<E>,
            targetValue: T,
            sourceValueGetter: (E) -> T
        ): E {
            return entries.find { sourceValueGetter(it) == targetValue }
                ?: throw IllegalArgumentException("Matched element of specific Enum not found:" +
                        " Enum.class: ${E::class}, targetValue: $targetValue.")
        }
    }
}