package com.ragenotes.blacklist.web.screens.voterprofile;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("blacklist_VoterProfile.edit")
@UiDescriptor("voter-profile-edit.xml")
@EditedEntityContainer("voterProfileDc")
@LoadDataBeforeShow
public class VoterProfileEdit extends StandardEditor<VoterProfile> {
}