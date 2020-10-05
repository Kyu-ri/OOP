package Diary.Input;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Diary.Panel.DiaryPanel;

public class InputDiary extends JFrame implements ActionListener {
	private int year;
	private int month;
	private int day;

	private JButton write;
	private JButton edit;
	private JTextField secretTitle;
	private JTextField secretWeather;
	private JTextField secretEmotion;
	private JTextArea secretPageContent;
	
	private File file;
	private File dir;
	
	private DiaryPanel diaryPanel;
	private JButton diaryButton;
	
	private Font f = new Font("Sherif", Font.BOLD, 20);
	
	public InputDiary(String year, String month, String day, DiaryPanel diaryPanel) {
		this(year, month, day, diaryPanel, new JButton());
	}
	
	public InputDiary(int year, int month, int day, DiaryPanel diaryPanel) {
		this(year, month, day, diaryPanel, new JButton());
	}
	
	public InputDiary(String year, String month, String day, DiaryPanel diaryPanel, JButton diaryButton) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), diaryPanel, diaryButton);
	}

	public InputDiary(int year, int month, int day, DiaryPanel diaryPanel, JButton diaryButton) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.diaryPanel = diaryPanel;
		this.diaryButton = diaryButton;
		
		dir = new File("Diary\\" + String.valueOf(year)+ "-" + String.valueOf(month) + "-" + String.valueOf(day));
		System.out.println(dir.getPath());
		
		this.setTitle("My Secret Diary " + year + "-" + month + "-" + day);

		// �۾��� ��ư �� Panel
		write = new JButton("�۾���");
		write.setFont(f);
		write.addActionListener(this);
		edit = new JButton("����");
		edit.setFont(f);
		edit.addActionListener(this);
		JPanel writeButton = new JPanel();
		// writeButton.setLayout(new GridLayout(12, 4));
		writeButton.add(write);
		writeButton.add(edit);

		// ����, ��� ���� �ϴ� �� Panel
		secretWeather = new JTextField();
		secretWeather.setFont(f);
		secretEmotion = new JTextField();
		secretEmotion.setFont(f);
		JPanel secretOption = new JPanel();
		secretOption.setLayout(new GridLayout(1, 6));
		secretOption.add(new JLabel(""));
		JLabel weather = new JLabel("����");
		weather.setFont(f);
		secretOption.add(weather);
		secretOption.add(secretWeather);
		secretOption.add(new JLabel(""));
		JLabel emotion = new JLabel("���");
		emotion.setFont(f);
		secretOption.add(emotion);
		secretOption.add(secretEmotion);
		secretOption.add(new JLabel(""));
		
		// Ÿ��Ʋ�ϰ� �ɼ��ϰ� ���� ��
		secretTitle = new JTextField();
		secretTitle.setFont(f);
		JPanel secretTitlePanel = new JPanel();
		secretTitlePanel.setLayout(new GridLayout(2, 1));
		secretTitlePanel.add(secretOption);
		secretTitlePanel.add(secretTitle);

		// ���̾ ���� ���� ����
		secretPageContent = new JTextArea();
		secretPageContent.setFont(f);

		// ����, ��� ���� �κ��ϰ� ���̾ ���� ��ġ�� �κ�
		JPanel secretPage = new JPanel();
		secretPage.setLayout(new BorderLayout());
		secretPage.add(secretTitlePanel, BorderLayout.NORTH);
		secretPage.add(secretPageContent, BorderLayout.CENTER);

		// �۾��� ��ư ���̶� ������ ��ģ�Ŷ� ��ġ�� �κ�
		this.initText();
		this.setLayout(new BorderLayout());
		this.add(writeButton, BorderLayout.NORTH);
		this.add(secretPage, BorderLayout.CENTER);

		this.setVisible(true);
		this.setSize(600, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void initText() {
		// FileOutputStream fos = new FileOutputStream("")
		dir= new File("Diary\\" + year + "-" + month + "-" + day);
		if(!dir.exists()) {
			dir.mkdirs();
			System.out.println(dir.isDirectory() + " ���丮�� �����Ǿ����ϴ�.");
		}
		file = new File(dir.getPath() + "\\" + diaryButton.getText() + ".txt");
		if (file.exists()) {
			try {

				System.out.println("getName: " + file.getName()); // ���� �̸� ���
				System.out.println("getPath: " + file.getPath()); // ���� ��� ���
				// ���� ���� ��� ���
				System.out.println("getAbsolutePath : " + file.getAbsolutePath());
				// ���� ���� ��� ���
				System.out.println("getCanonicalPath : " + file.getCanonicalPath());
				// ���� ���� ���
				System.out.println("getParent : " + file.getParent());

				// ������ ����/�б� ���� üũ
				if (file.canWrite())
					System.out.println(file.getName() + "�� �� �� �ֽ��ϴ�.");
				if (file.canRead())
					System.out.println(file.getName() + "�� ���� �� �ֽ��ϴ�.");

				// ��ü�� ����, ���� ���� üũ
				if (file.isFile()) {
					System.out.println(file.getName() + "�� �����Դϴ�.");
				} else if (file.isDirectory()) {
					System.out.println(file.getName() + "�� �����Դϴ�.");
				} else {
					System.out.println(file.getName() + "�� ���ϵ� ������ �ƴմϴ�.");
				}

				// ���� ���� ���� ���
				System.out.println(file.getName() + "�� ���̴� " + file.length() + " �Դϴ�.");
				
				BufferedReader br = new BufferedReader(new FileReader(file));
				ArrayList<String> lines = new ArrayList<String>();
				String context = null;
				this.secretWeather.setText(br.readLine());
				this.secretEmotion.setText(br.readLine());
				
				while(true) {
					context = br.readLine();
					if(context == null) break;
					lines.add(context);
				}
				context = "";
				for(int i = 0; i < lines.size(); i++) {
					context += lines.get(i) + '\n';
				}
				this.secretPageContent.setText(context);
				this.secretTitle.setText(this.diaryButton.getText());
				this.secretTitle.setEditable(false);
				this.secretPageContent.setEditable(false);
				this.secretWeather.setEditable(false);
				this.secretEmotion.setEditable(false);
				br.close();
			} catch (IOException e) {
				System.err.println(e);
				
			}

		} else {
			System.out.println("������ ã�� �� �����ϴ�. ");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == this.write && this.secretPageContent.isEditable() && 
				!this.secretEmotion.getText().equals("") && !this.secretPageContent.getText().equals("") && 
				!this.secretTitle.getText().equals("") && !this.secretWeather.getText().equals("")) {
			if(!dir.exists()) {
				dir.mkdirs();
				System.out.println(dir.getPath() + " ���丮�� �����Ǿ����ϴ�.");
			}
			if(file.exists()) {
				file.delete();
				diaryButton.setText(this.secretTitle.getText());
				System.out.println(diaryButton.getText());
			}
			file = new File(dir.getPath() + "\\" + this.secretTitle.getText() + ".txt");
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(this.secretWeather.getText() + '\n');
				fw.write(this.secretEmotion.getText() + '\n');
				fw.write(this.secretPageContent.getText());
				System.out.println(file.getName().substring(0, file.getName().indexOf(".txt")) + "�� �ۼ��Ǿ����ϴ�.");
				System.out.println(file.getPath() + "�� �ۼ��Ǿ����ϴ�.");
				System.out.println("������ ������ " + this.secretPageContent.getText() + "�Դϴ�.");
				fw.close();
				this.secretPageContent.setEditable(false);
				this.secretTitle.setEditable(false);
				this.secretWeather.setEditable(false);
				this.secretEmotion.setEditable(false);
				
				if(diaryButton.getText().equals("")) {
					diaryPanel.addButton(this.secretTitle.getText());
				}
				
				this.dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource() == this.edit && !this.secretPageContent.isEditable()) {
			this.secretPageContent.setEditable(true);
			this.secretWeather.setEditable(true);
			this.secretEmotion.setEditable(true);
			this.secretTitle.setEditable(true);
		}
	}
}
