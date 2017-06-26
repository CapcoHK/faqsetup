package com.capco.slack.faq.setup.output;

import com.capco.slack.faq.setup.domain.FaqContainer;
import com.capco.slack.faq.setup.input.ExcelSetupReader;
import com.capco.slack.faq.setup.input.SetupReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class TextFileSetupExporter implements SetupWriter {
    private static final Logger logger = LoggerFactory.getLogger(TextFileSetupExporter.class);

    private SetupWriter[] writers;

    public TextFileSetupExporter(SetupWriter... writers) {
        this.writers = writers;
    }

    public void write(FaqContainer container) throws WriteException {
        for (SetupWriter writer : writers) {
            logger.info("Starting writer {}", writer.getClass().getName());
            writer.write(container);
        }
    }
    public static void main(String[] args) throws SetupReader.SetupDefinitionException, WriteException {
        if (args.length < 4) {
            throw new IllegalArgumentException("This program expects 4 arguments in this order : 1) excel input file 2) Path of Synonym output file 3) Path of drop words output file 4) Path of FAQ output file");
        }

        String excelSetupFile = args[0]; //"C:\\Users\\Capco\\Desktop\\FAQ_bot.xlsx";
        String synonymOutputFile = args[1]; //"C:\\Users\\Capco\\Desktop\\synonyms.txt";
        String dropWordsOutputFile = args[2]; //"C:\\Users\\Capco\\Desktop\\dropwords.txt";
        String faqOutputFile = args[3]; //"C:\\Users\\Capco\\Desktop\\faq.txt";

        FaqContainer setup = new ExcelSetupReader(excelSetupFile).read();

        SynonymWriter synonymWriter = new SynonymWriter(synonymOutputFile, true);
        DropWordsWriter dropWordsWriter = new DropWordsWriter(dropWordsOutputFile, true);
        FaqWriter faqWriter = new FaqWriter(faqOutputFile, true);

        TextFileSetupExporter textFileSetupExporter = new TextFileSetupExporter(synonymWriter, dropWordsWriter, faqWriter);
        textFileSetupExporter.write(setup);
    }
}

