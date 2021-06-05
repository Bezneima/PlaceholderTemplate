package PlaceholderTemplate.dto;

public class Users {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Users(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String value;
    public String label;

}