package com.example.runtracker.presentation.screens.profile

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.presentation.components.SectionTitle
import com.example.runtracker.presentation.components.Title
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.ui.theme.LightGreyColor
import com.example.runtracker.ui.theme.backgroundColor
import com.example.runtracker.ui.theme.lightBlack
import kotlinx.coroutines.flow.first


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val userInfo = viewModel.getUserInfo().collectAsState(listOf(UserInfo())).value[0]

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Row {
                Title(title = "Profile")
            }
            Spacer(modifier = Modifier.height(40.dp))
            SetUserData(viewModel = viewModel, userInfo = userInfo, context)
        }

        Menu(
            navController = navController,
            currentScreen = "Profile"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUserData(
    viewModel: ProfileViewModel,
    userInfo: UserInfo,
    context: Context
) {

    var textStateWeight by remember { mutableStateOf(userInfo.userWeight.toString()) }
    var textStateName by remember { mutableStateOf(userInfo.nickname) }
    var textStateAge by remember { mutableStateOf(userInfo.age.toString()) }

    var textStateWorkouts by remember { mutableStateOf(userInfo.workoutsGoal.toString()) }
    var textStateKilometers by remember { mutableStateOf(userInfo.kilometersGoal.toString()) }

    LaunchedEffect(userInfo) {
        textStateName = userInfo.nickname
        textStateWeight =userInfo.userWeight.toString()
        textStateAge = userInfo.age.toString()
        textStateWorkouts = userInfo.workoutsGoal.toString()
        textStateKilometers =userInfo.kilometersGoal.toString()
    }

    SectionTitle(title = "Personal info")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        TextField(
            maxLines = 1,
            value = textStateName,
            onValueChange = {
                textStateName = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text(
                    text = "Your nick",
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        TextField(
            maxLines = 1,
            value = textStateWeight,
            onValueChange = {
                textStateWeight = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text(
                    text = "Weight [KG]",
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth(.5f)
        )
        TextField(
            maxLines = 1,
            value = textStateAge,
            onValueChange = {
                textStateAge = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text(
                    text = "Age",
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    SectionTitle(title = "Monthly goals activities")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        TextField(
            maxLines = 1,
            value = textStateWorkouts,
            onValueChange = {
                textStateWorkouts = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text(
                    text = "Workouts",
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        TextField(
            maxLines = 1,
            value = textStateKilometers,
            onValueChange = {
                textStateKilometers = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightGreyColor
            ),
            label = {
                Text(
                    text = "Kilometers",
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.saveUserData(context, textStateName, textStateAge, textStateWeight, textStateWorkouts, textStateKilometers)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = lightBlack,
                contentColor = LightGreyColor
            )
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp
            )
        }
    }
    
}


@Preview
@Composable
fun SetUserWeightPreview() {
    //SetUserWeight()
}