import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
	public void callGui() {
	//make:
	//1. A frame with two JTextArea
	//for q/a input
	//2. JButton "Next Card"
	//adds a card to card list
	//3.File>Save menu
	//to save the cards in list to file
	//4.File>New
	//to make a new set
	}
	//inner listener classes
	class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//add current card to a list
			//make next card, clear text fields
		}
	}
	
	class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//make new card set
		}
	}
	
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//save card set to a file
		}
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
