package com.ahkn.springtodoapp.controller;

import com.ahkn.springtodoapp.dto.TaskDto;
import com.ahkn.springtodoapp.dto.TaskResponse;
import com.ahkn.springtodoapp.service.TaskService;
import com.ahkn.springtodoapp.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/tasks")
@Tag(
        name = "REST API for Task"
)
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Create a task",
            description = "Creates a task with the given details"
    )
    @ApiResponse(
            responseCode = "201",
            description = "201 CREATED"
    )
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "List all tasks",
            description = "Retrieves all tasks"
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 SUCCESS"
    )
    @GetMapping
    public TaskResponse getAllTasks(
            @RequestParam(value = "completed", required = false) String completed,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        if (completed != null) {
            return taskService.getAllTasksByCompleted(completed.equals("true"), pageNo, pageSize, sortBy, sortDir);
        }
        return taskService.getAllTasks(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get a task by id",
            description = "Retrieves a task by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(
            summary = "Update a task",
            description = "Updates a task with the given details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 SUCCESS"
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto, @PathVariable(name = "id") long id) {
        TaskDto taskResponse = taskService.updateTask(taskDto, id);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a task",
            description = "Deletes the corresponding task"
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 SUCCESS"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>("The task deleted successfully.", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete tasks",
            description = "Batch delete tasks with the given status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "200 SUCCESS"
    )
    @DeleteMapping("/")
    public ResponseEntity<String> deleteTasks(@RequestParam(value = "completed", required = true) String completed) {
        if ("true".equals(completed) || "all".equals(completed)) {
            taskService.deleteTasksByCompleted(true);
        }
        if ("false".equals(completed) || "all".equals(completed)) {
            taskService.deleteTasksByCompleted(false);
        }
        return new ResponseEntity<>("The tasks deleted successfully.", HttpStatus.OK);
    }
}
