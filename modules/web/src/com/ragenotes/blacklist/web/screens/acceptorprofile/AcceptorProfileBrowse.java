package com.ragenotes.blacklist.web.screens.acceptorprofile;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;

@UiController("blacklist_AcceptorProfile.browse")
@UiDescriptor("acceptor-profile-browse.xml")
@LookupComponent("acceptorProfilesTable")
@LoadDataBeforeShow
public class AcceptorProfileBrowse extends StandardLookup<AcceptorProfile> {
}