package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;


import simulator.Control.Controller;

@SuppressWarnings("serial")
public class BodiesTable extends JPanel {
		
	BodiesTable(Controller ctrl) {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));
	
		BodiesTableModel info = new BodiesTableModel(ctrl);
		JTable table = new JTable(info);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		
	}

}