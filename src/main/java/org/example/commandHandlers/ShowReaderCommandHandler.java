package org.example.commandHandlers;

import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.Printer;

/**
 * Обрабатывает команду просмотра читателя
 */
public class ShowReaderCommandHandler implements CommandHandler{

    private final Printer printer;

    public ShowReaderCommandHandler(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.showReader(Long.parseLong(command[1]));
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return true, если команда корректна, иначе false
     */
    private boolean isCommandCorrect(LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkCommandSyntaxWithIdOnly(command);
    }
}
