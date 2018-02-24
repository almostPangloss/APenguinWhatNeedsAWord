package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public class Guitar {
    private static final List<Note> DEFAULT_BASE_NOTES =
        ImmutableList.of(new Note("E", 82.41), new Note("A", 110.0), new Note("D", 146.83), new Note("G", 196.0), new Note("B", 246.94), new Note("E", 329.63));
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
        // TODO: The rest of the stuff
    }

    public Wire getString(final int index) {
        return strings.get(index);
    }
}
