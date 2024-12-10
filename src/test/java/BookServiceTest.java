import org.example.dao.BookDAO;
import org.example.model.Book;
import org.example.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * Тестирует методы BookService
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookDAO bookDAOMock;

    private final BookService bookService;

    /**
     * Конструктор для инициализации bookService и bookDAOMock
     */
    public BookServiceTest(@Mock BookDAO bookDAOMock) {
        this.bookDAOMock = bookDAOMock;
        bookService = new BookService(bookDAOMock);
    }

    /**
     * Проверяет корректность добавления книги в библиотеку
     */
    @Test
    public void addBookTest() {
        Book book = new Book("Анна Каренина", "Лев Толстой", 1877);
        bookService.addBook(book);
        Mockito.verify(bookDAOMock, Mockito.times(1))
                .save(Mockito.any(Book.class));
    }

    /**
     * Проверяет корректность получения списка книг
     */
    @Test
    public void getListBooksTest() {
        Book book1 = new Book("Война и мир", "Лев Толстой", 1869);
        Book book2 = new Book("Преступление и наказание", "Фёдор Достоевский", 1866);
        Mockito.when(bookDAOMock.getAll()).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getListBooks();
        Assertions.assertEquals(2, books.size());
        Mockito.verify(bookDAOMock, Mockito.times(1)).getAll();
        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, books.get(0));
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, books.get(1));
    }

    /**
     * Проверяет корректность испраления данных книги и количество вызовов getById и update у bookDAO
     */
    @Test
    public void editBookTest() {
        Book book = new Book("Война и мир", "Лев Толстой", 1896);
        bookService.editBook(book, "Преступление и наказание", "Фёдор Достоевский", 1866);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
        Mockito.verify(bookDAOMock, Mockito.times(1)).update(Mockito.eq(book));
    }

    /**
     * Проверяет корректность удаления книги
     */
    @Test
    public void deleteBookInvokesDelete() {
        Book book = new Book("Война и мир", "Лев Толстой", 1869);
        bookService.deleteBook(book);
        Mockito.verify(bookDAOMock, Mockito.times(1)).deleteBook(book);
    }

    /**
     * Проверяет отсутствие вызова deleteBook у bookDAO, если при вызове deleteBook у bookService указан отсутсвующий ID
     */
    @Test
    public void deleteNotAddedBookNotInvokesDelete() {
        bookService.deleteBook(null);
        Mockito.verify(bookDAOMock, Mockito.never()).deleteBook(Mockito.any(Book.class));
    }

    /**
     * Проверяет корректность получения добавленной книги по id
     */
    @Test
    public void getBookTest() {
        Book book = new Book("Война и мир", "Лев Толстой", 1869);
        Mockito.when(bookDAOMock.getById(1L)).thenReturn(book);
        Book result = bookService.getBook(1L);
        Assertions.assertNotNull(result);
        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, result);
        Mockito.verify(bookDAOMock, Mockito.times(1)).getById(1L);
    }

    /**
     * Проверяет корректность получения не добавленной книги по ID или книги или с неверным ID
     */
    @Test
    public void getNotAddedBook() {
        Mockito.when(bookDAOMock.getById(1L)).thenReturn(null);
        Book result = bookService.getBook(1L);
        Assertions.assertNull(result);
        Mockito.verify(bookDAOMock, Mockito.times(1)).getById(1L);
    }

    /**
     * Проверяет, что метод save у bookDAO вызывается дважды для объектов с одинаковыми данными
     */
    @Test
    public void addMultipleBooksWithSameDataTest() {
        Book book = new Book("Преступление и наказание", "Фёдор Достоевский", 1866);
        bookService.addBook(book);
        bookService.addBook(book);
        Mockito.verify(bookDAOMock, Mockito.times(2)).save(Mockito.any(Book.class));
    }

    /**
     * Проверяет, что свойства книги совпадают с ожидаемыми значениями
     *
     * @param title  ожидаемое название
     * @param author ожидаемый автор
     * @param year   ожидаемый год публикации
     * @param book   сравниваемая книга
     */
    private void runAssertEqualsTest(String title, String author, int year, Book book) {
        Assertions.assertEquals(title, book.getTitle());
        Assertions.assertEquals(author, book.getAuthor());
        Assertions.assertEquals(year, book.getPublicationYear());
    }
}