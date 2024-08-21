package dev.mrkevr.taskbot.bot;

import dev.mrkevr.taskbot.config.BotConfig;
import dev.mrkevr.taskbot.constant.CommandConstants;
import dev.mrkevr.taskbot.entity.Task;
import dev.mrkevr.taskbot.service.TaskService;
import dev.mrkevr.taskbot.util.TelegramMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static dev.mrkevr.taskbot.constant.CommandConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final TaskService taskService;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy");

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {

        final String command = TelegramMessageUtils.extractCommand(update);
        final String chatId = update.getMessage().getChatId().toString();

        if(command.startsWith(TASK_LIST.getCommand())) {
            final List<Task> tasks = taskService.getByChatId(chatId);
            if(ObjectUtils.isEmpty(tasks)) {
                execute(new SendMessage(chatId, "No task found"));
            } else {
                final String response = breakdownTasks(tasks);
                execute(new SendMessage(chatId, response));
            }

        } else if(command.startsWith(TASK_ADD.getCommand())) {
            String taskBody = TelegramMessageUtils.extractCommandParameter(update.getMessage().getText(), TASK_ADD.getCommand());
            if(taskBody.length() >= 6) {
                Task task = taskService.addTask(chatId, taskBody);
                String message = "New task added: " + task.getBody();
                log.info(message);
                execute(new SendMessage(chatId, message));
            } else {
                execute(new SendMessage(chatId, "Invalid add format, use /add <task> (minimum of 6 characters)"));
            }

        } else if(command.startsWith(TASK_DELETE.getCommand())) {
            String taskId = TelegramMessageUtils.extractTaskId(update.getMessage().getText());
            taskService.deleteByTaskId(taskId);
            String message = "Task deleted: " + taskId;
            log.info(message);
            execute(new SendMessage(chatId, message));

        } else if(command.startsWith(TASK_HELP.getCommand())) {
            execute(new SendMessage(chatId, this.breakDownHelp()));

        } else {
            execute(new SendMessage(chatId, "Invalid command : " + command + "\n /help for command list"));
        }
    }

    private String breakdownTasks(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("🗓").append(LocalDateTime.now().format(DATE_FORMATTER)).append(" | ");
        sb.append("☀️").append(LocalDateTime.now().getDayOfWeek()).append("\n\n");
        int taskCount = 1;
        for(Task task : tasks) {
            sb.append(taskCount++).append(". ").append(task.getBody()).append(" ").append(TASK_DELETE.getCommand()).append("_").append(task.getTaskId()).append("\n");
        }
        return sb.toString();
    }

    private String breakDownHelp() {
        StringBuilder sb = new StringBuilder();
        for(CommandConstants commandConstants : CommandConstants.values()) {
            sb.append(commandConstants.getIcon())
                    .append(" " + commandConstants.getDescription())
                    .append(" " + commandConstants.getCommand() + "\n");
        }
        return sb.toString();
    }
}