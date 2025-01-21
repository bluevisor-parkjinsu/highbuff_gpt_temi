package kr.bluevisor.robot.highbuff_gpt_temi.util

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.atomic.AtomicBoolean

// SingleLiveEvent 클래스
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun setValue(t: T?) {
        if (mPending.compareAndSet(false, true)) {
            super.setValue(t)
        }
    }

    override fun postValue(value: T?) {
        if (mPending.compareAndSet(false, true)) {
            super.postValue(value)
        }
    }

    // 이벤트가 처리된 후에 "Reset" 상태로 돌아가는 메소드
    fun call() {
        value = null
    }
}