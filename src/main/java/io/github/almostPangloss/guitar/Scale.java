package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;

public class Scale {
    private static final Set<Integer> MAJOR_HALF_STEPS = ImmutableSet.of(2, 6);
    private static final Set<Integer> MINOR_HALF_STEPS = ImmutableSet.of(1, 4);
    private static final int SCALE_SIZE = 7;
    private final Note base;
    private final boolean minor;

    private final Supplier<List<Note>> notes = Suppliers.memoize(this::generateNotes);

    public Scale(final Note base) {
        this(base, false);
    }

    public Scale(final Note base, final boolean minor) {
        this.base = base;
        this.minor = minor;
    }

    private List<Note> generateNotes() {
        final List<Note> notes = new ArrayList<>(SCALE_SIZE);
        notes.add(base);

        final Set<Integer> halfSteps = minor ? MINOR_HALF_STEPS : MAJOR_HALF_STEPS;

        Note note = base;
        for(int i = 1; i < SCALE_SIZE; i++) {
            if(halfSteps.contains(i)) {
                note = note.higherSemitone(1);
            } else {
                note = note.higherSemitone(2);
            }

            notes.add(note);
        }

        return notes;
    }

    public Note getBase() {
        return base;
    }

    public Scale getMajor() {
        if(!minor) {
            throw new IllegalStateException("Scale is already major!");
        }
        return new Scale(base, false);
    }

    public Scale getMinor() {
        if(minor) {
            throw new IllegalStateException("Scale is already minor!");
        }
        return new Scale(base, true);
    }

    public List<Note> getNotes() {
        return notes.get();
    }

    public boolean isMajor() {
        return !minor;
    }

    public boolean isMinor() {
        return minor;
    }
}
