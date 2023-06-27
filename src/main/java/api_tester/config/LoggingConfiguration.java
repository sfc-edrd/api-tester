package api_tester.config;

import api_tester.logging.CustomLoggingFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingConfiguration
{
    private static Logger logInstance;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static InputStream inputStream;
    static
    {
        inputStream = LoggingConfiguration
                .class
                .getClassLoader()
                .getResourceAsStream("logging.properties");

        try
        {
            LogManager.getLogManager().readConfiguration(inputStream);
            System.setProperty("java.util.logging.SimpleFormatter.format", LogManager.getLogManager().getProperty("java.util.logging.SimpleFormatter.format"));
            CustomLoggingFormatter customLoggingFormatter = new CustomLoggingFormatter();
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(customLoggingFormatter);
            logInstance = Logger.getLogger(LoggingConfiguration.class.getName());
            logInstance.addHandler(consoleHandler);
//            logInstance.getHandlers();
        }
        catch (IOException ioe)
        {
            logInstance.warning(String.format(ANSI_RED, ioe.getMessage(), ANSI_RESET));
            ioe.printStackTrace();
        }
    }

    public static Logger getLogInstance()
    {
        return (logInstance);
    }
}
