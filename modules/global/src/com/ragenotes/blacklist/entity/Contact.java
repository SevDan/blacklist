package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|type")
@Table(name = "BL_CONTACT")
@Entity(name = "bl_Contact")
public class Contact extends StandardEntity {

    private static final long serialVersionUID = 827216102057289653L;

    @Column(name = "VALUE")
    protected String value;

    @Column(name = "TYPE_")
    protected String type;

    @Column(name = "DESCRIPTION", length = 1024)
    protected String description;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContactType getType() {
        return type == null ? null : ContactType.fromId(type);
    }

    public void setType(ContactType type) {
        this.type = type == null ? null : type.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}