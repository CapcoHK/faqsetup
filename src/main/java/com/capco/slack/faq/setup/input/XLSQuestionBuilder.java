package com.capco.slack.faq.setup.input;

import com.capco.slack.faq.setup.domain.Question;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class XLSQuestionBuilder {
    public static int TOPIC_COL_ID = 0;
    public static int SUMMARY_COL_ID = 1;
    public static int QUESTION_COL_ID = 2;
    public static int ANSWER_COL_ID = 3;

    public static Question build(Row row, String category) {
        String topic = getCellValueOrDefault(row, TOPIC_COL_ID, "");
        String summary = getCellValueOrDefault(row, SUMMARY_COL_ID, "");
        String question = getCellValueOrDefault(row, QUESTION_COL_ID, "");
        String answer = getCellValueOrDefault(row, ANSWER_COL_ID, "");
        List<Question.Synonym> synonyms = parseSynonyms(row);
        return new Question(category, topic, summary, question, answer, synonyms);
    }

    private static String getCellValueOrDefault(Row row, int index, String defaultValue) {
        Cell cell = row.getCell(index);
        if (cell != null) {
            return cell.getStringCellValue();
        }
        return defaultValue;
    }

    private static List<Question.Synonym> parseSynonyms(Row row) {
        List<Question.Synonym> retval = new ArrayList<>();
        for (int i = ANSWER_COL_ID + 1; i < row.getLastCellNum(); i++ ) {
            String synonymCsv = getCellValueOrDefault(row, i, null);
            if (synonymCsv != null && !synonymCsv.trim().isEmpty()) {
                retval.add(new Question.Synonym(synonymCsv));
            }
        }
        return retval;
    }
}
