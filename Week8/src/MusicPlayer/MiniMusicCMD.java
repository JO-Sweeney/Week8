package MusicPlayer;

import javax.sound.midi.*;

public class MiniMusicCMD {
	
	
	public static void main(String [] args) {
		MiniMusicCMD mini = new MiniMusicCMD();									//initialises instance of the music player 
		if(args.length < 2) {													//if not enough arguments
			System.out.println("dont forget the instrument and note args");		//feed that back to the user
		}else {
			int instrument  = Integer.parseInt(args [0]);						
			int note = Integer.parseInt(args [1]);
			mini.play(instrument, note);											//give play method the arguments for instrument and note
		}
	}
	
	//sets up midi events to play
	public void play(int instrument, int note) {
		try {
			Sequencer player = MidiSystem.getSequencer();						//Create sequencer (to place sequences in)
			player.open();														
			Sequence seq = new Sequence(Sequence.PPQ,4);							//Create a new sequence
			Track track = seq.createTrack();										//create a new track for that sequence
			
			MidiEvent event = null;												//create an empty midievent
			
			ShortMessage first = new ShortMessage();								//create a message to hold midi information (subclass of midiMessage)
			first.setMessage (192, 1, instrument, 0);							//set midi specific parameters - this is instrument selection)
			MidiEvent changeInstrument = new MidiEvent(first,1);					//change the instrument
			track.add(changeInstrument);											//add the updated instrument to the track at first measure
			
			ShortMessage a = new ShortMessage();									//create the message
			a.setMessage (144, 1, note, 100);									//set midi parameters -  to be the start of a note
			MidiEvent noteOn = new MidiEvent(a,1);								//Create midi event that starts note, at first measure of track
			track.add(noteOn);													
			
			ShortMessage b = new ShortMessage();									//Create a message to hold details of the midi event
			b.setMessage(128, 1, note, 100);										//set the parameters - note end
			MidiEvent noteOff = new MidiEvent(b,16);								//create the midi event, take parametres from message, and end note at measure 16
			track.add(noteOff);													
			player.setSequence(seq);												//finalise sequence
			player.start();														//play sequence 
			
		}catch(Exception ex) {													//if an exception is thrown by above code, 
			ex.printStackTrace();												//print stack details
		}
	}
	
}

