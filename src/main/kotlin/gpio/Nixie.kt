package gpio

import com.pi4j.wiringpi.Gpio
import kotlinx.coroutines.delay
import kotlin.random.Random


class Nixie {

    companion object {

        private const val DELAY_LONG = 1000L
        private const val DELAY_SHORT = 500L
        private const val DELAY_MICRO = 250L
        private const val DELAY_NANO = 100L
        private const val DELAY_SPI = 10L

        private const val DATA = 0
        private const val LATCH = 2
        private const val CLOCK = 3

        private const val B = 0b00000010
        private const val C = 0b00000100
        private const val D = 0b00001000
        private const val E = 0b00010000
        private const val F = 0b00100000
        private const val G = 0b01000000
        private const val H = 0b10000000

        private val DIGITS = arrayOf(
                D or E or C or G or B or H,
                B or C,
                D or B or F or H or G,
                D or B or F or C or G,
                E or B or F or C,
                D or E or F or C or G,
                D or E or F or C or G or H,
                D or B or C,
                D or E or F or C or G or B or H,
                D or E or F or C or G or B
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

    suspend fun demo() {
        while (true) {
            countDown()
//            segments()
            circleFilling(repeat = 5)
            circleRunning(repeat = 5)
            linesHorizontalSwap(repeat = 10)
            linesHorizontalBlink(repeat = 10)
            random(repeat = 20)
        }
    }

    private suspend fun countDown(repeat: Int = 1) {
        repeat(repeat) {
            DIGITS
                .reversed()
                .forEach {
                    write(it)
                    delay(DELAY_LONG)
                }
        }
    }

    private suspend fun segments(repeat: Int = 1) {
        val array = arrayOf(
                B,
                B or C,
                B or C or G,
                B or C or G or H,
                B or C or G or H or E,
                B or C or G or H or E or D,
                B or C or G or H or E or D or F,
        )
        repeat(repeat) {
            array.forEach {
                write(it)
                delay(DELAY_NANO)
            }
        }
    }

    private suspend fun circleRunning(repeat: Int = 1) {
        val array = arrayOf(
            B, C, G, H, E, D
        )
        repeat(repeat) {
            array
                .forEach {
                    write(it)
                    delay(DELAY_NANO)
                }
        }
    }

    private suspend fun circleFilling(repeat: Int = 1) {
        val array = arrayOf(
                B,
                B or C,
                B or C or G,
                B or C or G or H,
                B or C or G or H or E,
                B or C or G or H or E or D,
                C or G or H or E or D,
                G or H or E or D,
                H or E or D,
                E or D,
                D,
        )
        repeat(repeat) {
            array
                .forEach {
                    write(it)
                    delay(DELAY_NANO)
                }
        }
    }

    private suspend fun linesHorizontalSwap(repeat: Int = 1) {
        repeat(repeat) {
            write(D or G)
            delay(DELAY_MICRO)
            write(F)
            delay(DELAY_MICRO)
        }
    }

    private suspend fun linesHorizontalBlink(repeat: Int = 1) {
        repeat(repeat) {
            write(D or G or F)
            delay(DELAY_MICRO)
            write(0)
            delay(DELAY_MICRO)
        }
    }

    private suspend fun random(repeat: Int = 1) {
        val random = Random(System.currentTimeMillis())
        repeat(repeat) {
            val (a, b, c) = random.generate(3)
            val data = (1 shl a) or (1 shl b) or (1 shl c)
            write(data)
            delay(DELAY_SHORT)
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

    private suspend fun write(byte: Int) {
        for (bit in 0 until 8) {
            val output = 0x80 and (byte shl bit)
            Gpio.digitalWrite(DATA, output)
            Gpio.digitalWrite(CLOCK, Gpio.HIGH)
            delay(DELAY_SPI)
            Gpio.digitalWrite(CLOCK, Gpio.LOW)
        }
        Gpio.digitalWrite(LATCH, Gpio.HIGH)
        delay(DELAY_SPI)
        Gpio.digitalWrite(LATCH, Gpio.LOW)
    }
}