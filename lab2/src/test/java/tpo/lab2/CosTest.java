package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для функции cos(x)")
class CosTest {
    private Sin sin;
    private Cos cos;
    private static final double EPSILON = 1e-4;

    @BeforeEach
    void setUp() {
        sin = new Sin(1e-6);
        cos = new Cos(sin);
    }

    @Test
    @DisplayName("cos(0) должен быть 1")
    void testCosZero() {
        assertEquals(1, cos.calculate(0), EPSILON, "cos(0) должен быть 1");
    }

    @Test
    @DisplayName("cos(π/2) должен быть 0")
    void testCosPiHalf() {
        assertEquals(0, cos.calculate(Math.PI / 2), EPSILON, "cos(π/2) должен быть 0");
    }

    @Test
    @DisplayName("cos(π) должен быть -1")
    void testCosPi() {
        assertEquals(-1, cos.calculate(Math.PI), EPSILON, "cos(π) должен быть -1");
    }

    @Test
    @DisplayName("cos(-π/2) должен быть 0")
    void testCosNegativePiHalf() {
        assertEquals(0, cos.calculate(-Math.PI / 2), EPSILON, "cos(-π/2) должен быть 0");
    }

    @Test
    @DisplayName("cos(π/3) должен быть 0.5")
    void testCosPiThird() {
        assertEquals(0.5, cos.calculate(Math.PI / 3), EPSILON, "cos(π/3) должен быть 0.5");
    }

    @Test
    @DisplayName("cos(π/4) должен быть √2/2")
    void testCosPiFourth() {
        assertEquals(Math.sqrt(2) / 2, cos.calculate(Math.PI / 4), EPSILON, "cos(π/4) должен быть √2/2");
    }

    @Test
    @DisplayName("cos(x) = cos(-x) (четность)")
    void testCosEvenProperty() {
        double x = 0.5;
        assertEquals(cos.calculate(x), cos.calculate(-x), EPSILON, "cos(x) должен равняться cos(-x)");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0, 1.5})
    @DisplayName("cos(x) должен быть в диапазоне [-1, 1]")
    void testCosRange(double x) {
        double result = cos.calculate(x);
        assertTrue(result >= -1 && result <= 1, "cos(" + x + ") должен быть в диапазоне [-1, 1]");
    }

    @Test
    @DisplayName("Периодичность cos: cos(x) = cos(x + 2π)")
    void testCosPeriodicity() {
        double x = 0.7;
        double result1 = cos.calculate(x);
        double result2 = cos.calculate(x + 2 * Math.PI);
        assertEquals(result1, result2, EPSILON, "cos(x) должен быть равен cos(x + 2π)");
    }

    @Test
    @DisplayName("Основное тригонометрическое тождество: sin²(x) + cos²(x) = 1")
    void testFundamentalIdentity() {
        double x = 0.7;
        double sinX = sin.calculate(x);
        double cosX = cos.calculate(x);
        double sum = sinX * sinX + cosX * cosX;
        assertEquals(1, sum, EPSILON, "sin²(x) + cos²(x) должно быть 1");
    }
}
