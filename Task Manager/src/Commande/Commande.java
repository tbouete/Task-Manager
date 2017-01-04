package Commande;

import com.codurance.training.tasks.TaskList;

public class Commande {
	private ComportementCommande comportement;
	
	public Commande(ComportementCommande comportement){
		this.comportement = comportement;
	}
	
	public String execute(String commandLine, TaskList tl){
		return this.comportement.execute(commandLine, tl);
	}
}
