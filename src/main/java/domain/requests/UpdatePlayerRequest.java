package domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.data.JsonConverter;

import java.util.Objects;


public class UpdatePlayerRequest {

    private Object login;
    private Object password;
    private Object screenName;
    private Object role;
    private Object gender;
    private Object age;
    @JsonIgnore
    private Boolean ignoreNulls;


    public UpdatePlayerRequest() {
    }

    public UpdatePlayerRequest(Object login, Object password, Object screenName, Object role, Object gender, Object age) {
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    public UpdatePlayerRequest(Object login, Object password, Object screenName, Object role, Object gender, Object age, Boolean ignoreNulls) {
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.ignoreNulls = ignoreNulls;
    }


    public Object getLogin() {
        return login;
    }

    public void setLogin(Object login) {
        this.login = login;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }


    public String toJson() {
        return new JsonConverter().toJson(this, Objects.requireNonNullElse(ignoreNulls, false));
    }
}
