/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.bluevisor.robot.libs.core.platform.media.process

import enn.libs.and.llog.LLog
import org.tensorflow.lite.support.label.Category
import kotlin.math.max

class AudioClassifiedWavWriter(
    private val targetYamnetAudioCategoryIndexSet: Set<Int>,
    private val recentDetectionHistoryQueueMaxSize: Int,
    bufferSize: Int
): AudioWavWriter(bufferSize) {
    private val recentDetectionHistoryQueue = mutableListOf<Boolean>()

    fun hasRecentDetectionInHistory() = recentDetectionHistoryQueue.contains(true)

    private fun enqueRecentDetectionHistoryQueue(categories: Collection<Category>) {
        val detected = categories.any { targetYamnetAudioCategoryIndexSet.contains(it.index) }
        recentDetectionHistoryQueue.add(detected)
        if (recentDetectionHistoryQueue.size > max(recentDetectionHistoryQueueMaxSize, 0)) {
            recentDetectionHistoryQueue.removeAt(0)
        }

        // Called frequently.
        LLog.v("detection: $detected, categories: $categories.")
    }

    fun clearRecentDetectionHistoryQueue() = recentDetectionHistoryQueue.clear()

    fun needFlushAndEnqueueClassifiedAudio(
        categories: Collection<Category>,
        buffer: FloatArray,
        audioReadResult: Int,
    ): Boolean {
        enqueRecentDetectionHistoryQueue(categories)
        if (hasRecentDetectionInHistory()) {
            storeAudioBytes(buffer, audioReadResult)
        } else if (!hasRecentDetectionInHistory() && hasStoredBytes) {
            return true
        }
        return false
    }
}
