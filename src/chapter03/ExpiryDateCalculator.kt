package chapter03

import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.min

class ExpiryDateCalculator {

    fun calculate(payData: PayData): LocalDate {
        with(payData) {
            val months = getMonthsAdd(payAmount)
            val candidate = billingDate.plusMonths(months)
            if (dayNotMatch(firstBillingDate, candidate)) {
                return candidateDayAdjusted(candidate, firstBillingDate!!.dayOfMonth)
            }
            return candidate
        }
    }

    private fun getMonthsAdd(payAmount: Int): Long = if (payAmount == 100_000) 12L else payAmount / 10_000L

    private fun dayNotMatch(firstBillingDate: LocalDate?, candidate: LocalDate): Boolean =
        firstBillingDate != null &&
                firstBillingDate.dayOfMonth != candidate.dayOfMonth

    private fun candidateDayAdjusted(candidate: LocalDate, firstDateDayOfMonth: Int): LocalDate =
        candidate.withDayOfMonth(
            min(
                YearMonth.from(candidate).lengthOfMonth(),
                firstDateDayOfMonth
            )
        )
}