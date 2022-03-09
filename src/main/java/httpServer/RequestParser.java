package httpServer;

import httpServer.http.StatusCodes;
import httpServer.http.request.HTTPRequest;
import httpServer.http.request.HTTPRequestBuilder;
import httpServer.http.response.HTTPResponse;
import httpServer.outputManagement.ClientWriteable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import static httpServer.http.Constants.CRLF;

public class RequestParser {

    public boolean isRequestLine(String line) {
        if (line.contains(": ")) {
            return false;
        }
        return true;
    }

    public boolean atEndOfHeaders(StringBuilder processedInput) {
        return processedInput.toString().contains(CRLF + CRLF);
    }

    public HTTPRequest storeHttpRequestInHttpRequestObject(ClientReadable bufferedReader)  {

        HTTPRequestBuilder requestBuilder = new HTTPRequestBuilder();
        requestBuilder = readStatusCodeAndHeaders(bufferedReader, requestBuilder);
        int bodyLength = requestBuilder.getContentLengthInt();
        StringBuilder body = readBody(bufferedReader, bodyLength);
        return returnHttpRequest(body, requestBuilder);

    }

    private HTTPRequestBuilder readStatusCodeAndHeaders(ClientReadable bufferedReader, HTTPRequestBuilder requestBuilder) {

        String character;
        StringBuilder statusCodeAndHeaders = new StringBuilder();

        try {
            while ((character = bufferedReader.read()) != null) {

                statusCodeAndHeaders.append(character);
                if (atEndOfHeaders(statusCodeAndHeaders)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] metadata = statusCodeAndHeaders.toString().split(CRLF);

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

}
