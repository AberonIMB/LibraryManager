import org.example.model.Book;
import org.example.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookServiceTest {

    private final BookService bookService = new BookService();

    /**
     * Перед каждым тестом добавляет одну книгу в библиотеку
     */
    @BeforeEach
    public void setUp() {
        bookService.addBook("Война и мир", "Лев Толстой", 1869);
    }

    /**
     * Отчищает библиотеку после каждого теста
     */
    @AfterEach
    public void cleanUp() {
        bookService.deleteBook(1);
        bookService.deleteBook(2);
    }

    /**
     * Проверяет корректность добавления книги в библиотеку
     */
    @Test
    public void addBookTest() {
        Book book = bookService.getBook(1);

        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book);
    }

    /**
     * Проверяет корректность получения списка книг
     */
    @Test
    public void getListBooksTest() {
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        List<Book> books = bookService.getListBooks();
        Book book1 = bookService.getBook(1);
        Book book2 = bookService.getBook(2);

        Assertions.assertEquals(2, books.size());

        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book1);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book2);
    }

    /**
     * Проверяет корректность испраления данных книги
     */
    @Test
    public void editBookTest() {
        bookService.editBook(1,
                "Преступление и наказание",
                "Фёдор Достоевский",
                1866);

        Book book = bookService.getBook(1);

        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
    }

    /**
     * Проверяет корректность удаления книги
     */
    @Test
    public void deleteBookTest() {
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        bookService.deleteBook(1);
        Assertions.assertEquals(1, bookService.getListBooks().size());

        Book book = bookService.getBook(2);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
    }

    /**
     * Проверяет корректность получения книги по id
     */
    @Test
    public void getBookTest() {
        Book book = bookService.getBook(1);
        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, book);
    }

    /**
     * Тест, который проверяет, что книги с одинаковыми данными получают разные идентификаторы
     */
    @Test
    public void addMultipleBooksWithSameDataTest() {
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);

        Assertions.assertNotEquals(
                bookService.getListBooks().get(1).getId(),
                bookService.getListBooks().get(2).getId());
    }

    /**
     * Проверяет корректность многочисленных изменений одной и той же книги
     */
    @Test
    public void multipleEditBookTest() {

        for (int i = 0; i < 5; i++) {
            bookService.editBook(1, "Книга " + i, "Автор " + i, i);
            Book book = bookService.getBook(1);
            runAssertEqualsTest("Книга " + i, "Автор " + i, i, book);
        }
    }

    /**
     * Проверяет, что свойства книги совпадают с ожидаемыми значениями
     * @param title
     * @param author
     * @param year
     * @param book
     */
    private void runAssertEqualsTest(String title, String author, int year, Book book) {
        Assertions.assertEquals(title, book.getTitle());
        Assertions.assertEquals(title, book.getAuthor());
        Assertions.assertEquals(year, book.getPublicationYear());
    }
}