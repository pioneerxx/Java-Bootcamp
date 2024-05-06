package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String message) {
        return message.toUpperCase();
    }
}
