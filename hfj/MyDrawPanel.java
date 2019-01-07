import javax.swing.*;
import java.awt.*;

class MyDrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(20,50,100,100);
	}

	public static void main(String[]args) {
		MyDrawPanel pan = new MyDrawPanel();
		JFrame frame = new JFrame();

		frame.getContentPane().add(pan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}