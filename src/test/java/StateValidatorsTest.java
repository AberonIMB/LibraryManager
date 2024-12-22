import org.example.exceptions.stateExceptions.*;
import org.example.model.Book;
import org.example.model.Reader;
import org.example.stateValidators.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для Валидаторов состояния StateValidators
 */
public class StateValidatorsTest {

    private final CheckoutStateValidator checkoutStateValidator = new CheckoutStateValidator();
    private final DeleteBookStateValidator deleteBookStateValidator = new DeleteBookStateValidator();
    private final DeleteReaderStateValidator deleteReaderStateValidator = new DeleteReaderStateValidator();
    private final EditBookStateValidator editBookStateValidator = new EditBookStateValidator();
    private final ReaderNotNullStateValidator readerNotNullStateValidator = new ReaderNotNullStateValidator();
    private final ReturnStateValidator returnStateValidator = new ReturnStateValidator();

    private final Book book = new Book("title", "author", 2023);
    private final Reader reader = new Reader("name");

    /**
     * Удаляет читателя у кнги
     */
    @BeforeEach
    public void setUp() {
        book.setReader(null);
    }

    /**
     * Проверяет корректность работы валидаторов состояния при корректных данных
     */
    @Test
    public void testValidateCorrectState() {
        Assertions.assertDoesNotThrow(() -> checkoutStateValidator.validateState(1L, 1L, book, reader));

        Assertions.assertDoesNotThrow(() -> deleteBookStateValidator.validateState(1L, book));

        Assertions.assertDoesNotThrow(() -> deleteReaderStateValidator.validateState(1L, reader));

        Assertions.assertDoesNotThrow(() -> editBookStateValidator.validateState(1L, book));

        Assertions.assertDoesNotThrow(() -> readerNotNullStateValidator.validateState(1L, reader));

        book.setReader(reader);
        Assertions.assertDoesNotThrow(() -> returnStateValidator.validateState(1L, book));
    }

    /**
     * Проверяет корректность работы валидатора checkoutStateValidator при выдаче несуществующией книги
     */
    @Test
    public void testValidateStateForCheckoutWithNullBook() {
        BookIsNullException e = Assertions.assertThrows(BookIsNullException.class,
                () -> checkoutStateValidator.validateState(1L, 1L, null, reader));

        Assertions.assertEquals("Книга с ID 1 не найдена.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора checkoutStateValidator при выдаче книги несуществующему читателю
     */
    @Test
    public void testValidateStateForCheckoutWithNullReader() {
        ReaderIsNullException e = Assertions.assertThrows(ReaderIsNullException.class,
                () -> checkoutStateValidator.validateState(1L, 1L, book, null));

        Assertions.assertEquals("Читатель с ID 1 не найден.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора checkoutStateValidator при выдаче книги, которая уже выдана
     */
    @Test
    public void testValidateStateForCheckoutWhenBookAlreadyCheckedOut() {
        book.setReader(reader);
        BookAlreadyCheckedOutException e = Assertions.assertThrows(BookAlreadyCheckedOutException.class,
                () -> checkoutStateValidator.validateState(1L, 1L, book, reader));

        Assertions.assertEquals("Невозможно выполнить операцию, так как книга \"title\" выдана читателю ID: null ФИО: name.",
                e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора deleteBookStateValidator при удалении несуществующей книги
     */
    @Test
    public void testValidateStateForDeleteBookWithNullBook() {
        BookIsNullException e = Assertions.assertThrows(BookIsNullException.class,
                () -> deleteBookStateValidator.validateState(1L, null));

        Assertions.assertEquals("Книга с ID 1 не найдена.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора deleteBookStateValidator при удалении книги, которая выдана
     */
    @Test
    public void testValidateStateForDeleteBookWithReader() {
        book.setReader(reader);
        BookAlreadyCheckedOutException e = Assertions.assertThrows(BookAlreadyCheckedOutException.class,
                () -> deleteBookStateValidator.validateState(1L, book));

        Assertions.assertEquals("Невозможно выполнить операцию, так как книга \"title\" выдана читателю ID: null ФИО: name.",
                e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора deleteReaderStateValidator при удалении несуществующего читателя
     */
    @Test
    public void testValidateStateForDeleteReaderWithNullReader() {
        ReaderIsNullException e = Assertions.assertThrows(ReaderIsNullException.class,
                () -> deleteReaderStateValidator.validateState(1L, null));

        Assertions.assertEquals("Читатель с ID 1 не найден.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора deleteReaderStateValidator при удалении читателя с книгами
     */
    @Test
    public void testValidateStateForDeleteReaderWithNotEmptyBookList() {
        reader.getBooks().add(book);
        DeleteReaderWithNotEmptyBookListException e = Assertions.assertThrows(DeleteReaderWithNotEmptyBookListException.class,
                () -> deleteReaderStateValidator.validateState(1L, reader));

        Assertions.assertEquals("Невозможно удалить читателя ID: null ФИО: name, так как у него есть выданные книги.",
                e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора editBookStateValidator при редактировании несуществующей книги
     */
    @Test
    public void testValidateStateForEditBookWithNullBook() {
        BookIsNullException e = Assertions.assertThrows(BookIsNullException.class,
                () -> editBookStateValidator.validateState(1L, null));

        Assertions.assertEquals("Книга с ID 1 не найдена.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора readerNotNullStateValidator с несуществующим читателем
     */
    @Test
    public void testValidateStateForReaderNotNullWithNullReader() {
        ReaderIsNullException e = Assertions.assertThrows(ReaderIsNullException.class,
                () -> readerNotNullStateValidator.validateState(1L, null));

        Assertions.assertEquals("Читатель с ID 1 не найден.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора returnStateValidator при возврате несуществующей книги
     */
    @Test
    public void testValidateStateForReturnWithNullBook() {
        BookIsNullException e = Assertions.assertThrows(BookIsNullException.class,
                () -> returnStateValidator.validateState(1L, null));

        Assertions.assertEquals("Книга с ID 1 не найдена.", e.getMessage());
    }

    /**
     * Проверяет корректность работы валидатора returnStateValidator при возврате книги, которая уже находится в библиотеке
     */
    @Test
    public void testValidateStateForReturnBookWithoutReader() {
        BookAlreadyInLibraryException e = Assertions.assertThrows(BookAlreadyInLibraryException.class,
                () -> returnStateValidator.validateState(1L, book));

        Assertions.assertEquals("Невозможно выполнить операцию, так как книга \"title\" уже находится в библиотеке.",
                e.getMessage());
    }
}