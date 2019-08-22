package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;

import javax.inject.Inject;

@UiController("blacklist_BlackListEntryVote.edit")
@UiDescriptor("black-list-entry-vote.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryVote extends StandardEditor<BlackListEntry> {
    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;

    @Subscribe
    private void init(InitEvent event) {
        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
                "extUser-full");
        if (user.getVoterProfile() == null) this.closeWithDiscard();
    }
}