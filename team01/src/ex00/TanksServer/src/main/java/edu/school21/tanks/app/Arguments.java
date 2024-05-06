package edu.school21.tanks.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Arguments {
        @Parameter(names = "--port", required = true)
        private int port;
        public int getPort() {
            return port;
        }
}
