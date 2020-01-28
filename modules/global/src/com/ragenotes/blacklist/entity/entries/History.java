package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s|name")
@Table(name = "BL_HISTORY")
@Entity(name = "bl_History")
public class History extends StandardEntity {

    private static final long serialVersionUID = -22614358449823602L;

    @Column(name = "NAME")
    protected String name;

    @Lob
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}