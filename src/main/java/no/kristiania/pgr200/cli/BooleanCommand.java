package no.kristiania.pgr200.cli;

public class BooleanCommand extends Command<String> {

    public BooleanCommand(String name, String description, String type, String mode, String table,String subQuestionValue, String[] subQuestionName) {
        super(name, description, type, mode, table,subQuestionValue, subQuestionName);
    }

    public BooleanCommand() {
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public Command setValue(String value) {
        String result = "";
        if(value.toUpperCase().contains("Y")||value.toUpperCase().contains("T")){
            result = "TRUE";
        }else if(value.toUpperCase().contains("N")||value.toUpperCase().contains("F")){
            result = "FALSE";
        }
        return super.setValue(result);
    }

    @Override
    String handleValue(String value) throws IllegalArgumentException{
        if(value != null && value instanceof String && !value.isEmpty()){
            return value;
        }
        else{
            throw new IllegalArgumentException("Invalid Input");
        }
    }
}
