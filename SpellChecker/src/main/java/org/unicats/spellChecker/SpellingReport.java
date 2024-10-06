package org.unicats.spellChecker;

import java.util.ArrayList;
import java.util.List;

public class SpellingReport {

    private final List<Word> possibleErrors = new ArrayList<>();

    public void addPossibleError(Word word) {
        possibleErrors.add(word);
    }

    public void displayData() {
        for (Word word : possibleErrors) {
            System.out.println("---------------- Problem on line " + word.getLine().getLineNumber() + ", col " + word.getColumnNumber() + " ----------------------");
            System.out.println(word.getLine().pointProblemLocation(word.getColumnNumber()));
            if (word.getCandidates().isEmpty()) {
                System.out.println(word.getOriginal() + " - no possible matches");
            } else {
                System.out.println(word.getOriginal() + " - possible matches: " + String.join(", ", word.getCandidates()));
            }
        }
    }

    public List<Word> getPossibleErrors() {
        return possibleErrors;
    }

}
