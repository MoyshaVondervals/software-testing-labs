package tpo.lab2;

/**
 * Система функций
 * При x <= 0: (((((((((cot(x) / sin(x)) + sec(x)) / cot(x)) / tan(x)) / tan(x)) - cot(x)) - tan(x)) * sin(x)) / 
 *             ((tan(x) ^ 2) - (sec(x) * ((tan(x) - (tan(x) + cos(x))) - csc(x))))
 * 
 * При x > 0:  (((((log_2(x) + log_3(x)) + log_5(x)) / log_3(x)) + (ln(x) - log_2(x))) * 
 *             ((log_2(x) * (log_3(x) ^ 3)) * log_3(x)))
 */
public class SystemFunction {
    private Sin sin;
    private Cos cos;
    private Tan tan;
    private Cot cot;
    private Sec sec;
    private Csc csc;
    private Log log;
    private Ln ln;

    public SystemFunction(Sin sin, Cos cos, Tan tan, Cot cot, Sec sec, Csc csc, Log log, Ln ln) {
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.csc = csc;
        this.log = log;
        this.ln = ln;
    }

    /**
     * Вычисляет значение системной функции
     */
    public double calculate(double x) {
        if (x <= 0) {
            return calculateForNegative(x);
        } else {
            return calculateForPositive(x);
        }
    }

    /**
     * Вычисляет систему для x <= 0:
     * (((((((((cot(x) / sin(x)) + sec(x)) / cot(x)) / tan(x)) / tan(x)) - cot(x)) - tan(x)) * sin(x)) / 
     * ((tan(x) ^ 2) - (sec(x) * ((tan(x) - (tan(x) + cos(x))) - csc(x))))
     */
    private double calculateForNegative(double x) {
        double sinX = sin.calculate(x);
        double cosX = cos.calculate(x);
        double tanX = tan.calculate(x);
        double cotX = cot.calculate(x);
        double secX = sec.calculate(x);
        double cscX = csc.calculate(x);

        // Числитель: (((((((((cot(x) / sin(x)) + sec(x)) / cot(x)) / tan(x)) / tan(x)) - cot(x)) - tan(x)) * sin(x))
        double numerator = cotX / sinX;
        numerator += secX;
        numerator /= cotX;
        numerator /= tanX;
        numerator /= tanX;
        numerator -= cotX;
        numerator -= tanX;
        numerator *= sinX;

        // Знаменатель: ((tan(x) ^ 2) - (sec(x) * ((tan(x) - (tan(x) + cos(x))) - csc(x))))
        double denominator = tanX * tanX;
        double inner = tanX - (tanX + cosX);
        inner -= cscX;
        denominator -= secX * inner;

        if (Math.abs(denominator) < 1e-10) {
            throw new ArithmeticException("System function denominator is zero for x = " + x);
        }

        return numerator / denominator;
    }

    /**
     * Вычисляет систему для x > 0:
     * (((((log_2(x) + log_3(x)) + log_5(x)) / log_3(x)) + (ln(x) - log_2(x))) * 
     * ((log_2(x) * (log_3(x) ^ 3)) * log_3(x)))
     */
    private double calculateForPositive(double x) {
        double log2X = log.calculate(x, 2);
        double log3X = log.calculate(x, 3);
        double log5X = log.calculate(x, 5);
        double lnX = ln.calculate(x);

        // Первая часть: (((((log_2(x) + log_3(x)) + log_5(x)) / log_3(x)) + (ln(x) - log_2(x)))
        double part1 = log2X + log3X;
        part1 += log5X;
        part1 /= log3X;
        part1 += (lnX - log2X);

        // Вторая часть: ((log_2(x) * (log_3(x) ^ 3)) * log_3(x))
        double part2 = log2X * (log3X * log3X * log3X);
        part2 *= log3X;

        return part1 * part2;
    }
}
