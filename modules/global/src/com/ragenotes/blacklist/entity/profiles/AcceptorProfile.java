package com.ragenotes.blacklist.entity.profiles;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.Contact;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "BL_ACCEPTOR_PROFILE")
@Entity(name = "bl_AcceptorProfile")
public class AcceptorProfile extends StandardEntity {

    private static final long serialVersionUID = 3949723313495069240L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @JoinTable(name = "BL_ACCEPTOR_PROFILE_CONTACT_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    @ManyToMany
    protected List<Contact> contacts;

    @JoinTable(name = "BL_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ENTRY_ID"))
    @ManyToMany
    protected List<BlackListEntry> acceptedEntries;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected ExtUser user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<BlackListEntry> getAcceptedEntries() {
        return acceptedEntries;
    }

    public void setAcceptedEntries(List<BlackListEntry> acceptedEntries) {
        this.acceptedEntries = acceptedEntries;
    }

    public ExtUser getUser() {
        return user;
    }

    public void setUser(ExtUser user) {
        this.user = user;
    }
}