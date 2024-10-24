package com.easternpearl.tasktracker.Db;

import com.easternpearl.tasktracker.control.TaskState;
import com.easternpearl.tasktracker.medel.Category;
import com.easternpearl.tasktracker.medel.Search;
import com.easternpearl.tasktracker.medel.SubTask;
import com.easternpearl.tasktracker.medel.Task;

import java.util.ArrayList;

public interface Database {

  public void AddTodos(Task newToDo);
  public ArrayList<Task> getToDoAll();
  public ArrayList<Task> getToDosOnState(TaskState taskState);
  public void updateTodo(Task updatedTodo);
  public void deleteTodo(Task deleteTodo);
  public ArrayList<Task> searchToDo(Search search);


  public ArrayList<String> getCategoryAll();
  public Category getCategory(int CategoryId);
  public boolean addCategory(String category);
  public void editCategory(Category category);
  public void deleteCategory(Category category);
  ArrayList<SubTask> getSubTasks(int mainTaskId);






  public static Database getDataBase(){
      return ArrayDatabase.getDatabase();
  }


  void addSubTask(SubTask subTask);
}
