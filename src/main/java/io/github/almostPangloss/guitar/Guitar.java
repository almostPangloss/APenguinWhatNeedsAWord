package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public class Guitar {
    private static final List<Note> DEFAULT_BASE_NOTES =
        ImmutableList.of(new Note("E", 82.41), new Note("A", 110.0), new Note("D", 146.83), 
        				new Note("G", 196.0), new Note("B", 246.94), new Note("E", 329.63));
    private static final int MAX_FRETTING_SPAN = 3;
    private List<Wire> strings;

    public Guitar() {
        this(DEFAULT_BASE_NOTES);
    }

    public Guitar(final List<Note> baseNotes) {
        strings = new ArrayList<>(baseNotes.size());

        for(final Note base : baseNotes) {
            strings.add(new Wire(base));
        }
        strings = Collections.unmodifiableList(strings);
    }

    public Set<Set<Map<Wire, Integer>>> getFrettings(final Chord chord) {
        List<Note> notes = new ArrayList<>(chord);
        //notes.sort(Note::compareTo);
        
        /*
         *  
         * Map<Note, Map<Wire, Integer>>
         * 
         */
        
        // This gets each location of those exact frequencies found in the passed-in Chord.
        Map<Note, Map<Wire, Integer>> chordElementLocations = noteByFretOnEachString(chord);
        
        
        // RESOLVED: Almost working. In Console it seems to be, but in Debug it looks like keys are possibly getting overwritten?
        // Maybe include getting the frettings going in here? an if/then grabbing the first stringAndFret combo with a Fret value below 4, then build from there?
        
        // This gets each location of the note names found in the passed-in Chord.
        Map<String, Map<Wire, Integer>> chordNoteNameLocations = allNoteAppearances(chord);
        
        
        
        
        /*
         * 
         * Pick a Note from notes, 
         * find its first instance within the first four frets,
         * move up a string and continue
         * 
         */
        
        
        // chord.getName() will give the note name of the lowest note, then just find that
        System.out.println("\n\nHopefully the set of keys?");
        System.out.println(chordNoteNameLocations.keySet().toString());
        
			        // Hopefully this will loop through the list of note names starting from E looking to see 
			        // if the current note name is in the key set of the chord. When it is, that's the name of the chord.
			        String firstNoteOfFretting = "";
			        int index = 0;
			        
			        while (firstNoteOfFretting == "") {
			        	if (chordNoteNameLocations.keySet().contains(Note.NAMES.get(index))) {
			        		firstNoteOfFretting = Note.NAMES.get(index);
			        	}
			        	index++;
			        }
			        
			        System.out.println("First note of fretting: " + firstNoteOfFretting);
			        
			        System.out.println("Chord name: " + chord.getName());
			        
			        // OK, glad I included the chord.getname(), because the whole loop before that doesn't do what I had anticipated. 
			        // What's the trouble with my thought, there? Why does it not do the thing I expect/want? I mean, I see why, actually.
			        // The question is why I thought it would before I tested it?
        
        // fretting.put(key, value)
			        
		int wire = 0;
		int fret = 0;
		String noteName = chord.getName();
		while (wire <= 5){
			if (fret < 4) {
				
			}
		}
        
		// Initializing fret position bounds
		int lowerBound = 0;
		int upperBound = 3;
		
		Set<Set<Map<Wire, Integer>>> frettings = new HashSet<>();
		Collection<Map<Wire, Integer>> noteNameCollection = chordNoteNameLocations.values();
		
		for (Map<Wire, Integer> m : noteNameCollection) {
			Set<Map<Wire, Integer>> fretting = new HashSet<>();
			while (wire <= 5) {
				for (Integer f : m.values()) {
					if ( f >= lowerBound && f <= upperBound) {						
						fretting.add(m);
					}
				}
				wire++;
			}
			frettings.add(fretting);
		}
				// Hmm, this seems to break things:
				// System.out.println("Frettings size: " + frettings.size());
		
        /*
         * OK, it looks, through debugging, like generating the elements of the chord isn't working, exactly. 
         * Or it is, but in a useless way.
         * It finds freq's that are mostly meaningless for the second and third elements, which then means the 
         * fret found is also mostly useless.
         * But I've been reading over this code for probably two hours, now, so I could easily be confused.
         * 
         * For the fretting, all we care about is finding the first/lowest note, and after that, each string
         * needs to be used only once and must be fretted such that it generates one of the three note names 
         * in the chord.
         * 
         * Why, though, does noteFretByString have three elements - the three notes of a chord - with each, 
         * then, having a place for every string? 
         */
        
        return frettings;
    }
    
    public Map<Note, Map<Wire, Integer>> noteByFretOnEachString (Chord chord) {
    	
        List<Note> notes = new ArrayList<>(chord);
        notes.sort(Note::compareTo);
        
        // This gets each location of those exact frequencies found in the passed-in Chord.
        Map<Note, Map<Wire, Integer>> noteByFretOnEachString = new HashMap<Note, Map<Wire, Integer>>(notes.size());
        for (int i = 0; i < notes.size(); i++ ) {
            Note note = notes.get(i);
            double noteFrequency = note.getFrequency();
            
            for ( Wire wire : strings) {
                for (int f = 0; f < wire.frets; f++) {
                    //Testing for equivalence between Doubles with an AbsVal of Subtraction
                	Map<Wire, Integer> stringAndFret = new HashMap<>();
                    if ( Math.abs(noteFrequency - wire.getNote(f).getFrequency()) < .1  ) { 
                        stringAndFret.put(wire, f);
                        System.out.println("\nWire: " + strings.indexOf(wire) + "\nFret: " + f);
                        noteByFretOnEachString.put(note, stringAndFret);
                    }
                }
            }
        }
        
		return noteByFretOnEachString;
    }
    
    public Map<String, Map<Wire, Integer>> allNoteAppearances (Chord chord) {
    	
        List<String> notes = new ArrayList<>();
        for (Note note : chord) {
        	notes.add(note.getName());
        }
        
    	Map<String, Map<Wire, Integer>> allNoteAppearances = new HashMap<String, Map<Wire, Integer>>(notes.size());
    	for (int i = 0; i < notes.size(); i++ ) {
    		String note = notes.get(i);
    		
    		System.out.println("\n\n*********All Instances of " + note + " **********");
    		for ( Wire wire : strings) {
    			for (int f = 0; f < wire.frets; f++) {    	
    				Map<Wire, Integer> stringAndFret = new HashMap<>();
    				//Testing for equivalence between Doubles with an AbsVal of Subtraction
    				if ( note == wire.getNote(f).getName() ) { 
    					stringAndFret.put(wire, f);
    					System.out.println("\nWire: " + strings.indexOf(wire) + "\nFret: " + f);
    					allNoteAppearances.put(note, stringAndFret);
    				}
    			}
    		}
    	}
    	
		return allNoteAppearances;
    }


    public Wire getString(final int index) {
        return strings.get(index);
    }
    
}
