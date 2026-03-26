package tpo.lab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvExporter {
    private final String separator;

    public CsvExporter(String separator) {
        this.separator = separator;
    }


    public void exportTrigonometric(Sin sin, Cos cos, Tan tan, Cot cot, Sec sec, Csc csc,
                                    double startX, double endX, double step, Path outputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("X" + separator + "sin(X)" + separator + "cos(X)" + separator + "tan(X)" + separator + 
                  "cot(X)" + separator + "sec(X)" + separator + "csc(X)");

        for (double x = startX; x <= endX; x += step) {
            try {
                double sinX = sin.calculate(x);
                double cosX = cos.calculate(x);
                double tanX = tan.calculate(x);
                double cotX = cot.calculate(x);
                double secX = sec.calculate(x);
                double cscX = csc.calculate(x);

                lines.add(String.format("%.6f%s%.6f%s%.6f%s%.6f%s%.6f%s%.6f%s%.6f",
                        x, separator, sinX, separator, cosX, separator, tanX, separator,
                        cotX, separator, secX, separator, cscX));
            } catch (Exception e) {
                // Пропускаем точки, где функция не определена
                lines.add(String.format("%.6f%s%s", x, separator, "undefined"));
            }
        }

        Files.write(outputPath, lines);
    }


    public void exportLogarithmic(Log log, Ln ln, double startX, double endX,
                                  double step, Path outputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("X" + separator + "log2(X)" + separator + "log3(X)" + separator + "log5(X)" + separator + "ln(X)");

        for (double x = startX; x <= endX; x += step) {
            try {
                double log2X = log.calculate(x, 2);
                double log3X = log.calculate(x, 3);
                double log5X = log.calculate(x, 5);
                double lnX = ln.calculate(x);

                lines.add(String.format("%.6f%s%.6f%s%.6f%s%.6f%s%.6f",
                        x, separator, log2X, separator, log3X, separator, log5X, separator, lnX));
            } catch (Exception e) {
                lines.add(String.format("%.6f%s%s", x, separator, "undefined"));
            }
        }

        Files.write(outputPath, lines);
    }


    public void exportSystemFunction(SystemFunction systemFunction, double startX, double endX, double step, Path outputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("X" + separator + "System(X)");

        for (double x = startX; x <= endX; x += step) {
            try {
                double result = systemFunction.calculate(x);
                lines.add(String.format("%.6f%s%.6f", x, separator, result));
            } catch (Exception e) {
                lines.add(String.format("%.6f%s%s", x, separator, "undefined"));
            }
        }

        Files.write(outputPath, lines);
    }
}
