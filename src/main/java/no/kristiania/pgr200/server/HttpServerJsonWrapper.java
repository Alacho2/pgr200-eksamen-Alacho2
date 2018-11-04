package no.kristiania.pgr200.server;

import no.kristiania.pgr200.db.Conference;
import no.kristiania.pgr200.db.Talk;
import no.kristiania.pgr200.db.Track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServerJsonWrapper {

    private String mode;
    private String table;
    private List<Field> fields = null;
    private Map<String, Object> fieldMap = new HashMap<String, Object>();

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode.trim();
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table.trim();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap() {
        for (Field field: fields) {
            fieldMap.put(field.getName().trim(), field.getValue().trim());
        }
    }

    public Conference createConferenceFromJson(){
        Conference conference = new Conference();
        conference.setTitle(fieldMap.get("title").toString());
        conference.setDescription(fieldMap.get("description").toString());
        conference.setDate_start(fieldMap.get("time-start").toString());
        conference.setDate_end(fieldMap.get("time-end").toString());
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

    public class Field {

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
