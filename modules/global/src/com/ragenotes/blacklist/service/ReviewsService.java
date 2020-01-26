package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import jdk.internal.jline.internal.Nullable;

public interface ReviewsService {
    String NAME = "bl_ReviewsService";

    @Nullable
    ReviewerProfile getCurrentProfile();

    @Nullable
    Review getExistsReview(ReviewerProfile profile, BlackListEntry entry);

    Boolean availableToAcceptance(BlackListEntry entry);

    Integer getReviewsCount(BlackListEntry entry);
}