package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для функции sin(x)")
class SinTest {
    private Sin sin;
    private static final double EPSILON = 1e-4;

    @BeforeEach
    void setUp() {
        sin = new Sin(1e-6);
    }

    @Test
    @DisplayName("sin(0) должен быть 0")
    void testSinZero() {
        assertEquals(0, sin.calculate(0), EPSILON, "sin(0) должен быть 0");
    }

    @Test
    @DisplayName("sin(π/2) должен быть 1")
    void testSinPiHalf() {
        assertEquals(1, sin.calculate(Math.PI / 2), EPSILON, "sin(π/2) должен быть 1");
    }

    @Test
    @DisplayName("sin(π) должен быть 0")
    void testSinPi() {
        assertEquals(0, sin.calculate(Math.PI), EPSILON, "sin(π) должен быть 0");
    }

    @Test
    @DisplayName("sin(-π/2) должен быть -1")
    void testSinNegativePiHalf() {
        assertEquals(-1, sin.calculate(-Math.PI / 2), EPSILON, "sin(-π/2) должен быть -1");
    }

    @Test
    @DisplayName("sin(π/6) должен быть 0.5")
    void testSinPiSixth() {
        assertEquals(0.5, sin.calculate(Math.PI / 6), EPSILON, "sin(π/6) должен быть 0.5");
    }

    @Test
    @DisplayName("sin(π/4) должен быть √2/2")
    void testSinPiFourth() {
        assertEquals(Math.sqrt(2) / 2, sin.calculate(Math.PI / 4), EPSILON, "sin(π/4) должен быть √2/2");
    }

    @Test
    @DisplayName("sin(x) = -sin(-x) (нечетность)")
    void testSinOddProperty() {
        double x = 0.5;
        assertEquals(sin.calculate(x), -sin.calculate(-x), EPSILON, "sin(x) должен равняться -sin(-x)");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0, 1.5})
    @DisplayName("sin(x) должен быть в диапазоне [-1, 1]")
    void testSinRange(double x) {
        double result = sin.calculate(x);
        assertTrue(result >= -1 && result <= 1, "sin(" + x + ") должен быть в диапазоне [-1, 1]");
    }

    @Test
    @DisplayName("Периодичность sin: sin(x) = sin(x + 2π)")
    void testSinPeriodicity() {
        double x = 0.7;
        double result1 = sin.calculate(x);
        double result2 = sin.calculate(x + 2 * Math.PI);
        assertEquals(result1, result2, EPSILON, "sin(x) должен быть равен sin(x + 2π)");
    }
}
