package no.kristiania.pgr200.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServerJsonWrapper {

    private String mode;
    private String table;
    private List<Field> fields = null;
    private Map<String, String> fieldMap = new HashMap<String, String>();

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

    public Map<String, String> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap() {
        for (Field field: fields) {
            fieldMap.put(field.getName().trim(), field.getValue().trim());
        }
    }

    public String getField(String param){
        return fieldMap.get(param).toString();
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
