package n_queens;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

class ChessBoard extends JPanel{
	private static final long serialVersionUID = 1L;

	private Dimension chessView;
	private GridBagLayout chessBoardLayout;
	private Image queen;              		//�ѽL�W�ӦZ��icon
	private int chessWidth ;          		//��e�ѽL���e��
	private int chessHeight ;         		//��e�ѽL������
	private	int chessBoardMinLength;  		//��e�ѽL�e�שM���ר�ӭȤ��̤p�����@��
	private	int cell;   					//�ѽL��l�j�p
	private int space; 						//�ѽL��JPanel��ɤ����d�դj�p
	private int grids ;
	private int[][] poistionOccupied; // �P�_�ѽL��l�W�O�_���Ѥl
	private ArrayList<int[]> chessMoves; // �Ȯ��x�s�Ѥl�U�L���B��
	private int x; // �ƹ��b�ѽL�W�I����m��X�y��
	private int y; // �ƹ��b�ѽL�W�I����m��Y�y��
	private int coordinateX; // �ѽL��X�y��
	private int coordinateY; // �ѽL��Y�y��
	private Mode gameMode ;
	private ButtonController buttonController;
	//private MessageBoxController messageBoxController;
	private GridBagConstraints constraints_ChessBoardControlPanel;
		
	public ChessBoard(int grids){
		this.grids = grids;			
		this.queen = getToolkit().getImage("queen.jpg");	
		this.chessBoardLayout = new GridBagLayout();
		addMouseListener(placeQueenHandler);
		//this.chessOperation = new ChessOperation(this);
			
		constraints_ChessBoardControlPanel = new GridBagConstraints();
		constraints_ChessBoardControlPanel.gridx = 0;
		constraints_ChessBoardControlPanel.gridy = 0;
		constraints_ChessBoardControlPanel.gridwidth = 4;
		constraints_ChessBoardControlPanel.gridheight = 4;
		constraints_ChessBoardControlPanel.weightx = 50;
		constraints_ChessBoardControlPanel.weighty = 50;
		constraints_ChessBoardControlPanel.fill = GridBagConstraints.BOTH;

		chessBoardLayout.setConstraints(this, constraints_ChessBoardControlPanel);
		setLayout(chessBoardLayout);		
		
		chessView = this.getSize();		
		chessWidth = chessView.width;
		chessHeight = chessView.height;
		
		//�����e�����j�p�̤p�����μe �A�e��8*8�ѽL
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		calculateChessBoardViewMin();		
		initializeChessBoard();
	}
	
	public void initializeChessBoard(){
		setBackground(Color.GRAY);		
		
		//�I�s��l�ƴѽL���A
		poistionOccupied= new int[grids][grids];
		chessMoves = new ArrayList<int[]>();
	}

	private void calculateChessBoardViewMin(){
		if(chessWidth > chessHeight){
			chessBoardMinLength = chessHeight ;			
		}else{
			chessBoardMinLength = chessWidth ;			
		}
	}

	public void setGrids(int grids){
		this.grids = grids;
	}
	
	/**
	 * ��l�ƹC���Ҧ�
	 * 
	 */
	
	public void initializeGameMode(){
		setGameMode(Mode.none);
	}
		
	public void setButtonController(ButtonController buttonController){
		this.buttonController = buttonController;
	}
	
	/**
	 * ���C���Ҧ�
	 * 
	 * @param mode �n�i�J���C���Ҧ�
	 */
	
	public void setGameMode(Mode mode ){
		gameMode = mode;	
	}
	
	public Mode getGameMode( ){
		return this.gameMode;	
	}
	
	/**
	 * ���ѡA�^��W�@��Ѫ��ѽL���A
	 */
	public void undo() {
		int index = chessMoves.size();
		int[] lastmove = chessMoves.get(index - 1);
		int x = lastmove[0];
		int y = lastmove[1];

		chessMoves.remove(index - 1); // �����̫�U�Ӫ����B�� �Ѥl�U�L���Ȧs-1
		poistionOccupied[x][y] = 0; // ��ӨB�Ѫ�poistionOccupied�k0
		buttonController.checkButtonStatus(); //�ˬd���s���A
	}

	/**
	 * �M�żȦs�ѨB
	 * 
	 */
	public void clearChessMove() {
		chessMoves.clear();
	}

	/**
	 * �I���ѽL��b�Ȧs�̲K�[�ѨB
	 * 
	 */
	public void addChessMove(int i, int y) {
		chessMoves.add(new int[] { i, y });
	}

	/**
	 * �Ǧ^�����C���ثe�U�L���ӦZ��
	 * 
	 * @return �����C���ثe�U�L���ӦZ��
	 */

	public int getChessMovesCount() {
		return chessMoves.size();
	}
	
	public ArrayList<int[]> getChessMoves() {
		return this.chessMoves;
	}

	private int getChessBoardWidth(){
		return this.getChessWidth();
	}
	
	private int getChessBoardHeight(){
		return this.getChessHeight();
	}
		
	/**
	 * �ˬd�ثe�o�B�ण�ন�\�\����x�s
	 * 
	 * @param x
	 *            �ثe�o�B�b�ѽL�W��X�y��
	 * @param y
	 *            �ثe�o�B�b�ѽL�W��Y�y��
	 */
	private void recordChessMoves(int x, int y) {
		if (chessMoves != null && chessMoves.size() < 8) { // chessMoves.size()<8
															// 8���ӭn�令N
															// ��N�ӦZ���D�ʺA���X
			// �z�L�ˬdpoistionOccupied�o��flag�ӧP�_��l�O�_�Q�e�� �i�ӱư��w�g�U�L���B��
			if (poistionOccupied[x][y] == 0) {
				poistionOccupied[x][y] = 1;
				chessMoves.add(new int[] { x, y });
				buttonController.checkButtonStatus(); //�ˬd��e���s���A �p�G�ѽL�Ȧs���F�N���"���ҵ���"�s
			}
		}
	}
		
	public void clearChessMoves(){
		this.chessMoves.clear();
	}
	
	private ArrayList<int[]> getCurrentChessMoves(){
		return this.getChessMoves();
	}

	public int getChessWidth(){
		return chessWidth;
	}
	
	public int getChessHeight(){
		return chessHeight;
	}
		
	public int getChessBoardMinLength(){
		calculateChessBoardViewMin();
		return chessBoardMinLength;
	}
	
	public int getSpace(){
		return space;
	}
	
	public int getCell(){
		return cell;
	}
	
	public int getGrids(){
		return grids;
	}
		
	/**
	 * �ˬdJPanel��e���e�A�åH���p��X�̾A�X����e�����j�p���ѽL���e�M�ѽL��l�H�ίd�յ��Ѽ�
	 */
	//��sJPanel�M���e �ѽL��l�j�p���Ѽ�
	public void checkView(){	
		chessView = this.getSize();					//�ˬdJPanel��e���e
		chessWidth = chessView.width;
		chessHeight = chessView.height;
		
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidfth > chessHeight)��b�ӱ`�Ψ�F
		if(chessWidth > chessHeight){
			chessBoardMinLength = chessHeight;
			cell = chessBoardMinLength/grids;       //�ѽL��l�j�p = ��e�ѽL�e�שM���ר�ӭȤ��̤p�����@�Ӱ��H�ѽL����
			space= (chessWidth-chessHeight)/2;      //�d�լO��ѽL�m�� ���䳣���ۦP�j�p���d�հ϶�
		}else{
			chessBoardMinLength = chessWidth;
			cell = chessBoardMinLength/grids;
			space= (chessHeight-chessWidth)/2;
		}
	}
	
	/**
	 * �e�ѽL��l
	 * 
	 * @param g
	 */

	public void drawGrids(Graphics g){
		
		chessView = this.getSize();
		chessWidth = chessView.width;
		chessHeight = chessView.height;
		
		//�����e�����j�p�̤p�����μe �A�e��N*N�ѽL
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		if(chessWidth > chessHeight){
			chessBoardMinLength = chessHeight ;
			cell = chessBoardMinLength/grids;
			space= (chessWidth-chessHeight)/2;          //�|�P�d�� �ݪ���e���Ӥ���� ����N�ݭn�d�� 
				                                        //�a�W�[�d�հϰ�]�wø�Ϫ�l��m�s�y�m���ĪG							
			//�W��				
			paintWhiteGrids(g);
			paintBlackGrids(g);	
		}else{
			chessBoardMinLength = chessWidth ;			
			cell = chessBoardMinLength/grids;
			space= (chessHeight-chessWidth)/2;
															
			paintWhiteGrids(g);
			paintBlackGrids(g);							
		}										
	}
	
	/**
	 * �e�ѽL��u
	 * 
	 * @param g
	 */

	public void drawLines(Graphics g){
		g.setColor(Color.BLACK);
		
		//�@���e�@��������@��� �ѽL���X���N�e�X��
		for(int i = 0;i<=grids;i++){	
			chessView = this.getSize();
			chessWidth = chessView.width;
			chessHeight = chessView.height;
		
			//�����e�����j�p�̤p�����μe �A�e��8*8�ѽL
			//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
			if(chessWidth > chessHeight){
				chessBoardMinLength = chessHeight ;
				cell = chessBoardMinLength/grids;
				//�Ҽ{��space�ʸ˰_�� space = ����e������� �o�˴N���ݭn�P�_���e���Ӫ��Ȥ���j�F
				space= (chessWidth-chessHeight)/2;          //�|�P�d�� �ݪ���e���Ӥ���� ����N�ݭn�d�� 
				                                            //�a�W�[�d�հϰ�]�wø�Ϫ�l��m�s�y�m���ĪG							
				g.drawLine(0+space, i*cell , grids*cell+space, i*cell);		
				//��u �q(space,i*cell)��(grids*cell+space,i*cell)���e�@�����u
				//i*cell�N��F�C��for�j�驹�U�e�U�@�����u��Y�y�Ф@�w�O��W�@��((i-1)*cell)�Z���@�ӴѽL�檺�Z��
				g.drawLine(i*cell+space, 0 , i*cell+space, grids*cell);		
				//���u �q(i*cell+space,0)��(i*cell+space,grids*cell)���e�@�����u
				//+space�O�ҵe�X�ӷ|�bJPanel������	
			}else{
				chessBoardMinLength = chessWidth ;
				cell = chessBoardMinLength/grids;
				space= (chessHeight-chessWidth)/2;		    //�|�P�d�� �ݪ���e���Ӥ���� ����N�ݭn�d�� 
															//�a�W�[�d�հϰ�]�wø�Ϫ�l��m�s�y�m���ĪG																		
				g.drawLine(0, i*cell+space , grids*cell, i*cell+space);		//��u
				g.drawLine(i*cell, 0+space , i*cell, grids*cell+space);		//���u
			}								
		}
	}
	
	//�e�_�Ʈ檺�ѽL�|�X���D column�|�h�@�� 
	//M���{���X �Ҽ{���c
	/**
	 * �e�X�¦⪺�ѽL��l
	 * 
	 * @param g
	 */
	public void paintBlackGrids(Graphics g){
		g.setColor(Color.BLACK);
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		if(chessWidth > chessHeight){
			for(int i=0 ; i<grids ;i++){   //Y��V�`�� �`��grids��
				//�Ĥ@�� �ˬd�_�Ʀ�
				for(int j=0 ;j<grids ;j++){  //X��V�`�� �e�_�Ʀ檺�®�
					if(j%2!=0){
						g.fillRect(0+space+j*cell, i*cell , cell, cell);									   						
					}	
				}
				//�ĤG�� �ˬd���Ʀ�   ++i�T�O���|�h�L�@��row
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2==0){   //X��V�`�� �e���Ʀ檺�®�
							g.fillRect(0+space+j*cell, i*cell , cell, cell);																	
						}					
					}
				}					
			}				
		}else{     //Height > Width
			for(int i=0 ; i<grids ;i++){   //Y��V�`�� �`��grids��
				//�Ĥ@�� �ˬd�_�Ʀ�
				for(int j=0 ;j<grids ;j++){  //X��V�`�� �e�_�Ʀ檺�®�
					if(j%2!=0){
						g.fillRect(j*cell, 0+space+i*cell, cell, cell);				
					}
				}							
				//�ĤG�� �ˬd���Ʀ�   ++i�T�O���|�h�L�@��row
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2==0){   //X��V�`�� �e���Ʀ檺�®�
							g.fillRect(j*cell, 0+space+i*cell, cell, cell);
						}	
					}
				}									
			}
		}
	}
	
	/**
	 * �e�X�զ⪺�ѽL��l
	 * 
	 * @param g
	 */
	public void paintWhiteGrids(Graphics g){
		g.setColor(Color.WHITE);
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		if(chessWidth > chessHeight){
			for(int i=0 ; i<grids ;i++){   //Y��V�`�� �`��grids��
				//�Ĥ@�� �ˬd�_�Ʀ�
				for(int j=0 ;j<grids ;j++){  //X��V�`�� �e�_�Ʀ檺�ծ�
					if(j%2==0){
						g.fillRect(0+space+j*cell, i*cell , cell, cell);									   						
					}						
				}							
				//�ĤG�� �ˬd���Ʀ�   ++i�T�O���|�h�L�@��row
				if(++i<grids){				
					for(int j=0 ; j<grids ;j++){				
						if(j%2!=0){   //X��V�`�� �e���Ʀ檺�ծ�
							g.fillRect(0+space+j*cell, i*cell , cell, cell);																	
						}		
					}		
				}
			}
		}else{     //Height > Width
			for(int i=0 ; i<grids ;i++){   //Y��V�`�� �`��grids��
				//�Ĥ@�� �ˬd�_�Ʀ�
				for(int j=0 ;j<grids ;j++){  //X��V�`�� �e�_�Ʀ檺�ծ�
					if(j%2==0){
						g.fillRect(j*cell, 0+space+i*cell, cell, cell);								
					}
				}			
				//�ĤG�� �ˬd���Ʀ�   ++i�T�O���|�h�L�@��row				
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2!=0){   //X��V�`�� �e���Ʀ檺�ծ�
							g.fillRect(j*cell, 0+space+i*cell, cell, cell);
						}													
					}	
				}
			}
		}		
	}
	
	/**
	 * �e�X�Ѥl�B�ƼȦs�����Ҧ��ӦZ
	 * 
	 * @param g
	 */
	
	private void drawAllQueens(Graphics g){
		checkView();
		//�Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		if(chessWidth > chessHeight){	
			Iterator<int[]> it = getCurrentChessMoves().iterator();	
			while(it.hasNext()){				
				int[] moves  = it.next();				
				//�}�o�ɼȮɥN���ӦZ�ϼЪ��L�Ѥl��k
				//g.fillRect(0+space+moves[0]*cell,moves[1]*cell,cell,cell);				
				g.drawImage(queen, 0+space+moves[0]*cell, moves[1]*cell, cell, cell, this);
			}		
		}else{  //Height > Width
			Iterator<int[]> it = getCurrentChessMoves().iterator();	
			while(it.hasNext()){				
				int[] moves  = it.next();				
				//g.fillRect(moves[0]*cell,0+space+moves[1]*cell,cell,cell);	
				g.drawImage(queen, moves[0]*cell, 0+space+moves[1]*cell, cell, cell, this);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);	
		
		
		drawGrids(g);
		drawAllQueens(g);
		drawLines(g);
		//���e�ѽL��A�A�e�Ѥl�A�̫�e�u
		//���M���e�Ѥl���ܡA�Ѥl�|�Q�ѽL��\���C�P�z�A�u���̫�e���ܷ|�Q�Ѥl��ѽL�\��		
	}
	
	private MouseListener placeQueenHandler = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			
			if (gameMode == Mode.normal) { // �C���Ҧ��U�~�i�\��Ѥl
				//getCurrentChessBoardInformation();
				// �Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth >
				// chessHeight)��b�ӱ`�Ψ�F
				if (getChessBoardWidth() > getChessBoardHeight()) {
					checkView();
					if (x <= getChessBoardMinLength() + getSpace() && x >= 0 + getSpace()
							&& y <= getChessBoardMinLength() && y >= 0) {
						coordinateX = getXCoordinate(x);
						coordinateY = getYCoordinate(y);
						recordChessMoves(coordinateX, coordinateY);
						repaint();
					}
				} else { // chessHeight > chessWidth
					checkView();
					if (x <= getChessBoardMinLength() && x >= 0 && y <= getChessBoardMinLength() + getSpace()
							&& y >= 0 + getSpace()) {
						coordinateX = getXCoordinate(x);
						coordinateY = getYCoordinate(y);
						recordChessMoves(coordinateX, coordinateY);
						repaint();
					}
				}
			}
		}
	};

	/**
	 * �p���e�ƹ��I����m�����쪺�ѽLX�y��(0~7)
	 * 
	 * @param x
	 *            ��e�ƹ��b����W�I����X�y��
	 * @return �ƹ��I������m������ѽL�W��X�y��
	 */
	private int getXCoordinate(int x) {
		// �Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		//getCurrentChessBoardInformation();
		checkView();
		if ( getChessBoardWidth() > getChessBoardHeight()) {
			for (int i = 0; i < getGrids(); i++) {
				if (x >= i * getCell()+ getSpace() && x < (i + 1) * getCell()+ getSpace())
					return i;
			}
			return getGrids() - 1; // ���p��cell�ɩ������p���I�i��y���᭱���{�ɰ϶��P�w���~ �areturn
										// grids-1�ӭץ�
			// �p�G�ѽL���Ƨ�j���Ӥ���o�˧� �Ҽ{��cell��o���T �ݯण����p���I��ĤG��
		} else { // chessHeight > chessWidth
			for (int i = 0; i < getGrids(); i++) {
				if (x >= i * getCell() && x < (i + 1) * getCell())
					return i;
			}
			return getGrids() - 1;
		}
	}

	/**
	 * �p���e�ƹ��I����m�����쪺�ѽLY�y��(0~7)
	 * 
	 * @param y
	 *            ��e�ƹ��b����W�I����Y�y��
	 * @return �ƹ��I������m������ѽL�W��Y�y��
	 */
	private int getYCoordinate(int y) {
		// �Ҽ{��o�ӧP�_�M���򪺰���ʸ˰_�ө�b����i�����a�� �]��if(chessWidth > chessHeight)��b�ӱ`�Ψ�F
		//getCurrentChessBoardInformation();
		checkView();
		if ( getChessBoardWidth() > getChessBoardHeight()) {
			for (int i = 0; i < getGrids(); i++) {
				if (y >= i * getCell() && y < (i + 1) * getCell())
					return i;
			}
			return getGrids()-1; // ���p��cell�ɩ������p���I�i��y���᭱���{�ɰ϶��P�w���~ �areturn
										// grids-1�ӭץ�
			// �p�G�ѽL���Ƨ�j���Ӥ���o�˧� �Ҽ{��cell��o���T �ݯण����p���I��ĤG��
		}else { // chessHeight > chessWidth
			for (int i = 0; i < getGrids(); i++) {
				if (y >= i * getCell() + getSpace() && y < (i + 1) * getCell() + getSpace())
					return i;
			}
			return getGrids()-1;
		}
	}
}