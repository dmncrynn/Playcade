package com.example.test2.ui.screens.quizTriviaScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test2.R
import com.example.test2.model.Trivia
import com.example.test2.network.ApiClient1
import com.example.test2.ui.screens.gtwScreen.QuizTriviaHomeScreen
import kotlin.random.Random


@Composable
fun TriviaGame() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.qt_color)),
        contentAlignment = Alignment.Center
    ) {


        val trivia = remember { mutableStateOf(emptyList<Trivia>()) }
        val isLoading = remember { mutableStateOf(true) }
        val currentTrivia = remember { mutableStateOf<Trivia?>(null) }
        val guess = remember { mutableStateOf("") }
        val answer = remember { mutableStateOf("") }
        val result = remember { mutableStateOf("") }
        val score = remember { mutableStateOf(0) }
        val level = remember { mutableStateOf(1) }
        val isStarting = remember { mutableStateOf(false) }
        val options = remember { mutableStateOf(mutableListOf<String>()) }



        LaunchedEffect(key1 = Unit) {
            val triviaResponse = ApiClient1.apiService.getTrivia()
            val triviaList = triviaResponse.results
            trivia.value = triviaList
            isLoading.value = false
            currentTrivia.value = trivia.value[Random.nextInt(0, trivia.value.size)]
            options.value = currentTrivia.value!!.incorrectAnswers.toMutableList()
            options.value.add(currentTrivia.value!!.correctAnswer)
            options.value.shuffle()
            answer.value = currentTrivia.value!!.correctAnswer
        }

        if (isStarting.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator()
                } else {

                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "Score: ${score.value}",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Spacer(Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Category",
                                style = MaterialTheme.typography.h5,
                                color = colorResource(id = R.color.qt_button),
                            )
                            Text(
                                text = currentTrivia.value!!.category,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                fontSize = 17.sp
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = true
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Question",
                                style = MaterialTheme.typography.h5,
                                color = colorResource(id = R.color.qt_button),
                            )
                            Text(
                                text = currentTrivia.value!!.question,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                fontSize = 17.sp
                            )
                        }
                    }
                    Text(
                        text = result.value,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    var selectedOption = remember { mutableStateOf("")}

                    for (option in options.value) {
                        var isSelected = option == selectedOption.value
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    selectedOption.value = option
                                    guess.value = option

                                },
                                modifier = Modifier.padding(3.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Cyan else Color.White,
                                    contentColor = colorResource(id = R.color.qt_button)
                                )
                            ) {
                                Text(text = option, fontSize = (23.sp))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                    ) {
                        Button(
                            modifier = Modifier
                                .width(223.dp)
                                .height(65.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .shadow(elevation = 40.dp)
                            ,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.qt_button),
                                contentColor = Color.White
                            ),
                            onClick = {
                                if (guess.value.equals(
                                        currentTrivia.value!!.correctAnswer,
                                        ignoreCase = true
                                    )
                                ) {
                                    result.value = "Correct!"
                                    score.value += 1
                                    guess.value = ""
                                    currentTrivia.value =
                                        trivia.value[Random.nextInt(0, trivia.value.size)]
                                    options.value =
                                        currentTrivia.value!!.incorrectAnswers.toMutableList()
                                    options.value.add(currentTrivia.value!!.correctAnswer)
                                    options.value.shuffle()
                                } else {
                                    result.value = "incorrect Try Again!"
                                    guess.value = ""
                                }
                            }

                        ) {
                            Text(text = "Submit")
                        }

                    }
                }
            }
        }
        else {
            QuizTriviaHomeScreen(onStartClicked = { isStarting.value = true })
        }
    }
}