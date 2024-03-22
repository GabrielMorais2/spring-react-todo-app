package com.ahkn.springtodoapp.service;

import com.ahkn.springtodoapp.dto.TaskDto;
import com.ahkn.springtodoapp.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskResponse getAllTasks(int pageNo, int pageSize, String sortBy, String sortDir);

    TaskDto getTaskById(long id);

    TaskDto updateTask(TaskDto postDto, long id);

    void deleteTaskById(long id);

    void deleteTasksByCompleted(boolean completed);

    TaskResponse getAllTasksByCompleted(boolean completed, int pageNo, int pageSize, String sortBy, String sortDir);
}
