package chapter02

class PasswordStrengthMeter {

    fun meter(password: String): PasswordStrength {
        if (password.isEmpty()) return PasswordStrength.INVALID

        return when (getScore(password)) {
            0, 1 -> PasswordStrength.WEAK
            2 -> PasswordStrength.NORMAL
            3 -> PasswordStrength.STRONG
            else -> PasswordStrength.INVALID
        }
    }

    private fun getScore(password: String): Int {
        var count = 0
        if (password.length >= 8) count++
        if (password.containsDigit()) count++
        if (password.containsUpperCase()) count++
        return count
    }

    private fun String.containsDigit() = this.any { it.isDigit() }

    private fun String.containsUpperCase() = this.any { it.isUpperCase() }
}