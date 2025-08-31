package com.oocl.training.ToDoList.Integration;


import com.oocl.training.ToDoList.dao.ToDoListDao;
import com.oocl.training.ToDoList.model.ToDo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ToDoListTest {
    @Autowired
    private MockMvc client;

    @Autowired
    private ToDoListDao toDoListDao;

    @BeforeAll
    public void setup(){
        toDoListDao.addToDo(new ToDo("title1", "todo"));
        toDoListDao.addToDo(new ToDo("title2", "doing"));
        toDoListDao.addToDo(new ToDo("title3", "done"));
    }

    @AfterAll
    public void tearDown(){
        toDoListDao.getToDos().forEach(todo -> toDoListDao.updateToDoStatusById(todo.getId(), "deleted"));
    }

    @Test
    public void should_return_all_todos_when_get_all_given_no_filter() throws Exception {
//    given
        List<ToDo> expectedEmployees = toDoListDao.getToDos();
//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.get("/todos"));
//        then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(expectedEmployees.get(0).getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].status").value(expectedEmployees.get(0).getStatus()));

    }
}
