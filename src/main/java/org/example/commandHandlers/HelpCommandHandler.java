package org.example.commandHandlers;

import org.example.service.LibraryService;
import org.example.util.Printer;

/**
 * Обрабатывает команду help
 */
public class HelpCommandHandler implements CommandHandler {
    private final Printer printer;

    /**
     * Конструктор
     * @param printer класс для вывода текста в консоль
     */
    public HelpCommandHandler(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        printer.printHelp();
    }
}