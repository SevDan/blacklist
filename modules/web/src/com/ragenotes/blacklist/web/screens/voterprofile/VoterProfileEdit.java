package com.ragenotes.blacklist.web.screens.voterprofile;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;
import com.ragenotes.blacklist.service.CodeGeneratorService;

import javax.inject.Inject;

@UiController("bl_VoterProfile.edit")
@UiDescriptor("voter-profile-edit.xml")
@EditedEntityContainer("voterProfileDc")
@LoadDataBeforeShow
public class VoterProfileEdit extends StandardEditor<VoterProfile> {

    @Inject
    private CodeGeneratorService codeGeneratorService;

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (Strings.isNullOrEmpty(getEditedEntity().getCode())) {
            getEditedEntity().setCode(codeGeneratorService.generateVoterCode(getEditedEntity()));
        }
    }
}