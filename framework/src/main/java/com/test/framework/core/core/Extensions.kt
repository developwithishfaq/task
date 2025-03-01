package com.test.framework.core.core

import android.view.View
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 2,4,5,1,3
// 5, 4, 2 ,1,3
suspend fun doAsyncOperation() {
    delay(1000)
    println("Async operation completed")//1
}
fun main() {
    runBlocking {
        launch {
            println("Coroutine is starting") //2
            doAsyncOperation()
            println("Coroutine is finishing") //3
        }

        println("Main function is not blocked") //4
    }
    println("runBlocking completed") //5
}


fun View.makeVisible(check: Boolean = true) {
    visibility = if (check) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.makeGone(check: Boolean = true) {
    visibility = if (check) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


// 5,2,4,1,3
// 4,2,1,3
