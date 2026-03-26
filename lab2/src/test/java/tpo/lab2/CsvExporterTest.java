package tpo.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для CSV экспортера")
class CsvExporterTest {
    private CsvExporter exporter;
    private Sin sin;
    private Cos cos;
    private Tan tan;
    private Cot cot;
    private Sec sec;
    private Csc csc;
    private Ln ln;
    private Log log;
    private SystemFunction systemFunction;

    @BeforeEach
    void setUp() {
        exporter = new CsvExporter(",");
        sin = new Sin(1e-6);
        cos = new Cos(sin);
        tan = new Tan(sin, cos);
        cot = new Cot(sin, cos);
        sec = new Sec(cos);
        csc = new Csc(sin);
        ln = new Ln(1e-6);
        log = new Log(ln);
        systemFunction = new SystemFunction(sin, cos, tan, cot, sec, csc, log, ln);
    }

    @Test
    @DisplayName("Экспорт тригонометрических функций в CSV")
    void testExportTrigonometric(@TempDir Path tempDir) throws Exception {
        Path outputPath = tempDir.resolve("trig.csv");
        exporter.exportTrigonometric(sin, cos, tan, cot, sec, csc,
                0, 1, 0.25, outputPath);

        assertTrue(Files.exists(outputPath), "Файл должен быть создан");
        
        List<String> lines = Files.readAllLines(outputPath);
        assertFalse(lines.isEmpty(), "Файл не должен быть пустым");
        
        // Проверяем заголовок
        String header = lines.get(0);
        assertTrue(header.contains("X"), "Заголовок должен содержать 'X'");
        assertTrue(header.contains("sin(X)"), "Заголовок должен содержать 'sin(X)'");
        assertTrue(header.contains("cos(X)"), "Заголовок должен содержать 'cos(X)'");
        
        // x от 0 до 1 с шагом 0.25: x = 0, 0.25, 0.5, 0.75, 1.0 => 5 данных + 1 заголовок = 6
        assertEquals(6, lines.size(), "Должно быть 6 строк (заголовок + 5 данных)");
    }

    @Test
    @DisplayName("Экспорт логарифмических функций в CSV")
    void testExportLogarithmic(@TempDir Path tempDir) throws Exception {
        Path outputPath = tempDir.resolve("log.csv");
        exporter.exportLogarithmic(log, ln, 1, 4, 1, outputPath);

        assertTrue(Files.exists(outputPath), "Файл должен быть создан");
        
        List<String> lines = Files.readAllLines(outputPath);
        assertFalse(lines.isEmpty(), "Файл не должен быть пустым");
        
        // Проверяем заголовок
        String header = lines.get(0);
        assertTrue(header.contains("X"), "Заголовок должен содержать 'X'");
        assertTrue(header.contains("log2(X)"), "Заголовок должен содержать 'log2(X)'");
        assertTrue(header.contains("log3(X)"), "Заголовок должен содержать 'log3(X)'");
        assertTrue(header.contains("ln(X)"), "Заголовок должен содержать 'ln(X)'");
        
        // Проверяем количество строк
        assertEquals(5, lines.size(), "Должно быть 5 строк (заголовок + 4 данные)");
    }

    @Test
    @DisplayName("Экспорт системной функции в CSV")
    void testExportSystemFunction(@TempDir Path tempDir) throws Exception {
        Path outputPath = tempDir.resolve("system.csv");
        exporter.exportSystemFunction(systemFunction, 0.5, 3, 0.5, outputPath);

        assertTrue(Files.exists(outputPath), "Файл должен быть создан");
        
        List<String> lines = Files.readAllLines(outputPath);
        assertFalse(lines.isEmpty(), "Файл не должен быть пустым");
        
        // Проверяем заголовок
        String header = lines.get(0);
        assertTrue(header.contains("X"), "Заголовок должен содержать 'X'");
        assertTrue(header.contains("System(X)"), "Заголовок должен содержать 'System(X)'");
        
        // x от 0.5 до 3.0 с шагом 0.5: 6 точек + 1 заголовок = 7
        assertEquals(7, lines.size(), "Должно быть 7 строк (заголовок + 6 данных)");
    }

    @Test
    @DisplayName("CSV использует правильный разделитель")
    void testCsvSeparator(@TempDir Path tempDir) throws Exception {
        String separator = ";";
        CsvExporter customExporter = new CsvExporter(separator);
        Path outputPath = tempDir.resolve("custom.csv");
        
        customExporter.exportTrigonometric(sin, cos, tan, cot, sec, csc,
                0, 0.5, 0.5, outputPath);

        List<String> lines = Files.readAllLines(outputPath);
        String header = lines.get(0);
        
        assertTrue(header.contains(separator), "Заголовок должен использовать пользовательский разделитель");
    }

    @Test
    @DisplayName("CSV правильно форматирует числовые значения")
    void testCsvNumberFormatting(@TempDir Path tempDir) throws Exception {
        Path outputPath = tempDir.resolve("format.csv");
        // Используем x=0.5, где все тригонометрические функции определены
        exporter.exportTrigonometric(sin, cos, tan, cot, sec, csc,
                0.5, 1.0, 0.5, outputPath);

        List<String> lines = Files.readAllLines(outputPath);
        String dataLine = lines.get(1);
        
        // Проверяем, что данные содержат числовые значения
        String[] parts = dataLine.split(",");
        assertEquals(7, parts.length, "Строка должна содержать 7 полей");
        
        // Первое поле - это X значение
        double xValue = Double.parseDouble(parts[0]);
        assertTrue(xValue >= 0, "X должно быть >= 0");
    }

    @Test
    @DisplayName("CSV экспорт обрабатывает неопределенные значения")
    void testCsvUndefinedValues(@TempDir Path tempDir) throws Exception {
        Path outputPath = tempDir.resolve("undefined.csv");
        // Экспортируем в диапазон, где tan может быть неопределен
        exporter.exportTrigonometric(sin, cos, tan, cot, sec, csc,
                Math.PI / 2 - 0.1, Math.PI / 2 + 0.1, 0.05, outputPath);

        List<String> lines = Files.readAllLines(outputPath);
        
        // Файл должен быть создан несмотря на неопределенные значения
        assertTrue(Files.exists(outputPath), "Файл должен быть создан");
        assertTrue(lines.size() > 1, "Файл должен содержать данные");
    }

    @Test
    @DisplayName("CSV экспорт создает файл с правильным содержимым для разных шагов")
    void testCsvDifferentSteps(@TempDir Path tempDir) throws Exception {
        Path outputPath1 = tempDir.resolve("step01.csv");
        Path outputPath2 = tempDir.resolve("step02.csv");
        
        exporter.exportSystemFunction(systemFunction, 1, 5, 0.5, outputPath1);
        exporter.exportSystemFunction(systemFunction, 1, 5, 1, outputPath2);

        List<String> lines1 = Files.readAllLines(outputPath1);
        List<String> lines2 = Files.readAllLines(outputPath2);
        
        // С меньшим шагом должно быть больше данных
        assertTrue(lines1.size() > lines2.size(), 
                "Меньший шаг должен дать больше данных");
    }
}
