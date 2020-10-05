package AccountBook.Input;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JTextField;

import AccountBook.Panel.AccountBookPanel;
import DataStructure.MySet;

public class InputAccountBook extends JFrame implements ActionListener, KeyListener {
	private int year;
	private int month;
	private int day;

	private JTextField depositInput;
	private JTextField wasteInput;

	private JPanel inputList;
	/*
	 * private JButton signButton; private JTextField list; private JTextField
	 * amount;
	 */
	private JButton itemAddButton;

	private JButton finish;

	private MySet account;

	private AccountBookPanel accountPanel;
	private JTextField totalDeposit;
	private JTextField totalWaste;
	private JTextField total;

	private File tradeFile;
	private File totalFile;
	private File dir;

	private JFrame mainFrame;

	private Font font = new Font("Sherif", Font.BOLD, 17);

	public InputAccountBook(String year, String month, String day, AccountBookPanel accountPanel,
			JTextField totalDeposit, JTextField totalWaste, JTextField total, JFrame mainFrame) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), accountPanel, totalDeposit,
				totalWaste, total, mainFrame);
	}

	public InputAccountBook(int year, int month, int day, AccountBookPanel accountPanel, JTextField totalDeposit,
			JTextField totalWaste, JTextField total, JFrame mainFrame) {
		super(year + "-" + month + "-" + day + " 의 가계부 입력");

		this.year = year;
		this.month = month;
		this.day = day;
		this.accountPanel = accountPanel;
		this.totalDeposit = totalDeposit;
		this.totalWaste = totalWaste;
		this.total = total;
		this.mainFrame = mainFrame;

		account = new MySet<JButton, JTextField, JTextField, JButton, JButton, JButton>();

		depositInput = new JTextField(10);
		depositInput.setFont(font);
		depositInput.setText("0");
		depositInput.setEditable(false);
		JPanel depositIn = new JPanel();
		JLabel tempDeposit = new JLabel("총 입금");
		tempDeposit.setFont(font);
		depositIn.add(tempDeposit);
		depositIn.add(depositInput);
		JLabel won = new JLabel("  원");
		won.setFont(font);
		depositIn.add(won);

		wasteInput = new JTextField(10);
		wasteInput.setFont(font);
		wasteInput.setText("0");
		wasteInput.setEditable(false);
		JPanel wasteIn = new JPanel();
		JLabel tempWaste = new JLabel("총 지출");
		tempWaste.setFont(font);
		wasteIn.add(tempWaste);
		wasteIn.add(wasteInput);
		wasteIn.add(won);

		JPanel finishButton = new JPanel();
		// finishButton.setLayout(new BorderLayout());
		finish = new JButton("완료");
		finish.setFont(font);
		finish.addActionListener(this);
		finishButton.add(finish/* , BorderLayout.EAST */);

		JPanel displayInput = new JPanel();
		this.itemAddButton = new JButton("항목 추가");
		this.itemAddButton.setFont(font);
		this.itemAddButton.addActionListener(this);
		displayInput.setLayout(new GridLayout(4, 1));
		displayInput.add(itemAddButton);
		displayInput.add(depositIn);
		displayInput.add(wasteIn);
		// displayInput.add(totalIn);
		displayInput.add(finishButton);

		inputList = new JPanel();
		JPanel labelList = new JPanel();
		labelList.setLayout(new GridLayout(1, 10));
		labelList.add(new JLabel(""));
		labelList.add(new JLabel(""));
		JLabel tempList = new JLabel("내역");
		tempList.setFont(font);
		labelList.add(tempList);
		labelList.add(new JLabel(""));
		labelList.add(new JLabel(""));
		JLabel tempAmount = new JLabel("금액");
		tempAmount.setFont(font);
		labelList.add(tempAmount);
		labelList.add(new JLabel(""));
		labelList.add(new JLabel(""));
		labelList.add(new JLabel(""));
		labelList.add(new JLabel(""));
		inputList.setLayout(new GridLayout(0, 1));
		inputList.add(labelList);
		// inputList.add(plusInput);

		JPanel inputPage = new JPanel();
		inputPage.setLayout(new BorderLayout());
		inputPage.add(inputList, BorderLayout.NORTH);
		inputPage.add(displayInput, BorderLayout.SOUTH);

		JPanel dateInput = new JPanel();
		JLabel yearLabel = new JLabel(String.valueOf(year) + "년  ");
		yearLabel.setFont(font);
		dateInput.add(yearLabel);
		JLabel monthLabel = new JLabel(String.valueOf(month) + "월  ");
		monthLabel.setFont(font);
		dateInput.add(monthLabel);
		JLabel dateLabel = new JLabel(String.valueOf(day) + "일");
		dateLabel.setFont(font);
		dateInput.add(dateLabel);
	
		// dateInput.add(new JLabel(""));

		this.setLayout(new BorderLayout());
		this.add(dateInput, BorderLayout.NORTH);
		this.add(inputPage, BorderLayout.CENTER);

		this.setSize(639, 302);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.itemAddButton) {
			JPanel inputPanel = new JPanel();
			// inputPanel.setLayout(new GridLayout(1, 6));
			JButton signButton = new JButton("+");
			signButton.setFont(font);
			JButton inputButton = new JButton("확인");
			inputButton.setFont(font);
			JButton editButton = new JButton("수정");
			editButton.setFont(font);
			JButton deleteButton = new JButton("삭제");
			deleteButton.setFont(font);
			JTextField list = new JTextField(15);
			list.setFont(font);
			JTextField amount = new JTextField(15);
			amount.setFont(font);
			signButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (((JButton) e.getSource()).getText().equals("+")) {
						((JButton) e.getSource()).setText("-");
					} else {
						((JButton) e.getSource()).setText("+");
					}
				}
			});
			inputButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < account.size(); i++) {
						if (account.getInput(i) == e.getSource()) {
							if (((JButton) account.getSign(i)).getText().equals("+")
									&& ((JTextField) account.getList(i)).isEditable()
									&& ((JTextField) account.getAmount(i)).isEditable()) {
								depositInput.setText(String.valueOf(Integer.parseInt(depositInput.getText())
										+ Integer.parseInt(((JTextField) account.getAmount(i)).getText())));
							} else if (((JButton) account.getSign(i)).getText().equals("-")
									&& ((JTextField) account.getList(i)).isEditable()
									&& ((JTextField) account.getAmount(i)).isEditable()) {
								wasteInput.setText(String.valueOf(Integer.parseInt(wasteInput.getText())
										+ Integer.parseInt(((JTextField) account.getAmount(i)).getText())));
							}
							((JTextField) account.getList(i)).setEditable(false);
							((JTextField) account.getAmount(i)).setEditable(false);
							System.out.println(i);
							break;
						}
					}
				}
			});

			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < account.size(); i++) {
						if (account.getEdit(i) == e.getSource()) {
							if (((JButton) account.getSign(i)).getText().equals("+")
									&& !((JTextField) account.getList(i)).isEditable()
									&& !((JTextField) account.getAmount(i)).isEditable()) {
								depositInput.setText(String.valueOf(Integer.parseInt(depositInput.getText())
										- Integer.parseInt(((JTextField) account.getAmount(i)).getText())));
							} else if (((JButton) account.getSign(i)).getText().equals("-")
									&& !((JTextField) account.getList(i)).isEditable()
									&& !((JTextField) account.getAmount(i)).isEditable()) {
								wasteInput.setText(String.valueOf(Integer.parseInt(wasteInput.getText())
										- Integer.parseInt(((JTextField) account.getAmount(i)).getText())));
							}
							((JTextField) account.getList(i)).setEditable(true);
							((JTextField) account.getAmount(i)).setEditable(true);
							System.out.println(i);
							break;
						}
					}
				}
			});

			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < account.size(); i++) {
						if (account.getDelete(i) == e.getSource()) {
							inputList.remove(i + 1);
							// inputPanel.removeAll();

							// inputPanel.remove(i);
							account.remove(i);
							/*
							 * for(int j = 0; j < account.size(); j++) {
							 * inputPanel.add((JButton)account.getSign(j));
							 * inputPanel.add((JTextField)account.getList(j));
							 * inputPanel.add(new JLabel(" "));
							 * inputPanel.add((JTextField)account.getAmount(j));
							 * inputPanel.add(new JLabel("원"));
							 * inputPanel.add((JButton)account.getInput(j));
							 * inputPanel.add((JButton)account.getEdit(j));
							 * inputPanel.add((JButton)account.getDelete(j)); }
							 */
							// inputPanel.repaint();
							System.out.println(i);
							inputList.revalidate();
							if (account.size() == 0) {
								setSize(639, 302);
							} else {
								pack();
							}
							break;
						}
					}
				}

			});
			amount.addKeyListener(this);

			inputPanel.add(signButton);
			inputPanel.add(list);
			inputPanel.add(new JLabel(" "));
			inputPanel.add(amount);
			inputPanel.add(new JLabel("원"));
			inputPanel.add(inputButton);
			inputPanel.add(editButton);
			inputPanel.add(deleteButton);

			this.inputList.add(inputPanel);

			this.pack();
			account.add(signButton, list, amount, inputButton, editButton, deleteButton);
		} else if (e.getSource() == this.finish) {
			for (int i = 0; i < account.size(); i++) {
				if ((((JTextField) account.getList(i)).isEditable()
						&& ((JTextField) account.getAmount(i)).isEditable()))  {
					return;
				}
			}
			if(account.size() == 0) return;
			dir = new File("AccountBook\\" + year + "-" + month + "-" + day);
			if (!dir.exists()) {
				dir.mkdirs();
				System.out.println(dir.getPath() + "디렉토리가 생성되었습니다.");
			}
			tradeFile = new File(dir.getPath() + "\\거래내역.txt");
			try {

				FileWriter fw = new FileWriter(tradeFile, true);
				for (int i = 0; i < account.size(); i++) {
					fw.write(((JButton) this.account.getSign(i)).getText() + '\n');
					fw.write(((JTextField) this.account.getList(i)).getText() + '\n');
					fw.write(((JTextField) this.account.getAmount(i)).getText() + '\n');
				}

				fw.close();
				totalFile = new File(dir.getPath() + "\\총 내역.txt");
				if (tradeFile.exists()) {
					fw = new FileWriter(totalFile);
					ArrayList<String> plus = new ArrayList<String>();
					ArrayList<String> minus = new ArrayList<String>();
					BufferedReader br = new BufferedReader(new FileReader(tradeFile));
					while (true) {
						String sign = null;
						sign = br.readLine();
						if (sign == null)
							break;
						System.out.println("aa" + sign);
						System.out.println(br.readLine());
						if (sign.equals("+")) {
							plus.add(br.readLine());
						} else {
							minus.add(br.readLine());
						}
					}
					String total = "0";
					for (int i = 0; i < plus.size(); i++) {
						total = String.valueOf(Integer.parseInt(total) + Integer.parseInt(plus.get(i)));
					}
					fw.write(total + '\n');
					total = "0";
					for (int i = 0; i < minus.size(); i++) {
						total = String.valueOf(Integer.parseInt(total) + Integer.parseInt(minus.get(i)));
					}

					fw.write(total);
					fw.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			this.accountPanel.init();
			this.dispose();

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();

		if (!Character.isDigit(c)) {
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

	public JButton getFinish() {
		return finish;
	}

}
