package no.kristiania.pgr200.cli;

public class StringCommand extends Command<String> {
    public StringCommand(String name, String description, String type, String mode, String table,String subQuestionValue, String[] subQuestionName) {
        super(name, description, type, mode, table,subQuestionValue, subQuestionName);
    }

    public StringCommand() {
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
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
