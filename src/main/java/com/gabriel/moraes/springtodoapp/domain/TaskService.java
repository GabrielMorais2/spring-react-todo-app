package com.gabriel.moraes.springtodoapp.domain;

import com.gabriel.moraes.springtodoapp.domain.payloads.TaskDto;
import com.gabriel.moraes.springtodoapp.domain.payloads.TaskResponse;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskResponse getAllTasks(int pageNo, int pageSize, String sortBy, String sortDir);

    TaskDto getTaskById(long id);

    TaskDto updateTask(TaskDto postDto, long id);

    void deleteTaskById(long id);

    void deleteTasksByCompleted(boolean completed);

    TaskResponse getAllTasksByCompleted(boolean completed, int pageNo, int pageSize, String sortBy, String sortDir);
}
