package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum ExportConsumerType implements EnumClass<String> {

    VK("vk"),
    PostConsumer("post_consumer"),
    VKWall("vk_wall");

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