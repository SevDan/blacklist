package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.entries.History;
import com.ragenotes.blacklist.entity.entries.PlayerIP;
import com.ragenotes.blacklist.web.screens.contact.ContactEdit;
import com.ragenotes.blacklist.web.screens.history.HistoryEdit;
import com.ragenotes.blacklist.web.screens.playerip.PlayerIPEdit;
import com.ragenotes.blacklist.web.screens.review.ReviewEdit;
import jdk.jfr.internal.Options;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@UiController("bl_BlackListEntryView.edit")
@UiDescriptor("black-list-entry-view.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryView extends StandardEditor<BlackListEntry> {

    @Inject
    private ScreenBuilders screenBuilders;

    @Named("reviewsDl")
    private CollectionLoader<Review> reviewsDl;

    @Named("contactsTable")
    private Table<Contact> contactsTable;
    @Named("playerIpsTable")
    private Table<PlayerIP> playerIPsTable;
    @Named("historiesTable")
    private Table<History> historiesTable;
    @Named("reviewsTable")
    private Table<Review> reviewsTable;


    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        reviewsDl.setParameter("entry", getEditedEntity());
        reviewsDl.load();
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
        if (selected == null) return;

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
        if (selected == null) return;

        PlayerIPEdit editor = screenBuilders.editor(PlayerIP.class, this)
                .editEntity(selected)
                .withScreenClass(PlayerIPEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build();

        editor.setReadOnly(true);

        editor.show();
    }

    @Subscribe("reviewsTable.details")
    private void onReviewDetails(Action.ActionPerformedEvent event) {
        Review selected = reviewsTable.getSingleSelected();
        if (selected == null) return;

        ReviewEdit editor = screenBuilders.editor(Review.class, this)
                .editEntity(selected)
                .withScreenClass(ReviewEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build();

        editor.setReadOnly(true);

        editor.show();
    }
}