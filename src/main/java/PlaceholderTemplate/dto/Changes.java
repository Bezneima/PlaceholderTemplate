package PlaceholderTemplate.dto;

public class Changes {
    String key;
    String value;
    String valueTo;

    public Changes(String key, String value, String valueTo) {
        this.key = key;
        this.value = value;
        this.valueTo = valueTo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }
}
