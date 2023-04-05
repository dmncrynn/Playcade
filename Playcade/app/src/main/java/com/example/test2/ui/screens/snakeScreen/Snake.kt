package com.example.test2.ui.screens.snakeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.test2.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import com.example.test2.R

@Composable
fun Snake(game: GameX) {
        val state = game.state.collectAsState(initial = null)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(50.dp))
            Row {
                Image(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "Background Image",
                )
                Text(
                    text = " ${state.value?.snake?.size?.minus(4)}",
                    style = MaterialTheme.typography.h5,
                    color = Color.White.copy(0.8f)
                )
            }

            state.value?.let {
                Board(it)
            }
            Buttons {
                game.move = it
            }
        }
}

class GameX(private val scope: CoroutineScope) {
        private val mutex = Mutex()
        private val mutableState: MutableStateFlow<State> = MutableStateFlow(State(food = Pair(5, 5), snake = listOf(Pair(7,7))))
        val state: Flow<State> = mutableState
        var move: Pair<Int, Int> = Pair(1, 0)
            set(value) {
                scope.launch {
                    mutex.withLock {
                        if (field.first != -value.first && field.second != -value.second) {
                            field = value
                        }
                    }
                }
            }

        init {
            scope.launch {
                var snakeLength = 4

                while (true) {
                    delay(150)
                    mutableState.update {
                        val newPosition:Pair<Int, Int> = it.snake.first().let {poz : Pair<Int, Int> ->
                            mutex.withLock {
                                Pair(
                                    (poz.first+move.first+BOARD_SIZE) % BOARD_SIZE,
                                    (poz.second+move.second+BOARD_SIZE) % BOARD_SIZE
                                )
                            }
                        }

                        if(newPosition == it.food) {
                            snakeLength++
                        }

                        if(it.snake.contains(newPosition)) {
                            snakeLength = 4

                        }

                        it.copy(
                            food = if(newPosition == it.food) Pair(
                                Random().nextInt(BOARD_SIZE),
                                Random().nextInt(BOARD_SIZE)
                            ) else it.food,
                            snake = listOf(newPosition) + it.snake.take(snakeLength - 1)
                        )
                    }
                }
            }
        }

        companion object {
            const val BOARD_SIZE = 16
        }
    }


    @Composable
    fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
        val buttonSize = Modifier.size(64.dp)

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Button(onClick = { onDirectionChange(Pair(0, -1)) }, modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.green),
                    contentColor = Color.White
                )) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
            Row {
                Button(onClick = { onDirectionChange(Pair(-1, 0)) }, modifier = buttonSize,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.green),
                        contentColor = Color.White
                    )) {
                    Icon(Icons.Default.KeyboardArrowLeft, null)
                }
                Spacer(modifier = buttonSize)
                Button(onClick = { onDirectionChange(Pair(1, 0)) }, modifier = buttonSize,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.green),
                        contentColor = Color.White
                    )) {
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }
            Button(onClick = { onDirectionChange(Pair(0, 1)) }, modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.green),
                    contentColor = Color.White
                )) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }

    @Composable
    fun Board(state: State) {
        BoxWithConstraints(Modifier.padding(16.dp)) {
            val tileSize: Dp = maxWidth / GameX.BOARD_SIZE

            Box(
                Modifier.size(maxWidth)
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.snake_bg),
                    contentDescription = "Background",
                    contentScale = ContentScale.FillBounds
                )
            }


            Box(
                Modifier
                    .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                    .size(tileSize)
                    .clip(RoundedCornerShape(50.dp))
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "Apple",
                    contentScale = ContentScale.FillBounds
                )


            }

            state.snake.forEachIndexed { index, it ->
                if(it === state.snake.first()) {
                    Box(modifier = Modifier
                        .offset(x = tileSize * it.first, y = tileSize * it.second)
                        .size(tileSize)
                        .rotate(getRotationAngle(index, state.snake))
                    ){
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(-90f),
                            painter = painterResource(id = R.drawable.snake_head),
                            contentDescription = "head",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .offset(x = tileSize * it.first, y = tileSize * it.second)
                            .size(tileSize)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.snake_body),
                            contentDescription = "body",
                            contentScale = ContentScale.FillBounds
                        )

                    }

                }
            }
        }
    }

    private fun getRotationAngle(index: Int, snake: List<Pair<Int, Int>>): Float {
        val head = snake[0]
        val next = if (snake.size > 1) snake[1] else head
        return when {
            index != 0 -> 0f // only rotate the head
            next.first > head.first -> 180f
            next.first < head.first -> 3600f
            next.second > head.second -> 270f
            else -> 90f
        }
    }



