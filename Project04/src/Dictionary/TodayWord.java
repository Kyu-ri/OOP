package Dictionary;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TodayWord extends JFrame{
	private int year;
	private int month;
	private int date;
	
	private static final int WORDCOUNT = 5;
	
	private Font wordFont = new Font("Sherif", Font.BOLD, 25);
	private Font meaningFont = new Font("Sherif", Font.BOLD, 17);
	
	private Color wordColor = Color.blue;
	private Color meaningColor = Color.black;
	
	private JLabel[] word = new JLabel[WORDCOUNT];
	private JLabel[] meaning = new JLabel[WORDCOUNT];
	
	private File todayWord;
	
	private Dictionary dic;
	
	public TodayWord(int year, int month, int date, Dictionary dic) {
		super(year+"-"+ month+"-"+ date+"의 단어");
		this.year = year;
		this.month = month;
		this.date = date;
		
		this.dic = dic;
		
		this.setLayout(new GridLayout(WORDCOUNT, 1));
		
		JPanel[] wordPanel = new JPanel[WORDCOUNT];
		
		
		for(int i = 0; i < WORDCOUNT; i++) {
			wordPanel[i] = new JPanel();
			wordPanel[i].setLayout(new GridLayout(2, 1));
					
			word[i] = new JLabel("");

			wordPanel[i].add(word[i]);
			
			meaning[i] = new JLabel("");
			wordPanel[i].add(meaning[i]);
			
			word[i].setFont(wordFont);
		
			meaning[i].setFont(meaningFont);
			
			word[i].setForeground(wordColor);
			meaning[i].setForeground(meaningColor);
			
			this.add(wordPanel[i]);
		}
		
		init();
		
		this.setVisible(true);
		//this.pack();
		this.setSize(370, 407);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
	}
	
	private void init() {
		todayWord = new File("Dictionary\\" + year + "-" + month + "-" + date+".txt");
		if(todayWord.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(todayWord));
				
				for(int i = 0; i < WORDCOUNT; i++) {
					word[i].setText(br.readLine());
					meaning[i].setText(br.readLine());
				}
				
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println(todayWord.getPath() + todayWord.getName() + "이 존재하지 않습니다.");
			FileWriter fw;
			try {
				fw = new FileWriter(todayWord);
				for(int i = 0; i < WORDCOUNT; i++) {
					int rand = ((int)(Math.random() * dic.getWordsList().size()));
					System.out.println(rand);
					String sWord = dic.getWordsList().get(rand).split("///")[0];
					fw.write(sWord + '\n');
					String sMeaning = dic.getWordsList().get(rand).split("///")[1];
					fw.write(sMeaning + '\n');
					word[i].setText(sWord);
					meaning[i].setText(sMeaning);
					
					
					
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
	}
}
