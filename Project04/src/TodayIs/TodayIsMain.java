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

	private JTextField depositOutput; // 총 입금
	private JTextField wasteOutput; // 총 지출
	private JTextField totalOutput; // 총 액

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
		// 가장 윗 부분
		// -----------------------------------------------------------------------
		/*
		 * JPanel totaldate = new JPanel(); totaldate.setLayout(new
		 * GridLayout(1, 7));
		 * 
		 * totaldate.add(new JLabel(" ")); totaldate.add(yearField);
		 * totaldate.add(new JLabel("  년 ")); totaldate.add(monthField);
		 * totaldate.add(new JLabel("  월 ")); totaldate.add(dateField);
		 * totaldate.add(new JLabel("  일 "));
		 */

		// ------------------------------------------------------------------------
		// 중간 년 월 일 수정 있는 부분
		// ------------------------------------------------------------------------
		f = new Font("Sherif", Font.BOLD, 15);
		
		/*editButton = new JButton("수정");
		editButton.setFont(f);
		editButton.addActionListener(this);*/
		back = new JButton("뒤로가기");
		back.setFont(f);
		back.addActionListener(this);
		inputButton = new JButton("입력");
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
		JLabel y = new JLabel("년");
		y.setFont(f);
		date.add(y);
		JLabel m = new JLabel("월");
		m.setFont(f);
		date.add(monthField);
		date.add(m);
		JLabel d = new JLabel("일");
		d.setFont(f);
		date.add(dateField);
		date.add(d);
		date.add(inputButton);
/*		date.add(editButton);*/

		// --------------------------------------------------------------------------
		// 가장 아래 부분
		// --------------------------------------------------------------------------
		f = new Font("Sherif", Font.BOLD, 18);
		depositOutput = new JTextField(10);
		depositOutput.setText("0");
		depositOutput.setEditable(false);
		JPanel deposit = new JPanel();
		//deposit.setLayout(new GridLayout(1, 3));
		deposit.add(new JLabel("총 입금"));
		deposit.add(depositOutput);
		deposit.add(new JLabel("  원"));

		wasteOutput = new JTextField(10);
		wasteOutput.setText("0");
		wasteOutput.setEditable(false);
		JPanel waste = new JPanel();
		//waste.setLayout(new GridLayout(1, 3));
		waste.add(new JLabel("총 지출"));
		waste.add(wasteOutput);
		waste.add(new JLabel("  원"));

		totalOutput = new JTextField(10);
		totalOutput.setText(
				String.valueOf(Integer.parseInt(depositOutput.getText()) - Integer.parseInt(wasteOutput.getText())));
		totalOutput.setEditable(false);
		JPanel total = new JPanel();
		//total.setLayout(new GridLayout(1, 5));
		total.add(new JLabel("총  액"));
		total.add(totalOutput);
		total.add(new JLabel("  원"));

		JPanel result = new JPanel();
		result.setLayout(new GridLayout(3, 1));
		result.add(deposit);
		result.add(waste);
		result.add(total);

		// ---------------------------------------------------------------
		// 중간 부분
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
		// 여기에 다이어리하고 가계부 넣으면 될듯.
		display.add(result, BorderLayout.SOUTH);

		JPanel inform = new JPanel();
		inform.setLayout(new BorderLayout());
		inform.add(date, BorderLayout.NORTH);
		inform.add(display, BorderLayout.CENTER);

		diaryButton = new JButton("다이어리");
		diaryButton.setFont(f);
		diaryButton.addActionListener(this);
		dutchButton = new JButton("더치페이");
		dutchButton.setFont(f);
		dutchButton.addActionListener(this);
		accountBookButton = new JButton("가계부");
		accountBookButton.setFont(f);
		accountBookButton.addActionListener(this);
		dictionaryButton = new JButton("사전");
		dictionaryButton.setFont(f);
		dictionaryButton.addActionListener(this);
		calorieButton = new JButton("칼로리");
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
		private String[] days = { "일", "월", "화", "수", "목", "금", "토" };
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
			today = Calendar.getInstance(); // 디폴트의 타임 존 및 로케일을 사용해 달력을 가져옵니다.
			cal = new GregorianCalendar();
			year = today.get(Calendar.YEAR);
			currentYear = year;
			moveYear = year;
			yearField.setText(String.valueOf(year));
			month = today.get(Calendar.MONTH) + 1;// 1월의 값이 0
			currentMonth = month;
			moveMonth = month;
			currentDate = cal.get(Calendar.DATE);
			monthField.setText(String.valueOf(month));
			dateField.setText(String.valueOf(cal.get(Calendar.DATE)));
			panNorth = new JPanel();
			panNorth.add(btnBefore = new JButton("Before"));
			btnBefore.setFont(new Font("San-Serif", Font.PLAIN, 25));
			panNorth.add(txtYear = new JTextField(year + "년"));

			panNorth.add(txtMonth = new JTextField(month + "월", 3));

			txtYear.setEnabled(false);
			txtMonth.setEnabled(false);
			panNorth.add(btnAfter = new JButton("After"));
			btnAfter.setFont(new Font("San-Serif", Font.PLAIN, 25));

			f = new Font("Sherif", Font.BOLD, 25);
			txtYear.setFont(f);
			txtMonth.setFont(f);
			this.add(panNorth, bLayout.NORTH);
			// 이놈은 달력에 날에 해당하는 부분
			panWest = new JPanel(new GridLayout(7, 7));// 격자나,눈금형태의 배치관리자
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
			 * get 및 set 를 위한 필드치로, 요일을 나타냅니다. 이 필드의
			 * 값은,SUNDAY,MONDAY,TUESDAY,WEDNESDAY ,THURSDAY,FRIDAY, 및 SATURDAY 가
			 * 됩니다. get()메소드의 의해 요일이 숫자로 반환
			 */
			int j = 0;
			int hopping = 0;
			calBtn[0].setForeground(new Color(255, 0, 0));// 일요일 "일"
			calBtn[6].setForeground(new Color(0, 0, 255));// 토요일 "토"
			for (int i = cal.getFirstDayOfWeek(); i < dayOfWeek; i++) {
				j++;
			}
			/*
			 * 일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해
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
					if ((i + hopping - 1) % 7 == 0) {// 일요일
						calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 0));
					}
					if ((i + hopping) % 7 == 0) {// 토요일
						calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 255));
					}
				}
				/*
				 * 요일을 찍은 다음부터 계산해야 하니 요일을 찍은 버튼의 갯수를 더하고 인덱스가 0부터 시작이니 -1을 해준
				 * 값으로 연산을 해주고 버튼의 색깔을 변경해준다.
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
				this.txtYear.setText(year + "년");
				this.txtMonth.setText(month + "월");
				moveYear = year;
				moveMonth = month;
			} else if (ae.getSource() == btnAfter) {
				this.panWest.removeAll();
				calInput(1);
				gridInit();
				panelInit();
				calSet();
				hideInit();
				this.txtYear.setText(year + "년");
				this.txtMonth.setText(month + "월");
				moveYear = year;
				moveMonth = month;
			} else if (Integer.parseInt(ae.getActionCommand()) >= 1 && Integer.parseInt(ae.getActionCommand()) <= 31) {
				day = Integer.parseInt(ae.getActionCommand());
				// 버튼의 밸류 즉 1,2,3.... 문자를 정수형으로 변환하여 클릭한 날짜를 바꿔준다.
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
				// 일이 찍히지 않은 나머지 버튼을 비활성화 시킨다.
			} // end for
		}// end hideInit()

		public void gridInit() {
			// jPanel3에 버튼 붙이기
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