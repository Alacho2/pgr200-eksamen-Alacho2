
package no.kristiania.pgr200.server;

import com.google.gson.Gson;
import no.kristiania.pgr200.db.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HttpServerRequestHandlerCapi<T extends DataAccessObject, K extends TableObject> implements HttpServerRequestHandler {

    private String  tableName, operationName;
    private AbstractDao dao;
    private HttpServerJsonWrapper jsonQuery;
    private K table;
    private HttpServerRequest request;
    private HttpServerResponse response;
    private Gson gson = new Gson();

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {
        this.request = request;
        this.response = response;

        if(request.getPath().toLowerCase().contains("capi")) {

            if(request.getBody().isEmpty()){
                response.setBody("Invalid request body.");
                return false;
            }
            try {
                jsonQuery = gson.fromJson(request.getBody(), HttpServerJsonWrapper.class);
                ArrayList<HttpServerJsonWrapper.Field> jsonFields = (ArrayList<HttpServerJsonWrapper.Field>) jsonQuery.getFields();
                jsonQuery.setFieldMap();
                createDataObjects(jsonQuery.getTable());
                try {
                    executeDbOperation(jsonQuery.getMode(), (T) this.dao, (K) this.table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

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

    private void createDataObjects(String path){
        switch(path.toLowerCase()){
            case "conference":
                dao = new ConferenceDao();
                table = (K) createConferenceFromJson();
                break;
            case "track":
                dao =  new TrackDao();
                break;
            case "talk":
                dao =  new TalkDao();
                break;
            default:
                throw new InvalidParameterException();
        }
    }

    private void executeDbOperation(String mode, T dao, K TableObject) throws SQLException {
        switch(mode.toLowerCase()){
            case "insert":
                dao.create(TableObject);
                response.setBody(gson.toJson(TableObject));
                response.setStatusCode(200);
                break;
            case "delete":
                break;
            case "update":
                break;
            case "retrieve":
                response.setBody(gson.toJson(dao.readOne(Long.parseLong(jsonQuery.getField("id")))));
                response.setStatusCode(200);
                break;
            case "reset":
                break;
            default:
        }
    }

    public Conference createConferenceFromJson(){
        Conference conference = new Conference();
        conference.setTitle(jsonQuery.getFieldMap().get("title").toString());
        conference.setDescription(jsonQuery.getFieldMap().get("description").toString());
        conference.setDate_start(jsonQuery.getFieldMap().get("time-start").toString());
        conference.setDate_end(jsonQuery.getFieldMap().get("time-end").toString());
        return conference;
    }

    public Track createTrackFromJson(){
        Track track = new Track();
        return track;
    }

    public Talk createTalkFromJson(){
        Talk talk = new Talk();
        return talk;
    }
}
