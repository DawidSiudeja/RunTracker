package com.example.runtracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.R
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.presentation.workout_item.WorkoutItem
import com.example.runtracker.ui.theme.textColor

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val userInfoData = viewModel.getUserInfoData().collectAsState(listOf(UserInfo())).value[0]
    val workoutsData = viewModel.getAllWorkouts().collectAsState(listOf(Workout())).value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greetings(nick = userInfoData.nickname)

            Spacer(modifier = Modifier.height(40.dp))

            Column {
                SectionTitle(title = "Monthly goals activities")
                MonthlyGoals(
                    userInfo = userInfoData,
                    workouts = workoutsData
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Column {
                SectionTitle(title = "Your last workout")
                WorkoutItem(workout = workoutsData[workoutsData.size - 1])
            }


        }
        Menu(
            navController = navController,
            currentScreen = "Home"
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


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(900),
            color = textColor,
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
}