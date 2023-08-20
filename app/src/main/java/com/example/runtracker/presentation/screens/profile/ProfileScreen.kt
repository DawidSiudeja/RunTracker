package com.example.runtracker.presentation.screens.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.ui.theme.LightGreyColor
import com.example.runtracker.ui.theme.OrangeSecondaryColor

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val userInfo = viewModel.getUserWeight().collectAsState(listOf()).value
    var userWeight = -1

    if (userInfo.isNotEmpty()) {
       userWeight = userInfo[userInfo.size - 1].userWeight
    }
    Log.d("ProfileScreen", "test: ${userInfo.size}")

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "Profile Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(20.dp)
            )

            SetUserWeight(viewModel = viewModel, userWeight = userWeight)
        }

        Menu(
            navController = navController,
            currentScreen = "Profile"
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUserWeight(
    viewModel: ProfileViewModel,
    userWeight: Int
) {
    var textStateWeight by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = textStateWeight,
            onValueChange = {
                textStateWeight = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text("Current weight: $userWeight")
            },
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(60.dp)
                .background(LightGreyColor, shape = RectangleShape)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Button(
            onClick = {
                viewModel.setUserWeight(textStateWeight.toInt())
            },
            modifier = Modifier
                .height(60.dp)
                .width(80.dp)
                .background(OrangeSecondaryColor, shape = RectangleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangeSecondaryColor
            )
        ) {
            Text(
                text = "Save",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    
}


@Preview
@Composable
fun SetUserWeightPreview() {
    //SetUserWeight()
}