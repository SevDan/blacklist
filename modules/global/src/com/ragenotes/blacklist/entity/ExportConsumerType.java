package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum ExportConsumerType implements EnumClass<String> {

    VK("vk"),
    PostConsumer("PostConsumer");

    private String id;

    ExportConsumerType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ExportConsumerType fromId(String id) {
        for (ExportConsumerType at : ExportConsumerType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}