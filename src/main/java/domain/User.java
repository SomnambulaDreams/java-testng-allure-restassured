package domain;

public enum User {

    // After I found a critical bug - supervisor's login is empty and this fact blocks using this account for
    // creating and/or executing some tests.
    // I decided to create 2 users - they are used for creating other users only.

    ADMIN_1 (221000330, "admin1"),
    ADMIN_2 (1973398025, "admin2");

    public final int id;
    public final String login;


    User(int id, String login) {
        this.id = id;
        this.login = login;
    }
}
