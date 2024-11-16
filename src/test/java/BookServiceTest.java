import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.Printer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookServiceTest {

    private final LibraryService libraryService = new LibraryService(new Printer());

    /**
     * Перед каждым тестом добавляет одну книгу в библиотеку
     */
    @BeforeEach
    public void setUp() {
        libraryService.addBook("Война и мир", "Лев Толстой", 1869);
    }

    /**
     * Очищает библиотеку после каждого теста
     */
    @AfterEach
    public void cleanUp() {
        libraryService.deleteBook(1);
        libraryService.deleteBook(2);
    }

    /**
     * Проверяет корректность добавления книги в библиотеку
     */
    @Test
    public void addBookTest() {
        Book book = libraryService.getBook(1);

        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book);
    }

    /**
     * Проверяет корректность получения списка книг
     */
    @Test
    public void getListBooksTest() {
        libraryService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        List<Book> books = libraryService.getListBooks();
        Book book1 = libraryService.getBook(1);
        Book book2 = libraryService.getBook(2);

        Assertions.assertEquals(2, books.size());

        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book1);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book2);
    }

    /**
     * Проверяет корректность испраления данных книги
     */
    @Test
    public void editBookTest() {
        libraryService.editBook(1,
                "Преступление и наказание",
                "Фёдор Достоевский",
                1866);

        Book book = libraryService.getBook(1);

        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
    }

    /**
     * Проверяет корректность удаления книги
     */
    @Test
    public void deleteBookTest() {
        libraryService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        libraryService.deleteBook(1);
        Assertions.assertEquals(1, libraryService.getListBooks().size());

        Book book = libraryService.getBook(2);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
    }

    /**
     * Проверяет корректность получения книги по id
     */
    @Test
    public void getBookTest() {
        Book book = libraryService.getBook(1);
        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book);
    }

    /**
     * Тест, который проверяет, что книги с одинаковыми данными получают разные идентификаторы
     */
    @Test
    public void addMultipleBooksWithSameDataTest() {
        libraryService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);
        libraryService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        Assertions.assertNotEquals(
                libraryService.getListBooks().get(1).getId(),
                libraryService.getListBooks().get(2).getId());
    }

    /**
     * Проверяет корректность многочисленных изменений одной и той же книги
     */
    @Test
    public void multipleEditBookTest() {

        for (int i = 0; i < 5; i++) {
            libraryService.editBook(1, "Книга " + i, "Автор " + i, i);
            Book book = libraryService.getBook(1);
            runAssertEqualsTest("Книга " + i, "Автор " + i, i, book);
        }
    }

    /**
     * Проверяет, что свойства книги совпадают с ожидаемыми значениями
     * @param title ожидаемое название
     * @param author ожидаемый автор
     * @param year ожидаемый год публикации
     * @param book сравниваемая книга
     */
    private void runAssertEqualsTest(String title, String author, int year, Book book) {
        Assertions.assertEquals(title, book.getTitle());
        Assertions.assertEquals(author, book.getAuthor());
        Assertions.assertEquals(year, book.getPublicationYear());
    }
}