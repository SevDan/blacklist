package com.ragenotes.blacklist.entity.entries;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.Mark;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s %s|fullName,nickName")
@Table(name = "BLACKLIST_BLACK_LIST_ENTRY")
@Entity(name = "blacklist_BlackListEntry")
public class BlackListEntry extends StandardEntity {

    private static final long serialVersionUID = 8486763652386562234L;

    @Column(name = "NICK_NAME")
    protected String nickName;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Column(name = "FIRST_NAME")
    protected String firstName;

    @Column(name = "SECOND_NAME")
    protected String secondName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @Column(name = "DESCRIPTION")
    protected String description;

    @JoinTable(name = "BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK",
            joinColumns = @JoinColumn(name = "BLACKLISTENTRY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    @ManyToMany
    protected List<Contact> contacts;

    @JoinTable(name = "BLACKLIST_BLACK_LIST_ENTRY_PLAYER_IP_LINK",
            joinColumns = @JoinColumn(name = "BLACKLISTENTRY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PLAYERIP_ID"))
    @ManyToMany
    protected List<PlayerIP> playerIps;

    @JoinTable(name = "BLACKLIST_BLACK_LIST_ENTRY_HISTORY_LINK",
            joinColumns = @JoinColumn(name = "BLACKLISTENTRY_ID"),
            inverseJoinColumns = @JoinColumn(name = "HISTORY_ID"))
    @ManyToMany
    protected List<History> histories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCEPTOR_ID")
    protected AcceptorProfile acceptor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOTER_ID")
    protected VoterProfile voter;

    @Column(name = "STATUS")
    protected String status;

    @MetaProperty
    public Mark getMark() {
        DataManager dataManager = AppBeans.get(DataManager.class);
        List<Review> reviews = dataManager.load(Review.class)
                .query("select r " +
                        "from blacklist_Review r " +
                        "where r.entry.id = :id")
                .parameter("id", getUuid())
                .list();
        if(reviews.isEmpty()) return Mark.Neutral;

        int sum = 0;

        for (Review review : reviews) {
            sum += review.getMark().getId();
        }

        return  Mark.fromId(sum / reviews.size());
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<PlayerIP> getPlayerIps() {
        return playerIps;
    }

    public void setPlayerIps(List<PlayerIP> playerIps) {
        this.playerIps = playerIps;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public AcceptorProfile getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(AcceptorProfile acceptor) {
        this.acceptor = acceptor;
    }

    public VoterProfile getVoter() {
        return voter;
    }

    public void setVoter(VoterProfile voter) {
        this.voter = voter;
    }

    public EntryStatus getStatus() {
        return status == null ? null : EntryStatus.fromId(status);
    }

    public void setStatus(EntryStatus status) {
        this.status = status == null ? null : status.getId();
    }
}