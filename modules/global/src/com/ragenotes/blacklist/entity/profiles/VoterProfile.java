package com.ragenotes.blacklist.entity.profiles;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "BLACKLIST_VOTER_PROFILE")
@Entity(name = "blacklist_VoterProfile")
public class VoterProfile extends StandardEntity {

    private static final long serialVersionUID = 2362426971769122731L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @JoinTable(name = "BLACKLIST_VOTER_PROFILE_CONTACT_LINK",
            joinColumns = @JoinColumn(name = "ROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    @ManyToMany
    protected List<Contact> contacts;

    @JoinTable(name = "BLACKLIST_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ENTRY_ID"))
    @ManyToMany
    protected List<BlackListEntry> votedEntries;

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

    public List<BlackListEntry> getVotedEntries() {
        return votedEntries;
    }

    public void setVotedEntries(List<BlackListEntry> votedEntries) {
        this.votedEntries = votedEntries;
    }

    public ExtUser getUser() {
        return user;
    }

    public void setUser(ExtUser user) {
        this.user = user;
    }
}