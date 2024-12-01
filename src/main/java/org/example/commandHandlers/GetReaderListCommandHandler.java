package org.example.commandHandlers;

import org.example.service.LibraryService;
import org.example.util.Printer;

/**
 * Обрабатывает команду получения списка читателей
 */
public class GetReaderListCommandHandler implements CommandHandler{

    private final Printer printer;

    /**
     * Конструктор
     * @param printer класс для вывода текста в консоль
     */
    public GetReaderListCommandHandler(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        printer.printReaderList(libraryService.getReaderList());
    }
}
