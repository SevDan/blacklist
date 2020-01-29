package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.entries.History;
import com.ragenotes.blacklist.entity.entries.PlayerIP;
import com.ragenotes.blacklist.service.CodeGeneratorService;
import com.ragenotes.blacklist.web.screens.contact.ContactEdit;
import com.ragenotes.blacklist.web.screens.history.HistoryEdit;
import com.ragenotes.blacklist.web.screens.playerip.PlayerIPEdit;
import com.ragenotes.blacklist.web.screens.review.ReviewEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@UiController("bl_BlackListEntryVote.edit")
@UiDescriptor("black-list-entry-vote.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryVote extends StandardEditor<BlackListEntry> {

    private static final String NUMBERS_DOMAIN = "blackListEntrynumber";

    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private CodeGeneratorService codeGeneratorService;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Named("statusField")
    private LookupField<EntryStatus> statusField;
    @Named("historiesDc")
    private CollectionContainer<History> historiesDc;
    @Named("playerIpsDc")
    private CollectionContainer<PlayerIP> playerIpsDc;
    @Named("contactsDc")
    private CollectionContainer<Contact> contactsDc;

    @Named("contactsTable")
    private Table<Contact> contactsTable;
    @Named("playerIpsTable")
    private Table<PlayerIP> playerIPsTable;
    @Named("historiesTable")
    private Table<History> historiesTable;

    @Subscribe
    private void init(InitEvent event) {
        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
                "extUser-full");
        if (user.getVoterProfile() == null) this.closeWithDiscard();

        statusField.setOptionsList(Arrays.asList(EntryStatus.Voting, EntryStatus.Reviewing));
    }

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (Strings.isNullOrEmpty(getEditedEntity().getCode())) {
            getEditedEntity().setCode(codeGeneratorService.generateEntryCode(getEditedEntity()));
        }

        if (PersistenceHelper.isNew(getEditedEntity())) {
            getEditedEntity().setNumber(uniqueNumbersService.getNextNumber(NUMBERS_DOMAIN));
        }
    }

    @Subscribe("historiesTable.add")
    private void onHistoriesAdd(Action.ActionPerformedEvent event) {
        screenBuilders.editor(History.class, this)
                .newEntity()
                .withScreenClass(HistoryEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (WINDOW_COMMIT_AND_CLOSE_ACTION.equals(e.getCloseAction())) {
                        historiesDc.getMutableItems().add(e.getSource().getEditedEntity());
                    }
                })
                .show();
    }

    @Subscribe("playerIpsTable.add")
    private void onPlayerIPAdd(Action.ActionPerformedEvent event) {
        screenBuilders.editor(PlayerIP.class, this)
                .newEntity()
                .withScreenClass(PlayerIPEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (WINDOW_COMMIT_AND_CLOSE_ACTION.equals(e.getCloseAction())) {
                        playerIpsDc.getMutableItems().add(e.getSource().getEditedEntity());
                    }
                })
                .show();
    }

    @Subscribe("contactsTable.add")
    private void onContactAdd(Action.ActionPerformedEvent event) {
        screenBuilders.editor(Contact.class, this)
                .newEntity()
                .withScreenClass(ContactEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (WINDOW_COMMIT_AND_CLOSE_ACTION.equals(e.getCloseAction())) {
                        contactsDc.getMutableItems().add(e.getSource().getEditedEntity());
                    }
                })
                .build()
                .show();
    }

    @Subscribe("contactsTable.details")
    private void onContactDetails(Action.ActionPerformedEvent event) {
        Contact selected = contactsTable.getSingleSelected();
        if (selected == null) return;

        ContactEdit editor = screenBuilders.editor(Contact.class, this)
                .editEntity(selected)
                .withScreenClass(ContactEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build();

        editor.setReadOnly(true);

        editor.show();
    }

    @Subscribe("historiesTable.details")
    private void onHistoryDetails(Action.ActionPerformedEvent event) {
        History selected = historiesTable.getSingleSelected();
        if(selected == null) return;

        HistoryEdit editor = screenBuilders.editor(History.class, this)
                .editEntity(selected)
                .withScreenClass(HistoryEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build();

        editor.setReadOnly(true);

        editor.show();
    }

    @Subscribe("playerIpsTable.details")
    private void onPlayerIPDetails(Action.ActionPerformedEvent event) {
        PlayerIP selected = playerIPsTable.getSingleSelected();
        if(selected == null) return;

        PlayerIPEdit editor = screenBuilders.editor(PlayerIP.class, this)
                .editEntity(selected)
                .withScreenClass(PlayerIPEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build();

        editor.setReadOnly(true);

        editor.show();
    }
}