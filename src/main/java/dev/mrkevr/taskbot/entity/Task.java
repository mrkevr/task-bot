package dev.mrkevr.taskbot.entity;

import dev.mrkevr.taskbot.util.RandomUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tasks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends GenericEntity {

    @Column(name = "chat_id")
    String chatId;

    @Column(name = "body")
    String body;

    @Column(name = "task_id")
    String taskId;

    public Task(String chatId, String body) {
        this.chatId = chatId;
        this.body = body;
        this.taskId = RandomUtils.generateRandomAlphanumeric();
    }
}
