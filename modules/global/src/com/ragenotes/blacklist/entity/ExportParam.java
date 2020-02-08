package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s [%s]|name,key")
@Table(name = "BL_EXPORT_PARAM")
@Entity(name = "bl_ExportParam")
public class ExportParam extends StandardEntity {

    private static final long serialVersionUID = -7131077662889108217L;

    @JoinColumn(name = "CONSUMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExportConsumer consumer;

    @Column(name = "NAME")
    private String name;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExportConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(ExportConsumer consumer) {
        this.consumer = consumer;
    }
}