package com.oocl.training.ToDoList.service;

import com.oocl.training.ToDoList.dao.ToDoListDao;
import com.oocl.training.ToDoList.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    private ToDoListDao toDoListDao;

    public List<ToDo> getToDos(Integer page,Integer size) {

       List<ToDo> toDos =  toDoListDao.getToDos();
        if (page != null && size != null) {
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, toDos.size());
            if (fromIndex >= toDos.size()) {
                return List.of(); // Return empty list if page is out of bounds
            }
            return toDos.subList(fromIndex, toIndex);
        }
        return toDos;
    }

    public ToDo getToDoById(Integer id) {
        return toDoListDao.getToDoById(id);
    }

    public ToDo updateToDoById(Integer id, ToDo toDo) {
        ToDo existingToDo = toDoListDao.getToDoById(id);
        if (existingToDo == null) {
            throw new RuntimeException("ToDo not found");
        }
        return toDoListDao.updateToDoById(id, toDo);
    }

    public ToDo addToDo(ToDo toDo) {
        toDoListDao.addToDo(toDo);
        return toDo;
    }

    public ToDo updateTodoSatusById(Integer id, Boolean status) {
        ToDo existingToDo = toDoListDao.getToDoById(id);
        if (existingToDo == null) {
            throw new RuntimeException("ToDo not found");
        }
        return toDoListDao.updateToDoStatusById(id, status);
    }

    public void deleteToDoById(Integer id) {
        ToDo existingToDo = toDoListDao.getToDoById(id);
        if (existingToDo == null) {
            throw new RuntimeException("ToDo not found");
        }
        toDoListDao.updateToDoStatusById(id, true);
    }
}
