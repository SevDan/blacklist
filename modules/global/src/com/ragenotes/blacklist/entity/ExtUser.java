package com.ragenotes.blacklist.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "blacklist_ExtUser")
@Extends(User.class)
public class ExtUser extends User {

    private static final long serialVersionUID = 3286825261717285099L;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(name = "ACCEPTOR_PROFILE_ID")
    protected AcceptorProfile acceptorProfile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(name = "REVIEWER_PROFILE_ID")
    protected ReviewerProfile reviewerProfile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(name = "VOTER_PROFILE_ID")
    protected VoterProfile voterProfile;

    public AcceptorProfile getAcceptorProfile() {
        return acceptorProfile;
    }

    public void setAcceptorProfile(AcceptorProfile acceptorProfile) {
        this.acceptorProfile = acceptorProfile;
    }

    public ReviewerProfile getReviewerProfile() {
        return reviewerProfile;
    }

    public void setReviewerProfile(ReviewerProfile reviewerProfile) {
        this.reviewerProfile = reviewerProfile;
    }

    public VoterProfile getVoterProfile() {
        return voterProfile;
    }

    public void setVoterProfile(VoterProfile voterProfile) {
        this.voterProfile = voterProfile;
    }
}