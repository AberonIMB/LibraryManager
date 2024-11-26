package org.example.commandHandlers;

import org.example.service.LibraryService;
import org.example.util.Printer;

/**
 * Обрабатывает команду получения списка книг
 */
public class GetBookListCommandHandler implements CommandHandler {
    private final Printer printer;

    /**
     * Конструктор
     * @param printer класс для вывода текста в консоль
     */
    public GetBookListCommandHandler(Printer printer) {
        this.printer = printer;
    }

    /**
     * Выводит список книг
     * @param libraryService Сервис библиотеки
     * @param command название команды и параметры
     */
    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        printer.printBookList(libraryService.getListBooks());
    }
}
