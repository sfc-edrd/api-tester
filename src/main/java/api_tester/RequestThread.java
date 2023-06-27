//package api_tester;
//
//import api_tester.config.LoggingConfiguration;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.ToNumberPolicy;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//public class RequestThread extends Thread
//{
//    private Logger                          log;
//    private LinkedHashMap<String, Object>   requestData;
//
//    public RequestThread(LinkedHashMap requestData)
//    {
//        this.requestData = requestData;
//        log = LoggingConfiguration.getLogInstance();
//    }
//
//    @Override
//    public void run()
//    {
//        URL url;
//        String                          output;
//        HttpURLConnection               connection;
//        OutputStream                    os;
//        OutputStreamWriter              osw;
//        LinkedHashMap<String, String>   headers;
//        InputStreamReader               in;
//        BufferedReader                  br;
//        long                            initialTimestamp;
//        LinkedHashMap<String, String>   assertions;
//        Gson                            gson;
//        List<Boolean>                   collect;
//
//        gson = new GsonBuilder()
//                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .create();
//        initialTimestamp = System.currentTimeMillis();
//        try
//        {
//            // set url
//            url = new URL(requestData.get("location").toString());
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            // set headers
//            headers = (LinkedHashMap<String, String>) requestData.get("headers");
//            headers.entrySet().stream().forEach(e -> connection.setRequestProperty(e.getKey(), e.getValue()));
//            // set body
//            connection.setDoOutput(true);
//            os = connection.getOutputStream();
//            osw = new OutputStreamWriter(os, "UTF-8");
//            osw.write(requestData.get("body").toString());
//            osw.flush();
//            osw.close();
//            os.close();
//
//            connection.connect();
//            // check return
//            if (connection.getResponseCode() == Integer.parseInt(requestData.get("responseStatus").toString()))
//            {
//                in = new InputStreamReader(connection.getInputStream());
//                br = new BufferedReader(in);
//                while ((output = br.readLine()) != null)
//                {
//                    assertions = (LinkedHashMap<String, String>) requestData.get("responseAssertions");
//                    LinkedHashMap mappedResponse = gson.fromJson(output, LinkedHashMap.class);
//                    collect = assertions.entrySet().stream().map(e -> mappedResponse.get(e.getKey()).toString().equals(e.getValue())).collect(Collectors.toList());
//                    log.info("--------------------------------------------------------");
//                    if (collect.contains(false))
//                    {
//                        log.info(requestData.get("location") + " => NOK [" + (System.currentTimeMillis() - initialTimestamp) + "ms]\nExpected: " + assertions + "\nReceived: " + mappedResponse.toString());
//                    } else {
//                        log.info(requestData.get("location") + " => OK [" + (System.currentTimeMillis() - initialTimestamp) + "ms]");
//                    }
//                }
//                connection.disconnect();
//            } else
//                log.info(requestData.get("location") + " => Response code: " + connection.getResponseCode());
//
//        }
//        catch (IOException ioe)
//        {
//            throw new RuntimeException(ioe.getMessage());
//        }
//    }
//}
