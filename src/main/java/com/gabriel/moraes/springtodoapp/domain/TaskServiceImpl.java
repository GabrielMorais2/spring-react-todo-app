package com.gabriel.moraes.springtodoapp.domain;

import com.gabriel.moraes.springtodoapp.domain.payloads.TaskDto;
import com.gabriel.moraes.springtodoapp.domain.payloads.TaskResponse;
import com.gabriel.moraes.springtodoapp.exception.TodoNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper mapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = mapToEntity(taskDto);
        Task newTask = taskRepository.save(task);
        return mapToDTO(newTask);
    }

    @Override
    public TaskResponse getAllTasks(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Task> tasks = taskRepository.findAll(pageable);
        return buildTaskResponse(tasks);
    }

    @Override
    public TaskDto getTaskById(long id) {
        Task task = getTaskEntityById(id);
        return mapToDTO(task);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, long id) {
        Task task = getTaskEntityById(id);
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }

    @Override
    public void deleteTaskById(long id) {
        Task task = getTaskEntityById(id);
        taskRepository.delete(task);
    }

    @Override
    public void deleteTasksByCompleted(boolean completed) {
        Pageable pageable = PageRequest.of(0, 1000);
        Page<Task> tasksToDelete = taskRepository.findByCompleted(completed, pageable);
        if (!tasksToDelete.isEmpty()) {
            taskRepository.deleteInBatch(tasksToDelete);
        }
    }

    @Override
    public TaskResponse getAllTasksByCompleted(boolean completed, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Task> tasks = taskRepository.findByCompleted(completed, pageable);
        return buildTaskResponse(tasks);
    }

    private Task getTaskEntityById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Task not found with id: " + id));
    }

    private TaskResponse buildTaskResponse(Page<Task> tasks) {
        List<TaskDto> taskDtoList = tasks.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new TaskResponse(taskDtoList, tasks.getNumber(),
                tasks.getSize(), tasks.getTotalElements(),
                tasks.getTotalPages(), tasks.isLast());
    }

    private Task mapToEntity(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }

    private TaskDto mapToDTO(Task task) {
        return mapper.map(task, TaskDto.class);
    }
}