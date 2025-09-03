package com.oocl.training.ToDoList.Integration;


import com.oocl.training.ToDoList.dao.ToDoListDao;
import com.oocl.training.ToDoList.model.ToDo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
        toDoListDao.addToDo(new ToDo("title1", true));
        toDoListDao.addToDo(new ToDo("title2", false));
        toDoListDao.addToDo(new ToDo("title3", true));
    }

    @AfterAll
    public void tearDown(){
        toDoListDao.getToDos().forEach(todo -> toDoListDao.updateToDoStatusById(todo.getId(), true));
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].status").value(expectedEmployees.get(0).getCompleted()));

    }

    @Test
    public void should_return_todo_when_get_todo_by_id_given_id() throws Exception {
//    given
        ToDo expectedToDo = toDoListDao.getToDos().get(0);
//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.get("/todos/{id}", expectedToDo.getId()));
//        then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedToDo.getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(expectedToDo.getCompleted()));
    }

    @Test
    public void should_return_updated_todo_when_update_todo_by_id_given_id_and_todo() throws Exception {
//    given
        ToDo toUpdateToDo = toDoListDao.getToDos().get(0);
        String updatedTitle = "updated title";
        String updatedStatus = "doing";
        String updateToDoJson = String.format("{\"title\":\"%s\",\"status\":\"%s\"}", updatedTitle, updatedStatus);
//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.put("/todos/{id}", toUpdateToDo.getId())
                .contentType("application/json")
                .content(updateToDoJson));

//        then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(toUpdateToDo.getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedTitle));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updatedStatus));
    }

    @Test
    public void should_return_added_todo_when_add_todo_given_todo() throws Exception {
//    given
        String newTitle = "new title";
        String newStatus = "todo";
        String newToDoJson = String.format("{\"title\":\"%s\",\"status\":\"%s\"}", newTitle, newStatus);
//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.post("/todos")
                .contentType("application/json")
                .content(newToDoJson));
//        then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newTitle));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newStatus));
    }

    @Test
    public void should_return_updated_todo_when_update_todo_status_by_id_given_id_and_status() throws Exception {
//    given
        ToDo toUpdateToDo = toDoListDao.getToDos().get(0);
        String updatedStatus = "done";
        String updateStatusJson = String.format("{\"status\":\"%s\"}", updatedStatus);


//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.patch("/todos/{id}", toUpdateToDo.getId())
                .contentType("application/json")
                .content(updateStatusJson));
//        then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(toUpdateToDo.getId()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(toUpdateToDo.getTitle()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updatedStatus));
    }

    @Test
    public void should_return_nothing_when_delete_todo_by_id_given_id() throws Exception {
//    given
        ToDo toDeleteToDo = toDoListDao.getToDos().get(0);
//    when
        ResultActions result = client.perform(MockMvcRequestBuilders.delete("/todos/{id}", toDeleteToDo.getId()));
//        then
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
        ToDo deletedToDo = toDoListDao.getToDoById(toDeleteToDo.getId());
        assert(deletedToDo.getCompleted().equals("deleted"));
    }

}
