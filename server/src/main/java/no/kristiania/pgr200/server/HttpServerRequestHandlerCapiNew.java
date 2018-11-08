package no.kristiania.pgr200.server;

import com.google.gson.Gson;
import no.kristiania.pgr200.db.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class HttpServerRequestHandlerCapiNew implements HttpServerRequestHandler {

    HttpServerRequest request;
    HttpServerResponse response;
    Map<String, Integer> params = new HashMap<>();
    List<String> patterns = Arrays.asList("capi/conference/:id", "capi/conference", "capi/track/:id", "capi/track", "capi/talk/:id", "capi/talk");

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {
        this.request = request;
        this.response = response;

        for (String pattern : patterns) {
            if (requestMatches(request.getPath(), pattern, params)) {
                try {
                    if (processDbRequest()) {
                        return true;
                    }
                } catch (SQLException e) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean requestMatches(String path, String pattern, Map<String, Integer> params) {
        String actualParts[] = path.split("/");
        String patternParts[] = pattern.split("/");
        if (actualParts.length != patternParts.length) {
            return false;
        }
        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i].startsWith(":")) {
                try {
                    params.put(patternParts[i], Integer.parseInt(actualParts[i]));
                } catch (NumberFormatException e) {
                    return false;
                }

            } else if (!actualParts[i].equals(patternParts[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean processDbRequest() throws SQLException {
        Gson gson = new Gson();
        if (request.getPath().startsWith("capi/conference")) {
            Conference conference;
            ConferenceDao dao = new ConferenceDao();
            switch (request.getHttpMethod()) {
                case "GET":
                    if (params.containsKey(":id")) {
                        response.setBody(gson.toJson(dao.readOne(params.get(":id"))));
                    } else {
                        response.setBody(gson.toJson(dao.readAll()));
                    }
                    response.setStatusCode(200);
                    break;
                case "DELETE":
                    dao.deleteOneById(params.get(":id"));
                    response.setBody(gson.toJson("Element with id " + params.get(":id") + " successfully deleted."));
                    response.setStatusCode(200);
                    break;
                case "PUT":
                    conference = gson.fromJson(request.getBody(), Conference.class);
                    conference.setId(params.get(":id"));
                    dao.updateOneById(conference);
                    response.setBody(gson.toJson(conference));
                    response.setStatusCode(200);
                    break;
                case "POST":
                    conference = gson.fromJson(request.getBody(), Conference.class);
                    dao.create(conference);
                    response.setBody(gson.toJson(conference));
                    response.setStatusCode(200);
                    break;
            }
            return true;
        }
        if(request.getPath().startsWith("capi/track")){
            Track track;
            TrackDao dao = new TrackDao();
            switch(request.getHttpMethod()){
                case "GET":
                    if(params.containsKey(":id")) {
                        response.setBody(gson.toJson(dao.readOne(params.get(":id"))));
                    } else{
                        response.setBody(gson.toJson(dao.readAll()));
                    }
                    response.setStatusCode(200);
                    break;
                case "DELETE":
                    dao.deleteOneById(params.get(":id"));
                    response.setBody(gson.toJson("Element with id " + params.get(":id") + " successfully deleted."));
                    response.setStatusCode(200);
                    break;
                case "PUT":
                    track = gson.fromJson(request.getBody(), Track.class);
                    track.setId(params.get(":id"));
                    dao.updateOneById(track);
                    response.setBody(gson.toJson(track));
                    response.setStatusCode(200);
                    break;
                case "POST":
                    track = gson.fromJson(request.getBody(), Track.class);
                    dao.create(track);
                    response.setBody(gson.toJson(track));
                    response.setStatusCode(200);
                    break;
            }
            return true;
        }

        if(request.getPath().startsWith("capi/talk")){
            Talk talk;
            TalkDao dao = new TalkDao();
            switch(request.getHttpMethod()){
                case "GET":
                    if(params.containsKey(":id")) {
                        response.setBody(gson.toJson(dao.readOne(params.get(":id"))));
                    } else{
                        response.setBody(gson.toJson(dao.readAll()));
                    }
                    response.setStatusCode(200);
                    break;
                case "DELETE":
                    dao.deleteOneById(params.get(":id"));
                    response.setBody(gson.toJson("Element with id " + params.get(":id") + " successfully deleted."));
                    response.setStatusCode(200);
                    break;
                case "PUT":
                    talk = gson.fromJson(request.getBody(), Talk.class);
                    talk.setId(params.get(":id"));
                    dao.updateOneById(talk);
                    response.setBody(gson.toJson(talk));
                    response.setStatusCode(200);
                    break;
                case "POST":
                    talk = gson.fromJson(request.getBody(), Talk.class);
                    dao.create(talk);
                    response.setBody(gson.toJson(talk));
                    response.setStatusCode(200);
                    break;
            }
            return true;
        }

        return false;
    }
}
