package DataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MyJButton extends JButton implements ActionListener{
	private boolean isClicked = false;
	
	public MyJButton(String s) {
		super(s);
		super.addActionListener(this);
	}
	
	public void initClicked() {isClicked = false;}
	
	public boolean isClicked() {return isClicked;}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		isClicked = true;
	}
	
}
