package com.oocl.training.ToDoList.dao;

import com.oocl.training.ToDoList.model.ToDo;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ToDoListDao {
    public List<ToDo> getToDos();

    public ToDo addToDo(ToDo toDo);

    ToDo getToDoById(Integer id);

    ToDo updateToDoById(Integer id, ToDo toDo);

    ToDo updateToDoStatusById(Integer id, String status);
}
