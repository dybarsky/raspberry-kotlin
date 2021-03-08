package gpio

import com.pi4j.Pi4J
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalState

private const val PIN = 23

private val pi4j = Pi4J.newAutoContext()

private val ledConfig = DigitalOutput
    .newConfigBuilder(pi4j)
    .id("led")
    .name("LED Flasher")
    .address(PIN)
    .shutdown(DigitalState.LOW)
    .initial(DigitalState.LOW)
.provider("pigpio-digital-output")

private val led = pi4j.create(ledConfig)

fun ledOn() {
    led.high()
}

fun ledOff() {
    led.low()
}

val ledOn: Boolean
    get() = led.equals(DigitalState.HIGH)