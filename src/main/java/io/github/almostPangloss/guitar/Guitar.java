package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        
        List<List<Integer>> noteFretByString = new ArrayList<>(notes.size());
        for(int i = 0; i < notes.size(); i++) {
            noteFretByString.add(new ArrayList<>(strings.size()));
        }
        
        for(int n = 0; n < notes.size(); n++) {
            Note note = notes.get(n);
            for(Wire string : strings) {
                int fret = string.getFret(note);
                noteFretByString.get(n).add(fret);
            }
        }
        
        Set<Fretting> frettings = new HashSet<>();
        
        // TODO: stuff
        
        return frettings;
    }

    public Wire getString(final int index) {
        return strings.get(index);
    }
}
