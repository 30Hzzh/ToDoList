package com.oocl.training.ToDoList.dao;

import com.oocl.training.ToDoList.model.ToDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ToDoListDbDao implements ToDoListDao {


    JpaToDoListDao jpaToDoListDao;
    public ToDoListDbDao(JpaToDoListDao jpaToDoListDao) {
        this.jpaToDoListDao = jpaToDoListDao;
    }

    @Override
    public List<ToDo> getToDos() {
        // 状态为deleted的过滤
        List<ToDo> toDos = jpaToDoListDao.findAll().stream().filter(toDo -> !"deleted".equals(toDo.getStatus())).toList();
        return toDos;
    }

    @Override
    public ToDo addToDo(ToDo toDo) {
        return jpaToDoListDao.save(toDo);
    }

    @Override
    public ToDo getToDoById(Integer id) {
        return jpaToDoListDao.findById(id).orElse(null);
    }

    @Override
    public ToDo updateToDoById(Integer id, ToDo toDo) {
        ToDo existingToDo = jpaToDoListDao.findById(id).orElse(null);
        if (existingToDo == null) {
            throw new RuntimeException("ToDo not found");
        }
        existingToDo.setTitle(toDo.getTitle());
        existingToDo.setStatus(toDo.getStatus());
        return jpaToDoListDao.save(existingToDo);
    }

    @Override
    public ToDo updateToDoStatusById(Integer id, String status) {
        ToDo toDo = jpaToDoListDao.findById(id).orElse(null);
        if (toDo == null) {
            throw new RuntimeException("ToDo not found");
        }
        toDo.setStatus(status);
        return jpaToDoListDao.save(toDo);
    }
}
