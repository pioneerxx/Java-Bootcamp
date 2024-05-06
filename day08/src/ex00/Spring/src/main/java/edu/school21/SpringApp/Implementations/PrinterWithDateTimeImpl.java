package edu.school21.SpringApp.Implementations;

import edu.school21.SpringApp.Interfaces.Printer;
import edu.school21.SpringApp.Interfaces.Renderer;
import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;
    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String message) {
        renderer.render(LocalDateTime.now().getYear() + "/"
                + LocalDateTime.now().getMonth() + "/"
                + LocalDateTime.now().getDayOfMonth() + " "
                + LocalDateTime.now().getHour() + ":"
                + LocalDateTime.now().getMinute() + " " + message);
    }
}
