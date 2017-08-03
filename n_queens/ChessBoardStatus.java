package n_queens;

import java.awt.Image;
import java.util.ArrayList;

class ChessBoardStatus {
	
	private ChessBoard currentChessBoard;
	/*
	// private Image queen; //�ѽL�W�ӦZ��icon
	private int currentChessBoardWidth; // ��e�ѽL���e��
	private int currentChessBoardHeight; // ��e�ѽL������
	private int currentChessBoardMinLength; // ��e�ѽL�e�שM���ר�ӭȤ��̤p�����@��
	private int currentCell; // �ѽL��l�j�p
	private int currentSpace; // �ѽL��JPanel��ɤ����d�դj�p
	private int currentGrids;
	private static boolean inCheatMode = false; //�H�ᱱ��@���Ҧ��}�Ҫ�flag �ثe�L�@��
	private Mode gameMode; // �C���Ҧ����A
	*/
	/*
	 * �`�O�z�L�����s��gameMode�ݩʨӶi��ާ@ �n���c �Ҽ{��GameStatusController���O����
	 * �����b�����ܼƳ]�wgameMode�ݩʴN�n
	 * 
	 */

	public Mode getGameMode() {
		return currentChessBoard.getGameMode();
	}

	/**
	 * ��l�ƹC���Ҧ�
	 * 
	 */

	public void initializeGameMode() {
		setGameMode(Mode.none);

	}

	public int getCurrentGrids(){
		return currentChessBoard. getGrids();
	}
		
	/**
	 * ���C���Ҧ�
	 * 
	 * @param mode
	 *            �n�i�J���C���Ҧ�
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
