package api_tester.api;

import api_tester.exceptions.TestFileParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ErrorHandlingResource
{
    @ExceptionHandler(value = {TestFileParsingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String fileParsingException(TestFileParsingException e)
    {
        log.error("File parsing error: " + e.getMessage());

        return (e.getMessage());
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noSuchElementException(NoSuchElementException e)
    {
        log.error("Element not found: " + e.getMessage());

        return (e.getMessage());
    }
}
