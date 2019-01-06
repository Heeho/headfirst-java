import javax.sound.midi.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class BeatBox {
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;

	String[] instrumentNames = {“Bass Drum”, “Closed Hi-Hat”, “Open Hi-Hat”,”Acoustic Snare”, “Crash Cymbal”, “Hand Clap”, “High Tom”, “Hi Bongo”, “Maracas”, “Whistle”, “Low Conga”, “Cowbell”, “Vibraslap”, “Low-mid Tom”, “High Agogo”, “Open Hi Conga”};
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main (String[] args) {
		Beatbox bbox = new Beatbox();
		bbox.buildGUI();
	}

	public void buildGUI() {
		theFrame = new JFrame("BeatBox!")
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel bckgrnd = new JPanel(layout);
		bckgrnd.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		checkboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);

		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);

		JButton stop = new JButton("Stop");
		start.addActionListener(new MyStopListener());
		buttonBox.add(stop);

		JButton tempoUp = new JButton("Tempo Up");
		start.addActionListener(new MyTempoUpListener());
		buttonBox.add(tempoUp);

		JButton tempoDown = new JButton("Tempo Down");
		start.addActionListener(new MyTempoDownListener());
		buttonBox.add(tempoDown);

		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < 16; i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}

		bckgrnd.add(BorderLayout.EAST, buttonBox);
		bckgrnd.add(BorderLayout.WEST, nameBox);

		theFrame.getContentPane().add(bckgrnd);

		GridLayout grid = new GridLayout(16,16);
		grid.setVgap(1);
		grid.setVgap(2);
		mainPanel = new JPanel(grid);
		bckgrnd.add(BorderLayout.CENTER, mainPanel);

		for (int i = 0; int < 256; i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}

		setUpMIDI();	

		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}

	public void setUpMIDI {

	}

	public void buildTrackAndStart() {
	}

	public class MyStartListener implements ActionListener {
	}//inner

	public class MyStopListener implements ActionListener {
	}//inner

	public class MyTempoUpListener implements ActionListener {
	}//inner

	public class MyTempoDownListener implements ActionListener {
	}//inner

	public void makeTracks(int[] list) {
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
	}//method
}//outer