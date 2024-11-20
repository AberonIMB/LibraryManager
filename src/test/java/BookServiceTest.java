import org.example.model.Book;
import org.example.DAO.BookDAO;
import org.example.service.BookService;
import org.example.util.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import java.util.Arrays;
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
        bookService = new BookService(bookDAOMock, new Printer());
    }

    /**
     * Проверяет корректность добавления книги в библиотеку
     */
    @Test
    public void addBookTest() {
        Mockito.doNothing().when(bookDAOMock).save(Mockito.any(Book.class));
        bookService.addBook("Анна Каренина", "Лев Толстой", 1877);
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
        Mockito.when(bookDAOMock.getAll()).thenReturn(Arrays.asList(book1, book2));
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

        Book book = new Book("Преступление и наказание", "Фёдор Достоевский", 866);
        Mockito.doNothing().when(bookDAOMock).update(book);
        Mockito.when(bookDAOMock.getById(1)).thenReturn(book);
        bookService.editBook(1, "Преступление и наказание", "Фёдор Достоевский", 1866);
        runAssertEqualsTest("Преступление и наказание", "Фёдор Достоевский", 1866, book);
        Mockito.verify(bookDAOMock, Mockito.times(1)).getById(1);
        Mockito.verify(bookDAOMock, Mockito.times(1)).update(Mockito.eq(book));
    }

    /**
     * Проверяет корректность удаления книги
     */
    @Test
    public void deleteBookInvokesDelete() {
        Book book = new Book("Война и мир", "Лев Толстой", 1869);
        Mockito.doNothing().when(bookDAOMock).deleteBook(Mockito.any(Integer.class));
        Mockito.when(bookDAOMock.getById(1)).thenReturn(book);
        bookService.deleteBook(1);
        Mockito.verify(bookDAOMock, Mockito.times(1)).deleteBook(1);
    }

    /**
     * Проверяет отсутствие вызова deleteBook у bookDAO, если при вызове deleteBook у bookService указан отсутсвующий ID
     */
    @Test
    public void deleteNotAddedBookNotInvokesDelete() {
        Mockito.when(bookDAOMock.getById(1)).thenReturn(null);
        bookService.deleteBook(1);
        Mockito.verify(bookDAOMock, Mockito.never()).deleteBook(1);
    }

    /**
     * Проверяет корректность получения добавленной книги по id
     */
    @Test
    public void getBookTest() {
        Book book = new Book("Война и мир", "Лев Толстой", 1869);
        Mockito.when(bookDAOMock.getById(1)).thenReturn(book);
        Book result = bookService.getBook(1);
        Assertions.assertNotNull(result);
        runAssertEqualsTest("Война и мир", "Лев Толстой", 1869, result);
        Mockito.verify(bookDAOMock, Mockito.times(1)).getById(1);
    }

    /**
     * Проверяет корректность получения не добавленной книги по ID или книги или с неверным ID
     */
    @Test
    public void getNotAddedBook() {
        Mockito.when(bookDAOMock.getById(1)).thenReturn(null);
        Book result = bookService.getBook(1);
        Assertions.assertNull(result);
        Mockito.verify(bookDAOMock, Mockito.times(1)).getById(1);
    }

    /**
     * Проверяет, что метод save у bookDAO вызывается дважды для объектов с одинаковыми данными
     */
    @Test
    public void addMultipleBooksWithSameDataTest() {
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);
        bookService.addBook("Преступление и наказание", "Фёдор Достоевский", 1866);
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