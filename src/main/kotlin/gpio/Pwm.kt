package gpio

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.RaspiPin

class Pwm(gpio: GpioController) {

    companion object {
        private val pin = RaspiPin.GPIO_26
    }

    private val pwm = gpio
            .provisionPwmOutputPin(pin)
            .apply {
                setPwmRange(1000)
            }


    fun off() {
        pwm.pwm = 0
    }

    fun min() {
        pwm.pwm = 100
    }

    fun half() {
        pwm.pwm = 500
    }

    fun max()  {
        pwm.pwm = 1000
    }
}