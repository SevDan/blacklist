package com.ragenotes.blacklist.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.ragenotes.blacklist.config.EntriesProcessingConfig;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import jdk.internal.jline.internal.Nullable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(ReviewsService.NAME)
public class ReviewsServiceBean implements ReviewsService {

    @Inject
    private DataManager dataManager;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private EntriesProcessingConfig entriesProcessingConfig;

    @Nullable
    @Override
    public ReviewerProfile getCurrentProfile() {
        return dataManager.load(ReviewerProfile.class)
                .query("select pr " +
                        "from bl_ReviewerProfile pr " +
                        "where pr.user = :currentUser")
                .parameter("currentUser", userSessionSource.getUserSession()
                        .getCurrentOrSubstitutedUser())
                .optional().orElse(null);
    }

    @Nullable
    @Override
    public Review getExistsReview(ReviewerProfile profile, BlackListEntry entry) {
        return dataManager.load(Review.class)
                .query("select r " +
                        "from bl_Review r " +
                        "where r.entry = :entry " +
                        "and r.author = :authorProfile")
                .parameter("entry", entry)
                .parameter("authorProfile", profile)
                .optional().orElse(null);
    }

    @Override
    public Boolean availableToAcceptance(BlackListEntry entry) {
        return getReviewsCount(entry) >= entriesProcessingConfig.getReviewingQuorum()
                && entry.getMark() > 0;
    }

    @Override
    public Integer getReviewsCount(BlackListEntry entry) {
        return dataManager.loadValue("select count(r) " +
                "from bl_Review r " +
                "where r.entry = :entry", Integer.class)
                .parameter("entry", entry)
                .optional().orElse(0);
    }
}