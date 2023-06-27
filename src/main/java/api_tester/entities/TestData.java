package api_tester.entities;

import api_tester.exceptions.TestFileParsingException;
import lombok.Builder;
import org.springframework.lang.NonNull;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

//@Builder
public class TestData
{
    @NonNull
    private String id;
    @NonNull
    private String location;
    @NonNull
    private LinkedHashMap<String, String> headers;
    @NonNull
    private LinkedHashMap<String, Object>  body;
    @NonNull
    private Integer responseStatus;
    @NonNull
    private LinkedHashMap<String, String>  responseAssertions;

    public TestData()
    {
        headers = new LinkedHashMap<>();
        body = new LinkedHashMap<>();
        responseAssertions = new LinkedHashMap<>();
    }

    public void setId(String name)
    {
        this.id = name.trim();
    }

    public void setLocation(String location)
    {
        this.location = location.trim();
    }

    public void setHeaders(List<String> headers)
            throws ArrayIndexOutOfBoundsException
    {
        headers.stream().
                forEach(h -> this.headers.put(
                        h.split(":")[0].trim(),
                        h.split(":")[1].trim()));

    }

    public void setBody(List<String> body)
    {
        body.stream().
                forEach(h -> this.body.put(
                        h.split(":")[0].trim(),
                        h.split(":")[1].trim()));

//        this.body = body.trim();
    }

    public void setResponseStatus(String responseStatus)
    {
        this.responseStatus = Integer.parseInt(responseStatus);
    }

    public void setResponseAssertions(List<String> responseAssertions)
    {
        responseAssertions.stream().
                forEach(h -> this.responseAssertions.put(
                        h.split(":")[0].trim(),
                        h.split(":")[1].trim()));

    }

    public void parse(File testFile)
            throws TestFileParsingException
    {
        ArrayList<String>   requiredFields;
        BufferedReader      buffReader;
        String              line;
        String[]            lineData;
        String              lineKey;
        String              lineValue;
        String[]            valueSplit;
        Method              setMethod;

        try
        {
            this.setId(testFile.getName());
            requiredFields = new ArrayList<>(List.of("location", "headers", "body", "responseStatus", "responseAssertions"));
            buffReader = new BufferedReader(new FileReader(testFile));
            while ((line = buffReader.readLine()) != null)
            {
                lineData = line.split("=");
                lineKey = lineData[0];
                lineValue = lineData[1];
                if (requiredFields.contains(lineKey))
                {
                    requiredFields.remove(lineKey);
                    String finalLineKey = lineKey;
                    setMethod = Arrays.stream(TestData.class.getMethods())
                            .filter(f ->  f.getName().equalsIgnoreCase("set" + finalLineKey))
                            .findFirst()
                            .orElseThrow();
                    if (lineValue.startsWith("[") || lineValue.startsWith("{"))
                    {
                        valueSplit = lineValue
                                .replaceAll("\\[", "")
                                .replaceAll("\\{", "")
                                .replaceAll("\\]", "")
                                .replaceAll("\\}", "")
                                .replaceAll("\"", "")
                                .split(",");
                        setMethod.invoke(this, List.of(valueSplit));
        //                    Map<String, String> valueMap = new LinkedHashMap<>();
        //                    for (String values : valueSplit)
        //                    {
        //                        String valueKey = values.split(":")[0].replace("\"", "").trim();
        //                        String valueData = values.split(":")[1].replace("\"", "").trim();
        //                        valueMap.put(valueKey, valueData);
        //                    }
        //                    dataMapping.put(lineKey, valueMap);
        ////                        continue;
                    }
                    else
                        setMethod.invoke(this, lineValue);
                }
            }

            if (!requiredFields.isEmpty())
                throw new MissingFormatArgumentException("Missing arguments in .test file: " + requiredFields.toString());
        }
        catch (IllegalAccessException | InvocationTargetException | IOException e)
        {
            throw new TestFileParsingException(e.getMessage());
        }
    }

    public String getId()
    {
        return (id);
    }

    public String getLocation()
    {
        return (location);
    }

    public LinkedHashMap<String, String> getHeaders()
    {
        return (headers);
    }

    public LinkedHashMap<String, Object> getBody()
    {
        return (body);
    }

    public Integer getResponseStatus()
    {
        return (responseStatus);
    }

    public LinkedHashMap<String, String> getResponseAssertions()
    {
        return (responseAssertions);
    }
}
