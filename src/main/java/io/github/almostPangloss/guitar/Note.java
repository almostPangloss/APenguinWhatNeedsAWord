package io.github.almostPangloss.guitar;

import java.text.DecimalFormat;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Note implements Comparable<Note> {
    private static final DecimalFormat FREQUENCY_FORMAT = new DecimalFormat(".##hz");
    public static List<String> NAMES = ImmutableList.of("E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#");
    private static final double SEMITONE_FACTOR = 1.0595;
    public static int SEMITONES_PER_OCTAVE = NAMES.size();
    private final double frequency;
    private final String name;

    public Note(final String name, final double frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    /**
     * @return the frequency
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public Note higherSemitone(final int number) {
        final double frequency = this.frequency * Math.pow(SEMITONE_FACTOR, number);
        final int nameIndex = (NAMES.indexOf(name) + number) % NAMES.size();
        return new Note(NAMES.get(nameIndex), frequency);
    }
    
    public Note lowerSemitone(final int number) {
        final double inverseFactor = 1 / SEMITONE_FACTOR;
        final double frequency = this.frequency * Math.pow(inverseFactor, number);
        final int nameIndex = (NAMES.indexOf(name) - number) % NAMES.size();
        return new Note(NAMES.get(nameIndex), frequency);
    }

    @Override
    public String toString() {
        return "(" + name + " - " + FREQUENCY_FORMAT.format(frequency) + ")";
    }

    @Override
    public int compareTo(Note other) {
        return Double.compare(frequency, other.frequency);
    }
}
