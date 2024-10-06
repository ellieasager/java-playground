package org.unicats.spellChecker;

public class Line {
    private String originalContent = "";
    private int lineNumber = 0;

    public Line(String line) {
        originalContent = line;
    }

    public int findWordEnd(int currentPosition) {
        char[] spaceAndPunctuationCharacters = {' ',',','.','?','!',';',':','"','-',')'};
        int closestWordEnd = originalContent.length();
        for (char ch : spaceAndPunctuationCharacters) {
            int chPosition = originalContent.indexOf(String.valueOf(ch), currentPosition);
            if (chPosition == -1) {
                chPosition = originalContent.length();
            }
            if (chPosition < closestWordEnd) {
                closestWordEnd = chPosition;
            }
        }
        return closestWordEnd;
    }

    public String pointProblemLocation(int columnNumber) {
        return originalContent.substring(0, columnNumber-1) + "->" + originalContent.substring(columnNumber-1);
    }

    public String getOriginalContent() {
        return originalContent;
    }
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int l) {
        lineNumber = l;
    }
}