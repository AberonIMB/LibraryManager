package org.example.util;

import java.util.Scanner;

/**
 * обработчик ввода и вывода в консоль
 */
public class IOConsoleHandler implements IOHandler {
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printFormatted(String message, Object... args) {
        System.out.printf(message + "\n", args);
    }

    @Override
    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}