import javax.swing.*;
import java.awt.*;

public class MyGui {
	JButton b;
	public static void main (String[] args);{
		MyGui gui = new MyGui();
		gui.go();
	}
	
	public void go() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		b = new JButton("HEY");
		b.addListener(new BListener());
		frame.getContentPane().add(BorderLayout.CENTER, b);
		frame.setSize(200,200);
		frame.setVisible(true);
	}
	
	public class BListener implements ActionListener {
		public void actionPerformed() {
			if(b.getText().equals("HEY")) {
				b.setText("YOU");
			} else {
				b.setText("HEY");
			}
		}
	} //inner
} //outer
