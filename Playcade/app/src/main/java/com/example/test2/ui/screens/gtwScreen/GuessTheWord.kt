package com.example.test2.ui.screens.gtwScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test2.R
import com.example.test2.model.Words
import com.example.test2.network.ApiClient
import kotlin.random.Random

@Composable
fun GuessTheWordApp () {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )

        val hexColor = "#F72585"
        val color = Color(android.graphics.Color.parseColor(hexColor))

        val words = remember { mutableStateOf(emptyList<Words>()) }
        val isLoading = remember { mutableStateOf(true) }
        val currentWord = remember { mutableStateOf<Words?>(null) }
        val guess = remember { mutableStateOf("") }
        val guessResult = remember { mutableStateOf("") }
        val scrambledWord = remember { mutableStateOf("") }
        val points = remember { mutableStateOf(0) }
        val isStarting = remember { mutableStateOf(false) }
        val hint = remember { mutableStateOf("") }

        LaunchedEffect(key1 = Unit) {
            try {
                val res = ApiClient.apiService.getWords()
                words.value = res
                isLoading.value = false
                currentWord.value = words.value[Random.nextInt(0, words.value.size)]
                scrambledWord.value = currentWord.value!!.words.toCharArray().apply {
                    shuffle()
                }.joinToString("")
            } catch (e: Exception) {
                Log.d("HELLO", e.message.toString());
            }

        }

        if (isStarting.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator()
                }
                else {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "Points: ${points.value}",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                    Text(
                        text = guessResult.value,
                        style= MaterialTheme.typography.h5,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(100.dp))
                    Card(modifier = Modifier
                        .width(350.dp)
                        .height(100.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = true
                        )

                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                            Text(
                                text = "Category",
                                style = MaterialTheme.typography.h5,
                                color = color,
                            )
                            Text(
                                    text = currentWord.value!!.category,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Black,
                                    fontSize = (17.sp)
                                )

                        }
                    }


                    TextField(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp)),
                        value = guess.value,
                        onValueChange = { guess.value = it },
                        singleLine = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        )

                    )

                    val chunkSize = 5
                    val chunks = scrambledWord.value.toCharArray().toList().chunked(chunkSize)


                    for (chunk in chunks) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (letter in chunk) {
                                Button(
                                    onClick = {
                                            guess.value += letter
                                    },
                                    modifier = Modifier.padding(3.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White,
                                        contentColor = color
                                    )
                                ) {
                                    Text(text = letter.toString(), fontSize = (23.sp))
                                }
                            }
                        }
                    }

                    Text(
                        text = hint.value,
                        style= MaterialTheme.typography.h6,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            guess.value = ""
                        }
                    ) {
                        Text(text = "Clear")
                    }


                    Row {
                        Button(
                            onClick = {
                                if (guess.value.equals(
                                        currentWord.value!!.words,
                                        ignoreCase = true
                                    )
                                ) {
                                    guessResult.value = "Correct!"
                                    points.value += 10
                                    guess.value = ""
                                    currentWord.value =
                                        words.value[Random.nextInt(0, words.value.size)]
                                    scrambledWord.value =
                                        currentWord.value!!.words.toCharArray().apply { shuffle() }
                                            .joinToString("")
                                }
                            }

                        ) {
                            Text(text = "Submit")
                        }


                        Button(
                            onClick = {
                                hint.value =
                                    "The first letter is: ${currentWord.value!!.words.first()}"
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = color,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "?")
                        }
                    }
                }
            }
        }
        else {
            GtwHomeScreen(onStartClicked = { isStarting.value = true })
        }
    }
}


