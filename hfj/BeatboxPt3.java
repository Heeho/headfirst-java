import javax.sound.midi.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class BeatboxPt3 {

	static JFrame f = new JFrame("LaLaRectangles!");
	static MyDrawPanel ml;

	public static void main (String[] args) {
		BeatboxPt3 bbox = new BeatboxPt3();
		bbox.go();
	}

	public void setUpGui() {
		ml = new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(30,30,300,300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void go() {
		setUpGui();

		try {
            
			Sequencer player =MidiSystem.getSequencer();
			player.open();

			int [] eventsIWant = {127};
			player.addControllerEventListener(ml , eventsIWant);

			Sequence seq = new Sequence (Sequence.PPQ, 4);
            		Track track = seq.createTrack();

			int r = 0;
			for (int i = 5; i < 65; i+= 4) {
				r = (int)((Math.random()*50)+50);

				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i)); //inserting ControlEvent
      				track.add(makeEvent(128,1,r,100,i+2));
			}

           	 	player.setSequence(seq);
			player.setTempoInBPM(120);
            		player.start();
        	} catch (Exception ex) {
	        	ex.printStackTrace();
	        }
	} //go

	public static MidiEvent makeEvent (int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) { }
		return event;
	}


	class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg;
		public void controlChange(ShortMessage event) { //handling ControlEvent
//			System.out.print("La ");
			msg = true;
			repaint();
		}

		public void paintComponent(Graphics g) {
			if (msg) {
				Graphics2D g2 = (Graphics2D) g;
				int r = (int) (Math.random()*250);
				int gr = (int) (Math.random()*250);
				int b = (int) (Math.random()*250);

				g.setColor(new Color(r,gr,b));

				int ht = (int) ((Math.random()*120)+10);
				int width = (int) ((Math.random()*120)+10);
				int x = (int) ((Math.random()*40)+10);
				int y = (int) ((Math.random()*40)+10);
			
				g.fillRect(x,y,width,ht);
				msg = false;
			}//if
		}//method
	}//inner		
} //outer