package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s|name")
@Table(name = "BLACKLIST_HISTORY")
@Entity(name = "blacklist_History")
public class History extends StandardEntity {

    private static final long serialVersionUID = -22614358449823602L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}