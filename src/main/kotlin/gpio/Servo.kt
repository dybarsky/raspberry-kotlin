package gpio

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.wiringpi.Gpio

class Servo(gpio: GpioController) {

    companion object {

    }

    private val pwm = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26)

    init {
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS)
        Gpio.pwmSetClock(1920)
        Gpio.pwmSetRange(200)
    }

    fun min() {
        pwm.pwm = 5
    }

    fun max()  {
        pwm.pwm = 20
    }
}