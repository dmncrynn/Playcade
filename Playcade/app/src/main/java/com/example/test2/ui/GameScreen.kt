package com.example.test2.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test2.*
import com.example.test2.R
import com.example.test2.model.GameScreen
import com.example.test2.ui.screens.gtwScreen.GuessTheWordActivity
import com.example.test2.ui.screens.quizTriviaScreen.TriviaQuizActivity
import com.example.test2.ui.screens.snakeScreen.SnakeActivity
import com.example.test2.ui.screens.spaceInvadersScreen.StartUp
import com.example.test2.ui.screens.ticTacToeScreen.TicTacToeActivity


@Composable
fun GameListScreen() {
    val games = listOf(
        GameScreen("Space Invader", StartUp::class.java,Color(0xFF6444BF)),
        GameScreen("Wordle", WordleActivity::class.java,Color(0xFF335AFF)),
        GameScreen("Guess The Word", GuessTheWordActivity::class.java,  Color(0xFFD14B30)),
        GameScreen("TicTacToe", TicTacToeActivity::class.java,  Color(0xFFDEBF5D)),
        GameScreen("Snake", SnakeActivity::class.java,  Color(0xFFB6DC6B)),
        GameScreen("TriviaQuiz", TriviaQuizActivity::class.java, Color(0xFF6DA2E9))
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.nav_bg),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )
        LazyColumn(
            modifier = Modifier.padding(top = 120.dp)
        ){
            items(games) { game ->
                GameListItem(game = game)
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun GameListItem(game: GameScreen) {
    val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, game.activityClass)
                    context.startActivity(intent)
                }
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(17.dp))
                        .alpha(0.9f)
                        .background(color = game.color)
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
}
