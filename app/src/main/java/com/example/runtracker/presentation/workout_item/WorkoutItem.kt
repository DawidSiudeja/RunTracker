package com.example.runtracker.presentation.workout_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.example.runtracker.ui.theme.OrangeSecondaryColorAspect
import java.time.LocalDateTime

@Composable
fun WorkoutItem(workout: Workout) {

    val monthNumber = workout.date.substring(5,7)
    val month = numberMonthToName(monthNumber)

    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                            "${workout.distance.toInt() / 1000} ${workout.distance.toInt() % 1000}"
                        } else {
                            "0.${workout.distance.toInt()}"
                        },
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "Kilometers",
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

fun numberMonthToName(monthNumber: String): String {
    return when(monthNumber) {
        "01" -> "jan"
        "02" -> "feb"
        "03" -> "mar"
        "04" -> "apr"
        "05" -> "mai"
        "06" -> "jun"
        "07" -> "jul"
        "08" -> "aug"
        "09" -> "sep"
        "10" -> "oct"
        "11" -> "nov"
        "12" -> "dec"
        else -> { "" }
    }
}


@Composable
@Preview
fun WorkoutItemPreview() {
    WorkoutItem(
        Workout(
            distance = 13100.559,
            kcal = 750.00,
            date = LocalDateTime.now().toString(),
            time = 3900
        )
    )
}