package com.ragenotes.blacklist.web.screens.reviewerprofile;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.service.CodeGeneratorService;

import javax.inject.Inject;

@UiController("bl_ReviewerProfile.edit")
@UiDescriptor("reviewer-profile-edit.xml")
@EditedEntityContainer("reviewerProfileDc")
@LoadDataBeforeShow
public class ReviewerProfileEdit extends StandardEditor<ReviewerProfile> {

    @Inject
    private CodeGeneratorService codeGeneratorService;

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (Strings.isNullOrEmpty(getEditedEntity().getCode())) {
            getEditedEntity().setCode(codeGeneratorService.generateReviewerCode(getEditedEntity()));
        }
    }
}