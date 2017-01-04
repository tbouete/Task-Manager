package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskList;

import Commande.Commande;
import Commande.ComportementAdd;

public class Menu implements Runnable{
	private static final String QUIT = "quit";
    private BufferedReader in;
    private PrintWriter out;
    private TaskList tl = new TaskList(in, out);
    
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        menu.in = in;
        menu.out = out;
        menu.run();
    }

	@Override
	public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }
	
	private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }
	
    private void show() {
        for (Map.Entry<String, List<Task>> project : tl.getTasks().entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }
    
   //A remove mais on le garde en cas d'accident
    private void add(String commandLine) {
    	
    	ComportementAdd compTemp = new ComportementAdd();
    	Commande commandeAddTemp = new Commande(compTemp);
    	out.print(commandeAddTemp.execute(commandLine, this.tl));
    	
    	/*
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            this.tl.addTask(projectTask[0], projectTask[1]);
        }*/
    }
    
    //A remove mais on le garde en cas d'accident
    private void addProject(String name) {
        this.tl.getTasks().put(name, new ArrayList<Task>());
    }
    
    private void check(String idString) {
        setDone(idString, true);
    }
    
    private void uncheck(String idString) {
        setDone(idString, false);
    }
    
    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : this.tl.getTasks().entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }
    
    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println();
    }
    
    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
