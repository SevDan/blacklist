package com.ragenotes.blacklist.web.screens.reviewerprofile;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;

@UiController("blacklist_ReviewerProfile.edit")
@UiDescriptor("reviewer-profile-edit.xml")
@EditedEntityContainer("reviewerProfileDc")
@LoadDataBeforeShow
public class ReviewerProfileEdit extends StandardEditor<ReviewerProfile> {
}