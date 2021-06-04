package PlaceholderTemplate.dto;

import PlaceholderTemplate.Dao.DocFilesDao;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "doc_files")
public class DocFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("fileId")
    private int file_Id;
    @SerializedName("groupId")
    @Column(name = "group_id")
    private String groupId;
    @SerializedName("fileName")
    @Column(name = "file_name")
    private String fileName;
    @SerializedName("path")
    @Column(name = "path")
    private String path;
    @SerializedName("fileFormat")
    @Column(name = "file_format")
    private String fileFormat;
    @SerializedName("fileHashName")
    @Column(name = "file_hash_name")
    private String fileHashName;
    @SerializedName("inputFieldsNames")
    @Column(name = "input_fields_names")
    private String inputFieldsNames;

    public DocFiles(String groupId, String fileName, String path, String fileFormat,String fileHashName,String inputFieldsNames) {
        this.groupId = groupId;
        this.fileName = fileName;
        this.path = path;
        this.fileFormat = fileFormat;
        this.fileHashName = fileHashName;
        this.inputFieldsNames = inputFieldsNames;
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

    public String getInputFieldsNames() {
        return inputFieldsNames;
    }

    public void setInputFieldsNames(String inputFieldsNames) {
        this.inputFieldsNames = inputFieldsNames;
    }
}
