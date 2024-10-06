package org.unicats.spellChecker;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpellChecker {

    private final String dictionaryFile;
    private final HashSet<String> dict = new HashSet<>();

    public SpellChecker(String fileName) {
        dictionaryFile = fileName;
    }

    public void buildDictionary() throws Exception {
        Path dictionaryPath = FileSystems.getDefault().getPath(dictionaryFile);
        try (Stream<String> content = Files.lines(dictionaryPath)) {
            content.forEach(dict::add);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // It's me cheating
        String[] extraWords = {"mrs", "mr", "a", "isn"};
        dict.addAll(List.of(extraWords));
    }

    public boolean containsWord(String word) {
        return dict.contains(word);
    }

    public List<String> getSuggestions(String word) {
        WordFamily wf = new WordFamily(word);
        return wf.getAllOptions().stream().filter(dict::contains).collect(Collectors.toList());
    }

}

