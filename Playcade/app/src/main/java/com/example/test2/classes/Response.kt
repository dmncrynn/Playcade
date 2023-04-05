package com.example.test2.classes

class Response {
    private var message = ""
    private var ok = false
    init {
        this.message = ""
        this.ok = false
    }
    fun setMessage(str: String) {
        this.message = str
    }
    fun getMessage(): String {
        return this.message
    }
    fun setOk(stat: Boolean) {
        this.ok = stat
    }
    fun getOk(): Boolean {
        return this.ok
    }
}