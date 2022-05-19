package chapter03

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class ExpiryDateCalculatorTest {
    private val calculator = ExpiryDateCalculator()

    private fun assertExpiryDate(
        payData: PayData,
        expected: LocalDate
    ) {
        val actual = calculator.calculate(payData)
        assertEquals(expected, actual)
    }

    @Test
    fun expireAfterMonth() {
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 5, 17))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 6, 17)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2020, 1, 1))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2020, 2, 1)
        )
    }

    @Test
    fun expireAfterMonth_DayNotSame() {
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 1, 31))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 2, 28)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 5, 31))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 6, 30)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2020, 1, 31))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2020, 2, 29)
        )
    }

    @Test
    fun startDayAndExpiryDayDifferent() {
        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 31))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 3, 31)
        )

        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 30))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 3, 30)
        )

        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 5, 31))
                .billingDate(LocalDate.of(2022, 6, 30))
                .payAmount(10_000)
                .build(),
            expected = LocalDate.of(2022, 7, 31)
        )
    }

    @Test
    fun payForManyMonths() {
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 3, 1))
                .payAmount(20_000)
                .build(),
            expected = LocalDate.of(2022, 5, 1)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 3, 1))
                .payAmount(30_000)
                .build(),
            expected = LocalDate.of(2022, 6, 1)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 3, 1))
                .payAmount(60_000)
                .build(),
            expected = LocalDate.of(2022, 9, 1)
        )
    }

    @Test
    fun setFirstBillingDate_PayForManyMonths() {
        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 31))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(20_000)
                .build(),
            expected = LocalDate.of(2022, 4, 30)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 31))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(40_000)
                .build(),
            expected = LocalDate.of(2022, 6, 30)
        )
        assertExpiryDate(
            payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 31))
                .billingDate(LocalDate.of(2022, 4, 30))
                .payAmount(30_000)
                .build(),
            expected = LocalDate.of(2022, 7, 31)
        )
    }

    @Test
    fun payForTenMonth_ProvideYear() {
        assertExpiryDate(
            payData = PayData.builder()
                .billingDate(LocalDate.of(2022, 3, 1))
                .payAmount(100_000)
                .build(),
            expected = LocalDate.of(2023, 3, 1)
        )
    }
}