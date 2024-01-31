package tiny;

import tiny.exceptions.TinyException;
import tiny.tasks.Deadline;
import tiny.tasks.Event;
import tiny.tasks.Task;
import tiny.tasks.Todo;

import java.util.ArrayList;


public class TaskList {
    protected ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {}

    public TaskList(ArrayList<String> data) throws TinyException {
        // Parse the data here.
        for (int i = 0; i < data.size(); i++) {
            String[] entry = data.get(i).split(" \\| ");
            if (entry[0].equals("T")) {
                Todo todo = new Todo(entry[2], !entry[1].equals("0"));
                tasks.add(todo);
            } else if (entry[0].equals("D")) {
                Deadline deadline = new Deadline(entry[2], !entry[1].equals("0"), entry[3]);
                tasks.add(deadline);
            } else if (entry[0].equals("E")) {
                Event event = new Event(entry[2], !entry[1].equals("0"), entry[3], entry[4]);
                tasks.add(event);
            }
        }
    }
    
    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(Integer ind) {
        tasks.remove(tasks.get(ind));
    }    

    public Task get(Integer ind) {
        return tasks.get(ind);
    }

    public Integer size() {
        return tasks.size();
    }

    public String list() {
        if (tasks.size() == 0) {
            return "You don't have any tasks!";
        }
        String output = "";
        for (int i = 0; i < tasks.size(); i++) {
            output += (i + 1) + "." + tasks.get(i);
            output += "\n   ";
        }
        return output;
    }

    public String find(String input) {
        /* 
        int index = 1;  
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).descriptionSearch(input)) {
            }
        }
        */
        return "";
    }


    public ArrayList<String> toSave() {
        ArrayList<String> tasksToSave = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            tasksToSave.add(tasks.get(i).toSave());
        }
        return tasksToSave;
    }

}
