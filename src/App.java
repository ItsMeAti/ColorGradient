import javax.swing.SwingUtilities;

import GUI.GUI;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new GUI();
			}
		});
	}

}
