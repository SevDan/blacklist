package com.ragenotes.blacklist.web.screens.reviewerprofile;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;

@UiController("blacklist_ReviewerProfile.browse")
@UiDescriptor("reviewer-profile-browse.xml")
@LookupComponent("reviewerProfilesTable")
@LoadDataBeforeShow
public class ReviewerProfileBrowse extends StandardLookup<ReviewerProfile> {
}