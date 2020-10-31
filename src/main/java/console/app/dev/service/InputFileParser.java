package console.app.dev.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.io.FilenameUtils;

public class InputFileParser {
    public List<File> parseInput(String filename) throws FileNotFoundException {
        List<File> result = new CopyOnWriteArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(FilenameUtils.separatorsToSystem(filename)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(new File(FilenameUtils.separatorsToSystem(line)));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
