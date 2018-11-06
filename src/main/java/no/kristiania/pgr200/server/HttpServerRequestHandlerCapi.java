
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
                    executeDbOperation(jsonQuery.getMode(), (T) this.dao, this.table);
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
        response.setContentType("application/json");
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
                table = (K) createTrackFromJson();
                break;
            case "talk":
                dao =  new TalkDao();
                table = (K) createTalkFromJson();
                break;
            case "reset":
                // reset db
                break;
            default:
                throw new InvalidParameterException();
        }
    }

    private void executeDbOperation(String mode, T dao, K tableObject) throws SQLException {
        switch(mode.toLowerCase()){
            case "insert":
                dao.create(tableObject);
                System.out.println(tableObject.toString());
                response.setBody(gson.toJson(tableObject));
                response.setStatusCode(200);
                break;
            case "delete":
                response.setBody(gson.toJson("Object " + jsonQuery.getField("id") + " deleted."));
                response.setStatusCode(200);
                break;
            case "update":
                System.out.println(tableObject.toString());
                dao.updateOneById(tableObject);
                response.setBody(gson.toJson(tableObject));
                response.setStatusCode(200);
                break;
            case "retrieve":
                if(jsonQuery.getFieldMap().containsKey("id")) {
                    response.setBody(gson.toJson(dao.readOne(Long.parseLong(jsonQuery.getField("id")))));
                } else{
                    response.setBody(gson.toJson(dao.readAll()));
                }
                response.setStatusCode(200);
                break;
            case "reset":
                // reset db
                break;
            default:
        }
    }

    private Conference createConferenceFromJson(){
        Conference conference = new Conference();
        if(jsonQuery.getMode().contains("update")) {
            conference.setId(Integer.parseInt(jsonQuery.getField("id")));
            conference.setTitle(jsonQuery.getFieldMap().get("title"));
            conference.setDescription(jsonQuery.getFieldMap().get("description"));
            conference.setDate_start(jsonQuery.getFieldMap().get("time-start"));
            conference.setDate_end(jsonQuery.getFieldMap().get("time-end"));
        } else if(jsonQuery.getMode().contains("insert")){
            conference.setTitle(jsonQuery.getFieldMap().get("title"));
            conference.setDescription(jsonQuery.getFieldMap().get("description"));
            conference.setDate_start(jsonQuery.getFieldMap().get("time-start"));
            conference.setDate_end(jsonQuery.getFieldMap().get("time-end"));
        }
        return conference;
    }

    private Track createTrackFromJson(){
        Track track = new Track();
        if(jsonQuery.getMode().contains("update")){
            track.setId(Integer.parseInt(jsonQuery.getField("id")));
            track.setTitle(jsonQuery.getFieldMap().get("title"));
            track.setDescription(jsonQuery.getFieldMap().get("description"));
            track.setTrack_conference_id(Integer.parseInt(jsonQuery.getFieldMap().get("track_conference_id")));
            return track;
        } else if(jsonQuery.getMode().contains("insert")){
            track.setTitle(jsonQuery.getFieldMap().get("title"));
            track.setDescription(jsonQuery.getFieldMap().get("description"));
            track.setTrack_conference_id(Integer.parseInt(jsonQuery.getFieldMap().get("track_conference_id")));
            return track;
        }
        return track;
    }

    private Talk createTalkFromJson(){
        Talk talk = new Talk();
        if(jsonQuery.getMode().contains("update")) {
            talk.setId(Integer.parseInt(jsonQuery.getField("id")));
            talk.setTitle(jsonQuery.getFieldMap().get("title"));
            talk.setDescription(jsonQuery.getFieldMap().get("description"));
            talk.setTalk_location(jsonQuery.getFieldMap().get("talk_location"));
            talk.setTimeslot(jsonQuery.getFieldMap().get("timeslot"));
            talk.setTalk_track_id(Integer.parseInt(jsonQuery.getFieldMap().get("talk_track_id")));
        } else if(jsonQuery.getMode().contains("insert")){
            talk.setTitle(jsonQuery.getFieldMap().get("title"));
            talk.setDescription(jsonQuery.getFieldMap().get("description"));
            talk.setTalk_location(jsonQuery.getFieldMap().get("talk_location"));
            talk.setTimeslot(jsonQuery.getFieldMap().get("timeslot"));
            talk.setTalk_track_id(Integer.parseInt(jsonQuery.getFieldMap().get("talk_track_id")));
        }
        return talk;
    }
}
