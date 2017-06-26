package com.capco.slack.faq.setup.output;

import com.capco.slack.faq.setup.domain.FaqContainer;

/**
 * Created by Bhushan on 6/19/2017.
 */
public interface SetupWriter {
    void write(FaqContainer container) throws WriteException;

    public class WriteException extends Exception{
        private final String message;

        public WriteException(Exception e) {
            super(e);
            this.message = "Exception while writing setup files : " + e.getMessage();
        }
    }
}
