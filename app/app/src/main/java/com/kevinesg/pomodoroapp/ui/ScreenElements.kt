package com.kevinesg.pomodoroapp.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.kevinesg.pomodoroapp.MainActivity
import com.kevinesg.pomodoroapp.MainViewModel
import com.kevinesg.pomodoroapp.R
import kotlin.system.exitProcess

@Composable
fun WorkButton(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Button(
        modifier = Modifier.width(150.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF000000), contentColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(20),
        onClick = {
            viewModel.isWorking.value = true
            viewModel.isResting.value = false
            navController.navigate(Screens.WORK.name) {
                popUpTo(0)
            }
        }
    ) {
        Text(text = "Work", fontSize = 25.sp, color = Color(0xFFFFFFFF))
    }
}


@Composable
fun RestButton(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Button(
        modifier = Modifier.width(150.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF000000), contentColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(20),
        onClick = {
            viewModel.isResting.value = true
            viewModel.isWorking.value = false
            navController.navigate(Screens.REST.name) {
                popUpTo(0)
            }
        }
    ) {
        Text(text = "Rest", fontSize = 25.sp, color = Color(0xFFFFFFFF))
    }
}


@Composable
fun BottomButtons(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.pause_circle_outline),
            contentDescription = "Pause Button",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    viewModel.pauseTimers()
                    navController.navigate(Screens.PAUSE.name) {
                        popUpTo(0)
                    }
                },
            colorResource(id = R.color.black)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Icon(
            painter = painterResource(id = R.drawable.close_circle_outline),
            contentDescription = "Reset Button",
            modifier = Modifier
                .size(50.dp)
                .clickable { viewModel.showAlert.value = true },
            colorResource(id = R.color.black)
        )

    }
}


@Composable
fun WorkTimerChecker(viewModel: MainViewModel) {
    if (viewModel.workTimer.value < viewModel.restTimer.value * 5) {
        Text(
            "I have to work extra ${viewModel.extraWork()}",
            color = Color(0xFF000000),
            fontSize = 25.sp
        )
    } else {
        Text(" ", fontSize = 25.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
fun RestTimerChecker(viewModel: MainViewModel) {
    if (viewModel.workTimer.value >= viewModel.restTimer.value * 5) {
        Text(
            "I can rest for at most ${viewModel.extraRest()}",
            color = Color(0xFF000000),
            fontSize = 25.sp
        )
    } else {
        Text(" ", fontSize = 25.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
fun ConfirmReset(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Surface (
        color = Color.Black.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { viewModel.showAlert.value = false }
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp, 275.dp)
                    .background(Color(0xFF42414d), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Are you sure you want to clear the session?",
                        color = Color(0xFFFFFFFF),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00ddff), contentColor = Color(0xFF000000)
                        ),
                        onClick = {
                            viewModel.resetTimers()
                            viewModel.showAlert.value = false
                            navController.navigate(Screens.HOME.name) {
                                popUpTo(0)
                            }
                        }
                    ) {
                        Text("Yes", color = Color(0xFFFFFFFF), fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2b2a33), contentColor = Color(0xFFFFFFFF)
                        ),
                        onClick = { viewModel.showAlert.value = false }
                    ) {
                        Text("No", color = Color(0xFFFFFFFF), fontSize = 20.sp)
                    }
                }
            }
        }

        BackHandler {
            if (viewModel.showAlert.value) {
                viewModel.showAlert.value = false
            }
        }
    }
}


@Composable
fun OptionsButton(
    viewModel: MainViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dots_vertical),
            contentDescription = "Information",
            modifier = Modifier
                .size(30.dp)
                .clickable { viewModel.showOptions.value = !viewModel.showOptions.value },
            colorResource(id = R.color.black)
        )
    }
}


@Composable
fun OptionsDropdownMenu(
    viewModel: MainViewModel,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(40.dp)) {
        DropdownMenu(
            modifier = modifier,
            expanded = true,
            onDismissRequest = { viewModel.showOptions.value = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    viewModel.showOptions.value = false
                    viewModel.showHelp.value = true
                }
            ) {
                Text(text = "Help")
            }
            DropdownMenuItem(
                onClick = {
                    viewModel.showOptions.value = false
                    viewModel.showAbout.value = true
                }
            ) {
                Text(text = "About")
            }
        }
    }
}


@Composable
fun Help(viewModel: MainViewModel) {
    Surface (
        color = Color.Black.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { viewModel.showHelp.value = false }
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp, 350.dp)
                    .background(Color(0xFFFFFFFF))
                    .padding(20.dp)
                    .clickable { viewModel.showHelp.value = false }
            ) {
                Text(
                    text = "How to use",
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    color = Color(0xFF000000),
                    text = AnnotatedString(text = "1. Click Work to start work session",
                        spanStyles = listOf(
                            AnnotatedString.Range(
                                SpanStyle(fontWeight = FontWeight.Bold), start = 8, end = 13
                            ))
                    )
                )
                Text(
                    color = Color(0xFF000000),
                    text = AnnotatedString(text = "2. Click Rest to switch to rest",
                        spanStyles = listOf(
                            AnnotatedString.Range(
                                SpanStyle(fontWeight = FontWeight.Bold), start = 8, end = 13
                            ))
                    )
                )
                Text(color = Color(0xFF000000), text = "3. Follow the messages")
                Text(color = Color(0xFF000000), text = "      ○ I have to work extra...", fontStyle = FontStyle.Italic)
                Text(color = Color(0xFF000000), text = "      ○ I can work for at most...", fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.pause_circle_outline),
                        contentDescription = null,
                        modifier = Modifier,
                        colorResource(id = R.color.black)
                    )
                    Text(color = Color(0xFF000000), text = " to pause")
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.close_circle_outline),
                        contentDescription = null,
                        modifier = Modifier,
                        colorResource(id = R.color.black)
                    )
                    Text(color = Color(0xFF000000), text = " to clear the session")
                }
            }
            BackHandler {
                if (viewModel.showHelp.value) {
                    viewModel.showHelp.value = false
                }
            }
        }
    }
}


@Composable
fun About(viewModel: MainViewModel) {
    val context = LocalContext.current
    val intentGithub = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kevinesg/Pomodoro.app")) }
    val intentLinkedin = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/kevinesg/")) }
    val intentPaypal = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/paypalme/kevinesg")) }
    Surface (
        color = Color.Black.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { viewModel.showAbout.value = false }
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp, 400.dp)
                    .background(Color(0xFFFFFFFF))
                    .padding(20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "About",
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "  Pomodoro.app is a lightweight app to track productivity and rest times. It is open-source-- the complete source code can be found in the Github link below.",
                    color = Color(0xFF000000),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "  The current version is heavily inspired by Freemodoro. This is built primarily as a hobby and out of interest :)",
                    color = Color(0xFF000000),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "  It is free and ad-free, but donations are welcome.",
                    color = Color(0xFF000000),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.github_icon),
                        contentDescription = "Github icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { context.startActivity(intentGithub) },
                        colorResource(id = R.color.black)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.linkedin_icon),
                        contentDescription = "Linkedin icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { context.startActivity(intentLinkedin) }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.paypal_icon),
                        contentDescription = "Paypal icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { context.startActivity(intentPaypal) }
                    )
                }
            }
            BackHandler {
                if (viewModel.showAbout.value) {
                    viewModel.showAbout.value = false
                }
            }
        }
    }
}


@Composable
fun ConfirmExitApp(
    viewModel: MainViewModel,
    mainActivity: MainActivity
) {
    Surface (
        color = Color.Black.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { viewModel.showConfirmExitApp.value = false }
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp, 250.dp)
                    .background(Color(0xFF42414d), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Are you sure you want to exit the app?",
                        color = Color(0xFFFFFFFF),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00ddff), contentColor = Color(0xFF000000)
                        ),
                        onClick = {
                            mainActivity.finish()
                            exitProcess(0)
                        }
                    ) {
                        Text("Yes", color = Color(0xFFFFFFFF), fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2b2a33), contentColor = Color(0xFFFFFFFF)
                        ),
                        onClick = { viewModel.showConfirmExitApp.value = false }
                    ) {
                        Text("No", color = Color(0xFFFFFFFF), fontSize = 20.sp)
                    }
                }
            }
        }

        BackHandler {
            if (viewModel.showConfirmExitApp.value) {
                viewModel.showConfirmExitApp.value = false
            }
        }
    }
}