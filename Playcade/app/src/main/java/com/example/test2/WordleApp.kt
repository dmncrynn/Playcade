package com.example.test2

//import com.example.test2.ui.screens.wordleScreen.NavBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.test2.ui.screens.wordleScreen.Body
import com.example.test2.ui.screens.wordleScreen.NavBar
import com.example.test2.ui.screens.wordleScreen.getNewWord
import com.example.test2.ui.screens.wordleScreen.getWords

class WordleActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    NavBar(onBackClick = {onBackPressed()})
                },
                content = {
                    Main()
                }
            )
        }
    }
}

@Composable
fun Main() {
    val w = getWords()
    Column(horizontalAlignment = Alignment.Start) {
        Body(getNewWord(w), w)
    }
}

