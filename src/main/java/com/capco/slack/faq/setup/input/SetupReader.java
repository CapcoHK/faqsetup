package com.capco.slack.faq.setup.input;

import com.capco.slack.faq.setup.domain.FaqContainer;

/**
 * Created by Bhushan on 6/19/2017.
 */
public interface SetupReader {
    FaqContainer read() throws SetupDefinitionException;

    public class SetupDefinitionException extends Exception{
        private final String message;
        public SetupDefinitionException(Exception e){
            super(e);
            this.message = "Exception while reading setup : " + e.getMessage();
        }

        @Override
        public String toString() {
            return "SetupDefinitionException{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
