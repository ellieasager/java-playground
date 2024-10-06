package org.unicats.spellChecker;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter the name of the dictionary file and user input file. Exiting.");
            return;
        }
        String dictionaryFile = args[0];
        String userFile = args[1];

        try {
            SpellChecker sp = new SpellChecker(dictionaryFile);
            TextProcessor tp = new TextProcessor(sp, userFile);
            tp.readInput();
            tp.generateSpellingReport();
            tp.printSpellingReport();
        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
        }
    }
}

