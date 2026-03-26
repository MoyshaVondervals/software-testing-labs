package tpo.lab2;

/**
 * Реализация функции log_a(x) = ln(x) / ln(a)
 */
public class Log {
    private Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    /**
     * Вычисляет логарифм x по основанию a
     * @param x значение аргумента (x > 0)
     * @param base основание логарифма (base > 0, base != 1)
     * @return log_base(x)
     */
    public double calculate(double x, double base) {
        if (x <= 0) {
            throw new IllegalArgumentException("log(x) undefined for x <= 0");
        }
        if (base <= 0 || Math.abs(base - 1) < 1e-10) {
            throw new IllegalArgumentException("Invalid logarithm base: " + base);
        }

        return ln.calculate(x) / ln.calculate(base);
    }
}
