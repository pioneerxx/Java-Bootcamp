package edu.school21.SpringApp.Program;

import edu.school21.SpringApp.Implementations.PrinterWithPrefixImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        PrinterWithPrefixImpl printer = context.getBean("printerPrefixStandardToLower", PrinterWithPrefixImpl.class);
        printer.setPrefix("BEBS");
        printer.print("Hello!");
    }
}