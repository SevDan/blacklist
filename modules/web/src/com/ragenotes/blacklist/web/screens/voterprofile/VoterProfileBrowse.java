package com.ragenotes.blacklist.web.screens.voterprofile;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

@UiController("blacklist_VoterProfile.browse")
@UiDescriptor("voter-profile-browse.xml")
@LookupComponent("voterProfilesTable")
@LoadDataBeforeShow
public class VoterProfileBrowse extends StandardLookup<VoterProfile> {
}