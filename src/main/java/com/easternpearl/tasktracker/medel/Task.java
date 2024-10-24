package com.easternpearl.tasktracker.medel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Task {
    int id;
    String Title;
    String category;
    String Description;
    Boolean finished;
    LocalDate startDate;
    LocalDate endDate;
}
