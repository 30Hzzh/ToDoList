package com.oocl.training.ToDoList.dao;

import com.oocl.training.ToDoList.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JpaToDoListDao extends JpaRepository<ToDo, Integer> {
}
