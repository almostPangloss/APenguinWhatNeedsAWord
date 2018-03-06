package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public class Guitar {
    private static final List<Note> DEFAULT_BASE_NOTES =
        ImmutableList.of(new Note("E", 82.41), new Note("A", 110.0), new Note("D", 146.83), new Note("G", 196.0), new Note("B", 246.94), new Note("E", 329.63));
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

    public Set<Fretting> getFrettings(final Chord chord) {
        List<Note> notes = new ArrayList<>(chord);
        notes.sort(Note::compareTo);
        
        /*
         *  
         * Map<Note, Map<Wire, Integer>>
         * 
         */
        
        // This gets each location of those exact frequencies found in the passed-in Chord.
        Map<Note, Map<Wire, Integer>> noteByFretOnEachString = new HashMap<Note, Map<Wire, Integer>>(notes.size());
        for (int i = 0; i < notes.size(); i++ ) {
            Note note = notes.get(i);
            double noteFrequency = note.getFrequency();
            
            for ( Wire wire : strings) {
                for (int f = 0; f < wire.frets; f++) {
                    //Testing for equivalence between Doubles with an AbsVal of Subtraction
                    if ( Math.abs(noteFrequency - wire.getNote(f).getFrequency()) < .1  ) { 
                        Map<Wire, Integer> stringAndFret = new HashMap<>();
                        stringAndFret.put(wire, f);
                        System.out.println("\nWire: " + strings.indexOf(wire) + "\nFret: " + f);
                        noteByFretOnEachString.put(note, stringAndFret);
                    }
                }
            }
        }
        
        // This gets each location of the note names found in the passed-in Chord.
        
        //Almost working. In Console it seems to be, but in Debug it looks like keys are possibly getting overwritten?
        
        Map<Note, Map<Wire, Integer>> allNoteAppearances = new HashMap<Note, Map<Wire, Integer>>(notes.size());
        for (int i = 0; i < notes.size(); i++ ) {
            Note note = notes.get(i);
            String noteName = note.getName();
            
            System.out.println("*********All Instances of " + noteName + " **********");
            for ( Wire wire : strings) {
                for (int f = 0; f < wire.frets; f++) {
                    //Testing for equivalence between Doubles with an AbsVal of Subtraction
                    if ( noteName == wire.getNote(f).getName() ) { 
                        Map<Wire, Integer> stringAndFret = new HashMap<>();
                        stringAndFret.put(wire, f);
                        System.out.println("\nWire: " + strings.indexOf(wire) + "\nFret: " + f);
                        allNoteAppearances.put(note, stringAndFret);
                    }
                }
            }
        }
        
        
//        /*
//        List<List<Integer>> noteFretByString = new ArrayList<>(notes.size());
//        for(int i = 0; i < notes.size(); i++) {
//            noteFretByString.add(new ArrayList<>(strings.size()));
//        }
//        
//        for(int n = 0; n < notes.size(); n++) {
//            Note note = notes.get(n);
//            for(Wire string : strings) {
//                int fret = string.getFret(note);
//                noteFretByString.get(n).add(fret);
//            }
//        }
//        */
        
        Set<Fretting> frettings = new HashSet<>();
        
        /*
         * 
         * Pick a Note from notes, 
         * find its first instance within the first four frets,
         * move up a string and continue
         * 
         */
        
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
         * 
         * Using (E, 82.41) it doesn't properly pick up the B in the chord, even though it should be able to.
         * 
         * Using A, 110 is giving the Amin chord...? It's finding the wrong Third: [(A - 110.0hz), **(C - 130.83hz)**, (E - 164.85hz)]
         */
        
        return frettings;
    }

    public Wire getString(final int index) {
        return strings.get(index);
    }
    
    public static void main (String[] args) {
        Note note = new Note("A", 110.0);
        Chord chord = new Chord(note.getName());
        Guitar guitar = new Guitar();
        System.out.print(guitar.getFrettings(chord.major(note)).toString());
    }
}
