package com.bsitapps.lifecycleownerdemo

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class TimerToast(application: Application, lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private var isTimerStarted = false;
    private val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    init {
        lifecycle.addObserver(this)
    }

    private val timer: CountDownTimer = object : CountDownTimer(60000, 3000) {
        override fun onTick(leftTime: Long) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                Toast.makeText(application, "$leftTime", Toast.LENGTH_SHORT).show()
        }

        override fun onFinish() {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                Toast.makeText(application, "Finished", Toast.LENGTH_SHORT).show()
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        if (!isTimerStarted) {
            isTimerStarted = true
            timer.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopTimer() {
        timer.cancel()
        isTimerStarted = false
    }
}