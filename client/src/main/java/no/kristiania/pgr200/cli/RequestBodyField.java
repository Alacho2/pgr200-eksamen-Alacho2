package no.kristiania.pgr200.cli;

public class RequestBodyField<T> {
    String name;
    T value;

    public RequestBodyField(String name, T value) {
        this.name = name;
        this.value = value;
    }

}
