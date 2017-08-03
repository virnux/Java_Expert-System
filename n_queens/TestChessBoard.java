package n_queens;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * setUpBeforeClass()，class初始化之後調用，用來作測試的準備工作。
 * tearDownAfterClass()，class結束之前調用，用來作測試的清理工作。
 * setUp() ，在測試method前調用，用來作測試的準備工作
 * tearDown() 在測試method後調用，用來作測試的清理工作
 * 
 */

public class TestChessBoard {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		System.out.println("---------------------------");
		System.out.println("test java.openyu.org");
		System.out.println("---------------------------");
		//fail("Not yet implemented");
	}

}
