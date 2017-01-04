package Commande;

import com.codurance.training.tasks.TaskList;

public interface ComportementCommande {

	public String execute(String commandLine, TaskList tl);
}
