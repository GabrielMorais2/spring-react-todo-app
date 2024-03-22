package com.ahkn.springtodoapp.service.impl;

import com.ahkn.springtodoapp.dto.TaskDto;
import com.ahkn.springtodoapp.dto.TaskResponse;
import com.ahkn.springtodoapp.exception.ResourceNotFoundException;
import com.ahkn.springtodoapp.entity.Task;
import com.ahkn.springtodoapp.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ahkn.springtodoapp.repository.TaskRepository;

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
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Task> tasks = taskRepository.findAll(pageable);

        List<Task> taskList = tasks.getContent();
        List<TaskDto> taskDtoList = taskList.stream().map(this::mapToDTO).collect(Collectors.toList());

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setData(taskDtoList);
        taskResponse.setPageNo(tasks.getNumber());
        taskResponse.setPageSize(tasks.getSize());
        taskResponse.setTotalElements(tasks.getTotalElements());
        taskResponse.setTotalPages(tasks.getTotalPages());
        taskResponse.setLast(tasks.isLast());

        return taskResponse;
    }

    @Override
    public TaskDto getTaskById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return mapToDTO(task);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }

    @Override
    public void deleteTaskById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
    }

    @Override
    public void deleteTasksByCompleted(boolean completed) {
        Pageable pageable = PageRequest.of(0, 1000); // You can adjust the page size as needed

        Page<Task> tasksToDelete = taskRepository.findByCompleted(completed, pageable);
        if (!tasksToDelete.isEmpty()) {
            taskRepository.deleteInBatch(tasksToDelete);
        }
    }

    @Override
    public TaskResponse getAllTasksByCompleted(boolean completed, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Task> tasks;
        if (completed) {
            tasks = taskRepository.findByCompleted(true, pageable);
        } else {
            tasks = taskRepository.findByCompleted(false, pageable);
        }

        List<Task> taskList = tasks.getContent();
        List<TaskDto> taskDtoList = taskList.stream().map(this::mapToDTO).collect(Collectors.toList());

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setData(taskDtoList);
        taskResponse.setPageNo(tasks.getNumber());
        taskResponse.setPageSize(tasks.getSize());
        taskResponse.setTotalElements(tasks.getTotalElements());
        taskResponse.setTotalPages(tasks.getTotalPages());
        taskResponse.setLast(tasks.isLast());

        return taskResponse;
    }

    private Task mapToEntity(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }

    private TaskDto mapToDTO(Task task) {
        return mapper.map(task, TaskDto.class);
    }
}