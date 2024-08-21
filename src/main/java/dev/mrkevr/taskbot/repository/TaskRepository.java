package dev.mrkevr.taskbot.repository;

import dev.mrkevr.taskbot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByChatId(String chatId);

    void deleteByTaskId(String taskId);
}
