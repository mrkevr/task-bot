package dev.mrkevr.taskbot.constant;

import lombok.Getter;

@Getter
public enum CommandConstants {

    TASK_LIST("/tasks", "Display list of tasks", "🔖"),
    TASK_ADD("/add", "Add a new task", "📝"),
    TASK_DELETE("/del", "Delete an existing task by task id", "🗑"),
    TASK_HELP("/help", "Display list of commands", "❓");

    private String command;
    private String description;
    private String icon;

    CommandConstants(String command, String description, String icon) {
        this.command = command;
        this.description = description;
        this.icon = icon;
    }
}