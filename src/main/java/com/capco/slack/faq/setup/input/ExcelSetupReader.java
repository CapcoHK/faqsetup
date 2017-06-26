package com.capco.slack.faq.setup.input;

import com.capco.slack.faq.setup.domain.Question;
import com.capco.slack.faq.setup.domain.FaqContainer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class ExcelSetupReader implements SetupReader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelSetupReader.class);
    private static final String DROP_WORDS = "DROP WORDS";

    private final File file;

    public ExcelSetupReader(String fileName) {
        this.file = new File(fileName);
    }


    public FaqContainer read() throws SetupDefinitionException {
        try {
            logger.info("Reading setup file {}", file.getName());
            Workbook workBook = new XSSFWorkbook(file);
            int numberOfSheets = workBook.getNumberOfSheets();
            logger.debug("Found {} categories", numberOfSheets);
            FaqContainer retval = new FaqContainer();
            for(int i = 0; i < numberOfSheets; i++) {
                Sheet workSheet = workBook.getSheetAt(i);
                if (workSheet.getSheetName().equalsIgnoreCase(DROP_WORDS)) {
                    Set<String> dropWords = parseDropWords(workSheet);
                    dropWords.forEach(retval::addDropWord);
                } else {
                    List<Question> questions = getQuestions(workSheet);
                    questions.forEach(retval::addQuestion);
                }
            }
            log(retval);
            return retval;
        } catch (IOException | InvalidFormatException e) {
            logger.error("Unable to parse setup file {}", file.getName(), e);
            throw new SetupDefinitionException(e);
        }
    }

    private void log(FaqContainer retval) {
        logger.info("Successfully read setup");
        logger.info("Total questions : {}", retval.getQuestions().size());
        logger.info("Total drop words : {}", retval.getDropWords().size());
        retval.getQuestions().forEach(q -> logger.info(q.toString()));
    }

    private List<Question> getQuestions(Sheet workSheet) {
        List<Question> retval = new ArrayList<>(workSheet.getLastRowNum());
        for(int i = 1; i < workSheet.getLastRowNum() + 1; i++) { //first row ignored
            retval.add(XLSQuestionBuilder.build(workSheet.getRow(i), workSheet.getSheetName()));
        }
        return retval;
    }

    private Set<String> parseDropWords(Sheet workSheet) {
        Set<String> retval = new HashSet<>();
        for (int i = 1; i < workSheet.getLastRowNum() + 1; i++) {
            Cell cell = workSheet.getRow(i).getCell(0);
            if (cell != null) {
                retval.add(cell.getStringCellValue());
            }
        }
        return retval;
    }
}
