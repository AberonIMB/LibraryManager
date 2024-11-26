import org.example.util.SyntaxChecker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тестирует методы SyntaxChecker
 */
public class SyntaxCheckerTest {
    private final SyntaxChecker syntaxChecker = new SyntaxChecker();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * Перенаправляем вывод в отдельный поток
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Возвращаем вывод в стандартный поток
     */
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    /**
     * Проверяет корректность работы метода checkAddBookCommandSyntax с правильными данными
     */
    @Test
    public void testCheckAddBookCommandSyntaxCorrect() {
        String[] command = new String[]{"add-book", "title", "author", "2014"};
        assertCorrectCommand(syntaxChecker.checkAddBookCommandSyntax(command));
    }

    /**
     * Проверяет корректность работы метода checkAddBookCommandSyntax с неверным количетсвом аргументов
     */
    @Test
    public void testCheckAddBookCommandSyntaxInvalidArgsCount() {
        String[] command = new String[]{"add-book", "title", "author"};
        boolean answer = syntaxChecker.checkAddBookCommandSyntax(command);

        assertInvalidArgsCountCommand(answer, 3, command);
    }

    /**
     * Проверяет корректность работы метода checkAddBookCommandSyntax с неверным типом аргумента
     */
    @Test
    public void testCheckAddBookCommandSyntaxYearNotNumber() {
        String[] command = new String[]{"add-book", "title", "author", "year"};

        assertIdOrYearNotNumberCommand(syntaxChecker.checkAddBookCommandSyntax(command),
                "Год публикации должен быть представлен числом.");
    }

    /**
     * Проверяет корректность работы метода checkEditBookCommandSyntax с правильными данными
     */
    @Test
    public void testCheckEditBookCommandSyntaxCorrect() {
        String[] command = new String[]{"edit-book", "1", "title", "author", "2014"};

        assertCorrectCommand(syntaxChecker.checkEditBookCommandSyntax(command));
    }

    /**
     * Проверяет корректность работы метода checkEditBookCommandSyntax с неверным количетсвом аргументов
     */
    @Test
    public void testCheckEditBookCommandSyntaxInvalidArgsCount() {
        String[] command = new String[]{"edit-book", "1", "title", "author"};
        boolean answer = syntaxChecker.checkEditBookCommandSyntax(command);

        assertInvalidArgsCountCommand(answer, 4, command);
    }

    /**
     * Проверяет корректность работы метода checkEditBookCommandSyntax с неверным типом ID
     */
    @Test
    public void testCheckEditBookCommandSyntaxIdNotNumber() {
        String[] command = new String[]{"edit-book", "id", "title", "author", "2020"};

        assertIdOrYearNotNumberCommand(syntaxChecker.checkEditBookCommandSyntax(command),
                "ID должен быть представлен числом.");
    }

    /**
     * Проверяет корректность работы метода checkEditBookCommandSyntax с неверным типом года публикации
     */
    @Test
    public void testCheckEditBookCommandSyntaxYearNotNumber() {
        String[] command = new String[]{"edit-book", "1", "title", "author", "year"};

        assertIdOrYearNotNumberCommand(syntaxChecker.checkEditBookCommandSyntax(command),
                "Год публикации должен быть представлен числом.");
    }

    /**
     * Проверяет корректность работы метода checkDeleteBookCommandSyntax с правильными данными
     */
    @Test
    public void testCheckDeleteBookCommandSyntaxCorrect() {
        String[] command = new String[]{"delete-book", "1"};
        boolean answer = syntaxChecker.checkDeleteBookCommandSyntax(command);

        assertCorrectCommand(answer);
    }

    /**
     * Проверяет корректность работы метода checkDeleteBookCommandSyntax с неверным количетсвом аргументов
     */
    @Test
    public void testCheckDeleteBookCommandSyntaxInvalidArgsCount() {
        String[] command = new String[]{"delete-book", "book", "title"};
        boolean answer = syntaxChecker.checkDeleteBookCommandSyntax(command);

        assertInvalidArgsCountCommand(answer, 1, command);
    }

    /**
     * Проверяет корректность работы метода checkDeleteBookCommandSyntax с неверным типом аргумента
     */
    @Test
    public void testCheckDeleteBookCommandSyntaxIdNotNumber() {
        String[] command = new String[]{"delete-book", "book"};

        assertIdOrYearNotNumberCommand(syntaxChecker.checkDeleteBookCommandSyntax(command),
                "ID должен быть представлен числом.");
    }

    /**
     * Проверяет, что команда является правильной и вывод пустой строкой
     * @param answer результат проверки команды(должен быть true)
     */
    private void assertCorrectCommand(boolean answer) {
        Assertions.assertTrue(answer);
        Assertions.assertEquals("", outputStreamCaptor.toString());
    }

    /**
     * Проверяет, что команда имеет неверное количество аргументов и выводит правильное сообщение об ошибке
     * @param answer результат проверки команды(должен быть false)
     * @param expectedCount ожидаемое число аргументов
     * @param command выполненная команда
     */
    private void assertInvalidArgsCountCommand(boolean answer, int expectedCount, String[] command) {
        Assertions.assertFalse(answer);
        Assertions.assertEquals(
                String.format("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                        expectedCount, command.length - 1),
                outputStreamCaptor.toString().trim());
    }

    /**
     * Проверяет, что аргумент команды имеет неверный тип и выводит правильное сообщение об ошибке
     * @param answer результат проверки команды(должен быть false)
     */
    private void assertIdOrYearNotNumberCommand(boolean answer, String outputMessage) {
        Assertions.assertFalse(answer);
        Assertions.assertEquals(outputMessage, outputStreamCaptor.toString().trim());
    }
}