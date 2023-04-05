package com.example.test2.ui.screens.snakeScreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.lifecycleScope
import com.example.test2.R
import java.util.*


class SnakeActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val game = GameX(lifecycleScope)
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "") },
                        backgroundColor = colorResource(id = R.color.background),
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = colorResource(id = R.color.background),
                        ) {
                            Snake(game)
                        }
                    }
                }
            )
        }
    }
}

