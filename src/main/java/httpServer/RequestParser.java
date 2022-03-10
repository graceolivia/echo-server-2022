package httpServer;

import httpServer.http.request.HTTPRequest;
import httpServer.http.request.HTTPRequestBuilder;

import java.io.IOException;

import static httpServer.http.Constants.CRLF;

public class RequestParser implements RequestParseable {

    public HTTPRequest storeHttpRequest(ClientReadable bufferedReader)  {

        HTTPRequestBuilder requestBuilder = new HTTPRequestBuilder();
        requestBuilder = readMetadata(bufferedReader, requestBuilder);
        int bodyLength = requestBuilder.getContentLengthInt();
        StringBuilder body = readBody(bufferedReader, bodyLength);
        return returnHttpRequest(body, requestBuilder);

    }

    private HTTPRequestBuilder readMetadata(ClientReadable bufferedReader, HTTPRequestBuilder requestBuilder) {

        String character;
        StringBuilder metadataStringBuilder = new StringBuilder();

        try {
            while ((character = bufferedReader.read()) != null) {

                metadataStringBuilder.append(character);
                if (atEndOfHeaders(metadataStringBuilder)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] metadata = metadataStringBuilder.toString().split(CRLF);

        for (String metadataLine: metadata) {
            requestBuilder = isRequestLine(metadataLine) ? requestBuilder.buildRequestLine(metadataLine) : requestBuilder.buildHeaderLine(metadataLine);
        }
        return requestBuilder;

    }

    private StringBuilder readBody(ClientReadable bufferedReader, int bodyLength) {
        String character;
        StringBuilder body = new StringBuilder();
        if (bodyLength == 0) {
            return body;
        }
        try {
            while ((character = bufferedReader.read()) != null) {
                body.append(character);
                if (body.length() == bodyLength) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private HTTPRequest returnHttpRequest(StringBuilder body, HTTPRequestBuilder requestBuilder) {
        if (body.length() != 0) {
            requestBuilder.setBody(body.toString());
        }
        return requestBuilder.build();
    }

    private boolean isRequestLine(String line) {
        if (line.contains(": ")) {
            return false;
        }
        return true;
    }

    private boolean atEndOfHeaders(StringBuilder processedInput) {
        return processedInput.toString().contains(CRLF + CRLF);
    }

}
