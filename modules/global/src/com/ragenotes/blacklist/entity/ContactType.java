package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum ContactType implements EnumClass<String> {

    // you should change this enum for your game model (you can use it for game params) (use it for payment params is not recommended)
    VkUrl("vkUrl"), // if your game based on social networks
    FacebookUrl("facebookUrl"), 
    YouTubeUrl("youTubeUrl"),
    Twitch("twitch"),
    Skype("skype"),
    Telegram("telegram"),
    EMail("e-mail"),
    Instagram("instagram"),
    PhoneNumber("phoneNumber"), // deprecated
    YandexMoney("yandexMoney"), // deprecated
    WebMoney("webMoney"), // deprecated
    Qiwi("quwi"), // deprecated
    PayPal("payPal"), // deprecated
    Bank("bank"), // deprecated
    Other("other");

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
