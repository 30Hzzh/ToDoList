package com.oocl.training.ToDoList.controller.mapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.oocl.training.ToDoList.controller.dto.ToDoListRequest;
import com.oocl.training.ToDoList.controller.dto.ToDoListResponse;
import com.oocl.training.ToDoList.model.ToDo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToDoListMapper {

    public List<ToDoListResponse> toResponse(List<ToDo> todos) {
        return todos.stream().map(this::toResponse).toList();
    }

    public ToDoListResponse toResponse(ToDo todo) {
        ToDoListResponse response = new ToDoListResponse();
        BeanUtils.copyProperties(todo, response);
        return response;
    }

    public ToDo toEntity(ToDoListRequest toDoListRequest) {
        ToDo toDo = new ToDo();
        BeanUtils.copyProperties(toDoListRequest, toDo);
        return toDo;
    }
}
