package io.github.almostPangloss.guitar;

public class Main {

	public static void main(String[] args) {
		
        Note note = new Note("D", 146.83);
        Chord chord = new Chord(note.getName());
        Guitar guitar = new Guitar();
        System.out.print("Frettings of a chord:" + guitar.getFrettings(chord.major(note)).toString());        
        
	}

}