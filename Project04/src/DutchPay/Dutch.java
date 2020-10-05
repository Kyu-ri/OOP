package DutchPay;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AccountBook.Panel.AccountBookPanel;

public class Dutch extends JFrame implements ActionListener, KeyListener {
	private int year;
	private int month;
	private int date;

	private AccountBookPanel accountBookPanel;
	private JTextField totalWaste;
	private JTextField total;

	private File dir;
	private File tradeFile;
	private File totalFile;

	private JButton addButton;

	private JTextField ownMoney;

	private JTextField tradeInfor;
	private JTextField totalDutchAccount;
	private JTextField divideAccount;

	private ArrayList<JTextField> list;
	private ArrayList<JButton> deleteButtons;

	private JButton finish;

	private JPanel dutchList;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);

	public Dutch(String year, String month, String date, AccountBookPanel accountBookPanel, JTextField totalWaste,
			JTextField total) {
		this(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), accountBookPanel, totalWaste,
				total);
	}

	public Dutch(int year, int month, int date, AccountBookPanel accountBookPanel, JTextField totalWaste,
			JTextField total) {

		super("더치페이");

		this.year = year;
		this.month = month;
		this.date = date;
		this.accountBookPanel = accountBookPanel;
		this.totalWaste = totalWaste;
		totalWaste.setFont(f);
		this.total = total;
		this.deleteButtons = new ArrayList<JButton>();

		list = new ArrayList<JTextField>();

		// 친구추가, +button이 있는 줄
		addButton = new JButton("사람 추가");
		addButton.setFont(f);
		addButton.addActionListener(this);
		tradeInfor = new JTextField(10);
		tradeInfor.setFont(f);
		JPanel addFriend = new JPanel();
		// addFriend.setLayout(new GridLayout(1, 3));
		addFriend.add(addButton);
		JLabel tradeList = new JLabel("       거래내역");
		tradeList.setFont(f);
		addFriend.add(tradeList);
		addFriend.add(tradeInfor);

		// 더치 페이할 친구들을 추가하는 곳
		dutchList = new JPanel();
		ownMoney = new JTextField(10);
		ownMoney.setFont(f);
		ownMoney.setText("0");
		ownMoney.setEditable(false);
		dutchList.setLayout(new GridLayout(0, 1));
		JPanel ownPanel = new JPanel();
		ownPanel.setLayout(new GridLayout(1, 5));
		ownPanel.add(new JLabel(""));
		JLabel me = new JLabel("나 : ");
		me.setFont(f);
		ownPanel.add(me);
		ownPanel.add(new JLabel(""));
		ownPanel.add(ownMoney);
		ownPanel.add(new JLabel(""));
		dutchList.add(ownPanel);

		list.add(ownMoney);

		/*
		 * DefaultTableModel DutchListModel = new DefaultTableModel();
		 * DutchListModel.setColumnCount(5); JTable dutchInform = new
		 * JTable(DutchListModel);
		 * 
		 * DutchList.add(dutchInform);
		 */

		// 친구추가 줄과 더치페이 친구들 목록을 합치는 부분
		JPanel addLine = new JPanel();
		addLine.setLayout(new BorderLayout());
		addLine.add(addFriend, BorderLayout.NORTH);
		addLine.add(dutchList, BorderLayout.CENTER);

		// 총 금액, 나눠야할 금액 입력, 보여주는 곳
		totalDutchAccount = new JTextField();
		totalDutchAccount.setFont(f);
		totalDutchAccount.setText("0");
		totalDutchAccount.addActionListener(this);
		totalDutchAccount.addKeyListener(this);
		
		divideAccount = new JTextField();
		divideAccount.setFont(f);
		divideAccount.setText("1");
		divideAccount.addActionListener(this);
		divideAccount.addKeyListener(this);
		
		JPanel totalDutch = new JPanel();
		totalDutch.setLayout(new GridLayout(2, 5));
		
		totalDutch.add(new JLabel(""));//0, 0
		
		JLabel totalAmountLabel = new JLabel("총 금액 : ");
		totalAmountLabel.setFont(f);
		totalDutch.add(totalAmountLabel);//0, 1
		
		totalDutch.add(totalDutchAccount);
		
		JLabel wonLabel = new JLabel("원");
		wonLabel.setFont(f);
		totalDutch.add(wonLabel);
		
		
		
		totalDutch.add(new JLabel("원"));
		totalDutch.add(new JLabel(""));
		totalDutch.add(new JLabel(""));
		JLabel divideLabel = new JLabel("나눌 단위 :");
		divideLabel.setFont(f);
		totalDutch.add(divideLabel);
		totalDutch.add(divideAccount);
		totalDutch.add(wonLabel);
		
		totalDutch.add(new JLabel(""));

		// 총금액 보여주는 곳과, 위에 친구추가, 친구들 목록 을 합치는 곳
		JPanel totalDutchPage = new JPanel();
		totalDutchPage.setLayout(new BorderLayout());
		totalDutchPage.add(addLine, BorderLayout.CENTER);
		totalDutchPage.add(totalDutch, BorderLayout.SOUTH);

		// 밑에 두개의 버튼 있는곳
		JPanel DutchButton = new JPanel();
		finish = new JButton("확인");
		finish.setFont(f);
		finish.addActionListener(this);

		DutchButton.add(finish);

		// 최종적으로 합침
		this.setLayout(new BorderLayout());
		this.add(totalDutchPage, BorderLayout.CENTER);
		this.add(DutchButton, BorderLayout.SOUTH);

		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		//this.setSize(500, 190);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
	public void keyPressed(KeyEvent e){}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.addButton) {
			String name = JOptionPane.showInputDialog("Input name");
			if (name == null || name.equals(""))
				return;
			JPanel temp = new JPanel();
			JTextField text = new JTextField(10);
			text.setFont(f);
			text.setEditable(false);
			temp.setLayout(new GridLayout(1, 5));
			temp.add(new JLabel(""));
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(f);
			JLabel tt = new JLabel(":");
			tt.setFont(f);
			temp.add(nameLabel);
			temp.add(tt);
			temp.add(text);
			JButton btn = new JButton("삭제");
			btn.setFont(f);
			
			deleteButtons.add(btn);
			temp.add(btn);

			list.add(text);
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 0; i <deleteButtons.size(); i++) {
						if(e.getSource() == deleteButtons.get(i)) {
							dutchList.remove(i + 1);
							dutchList.revalidate();
							setSize(getSize().width, getSize().height - 40);
							deleteButtons.remove(i);
							list.remove(i + 1);
							System.out.println(deleteButtons.size());
							System.out.println(list.size());
							calculateDutch();
						}
					}
					
				}
			});

			this.calculateDutch();

			this.dutchList.add(temp);
			this.setSize(this.getSize().width, this.getSize().height + 40);
		} else if (e.getSource() == this.totalDutchAccount || e.getSource() == this.divideAccount) {
			this.calculateDutch();
		} else if (e.getSource() == this.finish) {
			int ok = new JOptionPane().showOptionDialog(null, "입력하시겠습니까?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(ok == JOptionPane.NO_OPTION || ok == JOptionPane.CLOSED_OPTION) return;
			System.out.println("aa");
			dir = new File("AccountBook\\" + year + "-" + month + "-" + date);
			if (!dir.exists()) {
				dir.mkdirs();
				System.out.println(dir.getPath() + " 디렉토리가 생성되었습니다.");
			}
			tradeFile = new File(dir.getPath() + "\\" + "거래내역.txt");
			totalFile = new File(dir.getPath() + "\\" + "총 내역.txt");

			try {
				FileWriter fw = new FileWriter(tradeFile, true);
				fw.write("-" + '\n');
				fw.write(this.tradeInfor.getText() + '\n');
				fw.write(this.ownMoney.getText() + '\n');

				fw.close();
				String deposit = "0";
				String waste = "0";
				if(totalFile.exists()) {
					BufferedReader br = new BufferedReader(new FileReader(totalFile));
					deposit = br.readLine();
					waste = br.readLine();
					System.out.println(deposit);
					System.out.println(waste);
					System.out.println(this.ownMoney.getText());
					br.close();
				}
				
				this.totalWaste
						.setText(String.valueOf(Integer.parseInt(waste) + Integer.parseInt(this.ownMoney.getText())));
				this.total.setText(
						String.valueOf(Integer.parseInt(deposit) - Integer.parseInt(this.totalWaste.getText())));

				fw = new FileWriter(totalFile);
				fw.write(deposit + '\n');
				fw.write(String.valueOf(Integer.parseInt(waste) + Integer.parseInt(this.ownMoney.getText())) + '\n');

				fw.close();
				this.dispose();

				accountBookPanel.init();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void calculateDutch() {
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setText(String.valueOf(
					(Integer.parseInt(totalDutchAccount.getText()) / Integer.parseInt(this.divideAccount.getText()))
							/ list.size() * Integer.parseInt(this.divideAccount.getText())));
			total += Integer.parseInt(list.get(i).getText());
		}

		morePay(total);

	}

	private void morePay(int total) {
		int remainAmount = (Integer.parseInt(totalDutchAccount.getText()) - total);
		int morePay = 0;
		System.out.println("total : " + total);
		System.out.println("RemainAmount : " + remainAmount);

		if (total != Integer.parseInt(totalDutchAccount.getText())) {
			int morePerson = remainAmount / Integer.parseInt(this.divideAccount.getText());
			System.out.println("more person : " + morePerson);
			morePay = (Integer.parseInt(this.divideAccount.getText())) * morePerson;
			System.out.println("more pay : " + morePay);
			int[] selected = new int[morePerson];
			for (int i = 0; i < morePerson; i++) {
				selected[i] = (int) (Math.random() * list.size());
				System.out.println("Selected person : " + selected[i]);
				list.get(selected[i]).setText(String.valueOf(Integer.parseInt(list.get(selected[i]).getText())
						+ (Integer.parseInt(this.divideAccount.getText()))));
			}
		}
		remain(Integer.parseInt(totalDutchAccount.getText()) - (total + morePay));
	}

	private void remain(int remainMoney) {
		System.out.println(remainMoney);
		int selected = (int) (Math.random() * list.size());
		list.get(selected).setText(String.valueOf(Integer.parseInt(list.get(selected).getText()) + remainMoney));
	}
}
