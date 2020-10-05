package Dictionary;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AllDictionary extends JFrame{
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private JScrollPane scroll;
	
	private Dictionary dic;
	
	private Font f = new Font("Sherif", Font.BOLD, 17);
	
	public AllDictionary(Dictionary dic) {
		super("Dictionary");
		this.dic = dic;
		
		String[] columnNames = {"Word", "Meaning"};
		String[][] rowData = new String[dic.getWordsList().size()][2];
		for(int i = 0; i < dic.getWordsList().size(); i++) {
			for (int j = 0; j <2; j++) {
				rowData[i][j] = dic.getWordsList().get(i).split("///")[j];
			}
		}
		
		defaultTableModel = new DefaultTableModel(rowData, columnNames);
		
		table = new JTable(defaultTableModel);
		table.setFont(f);
		scroll = new JScrollPane(table);
		
		this.add(scroll);
		this.setVisible(true);
		this.pack();
		this.setSize(520, 510);
		System.out.println(this.getWidth() + "  " + this.getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}
}
