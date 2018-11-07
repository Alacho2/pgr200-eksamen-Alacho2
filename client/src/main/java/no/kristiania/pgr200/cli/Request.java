package no.kristiania.pgr200.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Request<T extends Command> {
    String hostName, table, method, path, mode;
    int port;
    Number id;
    List<T> body;

    public Request(String hostName, int port, List<T> body) {
        setHostName(hostName);
        setPort(port);
        setBody(body);
        setId((Number) getCommandValue("id"));
        for(T t : this.body){
            setMethod(t.getMode());
            setMode(t.getMode());
            setTable(t.getTable());
        }
        createPath();
    }

    public Request(String hostName, int port, T body) {
        setHostName(hostName);
        setPort(port);
        setBody(body);
        setId((Number) getCommandValue("id"));
        for(T t : this.body){
            setMethod(t.getMode());
            setMode(t.getMode());
            setTable(t.getTable());
        }
        createPath();
    }

    public String getHostName() {
        return hostName;
    }

    private void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTable() {
        return table;
    }

    private void setTable(String table) {
        this.table = table;
    }

    public String getMethod() {
        return method;
    }

    private void setMethod(String method) {
        this.method = mapToHttpMethod(method);
    }

    public String getPath() {
        return path;
    }

    public String getMode() {
        return mode;
    }

    private void setMode(String mode) {
        this.mode = mode;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
    }

    public Number getId() {
        return id;
    }

    private void setId(Number id) {
        this.id = id;
    }

    public String getBody(){
        return new RequestBodyHandler<>(this.body, getTable(), getMode()).getRequestBody();
    }

    private void setBody(List<T> body) {
        this.body = body;
    }

    private void setBody(T body) {
        List<T> list = new ArrayList<>();
        list.add(body);
        this.body = list;
    }

    private void createPath(){
        String path = "/capi/"+getTable();
        if(getId() != null && !getMethod().equals("POST")) path += "/"+id;
        this.path = path;
    }

    private String mapToHttpMethod(String method){
        switch (method.toUpperCase()){
            case "RETRIEVE":
                return "GET";
            case "INSERT":
                return "POST";
            case "UPDATE":
                return "PUT";
            case "DELETE":
                return "DELETE";

        }
        return null;
    }

    /**
     * @param name
     * @return The command value of the command that has a name matching the parameter input, if no match return null.
     */
    private Object getCommandValue(String name){
        for(T t : body){
            if(t.getName().toUpperCase().equals(name.toUpperCase())) return t.getValue();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request<?> request = (Request<?>) o;
        return port == request.port &&
                Objects.equals(hostName, request.hostName) &&
                Objects.equals(table, request.table) &&
                Objects.equals(method, request.method) &&
                Objects.equals(path, request.path) &&
                Objects.equals(mode, request.mode) &&
                Objects.equals(id, request.id) &&
                Objects.equals(body, request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, table, method, path, mode, port, id, body);
    }

    @Override
    public String toString() {
        return "Request{" +
                "hostName='" + hostName + '\'' +
                ", table='" + table + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", mode='" + mode + '\'' +
                ", port=" + port +
                ", id=" + id +
                ", body=" + body +
                '}';
    }
}
