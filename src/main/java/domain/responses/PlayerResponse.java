package domain.responses;

import java.util.Objects;

public class PlayerResponse {

    private Long id;
    private String login;
    private String password;
    private String screenName;
    private String role;
    private String gender;
    private Integer age;


    public PlayerResponse() {
    }

    public PlayerResponse(Long id, String login, String password, String screenName, String role, String gender, Integer age) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PlayerResponse)) return false;
        PlayerResponse that = (PlayerResponse) object;
        return
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getScreenName(), that.getScreenName()) &&
                Objects.equals(getRole(), that.getRole()) &&
                Objects.equals(getGender(), that.getGender()) &&
                Objects.equals(getAge(), that.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getScreenName(), getRole(), getGender(), getAge());
    }
}
