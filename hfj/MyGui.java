import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MyGui {
	JFrame frame;
	JButton b;
	public static void main (String[] args){
		MyGui gui = new MyGui();
		gui.go();
	}
	
	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		b = new JButton("HEY");
		b.addActionListener(new BListener());
		frame.getContentPane().add(BorderLayout.CENTER, b);
		frame.setSize(200,200);
		frame.setVisible(true);
	}
	
	class BListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(b.getText().equals("HEY")) {
				b.setText("YOU");
			} else {
				b.setText("HEY");
			}
		}
	} //inner
} //outer
