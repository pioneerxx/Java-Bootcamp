package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.PreProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String process(String message) {
        return message.toLowerCase();
    }
}
