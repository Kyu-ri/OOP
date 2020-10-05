package Diary;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TotalDiary extends JFrame {
	private int year;
	private int month;

	private File dir;
	private File file;

	private JTextArea text;
	private JScrollPane scroll;

	public void setDays(String year, String month) {
		this.setDays(Integer.parseInt(year), Integer.parseInt(month));
	}

	public void setDays(int year, int month) {
		this.year = year;
		this.month = month;

		this.init();
	}

	/*public TotalDiary() {
		text = new JTextArea();
		text.setEditable(false);

		text.setFont(new Font("Sherif", Font.BOLD, 15));

		scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(text);
		this.setVisible(true);
		// this.setLayout(new ScrollPaneLayout());
	}*/

	public TotalDiary(String year, String month) {
		this(Integer.parseInt(year), Integer.parseInt(month));
	}

	public TotalDiary(int year, int month) {
		super(year + "-" + month + " Diary");
		this.year = year;
		this.month = month;

		text = new JTextArea();
		text.setEditable(false);

		text.setFont(new Font("Sherif", Font.BOLD, 17));

		scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scroll);
		this.setVisible(true);
		this.setSize(500, 400);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.init();
	}

	private void init() {
		for (int i = 1; i <= 31; i++) {
			dir = new File("Diary\\" + year + "-" + month + "-" + String.valueOf(i));
			if (dir.exists()) {
				BufferedReader br;
				System.out.println(dir.getPath() + " 디렉토리가 존재합니다.");
				File[] fileList = dir.listFiles();
				text.append(year + "년 " + month + "월 " + i + "일" + '\n');
				/*
				 * JLabel dateLabel = new JLabel(year + "년 " + month + "월 " + i
				 * +"일"); dateLabel.setFont(new Font("Sherif", Font.BOLD, 15));
				 * this.add(dateLabel);
				 */
				for (int j = 0; j < fileList.length; j++) {
					try {
						br = new BufferedReader(new FileReader(fileList[j]));
						text.append("Title : "
								+ fileList[j].getName().substring(0, fileList[j].getName().indexOf(".txt")) + '\n');
						/*
						 * JLabel titleLabel = new JLabel("Title : " +
						 * fileList[j].getName().substring(0,
						 * fileList[j].getName().indexOf(".txt")));
						 * titleLabel.setFont(new Font("Sherif", Font.BOLD,
						 * 10)); this.add(titleLabel);
						 */

						String weather = br.readLine();
						String emotion;

						emotion = br.readLine();

						text.append("Weather : " + weather + "   Emotion : " + emotion + '\n');
						/*
						 * JLabel extraInfor = new JLabel("Weather : " + weather
						 * + "   Emotion : " + emotion); extraInfor.setFont(new
						 * Font("Sherif", Font.BOLD, 10)); this.add(extraInfor);
						 */
						/*
						 * JLabel contextLabel = new JLabel("");
						 * contextLabel.setFont(new Font("Sherif", Font.BOLD,
						 * 10));
						 */
						String con = new String("");
						while (true) {
							String line = null;

							line = br.readLine();
							if (line == null)
								break;
							text.append(line);
						}
						text.append("\n\n");
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
