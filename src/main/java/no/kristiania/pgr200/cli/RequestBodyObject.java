package no.kristiania.pgr200.cli;

import java.util.List;
import java.util.Objects;

public class RequestBodyObject {
    String mode, table;
    List<RequestBodyField> fields;

    public RequestBodyObject(String mode, String table, List<RequestBodyField> fields) {
        this.mode = mode;
        this.table = table;
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBodyObject that = (RequestBodyObject) o;
        return Objects.equals(mode, that.mode) &&
                Objects.equals(table, that.table) &&
                Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, table, fields);
    }

    @Override
    public String toString() {
        return "RequestBodyObject{" +
                "mode='" + mode + '\'' +
                ", table='" + table + '\'' +
                ", fields=" + fields +
                '}';
    }
}
