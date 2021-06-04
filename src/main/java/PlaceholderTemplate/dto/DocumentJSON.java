package PlaceholderTemplate.dto;

public class DocumentJSON {
    int index;
    String title;
    Changes[] changes;

    public DocumentJSON(int index, String title, Changes[] changes) {
        this.index = index;
        this.title = title;
        this.changes = changes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Changes[] getChanges() {
        return changes;
    }

    public void setChanges(Changes[] changes) {
        this.changes = changes;
    }
}

