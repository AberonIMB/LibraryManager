package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс команды, который принимает команду и разбивает её на параметры
 */
public class Command {
    private String name;
    private List<String> params;

    public Command(String command) {
        List<String> args = readCommand(command);

        if (!args.isEmpty()) {
            this.name = args.get(0);
            this.params = args.subList(1, args.size());
        }
    }

    /**
     * Разбивает команду на название и параметры
     */
    private List<String> readCommand(String command) {
        List<String> args = new ArrayList<>();
//        Pattern pattern = Pattern.compile("[\"“](.*?)[\"”]|(\\S+)");
        Pattern pattern = Pattern.compile("(\\S+)|[\"“](.*?)[\"”]");
        // выбирает либо выражения в ковычках, либо выражения без пробела
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            String subString = matcher.group(1);
            if (subString != null) {
                args.add(subString);
            } else {
                args.add(matcher.group(2));
            }
        }
        return args;
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
}