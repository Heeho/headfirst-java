import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Animation {
	JFrame frame;
	JButton b;
	boolean start;
	boolean quit;

	public static void main(String[] args) {
		Animation gui = new Animation();
		gui.go();
	}

	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		b = new JButton("Start");
		b.addActionListener(new BListener());

		MyDrawPanel drawPanel = new MyDrawPanel();
		
		frame.getContentPane().add(drawPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, b);
		frame.setSize(300,300);
		frame.setVisible(true);

		while(!quit) {
			while(start) {
				drawPanel.repaint();
			}
		}
	}//go

	class BListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(!start) {
				start = true;
				b.setText("Stop");
			} else {
				start = false;
				b.setText("Start");
			}
		}
	}
			
	class MyDrawPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			int red = (int) (Math.random()*256);
			int green = (int) (Math.random()*256);
			int blue = (int) (Math.random()*256);
			Color startColor = new Color(red, green, blue);

			red = (int) (Math.random()*256);
			green = (int) (Math.random()*256);
			blue = (int) (Math.random()*256);
			Color endColor = new Color(red, green, blue);

			GradientPaint gradient = new GradientPaint(70,70, startColor, 150,150, endColor);
			
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			g2d.setPaint(gradient);
			g2d.fillOval(70,70,100,100);
		}//method
	} //inner
} //outer



