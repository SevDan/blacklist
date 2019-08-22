package com.ragenotes.blacklist.entity.profiles;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Mark;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.History;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "BLACKLIST_REVIEWER_PROFILE")
@Entity(name = "blacklist_ReviewerProfile")
public class ReviewerProfile extends StandardEntity {

    private static final long serialVersionUID = 9202138315757837393L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "CODE", nullable = false, length = 100)
    protected String code;

    @JoinTable(name = "BLACKLIST_REVIEWER_PROFILE_CONTACT_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    @ManyToMany
    protected List<Contact> contacts;

    @JoinTable(name = "BLACKLIST_REVIEWER_PROFILE_REVIEW_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "REVIEW_ID"))
    @ManyToMany
    protected List<Review> reviews = new ArrayList<>();

    @JoinTable(name = "BLACKLIST_REVIEWER_PROFILE_HISTORY_LINK",
            joinColumns = @JoinColumn(name = "PROFILE_ID"),
            inverseJoinColumns = @JoinColumn(name = "HISTORY_ID"))
    @ManyToMany
    protected List<History> histories;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected ExtUser user;

    @MetaProperty
    public Mark getAverageMark() {
        if (reviews == null || reviews.size() == 0) {
            return Mark.Neutral;
        }

        int sum = reviews.stream()
                .mapToInt(r -> r.getMark().getId())
                .sum();

        return Mark.fromId((sum / reviews.size()) % 10);
    }

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public ExtUser getUser() {
        return user;
    }

    public void setUser(ExtUser user) {
        this.user = user;
    }
}