package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.kristiania.pgr200.server.HttpClientRequest;
import no.kristiania.pgr200.server.HttpClientResponse;
import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.utils.OutputHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class RequestHandler {

    List<Command> commands;
    String table, method;

    public RequestHandler(List<Command> commands) {
        this.commands = commands;
        this.table = (String) getCommandValue("table");
        for(Command c : this.commands) method = c.getMode().toUpperCase().equals("RETRIEVE") ? "GET" : "POST";
    }

    public RequestHandler(String table, String method) {
        this.table = table.toUpperCase();
        this.method = method;
    }

    public String execute(int port, String hostName) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpClientRequest request = checkRequestMethod(mapToRequest(port, hostName, getJson()));
        Response response = new Response(request.execute());
        sb.append("StatusCode:\n"+response.getStatusCode()+"\n");
        sb.append("Body:\n"+response.getBody());
        return sb.toString();
    }

    private HttpClientRequest checkRequestMethod(Request r){
        HttpClientRequest httpClientRequest = null;
        switch (r.getMethod()){
            case "GET":
                httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod());
            case "POST":
                httpClientRequest = new HttpClientRequest(r.getHostName(), r.getPort(), r.getPath(), r.getMethod(), r.getBody());
        }
        return httpClientRequest;
    }


    private Request mapToRequest(int port, String hostName, String data){
        Number id = (Number) getCommandValue("id");
        return new Request(hostName, table, method, port, id, data);
    }

    private String getJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(commands);
    }

    /**
     * @param name
     * @return The command value of the command that has a name matching the parameter input, if no match return null.
     */
    private Object getCommandValue(String name){
        for(Command c : commands){
            if(c.getName().toUpperCase().equals(name.toUpperCase())) return c.getValue();
        }
        return null;
    }
}
