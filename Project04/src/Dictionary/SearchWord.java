package Dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchWord extends JFrame implements ActionListener, KeyListener{
	private Dictionary dic;
	
	private Font wordFont = new Font("Sherif", Font.BOLD, 25);
	private Font meaningFont = new Font("Sherif", Font.BOLD, 17);
	
	private Color wordColor = Color.blue;
	private Color meaningColor = Color.black;
	
	private JTextField searchWord;
	
	private JLabel word;
	private JLabel meaning;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	public SearchWord(Dictionary dic) {
		super("SearchWord");
		this.dic = dic;
		
		JPanel inputPanel = new JPanel();
		JLabel searchWordLabel = new JLabel("찾을 단어 : ");
		searchWordLabel.setFont(f);
		inputPanel.add(searchWordLabel);
		
		searchWord = new JTextField(20);
		searchWord.setFont(f);
		searchWord.addActionListener(this);
		searchWord.addKeyListener(this);
		inputPanel.add(searchWord);
		
		JPanel searchedPanel = new JPanel();
		searchedPanel.setLayout(new GridLayout(2, 1));
		
		word = new JLabel("");
		word.setFont(wordFont);
		word.setForeground(wordColor);
		
		searchedPanel.add(word);
		
		meaning = new JLabel("");
		meaning.setFont(meaningFont);
		meaning.setForeground(meaningColor);
		
		searchedPanel.add(meaning);
		
	
		
		this.setLayout(new BorderLayout());
		this.add(inputPanel, BorderLayout.NORTH);
		this.add(searchedPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.searchWord) {
			String meaning = dic.getWordsMap().get(this.searchWord.getText().toLowerCase());
			String word = this.searchWord.getText().toLowerCase();
			System.out.println(meaning);
			if(meaning != null) {
				this.meaning.setText(meaning);
				this.word.setText(word);
				this.pack();
			}	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();

		if (!Character.isAlphabetic(c)) {
			e.consume();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
