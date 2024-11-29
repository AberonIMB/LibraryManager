import org.example.DAO.ReaderDAO;
import org.example.model.Book;
import org.example.model.Reader;
import org.example.service.ReaderService;
import org.example.util.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Тестирует методы readerService
 */
@ExtendWith(MockitoExtension.class)
public class ReaderServiceTest {
    private final ReaderDAO readerDAOMock;

    private final ReaderService readerService;

    /**
     * Конструктор для инициализации readerService и readerDAOMock
     */
    public ReaderServiceTest(@Mock ReaderDAO readerDAOMock) {
        this.readerDAOMock = readerDAOMock;
        readerService = new ReaderService(readerDAOMock, new Printer());
    }

    /**
     * Проверяет корректность добавления читателя в библиотеку
     */
    @Test
    public void addReaderTest() {
        Mockito.doNothing().when(readerDAOMock).save(Mockito.any(Reader.class));
        readerService.addReader("Читатель");
        Mockito.verify(readerDAOMock, Mockito.times(1))
                .save(Mockito.any(Reader.class));
    }

    /**
     * Проверяет корректность получения списка читателей
     */
    @Test
    public void getListReadersTest() {
        Reader reader1 = new Reader("Читатель - 1");
        Reader reader2 = new Reader("Читатель - 2");
        Mockito.when(readerDAOMock.getAll()).thenReturn(Arrays.asList(reader1, reader2));
        List<Reader> readers = readerService.getListReaders();
        Assertions.assertEquals(2, readers.size());
        Mockito.verify(readerDAOMock, Mockito.times(1)).getAll();
        runAssertEqualsTest("Читатель - 1", new ArrayList<>(), readers.get(0));
        runAssertEqualsTest("Читатель - 2", new ArrayList<>(), readers.get(1));
    }

    /**
     * Проверяет корректность исправления данных читателя и количество вызовов getById и update у readerDAO
     */
    @Test
    public void editReaderTest() {
        Reader reader = new Reader("Читатель - 0");
        Mockito.doNothing().when(readerDAOMock).update(reader);
        Mockito.when(readerDAOMock.getById(1L)).thenReturn(reader);
        readerService.editReader(1L, "Читатель - 1");
        runAssertEqualsTest("Читатель - 1", new ArrayList<>(), reader);
        Mockito.verify(readerDAOMock, Mockito.times(1)).getById(1L);
        Mockito.verify(readerDAOMock, Mockito.times(1)).update(Mockito.eq(reader));
    }

    /**
     * Проверяет корректность удаления читателя
     */
    @Test
    public void deleteReaderInvokesDelete() {
        Reader reader = new Reader("Читатель - 1");
        Mockito.doNothing().when(readerDAOMock).deleteReader(Mockito.any(Reader.class));
        Mockito.when(readerDAOMock.getById(1L)).thenReturn(reader);
        readerService.deleteReader(1L);
        Mockito.verify(readerDAOMock, Mockito.times(1)).deleteReader(reader);
    }

    /**
     * Проверяет отсутствие вызова deleteReader у readerDAO, если при вызове deleteReader у readerService указан отсутсвующий ID
     */
    @Test
    public void deleteNotAddedReaderNotInvokesDelete() {
        Mockito.when(readerDAOMock.getById(1L)).thenReturn(null);
        readerService.deleteReader(1L);
        Mockito.verify(readerDAOMock, Mockito.never()).deleteReader(Mockito.any(Reader.class));
    }

    /**
     * Проверяет корректность получения добавленной читателя по id
     */
    @Test
    public void getReaderTest() {
        Reader reader = new Reader("Читатель - 1");
        Mockito.when(readerDAOMock.getById(1L)).thenReturn(reader);
        Reader result = readerService.getReader(1L);
        Assertions.assertNotNull(result);
        runAssertEqualsTest("Читатель - 1", new ArrayList<>(), result);
        Mockito.verify(readerDAOMock, Mockito.times(1)).getById(1L);
    }

    /**
     * Проверяет корректность получения не добавленного читателя по ID или читателя с неверным ID
     */
    @Test
    public void getNotAddedReader() {
        Mockito.when(readerDAOMock.getById(1L)).thenReturn(null);
        Reader result = readerService.getReader(1L);
        Assertions.assertNull(result);
        Mockito.verify(readerDAOMock, Mockito.times(1)).getById(1L);
    }

    /**
     * Проверяет, что свойства читателя совпадают с ожидаемыми значениями
     */
    private void runAssertEqualsTest(String name, List<Book> books, Reader reader) {
        Assertions.assertEquals(name, reader.getName());
        Assertions.assertEquals(books, reader.getBooks());
    }

}
