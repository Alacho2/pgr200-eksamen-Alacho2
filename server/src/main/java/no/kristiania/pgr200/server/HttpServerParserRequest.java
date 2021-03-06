package no.kristiania.pgr200.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpServerParserRequest {

    private HttpServerRequest request;
    private InputStream input;

    public HttpServerRequest parse(InputStream input) throws IOException {
        request = new HttpServerRequest();
        this.input = input;
        parseRequestLine();
        parseHeaderLines();
        parseBody();
        return request;
    }

    private void parseRequestLine() throws IOException{
        try {
            String[] s = HttpUtils.readNextLine(input).split("[ ]");
            request.setHttpMethod(s[0]);
            if(!s[1].isEmpty()) {
                request.setURL(s[1].substring(1));
            } else {
                request.setURL("");
            }
            request.setHttpVersion(s[2]);
            parseParameters(parsePath());
        } catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds on parseRequestLine.");
        } catch(NullPointerException e){
            System.out.println("Null pointer exception on parseRequestLine.");
        }
    }

    private String parsePath() {
        String uri[] = request.getURL().split("[?]");
        request.setPath(uri[0]);
        if(uri.length > 1) {
            return uri[1];
        } else{
            return "";
        }
    }

    private void parseParameters(String paramString) {
        if(!paramString.isEmpty()) {
            String queryParams[] = paramString.split("&");
            for (String param : queryParams) {
                int delimiterPos = param.indexOf("=");
                try {
                    if(delimiterPos > -1) {
                        request.setParameter(param.substring(0, delimiterPos), URLDecoder.decode(param.substring(delimiterPos + 1), "UTF-8"));
                    }
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Error parsing parameters.");
                } catch (StringIndexOutOfBoundsException e){
                    System.out.println("Malformed parameter " + param + " rejected.");
                }
            }
        }
    }

    private void parseHeaderLines() throws IOException {

        String line = HttpUtils.readNextLine(input);
        while(!line.isEmpty()) {
            int colonPos = line.indexOf(":");
            if(colonPos > -1) {
                request.setHeader(line.substring(0, colonPos), line.substring(colonPos + 1).trim());
                line = HttpUtils.readNextLine(input);
            }
        }
    }

    private void parseBody() throws NullPointerException{
        try {
            StringBuilder body = new StringBuilder();
            if(request.getHeader("Content-Length") != null) {
                for (int i = 0; i < Integer.parseInt(request.getHeader("Content-Length").trim()); i++) {
                    try {
                        int c = input.read();
                        body.append((char) c);
                    } catch (IOException e) {
                        System.out.println("Error parsing body.");
                    }
                }
                if(request.getHeader("Content-Type").equals("text/plain")) {
                    parseParameters(body.toString());
                } else {
                    request.setBody(body.toString());
                }
            }

        } catch(NullPointerException npe){
            System.out.println("Null pointer exception in parseBody.");
        }
    }
}
