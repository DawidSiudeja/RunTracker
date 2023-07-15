package com.example.runtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.runtracker.navigation.SetupNavigation
import com.example.runtracker.ui.theme.RunTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunTrackerTheme {
                SetupNavigation()
            }
        }
    }
}

