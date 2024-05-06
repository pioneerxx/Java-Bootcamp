package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.Printer;
import edu.school21.SpringApp.Interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;
    private String prefix;
    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String message) {
        renderer.render(prefix + " " + message);
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;

    }
}
