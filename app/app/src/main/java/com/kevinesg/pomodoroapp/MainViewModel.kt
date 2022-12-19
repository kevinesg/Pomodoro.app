package com.kevinesg.pomodoroapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val workTimer = mutableStateOf(0)
    val restTimer = mutableStateOf(0)
    val isWorking = mutableStateOf(false)
    val isResting = mutableStateOf(false)
    val showAlert = mutableStateOf(false)
    val showOptions = mutableStateOf(false)
    val showHelp = mutableStateOf(false)
    val showAbout = mutableStateOf(false)
    val showConfirmExitApp = mutableStateOf(false)


    fun pauseTimers() {
        isWorking.value = false
        isResting.value = false
    }

    fun resetTimers() {
        workTimer.value = 0
        restTimer.value = 0
        isWorking.value = false
        isResting.value = false
    }

    fun displayClock(timer: Int): String {
        val minutes: Int = (timer / 60)
        val seconds: Int = timer.mod(60)

        val minutesStr = if (minutes.toString().length == 1) {
            "0$minutes"
        } else {
            "$minutes"
        }

        val secondsStr = if (seconds.toString().length == 1) {
            "0$seconds"
        } else {
            "$seconds"
        }

        return "$minutesStr:$secondsStr"
    }

    fun extraWork(): String {
        return displayClock(restTimer.value * 5 - workTimer.value)
    }

    fun extraRest(): String {
        return displayClock(workTimer.value / 5 - restTimer.value)
    }
}