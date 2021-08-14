package org.codeformatter.codesources;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileCodeSource implements CodeSource {

    private final Path pathToFile;

    public FileCodeSource(String relativePathToFile) {

        this.pathToFile = Paths.get(relativePathToFile);
    }

    @Override
    public String getCode() {

        try (Stream<String> lines = Files.lines(pathToFile)) {

            return lines.collect(Collectors.joining());

        } catch (IOException e) {
            log.error("Exception while reading the file with code", e);
            throw new RuntimeException(e);
        }

    }
}
