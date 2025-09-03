package com.oocl.training.ToDoList.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListRequest {
    private String title;
    private Boolean completed;
}
