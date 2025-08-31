package com.oocl.training.ToDoList.controller;

import com.oocl.training.ToDoList.controller.dto.ToDoListRequest;
import com.oocl.training.ToDoList.controller.dto.ToDoListResponse;
import com.oocl.training.ToDoList.controller.mapper.ToDoListMapper;
import com.oocl.training.ToDoList.dao.ToDoListDao;
import com.oocl.training.ToDoList.model.ToDo;
import com.oocl.training.ToDoList.service.ToDoListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/todos")
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private ToDoListMapper toDoListMapper;

    @GetMapping("")
    public List<ToDoListResponse> getToDos() {
        List<ToDo> todos =  toDoListService.getToDos();
        List<ToDoListResponse> responses = toDoListMapper.toResponse(todos);
        return responses;
    }

    @GetMapping("/{id}")
    public ToDoListResponse getToDoById(@PathVariable Integer id) {
        ToDo todo =  toDoListService.getToDoById(id);
        ToDoListResponse response = toDoListMapper.toResponse(todo);
        return response;
    }

    @PutMapping("/{id}")
    public ToDoListResponse updateToDoById(@PathVariable Integer id, @RequestBody ToDo toDo) {
        ToDo existingToDo = toDoListService.updateToDoById(id, toDo);
        ToDoListResponse response = toDoListMapper.toResponse(existingToDo);
        return response;
    }

    @PostMapping("")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public ToDoListResponse addToDo(@Valid @RequestBody ToDoListRequest toDoListRequest) {
        ToDo toDo = toDoListMapper.toEntity(toDoListRequest);
        ToDo newToDo = toDoListService.addToDo(toDo);
        ToDoListResponse response = toDoListMapper.toResponse(newToDo);
        return response;
    }

    @PatchMapping("/{id}")
    public ToDoListResponse updateToDoStatusById(@PathVariable Integer id, @RequestBody ToDoListRequest toDoRequest) {
        ToDo toDo = toDoListMapper.toEntity(toDoRequest);
        ToDo updatedToDo = toDoListService.updateTodoSatusById(id, toDo.getStatus());
        ToDoListResponse response = toDoListMapper.toResponse(updatedToDo);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteToDoById(@PathVariable Integer id) {
        toDoListService.deleteToDoById(id);
    }
}
