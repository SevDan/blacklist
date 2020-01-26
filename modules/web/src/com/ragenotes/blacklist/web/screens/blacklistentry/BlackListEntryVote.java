package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.service.CodeGeneratorService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@UiController("bl_BlackListEntryVote.edit")
@UiDescriptor("black-list-entry-vote.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryVote extends StandardEditor<BlackListEntry> {

    @Named("statusField")
    private LookupField<EntryStatus> statusField;
    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private CodeGeneratorService codeGeneratorService;

    @Subscribe
    private void init(InitEvent event) {
        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
                "extUser-full");
        if (user.getVoterProfile() == null) this.closeWithDiscard();

        statusField.setOptionsList(Arrays.asList(EntryStatus.Voting, EntryStatus.Reviewing));
    }

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        if(Strings.isNullOrEmpty(getEditedEntity().getCode())) {
            getEditedEntity().setCode(codeGeneratorService.generateEntryCode(getEditedEntity()));
        }
    }
}