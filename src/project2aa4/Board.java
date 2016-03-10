package project2aa4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.FileNotFoundException;

import module.Module;

public class Board extends JPanel implements ActionListener{

	private Timer timer;
	
	private Map m; //create a background object map for images
	private Module mo; //create a module aka brain 
	private boolean[] Error = new boolean[5]; //check below for what each part of array is
	private boolean[] SaveState= new boolean[3]; //used to output saves and file loads
	private String PlayerState;
	private boolean Overlap = false;  
	private boolean winChecker = false; 
	private int mode = 0;
	private String userTurn = "";
	
	public Board(){
		
		m = new Map();
		mo = new Module(Module.board); //setup up module with static variable board 
		mo.reset(); //reset it 
		addMouseListener(new Al()); //add a listener so waiting for a click
		setFocusable(true);
		timer = new Timer(25, this); //have a delay
		timer.start(); //start
		userTurn = m.randomizePlayer();
		
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint(); //when something is done, or clicked in our project, the object is repainted
	}
	
	public void paint(Graphics g){
		super.paint(g); //paint the background
		for (int i = 0; i<10; i++){ //i = y block
			for (int j=0; j<13; j++){ //j = x block
				
				if(m.getMap(i, j).equals("b")){ //add the base block by getting the baseblock image and the coordinates
					g.drawImage(m.getBase(), j*50, i*50, null);
				}
				else if(m.getMap(i, j).equals("c")){ // add the base wall by getting the baseblock image and the coordinates
					g.drawImage(m.getBaseWall(), j*50, i*50, null);
				}
				if((i > 2 && i < 9) && (j > 4 && j < 12)){
					if(Module.board[i-3][j-5].equals("blank")){ //check to see if spot is empty in the module
						g.drawImage(m.getBaseBlock(), j*50, i*50, null); //add the grey block
					}
					else if(Module.board[i-3][j-5].equals("red")){
						g.drawImage(m.getRedCoin(), j*50, i*50, null); //add the red block
					}
					else if(Module.board[i-3][j-5].equals("blue")){
						g.drawImage(m.getBlueCoin(), j*50, i*50, null); //add the blue block
					}
					
				}
				

			}
		}
		
		
		g.drawImage(m.getDoneButton(), 50, 325, null);// add the buttons and textboxes all around, titles on variables
		g.drawImage(m.getClearButton(), 50, 250, null);
		g.drawImage(m.getLastButton(), 50, 400, null);
		if (m.getTurn() == "red"){ //if red turn, draw red with blue faded
			g.drawImage(m.getRedCoin(), 600, 100, null);
			g.drawImage(m.getBlueFadedCoin(), 200, 100, null);
		}
		if (m.getTurn() == "blue"){ //if blue turn, draw blue with red faded
			g.drawImage(m.getRedFadedCoin(), 600, 100, null); 
			g.drawImage(m.getBlueCoin(), 200, 100, null);
		}
		g.drawImage(m.getTextBox(), 225, 5, null);
		g.drawImage(m.getTitle(), 25, 25, null);
		
		if (mode == 0){
			SaveState[0] = false;  //reset save and load messages
			SaveState[1] = false;
			SaveState[2] = false;
			g.drawString("Please Choose A Mode Below!", 250, 50);
			g.drawImage(m.getPFade(), 550, 450, null);
			g.drawImage(m.getCompFade(), 200, 450, null);
		}else if(mode == 1){
			g.drawImage(m.getPFade(), 550, 450, null);
			g.drawImage(m.getCompClick(), 200, 450, null);
		}else{
			g.drawImage(m.getPClick(), 550, 450, null);
			g.drawImage(m.getCompFade(), 200, 450, null);
		}
		
		
		if (Error[0]){ //4 in a row error
			g.drawString("Winner is " + mo.win(Module.board), 250, 35);
		}
		if (Error[1]){ //gravity error
			g.drawString("The gravity of the board is wrong", 250, 50);
		}
		if (Error[2]){
			g.drawString("Game is a Draw", 250, 55);
		}
		if(Error[3]){ //overlap error
			g.drawString("You cannot overlap pieces.", 250, 65);
		}
		if(Error[4]){ //successful check
			g.drawString("Game in Progress", 250, 55);
		}
		if(SaveState[0]){ //Game Saved
			g.drawString("Game Saved", 250, 55);
		}
		if(SaveState[1]){ //Game Loaded
			g.drawString("Game Loaded", 250, 55);
			
		}	
		if(SaveState[2]){ //Game Failed to Load 
			g.drawString("No Saves Found", 250, 55);
		}
		else { //else draw nothing
			g.drawString("", 250, 50);
		}
		
		if(PlayerState != null){userTurn = PlayerState;}
	}
	public Module getModuleObject(){
		return mo; //accessor method for the module
	}
	
	public class Al extends JComponent implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {//change all errors back to false when clicked on board
			Error[1] = false;
			Error[2] = false;
			Error[3] = false;
			Error[4] = false;
			SaveState[0] = false;
			SaveState[1] = false;
			SaveState[2] = false;
			
			
			int x=e.getX(); //get coordinates
		    int y=e.getY();
		    int tileX = (x-150)/50; //subtract the sides and divide by 50 so you have blocks instead of ranges
		    int tileY = (y-50)/50;
		    
		    if (mode == 1){ // comp mode
		    	if (userTurn.equals(m.getTurn())){//if users turn
				    if ((tileX > 1 && tileX < 9) && (tileY > 1 && tileY < 8) && winChecker != true){ //check to see if user clicks in game area
				    	if (!Module.board[tileY-2][tileX-2].equals("blank")){
				    		Overlap = true;
				    	}
				    	Module.board = mo.clickInsert(tileY-2,tileX-2, m.userClickBoard()); //insert a blue piece in the clicked box
				    	
				    	
				    	
				    	Error[4] = true;
				    	
				    	if(!(mo.checkGravity())){ //GRAVITY ERROR
			    			Error[1] = true;
			    			Error[4] = false;
			    			
			    		}else if(Overlap){
			    			Error[3] = true; //user overlapped colors
			    			Error[4] = false;
			    			
			    			Overlap = false;
			    		}
				    	
				    	
				    	else if (mo.win(Module.board) != "nothing") { //make sure there is a win or draw
			    			if(mo.win(Module.board) == "draw") {  //check to see if draw
				    			Error[2] = true; //draw 
				    		}else{
				    			Error[0] = true;  //win
				    		}
			    			Error[4] = false; //set reset to false
			    			winChecker = true; //make sure board doesn't reset
			    		}
			    		
			    		
			    		if(Error[4] == true){ //successful change so output game in progress and change turn
			    			mo.update2(); //update 2 has it so that the user now has a successful change and can go back to it	
			    			if (m.getTurn() == "red"){ //change turn
					    		m.setTurn("blue");
					    	}
					    	else{
					    		m.setTurn("red");
					    	}
			    		}else if(winChecker != true){	
			    			mo.update(); //go back to last update, unsuccessful change
			    		}
				    	
				    }
		    	}
		    	else{ //if computers turn 
		    		if ((tileX > 1 && tileX < 9) && (tileY > 1 && tileY < 8) && winChecker != true){ //check to see if someone has won
		    			mo.compTurn(m.getTurn()); //complete the computers turn
		    			
		    			
		    			Error[4] = true;
				    	
				    	if(!(mo.checkGravity())){ //GRAVITY ERROR
			    			Error[1] = true;
			    			Error[4] = false;
			    			
			    		}else if(Overlap){
			    			Error[3] = true; //user overlapped colors
			    			Error[4] = false;
			    			
			    			Overlap = false;
			    		}
				    	
				    	
				    	else if (mo.win(Module.board) != "nothing") { //make sure there is a win or draw
			    			if(mo.win(Module.board) == "draw") {  //check to see if draw
				    			Error[2] = true; //draw 
				    		}else{
				    			Error[0] = true;  //win
				    		}
			    			Error[4] = false; //set reset to false
			    			winChecker = true; //make sure board doesn't reset
			    		}
			    		
			    		
			    		if(Error[4] == true){ //successful change so output game in progress and change turn
			    			
			    			mo.update2(); //update 2 has it so that the user now has a successful change and can go back to it	
			    			if (m.getTurn() == "red"){ //change turn
					    		m.setTurn("blue");
					    	}
					    	else{
					    		m.setTurn("red");
					    	}
			    		}else if(winChecker != true){
			    			
			    			mo.update(); //go back to last update, unsuccessful change
			    		}
		    		}
		    		
			    	
					
		    	}
		    }
		    
		    if (mode == 2){ // 2 users
			    if ((tileX > 1 && tileX < 9) && (tileY > 1 && tileY < 8) && winChecker != true){ //check to see if user clicks in game area
			    	if (!Module.board[tileY-2][tileX-2].equals("blank")){
			    		Overlap = true;
			    	}
			    	Module.board = mo.clickInsert(tileY-2,tileX-2, m.userClickBoard()); //insert a blue piece in the clicked box
			    	
			    	
			    	
			    	Error[4] = true;
			    	
			    	if(!(mo.checkGravity())){ //GRAVITY ERROR
		    			Error[1] = true;
		    			Error[4] = false;
		    			
		    		}else if(Overlap){
		    			Error[3] = true; //user overlapped colors
		    			Error[4] = false;
		    			
		    			Overlap = false;
		    		}
			    	
			    	
			    	else if (mo.win(Module.board) != "nothing") { //make sure there is a win or draw
		    			if(mo.win(Module.board) == "draw") {  //check to see if draw
			    			Error[2] = true; //draw 
			    		}else{
			    			Error[0] = true;  //win
			    		}
		    			Error[4] = false; //set reset to false
		    			winChecker = true; //make sure board doesn't reset
		    		}
		    		
		    		
		    		if(Error[4] == true){ //successful change so output game in progress and change turn
		    			mo.update2(); //update 2 has it so that the user now has a successful change and can go back to it	
		    			if (m.getTurn() == "red"){ //change turn
				    		m.setTurn("blue");
				    	}
				    	else{
				    		m.setTurn("red");
				    	}
		    		}else if(winChecker != true){	
		    			mo.update(); //go back to last update, unsuccessful change
		    		}
			    	
			    }
		    }

		    if ((x > 200 && x < 300) && (y > 450) && (mode == 0)) { //Comp
		    	mode = 1;
		    }
		    else if((x > 550) && (y > 450) && (mode == 0)) { //2 Player
		    	mode = 2;
		    }
		    
		    
		    
		    double changeX = (x)/25; //divide into smaller blocks so easier to work with ranges for buttons
		    double changeY = (y-225)/25;
		    if ((changeX > 1 && changeX < 5) && (changeY > 0 && changeY < 9)){
		    	m.userClickOutside(changeY);
		    	if(m.getResult() == "clear"){ //check to see if reseted
		    		m.randomizeTurn(); //Initialize red to go first by brute force
		    		userTurn = m.randomizePlayer();
		    		mo.reset(); //reset the module board
		    		winChecker = false; //set the win variables to false 
		    		Error[0] = false;
		    		Error[4] = false; 
		    		mode = 0; 
		    	}else if(m.getResult() == "save"){ //SAVE
		    		if(mo.win(Module.board).equals("nothing")){
		    			mo.save(m.getTurn(),mode,userTurn.equals(m.getTurn()));	
		    			SaveState[0] = true; //Saved
		    			winChecker = false;
		    			Error[0] = false;
		    			Error[4] = false;
		    		}
			    }
		    	else if(m.getResult() == "load"){ //LOAD
		    		try {
		    			String[] loadfile = new String[3];
		    			loadfile = mo.load();
		 				m.setTurn(loadfile[0]);
		 				mode = Integer.parseInt(loadfile[1]);
						SaveState[1] = true;//Loaded Successfully
						Error[4] = false; //game is not yet in progress
						System.out.println(loadfile[2]);
						if(loadfile[1].equals("1")){
							userTurn = loadfile[2];
						}
					} catch (FileNotFoundException e1) {
						Error[4] = false; //game is not in progress
						SaveState[2] = true;//No Save Found
					}
		    		
		    		winChecker = false;
		    		Error[0] = false;
		    	}
		    }
		    
		    
		    
		    
		}
		    
		    
			
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {

			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	
	}
	
}
