package com.example.runtracker.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.ui.theme.OrangeSecondaryColor

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {



    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        SetUserWeight(viewModel)
        Menu(
            navController = navController,
            currentScreen = "Profile"
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUserWeight(
    viewModel: ProfileViewModel
) {
    var textStateWeight by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textStateWeight,
            onValueChange = {
                textStateWeight = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = OrangeSecondaryColor,
                textColor = Color.White,
            ),
            label = { Text("Enter your weight in kg") },
            modifier = Modifier
                .fillMaxWidth(.8f)
        )
        Button(
            onClick = { viewModel.setUserWeight(textStateWeight.toInt()) },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
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