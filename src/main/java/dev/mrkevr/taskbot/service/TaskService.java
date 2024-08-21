package dev.mrkevr.taskbot.service;

import dev.mrkevr.taskbot.entity.Task;
import dev.mrkevr.taskbot.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getByChatId(String chatId) {
        return taskRepository.findByChatId(chatId);
    }

    @Transactional
    public Task addTask(String chatId, String taskBody) {
        Task task = new Task(chatId, taskBody);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    public void deleteByTaskId(String taskId) {
        taskRepository.deleteByTaskId(taskId);
    }
}
