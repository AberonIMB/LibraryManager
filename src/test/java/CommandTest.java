import org.example.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Тестирует класс Command
 */
class CommandTest {

    /**
     * Проверяет, что команда корректно парсится, когда содержит только название.
     */
    @Test
    void shouldParseCommandWithOnlyName() {
        Command command = new Command("help");

        Assertions.assertEquals("help", command.getName(), "Имя команды должно быть: 'help'");
        Assertions.assertTrue(command.getParams().isEmpty(), "Список параметров должен быть пуст");
    }

    /**
     * Проверяет, что команда корректно парсится с названием и параметрами без кавычек
     */
    @Test
    void shouldParseCommandWithNameAndParams() {
        Command command = new Command("add-book param1 param2");

        Assertions.assertEquals("add-book", command.getName(),
                "Имя команды должно быть: 'add-book'");

        Assertions.assertEquals(List.of("param1", "param2"), command.getParams(),
                "Параметры должны совпадать");
    }

    /**
     * Проверяет корректную обработку команды с параметрами, содержащими кавычки
     */
    @Test
    void shouldParseCommandWithQuotedParams() {
        Command command = new Command("add-book \"Название\" \"Автор книги\"");

        Assertions.assertEquals("add-book", command.getName(), "Имя команды должно быть: 'add-book'");
        Assertions.assertEquals(List.of("Название", "Автор книги"), command.getParams(),
                "Параметры с кавычками должны парситься правильно");
    }

    /**
     * Проверяет корректную обработку команды, содержащей смешанные параметры (с кавычками и без них)
     */
    @Test
    void shouldParseCommandWithMixedParams() {
        Command command = new Command("edit-book 12345 \"Название\" \"Автор\" 12345");

        Assertions.assertEquals("edit-book", command.getName(),
                "Имя команды должно быть: 'edit-book'");

        Assertions.assertEquals(List.of("12345", "New Title", "12345"), command.getParams(),
                "Параметры команды должны включать параметры смешанных типов");
    }

    /**
     * Проверяет поведение для пустой строки команды
     */
    @Test
    void shouldHandleEmptyCommand() {
        Command command = new Command("");

        Assertions.assertNull(command.getName(), "Имя команды должно быть null при пустом вводе");
        Assertions.assertNull(command.getParams(), "Параметры команды должно быть null при пустом вводе");
    }

    /**
     * Проверяет поведение для строки, содержащей только пробелы
     */
    @Test
    void shouldHandleCommandWithOnlySpaces() {
        Command command = new Command(" ");

        Assertions.assertNull(command.getName(),
                "Имя команды должно быть null при вводе только пробелов");

        Assertions.assertNull(command.getParams(),
                "Параметры команды должно быть null при вводе только пробелов");
    }

    /**
     * Проверяет корректную обработку команды с параметрами, содержащими специальные символы
     */
    @Test
    void shouldParseCommandWithSpecialCharacters() {
        Command command = new Command("delete-book \"!@#$%^&*()\"");

        Assertions.assertEquals("delete-book", command.getName(),
                "Имя команды должно быть: 'delete-book'");

        Assertions.assertEquals(List.of("!@#$%^&*()"), command.getParams(),
                "Параметры команды должны включать спциальные символы");
    }
}