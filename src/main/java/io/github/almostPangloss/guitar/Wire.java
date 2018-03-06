package io.github.almostPangloss.guitar;

import java.util.ArrayList;
import java.util.List;

public class Wire {
    private static final int DEFAULT_FRETS = 15;
    private final Note base;
    public final int frets;

    public Wire(final Note base) {
        this(base, DEFAULT_FRETS);
    }

    public Wire(final Note base, final int frets) {
        this.base = base;
        this.frets = frets;
    }

    public int getFret(final Note note) {
        if(note.getFrequency() < base.getFrequency()) {
            return -1;
        }

        int fret = 0;
        while(fret <= frets) {
            final Note nextNote = base.higherSemitone(fret);
            if(nextNote.getFrequency() == note.getFrequency()) {
                return fret;
            }

            fret++;
        }

        return -1;
    }

    public List<Integer> getFrets(final String noteName) {
        if(noteName == null) {
            throw new IllegalArgumentException("noteName can't be null!");
        }

        final ArrayList<Integer> frets = new ArrayList<>();

        int fret = 0;
        while(fret <= this.frets) {
            final Note nextNote = base.higherSemitone(fret);
            if(noteName.equals(nextNote.getName())) {
                frets.add(fret);
                break;
            }

            fret++;
        }

        if(frets.size() >= 1) {
            final int last = frets.get(frets.size() - 1);
            int next = last + Note.SEMITONES_PER_OCTAVE;
            while(next <= this.frets) {
                frets.add(next);
                next += Note.SEMITONES_PER_OCTAVE;
            }
        }

        frets.trimToSize();
        return frets;
    }

    public Note getNote(final int fret) {
        if(fret < 0 || fret > frets) {
            throw new IllegalArgumentException("Can't use a negative fret or more than the number of frets on the string!");

        }
        if(fret == 0) {
            return base;
        }
        return base.higherSemitone(fret);
    }
}
