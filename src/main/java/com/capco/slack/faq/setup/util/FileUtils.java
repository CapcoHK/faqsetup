package com.capco.slack.faq.setup.util;

import com.capco.slack.faq.setup.output.SetupWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Created by Bhushan on 6/20/2017.
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static Path initFileForWriting(String outFilePath, boolean clean) throws SetupWriter.WriteException {
        Path outputFilePath;
        logger.info("Opening file at path {}", outFilePath);
        File file = new File(outFilePath);
        outputFilePath = Paths.get(file.toURI());
        if (!file.exists()) {
            try {
                logger.info("Creating new file...");
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Unable to create new synonyms file {}", file.getAbsolutePath(), e);
                throw new SetupWriter.WriteException(e);
            }
        } else if (clean){
            try {
                logger.info("Truncating existing file...");
                Files.write(outputFilePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                logger.error("Unable to truncate existing synonyms file {}", file.getAbsolutePath(), e);
                throw new SetupWriter.WriteException(e);
            }
        }
        return outputFilePath;
    }

    public static void archiveFile(String filePath) throws IOException {
        logger.info("Archiving file {}", filePath);
        Path newPath = Files.move(Paths.get(filePath), Paths.get(filePath + "_" + System.currentTimeMillis()), StandardCopyOption.REPLACE_EXISTING);
        logger.info("Successfully archived file as {} " + newPath.toAbsolutePath());
    }
}
