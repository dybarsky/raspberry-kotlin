@file:JvmName("App")

import com.pi4j.io.gpio.GpioFactory
import gpio.Led
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit =
    runBlocking {

        val gpio = GpioFactory.getInstance()
        val led = Led(gpio)

        while (true) {
            with (led) {
                if (isOn) off() else on()
            }
            delay(1000)
        }
    }
