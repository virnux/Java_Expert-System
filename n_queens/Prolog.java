package n_queens;

/* 
 * ClassName		 :
 * Prolog 
 * 
 * Description 	     :
 * 高師軟工碩一課程專家系統期中作業 - 八皇后遊戲
 * 功能:開新遊戲、悔棋、擺完八個皇后可以確認答案
 *     計算N皇后問題的可行解數(N = 4 ~10)
 *     可以展示出每一種八皇后問題的解
 * 
 * Author		     :
 * 許博淳
 * 
 * History	         :	    
 * Date		 2016/11/16
 * Version   1.0
 * 
 * Copyright notice  :
 * 非經原作者授權許可，禁止轉載抄襲。
 * 
 * 未來的更動:重構改善架構
 *          老師的建議:1.Chessboard類別應該要取消  考慮在負責處理gui的地方用一個副程式去呼叫繪(N路棋盤)圖的方式	
 * 					  2.把綁定在Left/Right/MidJPanel甚至是Chessboard裡的屬性給扁平化
 *                      也就是移到JFrame類別下進行宣告，這樣這些變數就變成全域可見
 *          改善串接字串的效能
 *          GameInterface類別內的功能太多要把他們分出來
 *          其他參照註解進行更動
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 * 實作向SWI Prolog進行各種查詢的方法
 * 
 * @note 八皇后遊戲
 * @author 	許博淳
 * @version 1.0
 * 
 */

class Prolog {
	/**
	 * 連結到目標SWI Prolog檔案
	 * 
	 * @param rulebase 要查詢的.pl檔案名稱
	 * @return consult prolog檔案是否成功
	 */
	boolean link(String rulebase){
		String command = "consult('" + rulebase + "')";
		return new Query(command).hasSolution();
	}
	
	/**
	 * 向SWI Prolog檔案查詢是否有解
	 * 
	 * @param command 下給SWI Prolog檔案的查詢語句
	 * @return 查詢結果是否為true
	 */
	
	boolean isTrue(String command){
		return new Query(command).hasSolution();
	}
	
	//感覺跟 isTrue(String command)重複了
	/**
	 * 向SWI Prolog檔案查詢是否有解
	 * 
	 * @param command 下給SWI Prolog檔案的查詢語句
	 * @return 查詢結果是否為true
	 */
	boolean drive_TrueORFalse(String command){	
		boolean isTrue = isTrue(command);
		Query q = new Query(command);	
		List <Map<String, Term>> list = new ArrayList <Map<String, Term>>();;
		if (isTrue){
			return  true;	   
		}else{
			return  false;	
		}		
	}
	
	/**
	 * 向SWI Prolog檔案查詢是否有解，如果有解則傳回計算出來的結果
	 * 
	 * @param command 下給SWI Prolog檔案的查詢語句
	 * @return prolog算出來的結果
	 */
	List drive(String command){
		boolean isTrue = isTrue(command);
		Query q = new Query(command);	
		
		List <Map<String, Term>> list = new ArrayList <Map<String, Term>>();	
		while ( q.hasMoreSolutions() ){
			Map<String, Term> s4=  q.nextSolution();			
			list.add(s4);				
		}
		return  list;	
	}
	
	//雖然說是DEMO版本用的 不過好像跟drive(String command)重複了 待研究
	/**
	 * 向SWI Prolog檔案查詢是否有解，如果有解則傳回計算出來的結果  
	 * 
	 * @param command 下給SWI Prolog檔案的查詢語句
	 * @return prolog算出來的結果
	 */
	
	List drive_demo(String command){
		boolean isTrue = isTrue(command);
		Query q = new Query(command);
		
		List list = new ArrayList();
		while ( q.hasMoreSolutions() ){   	
			String s = q.nextSolution().toString();
            list.add(s);
        }
		return  list;		
	}
	
}

