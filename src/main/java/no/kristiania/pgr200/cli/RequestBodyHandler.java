package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RequestBodyHandler<T extends Command> {
    List<T> body;
    List<RequestBodyField> fields;
    String table, mode;


    public RequestBodyHandler(List<T> body, String table, String mode) {
        this.body = body;
        this.table = table;
        this.mode = mode;
        addAllFields();
    }

    public String getRequestBody(){
        return getJson(new RequestBodyObject(mode,table, fields));
    }


    private void addAllFields(){
        fields = new ArrayList<>();
        for(T command : body){
            if(command.getTable().toUpperCase().equals(this.table.toUpperCase())) addNewField(command.getName(), command.getValue());
        }
    }


    private <V> void addNewField(String name, V value){
        fields.add(new RequestBodyField(name, value));
    }





    private String getJson(RequestBodyObject rbc) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(rbc);
    }
}
