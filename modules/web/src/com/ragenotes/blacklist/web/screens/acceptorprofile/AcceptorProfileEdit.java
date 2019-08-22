package com.ragenotes.blacklist.web.screens.acceptorprofile;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;

@UiController("blacklist_AcceptorProfile.edit")
@UiDescriptor("acceptor-profile-edit.xml")
@EditedEntityContainer("acceptorProfileDc")
@LoadDataBeforeShow
public class AcceptorProfileEdit extends StandardEditor<AcceptorProfile> {
}