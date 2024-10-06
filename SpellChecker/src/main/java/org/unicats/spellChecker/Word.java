package org.unicats.spellChecker;

import java.util.List;

public class Word {

    private final String original;
    private final Line line;
    private final int columnNumber; // points to the first character in the word
    private final String lowerCase;
    private List<String> candidates;
    public Word(String originalIn, Line lineIn, int columnNumberIn) {
        original = originalIn;
        line = lineIn;
        columnNumber = columnNumberIn;
        lowerCase = originalIn.toLowerCase();
    }

    public boolean isProperName() {
        return Character.isUpperCase(original.charAt(0));
    }

    public String getOriginal() {
        return original;
    }

    public String getLowerCase() {
        return lowerCase;
    }

    public Line getLine() {
        return line;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }

}
