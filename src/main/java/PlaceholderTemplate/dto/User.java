package PlaceholderTemplate.dto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "avatar_img_path")
    private String avatarImgPath;
    @Column(name = "role")
    private String role;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "last_user_token")
    private String lastUserToken;
    @Column(name = "token_created_at")
    private java.time.LocalDateTime tokenCreatedAt;

    public User(String role, String userName) {
        this.role = role;
        this.userName = userName;
    }

    public User() {

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
