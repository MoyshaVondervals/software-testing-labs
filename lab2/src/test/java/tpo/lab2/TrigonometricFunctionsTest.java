package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для тригонометрических функций Tan, Cot, Sec, Csc")
class TrigonometricFunctionsTest {
    private Sin sin;
    private Cos cos;
    private Tan tan;
    private Cot cot;
    private Sec sec;
    private Csc csc;
    private static final double EPSILON = 1e-4;

    @BeforeEach
    void setUp() {
        sin = new Sin(1e-6);
        cos = new Cos(sin);
        tan = new Tan(sin, cos);
        cot = new Cot(sin, cos);
        sec = new Sec(cos);
        csc = new Csc(sin);
    }

    // Тесты для tan(x)
    @Test
    @DisplayName("tan(0) должен быть 0")
    void testTanZero() {
        assertEquals(0, tan.calculate(0), EPSILON);
    }

    @Test
    @DisplayName("tan(π/4) должен быть 1")
    void testTanPiFourth() {
        assertEquals(1, tan.calculate(Math.PI / 4), EPSILON);
    }

    @Test
    @DisplayName("tan(π/6) должен быть 1/√3")
    void testTanPiSixth() {
        assertEquals(1 / Math.sqrt(3), tan.calculate(Math.PI / 6), EPSILON);
    }

    @Test
    @DisplayName("tan(π/3) должен быть √3")
    void testTanPiThird() {
        assertEquals(Math.sqrt(3), tan.calculate(Math.PI / 3), EPSILON);
    }

    @Test
    @DisplayName("tan(x) = sin(x) / cos(x)")
    void testTanDefinition() {
        double x = 0.5;
        double expected = sin.calculate(x) / cos.calculate(x);
        assertEquals(expected, tan.calculate(x), EPSILON);
    }

    @Test
    @DisplayName("tan(π/2) должен выбросить исключение")
    void testTanUndefined() {
        assertThrows(ArithmeticException.class, () -> tan.calculate(Math.PI / 2));
    }

    // Тесты для cot(x)
    @Test
    @DisplayName("cot(π/4) должен быть 1")
    void testCotPiFourth() {
        assertEquals(1, cot.calculate(Math.PI / 4), EPSILON);
    }

    @Test
    @DisplayName("cot(π/6) должен быть √3")
    void testCotPiSixth() {
        assertEquals(Math.sqrt(3), cot.calculate(Math.PI / 6), EPSILON);
    }

    @Test
    @DisplayName("cot(x) = cos(x) / sin(x)")
    void testCotDefinition() {
        double x = 0.7;
        double expected = cos.calculate(x) / sin.calculate(x);
        assertEquals(expected, cot.calculate(x), EPSILON);
    }

    @Test
    @DisplayName("cot(0) должен выбросить исключение")
    void testCotUndefined() {
        assertThrows(ArithmeticException.class, () -> cot.calculate(0));
    }

    // Тесты для sec(x)
    @Test
    @DisplayName("sec(0) должен быть 1")
    void testSecZero() {
        assertEquals(1, sec.calculate(0), EPSILON);
    }

    @Test
    @DisplayName("sec(π/4) должен быть √2")
    void testSecPiFourth() {
        assertEquals(Math.sqrt(2), sec.calculate(Math.PI / 4), EPSILON);
    }

    @Test
    @DisplayName("sec(x) = 1 / cos(x)")
    void testSecDefinition() {
        double x = 0.5;
        double expected = 1 / cos.calculate(x);
        assertEquals(expected, sec.calculate(x), EPSILON);
    }

    // Тесты для csc(x)
    @Test
    @DisplayName("csc(π/2) должен быть 1")
    void testCscPiHalf() {
        assertEquals(1, csc.calculate(Math.PI / 2), EPSILON);
    }

    @Test
    @DisplayName("csc(π/6) должен быть 2")
    void testCscPiSixth() {
        assertEquals(2, csc.calculate(Math.PI / 6), EPSILON);
    }

    @Test
    @DisplayName("csc(x) = 1 / sin(x)")
    void testCscDefinition() {
        double x = 0.7;
        double expected = 1 / sin.calculate(x);
        assertEquals(expected, csc.calculate(x), EPSILON);
    }

    @Test
    @DisplayName("csc(0) должен выбросить исключение")
    void testCscUndefined() {
        assertThrows(ArithmeticException.class, () -> csc.calculate(0));
    }

    // Тест на связь между функциями
    @Test
    @DisplayName("tan(x) * cot(x) = 1")
    void testTanCotRelation() {
        double x = 0.7;
        double product = tan.calculate(x) * cot.calculate(x);
        assertEquals(1, product, EPSILON);
    }

    @Test
    @DisplayName("sec(x) * cos(x) = 1")
    void testSecCosRelation() {
        double x = 0.5;
        double product = sec.calculate(x) * cos.calculate(x);
        assertEquals(1, product, EPSILON);
    }

    @Test
    @DisplayName("csc(x) * sin(x) = 1")
    void testCscSinRelation() {
        double x = 0.7;
        double product = csc.calculate(x) * sin.calculate(x);
        assertEquals(1, product, EPSILON);
    }
}
