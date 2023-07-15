package com.example.runtracker.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.runtracker.R
import com.example.runtracker.navigation.Screen
import com.example.runtracker.ui.theme.OrangeSecondaryColor

@Composable
fun Menu(
    navController: NavController,
    currentScreen: String
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .alpha(.45f)
                .background(OrangeSecondaryColor)

        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            MenuElement(
                destination = Screen.Home,
                text = "Home",
                icon = R.drawable.ic_home,
                navController = navController,
                modifier = Modifier
                    .weight(1f),
                activeScreen = currentScreen
            )
            MenuElement(
                destination = Screen.LatestWorkouts,
                text = "Workouts",
                icon = R.drawable.ic_run,
                navController = navController,
                modifier = Modifier
                    .weight(1f),
                activeScreen = currentScreen
            )
            MenuElement(
                destination = Screen.Workout,
                text = "Start",
                icon = R.drawable.ic_start,
                navController = navController,
                modifier = Modifier
                    .weight(1f),
                activeScreen = currentScreen
            )
            MenuElement(
                destination = Screen.Profile,
                text = "Profile",
                icon = R.drawable.ic_profile,
                navController = navController,
                modifier = Modifier
                    .weight(1f),
                activeScreen = currentScreen
            )
            MenuElement(
                destination = Screen.Settings,
                text = "Settings",
                icon = R.drawable.ic_settings,
                navController = navController,
                modifier = Modifier
                    .weight(1f),
                activeScreen = currentScreen
            )
        }
    }
}

@Composable
fun MenuElement(
    destination: Screen,
    text: String,
    icon: Int,
    navController: NavController,
    modifier: Modifier,
    activeScreen: String
) {

    val colorFilter = if (text == activeScreen) {
        ColorFilter.tint(OrangeSecondaryColor)
    } else {
        null
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                navController.navigate(destination.route)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            colorFilter = colorFilter
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text,
            color = if (text == activeScreen) OrangeSecondaryColor else Color.Black
        )
    }
}
