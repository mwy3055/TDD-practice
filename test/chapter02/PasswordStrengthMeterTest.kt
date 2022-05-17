package chapter02

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PasswordStrengthMeterTest {
    private val meter = PasswordStrengthMeter()

    private fun assertStrength(password: String, expected: PasswordStrength) {
        val actual = meter.meter(password)
        assertEquals(expected, actual)
    }

    @Test
    fun meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG)
        assertStrength("abc1!Add", PasswordStrength.STRONG)
    }

    @Test
    fun meetsAllCriteria_Except_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL)
        assertStrength("Ab12!c", PasswordStrength.NORMAL)
    }

    @Test
    fun meetsAllCriteria_Except_Number_Then_Normal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL)
    }

    @Test
    fun emptyInput_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID)
    }

    @Test
    fun meetsAllCriteria_Except_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL)
    }

    @Test
    fun meetsOnlyLength_Then_Weak() {
        assertStrength("abcaaddejk", PasswordStrength.WEAK)
    }

    @Test
    fun meetsOnlyNumber_Then_Weak() {
        assertStrength("1234", PasswordStrength.WEAK)
    }

    @Test
    fun meetsOnlyUpperCase_Then_Weak() {
        assertStrength("ABC", PasswordStrength.WEAK)
    }

    @Test
    fun meetsAnyCriteria_Then_Weak() {
        assertStrength("abc", PasswordStrength.WEAK)
    }
}