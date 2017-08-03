package n_queens;

class ButtonController {
	private LeftControlPanel leftPanel;
	private RightControlPanel rightPanel;
	
	public ButtonController(LeftControlPanel leftPanel, RightControlPanel rightPanel){
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
	}
	
	public void checkButtonStatus(){
		rightPanel.checkButtonStatus();	
		leftPanel.checkButtonStatus();
	}
}