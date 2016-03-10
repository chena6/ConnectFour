package project2aa4;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;


public class Map {
	
	private Random random = new Random();
    private boolean randomizer = random.nextBoolean();
    private String first;
    private String turn; //true = red, false = blue to check whose turn
    private String result; //get the button results
	private String[][] Map = new String[10][13]; //full gui map in block 10*13 with 50px blocks
	
	
	private Image baseBlock, base, baseWall, redC, blueC, redB, blueB, clearB, title, textBox, doneB, lastB, redFadedB, blueFadedB, pFade, pClick, compFade, compClick; //intialize images
	
	public Map(){	 //import all images from the res folder in src	
		ImageIcon img = new ImageIcon("src/res/baseblock.png");
		baseBlock = img.getImage();
		img = new ImageIcon("src/res/base.png");
		base = img.getImage();
		img = new ImageIcon("src/res/basewall.png");
		baseWall = img.getImage();
		img = new ImageIcon("src/res/red.png");
		redC = img.getImage();
		img = new ImageIcon("src/res/blue.png");
		blueC = img.getImage();
		img = new ImageIcon("src/res/buttonRed.png");
		redB = img.getImage();
		img = new ImageIcon("src/res/buttonBlue.png");
		blueB = img.getImage();
		img = new ImageIcon("src/res/buttonFadedRed.png");
		redFadedB = img.getImage();
		img = new ImageIcon("src/res/buttonFadedBlue.png");
		blueFadedB = img.getImage();
		img = new ImageIcon("src/res/buttonClear.png");
		clearB = img.getImage();
		img = new ImageIcon("src/res/title.png");
		title = img.getImage();
		img = new ImageIcon("src/res/textBox.png");
		textBox = img.getImage();
		img = new ImageIcon("src/res/buttonLast.png");
		lastB = img.getImage();
		img = new ImageIcon("src/res/buttonDone.png");
		doneB = img.getImage();
		
		img = new ImageIcon("src/res/button2PFade.png");
		pFade = img.getImage();
		img = new ImageIcon("src/res/button2PClick.png");
		pClick = img.getImage();
		img = new ImageIcon("src/res/buttonCompFade.png");
		compFade = img.getImage();
		img = new ImageIcon("src/res/buttonCompClick.png");
		compClick = img.getImage();
		
		


		if (random.nextBoolean()){
			turn = "red";
			first = "red";
		}
		else{
			turn = "blue";
			first = "blue";
		}
		setupMap(); //setup the gui
	}
	
	//ACCESSOR STATEMENTS TO GET PICTURES + MAP + TURN
	public String getFirstTurn(){
		return first;
	}
	
	public Image getPFade(){
		return pFade;
	}
	public Image getPClick(){
		return pClick;
	}
	public Image getCompFade(){
		return compFade;
	}
	public Image getCompClick(){
		return compClick;
	}
	public Image getTitle(){
		return title;
	}
	
	public Image getTextBox(){
		return textBox;
	}
	
	public Image getBaseBlock(){
		return baseBlock;
	}
	
	public Image getBase(){
		return base;
	}
	public Image getBaseWall(){
		return baseWall;
	}
	
	public Image getRedCoin(){
		return redC;
	}
	public Image getBlueCoin(){
		return blueC;
	}
	public Image getRedFadedCoin(){
		return redFadedB;
	}
	public Image getBlueFadedCoin(){
		return blueFadedB;
	}
	public Image getBlueButton(){
		return blueB;
	}
	public Image getRedButton(){
		return redB;
	}
	public Image getClearButton(){
		return clearB;
	}
	public Image getDoneButton(){
		return doneB;
	}
	public Image getLastButton(){
		return lastB;
	}
	
	public String getMap(int x, int y){
		return this.Map[x][y];
	}
	public String getTurn(){
		return this.turn;
	}
	public String getResult(){
		return this.result;
	}

	public void randomizeTurn(){
		if (random.nextBoolean()){
			turn = "red";
			first = "red";
		}
		else{
			turn = "blue";
			first = "blue";
		}
	}
	
	public String randomizePlayer(){
		if (random.nextBoolean()) {
			return "red";
		}
		else{
			return "blue";
		}
	}
	
	
	//when the user clicks the board, it is either red or blue
	public String userClickBoard(){
		if(turn=="red"){
			return "red";
		}
		else{
			return "blue";
		}
	}
	
	public void setTurn(String col){
		turn = col;
	}
	
	//if the user clicks the buttons on the side, switches turns or resets
	public void userClickOutside(double y){
		if(y < 3){
			result = "clear";
		}
		else if(y > 3 && y < 6){
			result = "save";
		}
		else if(y > 6 && y < 9){
			result = "load";
		}
		else{
			result = "nothing";
		}
	}
	
	//setup up the map with the letters to represent images
	public void setupMap(){
		for(int i=0; i<2; i++){
			for(int j=0; j<13; j++){
				Map[i][j] = "b"; //b == green background block
			}
		}
		for(int i=2; i < 10; i++){
			for(int j=0; j<13; j++){
				if (j<4){
					Map[i][j] = "b";
				}
				else{
					Map[i][j] = "c"; //represent darker shaded game area
				}
			}
		}
	}
	

}
	
	

