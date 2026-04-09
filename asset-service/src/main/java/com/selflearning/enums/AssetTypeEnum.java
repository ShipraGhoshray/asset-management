package com.selflearning.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetTypeEnum {
    REPO("REPO", "Repos"),
    REVREPO("REVREPO", "Reverse-Repos"),
    TBA("TBA", "To Be Announced");

    private final String code;
    private final String description;

    public static AssetTypeEnum fromCode(String code) {
        for (AssetTypeEnum type : AssetTypeEnum.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown asset type: " + code);
    }
}