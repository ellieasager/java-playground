package org.unicats.spellChecker;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TextProcessorTest {

    @Test
    public void testDeclarationOfIndependence() {

        String userFile = System.getProperty("user.dir")  + "/src/main/resources/DeclarationOfIndependence.txt";
        try {
            SpellingReport sr = createSpellingReport(userFile);
            List<Word> errorsFound = sr.getPossibleErrors();
            assertEquals(1, errorsFound.size());

            Word error = errorsFound.get(0);
            assertEquals("harrass", error.getOriginal());
            assertEquals(25, error.getLine().getLineNumber());
            assertEquals(82, error.getColumnNumber());
            assertEquals(1, error.getCandidates().size());
            assertEquals("harass", error.getCandidates().get(0));

        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
            fail();
        }
    }

    @Test
    public void testHobbit() {

        String userFile = System.getProperty("user.dir") + "/src/main/resources/Hobbit.txt";
        try {
            SpellingReport sr = createSpellingReport(userFile);
            List<Word> errorsFound = sr.getPossibleErrors();
            assertEquals(12, errorsFound.size());
            String[] expectedMisspellings = {"nassty", "pocketses", "guesseses", "s"};

            errorsFound.forEach((error) -> {
                assert(Arrays.stream(expectedMisspellings).toList().contains(error.getOriginal()));
            });

        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
            fail();
        }
    }

    @Test
    public void testPrideAndPrejudice() {

        String userFile = System.getProperty("user.dir") + "/src/main/resources/PrideAndPrejudice.txt";
        try {
            SpellingReport sr = createSpellingReport(userFile);
            List<Word> errorsFound = sr.getPossibleErrors();
            assert(errorsFound.isEmpty());
        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
            fail();
        }
    }

    @Test
    public void testOldMacDonald() {

        String userFile = System.getProperty("user.dir") + "/src/main/resources/OldMacDonald.txt";
        try {
            SpellingReport sr = createSpellingReport(userFile);
            List<Word> errorsFound = sr.getPossibleErrors();
            assert(errorsFound.isEmpty());
        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
            fail();
        }
    }

    @Test
    public void testGoTheFkToSleep() {

        String userFile = System.getProperty("user.dir") + "/src/main/resources/GoTheFkToSleep.txt";
        try {
            SpellingReport sr = createSpellingReport(userFile);
            List<Word> errorsFound = sr.getPossibleErrors();
            assertEquals(1, errorsFound.size());

            Word error = errorsFound.get(0);
            assertEquals("froggie", error.getOriginal());
            assertEquals(24, error.getLine().getLineNumber());
            assertEquals(5, error.getColumnNumber());
            assertEquals(1, error.getCandidates().size());
            assertEquals("froggier", error.getCandidates().get(0));

        } catch (Exception e) {
            System.out.println("We have a problem: " + e);
        }
    }

    private SpellingReport createSpellingReport(String userFile) throws Exception {
        String fullPath = System.getProperty("user.dir")  + "/src/main/resources/dictionary.txt";
        SpellChecker sp = new SpellChecker(fullPath);
        TextProcessor tp = new TextProcessor(sp, userFile);
        tp.readInput();
        tp.generateSpellingReport();
        return tp.getSpellingReport();
    }
}
