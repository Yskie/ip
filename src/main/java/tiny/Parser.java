package tiny;

import tiny.exceptions.TinyException;
import tiny.tasks.Deadline;
import tiny.tasks.Event;
import tiny.tasks.Todo;

public class Parser {
    protected boolean isExit = false;
    protected String input;
    protected TaskList taskList;

    public String parse(String input, TaskList taskList) throws TinyException {
        this.input = input;
        this.taskList = taskList;

        try {
            if (input.equals("list")) {
                return taskList.list();
            } else if (checkCmd(input, "mark", 4)) {
                return mark();
            } else if (checkCmd(input, "unmark", 6)) {
                return unmark();
            } else if (checkCmd(input, "todo", 4)) {
                return todo();
            } else if (checkCmd(input, "deadline", 8)) {
                return deadline();
            } else if (checkCmd(input, "event", 5)) {
                return event();
            } else if (checkCmd(input, "delete", 6)) {
                return delete();                
            } else if (input.equals("bye")) {
                isExit = true;
                return bye();
            } else {
                return cmdUnknown();
            }
        } catch (TinyException e) {
            throw e;
        }
    }

    public boolean isExit() {
        return isExit;
    }

    private String mark() throws TinyException {
        try {
            String[] s = input.split(" ");
            if (s.length != 2 || !s[0].equals("mark")) {
                return "OOPS! You need to type \"mark <number>\" to change the status to done!";
            }
            int ind = Integer.parseInt(s[1]);
            taskList.get(ind - 1).taskDone();
            return "Nice! I've marked this task as done:\n      " + taskList.get(ind - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "OOPS! You need to type \"mark <number>\" to change the status to done!";
        } catch (NullPointerException e) {
            return "OOPS! Please type a valid number! Type \"list\" to check the lists of tasks.";
        } catch (Exception e) {
            throw new TinyException("Something went wrong...");
        }
    }

    private String unmark() throws TinyException {
        try {
            String[] s = input.split(" ");
            if (s.length != 2 || !s[0].equals("unmark")) {
                return "OOPS! You need to type \"unmark <number>\" to change the status not done!";
            }
            int ind = Integer.parseInt(s[1]);
            taskList.get(ind - 1).taskUndone();
            return "OK, I've marked this task as not done yet:\n      " + taskList.get(ind - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "OOPS! You need to type \"unmark <number>\" to change the status to not done!";
        } catch (NullPointerException e) {
            return "OOPS! Please type a valid number! Type \"list\" to check the lists of tasks.";
        } catch (Exception e) {
            throw new TinyException("Something went wrong...");
        }
    }

    private String todo() throws TinyException {
        try {
            String name = "";
            String[] st = input.split("");
            String[] s = input.split(" ");
            if (!s[0].equals("todo")) {
                return "OOPS! You need to type \"todo <description>\" to create a new todo!";
            } else {
                for (int i = 5; i < st.length; i++) {
                    name += st[i];
                }
                if (name == "") {
                    return "OOPS! The description of a todo cannot be empty.";
                } else {
                    taskList.add(new Todo(name));
                    return "Got it. I've added this task:\n      " + taskList.get(taskList.size() - 1)
                            + "\n   Now you have " + taskList.size() + " task(s) in the list.";
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "OOPS! You need to type \"todo <description>\" to create a new todo!";
        } catch (Exception e) {
            throw new TinyException("Something went wrong...");
        }
    }

    private String deadline() throws TinyException {
        try {
            String name = "";
            String[] st = input.split("/by ");
            String[] s = input.split(" ");
            if (!s[0].equals("deadline")) {
                return "OOPS! You need to type \"deadline <description> /by <yyyy-mm-dd> <time>\" to create a new deadline!";
            } else {
                name = st[0].substring(9);
                taskList.add(new Deadline(name.trim(), st[1]));
                // printAdd(tasks.get(tasks.size() - 1).toString(), tasks.size());
                return "Got it. I've added this task:\n" + "      " + taskList.get(taskList.size() - 1)
                        + "\n   Now you have " + taskList.size() + " task(s) in the list.";
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "OOPS! You need to type \"deadline <description> /by <yyyy-mm-dd> <time>\" to create a new deadline!";
        } catch (TinyException e) {
            throw e;
        }
    }

    private String event() throws TinyException {
        try {
            String name = "";
            String[] s = input.split(" ");
            if (!s[0].equals("event")) {
                System.out.println("YES");
                return "OOPS! You need to type \"event <description> /from <start date> /to <end date>\" to create a new deadline!";
            } else {
                String[] from = input.split("/from ");
                String[] fromTo = from[1].split("/to ");
                name = from[0].substring(5);
                taskList.add(new Event(name.trim(), fromTo[0].trim(), fromTo[1].trim()));
                return "Got it. I've added this task:\n" + "      " + taskList.get(taskList.size() - 1)
                        + "\n   Now you have " + taskList.size() + " task(s) in the list.";
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "OOPS! You need to type \"event <description> /from <start date> /to <end date>\" to create a new deadline!";
        } catch (Exception e) {
            throw new TinyException("Something went wrong...");
        }
    }

    private String delete() throws TinyException {
        try {
            String[] s = input.split(" ");
            if (s.length != 2 || !s[0].equals("delete")) {
                return "OOPS! You need to type \"delete <number>\" to delete the task!";
            }
            int ind = Integer.parseInt(s[1]);  
            String output = "Noted. I've removed this task:" + 
            "\n      " + taskList.get(ind - 1).toString() + 
            "\n   Now you have " + (taskList.size() - 1) + " task(s) in the list.";
            taskList.delete(ind - 1);
            return output;

        } catch (NumberFormatException e) {
            return "OOPS! You need to type \"delete <number>\" to delete the task!";
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return 
                    "OOPS! Please type a valid number! Type \"list\" to check the lists of tasks.";
        } catch (Exception e) {
            throw new TinyException("Something went wrong...");
        }               
    }

    private String bye() {
        return "Bye. Hope to see you again soon!";
    }

    private String cmdUnknown() {
        return "I'm sorry, but I don't know what that means :-(";
    }

    private static boolean checkCmd(String input, String name, int len) {
        return input.length() >= len && input.substring(0, len).equals(name);
    }
}
