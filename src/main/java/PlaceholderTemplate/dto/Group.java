package PlaceholderTemplate.dto;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @SerializedName("group_id")
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer groupId;
    @SerializedName("group_name")
    @Column(name = "group_name")
    private String groupName;
    @SerializedName("member_name")
    @Column(name = "member_name")
    private String memberName;
    @SerializedName("group_admin")
    @Column(name = "group_admin")
    private boolean groupAdmin;


    public Group() {

    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public boolean isGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(boolean groupAdmin) {
        this.groupAdmin = groupAdmin;
    }
}
