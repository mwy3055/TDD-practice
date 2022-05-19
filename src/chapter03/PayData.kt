package chapter03

import java.time.LocalDate

class PayData private constructor(
    val firstBillingDate: LocalDate?,
    val billingDate: LocalDate,
    val payAmount: Int,
) {

    companion object {
        fun builder() = PayDataBuilder()
    }

    class PayDataBuilder {
        private var firstBillingDate: LocalDate? = null
        private var billingDate: LocalDate = LocalDate.now()
        private var payAmount: Int = 0

        fun firstBillingDate(firstDate: LocalDate): PayDataBuilder {
            firstBillingDate = firstDate
            return this
        }

        fun billingDate(date: LocalDate): PayDataBuilder {
            billingDate = date
            return this
        }

        fun payAmount(amount: Int): PayDataBuilder {
            payAmount = amount
            return this
        }

        fun build() = PayData(
            firstBillingDate = firstBillingDate,
            billingDate = billingDate,
            payAmount = payAmount
        )
    }
}
