package no.kristiania.pgr200.server;

import java.io.IOException;
import java.net.Socket;

public class HttpClientRequest {

    private String hostname, path, method, body;
    private int port;

    public HttpClientRequest(String hostname, int port, String path, String method) {
        this.hostname = hostname;
        this.path = path;
        this.port = port;
        this.method = method;
        this.body = "";
    }

    public HttpClientRequest(String hostname, int port, String path, String method, String body) {
        this.hostname = hostname;
        this.path = path;
        this.port = port;
        this.method = method;
        this.body = body;
    }

    public HttpClientResponse execute() throws IOException {
        try(Socket socket = new Socket(hostname, port)) {
            socket.getOutputStream()
                    .write((method + " " + path + " HTTP/1.1\r\n").getBytes());
            socket.getOutputStream()
                    .write(("Host: " + hostname + "\r\n").getBytes());
            socket.getOutputStream()
                    .write("Connection: close\r\n".getBytes());
            if(!body.isEmpty()){
                socket.getOutputStream().write(("Content-Length: " + body.length()).getBytes());
                socket.getOutputStream().write("\r\n".getBytes());
                socket.getOutputStream().write(body.getBytes());
            } else{
                socket.getOutputStream().write("\r\n".getBytes());
            }
            return new HttpClientResponse(socket);
        }

    }
}

