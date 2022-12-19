package com.kevinesg.pomodoroapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kevinesg.pomodoroapp.MainActivity
import com.kevinesg.pomodoroapp.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun MainActivityContent(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
    mainActivity: MainActivity
) {
    LaunchedEffect(
        key1 = viewModel.isWorking.value,
        block = {
            if (viewModel.isWorking.value) {
                while (true) {
                    delay(1000)
                    viewModel.workTimer.value++
                }
            }
        }
    )
    LaunchedEffect(
        key1 = viewModel.isResting.value,
        block = {
            if (viewModel.isResting.value) {
                while (true) {
                    delay(1000)
                    viewModel.restTimer.value++
                }
            }
        }
    )

    Box {
        NavHost(
            navController = navController,
            startDestination = Screens.HOME.name
        ) {
            composable(route = Screens.HOME.name) {
                HomeScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(route = Screens.WORK.name) {
                WorkScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(route = Screens.REST.name) {
                RestScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(route = Screens.PAUSE.name) {
                PauseScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

        OptionsButton(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        )

        if (viewModel.showAlert.value) {
            ConfirmReset(
                viewModel = viewModel,
                navController = navController
            )
        }

        if (viewModel.showOptions.value) {
            OptionsDropdownMenu(
                viewModel = viewModel,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
        }

        if (viewModel.showHelp.value) {
            Help(viewModel = viewModel)
        }

        if (viewModel.showAbout.value) {
            About(viewModel = viewModel)
        }

        if (viewModel.showConfirmExitApp.value) {
            ConfirmExitApp(
                viewModel = viewModel,
                mainActivity = mainActivity
            )
        }
    }
}


enum class Screens {
    HOME,
    WORK,
    REST,
    PAUSE
}