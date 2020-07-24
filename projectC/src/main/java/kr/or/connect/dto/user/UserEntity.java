package kr.or.connect.dto.user;

// 로그인 아이디와 암호 정보를 가지는 클래스
public class UserEntity {
    private int userId;
    private String email;
    private String password;

    public UserEntity (int userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
