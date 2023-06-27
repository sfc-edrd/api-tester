package api_tester.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomLoggingFormatter extends Formatter
{
//    public final String ANSI_RESET = "\u001B[0m";
//    public final String ANSI_RED = "\u001B[31m";
//    public final String ANSI_YELLOW = "\u001B[33m";
//    public final String ANSI_CYAN = "\u001B[36m";

    @Override
    public String getHead(Handler h)
    {
        return ("");
    }

//    protected CustomLoggingFormatter() {
//        super();
//    }

    @Override
    public String formatMessage(LogRecord record)
    {
        return record.getMessage();
//        return super.formatMessage(record);
    }

    @Override
    public String format(LogRecord record)
    {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(
                record.getInstant(), ZoneId.systemDefault());
//        String source;
//        if (record.getSourceClassName() != null) {
//            source = record.getSourceClassName();
//            if (record.getSourceMethodName() != null) {
//                source += " " + record.getSourceMethodName();
//            }
//        } else {
//            source = record.getLoggerName();
//        }
//        String message = formatMessage(record);
//        String throwable = "";
//        if (record.getThrown() != null) {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            pw.println();
//            record.getThrown().printStackTrace(pw);
//            pw.close();
//            throwable = sw.toString();
//        }
        return String.format("\n",
                zdt //,
//                "",
//                record.getLoggerName(),
//                message,
//                throwable
        );
    }
}
