package gpio

import com.pi4j.wiringpi.Gpio
import kotlinx.coroutines.delay

class Pot {

    companion object {
        private const val PIN_DISCHARGE = 28    // PIN 38
        private const val PIN_CHARGE = 29       // PIN 40
        const val MAX = 50000
    }

    init {
        Gpio.wiringPiSetup()

    }

    suspend fun read(): Int {
        discharge()
        return chargeTime()
    }

    private suspend fun discharge() {
        Gpio.pinMode(PIN_CHARGE, Gpio.INPUT)
        Gpio.pinMode(PIN_DISCHARGE, Gpio.OUTPUT)
        Gpio.digitalWrite(PIN_DISCHARGE, false)
        delay(5)
    }

    private fun chargeTime(): Int {
        Gpio.pinMode(PIN_CHARGE, Gpio.OUTPUT)
        Gpio.pinMode(PIN_DISCHARGE, Gpio.INPUT)
        var count = 0
        Gpio.digitalWrite(PIN_CHARGE, true)
        while (Gpio.digitalRead(PIN_DISCHARGE) != 1) {
            count ++
        }
        return count
    }
}