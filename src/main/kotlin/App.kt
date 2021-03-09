@file:JvmName("App")

import gpio.Spi
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit =
    runBlocking {

//        val gpio = GpioFactory.getInstance()
//        val led = Led(gpio)
//        led.on()
//        delay(1000)

            Spi().demo()



//        delay(10_000)

//        while (true) {
//            with (led) {
//                if (isOn) off() else on()
//            }
//            delay(1000)
//        }
    }
