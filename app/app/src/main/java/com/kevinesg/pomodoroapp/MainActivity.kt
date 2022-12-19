package com.kevinesg.pomodoroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import com.kevinesg.pomodoroapp.ui.MainActivityContent
import com.kevinesg.pomodoroapp.ui.theme.PomodoroappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroappTheme {
                Surface {
                    MainActivityContent(
                        viewModel = ViewModelProvider(this)[MainViewModel::class.java],
                        mainActivity = this
                    )
                }
            }
        }
    }
}