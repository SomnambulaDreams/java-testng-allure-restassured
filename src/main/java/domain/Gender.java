package domain;

import utils.data.DataGenerator;


public enum Gender {

    MALE   ("male"),
    FEMALE ("female");

    public final String value;


    Gender(String value) {
        this.value = value;
    }


    public static String random() {
        if (DataGenerator.getRandomBoolean())
            return MALE.value;
        else
            return FEMALE.value;
    }
}
