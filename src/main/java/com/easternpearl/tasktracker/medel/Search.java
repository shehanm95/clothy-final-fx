package com.easternpearl.tasktracker.medel;

import com.easternpearl.tasktracker.control.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Search {
    TaskState state;
    String category;
}
