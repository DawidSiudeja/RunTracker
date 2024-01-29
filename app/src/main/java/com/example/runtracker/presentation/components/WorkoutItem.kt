package com.example.runtracker.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.navigation.Screen
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.example.runtracker.ui.theme.OrangeSecondaryColorAspect
import com.example.runtracker.ui.theme.lightBlack

@Composable
fun WorkoutItem(workout: Workout, navController: NavController) {

    val monthNumber = workout.date.substring(5,7)
    val month = numberMonthToName(monthNumber)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(OrangeSecondaryColor)
            .clickable {
                navigate(
                    navController = navController,
                    destination = Screen.EndedWorkout,
                    arguments = listOf(workout.id)
                )
            }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 0.dp)
            ) {
                Text(
                    text = workout.date.substring(0, 4),
                    style = TextStyle(
                        color = OrangeSecondaryColorAspect,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 7.5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = workout.date.substring(8, 10) + " " + month,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Date",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text =
                        if(workout.time >= 3600) {
                            "${(workout.time / 3600)} h ${(workout.time % 3600 / 60)} min"
                        } else if (workout.time > 60) {
                            "${(workout.time / 60)} min ${(workout.time % 60)} s"
                        } else {
                            "${workout.time} s"
                        },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Time",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 7.5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = workout.kcal.toInt().toString(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Calories burned",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text =
                        if(workout.distance > 1000) {
                            "${workout.distance.toInt() / 1000}.${workout.distance.toInt() % 1000}"
                        } else {
                            "${workout.distance.toInt()}"
                        },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text =
                            if(workout.distance > 1000) {
                                "Kilometers"
                            } else {
                                "Meters"
                            },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(lightBlack),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Details",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                )
            }
        }
    }
}

fun numberMonthToName(monthNumber: String): String {
    return when(monthNumber) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "Mai"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Aug"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> { "" }
    }
}


fun navigate(navController: NavController, destination: Screen, arguments: List<Any>? = null) {

    var destinationString = destination.route

    if (arguments != null) {
        for (element in arguments) {
            destinationString += "/$element"
        }
    }
    navController.navigate(destinationString)
}



@Composable
fun WorkoutItemDetails(workout: Workout) {

    val monthNumber = workout.date.substring(5,7)
    val month = numberMonthToName(monthNumber)

    var minutesPerKm = workout.minutesPerKm

    val minutes = minutesPerKm.toInt()
    val seconds = ((minutesPerKm - minutes) * 60).toInt()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(OrangeSecondaryColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 0.dp)
            ) {
                Text(
                    text = workout.date.substring(0, 4),
                    style = TextStyle(
                        color = OrangeSecondaryColorAspect,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = workout.date.substring(8, 10) + " " + month,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Date",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text =
                        if(workout.time >= 3600) {
                            "${(workout.time / 3600)} h ${(workout.time % 3600 / 60)} min"
                        } else if (workout.time > 60) {
                            "${(workout.time / 60)} min ${(workout.time % 60)} s"
                        } else {
                            "${workout.time} s"
                        },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Time",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 7.5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = workout.kcal.toInt().toString(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Calories burned",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text =
                        if(workout.distance > 1000) {
                            "${workout.distance.toInt() / 1000},${workout.distance.toInt() % 1000}"
                        } else {
                            "${workout.distance.toInt()}"
                        },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text =
                            if(workout.distance > 1000) {
                                "Kilometers"
                            } else {
                                "Meters"
                            },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 7.5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "$minutes' $seconds''",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Minutes per km",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = workout.avgSpeed.toString(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Avg speed [Km/H]",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

