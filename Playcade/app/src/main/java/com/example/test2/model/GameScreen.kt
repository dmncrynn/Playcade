package com.example.test2.model

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color

data class GameScreen(val name: String, val activityClass: Class<out ComponentActivity>, val color: Color)
