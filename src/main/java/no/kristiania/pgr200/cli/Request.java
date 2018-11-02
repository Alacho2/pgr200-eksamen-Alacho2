package no.kristiania.pgr200.cli;

import java.util.Objects;

public class Request {
    String hostName, table, method, path, body;
    int port;
    Number id;

    public Request(String hostName, String table, String method, int port, Number id, String body) {
        setHostName(hostName);
        setTable(table);
        setMethod(method);
        setPort(port);
        setId(id);
        setBody(body);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        if(getId() != null) return "/api/"+this.method+"/"+id;
        return "/api/"+this.method;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return port == request.port &&
                Objects.equals(hostName, request.hostName) &&
                Objects.equals(table, request.table) &&
                Objects.equals(method, request.method) &&
                Objects.equals(path, request.path) &&
                Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, table, method, path, port, id);
    }

    @Override
    public String toString() {
        return "Request{" +
                "hostName='" + hostName + '\'' +
                ", table='" + table + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", port=" + port +
                ", id=" + id +
                '}';
    }
}
