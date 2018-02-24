package io.github.almostPangloss.guitar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

public class Chord implements Set<Note> {
    private static final List<Integer> TRIAD_NOTES = ImmutableList.of(0, 2, 4);

    public static Chord major(final Note note) {
        final Scale scale = new Scale(note);
        return Chord.major(scale);
    }

    public static Chord major(Scale scale) {
        if(scale.isMinor()) {
            scale = scale.getMajor();
        }

        final Chord chord = new Chord(scale.getBase().getName());

        final List<Note> notes = scale.getNotes();
        for(final Integer note : TRIAD_NOTES) {
            chord.add(notes.get(note));
        }

        return chord;
    }

    public static Chord minor(final Note note) {
        final Scale scale = new Scale(note);
        return Chord.minor(scale);
    }

    public static Chord minor(Scale scale) {
        if(scale.isMajor()) {
            scale = scale.getMinor();
        }

        final Chord chord = new Chord(scale.getBase().getName() + "min");

        final List<Note> notes = scale.getNotes();
        for(final Integer note : TRIAD_NOTES) {
            chord.add(notes.get(note));
        }

        return chord;
    }

    private final String name;
    private final Set<Note> notes;

    public Chord(final String name) {
        this(name, new HashSet<>());
    }

    public Chord(final String name, final Collection<Note> notes) {
        this.name = name;
        this.notes = new HashSet<>(notes);
    }

    @Override
    public boolean add(final Note note) {
        return notes.add(note);
    }

    @Override
    public boolean addAll(final Collection<? extends Note> notes) {
        return this.notes.addAll(notes);
    }

    @Override
    public void clear() {
        notes.clear();
    }

    @Override
    public boolean contains(final Object note) {
        return notes.contains(note);
    }

    @Override
    public boolean containsAll(final Collection<?> notes) {
        return this.notes.containsAll(notes);
    }

    public String getName() {
        return name;
    }

    /**
     * @return the notes
     */
    public Set<Note> getNotes() {
        return notes;
    }

    @Override
    public boolean isEmpty() {
        return notes.isEmpty();
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    @Override
    public boolean remove(final Object note) {
        return notes.remove(note);
    }

    @Override
    public boolean removeAll(final Collection<?> notes) {
        return this.notes.removeAll(notes);
    }

    @Override
    public boolean retainAll(final Collection<?> notes) {
        return this.notes.retainAll(notes);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public Object[] toArray() {
        return notes.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] notes) {
        return this.notes.toArray(notes);
    }

    @Override
    public String toString() {
        return name + " -- " + String.join(", ", notes.stream().map(Note::toString).collect(Collectors.toList()));
    }
}
