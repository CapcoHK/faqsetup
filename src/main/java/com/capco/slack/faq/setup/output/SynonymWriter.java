package com.capco.slack.faq.setup.output;

import com.capco.slack.faq.setup.domain.Question;
import com.capco.slack.faq.setup.util.FileUtils;
import com.capco.slack.faq.setup.domain.FaqContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class SynonymWriter implements SetupWriter {
    private static final Logger logger = LoggerFactory.getLogger(SynonymWriter.class);

    private final Path outputFilePath;

    public SynonymWriter(String outFilePath, boolean clean) throws WriteException {
        outputFilePath = FileUtils.initFileForWriting(outFilePath, clean);
    }

    @Override
    public void write(FaqContainer container) throws WriteException {
        int count = 0;
        for (Question que : container.getQuestions()) {
            for (Question.Synonym syn : que.getSynonyms()) {
                if (syn.getSynonyms().isEmpty()) {
                    continue;
                }
                count++;
                String synonymCsv = String.join(",", syn.getSynonyms()) + "\n";
                try {
                    Files.write(outputFilePath, synonymCsv.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    logger.error("Unable to write synonyms to file {}", outputFilePath.toAbsolutePath(), e);
                    throw new WriteException(e);
                }
            }
        }
        logger.info("Wrote {} synonym rows", count);
    }
}
