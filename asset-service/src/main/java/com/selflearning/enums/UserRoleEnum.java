package com.selflearning.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER_ROLE_ADMIN("ADMIN", "Super User"),
    USER_ROLE_ENUM("USER", "General User");

    private final String code;
    private final String description;
    UserRoleEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserRoleEnum fromCode(String code) {
        for (UserRoleEnum type : UserRoleEnum.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user role: " + code);
    }
}