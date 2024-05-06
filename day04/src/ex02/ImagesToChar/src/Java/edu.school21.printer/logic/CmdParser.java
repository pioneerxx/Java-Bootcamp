package Java.edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CmdParser {
    @Parameter(names = "--white", required = true)
    private String whiteColor;
    @Parameter(names = "--black", required = true)
    private String blackColor;
    
    
    public String getColorForWhite() {
        return whiteColor;
    }

    public String getColorForBlack() {
        return blackColor;
    }
}
 