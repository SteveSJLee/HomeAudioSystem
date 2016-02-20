package ca.mcgill.ecse321.HomeAudioSystem.application;

import ca.mcgill.ecse321.HomeAudioSystem.persistence.PersistenceHomeAudioSystem;
import ca.mcgill.ecse321.HomeAudioSystem.view.HomeAudioSystemPage;

public class HomeAudioSystem {
	public static void main(String[] args) {
		// load model 
		PersistenceHomeAudioSystem.loadHomeAudioSystemModel();
		
		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new HomeAudioSystemPage().setVisible(true);
			}
		});
	}
	// HAS 3:43 pm 02/20/2016
}
