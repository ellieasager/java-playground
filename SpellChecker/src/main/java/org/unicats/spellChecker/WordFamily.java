package org.unicats.spellChecker;

import java.util.HashSet;
import java.util.Vector;

public class WordFamily {

    private final Vector<String> charMissing = new Vector<>();
    private final Vector<String> charReplaced = new Vector<>();
    private final Vector<String> charInserted = new Vector<>();
    private final HashSet<String> charTransposed = new HashSet<>();

    public WordFamily(String original) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < original.length(); i++) {
            String missingChar = original.substring(0,i) + original.substring(i+1);
            charMissing.add(missingChar);

            for (int j = 0; j < letters.length(); j++) {
                char c = letters.charAt(j);
                String replacedChar = original.substring(0,i) + c + original.substring(i);
                charReplaced.add(replacedChar);
            }
        }
        for (int i = 0; i < original.length()+1; i++) {
            for (int j = 0; j < letters.length(); j++) {
                char c = letters.charAt(j);
                String insertedChar = original.substring(0,i) + c + original.substring(i);
                charInserted.add(insertedChar);
            }
        }
        for (int i = 0; i < original.length()-1; i++) {
            String transposedChar = original.substring(0,i) + original.charAt(i+1) + original.charAt(i) + original.substring(i+2);
            charTransposed.add(transposedChar);
        }
    }

    public HashSet<String> getAllOptions() {
        HashSet<String> result = new HashSet<>();
        result.addAll(charMissing);
        result.addAll(charReplaced);
        result.addAll(charInserted);
        result.addAll(charTransposed);
        return result;
    }
}
