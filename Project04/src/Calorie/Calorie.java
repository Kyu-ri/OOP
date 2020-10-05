package Calorie;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calorie extends JFrame implements ActionListener {
	private File dir = new File("Food\\");

	private int year;
	private int month;
	private int date;

	private JButton addFood;


	/*
	 * private JComboBox<String> majorKey; private JComboBox<String> middleKey;
	 * private JComboBox<String> count;
	 */

	private File[] fileList;

	// private HashMap<String, ArrayList<String>> foods;
	private ArrayList<JComboBox<String>> majorKeyList;
	private ArrayList<JComboBox<String>> middleKeyList;
	private ArrayList<JComboBox<String>> countList;
	private ArrayList<JTextField> calorieList;
	private ArrayList<JButton> inputButtonList;
	private ArrayList<JButton> editButtonList;
	
	private JLabel totalCalorie;

	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	
	public Calorie(String year, String month, String date) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date));
	}

	public Calorie(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.setLayout(new GridLayout(0, 1));
		majorKeyList = new ArrayList<JComboBox<String>>();
		middleKeyList = new ArrayList<JComboBox<String>>();
		countList = new ArrayList<JComboBox<String>>();
		calorieList = new ArrayList<JTextField>();
		inputButtonList = new ArrayList<JButton>();
		editButtonList = new ArrayList<JButton>();
		
		addFood = new JButton("항목추가");
		addFood.setFont(f);
		addFood.addActionListener(this);
		fileList = dir.listFiles();
		JPanel totalPanel = new JPanel();
		JLabel totalLabel = new JLabel("총 칼로리 : ");
		totalLabel.setFont(f);
		totalPanel.add(totalLabel);
		totalCalorie = new JLabel("0");
		totalCalorie.setFont(f);
		totalPanel.add(totalCalorie);
		this.add(addFood);
		
		JLabel maleCalorie = new JLabel("한국 남성 평균 권장 칼로리 : 2300~2500");
		JLabel femaleCalorie = new JLabel("한국 여성 평균 권장 칼로리 : 1800~2300");
		
		maleCalorie.setFont(f);
		femaleCalorie.setFont(f);
		this.add(maleCalorie);
		this.add(femaleCalorie);
		this.add(totalPanel);
		
		this.setTitle("Calorie");
		this.setVisible(true);
		this.setSize(800, 187);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void mayjorInit(JComboBox<String> majorKey) {
		if (dir.exists()) {
			String[] major = new String[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				majorKey.addItem(fileList[i].getName().substring(0, fileList[i].getName().indexOf(".txt")));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addFood) {
			System.out.println("add");
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 6));

			JComboBox<String> majorKey = new JComboBox<String>();
			majorKey.setFont(f);
			majorKeyList.add(majorKey);
			this.mayjorInit(majorKey);
			panel.add(majorKey);

			JComboBox<String> middleKey = new JComboBox<String>();
			middleKey.setFont(f);
			middleKeyList.add(middleKey);
			panel.add(middleKey);

			JComboBox<String> count = new JComboBox<String>();
			count.setFont(f);
			countList.add(count);
			panel.add(count);

			JTextField calorie = new JTextField(10);
			calorie.setFont(f);
			calorie.setEditable(false);
			panel.add(calorie);
			calorieList.add(calorie);
			
			JButton inputButton = new JButton("확인");
			inputButton.setFont(f);
			panel.add(inputButton);
			inputButtonList.add(inputButton);
			inputButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 0; i < inputButtonList.size(); i++) {
						if(e.getSource() == inputButtonList.get(i) && majorKeyList.get(i).isEnabled() && middleKeyList.get(i).isEnabled() && countList.get(i).isEnabled()) {
							totalCalorie.setText(String.valueOf(Integer.parseInt(totalCalorie.getText())+ Integer.parseInt(calorieList.get(i).getText())));
							majorKeyList.get(i).setEnabled(false);
							middleKeyList.get(i).setEnabled(false);
							countList.get(i).setEnabled(false);
						}
					}
				}
				
			});
			
			JButton editButton = new JButton("수정");
			editButton.setFont(f);
			panel.add(editButton);
			editButtonList.add(editButton);
			editButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 0; i < editButtonList.size(); i++) {
						if(e.getSource() == editButtonList.get(i) && !majorKeyList.get(i).isEnabled() && !middleKeyList.get(i).isEnabled() && !countList.get(i).isEnabled()) {
							totalCalorie.setText(String.valueOf(Integer.parseInt(totalCalorie.getText()) - Integer.parseInt(calorieList.get(i).getText())));
							majorKeyList.get(i).setEnabled(true);
							middleKeyList.get(i).setEnabled(true);
							countList.get(i).setEnabled(true);
						}
					}
				}
			});

			majorKey.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < majorKeyList.size(); i++) {
						if (e.getSource() == majorKeyList.get(i)) {
							System.out.println(i);
							if (fileList[majorKeyList.get(i).getSelectedIndex()].exists()) {
								System.out.println(((JComboBox<String>) e.getSource()).getSelectedItem());
								try {
									middleKeyList.get(i).removeAllItems();
									BufferedReader br = new BufferedReader(
											new FileReader(fileList[majorKeyList.get(i).getSelectedIndex()]));

									while (true) {
										String line = null;
										line = br.readLine();
										if (line == null)
											break;
										middleKeyList.get(i).addItem(line.split("//")[0]);

									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

				}
			});
			middleKey.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < middleKeyList.size(); i++) {
						if (e.getSource() == middleKeyList.get(i)) {
							if (fileList[majorKeyList.get(i).getSelectedIndex()].exists()) {
								System.out.println(((JComboBox<String>) e.getSource()).getSelectedItem());
								try {
									countList.get(i).removeAllItems();
									BufferedReader br = new BufferedReader(
											new FileReader(fileList[majorKeyList.get(i).getSelectedIndex()]));
									String clean = null;
									while (true) {
										String line = null;
										line = br.readLine();
										if (line == null)
											break;
										if(line.split("//")[0].equals(middleKeyList.get(i).getSelectedItem())) {
											clean = line.split("//")[1].replaceAll("[^0-9]", "");
											for(int j = 0; j < 10; j++) {
												countList.get(i).addItem(String.valueOf(Integer.parseInt(clean) *(j+1)));
											}
										}
										

									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

				}

			});
			count.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 0; i < countList.size(); i++) {
						if(e.getSource() == countList.get(i)) {
							if (fileList[majorKeyList.get(i).getSelectedIndex()].exists()) {
								System.out.println(((JComboBox<String>) e.getSource()).getSelectedItem());
								try {
									BufferedReader br = new BufferedReader(
											new FileReader(fileList[majorKeyList.get(i).getSelectedIndex()]));
									while (true) {
										String line = null;
										line = br.readLine();
										if (line == null)
											break;
										if(line.split("//")[0].equals(middleKeyList.get(i).getSelectedItem())) {
											calorieList.get(i).setText("" + ((((int)Double.valueOf((line.split("//")[2])).doubleValue()) * (countList.get(i).getSelectedIndex()+1))));
											System.out.println(countList.get(i).getSelectedIndex());
											break;
										}
										

									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
				
			});

			this.add(panel, this.getComponentCount() - 1);
			this.revalidate();
			this.setSize(this.getWidth(), this.getHeight() + 30);
			this.pack();
			System.out.println(this.getHeight() + "  " + this.getWidth());
		}
	}
}
