package TodayIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AccountBook.TotalAccountBook;
import AccountBook.Input.InputAccountBook;
import AccountBook.Panel.AccountBookPanel;
import Calorie.Calorie;
import Diary.TotalDiary;
import Diary.Input.InputDiary;
import Diary.Panel.DiaryPanel;
import Dictionary.AllDictionary;
import Dictionary.Dictionary;
import Dictionary.SearchWord;
import Dictionary.TodayWord;
import DutchPay.Dutch;

public class TodayIsMain extends JFrame implements ActionListener, KeyListener {
	private int currentYear;
	private int currentMonth;
	private int currentDate;
	
	private int moveYear;
	private int moveMonth;
	
	private JLabel yearField;
	private JLabel monthField;
	private JLabel dateField;

	/*private JButton editButton;*/

	private DiaryPanel diaryPanel;
	private AccountBookPanel accountPanel;

	private JTextField depositOutput; // �� �Ա�
	private JTextField wasteOutput; // �� ����
	private JTextField totalOutput; // �� ��

	private JButton inputButton;

	private JButton dutchButton;
	private JButton calorieButton;
	private JButton diaryButton;
	private JButton accountBookButton;
	private JButton dictionaryButton;

	private InputAccountBook inputAccount;
	
	private boolean editable = true;
	
	private CardLayout card;
	
	private Font f;
	
	private JButton back;
	
	private Dictionary dictionary;
	/*private TotalAccountBook totalAccount;*/

	public TodayIsMain() {
		super("Today is");
		dictionary = new Dictionary();
		// -----------------------------------------------------------------------
		// ���� �� �κ�
		// -----------------------------------------------------------------------
		/*
		 * JPanel totaldate = new JPanel(); totaldate.setLayout(new
		 * GridLayout(1, 7));
		 * 
		 * totaldate.add(new JLabel(" ")); totaldate.add(yearField);
		 * totaldate.add(new JLabel("  �� ")); totaldate.add(monthField);
		 * totaldate.add(new JLabel("  �� ")); totaldate.add(dateField);
		 * totaldate.add(new JLabel("  �� "));
		 */

		// ------------------------------------------------------------------------
		// �߰� �� �� �� ���� �ִ� �κ�
		// ------------------------------------------------------------------------
		f = new Font("Sherif", Font.BOLD, 15);
		
		/*editButton = new JButton("����");
		editButton.setFont(f);
		editButton.addActionListener(this);*/
		back = new JButton("�ڷΰ���");
		back.setFont(f);
		back.addActionListener(this);
		inputButton = new JButton("�Է�");
		inputButton.setFont(f);
		inputButton.addActionListener(this);
		f = new Font("Sherif", Font.BOLD, 25);
		yearField = new JLabel();
		yearField.setFont(f);
		monthField = new JLabel();
		monthField.setFont(f);
		dateField = new JLabel();
		dateField.setFont(f);

		JPanel date = new JPanel();
		//date.setLayout(new GridLayout(1, 5));
		date.add(back);
		date.add(yearField);
		JLabel y = new JLabel("��");
		y.setFont(f);
		date.add(y);
		JLabel m = new JLabel("��");
		m.setFont(f);
		date.add(monthField);
		date.add(m);
		JLabel d = new JLabel("��");
		d.setFont(f);
		date.add(dateField);
		date.add(d);
		date.add(inputButton);
/*		date.add(editButton);*/

		// --------------------------------------------------------------------------
		// ���� �Ʒ� �κ�
		// --------------------------------------------------------------------------
		f = new Font("Sherif", Font.BOLD, 18);
		depositOutput = new JTextField(10);
		depositOutput.setText("0");
		depositOutput.setEditable(false);
		JPanel deposit = new JPanel();
		//deposit.setLayout(new GridLayout(1, 3));
		deposit.add(new JLabel("�� �Ա�"));
		deposit.add(depositOutput);
		deposit.add(new JLabel("  ��"));

		wasteOutput = new JTextField(10);
		wasteOutput.setText("0");
		wasteOutput.setEditable(false);
		JPanel waste = new JPanel();
		//waste.setLayout(new GridLayout(1, 3));
		waste.add(new JLabel("�� ����"));
		waste.add(wasteOutput);
		waste.add(new JLabel("  ��"));

		totalOutput = new JTextField(10);
		totalOutput.setText(
				String.valueOf(Integer.parseInt(depositOutput.getText()) - Integer.parseInt(wasteOutput.getText())));
		totalOutput.setEditable(false);
		JPanel total = new JPanel();
		//total.setLayout(new GridLayout(1, 5));
		total.add(new JLabel("��  ��"));
		total.add(totalOutput);
		total.add(new JLabel("  ��"));

		JPanel result = new JPanel();
		result.setLayout(new GridLayout(3, 1));
		result.add(deposit);
		result.add(waste);
		result.add(total);

		// ---------------------------------------------------------------
		// �߰� �κ�
		// ---------------------------------------------------------------
		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		// ---------------------------------------
		JPanel totalInfor = new JPanel();
		totalInfor.setLayout(new GridLayout(2, 1));
		diaryPanel = new DiaryPanel(this);
		diaryPanel.setLayout(new GridLayout(0, 1));
		accountPanel = new AccountBookPanel(this, this.depositOutput, this.wasteOutput, this.totalOutput);
		accountPanel.setLayout(new GridLayout(0, 1));
		
		totalInfor.add(diaryPanel);
		totalInfor.add(accountPanel);
		display.add(totalInfor, BorderLayout.CENTER);
		// ---------------------------------------
		// ���⿡ ���̾�ϰ� ����� ������ �ɵ�.
		display.add(result, BorderLayout.SOUTH);

		JPanel inform = new JPanel();
		inform.setLayout(new BorderLayout());
		inform.add(date, BorderLayout.NORTH);
		inform.add(display, BorderLayout.CENTER);

		diaryButton = new JButton("���̾");
		diaryButton.setFont(f);
		diaryButton.addActionListener(this);
		dutchButton = new JButton("��ġ����");
		dutchButton.setFont(f);
		dutchButton.addActionListener(this);
		accountBookButton = new JButton("�����");
		accountBookButton.setFont(f);
		accountBookButton.addActionListener(this);
		dictionaryButton = new JButton("����");
		dictionaryButton.setFont(f);
		dictionaryButton.addActionListener(this);
		calorieButton = new JButton("Į�θ�");
		calorieButton.setFont(f);
		calorieButton.addActionListener(this);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 5));
		buttons.add(diaryButton);
		buttons.add(accountBookButton);
		buttons.add(dutchButton);
		buttons.add(dictionaryButton);
		buttons.add(calorieButton);

		JPanel calendar = new JPanel();
		calendar.setLayout(new BorderLayout());
		calendar.add(buttons, BorderLayout.NORTH);
		calendar.add(new Calender(), BorderLayout.SOUTH);

		/*JPanel Center = new JPanel();
		Center.setLayout(new BorderLayout());
		Center.add(calendar, BorderLayout.NORTH);*/
		
		
		//Center.add(inform, BorderLayout.CENTER);


		this.diaryPanel.setYear(Integer.parseInt(this.yearField.getText()));
		this.diaryPanel.setMonth(Integer.parseInt(this.monthField.getText()));
		this.diaryPanel.setDate(Integer.parseInt(this.dateField.getText()));
		this.diaryPanel.init();
		
		this.accountPanel.setYear(Integer.parseInt(this.yearField.getText()));
		this.accountPanel.setMonth(Integer.parseInt(this.monthField.getText()));
		this.accountPanel.setDate(Integer.parseInt(this.dateField.getText()));
		this.accountPanel.init();

		this.setLayout(new BorderLayout());
		
		
		
		card = new CardLayout();
		this.getContentPane().setLayout(card);
		this.getContentPane().add("Calendar", calendar);
		this.getContentPane().add("Infor", inform);
		
		card.show(this.getContentPane(), "Calendar");
		
		//this.add(totaldate, BorderLayout.NORTH);
		//this.add(Center, BorderLayout.CENTER);
		this.setVisible(true);
		this.setResizable(false);
		
		this.setSize(680, 500);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		TodayIsMain ABmain = new TodayIsMain();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.accountBookButton) {
			/*System.out.println("input");
			inputAccount = new InputAccountBook(yearField.getText(), monthField.getText(), dateField.getText(),
					this.accountPanel, this.depositOutput, this.wasteOutput, this.totalOutput, this);
			inputAccount.setVisible(true);*/
			new TotalAccountBook(moveYear, moveMonth);
		} else if (e.getSource() == this.diaryButton) {
			/*new InputSecretDiary(this.yearField.getText(), this.monthField.getText(), this.dateField.getText(), this.diaryPanel);*/
			new TotalDiary(moveYear, moveMonth);
		} else if(e.getSource() == this.dutchButton) {
			new Dutch(currentYear, currentMonth, currentDate, this.accountPanel,
					this.wasteOutput, this.totalOutput);
		} /*else if (e.getSource() == this.editButton && this.editable) {
			editable = false;
		} */else if(e.getSource() == this.back) {
			card.show(this.getContentPane(), "Calendar");
			this.setSize(680, 500);
		} else if (e.getSource() == this.dictionaryButton) {
			int select = new JOptionPane().showOptionDialog(null, "Select Item", "What you want?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Today Word", "Search Word", "Dictionary"}, null);
			if(select == JOptionPane.YES_OPTION) {
				new TodayWord(currentYear, currentMonth, currentDate, dictionary);
			} else if (select == JOptionPane.NO_OPTION) {
				new SearchWord(dictionary);
			} else if(select == JOptionPane.CANCEL_OPTION) {
				new AllDictionary(this.dictionary);
			}
			
		} else if(e.getSource() == this.inputButton) {
			int select = new JOptionPane().showOptionDialog(null, "Select Item", "What you want?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Diary", "Account Book"}, null);
			if(select == JOptionPane.YES_OPTION) {
				new InputDiary(this.yearField.getText(), this.monthField.getText(), this.dateField.getText(), this.diaryPanel);
			} else if (select == JOptionPane.NO_OPTION) {
				inputAccount = new InputAccountBook(yearField.getText(), monthField.getText(), dateField.getText(),
						this.accountPanel, this.depositOutput, this.wasteOutput, this.totalOutput, this);
				inputAccount.setVisible(true);
			}
		} else if(e.getSource() == this.calorieButton) {
			new Calorie(currentYear, currentMonth, currentDate);
		}
	}

	class Calender extends JPanel implements ActionListener {
		private String[] days = { "��", "��", "ȭ", "��", "��", "��", "��" };
		private int year, month, day, todays, memoday = 0;
		private Font f;
		private Color bc, fc;
		private Calendar today;
		private Calendar cal;
		private JButton btnBefore, btnAfter;
		private JButton[] calBtn = new JButton[49];
		private JLabel thing;
		private JLabel time;
		private JPanel panWest;
		private JPanel panSouth;
		private JPanel panNorth;
		private JTextField txtMonth, txtYear;
		private JTextField txtTime;
		private BorderLayout bLayout = new BorderLayout();

		////////////////////////////////////////
		public Calender() {
			this.setLayout(bLayout);
			today = Calendar.getInstance(); // ����Ʈ�� Ÿ�� �� �� �������� ����� �޷��� �����ɴϴ�.
			cal = new GregorianCalendar();
			year = today.get(Calendar.YEAR);
			currentYear = year;
			moveYear = year;
			yearField.setText(String.valueOf(year));
			month = today.get(Calendar.MONTH) + 1;// 1���� ���� 0
			currentMonth = month;
			moveMonth = month;
			currentDate = cal.get(Calendar.DATE);
			monthField.setText(String.valueOf(month));
			dateField.setText(String.valueOf(cal.get(Calendar.DATE)));
			panNorth = new JPanel();
			panNorth.add(btnBefore = new JButton("Before"));
			btnBefore.setFont(new Font("San-Serif", Font.PLAIN, 25));
			panNorth.add(txtYear = new JTextField(year + "��"));

			panNorth.add(txtMonth = new JTextField(month + "��", 3));

			txtYear.setEnabled(false);
			txtMonth.setEnabled(false);
			panNorth.add(btnAfter = new JButton("After"));
			btnAfter.setFont(new Font("San-Serif", Font.PLAIN, 25));

			f = new Font("Sherif", Font.BOLD, 25);
			txtYear.setFont(f);
			txtMonth.setFont(f);
			this.add(panNorth, bLayout.NORTH);
			// �̳��� �޷¿� ���� �ش��ϴ� �κ�
			panWest = new JPanel(new GridLayout(7, 7));// ���ڳ�,���������� ��ġ������
			f = new Font("Sherif", Font.BOLD, 10);
			gridInit();
			calSet();
			hideInit();
			this.add(panWest, bLayout.CENTER);

			btnBefore.addActionListener(this);
			btnAfter.addActionListener(this);

			setPreferredSize(new Dimension(600, 400));
			setMaximumSize(this.getPreferredSize());
			setMinimumSize(this.getPreferredSize());
			setVisible(true);
		}// end constuctor

		public void calSet() {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, (month - 1));
			cal.set(Calendar.DATE, 1);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			/*
			 * get �� set �� ���� �ʵ�ġ��, ������ ��Ÿ���ϴ�. �� �ʵ���
			 * ����,SUNDAY,MONDAY,TUESDAY,WEDNESDAY ,THURSDAY,FRIDAY, �� SATURDAY ��
			 * �˴ϴ�. get()�޼ҵ��� ���� ������ ���ڷ� ��ȯ
			 */
			int j = 0;
			int hopping = 0;
			calBtn[0].setForeground(new Color(255, 0, 0));// �Ͽ��� "��"
			calBtn[6].setForeground(new Color(0, 0, 255));// ����� "��"
			for (int i = cal.getFirstDayOfWeek(); i < dayOfWeek; i++) {
				j++;
			}
			/*
			 * �Ͽ��Ϻ��� �״��� ù���� ���ϱ��� ��ĭ���� �����ϱ� ����
			 */
			hopping = j;
			for (int kk = 0; kk < hopping; kk++) {

				calBtn[kk + 7].setText("");

			}
			for (int i = cal.getMinimum(Calendar.DAY_OF_MONTH); i <= cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
				cal.set(Calendar.DATE, i);
				if (cal.get(Calendar.MONTH) != month - 1) {
					break;
				}

				todays = i;
				if (memoday == 1) {
					calBtn[i + 6 + hopping].setForeground(new Color(0, 255, 0));
				} else {
					calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 0));
					if ((i + hopping - 1) % 7 == 0) {// �Ͽ���
						calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 0));
					}
					if ((i + hopping) % 7 == 0) {// �����
						calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 255));
					}
				}
				/*
				 * ������ ���� �������� ����ؾ� �ϴ� ������ ���� ��ư�� ������ ���ϰ� �ε����� 0���� �����̴� -1�� ����
				 * ������ ������ ���ְ� ��ư�� ������ �������ش�.
				 */
				calBtn[i + 6 + hopping].setFont(new Font("San-Serif", Font.PLAIN, 25));
				calBtn[i + 6 + hopping].setText((i) + "");
			} // for
		}// end Calset()

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnBefore) {
				this.panWest.removeAll();
				calInput(-1);
				gridInit();
				panelInit();
				calSet();
				hideInit();
				this.txtYear.setText(year + "��");
				this.txtMonth.setText(month + "��");
				moveYear = year;
				moveMonth = month;
			} else if (ae.getSource() == btnAfter) {
				this.panWest.removeAll();
				calInput(1);
				gridInit();
				panelInit();
				calSet();
				hideInit();
				this.txtYear.setText(year + "��");
				this.txtMonth.setText(month + "��");
				moveYear = year;
				moveMonth = month;
			} else if (Integer.parseInt(ae.getActionCommand()) >= 1 && Integer.parseInt(ae.getActionCommand()) <= 31) {
				day = Integer.parseInt(ae.getActionCommand());
				// ��ư�� ��� �� 1,2,3.... ���ڸ� ���������� ��ȯ�Ͽ� Ŭ���� ��¥�� �ٲ��ش�.
				System.out.println(+year + "-" + month + "-" + day);

				yearField.setText(String.valueOf(year));
				monthField.setText(String.valueOf(month));
				dateField.setText(String.valueOf(day));
				
				diaryPanel.init(year, month, day);
				accountPanel.init(year, month, day);
				card.show(getContentPane(), "Infor");
				TodayIsMain.this.setSize(TodayIsMain.this.getSize().width, TodayIsMain.this.getSize().height + 100);

				calSet();

			}
		}// end actionperformed()

		public void hideInit() {
			for (int i = 0; i < calBtn.length; i++) {
				if ((calBtn[i].getText()).equals(""))
					calBtn[i].setEnabled(false);
				// ���� ������ ���� ������ ��ư�� ��Ȱ��ȭ ��Ų��.
			} // end for
		}// end hideInit()

		public void gridInit() {
			// jPanel3�� ��ư ���̱�
			for (int i = 0; i < days.length; i++) {

				panWest.add(calBtn[i] = new JButton(days[i]));

				calBtn[i].setFont(new Font("San-Serif", Font.PLAIN, 25));
				calBtn[i].setContentAreaFilled(false);
				calBtn[i].setBorderPainted(false);
			}
			for (int i = days.length; i < 49; i++) {
				panWest.add(calBtn[i] = new JButton(""));
				calBtn[i].addActionListener(this);
			}
		}// end gridInit()

		public void panelInit() {
			GridLayout gridLayout1 = new GridLayout(7, 7);
			panWest.setLayout(gridLayout1);
		}// end panelInit()

		public void calInput(int gap) {
			month += (gap);
			if (month <= 0) {
				month = 12;
				year = year - 1;
			} else if (month >= 13) {
				month = 1;
				year = year + 1;
			}
		}// end calInput()

		public JButton[] getCalBtn() {
			return calBtn;
		}

		public int getYear() {
			return year;
		}

		public int getMonth() {
			return month;
		}

		public int getDay() {
			return day;
		}
	}// end class

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

}