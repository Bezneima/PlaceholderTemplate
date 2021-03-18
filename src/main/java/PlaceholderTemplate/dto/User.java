package PlaceholderTemplate.dto;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @SerializedName("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @SerializedName("avatar_img_path")
    @Column(name = "avatar_img_path")
    private String avatarImgPath;
    @SerializedName("role")
    @Column(name = "role")
    private String role;
    @SerializedName("user_name")
    @Column(name = "user_name")
    private String userName;
    @SerializedName("password")
    @Column(name = "password")
    private String password;
    @SerializedName("last_user_token")
    @Column(name = "last_user_token")
    private String lastUserToken;
    @SerializedName("token_created_at")
    @Column(name = "token_created_at")
    private java.time.LocalDateTime tokenCreatedAt;

    public User(String role, String userName) {
        this.role = role;
        this.userName = userName;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getAvatarImgPath() {
        return avatarImgPath;
    }

    public void setAvatarImgPath(String avatarImgPath) {
        this.avatarImgPath = avatarImgPath;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastUserToken() {
        return lastUserToken;
    }

    public void setLastUserToken(String lastUserToken) {
        this.lastUserToken = lastUserToken;
    }

    public LocalDateTime getTokenCreatedAt() {
        return tokenCreatedAt;
    }

    public void setTokenCreatedAt(LocalDateTime tokenCreatedAt) {
        this.tokenCreatedAt = tokenCreatedAt;
    }
}
