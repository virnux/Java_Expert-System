package n_queens;

import java.util.ArrayList;
import java.util.List;

abstract class NQueensQuerySolutionDemo {
	protected String ruleBase;
	protected Prolog prolog;
	protected MessageBoxController messageBoxController;
	protected ChessBoardStatus chessBoardStatus;
	protected LeftControlPanel leftControlPanel;
	protected int demoCount = -1;				//����t��k�Ҧ��Ushow�X��N�ոѱ���X��
	protected List<String> tempAnswer = new ArrayList<String>();  //�s���SWI Prolog�d�ߤK�ӦZ���D�᪺�U�إi���
	
	public void setCommand(String ruleBase , MessageBoxController messageBoxController, ChessBoardStatus chessBoardStatus, LeftControlPanel leftControlPanel){
		prolog = new Prolog();
		this.chessBoardStatus = chessBoardStatus;
		this.messageBoxController = messageBoxController;
		this.ruleBase = ruleBase;
		this.leftControlPanel = leftControlPanel;
	}

	public void setCommand(String ruleBase , MessageBoxController messageBoxController, ChessBoardStatus chessBoardStatus){
		prolog = new Prolog();
		this.chessBoardStatus = chessBoardStatus;
		this.messageBoxController = messageBoxController;
		this.ruleBase = ruleBase;		
	}
	
	public List inferNqueensSolutionDemo(String query){
		prolog.link(ruleBase); // �Pprolog�ɮ׶i��s��
		List result = prolog.drive_demo(query); // �I�sProlog���O������k�öǦ^�i�檺���צ�C
		return result;
	}
	
	public void repaintChessBoard(int grids){
		chessBoardStatus.setCurrentChessBoardGrids(grids);
		chessBoardStatus.refreshChessBoard();
	}
		
	abstract void inferNQueensSolutionNum();	
	
	abstract void NQueensLastSolutionDemo(int demoGrids);
	
	abstract void NQueensNextSolutionDemo(int demoGrids);

	public void flagCountAddOne(){
		demoCount = demoCount+1;
	}
	
	public void cleardemoCount(){
		this.demoCount =-1;
	}
	
	public int getDemoCount(){
		return demoCount;
	}
	
	public void flagCountMinus(){
		if(demoCount >= 0){
			demoCount = demoCount-1;
		}
	}
	
	protected void disabledShowAnswerButton(int solutionCounts) {
		if (demoCount <= 0) {
			leftControlPanel.disabledShowLastAnswer();
		} else if (demoCount == solutionCounts) {
			leftControlPanel.disabledShowNextAnswer();
		}
	}
}
