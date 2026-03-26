package tpo.lab2;


public class Ln {
    private final double precision;

    public Ln(double precision) {
        this.precision = precision;
    }

    public double calculate(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) undefined for x <= 0");
        }

        // Если x очень близко к 1, используем разложение напрямую
        if (Math.abs(x - 1) < 0.5) {
            return lnSeries(x);
        }

        // Иначе приводим x к диапазону [0.5, 1.5)
        int powerOf2 = 0;
        double normalized = x;
        
        while (normalized >= Math.E) {
            normalized /= Math.E;
            powerOf2++;
        }
        
        while (normalized < 1 / Math.E) {
            normalized *= Math.E;
            powerOf2--;
        }

        return powerOf2 + lnSeries(normalized);
    }

    /**
     * Вычисляет ln(1+x) где x близко к нулю
     */
    private double lnSeries(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Series requires x > 0");
        }

        // Сдвигаем x для использования ряда ln(1+u) = u - u^2/2 + u^3/3 - ...
        double u = (x - 1) / (x + 1);
        double uSquared = u * u;
        
        double result = 0;
        double term = u;
        int n = 1;

        while (Math.abs(term) > precision) {
            result += term / n;
            term *= uSquared;
            n += 2;
        }

        return 2 * result;
    }
}
