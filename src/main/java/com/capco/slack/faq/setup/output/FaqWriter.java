package com.capco.slack.faq.setup.output;

import com.capco.slack.faq.setup.domain.FaqContainer;
import com.capco.slack.faq.setup.domain.Question;
import com.capco.slack.faq.setup.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class FaqWriter implements SetupWriter {

    private static final Logger logger = LoggerFactory.getLogger(FaqWriter.class);
    private static final String NO = "%d";
    private static final String QUE = "%s";
    private static final String DATE = "%s";
    private static final String ANS = "%s";
    private static final String CATEGORY = "%s";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String ROW_FORMAT = NO + "\t" + QUE + "\t" + DATE + "\t" + CATEGORY + "\t" + ANS + "\n";
    private final Path outputFilePath;

    public FaqWriter(String outFilePath, boolean clean) throws WriteException {
        outputFilePath = FileUtils.initFileForWriting(outFilePath, clean);
    }

    @Override
    public void write(FaqContainer container) throws WriteException {
        int count = 0;
        String dateStr = sdf.format(new Date());
        for (Question que : container.getQuestions()) {
            count++;
            String queRow = String.format(ROW_FORMAT, count, que.getQuestion(), dateStr, "-", que.getAnswer());
            try {
                Files.write(outputFilePath, queRow.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Unable to write drop words to file {}", outputFilePath.toAbsolutePath(), e);
                throw new WriteException(e);
            }
        }
        logger.info("Wrote {} questions", count);
    }
}
