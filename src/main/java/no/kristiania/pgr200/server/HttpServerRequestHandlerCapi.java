package no.kristiania.pgr200.server;

import com.google.gson.Gson;
import no.kristiania.pgr200.db.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class HttpServerRequestHandlerCapi implements HttpServerRequestHandler {

    private String  tableName, operationName;
    private AbstractDao dao;
    private HttpServerJsonWrapper jsonQuery;

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        if(request.getPath().toLowerCase().contains("capi")) {
            Gson gson = new Gson();
            if(request.getBody().isEmpty()){
                response.setBody("Invalid request body.");
                return false;
            }
            try {
                jsonQuery = gson.fromJson(request.getBody(), HttpServerJsonWrapper.class);
                ArrayList<HttpServerJsonWrapper.Field> jsonFields = (ArrayList<HttpServerJsonWrapper.Field>) jsonQuery.getFields();
                jsonQuery.setFieldMap();
                createDataObjects(jsonQuery.getTable());
                executeDbOperation(jsonQuery.getMode());

            } catch (InvalidParameterException e) {
                response.setBody("Invalid path in request.");
                return false;
            }
        } else {
            return false;
        }
        response.setStatusCode(200);
        return true;
    }

    private void executeDbOperation(String mode) {
        switch(mode){
            case "insert":
                break;
            case "delete":
                break;
            case "update":
                break;
            case "retrieve":
                break;
            default:
        }
    }

    private AbstractDao createDataObjects(String path){
        switch(path.toLowerCase()){
            case "conference":
                dao = new ConferenceDao();
            case "track":
                dao =  new TrackDao();
            case "talk":
                dao =  new TalkDao();
            default:
                throw new InvalidParameterException();
        }
    }
}
