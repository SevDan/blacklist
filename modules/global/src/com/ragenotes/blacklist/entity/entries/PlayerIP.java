package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NamePattern("%s|ip")
@Table(name = "BL_PLAYER_IP")
@Entity(name = "bl_PlayerIP")
public class PlayerIP extends StandardEntity {

    private static final long serialVersionUID = -8143449229008867090L;

    @Column(name = "IP")
    protected String ip;

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