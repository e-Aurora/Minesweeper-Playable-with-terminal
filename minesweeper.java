package eflayilmaz;

import java.util.Random;
import java.util.Scanner;

public class minesweeper {

	static public void main(String [] args) {
		playGame();
	}
	
	static public void printBoard(String[][] Mine, Difficulty diff) {
		for(int i=0;i<diff.row;i++) {
			for(int j=0;j<diff.column;j++) {
				System.out.printf("%3s", Mine[i][j]);
			}
			System.out.println("");
		}
	}
	static public void playGame() {
		Scanner a = new Scanner(System.in);
		Boolean flagMode = false;
		System.out.println("Choose difficulty [Easy],[Normal],[Hard]");
		String Diff = a.next();
		Difficulty diff = null;
		String[][] Order = null;
		String[][] Mine = null;
		
		if(Diff.equals("Easy")) {
			diff = new Difficulty(10,9,9);
			Order = diff.setDiff();
			Mine = new String[diff.row][diff.column];
		}
		
		if(Diff.equals("Normal")) {
			diff = new Difficulty(50,16,16);
			Order = diff.setDiff();
			Mine = new String[diff.row][diff.column];
		}
		
		if(Diff.equals("Hard")) {
			diff = new Difficulty(100,16,30);
			Order = diff.setDiff();
			Mine = new String[diff.row][diff.column];
		}
		
		
		for(int i=0;i<diff.row;i++) {
			for(int j=0;j<diff.column;j++) {
				Mine[i][j]="X";
			}
		}
		
		printBoard(Mine,diff);
		
		Control c = new Control();
		
		while(c.ControlWin(Mine,diff)){
			System.out.println("Choose location: ");
			int x = a.nextInt();
			int y = a.nextInt();
			Click(Order,Mine,x-1,y-1,diff,flagMode);
			printBoard(Mine,diff);	
		}
		
		if(c.won == true){
			System.out.println("You Won!");
		}
		else{
			System.out.println("You Lost!");
		}
		
		
		a.close();
	}
	
	
	
	static public void Dig(String [][]Mine,String [][] Order,int x,int y) {
		if(Mine[x][y]=="X")
		Mine[x][y]=Order[x][y];
		else {
			int f=0;
			for(int i=x-1;i<x+2;i++) {
				for(int j=y-1;j<y+2;j++) {
					try {
						if(Mine[i][j]=="🚩") {
							f++;
						}
					}
					catch(Exception e) {
						
					}
				}
			}
			if(f==Integer.parseInt(Mine[x][y])) {
				for(int i=x-1;i<x+2;i++) {
					for(int j=y-1;j<y+2;j++) {
						try {
							Mine[i][j]=Order[i][j];
							}
						catch(Exception e) {
							
						}
					}
				}
			}
		}
		if (Mine[x][y]=="0") {
			for(int i=x-1;i<x+2;i++) {
				for(int j=y-1;j<y+2;j++) {
					try {
						if(Mine[i][j]=="X" && Order[i][j]=="0"){
							Dig(Mine,Order,i,j);
						}
						if(Mine[i][j]!="💣") {
							Mine[i][j]=Order[i][j];
						}
					}
					catch(Exception e) {
						
					}
				}
			}
			
		}
	}
	
	static public void Click(String[][]Order,String[][]Mine,int x,int y,Difficulty diff,Boolean flagMode) {
		if(flagMode==true && Mine[x][y]=="X") {
			Mine[x][y]="🚩";
		}
		else if(flagMode==false){
			if(Order[x][y]!="💣") {
			Dig(Mine,Order,x,y);
			}
			else {
				Mine[x][y]=Order[x][y];
			}
		}
	}
}

class Control {
	Boolean won;
	
	Boolean ControlWin(String[][]Mine,Difficulty diff) {
		int c=0;
		for(int i=0;i<diff.row;i++) {
			for(int j=0;j<diff.column;j++) {
				if(Mine[i][j]=="X" || Mine[i][j]=="🚩") {
					c++;
				}
				if(Mine[i][j]=="💣") {
					won = false;
					return false;
				}
					
			}
		}
		if(c==diff.mine) {
			won = true;
			return false;
		}
		else
			won = false;
		
		return true;
	}
}
class Difficulty {
	int mine;
	int row;
	int column;
	
	Difficulty(int m, int r, int c){
		mine=m;
		row=r;
		column=c;		
	}
	
	void boardPrint(String[][]Board) {
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				System.out.printf("%3s", Board[i][j]);
			}
			System.out.println();
		}
	}
	
	String [][] setDiff(){
		String [][] Order = new String [row][column];
		Random r = new Random();
		
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				Order[i][j]="0";
			}
		}
		
		for(int i=0;i<mine;i++) {
			int Row=r.nextInt(row);
			int Column=r.nextInt(column);
			if(Order[Row][Column]!="💣")
			Order[Row][Column]="💣";
			else
				i--;
		}
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				if (Order[i][j] == "💣") {
					for(int a=i-1;a<i+2;a++) {
						for(int b=j-1;b<j+2;b++) {
							try {
								if(Order[a][b]!="💣")
								Order[a][b] = String.valueOf(Integer.parseInt(Order[a][b])+1);
							}
							catch(Exception e) {
					
							}
						}
					}
				}
			}
		}
		return Order;
	}
}

