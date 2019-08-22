package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@UiController("blacklist_BlackListEntryAcceptance.edit")
@UiDescriptor("black-list-entry-accept.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryAccept extends StandardEditor<BlackListEntry> {

    @Named("statusField")
    private LookupField<EntryStatus> statusField;
    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;

    @Subscribe
    private void init(InitEvent e) {
        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
                "extUser-full");
        if (user.getAcceptorProfile() == null) this.closeWithDiscard();

        statusField.setOptionsList(Arrays.asList(EntryStatus.Accepting,
                EntryStatus.Accepted,
                EntryStatus.Rejected));
    }
}