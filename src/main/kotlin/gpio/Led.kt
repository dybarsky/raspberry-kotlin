package gpio

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.PinState.HIGH
import com.pi4j.io.gpio.PinState.LOW
import com.pi4j.io.gpio.RaspiPin

class Led(gpio: GpioController) {

    companion object {
        // PIN 16
        private val PIN = RaspiPin.GPIO_04
    }

    private val pin = gpio
            .provisionDigitalOutputPin(PIN, "LED", HIGH)
            .apply {
                setShutdownOptions(true, LOW)
            }

    fun on() = pin.high()

    fun off() = pin.low()

    val isOn: Boolean
        get() = pin.isHigh
}