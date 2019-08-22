package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NamePattern("%s|ip")
@Table(name = "BLACKLIST_PLAYER_IP")
@Entity(name = "blacklist_PlayerIP")
public class PlayerIP extends StandardEntity {

    private static final long serialVersionUID = -8143449229008867090L;

    @Column(name = "IP")
    protected String ip;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @Column(name = "FIXATION_DATE")
    protected Date fixationDate;

    @Column(name = "DESCRIPTION")
    protected String description;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getFixationDate() {
        return fixationDate;
    }

    public void setFixationDate(Date fixationDate) {
        this.fixationDate = fixationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}