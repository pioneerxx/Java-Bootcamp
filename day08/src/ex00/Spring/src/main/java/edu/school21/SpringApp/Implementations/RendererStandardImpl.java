package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.PreProcessor;
import edu.school21.SpringApp.Interfaces.Renderer;

public class RendererStandardImpl implements Renderer {
    private final PreProcessor processor;
    public RendererStandardImpl(PreProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void render(String message) {
        System.out.println(processor.process(message));
    }
}
