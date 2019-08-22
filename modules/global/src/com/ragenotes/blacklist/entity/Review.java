package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s|mark")
@Table(name = "BLACKLIST_REVIEW")
@Entity(name = "blacklist_Review")
public class Review extends StandardEntity {

    private static final long serialVersionUID = -1282041722159019347L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    protected BlackListEntry entry;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @Column(name = "MARK", nullable = false)
    protected Integer mark;

    public BlackListEntry getEntry() {
        return entry;
    }

    public void setEntry(BlackListEntry entry) {
        this.entry = entry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Mark getMark() {
        return mark == null ? null : Mark.fromId(mark);
    }

    public void setMark(Mark mark) {
        this.mark = mark == null ? null : mark.getId();
    }
}