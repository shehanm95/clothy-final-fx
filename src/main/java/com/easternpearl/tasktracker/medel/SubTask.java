package com.easternpearl.tasktracker.medel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SubTask {
    int parentTaskId;
    String subTaskTitle;
    boolean finished;

}
