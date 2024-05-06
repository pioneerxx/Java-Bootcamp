package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.PreProcessor;
import edu.school21.SpringApp.Interfaces.Renderer;

public class RendererErrImpl implements Renderer {

    private final PreProcessor processor;
    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void render(String message) {
        System.err.println(processor.process(message));
    }
}
