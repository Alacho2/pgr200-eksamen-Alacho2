package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RequestBodyHandler<T extends Command> {
    List<T> body;
    List<RequestBodyField> fields;
    String table, mode;
    boolean isID;


    public RequestBodyHandler(List<T> body, String table, String mode) {
        this.body = body;
        this.table = table;
        this.mode = mode;
        addAllFields();
        isID = false;
    }

    public String getRequestBody(){
        return getJson(new RequestBodyObject(mode,table, fields));
    }


    private void addAllFields(){
        fields = new ArrayList<>();
        for(T command : body){
            checkForElement(command);
            if(command.getTable().toUpperCase().equals(this.table.toUpperCase())) addNewField(command.getName(), command.getValue());
        }
    }

    private void checkForElement(T command){
            if(command.getName().toUpperCase().equals("ID") && command.getValue() != null){
                this.isID = true;
            }else{
                this.isID = false;
            }
    }

    private boolean isRetrieve(){
        return this.mode.toUpperCase().equals("RETRIEVE");
    }


    private <V> void addNewField(String name, V value){
        if(name == null || value == null) return;
        if(isRetrieve() && !isID) return; // Do not want to append to field array when there is no id, want all entries by default
        fields.add(new RequestBodyField(name, value));
    }





    private String getJson(RequestBodyObject rbc) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(rbc);
    }
}
