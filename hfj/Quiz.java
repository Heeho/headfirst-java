import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Quiz {
	JFrame startScreen;
	JButton builder;
	JButton play;
	
	public void init() {
		//make starting screen
		//to choose loader/builder
	}
	//inners
	class BuilderButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//call builder gui method
			//close starting screen frame
		}
	}
	class PlayButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//call player gui method
			//close starting screen frame
		}
	}
}

class QuizCardBuilder {
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;

	public void go() {
		frame = new JFrame(“Quiz Card Builder”);
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font(“sanserif”, Font.BOLD, 24);
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);

		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton nextButton = new JButton(“Next Card”);
		
		cardList = new ArrayList<QuizCard>();

		JLabel qLabel = new JLabel(“Question:”);
		JLabel aLabel = new JLabel(“Answer:”);

		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//add current card to a list
			//make next card, clear text fields
		}
	}
		
	class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//bring up a file dialog box to name the set and save it
			//save card set to a file using saveFile()
		}
	}

	class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//clear q/a text fields and card list to make a new card set
		}
	}

	private void saveFile(File file) {
		//iterate through the list of cards
		//write each to a text file in a parseable way
	}
}

class QuizCardPlayer {
	public void callGui() {
		//1.File>Load
		//to load a card set
		//2.Show Answer button
	}
	
	class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//add current card to a list
			//make next card, clear text fields
		}
	}
	
	class ShowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//show answer in bottom label
			//?
		}
	}
}

class QuizCard {
	
	private String question;
	private String answer;
	
	public QuizCard(String q, String a) {
		question = q;
		answer = a;
	}
	
	public String getQ() {
		return question;
	}
	
	public String getA() {
		return answer;
	}
}
