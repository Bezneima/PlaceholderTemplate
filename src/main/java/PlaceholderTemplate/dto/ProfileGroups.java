package PlaceholderTemplate.dto;

public class ProfileGroups {
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

    public ProfileGroups(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private String value;
    private String label;

}