package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.Control.Controller;

public class MainWindow {

	JPanel superiorTab;
	
	public MainWindow(Controller ctrl) {

		JFrame main = new JFrame("Physics Simulator");
		
		BorderLayout ly =  new BorderLayout();
		main.setLayout(ly);
		
		main.add(new ControlPanel(ctrl),  BorderLayout.NORTH);
		main.add(createCenterPanel(ctrl), BorderLayout.CENTER);
		main.add(new StatusBar(ctrl),  BorderLayout.SOUTH);

		//main.pack();
		main.setSize(800, 480);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel createCenterPanel(Controller ctrl) {
		 JPanel cPanel = new JPanel();
		 cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		 
		 //Componente 1
		 JComponent Table = new BodiesTable(ctrl);
		 Table.setPreferredSize(new Dimension(480, 150));	//Tengo que poner medidas minimas a las componentes aunque despues se reescalen para que se muestren bien
		 
		 //Componente 2
		 JComponent Viewer = new Viewer(ctrl);
		 Viewer.setPreferredSize(new Dimension(480, 400));
		 
		 //Añadimos las componentes
		 cPanel.add(Table);
		 cPanel.add(Viewer);
		 
		 return cPanel;
	}
	
}
