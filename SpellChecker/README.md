# SpellChecker

This is a quick-and-dirty implementation of a simple spellchecker.
It is not very intelligent, but it's trying :) Please be nice to it.

## How To Run

Check if you have `maven` installed. If you don't - stop here.

1. Download this code and cd to its root.
```bash
mvn clean install
mvn clean package
```
This should generate `spellChecker-1.0-SNAPSHOT.jar` in the `target` directory of the project.  
  

2. Go to the directory where your `dictionary.tst` and `file-to-check.txt` are located.
  

3. In terminal type
```bash
java -jar [path_to_spellChecker-1.0-SNAPSHOT.jar] dictionary.txt file-to-check.txt
```
To keep things simple, I recommend simply copying `spellChecker-1.0-SNAPSHOT.jar` to the same directory where you have
`dictionary.tst` and `file-to-check.txt`. Then you can type this:
```bash
java -jar spellChecker-1.0-SNAPSHOT.jar dictionary.txt file-to-check.txt
```

## Questions? Problems?

Contact esager@gmail.com