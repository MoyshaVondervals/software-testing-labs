package tpo.lab2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты с табличными заглушками (stubs).
 *
 * Стратегия интеграции: СНИЗУ-ВВЕРХ (Bottom-Up).
 *
 * Обоснование стратегии:
 * Система функций имеет чёткую иерархическую зависимость модулей:
 *
 * Уровень 0 (базовые): sin(x), ln(x)   — реализованы через ряды
 * Уровень 1 (производные): cos(x), csc(x) — зависят от sin(x)
 * Уровень 2 (производные): tan(x), cot(x), sec(x), log_a(x) — зависят от уровня 0-1
 * Уровень 3 (система): SystemFunction — зависит от всех уровней
 *
 * При восходящей интеграции мы начинаем с базовых модулей (sin, ln),
 * которые тестируются изолированно, затем подключаем зависимые модули,
 * заменяя заглушки реальными реализациями по одному модулю за раз.
 *
 * Порядок интеграции:
 * 1) Все модули — заглушки (полностью табличное тестирование)
 * 2) Подключаем sin(x) — остальные заглушки
 * 3) Подключаем cos(x) — sin реальный, остальные заглушки
 * 4) Подключаем tan(x), cot(x), sec(x), csc(x) — sin, cos реальные
 * 5) Подключаем ln(x) — тригонометрия реальная, логарифмы заглушки
 * 6) Подключаем log(x) — ln реальный
 * 7) Полная интеграция — все модули реальные
 */
@DisplayName("Интеграционные тесты с заглушками (Bottom-Up)")
class StubIntegrationTest {

    private static final double EPSILON = 1e-3;


    static class SinStub extends Sin {
        SinStub() {
            super(1e-6);
        }

        @Override
        public double calculate(double x) {
            // Нормализуем к [-π, π]
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) return 0.0;
            if (Math.abs(x - Math.PI / 6) < 1e-6) return 0.5;
            if (Math.abs(x - Math.PI / 4) < 1e-6) return Math.sqrt(2) / 2;
            if (Math.abs(x - Math.PI / 3) < 1e-6) return Math.sqrt(3) / 2;
            if (Math.abs(x - Math.PI / 2) < 1e-6) return 1.0;
            if (Math.abs(x - Math.PI) < 1e-6) return 0.0;
            if (Math.abs(x + Math.PI / 6) < 1e-6) return -0.5;
            if (Math.abs(x + Math.PI / 4) < 1e-6) return -Math.sqrt(2) / 2;
            if (Math.abs(x + Math.PI / 3) < 1e-6) return -Math.sqrt(3) / 2;
            if (Math.abs(x + Math.PI / 2) < 1e-6) return -1.0;
            if (Math.abs(x + Math.PI) < 1e-6) return 0.0;
            // Для произвольных значений используем Math.sin
            if (Math.abs(x - (-0.5)) < 1e-6) return Math.sin(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return Math.sin(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return Math.sin(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return Math.sin(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return Math.sin(-0.1);
            return Math.sin(x); // Fallback
        }
    }


    static class CosStub extends Cos {
        public CosStub() { super(new SinStub()); }

        @Override
        public double calculate(double x) {
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) return 1.0;
            if (Math.abs(x - Math.PI / 6) < 1e-6) return Math.sqrt(3) / 2;
            if (Math.abs(x - Math.PI / 4) < 1e-6) return Math.sqrt(2) / 2;
            if (Math.abs(x - Math.PI / 3) < 1e-6) return 0.5;
            if (Math.abs(x - Math.PI / 2) < 1e-6) return 0.0;
            if (Math.abs(x - Math.PI) < 1e-6) return -1.0;
            if (Math.abs(x + Math.PI / 6) < 1e-6) return Math.sqrt(3) / 2;
            if (Math.abs(x + Math.PI / 4) < 1e-6) return Math.sqrt(2) / 2;
            if (Math.abs(x + Math.PI / 3) < 1e-6) return 0.5;
            if (Math.abs(x + Math.PI / 2) < 1e-6) return 0.0;
            if (Math.abs(x + Math.PI) < 1e-6) return -1.0;
            if (Math.abs(x - (-0.5)) < 1e-6) return Math.cos(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return Math.cos(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return Math.cos(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return Math.cos(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return Math.cos(-0.1);
            return Math.cos(x);
        }
    }

    /** Табличная заглушка для tan(x) */
    static class TanStub extends Tan {
        public TanStub() { super(new SinStub(), new CosStub()); }

        @Override
        public double calculate(double x) {
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) return 0.0;
            if (Math.abs(x - Math.PI / 4) < 1e-6) return 1.0;
            if (Math.abs(x - Math.PI / 6) < 1e-6) return 1.0 / Math.sqrt(3);
            if (Math.abs(x + Math.PI / 4) < 1e-6) return -1.0;
            if (Math.abs(x + Math.PI / 6) < 1e-6) return -1.0 / Math.sqrt(3);
            if (Math.abs(x - (-0.5)) < 1e-6) return Math.tan(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return Math.tan(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return Math.tan(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return Math.tan(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return Math.tan(-0.1);
            return Math.tan(x);
        }
    }

    /** Табличная заглушка для cot(x) */
    static class CotStub extends Cot {
        public CotStub() { super(new SinStub(), new CosStub()); }

        @Override
        public double calculate(double x) {
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) throw new ArithmeticException("cot(0) undefined");
            if (Math.abs(x - Math.PI / 4) < 1e-6) return 1.0;
            if (Math.abs(x - Math.PI / 6) < 1e-6) return Math.sqrt(3);
            if (Math.abs(x + Math.PI / 4) < 1e-6) return -1.0;
            if (Math.abs(x + Math.PI / 6) < 1e-6) return -Math.sqrt(3);
            if (Math.abs(x - (-0.5)) < 1e-6) return 1.0 / Math.tan(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return 1.0 / Math.tan(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return 1.0 / Math.tan(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return 1.0 / Math.tan(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return 1.0 / Math.tan(-0.1);
            return 1.0 / Math.tan(x);
        }
    }

    /** Табличная заглушка для sec(x) */
    static class SecStub extends Sec {
        public SecStub() { super(new CosStub()); }

        @Override
        public double calculate(double x) {
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) return 1.0;
            if (Math.abs(x - Math.PI / 4) < 1e-6) return Math.sqrt(2);
            if (Math.abs(x - Math.PI / 3) < 1e-6) return 2.0;
            if (Math.abs(x - (-0.5)) < 1e-6) return 1.0 / Math.cos(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return 1.0 / Math.cos(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return 1.0 / Math.cos(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return 1.0 / Math.cos(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return 1.0 / Math.cos(-0.1);
            return 1.0 / Math.cos(x);
        }
    }

    /** Табличная заглушка для csc(x) */
    static class CscStub extends Csc {
        public CscStub() { super(new SinStub()); }

        @Override
        public double calculate(double x) {
            while (x > Math.PI) x -= 2 * Math.PI;
            while (x < -Math.PI) x += 2 * Math.PI;

            if (Math.abs(x - 0) < 1e-6) throw new ArithmeticException("csc(0) undefined");
            if (Math.abs(x - Math.PI / 6) < 1e-6) return 2.0;
            if (Math.abs(x - Math.PI / 2) < 1e-6) return 1.0;
            if (Math.abs(x + Math.PI / 6) < 1e-6) return -2.0;
            if (Math.abs(x + Math.PI / 2) < 1e-6) return -1.0;
            if (Math.abs(x - (-0.5)) < 1e-6) return 1.0 / Math.sin(-0.5);
            if (Math.abs(x - (-1.0)) < 1e-6) return 1.0 / Math.sin(-1.0);
            if (Math.abs(x - (-1.5)) < 1e-6) return 1.0 / Math.sin(-1.5);
            if (Math.abs(x - (-2.0)) < 1e-6) return 1.0 / Math.sin(-2.0);
            if (Math.abs(x - (-0.1)) < 1e-6) return 1.0 / Math.sin(-0.1);
            return 1.0 / Math.sin(x);
        }
    }

    /** Табличная заглушка для ln(x) */
    static class LnStub extends Ln {
        public LnStub() { super(1e-6); }

        @Override
        public double calculate(double x) {
            if (x <= 0) throw new IllegalArgumentException("ln(x) undefined for x <= 0");
            if (Math.abs(x - 1.0) < 1e-6) return 0.0;
            if (Math.abs(x - Math.E) < 1e-6) return 1.0;
            if (Math.abs(x - 0.5) < 1e-6) return Math.log(0.5);
            if (Math.abs(x - 2.0) < 1e-6) return Math.log(2.0);
            if (Math.abs(x - 3.0) < 1e-6) return Math.log(3.0);
            if (Math.abs(x - 5.0) < 1e-6) return Math.log(5.0);
            if (Math.abs(x - 10.0) < 1e-6) return Math.log(10.0);
            if (Math.abs(x - 0.1) < 1e-6) return Math.log(0.1);
            if (Math.abs(x - 0.01) < 1e-6) return Math.log(0.01);
            if (Math.abs(x - 0.001) < 1e-6) return Math.log(0.001);
            if (Math.abs(x - 100.0) < 1e-6) return Math.log(100.0);
            if (Math.abs(x - 1.5) < 1e-6) return Math.log(1.5);
            return Math.log(x);
        }
    }

    /** Табличная заглушка для log_a(x) */
    static class LogStub extends Log {
        public LogStub() { super(new LnStub()); }

        @Override
        public double calculate(double x, double base) {
            if (x <= 0) throw new IllegalArgumentException("log(x) undefined for x <= 0");
            if (base <= 0 || Math.abs(base - 1) < 1e-10)
                throw new IllegalArgumentException("Invalid base: " + base);
            if (Math.abs(x - 1.0) < 1e-6) return 0.0;
            return Math.log(x) / Math.log(base);
        }
    }

    // ============================================================
    // ЭТАП 1: ВСЕ МОДУЛИ — ЗАГЛУШКИ
    // ============================================================

    @Test
    @DisplayName("Этап 1: Все заглушки — проверка системы для x = -0.5")
    void testAllStubs_negativeHalf() {
        SystemFunction sf = new SystemFunction(
                new SinStub(), new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result), "Результат для x=-0.5 должен быть конечным");

        // Сравним с эталоном
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON, "Результат всех заглушек должен совпадать с эталоном");
    }

    @Test
    @DisplayName("Этап 1: Все заглушки — проверка системы для x = -1.0")
    void testAllStubs_negativeOne() {
        SystemFunction sf = new SystemFunction(
                new SinStub(), new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-1.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-1.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 1: Все заглушки — проверка системы для x = 2.0")
    void testAllStubs_positiveTwo() {
        SystemFunction sf = new SystemFunction(
                new SinStub(), new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(2.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(2.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 1: Все заглушки — проверка системы для x = 5.0")
    void testAllStubs_positiveFive() {
        SystemFunction sf = new SystemFunction(
                new SinStub(), new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(5.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(5.0);
        assertEquals(expected, result, EPSILON);
    }

    // ============================================================
    // ЭТАП 2: Подключаем sin(x) — остальные заглушки
    // ============================================================

    @Test
    @DisplayName("Этап 2: Реальный sin, остальные заглушки — x = -0.5")
    void testRealSin_negativeHalf() {
        Sin realSin = new Sin(1e-6);
        SystemFunction sf = new SystemFunction(
                realSin, new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 2: Реальный sin, остальные заглушки — x = -1.0")
    void testRealSin_negativeOne() {
        Sin realSin = new Sin(1e-6);
        SystemFunction sf = new SystemFunction(
                realSin, new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-1.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-1.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 2: Реальный sin, остальные заглушки — x = 2.0")
    void testRealSin_positiveTwo() {
        Sin realSin = new Sin(1e-6);
        SystemFunction sf = new SystemFunction(
                realSin, new CosStub(), new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(2.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(2.0);
        assertEquals(expected, result, EPSILON);
    }

    // ============================================================
    // ЭТАП 3: Подключаем cos(x) — sin реальный, остальные заглушки
    // ============================================================

    @Test
    @DisplayName("Этап 3: Реальные sin+cos, остальные заглушки — x = -0.5")
    void testRealSinCos_negativeHalf() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 3: Реальные sin+cos, остальные заглушки — x = -2.0")
    void testRealSinCos_negativeTwo() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, new TanStub(), new CotStub(),
                new SecStub(), new CscStub(), new LogStub(), new LnStub()
        );
        double result = sf.calculate(-2.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-2.0);
        assertEquals(expected, result, EPSILON);
    }

    // ============================================================
    // ЭТАП 4: Подключаем все тригонометрические — sin, cos реальные,
    // tan, cot, sec, csc реальные, логарифмы заглушки
    // ============================================================

    @Test
    @DisplayName("Этап 4: Реальная тригонометрия, заглушки логарифмов — x = -0.5")
    void testRealTrig_negativeHalf() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, new LogStub(), new LnStub()
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 4: Реальная тригонометрия, заглушки логарифмов — x = -1.0")
    void testRealTrig_negativeOne() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, new LogStub(), new LnStub()
        );
        double result = sf.calculate(-1.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-1.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 4: Реальная тригонометрия, заглушки логарифмов — x = 5.0")
    void testRealTrig_positiveFive() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, new LogStub(), new LnStub()
        );
        double result = sf.calculate(5.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(5.0);
        assertEquals(expected, result, EPSILON);
    }

    // ============================================================
    // ЭТАП 5: Подключаем ln(x) — тригонометрия реальная,
    // ln реальный, log заглушка
    // ============================================================

    @Test
    @DisplayName("Этап 5: Реальные тригонометрия + ln, заглушка log — x = -0.5")
    void testRealTrigLn_negativeHalf() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, new LogStub(), realLn
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 5: Реальные тригонометрия + ln, заглушка log — x = 2.0")
    void testRealTrigLn_positiveTwo() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, new LogStub(), realLn
        );
        double result = sf.calculate(2.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(2.0);
        assertEquals(expected, result, EPSILON);
    }

    // ============================================================
    // ЭТАП 6: Подключаем log(x) — все модули реальные
    // ============================================================

    @Test
    @DisplayName("Этап 6: Полная интеграция — x = -0.5")
    void testFullIntegration_negativeHalf() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        Log realLog = new Log(realLn);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, realLog, realLn
        );
        double result = sf.calculate(-0.5);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-0.5);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 6: Полная интеграция — x = -1.0")
    void testFullIntegration_negativeOne() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        Log realLog = new Log(realLn);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, realLog, realLn
        );
        double result = sf.calculate(-1.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedNegative(-1.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 6: Полная интеграция — x = 2.0")
    void testFullIntegration_positiveTwo() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        Log realLog = new Log(realLn);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, realLog, realLn
        );
        double result = sf.calculate(2.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(2.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 6: Полная интеграция — x = 5.0")
    void testFullIntegration_positiveFive() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        Log realLog = new Log(realLn);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, realLog, realLn
        );
        double result = sf.calculate(5.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(5.0);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Этап 6: Полная интеграция — x = 10.0")
    void testFullIntegration_positiveTen() {
        Sin realSin = new Sin(1e-6);
        Cos realCos = new Cos(realSin);
        Tan realTan = new Tan(realSin, realCos);
        Cot realCot = new Cot(realSin, realCos);
        Sec realSec = new Sec(realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln(1e-6);
        Log realLog = new Log(realLn);
        SystemFunction sf = new SystemFunction(
                realSin, realCos, realTan, realCot,
                realSec, realCsc, realLog, realLn
        );
        double result = sf.calculate(10.0);
        assertTrue(Double.isFinite(result));
        double expected = computeExpectedPositive(10.0);
        assertEquals(expected, result, EPSILON);
    }


    private double computeExpectedNegative(double x) {
        return switch (Double.toString(x)) {
            case "-0.5" -> -2.9778919938156019;
            case "-1.0" -> -0.35768856450097369;
            case "-2.0" -> 0.42917986583257056;
            default -> throw new IllegalArgumentException("Unsupported x for negative branch: " + x);
        };
    }


    private double computeExpectedPositive(double x) {
        return switch (Double.toString(x)) {
            case "2.0" -> 0.46915977177796336;
            case "5.0" -> 27.325751582645101;
            case "10.0" -> 144.11695547529726;
            default -> throw new IllegalArgumentException("Unsupported x for positive branch: " + x);
        };
    }
}
