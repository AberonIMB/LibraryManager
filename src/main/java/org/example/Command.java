package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс команды
 */
public class Command {
    private String name;
    private List<String> params;

    /**
     * Конструктор, принимающий команду
     */
    public Command(String command) {
        List<String> args = parseCommand(command);

        if (!args.isEmpty()) {
            this.name = args.get(0);
            this.params = args.subList(1, args.size());
        }
    }

    /**
     * Получить название команды
     */
    public String getName() {
        return name;
    }

    /**
     * Получить параметры
     */
    public List<String> getParams() {
        return params;
    }

    /**
     * Разбивает команду на название и параметры
     */
    private List<String> parseCommand(String command) {
        List<String> args = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*?)\"|(\\S+)");
        // выбирает либо выражения в кавычках, либо выражения без пробела
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Группа 1 содержит текст внутри кавычек, удаляем кавычки
                args.add(matcher.group(1));
            } else {
                // Группа 2 содержит текст без кавычек
                args.add(matcher.group(2));
            }
        }
        return args;
    }
}