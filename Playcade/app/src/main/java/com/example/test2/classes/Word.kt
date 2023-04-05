package com.example.test2.classes
import com.example.test2.R

class Word {
    private var letter: String = ""
    private var backgroundColor: Int = R.color.dark_background
    init {
        this.letter = ""
        this.backgroundColor = R.color.dark_background
    }
    fun setLetter(letter: String) {
        this.letter = letter
    }
    fun getLetter(): String {
        return this.letter
    }
    fun setBackground(background: Int) {
        this.backgroundColor = background
    }
    fun getBackground(): Int {
        return this.backgroundColor
    }
}
