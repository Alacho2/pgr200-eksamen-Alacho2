package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;

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
        commands.add(new BasicCommand<Number>("id", mode, table).setValue(id));
    }

    public RequestHandler(String table, String mode) {
        commands = new ArrayList<>();
        commands.add(new BasicCommand<Number>("id", mode, table));
    }

    public String execute(int port, String hostName) throws IOException {
        HttpClientResponse httpClientResponse = checkRequestMethod(mapToRequest(port, hostName)).execute();
        return new Response(httpClientResponse).getBody();
    }

    private HttpClientRequest checkRequestMethod(Request r) {
        HttpClientRequest httpClientRequest = null;
        System.out.println("method: \n" + r.getMethod());
        System.out.println("path: \n" + r.getPath());
        System.out.println("body: \n" + r.getBody());
        if(r.getMethod().equals("GET") || r.getMethod().equals("DELETE")) {
            httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod());
        }
        else{
            httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod(), "application/json", r.getBody());
        }
        return httpClientRequest;
    }


    Request mapToRequest(int port, String hostName) {
        return new Request(hostName, port, commands);
    }

}
