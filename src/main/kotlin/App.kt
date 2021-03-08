@file:JvmName("App")

import gpio.ledOff
import gpio.ledOn
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit =
    runBlocking {

        while (true) {
            if (ledOn) ledOff() else ledOn()
            delay(1000)
        }
    }
