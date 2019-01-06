//peepin' off the windows now

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SimpleGui1B {
	JFrame frame;
	JButton button;
	MyDrawPanel1 pan;
	MyDrawPanel2 im;

	public static void main(String[] args) {
		SimpleGui1B gui = new SimpleGui1B();
		gui.go();
	}

	public void go() {
		frame = new JFrame();
		button = new JButton("OW ! !");
		
		button.addActionListener(new MyDrawPanel1());
		button.addActionListener(new MyDrawPanel2());

		frame.getContentPane().add(BorderLayout.WEST, button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	class MyDrawPanel1 extends JPanel implements ActionListener {
		public void paintComponent(Graphics g) {
			g.setColor(Color.orange);
			g.fillRect(20,50,100,100);
		}
		public void actionPerformed(ActionEvent event) {
			if(button.getText().equals("OW ! !")) {
				button.setText("OUCH ! !");
				pan = new MyDrawPanel1();
				frame.getContentPane().add(pan);
			}
		}
	}

	class MyDrawPanel2 extends JPanel implements ActionListener {
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon("sshot.png").getImage();
			g.drawImage(img,3,4,this);
		}
		public void actionPerformed(ActionEvent event) {
			if(button.getText().equals("OUCH ! !")){
				button.setText("OW ! !");
				im = new MyDrawPanel2();
				frame.getContentPane().add(im);
			}
		}
	}
} //outer
