package Commande;

import java.util.ArrayList;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskList;

public class ComportementAdd implements ComportementCommande{

	@Override
	public String execute(String commandLine, TaskList tl) {
		String ret = "";   //change en fonction du fonctionnement des différentes méthodes
		String[] subcommandRest = commandLine.split(" ", 2);
	    String subcommand = subcommandRest[0];
	    if (subcommand.equals("project")) {
	        addProject(subcommandRest[1], tl);
	    } else if (subcommand.equals("task")) {
	        String[] projectTask = subcommandRest[1].split(" ", 2);
	        tl.addTask(projectTask[0], projectTask[1]);
	    }
	    
	    return ret;
		
	}

	private void addProject(String name, TaskList tl) {
        tl.getTasks().put(name, new ArrayList<Task>());
    }

	
	
}
	
