package Diary.Delete;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Diary.Panel.DiaryPanel;

public class DiaryDelete extends JFrame implements ActionListener{
	private int year;
	private int month;
	private int date;
	
	private DiaryPanel diaryPanel;
	private JButton finish;
	
	private JCheckBox[] diaryBox;
	
	private File dir;
	private File[] fileList;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	public DiaryDelete(int year, int month, int date, DiaryPanel diaryPanel) {
		super(year +"-" +month+"-"+date+" Diary Delete");
		this.year = year;
		this.month = month;
		this.date = date;
		this.diaryPanel = diaryPanel;
		
		this.setLayout(new GridLayout(0, 1));
		
		finish = new JButton("확인");
		finish.addActionListener(this);
		init();
		
		this.setVisible(true);
		this.pack();
		this.setSize(400, this.getWidth());
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void init() {
		
		dir = new File("Diary\\" + year +"-" +month+"-"+date);
		if(dir.exists()) {
			fileList = dir.listFiles();
			diaryBox = new JCheckBox[fileList.length];
			for(int i = 0; i < diaryBox.length; i++) {
				diaryBox[i] = new JCheckBox(fileList[i].getName().substring(0, fileList[i].getName().indexOf(".txt")));
				diaryBox[i].setFont(f);
				
				this.add(diaryBox[i]);
			}
			this.add(finish);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == finish) {
			int ok = new JOptionPane().showOptionDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(ok == JOptionPane.OK_OPTION) {
				for(int i = 0; i < diaryBox.length; i++) {
					if(diaryBox[i].isSelected()) {
						fileList[i].delete();
					}
				}
				diaryPanel.init();
				this.dispose();
			}
		}
	}
}
