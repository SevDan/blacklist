package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum Mark implements EnumClass<Integer> {

    Negative(-20),
    ProbablyNot(-10),
    Neutral(0),
    Probably(10),
    Positive(20);

    private Integer id;

    Mark(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Mark fromId(Integer id) {
        for (Mark at : Mark.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}