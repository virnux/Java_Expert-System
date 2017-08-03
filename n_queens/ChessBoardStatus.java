package n_queens;

import java.awt.Image;
import java.util.ArrayList;

class ChessBoardStatus {
	
	private ChessBoard currentChessBoard;
	/*
	// private Image queen; //棋盤上皇后的icon
	private int currentChessBoardWidth; // 當前棋盤的寬度
	private int currentChessBoardHeight; // 當前棋盤的高度
	private int currentChessBoardMinLength; // 當前棋盤寬度和高度兩個值中最小的那一個
	private int currentCell; // 棋盤格子大小
	private int currentSpace; // 棋盤跟JPanel邊界之間留白大小
	private int currentGrids;
	private static boolean inCheatMode = false; //以後控制作弊模式開啟的flag 目前無作用
	private Mode gameMode; // 遊戲模式狀態
	*/
	/*
	 * 總是透過直接存取gameMode屬性來進行操作 要重構 考慮把GameStatusController類別取消
	 * 直接在全域變數設定gameMode屬性就好
	 * 
	 */

	public Mode getGameMode() {
		return currentChessBoard.getGameMode();
	}

	/**
	 * 初始化遊戲模式
	 * 
	 */

	public void initializeGameMode() {
		setGameMode(Mode.none);

	}

	public int getCurrentGrids(){
		return currentChessBoard. getGrids();
	}
		
	/**
	 * 更改遊戲模式
	 * 
	 * @param mode
	 *            要進入的遊戲模式
	 */

	public void setGameMode(Mode mode) {
		currentChessBoard.setGameMode( mode);
		//gameMode = mode;
	}

	public ChessBoardStatus(ChessBoard currentChessBoard) {
		this.currentChessBoard = currentChessBoard;
		//currentChessBoard.addMouseListener(placeQueenHandler);
		//chessMoves = new ArrayList<int[]>();
	}
/*
	public void getCurrentChessBoardInformation() {
		currentChessBoardWidth = currentChessBoard.getChessWidth();
		currentChessBoardHeight = currentChessBoard.getChessHeight();
		currentChessBoardMinLength = currentChessBoard.getChessBoardMinLength();
		currentCell = currentChessBoard.getCell();
		currentSpace = currentChessBoard.getSpace();
		currentGrids = currentChessBoard.getGrids();
	}
*/
	public void setCurrentChessBoardGrids(int grids){
		currentChessBoard.setGrids(grids);
	}
	
	public void refreshChessBoard() {
		currentChessBoard.repaint();
	}
	
	public void newChessBoard(){
		currentChessBoard = new ChessBoard(9);
	}

	public void initializeChessBoard() {
		currentChessBoard.initializeChessBoard();
	}
	
	public int getChessMOveCount(){
		return currentChessBoard.getChessMovesCount();
	}
	
	public void undo(){
		currentChessBoard.undo();
	}
	
	public ArrayList<int[]> getChessMoves(){
		return currentChessBoard.getChessMoves();
	}
	
	public void clearChessMove() {
		currentChessBoard.clearChessMoves();
	}
	
	public void addChessMove(int i, int y){
		currentChessBoard.addChessMove(i, y);
	}
}
