package org.unicats.spellChecker;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextProcessor {

    private final SpellChecker spellChecker;
    private final String userFile;

    private final SpellingReport spellingReport = new SpellingReport();
    private final ArrayList<Line> lines = new ArrayList<>();
    private final String wordWithPunctuationRegex = "([A-Za-z|']+)[\\\\-|\"|'|\\p{Punct}]*";
    private final Pattern wordWithPunctuation = Pattern.compile(wordWithPunctuationRegex);

    public TextProcessor(SpellChecker sp, String uf) {
        spellChecker = sp;
        userFile = uf;
    }

    public void readInput() throws Exception {
        spellChecker.buildDictionary();
        Path userFilePath = FileSystems.getDefault().getPath(userFile);
        try (Stream<String> content = Files.lines(userFilePath)) {
            content.forEach( (line) -> {
                lines.add(new Line(line));
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateSpellingReport() {
        int l = 0;
        for (Line line : lines) {
            l += 1;
            if (!line.getOriginalContent().isBlank()) {
                line.setLineNumber(l);
                processLine(line);
            }
        }
    }

    private void processLine(Line line) {
        int currentPosition = 0;
        while (currentPosition < line.getOriginalContent().length()) {
            char ch = line.getOriginalContent().charAt(currentPosition);
            if (Character.isWhitespace(ch) || isPunctuation(ch)) {
                currentPosition +=1;
            } else {
                if (Character.isDigit(ch) || ch == '+' | ch == '-') {
                    currentPosition = checkIfNumber(line, currentPosition);
                } else {
                    currentPosition = checkIfWord(line, currentPosition);
                }
            }
        }
    }

    private int checkIfNumber(Line line, int currentPosition) {
        int nextSpace = line.getOriginalContent().indexOf(" ", currentPosition);
        if (nextSpace == -1) {
            nextSpace = line.getOriginalContent().length();
        }
        String possibleNumber = line.getOriginalContent().substring(currentPosition, nextSpace);
        if (isPunctuation(possibleNumber.charAt(possibleNumber.length()-1))) {
            possibleNumber = possibleNumber.substring(0, possibleNumber.length()-1);
        }
        String regexForNumber = "^(0|[1-9]\\d*)(\\.\\d+)?(\\d[eE][+\\-]?\\d+)?$";
        if (!possibleNumber.matches(regexForNumber)) {
            Word w = new Word(possibleNumber, line, currentPosition+1);
            spellingReport.addPossibleError(w);
        }
        return nextSpace;
    }

    private int checkIfWord(Line line, int currentPosition) {
        int nextPosition = line.findWordEnd(currentPosition);
        String possibleWordPlus = line.getOriginalContent().substring(currentPosition, nextPosition);
        Word notWord = new Word(possibleWordPlus, line, currentPosition+1);
        // if it doesn't start with a letter - it's not a word
        if (!possibleWordPlus.substring(0,1).matches("[A-Za-z]")) {
            spellingReport.addPossibleError(notWord);
            return nextPosition;
        }
        // check for punctuation:
        // we already know that it starts with a letter - check for proper format (alphanum + possible punctuation)
        if (!possibleWordPlus.matches(wordWithPunctuationRegex)) {
            spellingReport.addPossibleError(notWord);
            return nextPosition;
        }

        // looks like we have consecutive characters at the beginning - extract them:
        Matcher m = wordWithPunctuation.matcher(possibleWordPlus);
        if (!m.find()) {
            spellingReport.addPossibleError(notWord);
            return nextPosition;
        }
        String possibleWord = m.group(1);
        possibleWord = cleanupWord(possibleWord);

        Word w = new Word(possibleWord, line, currentPosition+1);
        if (!w.isProperName()) {
            processWord(w);
        }
        return nextPosition;
    }

    private String cleanupWord(String possibleWord) {
        // remove possible "'" from the end
        if (possibleWord.endsWith("'")) {
            return possibleWord.substring(0, possibleWord.length()-1);
        }
        // remove possible possessive "'s" from the end
        if (possibleWord.endsWith("'s")) {
            return possibleWord.substring(0, possibleWord.length()-2);
        }
        // remove possible possessive "'t" from the end
        if (possibleWord.endsWith("'t")) {
            return possibleWord.substring(0, possibleWord.length()-2);
        }
        // remove possible possessive "'re" from the end
        if (possibleWord.endsWith("'re")) {
            return possibleWord.substring(0, possibleWord.length()-3);
        }
        // remove possible possessive "'ll" from the end
        if (possibleWord.endsWith("'ll")) {
            return possibleWord.substring(0, possibleWord.length()-3);
        }
        return possibleWord;
    }

    private void processWord(Word word) {
        String wordToLookup = word.getLowerCase();
        if (!spellChecker.containsWord(wordToLookup)) {
            List<String> suggestions = spellChecker.getSuggestions(wordToLookup);
            word.setCandidates(suggestions);
            spellingReport.addPossibleError(word);
        }
    }

    private boolean isPunctuation(char ch) {
        return Pattern.matches("\\\\-|\"|'|\\p{Punct}", String.valueOf(ch));
    }

    public void printSpellingReport() {
        spellingReport.displayData();
    }

    public SpellingReport getSpellingReport() {
        return spellingReport;
    }
}
