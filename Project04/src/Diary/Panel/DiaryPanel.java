package Diary.Panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Diary.Delete.DiaryDelete;
import Diary.Input.InputDiary;

public class DiaryPanel extends JPanel implements ActionListener{
	private File file;
	private File dir;
	
	private int year;
	private int month;
	private int date;
	
	private JButton[] diarys;
	private JFrame mainFrame;
	private JButton deleteButton;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	public DiaryPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.deleteButton = new JButton("삭제");
		this.deleteButton.setFont(f);
		this.deleteButton.addActionListener(this);
		//super.setLayout(new GridLayout(0, 1));
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public DiaryPanel(String year, String month, String date, JFrame mainFrame) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), mainFrame);
	}
	
	public DiaryPanel(int year, int month, int date, JFrame mainFrame) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.mainFrame = mainFrame;
		this.deleteButton = new JButton("삭제");
		this.deleteButton.setFont(f);
		this.deleteButton.addActionListener(this);
		//super.setLayout(new GridLayout(0, 1));
	}
	
	public void init(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.deleteButton = new JButton("삭제");
		this.deleteButton.setFont(f);
		this.deleteButton.addActionListener(this);
		
		init();
	}
	
	public void init() {
		this.removeAll();
		JPanel temp = new JPanel();
		//temp.setLayout(new GridLayout(1, 7));
		JLabel label = new JLabel("다이어리 ");
		label.setFont(f);
		temp.add(label);
		temp.add(deleteButton);
		temp.add(new JLabel("                                                                                                                                                       "));
		this.add(temp);
		dir = new File("Diary\\" + year+ "-" + month + "-" + date);
		if (dir.exists()) {
			File[] fileList = dir.listFiles();
			diarys = new JButton[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				if (file.exists()) {
					diarys[i] = new JButton(file.getName().substring(0, file.getName().indexOf(".txt")));
					diarys[i].setFont(f);
					this.add(diarys[i]);
					diarys[i].addActionListener(this);
				} else {
					System.out.println("파일이 존재하지 않습니다. " + file.getName());
				}
			}
			this.revalidate();
		} else {
			System.out.println("디렉토리가 존재하지 않습니다. " + dir.getPath());
		}
	}
	public void addButton(String title) {
		JButton btn = new JButton(title);
		btn.setFont(f);
		this.add(btn);
		btn.addActionListener(this);
		this.revalidate();
		//mainFrame.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.deleteButton) {
			new DiaryDelete(year, month, date, this);
		}else {
			new InputDiary(year, month, date, this, ((JButton)e.getSource()));
		}
		
	}
}
