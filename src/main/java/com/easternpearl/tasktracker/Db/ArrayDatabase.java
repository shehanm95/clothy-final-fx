package com.easternpearl.tasktracker.Db;

import com.easternpearl.tasktracker.control.TaskState;
import com.easternpearl.tasktracker.medel.Category;
import com.easternpearl.tasktracker.medel.Search;
import com.easternpearl.tasktracker.medel.SubTask;
import com.easternpearl.tasktracker.medel.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class ArrayDatabase implements Database{
    private static ArrayDatabase database;

    public static Database getDatabase() {
        if(database == null) database = new ArrayDatabase();
        return database;
    }

    ArrayList<Task> todos = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<SubTask> subTasks = new ArrayList<>();


    private void addsamples(){

        todos.add(new Task(1,"Buy groceries","School Work", "Milk, Bread, Eggs", false,LocalDate.parse("2023-02-02"),null));
        todos.add(new Task(2,"Read book","Home Work", "Finish reading 'JavaFX for Dummies'", true,LocalDate.parse("2023-02-02"),LocalDate.parse("2024-02-02")));
        todos.add(new Task(3,"Exercise","Home Work", "Go for a 30-minute run", false,LocalDate.parse("2023-02-02"),LocalDate.parse("2023-02-02")));

        categories.add("All");
        categories.add("Default");
        categories.add("School Work");
        categories.add("Home Work");

        subTasks.add(new SubTask(1,"sub1",false));
        subTasks.add(new SubTask(1,"sub2",false));
        subTasks.add(new SubTask(1,"sub3",false));
        subTasks.add(new SubTask(1,"sub4",true));
        subTasks.add(new SubTask(1,"sub5",true));
        subTasks.add(new SubTask(2,"sub6",false));
        subTasks.add(new SubTask(2,"sub7",true));




    }

    private ArrayDatabase(){
        addsamples();
    }


    @Override
    public void AddTodos(Task newToDo) {
        todos.add(newToDo);
    }

    @Override
    public ArrayList<Task> getToDoAll() {
        return todos;
    }

    @Override
    public ArrayList<Task> getToDosOnState(TaskState taskState) {
        switch (taskState){
            case ALL -> {
                return this.getToDoAll();
            }
            case FINISHED -> {
                ArrayList<Task> finishedList = new ArrayList<>();
                for (Task todo : this.getToDoAll()){
                    if(todo.getFinished())
                        finishedList.add(todo);
                }
                return finishedList;
            }
            case ACTIVE -> {
                ArrayList<Task> notFinishedList = new ArrayList<>();
                for (Task todo : this.getToDoAll()){
                    if(!todo.getFinished())
                        notFinishedList.add(todo);
                }
                return notFinishedList;
            }
        }
        ArrayList<Task> todo = new ArrayList<>();
        return todo;
    }

    @Override
    public void updateTodo(Task updatedTodo) {
        for (int i = 0; i < todos.size(); i++) {
            if(todos.get(i).getId() == updatedTodo.getId()){
                todos.get(i).setId(updatedTodo.getId());
                todos.get(i).setDescription(updatedTodo.getDescription());
                todos.get(i).setFinished(updatedTodo.getFinished());
                todos.get(i).setTitle(updatedTodo.getTitle());
            }
        }
    }


// category area =========================================================================;
    @Override
    public void deleteTodo(Task deleteTodo) {
            this.todos.remove(todos.indexOf(deleteTodo));
    }

    @Override
    public ArrayList<Task> searchToDo(Search search) {
        ArrayList<Task> accordingToState = getToDosOnState(search.getState());
        ArrayList<Task> newArray = new ArrayList<>();
        int i = 0;
        if(search.getCategory() == null) search.setCategory("All");
        if (!search.getCategory().equals("All")) {
            for (Task task : accordingToState){
                if(task.getCategory().equals(search.getCategory())){
                    newArray.add(task);
                    System.out.println("added " + i);
                }
            }
        }else {
            return accordingToState;
        }

        return newArray;
    }

    @Override
    public ArrayList<String> getCategoryAll() {
        return categories;
    }

    @Override
    public Category getCategory(int CategoryId) {
        return null;
    }

    @Override
    public boolean addCategory(String category) {
        if(category.length() < 26 && !category.isEmpty()) {
            this.categories.add(category);
            return true;
        }
        return false;
    }

    @Override
    public void editCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category) {

    }



    //========================= Sub Task Area ==============================


    @Override
    public ArrayList<SubTask> getSubTasks(int mainTaskId) {
        ArrayList<SubTask> filteredSubTasks = new ArrayList<>();
        for(SubTask subTask : subTasks){
           if(subTask.getParentTaskId() == mainTaskId){
               filteredSubTasks.add(subTask);
           }
        }
        return filteredSubTasks;
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }


}
