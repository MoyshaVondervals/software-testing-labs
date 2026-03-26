package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для логарифмических функций")
class LogarithmicFunctionsTest {
    private Ln ln;
    private Log log;
    private static final double EPSILON = 1e-4;

    @BeforeEach
    void setUp() {
        ln = new Ln(1e-6);
        log = new Log(ln);
    }

    @Test
    @DisplayName("ln(1) должен быть 0")
    void testLnOne() {
        assertEquals(0, ln.calculate(1), EPSILON);
    }

    @Test
    @DisplayName("ln(e) должен быть 1")
    void testLnE() {
        assertEquals(1, ln.calculate(Math.E), EPSILON);
    }

    @Test
    @DisplayName("ln(e²) должен быть 2")
    void testLnESquared() {
        assertEquals(2, ln.calculate(Math.E * Math.E), EPSILON);
    }

    @Test
    @DisplayName("ln(1/e) должен быть -1")
    void testLnInverseE() {
        assertEquals(-1, ln.calculate(1 / Math.E), EPSILON);
    }

    @Test
    @DisplayName("ln(x) выбрасывает исключение при x <= 0")
    void testLnNegative() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1));
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(0));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0, 2.0, 5.0, 10.0})
    @DisplayName("ln(x) возвращает значение для x > 0")
    void testLnPositive(double x) {
        assertDoesNotThrow(() -> ln.calculate(x));
        double result = ln.calculate(x);
        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("ln(a*b) = ln(a) + ln(b)")
    void testLnAddProperty() {
        double a = 2.0;
        double b = 3.0;
        double lnProduct = ln.calculate(a * b);
        double sumOfLogs = ln.calculate(a) + ln.calculate(b);
        assertEquals(lnProduct, sumOfLogs, EPSILON);
    }

    @Test
    @DisplayName("ln(a/b) = ln(a) - ln(b)")
    void testLnSubtractProperty() {
        double a = 5.0;
        double b = 2.0;
        double lnQuotient = ln.calculate(a / b);
        double diffOfLogs = ln.calculate(a) - ln.calculate(b);
        assertEquals(lnQuotient, diffOfLogs, EPSILON);
    }

    @Test
    @DisplayName("ln(x^n) = n * ln(x)")
    void testLnPowerProperty() {
        double x = 2.0;
        int n = 3;
        double lnPower = ln.calculate(Math.pow(x, n));
        double nTimesLog = n * ln.calculate(x);
        assertEquals(lnPower, nTimesLog, EPSILON);
    }

    // Тесты для log_a(x)
    @Test
    @DisplayName("log_2(8) должен быть 3")
    void testLog2Eight() {
        assertEquals(3, log.calculate(8, 2), EPSILON);
    }

    @Test
    @DisplayName("log_10(100) должен быть 2")
    void testLog10Hundred() {
        assertEquals(2, log.calculate(100, 10), EPSILON);
    }

    @Test
    @DisplayName("log_2(1) должен быть 0")
    void testLogOne() {
        assertEquals(0, log.calculate(1, 2), EPSILON);
    }

    @Test
    @DisplayName("log_a(a) должен быть 1")
    void testLogBase() {
        assertEquals(1, log.calculate(2, 2), EPSILON);
        assertEquals(1, log.calculate(3, 3), EPSILON);
        assertEquals(1, log.calculate(5, 5), EPSILON);
    }

    @Test
    @DisplayName("log_a(x) выбрасывает исключение при x <= 0")
    void testLogNegativeArgument() {
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, 2));
    }

    @Test
    @DisplayName("log_a(x) выбрасывает исключение при a <= 0 или a = 1")
    void testLogInvalidBase() {
        assertThrows(IllegalArgumentException.class, () -> log.calculate(8, -1));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(8, 0));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(8, 1));
    }

    @Test
    @DisplayName("log_a(x) = log_b(x) / log_b(a) (формула изменения основания)")
    void testLogBaseChange() {
        double x = 8;
        double oldBase = 2;
        double newBase = 10;
        
        double result1 = log.calculate(x, oldBase);
        double result2 = log.calculate(x, newBase) / log.calculate(oldBase, newBase);
        
        assertEquals(result1, result2, EPSILON);
    }

    @Test
    @DisplayName("log_2(x), log_3(x), log_5(x) для x = 10")
    void testVariousLogarithms() {
        double x = 10;
        double log2 = log.calculate(x, 2);
        double log3 = log.calculate(x, 3);
        double log5 = log.calculate(x, 5);
        
        assertTrue(log2 > 0);
        assertTrue(log3 > 0);
        assertTrue(log5 > 0);
        
        assertTrue(log2 > log3); // Логарифм с меньшим основанием больше
        assertTrue(log3 > log5);
    }
}
