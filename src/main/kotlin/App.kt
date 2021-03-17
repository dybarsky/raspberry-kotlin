@file:JvmName("App")

import com.pi4j.io.gpio.GpioFactory
import gpio.Led
import gpio.Pot
import gpio.Pwm
import gpio.Servo
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

fun main(args: Array<String>): Unit =
    runBlocking {

        val gpio = GpioFactory.getInstance()

//      val nixie = Nixie()
//      val servo = Servo(gpio)
//      val led = Led(gpio)
        val pwm = Pwm(gpio)
        val pot = Pot()

        pot.read {
            pwm.percent(it)
        }

//      led.blinking()
//      servo.demo()
//      pwm.demo()
//      nixie.demo()
    }

private suspend fun Pot.read(callback: (Float) -> Unit) {
    while (coroutineContext.isActive) {
        val time = List(10) { read() }
            .average()
            .toFloat()
        val percent = time / Pot.MAX
        callback(percent)
    }
}

private suspend fun Led.blink() {
    delay(1000)
    while (coroutineContext.isActive) {
        if (isOn) off() else on()
        delay(1000)
    }
}

private suspend fun Pwm.demo() {
    min()
    repeat(10) {
        percent(it / 10f)
        delay(1000)
    }
//    half()
//    delay(1000)
    max()
//    delay(1000)
//    off()
}

private suspend fun Servo.demo() {
    min()
    delay(1000)
    max()
}