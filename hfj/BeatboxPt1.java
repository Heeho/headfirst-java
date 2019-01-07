import javax.sound.midi.*;

public class BeatboxPt1 {

	public static void main (String[] args) {

		try {
            
			Sequencer player =MidiSystem.getSequencer();
			player.open();

			Sequence seq = new Sequence (Sequence.PPQ, 4);
            		Track track = seq.createTrack();

			for (int i = 30; i < 120; i+= 4) {
				track.add(makeEvent(144,1,i,100,i));
      				track.add(makeEvent(128,1,i,100,i+2));
			}

           	 	player.setSequence(seq);
			player.setTempoInBPM(220);
            		player.start();

        	} catch (Exception ex) {
	        	ex.printStackTrace();
	        }
	} //main

	public static MidiEvent makeEvent (int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) { }
		return event;
	}
} //class