package teamtalk.enums;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created by stokowiec on 2015-06-19.
 */
public enum UserType {

    DEFAULT(1),
    ADMIN(2);

    private final int numericValue;

    UserType(int numericValue) {
        this.numericValue = numericValue;
    }

    @Override
    public String toString(){
        return String.valueOf(numericValue);
    }

    @JsonCreator
    public static UserType fromString(String text) {
        if (text != null) {
            for (UserType b : UserType.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }
}
