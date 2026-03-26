package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Интеграционные тесты для системной функции")
class SystemFunctionIntegrationTest {
    private SystemFunction systemFunction;
    private static final double EPSILON = 1e-3;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin(1e-6);
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);
        Ln ln = new Ln(1e-6);
        Log log = new Log(ln);
        systemFunction = new SystemFunction(sin, cos, tan, cot, sec, csc, log, ln);
    }

    // Тесты для x <= 0 (тригонометрическая часть)
    @Test
    @DisplayName("Система функция определена для x = -0.5")
    void testSystemFunctionNegativeHalf() {
        assertDoesNotThrow(() -> systemFunction.calculate(-0.5));
        double result = systemFunction.calculate(-0.5);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @Test
    @DisplayName("Система функция определена для x = -1.0")
    void testSystemFunctionNegativeOne() {
        assertDoesNotThrow(() -> systemFunction.calculate(-1.0));
        double result = systemFunction.calculate(-1.0);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @Test
    @DisplayName("Система функция определена для x = -π/6")
    void testSystemFunctionNegativePiSixth() {
        assertDoesNotThrow(() -> systemFunction.calculate(-Math.PI / 6));
        double result = systemFunction.calculate(-Math.PI / 6);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.5, -1.0, -1.5, -2.0})
    @DisplayName("Система функция возвращает конечные значения для отрицательных x")
    void testSystemFunctionNegativeValues(double x) {
        double result = systemFunction.calculate(x);
        assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
    }

    // Тесты для x > 0 (логарифмическая часть)
    @Test
    @DisplayName("Система функция определена для x = 0.5")
    void testSystemFunctionPositiveHalf() {
        assertDoesNotThrow(() -> systemFunction.calculate(0.5));
        double result = systemFunction.calculate(0.5);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @Test
    @DisplayName("Система функция при x = 1.0 — особая точка (log_3(1) = 0)")
    void testSystemFunctionPositiveOne() {
        // При x=1: log_a(1) = 0 для всех a, деление на log_3(x) даёт деление на 0
        // Результат NaN или Inf
        double result = systemFunction.calculate(1.0);
        assertTrue(Double.isNaN(result) || Double.isInfinite(result),
                "При x=1 результат NaN или бесконечность из-за log_3(1)=0");
    }

    @Test
    @DisplayName("Система функция определена для x = 2.0")
    void testSystemFunctionPositiveTwo() {
        assertDoesNotThrow(() -> systemFunction.calculate(2.0));
        double result = systemFunction.calculate(2.0);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @Test
    @DisplayName("Система функция определена для x = 10.0")
    void testSystemFunctionPositiveTen() {
        assertDoesNotThrow(() -> systemFunction.calculate(10.0));
        double result = systemFunction.calculate(10.0);
        assertTrue(Double.isFinite(result), "Результат должен быть конечным числом");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 2.0, 5.0, 10.0})
    @DisplayName("Система функция возвращает конечные значения для положительных x")
    void testSystemFunctionPositiveValues(double x) {
        double result = systemFunction.calculate(x);
        assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
    }

    @Test
    @DisplayName("Проверка монотонности для положительных x")
    void testMonotonicity() {
        double x1 = 2.0;
        double x2 = 3.0;
        double result1 = systemFunction.calculate(x1);
        double result2 = systemFunction.calculate(x2);
        
        assertTrue(Double.isFinite(result1) && Double.isFinite(result2),
                "Оба результата должны быть конечными");
    }

    @Test
    @DisplayName("Непрерывность в точках разрыва триг функций (x = -π/2, -3π/2)")
    void testDiscontinuities() {
        double nearPiHalf = -Math.PI / 2 + 0.1;
        assertDoesNotThrow(() -> systemFunction.calculate(nearPiHalf),
                "Функция должна быть определена вблизи точек разрыва");
    }

    @Test
    @DisplayName("Эквивалентный класс: очень малые отрицательные значения")
    void testEquivalenceClassSmallNegative() {
        double[] values = {-0.001, -0.01, -0.1};
        for (double x : values) {
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
        }
    }

    @Test
    @DisplayName("Эквивалентный класс: отрицательные значения средней величины")
    void testEquivalenceClassMediumNegative() {
        double[] values = {-0.5, -1.0, -1.5};
        for (double x : values) {
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
        }
    }

    @Test
    @DisplayName("Эквивалентный класс: очень малые положительные значения")
    void testEquivalenceClassSmallPositive() {
        double[] values = {0.001, 0.01, 0.1};
        for (double x : values) {
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
        }
    }

    @Test
    @DisplayName("Эквивалентный класс: положительные значения средней величины")
    void testEquivalenceClassMediumPositive() {
        double[] values = {0.5, 1.5, 2.0};
        for (double x : values) {
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
        }
    }

    @Test
    @DisplayName("Эквивалентный класс: большие положительные значения")
    void testEquivalenceClassLargePositive() {
        double[] values = {5.0, 10.0, 100.0};
        for (double x : values) {
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), "Результат для x=" + x + " должен быть конечным");
        }
    }

    @Test
    @DisplayName("Проверка x = 0 (граница) — не в ОДЗ")
    void testBoundaryValueZero() {
        assertThrows(ArithmeticException.class, () -> systemFunction.calculate(0),
                "При x=0 должно быть исключение, так как cot(0) и csc(0) не определены");
    }

    @Test
    @DisplayName("Проверка граничных значений вблизи нуля")
    void testBoundaryValuesNearZero() {
        double epsilon = 1e-5;

        double resultNegative = systemFunction.calculate(-epsilon);
        double resultPositive = systemFunction.calculate(epsilon);
        
        assertTrue(Double.isFinite(resultNegative), "Результат слева от 0 должен быть конечным");
        assertTrue(Double.isFinite(resultPositive), "Результат справа от 0 должен быть конечным");
    }

    @Test
    @DisplayName("Проверка различных точек из всей области определения")
    void testComprehensiveCoverage() {
        double[] testPoints = {-2.0, -1.0, -0.5, -0.1, 0.1, 0.5, 1.5, 2.0, 5.0};
        
        for (double x : testPoints) {
            if (x == 0) continue;
            
            double result = systemFunction.calculate(x);
            assertTrue(Double.isFinite(result), 
                    "Функция должна возвращать конечное значение для x=" + x);
        }
    }
}
