package PlaceholderTemplate.dto;

import javax.persistence.*;

@Entity
@Table(name = "doc_files")
public class DocFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int file_Id;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "path")
    private String path;
    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "file_hash_name")
    private String fileHashName;

    public DocFiles(String groupId, String fileName, String path, String fileFormat,String fileHashName) {
        this.groupId = groupId;
        this.fileName = fileName;
        this.path = path;
        this.fileFormat = fileFormat;
        this.fileHashName = fileHashName;
    }

    public int getFileId() {
        return file_Id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public DocFiles() {

    }

    public String getFileHashName() {
        return fileHashName;
    }

    public void setFileHashName(String fileHashName) {
        this.fileHashName = fileHashName;
    }

}
