package com.oocl.training.ToDoList.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ToDoListResponse {
    private Integer id;
    private String title;
    private String status;
}
