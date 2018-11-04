package no.kristiania.pgr200.server;

import com.google.gson.Gson;
import no.kristiania.pgr200.db.Conference;
import no.kristiania.pgr200.db.ConferenceDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HttpServerRequestHandlerCapi implements HttpServerRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        if(request.getPath().contains("capi")){
            Gson gson = new Gson();
            System.out.println("request json: " + request.getBody());
            HttpServerJsonWrapper jsonQuery = gson.fromJson(request.getBody(), HttpServerJsonWrapper.class);
            System.out.println("mode: " + jsonQuery.getMode());
            System.out.println("table: " + jsonQuery.getTable());
            ArrayList<HttpServerJsonWrapper.Field> jsonFields = (ArrayList<HttpServerJsonWrapper.Field>) jsonQuery.getFields();
            jsonQuery.setFieldMap();
            Conference conference = jsonQuery.createConferenceFromJson();
            ConferenceDao conferenceDao = new ConferenceDao();
            try {
                conferenceDao.create(conference);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }
}
