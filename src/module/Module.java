package module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Module {
	//initialized 6x7 board with null states
	//0,0 is top left
	public static String[][] board = new String[6][7];
	public static String[][] backup = new String[6][7];
	
	public Module(String[][] newBoard){
		this.board = newBoard;
	}

	public boolean isEmpty(int r, int c){
		//if empty at r and c return true
		if (board[r][c].equals("blank")){
				return true;
		} 
		else{
			return false;
		}
	}
	
	public String[][] clickInsert(int a, int b, String col){
		board[a][b] = col; //insert piece into board
		return board;
	}
	
	public boolean checkGravity(){
		//if it's empty, check that under is filled
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				int counter = 1;
				
				if (!board[i][j].equals("blank")){ //if there is a piece there
					while ((i+counter)!= 6){
						if(board[i+counter][j].equals("blank")){ //check all the pieces below until it hits bottom
							return false;
						}else{
							
							counter++;
						}
					}
				}
				
			}
		}
		return true;
	}
	
	public String[][] reset() {
		//reset game, set all positions to blank
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				board[i][j] = "blank";
				backup[i][j] = "blank";
			}
		}
		return board;
	}
	
	
	public void update() {
		//update the board
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				board[i][j] = backup[i][j];
				
			}
		}
	}
	
	public void update2() {
		//update the backup
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				backup[i][j] = board[i][j];
				
			}
		}
	}
	
	public void save(String col, int mode, boolean turn){
		//save the board to file
		PrintStream saveFile;
		try {
			saveFile = new PrintStream(new File("src/res/save1.sav"));
			
			for (int i=0; i<6; i++){ //transfer data from board to file
				for (int j=0; j<7; j++){
					saveFile.println(board[i][j]);	
				}
			}
			saveFile.println(mode);		//saves game mode - comp or 2p
			saveFile.println(col);		//saves turn colour
			saveFile.println(turn);		//saves turn colour
			saveFile.close();
			
		} catch (FileNotFoundException e) {
			//File is created if not found
		}
			
		
	}
	
	public String[] load() throws FileNotFoundException {
		//load the save from txt
		Scanner loadFile = new Scanner(new File("src/res/save1.sav"));
		String str,temp, turn;
		String[] state = new String[3];
		
		for (int i=0; i<6; i++){ //transfer data to board from file
				for (int j=0; j<7; j++){
					str = loadFile.next();
					board[i][j] = str;
				}
			}
		
			state[1] = loadFile.next();  //loads the game mode - comp or 2p
			str = loadFile.next();		
			turn = loadFile.next();
			loadFile.close();
			update2();
			
			if(str.equals("red")){state[0] = "red";}	//loads the turn colour
			else if(str.equals("blue")){state[0] = "blue";}

			if(turn.equals("true")){state[2] = "red";}	//diferentiates the human player from the computer player 
			else if(turn.equals("false")){state[2] = "blue";}
			
			return state;
	}
	
	public static String win(String[][] b){
		
		String state = null;
		boolean full = true;
		for (int i = 5; i >=0; i--){ //setup x
			for (int j = 6; j >=0; j--){ //setup y
				if (b[i][j].equals("blank")){
					full = false;
				}
				if (b[i][j].equals("red") || b[i][j].equals("blue")){ //if you find a coin on board go to next step
					
					state = b[i][j];
					int temp1 = 1;
					int temp2 = 1;
					int temp3 = 1;
					int temp4 = 1;
					while(temp1 < 4){ // checking vertical down to up
						if (i-temp1 >= 0){ //make sure no error at top
							if ((b[i-temp1][j] != state) || ((i-temp1) <= 0 && temp1 == 2)){ //break if doesn't equal same thing or if end of column
								temp1 = 5;
							}else{
							temp1++;
							}
						}else{
							temp1 = 5; //break if at top
						}
						
					}
					if (temp1 == 4){  //if 4 vertical pieces return true
						return state;
					}
					if (j > 2){ // checking horizontal
						while(temp2 < 4 ){
							if (!(b[i][j-temp2].equals(state)) ){ //break if doesn't equal same thing or if end of column
								temp2 = 5;
							}else{
							temp2++;
							}
						}
					}
					if (temp2 == 4){ // if 4 horizontal pieces return true
						return state;
					}
					
					if (j >2){ //diagonal bottom right to top left
						while(temp3 < 4 ){ 
							if (i-temp3 >= 0){ //make sure diagonal doesn't break code at top
								if (!(b[i-temp3][j-temp3].equals(state))){ //break if doesn't equal same thing or if end of column
									temp3 = 5;
								}else{
								temp3++;
								}
							}else{
								temp3 = 5; //if at top
							}
						}
					}
					if (temp3 == 4){
						return state;
					}
					if (j < 5){ //diagonal bottom left to top right
						while(temp4 < 4 ){
							if (j+temp4 < 7 && i-temp4 >= 0){
								if ((b[i-temp4][j+temp4] != state)){ //break if doesn't equal same thing or if end of column
									temp4 = 5;
								}else{
								temp4++;
								}
							}
							else{
								temp4 = 5; //if at top
							}
						}
					}
					if (temp4 == 4){
						return state;
					}
				}
			}
		}
		if (full){
			return "draw";
		}
		return "nothing";
	}
	
	
	//implemented a computer turn after the user turn
	public void compTurn(String colour) {
		String ucolour = ""; //user colour
		boolean cBlock = false; //check to see if computer can block
		boolean cWin = false; //check to see if computer can win
		//assign user colour
		if (colour.equalsIgnoreCase("red")){ //if computer is red
			ucolour = "blue"; //user is blue
		}
		else{
			ucolour = "red"; //else user is red
		}
		//check if need to block
		cWin = compWin(colour, board); //check to see if computer can win
		if (!cWin){ //if not check to see if computer can block
			
			cBlock = compBlock(ucolour, colour, board);
		}
		if (!cBlock && cWin == false){ //if computer cannot block or win
			board = compPlace(colour, board); //place a piece strategically
		} 
		
			
	}
	//check to see if computer can win
	public boolean compWin(String colour, String[][] b){
		
		int tempHolder = 0; 
		for (int j = 6; j >=0; j--){ //setup y
			for (int i = 5; i >=0; i--){//setup x
				if (board[i][j].equals("blank")){ //if the spot is empty
					board[i][j] = colour;  //put a piece there
					tempHolder = i; //keep the position in a temporary var
					i = -1; //break out of column
				}
				String result = win(b); //check to see if its a winning board
				
								
				if (result.equals(colour)){ //if it is a winning board of the comptuer colour
					return true;  
				}
				else{
					board[tempHolder][j] = backup[tempHolder][j]; //else return the board to the previous state without a new piece
				}
			}
			
		}return false; //if can't win, return false
	}
	
	public boolean compBlock(String ucolour, String colour, String[][] b){ //check to see if the computer can block

		int tempHolder = 0; 
		for (int j = 6; j >=0; j--){ //setup y
			for (int i = 5; i >=0; i--){//setup x
				if (board[i][j].equals("blank")){ //check to see where its blank
					board[i][j] = ucolour; //put the user colour piece there
					tempHolder = i; //hold spot in temp var
					i = -1;
				}
				String result = win(board); //check to see if user can win
				

				
				if (result.equals(ucolour)){ //if user can win
					board[tempHolder][j] = colour; //replace that piece with a computer piece
					return true; //successfully blanked
				}
				else{
					board[tempHolder][j] = backup[tempHolder][j]; //return the board back to normal
				}
			}
			
		}
		return false;
	}
	
	public String[][] compPlace(String colour, String[][] b){
		int counter = 1;
		boolean loopbreak = false; //a loop to check if a piece has been placed
		Random rand = new Random(); 
		for (int i = 5; i >=0; i--){ //setup y
			for (int j = 6; j >=0; j--){ //setup x
				int randomPicker = rand.nextInt(3); //initialize a randomizer from 0-2
				
				if (b[i][j].equals(colour) && (!loopbreak)){ //check if there is a piece on the board and if a piece has been placed this turn
					if ((i > 0) && (board[i-1][j].equals("blank")) && (randomPicker == 0)){ //check border and then up
						b[i-1][j] = colour; //place colour piece on top
						loopbreak = true; 
					}
					else if ((j < 6) && board[i][j+1].equals("blank") && (randomPicker == 1)){ //check border and then right
						b[i][j+1] = colour; //place piece to the right
						while (!checkGravity() && loopbreak == false){ //check to see if gravity is still set
							b[i][j+1] = "blank"; //if not return it back to normal
							b[i+counter][j+1] = colour; //put a piece under it
							counter++; //update the counter to put below
							loopbreak = true; //break the loop
						}
						loopbreak = true;
					}
					else if ((j > 0) && board[i][j-1].equals("blank") && (randomPicker == 2)){ //check border and then left
						b[i][j-1] = colour; //place piece to the left
						while (!checkGravity() && loopbreak == false){ //check to see if gravity is still set
							b[i][j-1] = "blank"; //if not return piece back to normal
							b[i+counter][j-1] = colour; //place piece under it
							counter++; //update the counter to put it below
							
							loopbreak = true; 
						}
						loopbreak = true;
					}
				}
			}
		}
		//random int
		//if you cannot find a piece, randomize a column to put the piece there
		
		
		int randomNum = rand.nextInt(7); //randomize through all the columns
		//place random
		if (!loopbreak){ //if a piece hasn't been placed
			for (int i = 5; i >=0; i--){ //setup y
				for (int j = 6; j >=0; j--){ //setup x
					if ((i+1 == 6) || !(b[i+1][randomNum].equals("blank"))){ //if the piece below is not blank
						if (b[i][randomNum].equals("blank")){ //if the spot is blank there
							b[i][randomNum] = colour; //place the computer piece there
							//break
							i=-1; //break out of loop
							j=-1; //break out of loop
							return b; //return the board
						}
					}
				}
			}
		}
		return b; //return the board
	}
	
}
	