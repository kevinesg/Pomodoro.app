package com.kevinesg.pomodoroapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kevinesg.pomodoroapp.MainViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFfff3e0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Let's go!", fontSize = 35.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        WorkButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        RestButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(navController = navController, viewModel = viewModel)
    }

    BackHandler(enabled = true) {
        viewModel.showConfirmExitApp.value = true

    }
}


@Composable
fun WorkScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFffcdd2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkTimerChecker(viewModel = viewModel)
        Text(text = "Work", color = Color.Black, fontSize = 25.sp)
        Text(
            text = viewModel.displayClock(viewModel.workTimer.value),
            fontSize = 35.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        RestButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(navController = navController, viewModel = viewModel)

        BackHandler {
            navController.navigate(Screens.HOME.name)
            viewModel.isWorking.value = false
            viewModel.isResting.value = false
        }
    }
}


@Composable
fun RestScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFb2dfdb)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.workTimer.value < viewModel.restTimer.value * 5) {
            WorkTimerChecker(viewModel = viewModel)
        } else {
            RestTimerChecker(viewModel = viewModel)
        }
        Text(text = "Rest", color = Color.Black, fontSize = 25.sp)
        Text(
            text = viewModel.displayClock(viewModel.restTimer.value),
            fontSize = 35.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        WorkButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(navController = navController, viewModel = viewModel)

        BackHandler {
            navController.navigate(Screens.HOME.name)
            viewModel.isWorking.value = false
            viewModel.isResting.value = false
        }
    }
}


@Composable
fun PauseScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFfff3e0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkTimerChecker(viewModel = viewModel)
        Text(
            text = "Work ${viewModel.displayClock(viewModel.workTimer.value)}",
            color = Color.Black,
            fontSize = 25.sp
        )
        Text(
            text = "Rest ${viewModel.displayClock(viewModel.restTimer.value)}",
            color = Color.Black,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        WorkButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        RestButton(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(navController = navController, viewModel = viewModel)

        BackHandler {
            navController.navigate(Screens.HOME.name)
            viewModel.isWorking.value = false
            viewModel.isResting.value = false
        }
    }
}