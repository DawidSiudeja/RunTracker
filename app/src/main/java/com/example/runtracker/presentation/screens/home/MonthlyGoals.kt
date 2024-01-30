package com.example.runtracker.presentation.screens.home

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.runtracker.R
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.example.runtracker.ui.theme.OrangeSecondaryColorAspect
import com.example.runtracker.ui.theme.lightBlack
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun MonthlyGoals(
    userInfo: UserInfo,
    workouts: List<Workout>
) {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
    val currentDateAsString = currentDate.format(formatter)

    var distance = 0.0  // Assuming distance is of type Double
    var workoutsAmounts = 0

    for (x in workouts) {
        if (x.date.substring(0, 7) == currentDateAsString) {
            workoutsAmounts += 1
            distance += x.distance
        }
    }
    distance /= 1000

    var distanceIsFinished = false
    if (userInfo.kilometersGoal <= distance) {
        distanceIsFinished = true
    }

    val distanceCurrentProgress: Float = distance.toFloat() / userInfo.kilometersGoal.toFloat()

    var workoutsIsFinished = false
    if (userInfo.workoutsGoal <= workoutsAmounts) {
        workoutsIsFinished = true
    }

    val workoutsCurrentProgress: Float = workoutsAmounts.toFloat() / userInfo.workoutsGoal.toFloat()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MonthlyGoalsItem(
            userInfo = userInfo,
            isFinished = distanceIsFinished,
            currentProgress = distanceCurrentProgress,
            progress = distance.roundToInt(),
            progressGoal = userInfo.kilometersGoal,
            modifier = Modifier.
                padding(end = 5.dp),
            width = .49f,
            type = "Kilometers"

        )
        MonthlyGoalsItem(
            userInfo = userInfo,
            isFinished = workoutsIsFinished,
            currentProgress = workoutsCurrentProgress,
            progress = workoutsAmounts,
            progressGoal = userInfo.workoutsGoal,
            width = .95f,
            modifier = Modifier.
                padding(start = 5.dp),
            type = "Workouts"
        )
    }
}

@Composable
fun MonthlyGoalsItem(
    userInfo: UserInfo,
    isFinished: Boolean,
    currentProgress: Float,
    progress: Int,
    progressGoal: Int,
    width: Float,
    modifier: Modifier,
    type: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(OrangeSecondaryColor)
            .fillMaxWidth(width)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(OrangeSecondaryColorAspect),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth((
                                if(currentProgress <= 1f) {
                                    currentProgress + .05f
                                } else {
                                    1f
                                }
                            ))
                        .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                        .background(lightBlack)
                        .padding(end = 10.dp),
                    horizontalArrangement = (if(isFinished) {
                        Arrangement.Center
                    } else {
                        Arrangement.End
                    }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(isFinished) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = "goal finished",
                            tint = Color.White
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_run),
                            contentDescription = "active goal",
                            tint = Color.White
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = progress.toString(),
                    fontSize = 30.sp,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Black
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = progressGoal.toString(),
                    fontSize = 30.sp,
                    color = OrangeSecondaryColorAspect,
                    style = TextStyle(
                        fontWeight = FontWeight.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = type,
                    fontSize = 14.sp,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }


    }
}

@Composable
@Preview
fun MonthlyGoalsItemPreview() {
    MonthlyGoals(
        userInfo = UserInfo(),
        workouts = listOf(
            Workout(),
            Workout()
        )
    )
}