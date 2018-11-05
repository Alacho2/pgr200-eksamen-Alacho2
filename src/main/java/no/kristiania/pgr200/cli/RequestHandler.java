package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.server.HttpClientRequest;
import no.kristiania.pgr200.server.HttpClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RequestHandler {

    List<Command> commands;

    public RequestHandler(List<Command> commands) {
        this.commands = commands;
    }

    public RequestHandler(String table, String mode, Number id) {
        commands = new ArrayList<>();
        commands.add(new BasicCommand<Number>("id",mode,table).setValue(id));
    }

    public RequestHandler(String table, String mode) {
        commands = new ArrayList<>();
        commands.add(new BasicCommand<Number>("id",mode,table));
    }

    public String execute(int port, String hostName) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpClientRequest request = checkRequestMethod(mapToRequest(port, hostName));
        HttpClientResponse httpClientResponse = request.execute();
        Response response = new Response(httpClientResponse.getStatusCode(), httpClientResponse.getBody());
        sb.append("StatusCode:\n"+response.getStatusCode()+"\n");
        sb.append("Body:\n"+response.getBody());
        return sb.toString();
    }

    private HttpClientRequest checkRequestMethod(Request r){
        HttpClientRequest httpClientRequest = null;
        System.out.println("path: \n"+r.getPath());
        System.out.println("body: \n"+r.getBody());
        switch (r.getMethod()){
            case "GET":
                httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod());
            case "POST":
                httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod(), "application/json", r.getBody());
        }
        return httpClientRequest;
    }


    Request mapToRequest(int port, String hostName){
        return new Request(hostName, port, commands);
    }

}
