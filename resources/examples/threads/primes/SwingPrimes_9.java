package tp.examples.threads.primes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SwingPrimes_9 extends JFrame {

	
	class MyTableModel extends AbstractTableModel {
		
		Object[][] data = { { 1,2,3 }, {4,5,6}, {7,8,9} }; 
		
		@Override
		public int getRowCount() {
			return 3;
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
			fireTableDataChanged();
		}
		
		JTextArea x;
		
		
	}
	
		
	private JButton clearButton;
	private JButton startButton;
	private JButton stopButton;

	MyTableModel tableModel;

	public SwingPrimes_9() {
		super("[=] Primes Generator 5 [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel buttonsBars = new JPanel();
		mainPanel.add(buttonsBars, BorderLayout.PAGE_START);

		startButton = new JButton("Bla");
		buttonsBars.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		startButton = new JButton("Start");
		buttonsBars.add(startButton);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					@Override
					public void run() {
						tableModel.setValueAt("10", 1, 1);
					}
				}.start();

			}
		});

		clearButton = new JButton("Clear");
		buttonsBars.add(clearButton);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					//x=0;
			}
		});

		stopButton = new JButton("Stop");
		buttonsBars.add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		tableModel = new MyTableModel();
		mainPanel.add(new JScrollPane( new JTable(tableModel)), BorderLayout.CENTER);

		setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingPrimes_9();
			}
		});
	}
}
