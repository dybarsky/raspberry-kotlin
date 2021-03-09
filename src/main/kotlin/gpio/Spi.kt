package gpio

import com.pi4j.wiringpi.Gpio
import kotlinx.coroutines.delay
import kotlin.random.Random


class Spi {

    companion object {
        private const val DATA = 0
        private const val LATCH = 2
        private const val CLOCK = 3

        private val DIGITS = arrayOf(
                0xDF, 0x06, 0xEB, 0x6F, 0x37, 0x7D, 0xFD, 0x0E, 0xFE, 0x7E
        )
    }

    init {
        Gpio.wiringPiSetup()
        Gpio.pinMode(DATA, Gpio.OUTPUT)
        Gpio.pinMode(LATCH, Gpio.OUTPUT)
        Gpio.pinMode(CLOCK, Gpio.OUTPUT)
        Gpio.digitalWrite(DATA, 0)
        Gpio.digitalWrite(LATCH, 0)
        Gpio.digitalWrite(CLOCK, 0)
    }

    private suspend fun countDown() {
        DIGITS
            .reversed()
            .forEach {
                println(it)
                write(it)
                delay(1_000)
            }
    }

    private fun Random.generate(size: Int): List<Int> {
        val list = mutableListOf<Int>()
        repeat(size) {
            var bit = -1
            while (bit < 1 || list.contains(bit)) {
                bit = nextInt(1, 7)
            }
            list.add(bit)
        }
        return list.toList()
    }

    private suspend fun random() {
        val random = Random(System.currentTimeMillis())
        while (true) {
            val (a, b, c) = random.generate(3)
            val data = (1 shl a) or (1 shl b) or (1 shl c)
            write(data)
            delay(500)
        }
    }

    suspend fun demo() {
        countDown()
        random()
    }

    private suspend fun write(byte: Int) {
        for (bit in 0 until 8) {
            val output = 0x80 and (byte shl bit)
            Gpio.digitalWrite(DATA, output)
            Gpio.digitalWrite(CLOCK, Gpio.HIGH)
            delay(10)
            Gpio.digitalWrite(CLOCK, Gpio.LOW)
        }
        Gpio.digitalWrite(LATCH, Gpio.HIGH)
        delay(10)
        Gpio.digitalWrite(LATCH, Gpio.LOW)
    }
}