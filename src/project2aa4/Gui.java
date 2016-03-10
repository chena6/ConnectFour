/**
 * GUI for 2aa4 project
 */
package project2aa4;

import javax.swing.JFrame;


public class Gui{
	public static void main(String[] args){
		new Gui();
	}
	
	public Gui(){ //create GUI
		JFrame f = new JFrame(); //Create the frame
		f.setTitle("Connect 4"); //set the title
		f.add(new Board()); //add the board
		String os = System.getProperty("os.name");
		if ( os.indexOf("Mac") != -1){ //set for mac
			f.setSize(650,520); //set the size
		}
		else if (os.indexOf("Windows") != -1){ //set for windows
			f.setSize(665,535);
		}
		else{
			f.setSize(665,535); //set for other os
		}
		f.setLocationRelativeTo(null); 
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //have x button close
	}


}
