package AccountBook.Panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AccountBookPanel extends JPanel implements ActionListener {
	private File tradeFile;
	private File totalFile;
	private File dir;

	private int year;
	private int month;
	private int date;

	private JFrame mainFrame;
	private JTextField depositOutput;
	private JTextField wasteOutput;
	private JTextField totalOutput;

	private JScrollPane incomeScroll;
	private JScrollPane wasteScroll;

	private JTextArea incomeText;
	private JTextArea wasteText;

	private JButton editButton;

	private Font f = new Font("Sherif", Font.BOLD, 17);

	public AccountBookPanel(JFrame mainFrame, JTextField depositOutput, JTextField wasteOutput,
			JTextField totalOutput) {
		this.mainFrame = mainFrame;
		this.depositOutput = depositOutput;
		this.depositOutput.setFont(f);
		this.wasteOutput = wasteOutput;
		this.wasteOutput.setFont(f);
		this.totalOutput = totalOutput;
		this.totalOutput.setFont(f);
		assert (!this.depositOutput.getText().equals("") && !this.wasteOutput.getText().equals("")
				&& !this.totalOutput.getText().equals(""));
		// super.setLayout(new GridLayout(0, 1));
		editButton = new JButton("수정");
		editButton.setFont(f);
		editButton.addActionListener(this);
		JPanel temp = new JPanel();
		JLabel label = new JLabel("가계부     ");
		label.setFont(f);
		temp.add(label);
		temp.add(editButton);
		temp.add(new JLabel(
				"                                                                                                                                                         "));

		this.add(temp);

		incomeText = new JTextArea();
		incomeText.setEditable(false);
		incomeText.setFont(f);

		incomeScroll = new JScrollPane(incomeText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(incomeScroll);
		wasteText = new JTextArea();
		wasteText.setEditable(false);
		wasteText.setFont(f);

		wasteScroll = new JScrollPane(wasteText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(wasteScroll);

		init();
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

	public AccountBookPanel(String year, String month, String date, JFrame mainFrame) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), mainFrame);
	}

	public AccountBookPanel(int year, int month, int date, JFrame mainFrame) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.mainFrame = mainFrame;
		super.setLayout(new GridLayout(0, 1));
	}

	public void init(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;

		init();
	}

	public void init() {
		incomeText.selectAll();
		wasteText.selectAll();
		int start = incomeText.getSelectionStart(); // 선택부분의 시작점
		int end = incomeText.getSelectionEnd(); // 선택부분의 끝점
		incomeText.replaceRange("", start, end); // 시작부분과 끝점 사이를 공백으로 교체
		start = wasteText.getSelectionStart();
		end = wasteText.getSelectionEnd();
		wasteText.replaceRange("", start, end);
		incomeText.append("수입" + '\n');
		wasteText.append("지출" + '\n');
		/* this.add(new JLabel("가계부")); */
		dir = new File("AccountBook\\" + year + "-" + month + "-" + date);
		if (dir.exists()) {
			try {
				tradeFile = new File(dir.getPath() + "\\" + "거래내역.txt");
				totalFile = new File(dir.getPath() + "\\" + "총 내역.txt");
				if (tradeFile.exists() && totalFile.exists()) {
					BufferedReader br;
					System.out.println(tradeFile.getName() + "파일을 열었습니다.");
					System.out.println(totalFile.getName() + "파일을 열었습니다.");

					br = new BufferedReader(new FileReader(tradeFile));
					ArrayList<String[]> plus = new ArrayList<String[]>();
					ArrayList<String[]> minus = new ArrayList<String[]>();
					while (true) {
						String line[] = new String[2];
						String sign = null;
						sign = br.readLine();
						System.out.println(sign);
						if (sign == null)
							break;
						line[0] = br.readLine();
						line[1] = br.readLine();
						if (sign.equals("+")) {
							System.out.println("+" + line[0] + line[1]);
							plus.add(line);
							this.incomeText.append(line[0] + "   " + line[1] + '\n');
						} else {
							System.out.println("-" + line[0] + line[1]);
							minus.add(line);
							this.wasteText.append(line[0] + "   " + line[1] + '\n');
						}
					}
					/*
					 * this.add(new JLabel("수입")); for (int i = 0; i <
					 * plus.size(); i++) { this.add(new JLabel("               "
					 * + plus.get(i)[0] + "   " + plus.get(i)[1])); }
					 * 
					 * this.add(new JLabel("지출")); for (int i = 0; i <
					 * minus.size(); i++) { this.add(new
					 * JLabel("               " + minus.get(i)[0] + "   " +
					 * minus.get(i)[1])); }
					 */
					br = new BufferedReader(new FileReader(totalFile));
					depositOutput.setText(br.readLine());
					wasteOutput.setText(br.readLine());
					assert (!depositOutput.getText().equals("") && !wasteOutput.getText().equals(""));
					totalOutput.setText(String.valueOf(
							Integer.parseInt(depositOutput.getText()) - Integer.parseInt(wasteOutput.getText())));
					br.close();
					// mainFrame.pack();
				} else {
					System.out.println("파일이 존재하지 않습니다. " + tradeFile.getName() + "  " + tradeFile.getPath());
					System.out.println("파일이 존재하지 않습니다. " + totalFile.getName() + "  " + totalFile.getPath());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			System.out.println("디렉토리가 존재하지 않습니다. " + dir.getPath());
			depositOutput.setText("0");
			wasteOutput.setText("0");
			totalOutput.setText("0");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == editButton && editButton.getText().equals("수정")) {
			editButton.setText("확인");
			this.incomeText.setEditable(true);
			this.wasteText.setEditable(true);
		} else if (e.getSource() == editButton && editButton.getText().equals("확인")) {
			editButton.setText("수정");
			this.incomeText.setEditable(false);
			this.wasteText.setEditable(false);

			try {
				String[] line = this.incomeText.getText().split("\n");
				int total = 0;

				FileWriter fw = new FileWriter(tradeFile);
				for (int i = 1; i < line.length; i++) {

					fw.write("+" + '\n');

					fw.write(line[i].split(" ")[0].trim() + "\n");
					int num = 1;
					while(line[i].split(" ")[num].equals("")) num++;
					fw.write(line[i].split(" ")[num].trim() + '\n');
					total += Integer.parseInt(line[i].split(" ")[num].trim());
				}
				FileWriter fww = new FileWriter(totalFile);
				fww.write(String.valueOf(total) + '\n');
				total = 0;
				line = this.wasteText.getText().split("\n");
				for (int i = 1; i < line.length; i++) {

					fw.write("-" + '\n');
					System.out.println(line[i]);
					System.out.println(line[i].split(" ")[0].trim());
					System.out.println(line[i].split(" ")[1].trim());
					fw.write(line[i].split(" ")[0].trim() + "\n");
					int num = 1;
					while(line[i].split(" ")[num].equals("")) num++;
					fw.write(line[i].split(" ")[num].trim() + '\n');
					total += Integer.parseInt(line[i].split(" ")[num].trim());
				}
				fww.write(String.valueOf(total) + '\n');
				fww.close();
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.init();
		}
	}
}
