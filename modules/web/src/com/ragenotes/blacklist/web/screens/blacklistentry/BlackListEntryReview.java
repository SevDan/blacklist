package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.data.ValueSource;
import com.haulmont.cuba.gui.components.data.value.ContainerValueSource;
import com.haulmont.cuba.gui.components.data.value.DatasourceValueSource;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.entries.History;
import com.ragenotes.blacklist.entity.entries.PlayerIP;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.service.NotificationService;
import com.ragenotes.blacklist.service.ReviewsService;
import com.ragenotes.blacklist.web.screens.contact.ContactEdit;
import com.ragenotes.blacklist.web.screens.history.HistoryEdit;
import com.ragenotes.blacklist.web.screens.playerip.PlayerIPEdit;
import com.ragenotes.blacklist.web.screens.review.ReviewEdit;
import jdk.jfr.Name;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UiController("bl_BlackListEntryReviewing.edit")
@UiDescriptor("black-list-entry-review.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryReview extends StandardEditor<BlackListEntry> {

    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private ReviewsService reviewsService;
    @Inject
    private NotificationService notificationService;

    @Named("statusField")
    private LookupField<EntryStatus> statusField;
    @Named("reviewsDl")
    private CollectionLoader<Review> reviewsDl;
    @Named("createBtn")
    private Button createBtn;
    @Named("reviewsDc")
    private CollectionContainer<Review> reviewDc;
    @Named("acceptanceAvailable")
    private CheckBox acceptanceAvailableCheckBox;
    @Named("form")
    private Form form;
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
    @Named("reviewsTable")
    private Table<Review> reviewsTable;

    private Boolean isAccepted = false;

    private ReviewerProfile reviewer;

    @Subscribe
    private void init(InitEvent e) {
        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
                "extUser-full");
        if (user.getReviewerProfile() == null) this.closeWithDiscard();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        reviewsDl.setParameter("entry", getEditedEntity());
        reviewsDl.load();

        if ((reviewer = reviewsService.getCurrentProfile()) == null) {
            this.closeWithDiscard();
        }

        if (!isAccepted) {
            checkAlreadyReviewed();
            reviewDc.addCollectionChangeListener(e -> {
                checkAlreadyReviewed();
            });

            checkReviewStatus();
        } else {
            form.setEditable(false);
            createBtn.setVisible(false);
        }
    }

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent e) {
        if(getEditedEntity().getStatus() == EntryStatus.Accepting) {
            notificationService.notifyAcceptingReadyEntry(getEditedEntity());
        }
    }

    @Subscribe("reviewsTable.create")
    private void onCreateReview(Action.ActionPerformedEvent event) {
        Review review = metadata.create(Review.class);
        review.setEntry(getEditedEntity());

        screenBuilders.editor(Review.class, this)
                .newEntity(review)
                .withScreenClass(ReviewEdit.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (e.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                        reviewsDl.load();
                        checkAlreadyReviewed();
                        checkReviewStatus();
                    }
                })
                .build()
                .show();
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

    private void checkReviewStatus() {
        List<EntryStatus> availableStatuses = new ArrayList<>(Arrays.asList(EntryStatus.Voting, EntryStatus.Reviewing));
        acceptanceAvailableCheckBox.setValue(false);

        if (reviewsService.availableToAcceptance(getEditedEntity())) {
            if(!acceptanceAvailableCheckBox.getValue()) {
                notificationService.notifyAcceptingReadyEntry(getEditedEntity());
            }
            availableStatuses.add(EntryStatus.Accepting);
            acceptanceAvailableCheckBox.setValue(true);
        }

        statusField.setOptionsList(availableStatuses);
    }

    private void checkAlreadyReviewed() {
        if (reviewsService.getExistsReview(reviewer, getEditedEntity()) != null) {
            createBtn.setEnabled(false);
        }
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}