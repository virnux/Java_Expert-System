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
	private Image queen;              		//棋盤上皇后的icon
	private int chessWidth ;          		//當前棋盤的寬度
	private int chessHeight ;         		//當前棋盤的高度
	private	int chessBoardMinLength;  		//當前棋盤寬度和高度兩個值中最小的那一個
	private	int cell;   					//棋盤格子大小
	private int space; 						//棋盤跟JPanel邊界之間留白大小
	private int grids ;
	private int[][] poistionOccupied; // 判斷棋盤格子上是否有棋子
	private ArrayList<int[]> chessMoves; // 暫時儲存棋子下過的步數
	private int x; // 滑鼠在棋盤上點擊位置的X座標
	private int y; // 滑鼠在棋盤上點擊位置的Y座標
	private int coordinateX; // 棋盤的X座標
	private int coordinateY; // 棋盤的Y座標
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
		
		//抓取當前視窗大小最小的長或寬 再畫成8*8棋盤
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		calculateChessBoardViewMin();		
		initializeChessBoard();
	}
	
	public void initializeChessBoard(){
		setBackground(Color.GRAY);		
		
		//呼叫初始化棋盤狀態
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
	 * 初始化遊戲模式
	 * 
	 */
	
	public void initializeGameMode(){
		setGameMode(Mode.none);
	}
		
	public void setButtonController(ButtonController buttonController){
		this.buttonController = buttonController;
	}
	
	/**
	 * 更改遊戲模式
	 * 
	 * @param mode 要進入的遊戲模式
	 */
	
	public void setGameMode(Mode mode ){
		gameMode = mode;	
	}
	
	public Mode getGameMode( ){
		return this.gameMode;	
	}
	
	/**
	 * 悔棋，回到上一手棋的棋盤狀態
	 */
	public void undo() {
		int index = chessMoves.size();
		int[] lastmove = chessMoves.get(index - 1);
		int x = lastmove[0];
		int y = lastmove[1];

		chessMoves.remove(index - 1); // 移除最後下個的那步棋 棋子下過的暫存-1
		poistionOccupied[x][y] = 0; // 把該步棋的poistionOccupied歸0
		buttonController.checkButtonStatus(); //檢查按鈕狀態
	}

	/**
	 * 清空暫存棋步
	 * 
	 */
	public void clearChessMove() {
		chessMoves.clear();
	}

	/**
	 * 點擊棋盤後在暫存裡添加棋步
	 * 
	 */
	public void addChessMove(int i, int y) {
		chessMoves.add(new int[] { i, y });
	}

	/**
	 * 傳回此局遊戲目前下過的皇后數
	 * 
	 * @return 此局遊戲目前下過的皇后數
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
	 * 檢查目前這步能不能成功擺放並儲存
	 * 
	 * @param x
	 *            目前這步在棋盤上的X座標
	 * @param y
	 *            目前這步在棋盤上的Y座標
	 */
	private void recordChessMoves(int x, int y) {
		if (chessMoves != null && chessMoves.size() < 8) { // chessMoves.size()<8
															// 8應該要改成N
															// 跟N皇后問題動態結合
			// 透過檢查poistionOccupied這個flag來判斷格子是否被占據 進而排除已經下過的步數
			if (poistionOccupied[x][y] == 0) {
				poistionOccupied[x][y] = 1;
				chessMoves.add(new int[] { x, y });
				buttonController.checkButtonStatus(); //檢查當前按鈕狀態 如果棋盤暫存滿了就能按"驗證答案"鈕
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
	 * 檢查JPanel當前長寬，並以此計算出最適合的當前視窗大小的棋盤長寬和棋盤格子以及留白等參數
	 */
	//更新JPanel和長寬 棋盤格子大小等參數
	public void checkView(){	
		chessView = this.getSize();					//檢查JPanel當前長寬
		chessWidth = chessView.width;
		chessHeight = chessView.height;
		
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidfth > chessHeight)實在太常用到了
		if(chessWidth > chessHeight){
			chessBoardMinLength = chessHeight;
			cell = chessBoardMinLength/grids;       //棋盤格子大小 = 當前棋盤寬度和高度兩個值中最小的那一個除以棋盤路數
			space= (chessWidth-chessHeight)/2;      //留白是把棋盤置中 兩邊都有相同大小的留白區塊
		}else{
			chessBoardMinLength = chessWidth;
			cell = chessBoardMinLength/grids;
			space= (chessHeight-chessWidth)/2;
		}
	}
	
	/**
	 * 畫棋盤格子
	 * 
	 * @param g
	 */

	public void drawGrids(Graphics g){
		
		chessView = this.getSize();
		chessWidth = chessView.width;
		chessHeight = chessView.height;
		
		//抓取當前視窗大小最小的長或寬 再畫成N*N棋盤
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		if(chessWidth > chessHeight){
			chessBoardMinLength = chessHeight ;
			cell = chessBoardMinLength/grids;
			space= (chessWidth-chessHeight)/2;          //四周留白 看長跟寬哪個比較長 哪邊就需要留白 
				                                        //靠增加留白區域設定繪圖初始位置製造置中效果							
			//上色				
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
	 * 畫棋盤格線
	 * 
	 * @param g
	 */

	public void drawLines(Graphics g){
		g.setColor(Color.BLACK);
		
		//一次畫一條直的跟一條橫的 棋盤有幾路就畫幾次
		for(int i = 0;i<=grids;i++){	
			chessView = this.getSize();
			chessWidth = chessView.width;
			chessHeight = chessView.height;
		
			//抓取當前視窗大小最小的長或寬 再畫成8*8棋盤
			//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
			if(chessWidth > chessHeight){
				chessBoardMinLength = chessHeight ;
				cell = chessBoardMinLength/grids;
				//考慮把space封裝起來 space = 長減寬的絕對值 這樣就不需要判斷長寬哪個的值比較大了
				space= (chessWidth-chessHeight)/2;          //四周留白 看長跟寬哪個比較長 哪邊就需要留白 
				                                            //靠增加留白區域設定繪圖初始位置製造置中效果							
				g.drawLine(0+space, i*cell , grids*cell+space, i*cell);		
				//橫線 從(space,i*cell)到(grids*cell+space,i*cell)間畫一條直線
				//i*cell代表了每次for迴圈往下畫下一條直線的Y座標一定是跟上一條((i-1)*cell)距離一個棋盤格的距離
				g.drawLine(i*cell+space, 0 , i*cell+space, grids*cell);		
				//直線 從(i*cell+space,0)到(i*cell+space,grids*cell)間畫一條直線
				//+space保證畫出來會在JPanel正中間	
			}else{
				chessBoardMinLength = chessWidth ;
				cell = chessBoardMinLength/grids;
				space= (chessHeight-chessWidth)/2;		    //四周留白 看長跟寬哪個比較長 哪邊就需要留白 
															//靠增加留白區域設定繪圖初始位置製造置中效果																		
				g.drawLine(0, i*cell+space , grids*cell, i*cell+space);		//橫線
				g.drawLine(i*cell, 0+space , i*cell, grids*cell+space);		//直線
			}								
		}
	}
	
	//畫奇數格的棋盤會出問題 column會多一格 
	//M型程式碼 考慮重構
	/**
	 * 畫出黑色的棋盤格子
	 * 
	 * @param g
	 */
	public void paintBlackGrids(Graphics g){
		g.setColor(Color.BLACK);
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		if(chessWidth > chessHeight){
			for(int i=0 ; i<grids ;i++){   //Y方向循環 循環grids次
				//第一次 檢查奇數行
				for(int j=0 ;j<grids ;j++){  //X方向循環 畫奇數行的黑格
					if(j%2!=0){
						g.fillRect(0+space+j*cell, i*cell , cell, cell);									   						
					}	
				}
				//第二次 檢查偶數行   ++i確保不會多印一個row
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2==0){   //X方向循環 畫偶數行的黑格
							g.fillRect(0+space+j*cell, i*cell , cell, cell);																	
						}					
					}
				}					
			}				
		}else{     //Height > Width
			for(int i=0 ; i<grids ;i++){   //Y方向循環 循環grids次
				//第一次 檢查奇數行
				for(int j=0 ;j<grids ;j++){  //X方向循環 畫奇數行的黑格
					if(j%2!=0){
						g.fillRect(j*cell, 0+space+i*cell, cell, cell);				
					}
				}							
				//第二次 檢查偶數行   ++i確保不會多印一個row
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2==0){   //X方向循環 畫偶數行的黑格
							g.fillRect(j*cell, 0+space+i*cell, cell, cell);
						}	
					}
				}									
			}
		}
	}
	
	/**
	 * 畫出白色的棋盤格子
	 * 
	 * @param g
	 */
	public void paintWhiteGrids(Graphics g){
		g.setColor(Color.WHITE);
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		if(chessWidth > chessHeight){
			for(int i=0 ; i<grids ;i++){   //Y方向循環 循環grids次
				//第一次 檢查奇數行
				for(int j=0 ;j<grids ;j++){  //X方向循環 畫奇數行的白格
					if(j%2==0){
						g.fillRect(0+space+j*cell, i*cell , cell, cell);									   						
					}						
				}							
				//第二次 檢查偶數行   ++i確保不會多印一個row
				if(++i<grids){				
					for(int j=0 ; j<grids ;j++){				
						if(j%2!=0){   //X方向循環 畫偶數行的白格
							g.fillRect(0+space+j*cell, i*cell , cell, cell);																	
						}		
					}		
				}
			}
		}else{     //Height > Width
			for(int i=0 ; i<grids ;i++){   //Y方向循環 循環grids次
				//第一次 檢查奇數行
				for(int j=0 ;j<grids ;j++){  //X方向循環 畫奇數行的白格
					if(j%2==0){
						g.fillRect(j*cell, 0+space+i*cell, cell, cell);								
					}
				}			
				//第二次 檢查偶數行   ++i確保不會多印一個row				
				if(++i<grids){
					for(int j=0 ; j<grids ;j++){				
						if(j%2!=0){   //X方向循環 畫偶數行的白格
							g.fillRect(j*cell, 0+space+i*cell, cell, cell);
						}													
					}	
				}
			}
		}		
	}
	
	/**
	 * 畫出棋子步數暫存中的所有皇后
	 * 
	 * @param g
	 */
	
	private void drawAllQueens(Graphics g){
		checkView();
		//考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		if(chessWidth > chessHeight){	
			Iterator<int[]> it = getCurrentChessMoves().iterator();	
			while(it.hasNext()){				
				int[] moves  = it.next();				
				//開發時暫時代替皇后圖標的印棋子方法
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
		//先畫棋盤格，再畫棋子，最後畫線
		//不然先畫棋子的話，棋子會被棋盤格蓋掉。同理，線不最後畫的話會被棋子跟棋盤蓋掉		
	}
	
	private MouseListener placeQueenHandler = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			
			if (gameMode == Mode.normal) { // 遊戲模式下才可擺放棋子
				//getCurrentChessBoardInformation();
				// 考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth >
				// chessHeight)實在太常用到了
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
	 * 計算當前滑鼠點擊位置對應到的棋盤X座標(0~7)
	 * 
	 * @param x
	 *            當前滑鼠在元件上點擊的X座標
	 * @return 滑鼠點擊的位置對應到棋盤上的X座標
	 */
	private int getXCoordinate(int x) {
		// 考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		//getCurrentChessBoardInformation();
		checkView();
		if ( getChessBoardWidth() > getChessBoardHeight()) {
			for (int i = 0; i < getGrids(); i++) {
				if (x >= i * getCell()+ getSpace() && x < (i + 1) * getCell()+ getSpace())
					return i;
			}
			return getGrids() - 1; // 當初計算cell時忽略的小數點可能造成後面的臨界區塊判定錯誤 靠return
										// grids-1來修正
			// 如果棋盤路數更大應該不能這樣弄 考慮把cell算得更精確 看能不能算到小數點後第二位
		} else { // chessHeight > chessWidth
			for (int i = 0; i < getGrids(); i++) {
				if (x >= i * getCell() && x < (i + 1) * getCell())
					return i;
			}
			return getGrids() - 1;
		}
	}

	/**
	 * 計算當前滑鼠點擊位置對應到的棋盤Y座標(0~7)
	 * 
	 * @param y
	 *            當前滑鼠在元件上點擊的Y座標
	 * @return 滑鼠點擊的位置對應到棋盤上的Y座標
	 */
	private int getYCoordinate(int y) {
		// 考慮把這個判斷和後續的執行封裝起來放在全域可見的地方 因為if(chessWidth > chessHeight)實在太常用到了
		//getCurrentChessBoardInformation();
		checkView();
		if ( getChessBoardWidth() > getChessBoardHeight()) {
			for (int i = 0; i < getGrids(); i++) {
				if (y >= i * getCell() && y < (i + 1) * getCell())
					return i;
			}
			return getGrids()-1; // 當初計算cell時忽略的小數點可能造成後面的臨界區塊判定錯誤 靠return
										// grids-1來修正
			// 如果棋盤路數更大應該不能這樣弄 考慮把cell算得更精確 看能不能算到小數點後第二位
		}else { // chessHeight > chessWidth
			for (int i = 0; i < getGrids(); i++) {
				if (y >= i * getCell() + getSpace() && y < (i + 1) * getCell() + getSpace())
					return i;
			}
			return getGrids()-1;
		}
	}
}