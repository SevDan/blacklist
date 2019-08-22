package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum EntryStatus implements EnumClass<String> {

    Voting("voting"),
    Reviewing("reviewing"),
    Accepting("accepting"),
    Accepted("accepted"),
    Rejected("rejected");

    private String id;

    EntryStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EntryStatus fromId(String id) {
        for (EntryStatus at : EntryStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}