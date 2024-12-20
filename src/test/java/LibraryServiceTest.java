import org.example.model.Book;
import org.example.service.BookService;
import org.example.service.LibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


/**
 * Класс для тестирования LibraryService
 */
@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookService bookServiceMock;

    @InjectMocks
    private LibraryService libraryService;

    /**
     * Проверяет корректность добавления новой книги
     */
    @Test
    public void testAddBook() {
        String title = "Title";
        String author = "Author";
        int year = 2023;

        Book result = libraryService.addBook(title, author, year);

        assertBooksEquals(result, title, author, year);

        Mockito.verify(bookServiceMock, Mockito.times(1)).addBook(result);
    }

    /**
     * Проверяет корректность редактирования существующей книги
     */
    @Test
    public void testEditBook() {
        Long bookId = 1L;
        String newTitle = "newTitle";
        String newAuthor = "newAuthor";
        int newYear = 2025;

        Book existingBook = new Book("Title", "Author", 2023);

        Mockito.when(bookServiceMock.getBook(bookId)).thenReturn(existingBook);

        Book result = libraryService.editBook(bookId, newTitle, newAuthor, newYear);

        assertBooksEquals(result, newTitle, newAuthor, newYear);

        Mockito.verify(bookServiceMock, Mockito.times(1)).editBook(existingBook);
    }

    /**
     * Проверяет корректность редактирования несуществующей книги
     */
    @Test
    public void testEditNullBook() {
        Long bookId = 1L;
        String newTitle = "newTitle";
        String newAuthor = "newAuthor";
        int newYear = 2025;

        Mockito.when(bookServiceMock.getBook(bookId)).thenReturn(null);

        Book result = libraryService.editBook(bookId, newTitle, newAuthor, newYear);

        Assertions.assertNull(result);

        Mockito.verify(bookServiceMock, Mockito.never()).editBook(Mockito.any(Book.class));
    }

    /**
     * Проверяет корректность удаления существующей книги
     */
    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Book book = new Book("Title", "Author", 2020);

        Mockito.when(bookServiceMock.getBook(bookId)).thenReturn(book);

        Book result = libraryService.deleteBook(bookId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book, result);

        Mockito.verify(bookServiceMock, Mockito.times(1)).deleteBook(book);
    }

    /**
     * Проверяет корректность удаления несуществующей книги
     */
    @Test
    public void testDeleteNullBook() {
        Long bookId = 1L;
        Mockito.when(bookServiceMock.getBook(bookId)).thenReturn(null);

        Book result = libraryService.deleteBook(bookId);

        Assertions.assertNull(result);

        Mockito.verify(bookServiceMock, Mockito.never()).deleteBook(Mockito.any(Book.class));
    }

    /**
     * Проверяет корректность получения списка книг
     */
    @Test
    public void testGetListBooks() {
        List<Book> books = List.of(
                new Book("Title1", "Author1", 2000),
                new Book("Title2", "Author2", 2010)
        );

        Mockito.when(bookServiceMock.getListBooks()).thenReturn(books);

        List<Book> result = libraryService.getListBooks();

        Assertions.assertEquals(books, result);
        Mockito.verify(bookServiceMock, Mockito.times(1)).getListBooks();
    }

    /**
     * Проверяет, что книга совпадает с ожидаемым значением
     */
    private void assertBooksEquals(Book result, String title, String author, int year) {
        Assertions.assertNotNull(result);
        Assertions.assertEquals(title, result.getTitle());
        Assertions.assertEquals(author, result.getAuthor());
        Assertions.assertEquals(year, result.getPublicationYear());
    }
}