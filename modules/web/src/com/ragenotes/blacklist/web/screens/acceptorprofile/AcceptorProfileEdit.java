package com.ragenotes.blacklist.web.screens.acceptorprofile;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.service.CodeGeneratorService;

import javax.inject.Inject;

@UiController("bl_AcceptorProfile.edit")
@UiDescriptor("acceptor-profile-edit.xml")
@EditedEntityContainer("acceptorProfileDc")
@LoadDataBeforeShow
public class AcceptorProfileEdit extends StandardEditor<AcceptorProfile> {

    @Inject
    private CodeGeneratorService codeGeneratorService;

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (Strings.isNullOrEmpty(getEditedEntity().getCode())) {
            getEditedEntity().setCode(codeGeneratorService.generateAcceptorCode(getEditedEntity()));
        }
    }
}