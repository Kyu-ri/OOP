package AccountBook;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TotalAccountBook extends JFrame{
	private int year;
	private int month;
	
	private JScrollPane incomeScroll;
	private JScrollPane wasteScroll;
	
	private JTextArea incomeText;
	private JTextArea wasteText;
	
	private JLabel totalIncome;
	private JLabel totalWaste;
	private JLabel total;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	private File dir;
	private File tradeFile;
	private File totalFile;
	
	
	public TotalAccountBook(String year, String month) {
		this(Integer.parseInt(year), Integer.parseInt(month));
	}
	public TotalAccountBook(int year, int month) {
		super(year + "-" + month + " 가계부");
		this.year = year;
		this.month = month;
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 2));
		
		incomeText = new JTextArea();
		incomeText.setEditable(false);
		incomeText.setFont(f);
		incomeText.setText("수입" + '\n');
		
		incomeScroll = new JScrollPane(incomeText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		wasteText = new JTextArea();
		wasteText.setEditable(false);
		wasteText.setFont(f);
		wasteText.setText("지출" + '\n');
		
		wasteScroll = new JScrollPane(wasteText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		textPanel.add(incomeScroll);
		textPanel.add(wasteScroll);
		
		JPanel totalPanel = new JPanel();
		JLabel incomeLabel = new JLabel("총 수입 : ");
		JLabel wasteLabel = new JLabel("총 지출 : ");
		JLabel totalLabel = new JLabel("총 액 : ");
		totalIncome = new JLabel("0");
		totalIncome.setFont(f);
		totalWaste = new JLabel("0");
		totalWaste.setFont(f);
		total = new JLabel("0");
		total.setFont(f);
		
		totalPanel.setLayout(new GridLayout(3,3));
		totalPanel.add(incomeLabel);
		totalPanel.add(totalIncome);
		totalPanel.add(new JLabel(""));
		totalPanel.add(wasteLabel);
		totalPanel.add(totalWaste);
		totalPanel.add(new JLabel(""));
		totalPanel.add(totalLabel);
		totalPanel.add(total);
		totalPanel.add(new JLabel(""));
		
		this.init();
		
		this.setLayout(new BorderLayout());
		this.add(textPanel, BorderLayout.CENTER);
		this.add(totalPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void init() {
		incomeText.removeAll();
		wasteText.removeAll();
		
		for(int i = 1; i <= 31; i++) {
			dir = new File("AccountBook\\" + year +"-" + month+"-" + i);
			if(dir.exists()) {
				System.out.println(dir.getPath() + " 디렉토리가 존재합니다.");
				incomeText.append(year + "-" + month + "-" + i + '\n');
				wasteText.append(year + "-" + month + "-" + i + '\n');
				
				tradeFile = new File(dir.getPath()+ "\\거래내역.txt");
				totalFile = new File(dir.getPath() + "\\총 내역.txt");
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(tradeFile));
					while(true) {
						String sign = br.readLine();
						if(sign == null) break;
						if(sign.equals("+")) {
							incomeText.append(br.readLine() + "   ");
							incomeText.append(br.readLine() + '\n');
						} else if (sign.equals("-")) {
							wasteText.append(br.readLine() + "   ");
							wasteText.append(br.readLine() + '\n');
						}
 					}
					incomeText.append("\n");
					wasteText.append("\n");
					
					br.close();
					
					br = new BufferedReader(new FileReader(totalFile));
					System.out.println(totalFile.getPath() + totalFile.getName());
					this.totalIncome.setText("총 수입 : ");
					this.totalWaste.setText("총 지출 : ");
					this.totalIncome.setText(br.readLine());
					System.out.println(this.totalIncome.getText());
					this.totalWaste.setText(br.readLine());
					System.out.println(this.totalWaste.getText());
					br.close();
					this.total.setText(String.valueOf(Integer.parseInt(totalIncome.getText()) - Integer.parseInt(this.totalWaste.getText())));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}


