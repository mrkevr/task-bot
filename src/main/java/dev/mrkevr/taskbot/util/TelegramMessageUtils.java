package dev.mrkevr.taskbot.util;

import dev.mrkevr.taskbot.constant.CommandConstants;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TelegramMessageUtils {

    private TelegramMessageUtils() {
    }

    public static String extractMessage(Update update) {
        return update.getMessage().getText();
    }

    public static String extractCommand(Update update) {
        String text = update.getMessage().getText();
        String[] words = text.split("\\s+"); // Split by one or more spaces
        return words.length > 0 ? words[0].toLowerCase() : "";
    }

    public static String extractCommandParameter(String source, String command) {
        // Ensure the command ends with a space to match only full commands
        String regex = Pattern.quote(command) + "\\s+(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        return matcher.find() ? matcher.group(1).trim() : "";
    }

    public static String extractTaskId(String source) {
        String prefix = CommandConstants.TASK_DELETE.getCommand() + "_";
        return source.substring(source.indexOf(prefix) + prefix.length());
    }
}

