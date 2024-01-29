package com.example.runtracker.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.R
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.presentation.components.SectionTitle
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.presentation.components.WorkoutItem
import com.example.runtracker.ui.theme.backgroundColor
import com.example.runtracker.ui.theme.textColor

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val userInfoList = viewModel.getUserInfoData().collectAsState(listOf(UserInfo())).value
    val workoutsData = viewModel.getAllWorkouts().collectAsState(listOf(Workout())).value

    var userInfoData = UserInfo()

    if (userInfoList.isNotEmpty()) {
        userInfoData = userInfoList[0]
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Greetings(nick = userInfoData.nickname)

            Spacer(modifier = Modifier.height(30.dp))

            Column {
                SectionTitle(title = "Monthly goals activities")
                MonthlyGoals(
                    userInfo = userInfoData,
                    workouts = workoutsData
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Column {
                SectionTitle(title = "Your last workout")
                if (workoutsData.size != 0) {
                    WorkoutItem(workout = workoutsData[workoutsData.size - 1], navController)
                } else {
                    Text(
                        text = "No workouts yet",
                        color = textColor,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            /*
            Column {
                SectionTitle(title = "Daily steps goal")
            }
             */
        }

        Menu(
            navController = navController,
            currentScreen = "Home",
        )
    }


}


@Composable
fun Greetings(nick: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Hi, $nick!".uppercase(),
            color = textColor,
            fontSize = 30.sp,
            style = TextStyle(
                fontWeight = FontWeight.Black
            )
        )
        Icon(painter = painterResource(id = R.drawable.ic_notification), contentDescription = "notification")
    }

}


