import javax.sound.midi.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class BeatBox {
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;

	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat","Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main (String[] args) {
		BeatBox bbox = new BeatBox();
		bbox.buildGUI();
	}

	public void buildGUI() {
		theFrame = new JFrame("BeatBox!");
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
		grid.setVgap(0);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		bckgrnd.add(BorderLayout.CENTER, mainPanel);

		for (int i = 0; i < 256; i++) {
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

	public void setUpMIDI() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void buildTrackAndStart() {
		int[] trackList = null;

		sequence.deleteTrack(track);
		track = sequence.createTrack();

		for(int i = 0; i < 16; i++) {

			trackList = new int[16];
			int key = instruments[i];

			for(int j = 0; j < 16; j++) {

				JCheckBox jc = checkboxList.get(j + 16*i);
				if(jc.isSelected()) {
					trackList[j] = key;
				} else {
					trackList[j] = 0;
				}
			}//loop

			makeTracks(trackList);
			track.add(makeEvent(176,1,127,0,16));
		}//loop
		
		track.add(makeEvent(192,9,1,0,15));
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}//method

	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}//inner

	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	}//inner

	public class MyTempoUpListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
		}
	}//inner

	public class MyTempoDownListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));
		}
	}//inner

	public void makeTracks(int[] list) {
		for(int i = 0; i < 16; i++) {
			int key = list[i];
			if(key!= 0) {
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
	}//method

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return event;
	}//method
}//outer