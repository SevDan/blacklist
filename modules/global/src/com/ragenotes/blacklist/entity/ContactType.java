package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum ContactType implements EnumClass<String> {

    VkUrl("vkUrl"),
    FacebookUrl("facebookUrl"),
    YouTubeUrl("youTubeUrl"),
    Skype("skype"),
    Telegram("telegram"),
    EMail("e-mail"),
    Instagram("instagram"),
    PhoneNumber("phoneNumber"),
    YandexMoney("yandexMoney"),
    WebMoney("webMoney");

    private String id;

    ContactType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ContactType fromId(String id) {
        for (ContactType at : ContactType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}