package domain;

public enum Role {

    SUPERVISOR ("supervisor"),
    ADMIN      ("admin"),
    USER       ("user");

    public final String value;


    Role(String value) {
        this.value = value;
    }
}
