import org.example.model.Book;
import org.example.model.Reader;
import org.example.service.BookService;
import org.example.service.LibraryService;
import org.example.service.ReaderService;
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

    @Mock
    private ReaderService readerServiceMock;

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

        assertBooksEquals(result, title, author, year, null);

        Mockito.verify(bookServiceMock, Mockito.times(1)).addBook(result);
    }

    /**
     * Проверяет корректность редактирования существующей книги
     */
    @Test
    public void testEditBook() {
        String newTitle = "newTitle";
        String newAuthor = "newAuthor";
        int newYear = 2025;

        Book existingBook = new Book("Title", "Author", 2023);

        libraryService.editBook(existingBook, newTitle, newAuthor, newYear);

        assertBooksEquals(existingBook, newTitle, newAuthor, newYear, null);

        Mockito.verify(bookServiceMock, Mockito.times(1)).editBook(existingBook);
    }

    /**
     * Проверяет корректность удаления существующей книги
     */
    @Test
    public void testDeleteBook() {
        Book book = new Book("Title", "Author", 2020);

        libraryService.deleteBook(book);

        Mockito.verify(bookServiceMock, Mockito.times(1)).deleteBook(book);
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
     * Проверяет корректность добавления нового читателя
     */
    @Test
    public void testAddReader() {
        String name = "Name";

        Reader reader = libraryService.addReader(name);

        assertReaderEquals(reader, name, List.of());

        Mockito.verify(readerServiceMock, Mockito.times(1)).addReader(reader);
    }

    /**
     * Проверяет корректность редактирования читателя
     */
    @Test
    public void testEditReader() {
        Reader reader = new Reader("Name");
        String newName = "newName";

        libraryService.editReader(reader, newName);

        assertReaderEquals(reader, newName, List.of());

        Mockito.verify(readerServiceMock, Mockito.times(1)).editReader(reader);
    }

    /**
     * Проверяет корректность удаления читателя
     */
    @Test
    public void testDeleteReader() {
        Reader reader = new Reader("Name");

        libraryService.deleteReader(reader);

        Mockito.verify(readerServiceMock, Mockito.times(1)).deleteReader(reader);
    }

    /**
     * Проверяет корректность получения списка читателей
     */
    @Test
    public void testGetListReaders() {
        List<Reader> readers = List.of(
                new Reader("Name1"),
                new Reader("Name2")
        );

        Mockito.when(readerServiceMock.getListReaders()).thenReturn(readers);

        List<Reader> result = libraryService.getListReaders();

        Assertions.assertEquals(readers, result);
        Mockito.verify(readerServiceMock, Mockito.times(1)).getListReaders();
    }

    /**
     * Проверяет корректность выдачи книги
     */
    @Test
    public void testCheckoutBook() {
        Book book = new Book("Title", "Author", 2020);
        Reader reader = new Reader("Name");

        libraryService.checkoutBook(book, reader);

        assertBooksEquals(book, "Title", "Author", 2020, reader);

        Mockito.verify(bookServiceMock, Mockito.times(1)).editBook(book);
    }

    /**
     * Проверяет корректность возврата книги
     */
    @Test
    public void testReturnBook() {
        Book book = new Book("Title", "Author", 2020);
        Reader reader = new Reader();
        book.setReader(reader);

        libraryService.returnBook(book);

        assertBooksEquals(book, "Title", "Author", 2020, null);

        Mockito.verify(bookServiceMock, Mockito.times(1)).editBook(book);
    }

    /**
     * Проверяет, что книга совпадает с ожидаемым значением
     */
    private void assertBooksEquals(Book result, String title, String author, int year, Reader reader) {
        Assertions.assertNotNull(result);
        Assertions.assertEquals(title, result.getTitle());
        Assertions.assertEquals(author, result.getAuthor());
        Assertions.assertEquals(year, result.getPublicationYear());
        Assertions.assertEquals(reader, result.getReader());
    }

    /**
     * Проверяет, что читатель совпадает с ожидаемым значением
     */
    private void assertReaderEquals(Reader result, String name, List<Book> books) {
        Assertions.assertNotNull(result);
        Assertions.assertEquals(name, result.getName());
        Assertions.assertEquals(books, result.getBooks());
    }
}