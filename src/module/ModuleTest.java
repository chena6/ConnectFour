package module;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import module.Module;

public class ModuleTest {
	String [][] boardTest1 = new String[6][7];
	String [][] boardTest2 = new String[6][7];
	String [][] boardTest3 = new String[6][7];
	String [][] boardTest4 = new String[6][7];
	String [][] boardTest5 = new String[6][7];
	String [][] boardTest6 = new String[6][7];
	String [][] boardTest7 = new String[6][7];
	String [][] emptyBoard = new String[6][7];
	
	@Before
	public void setUp() throws Exception {
		//initialize empty ("blank") board
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				boardTest1[i][j] = "blank";
				boardTest2[i][j] = "blank";
				boardTest3[i][j] = "blank";
				boardTest4[i][j] = "blank";
				boardTest5[i][j] = "blank";
				boardTest6[i][j] = "blank";
				boardTest7[i][j] = "blank";
				emptyBoard[i][j] = "blank";
			}
		}
	}
	Module test1 = new Module(boardTest1);
	Module test2 = new Module(boardTest2);
	Module test3 = new Module(boardTest3);
	Module test4 = new Module(boardTest4);
	Module test5 = new Module(boardTest5);
	Module test6 = new Module(boardTest6);
	Module test7 = new Module(boardTest7);
	
	
	@Test
	public void test() throws InvalidMove {
		
		//test clickInsert
		test1.clickInsert(5, 0, "red");
		assertEquals(test1.board[5][0],"red");
		
		//test isEmpty
		assertEquals(test1.isEmpty(5,1),true);
		assertEquals(test1.isEmpty(5,0),false);
		
		//test gravity
		test1.clickInsert(2, 2, "blue");
		assertEquals(test1.checkGravity(),false);
		
		//test win
		//horizontal
		test2.clickInsert(5,2,"red");
		test2.clickInsert(5,3,"red");
		test2.clickInsert(5,4,"red");
		test2.clickInsert(5,5,"red");
		assertEquals(Module.win(test2.board),"red");
		
		//vertical
		test3.clickInsert(5, 2, "blue");
		test3.clickInsert(4, 2, "blue");
		test3.clickInsert(3, 2, "blue");
		test3.clickInsert(2, 2, "blue");
		assertEquals(Module.win(test3.board),"blue");
		
		//diagonal
		test4.clickInsert(5, 0, "red"); test4.clickInsert(5, 1, "blue"); test4.clickInsert(4, 1, "red");
		test4.clickInsert(5, 2, "blue"); test4.clickInsert(5, 3, "red"); test4.clickInsert(4, 2, "blue");
		test4.clickInsert(3, 2, "red"); test4.clickInsert(4, 3, "blue"); test4.clickInsert(4, 0, "red");
		test4.clickInsert(3, 3, "blue"); test4.clickInsert(2, 3, "red");
		assertEquals(Module.win(test4.board),"red");
		
		//a draw
		test5.clickInsert(5,6,"red"); test5.clickInsert(4,6,"blue");
		test5.clickInsert(3,6,"red"); test5.clickInsert(2,6,"blue");
		test5.clickInsert(1,6,"red"); test5.clickInsert(0,6,"blue");
		
		test5.clickInsert(5,5,"red"); test5.clickInsert(4,5,"blue");
		test5.clickInsert(3,5,"red"); test5.clickInsert(2,5,"blue");
		test5.clickInsert(1,5,"red"); test5.clickInsert(0,5,"blue");
		
		test5.clickInsert(5,4,"red"); test5.clickInsert(4,4,"blue");
		test5.clickInsert(3,4,"red"); test5.clickInsert(2,4,"blue");
		test5.clickInsert(1,4,"red"); test5.clickInsert(0,4,"blue");
		
		test5.clickInsert(5,3,"blue"); test5.clickInsert(4,3,"red");
		test5.clickInsert(3,3,"blue"); test5.clickInsert(2,3,"red");
		test5.clickInsert(1,3,"blue"); test5.clickInsert(0,3,"red");
		
		test5.clickInsert(5,2,"blue"); test5.clickInsert(4,2,"red");
		test5.clickInsert(3,2,"blue"); test5.clickInsert(2,2,"red");
		test5.clickInsert(1,2,"blue"); test5.clickInsert(0,2,"red");
		
		test5.clickInsert(5,1,"red"); test5.clickInsert(4,1,"blue");
		test5.clickInsert(3,1,"red"); test5.clickInsert(2,1,"blue");
		test5.clickInsert(1,1,"red"); test5.clickInsert(0,1,"blue");
		
		test5.clickInsert(5,0,"red"); test5.clickInsert(4,0,"blue");
		test5.clickInsert(3,0,"red"); test5.clickInsert(2,0,"blue");
		test5.clickInsert(1,0,"red"); test5.clickInsert(0,0,"blue");
		
		assertEquals(Module.win(test4.board),"draw");
		
		
		//computer blocking
		test6.clickInsert(5, 1, "red");
		test6.clickInsert(5, 2, "red");
		test6.clickInsert(5, 3, "red");
		assertEquals(true,test6.compBlock("red", "blue", test6.board));
		
		//computer winning
		test7.clickInsert(5, 1, "red");
		test7.clickInsert(5, 2, "red");
		test7.clickInsert(5, 3, "red");
		assertEquals(true,test6.compWin("red", test7.board));
		
		//computerPlace cannot be tested, since it is randomized 
		
		//test reset (new game)
		test1.reset();
		test2.reset();
		test3.reset();
		test4.reset();
		test5.reset();
		test6.reset();
		test7.reset();
		//everything is empty
		assertEquals(emptyBoard,test1.board);
		assertEquals(emptyBoard,test2.board);
		assertEquals(emptyBoard,test3.board);
		assertEquals(emptyBoard,test4.board);
		assertEquals(emptyBoard,test5.board);
		assertEquals(emptyBoard,test6.board);
		assertEquals(emptyBoard,test7.board);
		
	}
}
