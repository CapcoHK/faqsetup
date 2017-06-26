package com.capco.slack.faq.setup.output;

import com.capco.slack.faq.setup.domain.FaqContainer;
import com.capco.slack.faq.setup.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class DropWordsWriter implements SetupWriter {
    private static final Logger logger = LoggerFactory.getLogger(DropWordsWriter.class);

    private final Path outputFilePath;

    public DropWordsWriter(String outFilePath, boolean clean) throws WriteException {
        outputFilePath = FileUtils.initFileForWriting(outFilePath, clean);
    }

    @Override
    public void write(FaqContainer container) throws WriteException {
        int count = 0;
        for (String dropWord : container.getDropWords()) {
            count++;
            String dropWordRow = dropWord + "\n";
            try {
                Files.write(outputFilePath, dropWordRow.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Unable to write drop words to file {}", outputFilePath.toAbsolutePath(), e);
                throw new WriteException(e);
            }
        }
        logger.info("Wrote {} drop words", count);
    }
}
