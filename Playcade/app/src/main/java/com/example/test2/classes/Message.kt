package com.example.test2.classes

import com.example.test2.R

class Message {
    private var message: String = "Have Fun!"
    private var color: Int = R.color.teal_200
    init {
        this.message = "Have Fun!"
        this.color = R.color.teal_200
    }
    fun setMessage(str: String) {
        this.message = str
    }
    fun getMessage(): String {
        return this.message
    }
    fun setColor(int: Int) {
        this.color = int
    }
    fun getColor(): Int {
        return this.color
    }
}