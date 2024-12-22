package org.example.util;

/**
 * отвечает за ввод и вывод сообщений
 */
public interface IOHandler {
    /**
     * Вывести сообщение
     */
    void print(String message);

    /**
     * Вывести форматированное сообщение
     */
    void printFormatted(String message, Object... args);

    /**
     * Считать сообщение
     */
    String readInput();
}
