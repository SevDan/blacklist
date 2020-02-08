package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;
import com.ragenotes.blacklist.service.NotificationService;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("bl_BlackListEntry.browse")
@UiDescriptor("black-list-entry-browse.xml")
@LoadDataBeforeShow
@LookupComponent("blackListEntriesTableAccepted")
public class BlackListEntryBrowse extends StandardLookup<BlackListEntry> {

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private NotificationService notificationService;
    @Inject
    private UserSession userSession;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private MessageBundle messageBundle;

    @Named("blackListEntriesVoteDl")
    private CollectionLoader<BlackListEntry> blackListEntriesVoteDl;
    @Named("blackListEntriesReviewDl")
    private CollectionLoader<BlackListEntry> blackListEntriesReviewDl;
    @Named("blackListEntriesAcceptDl")
    private CollectionLoader<BlackListEntry> blackListEntriesAcceptDl;
    @Named("blackListEntriesRejectedDl")
    private CollectionLoader<BlackListEntry> blackListEntriesRejectedDl;
    @Named("blackListEntriesAcceptedDl")
    private CollectionLoader<BlackListEntry> blackListEntriesAcceptedDl;
    @Named("blackListEntriesTableVote")
    private GroupTable<BlackListEntry> blackListEntriesTableVoting;
    @Named("blackListEntriesTableReview")
    private GroupTable<BlackListEntry> blackListEntriesTableReviewing;
    @Named("blackListEntriesTableAccept")
    private GroupTable<BlackListEntry> blackListEntriesTableAccept;
    @Named("blackListEntriesTableAccepted")
    private GroupTable<BlackListEntry> blackListEntriesTableAccepted;
    @Named("blackListEntriesTableReject")
    private GroupTable<BlackListEntry> blackListEntriesTableReject;

    // buttons
    @Named("deleteBtnReject")
    private Button deleteBtnReject;
    @Named("editBtnReject")
    private Button editBtnReject;
    @Named("editBtnAccept")
    private Button editBtnAccept;
    @Named("editBtnReview")
    private Button editBtnReview;
    @Named("createBtnVote")
    private Button createBtnVote;
    @Named("editBtnVote")
    private Button editBtnVote;

    @Subscribe
    public void init(BeforeShowEvent event) {
        blackListEntriesVoteDl.setParameter("vote_status", EntryStatus.Voting);
        blackListEntriesReviewDl.setParameter("review_status", EntryStatus.Reviewing);
        blackListEntriesAcceptDl.setParameter("accept_status", EntryStatus.Accepting);
        blackListEntriesRejectedDl.setParameter("rejected_status", EntryStatus.Rejected);
        blackListEntriesAcceptedDl.setParameter("accepted_status", EntryStatus.Accepted);

        ExtUser user = dataManager.reload((ExtUser) userSession.getCurrentOrSubstitutedUser(),
                "extUser-full");

        AcceptorProfile acceptor = user.getAcceptorProfile();
        VoterProfile voter = user.getVoterProfile();
        ReviewerProfile reviewer = user.getReviewerProfile();

        editBtnAccept.setEnabled(acceptor != null);

        editBtnReview.setEnabled(reviewer != null);

        deleteBtnReject.setEnabled(voter != null);
        editBtnReject.setEnabled(voter != null);
        createBtnVote.setEnabled(voter != null);
        editBtnVote.setEnabled(voter != null);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        blackListEntriesTableReviewing.addGeneratedColumn("marked", e -> {
            Integer count = dataManager.loadValue("select count(r)" +
                    "from bl_Review r " +
                    "where r.entry = :entry and r.author.user = :user", Integer.class)
                    .parameter("entry", e)
                    .parameter("user", userSession.getCurrentOrSubstitutedUser())
                    .optional().orElse(0);
            Label label = uiComponents.create(Label.class);

            if(count == 0) {
                label.setValue(messageBundle.getMessage("no"));
            } else {
                label.setValue(messageBundle.getMessage("yes"));
            }
            return label;
        });
    }

    @Subscribe("blackListEntriesTableVote.create")
    public void onCreateVoteEntry(CreateAction.ActionPerformedEvent event) {
        BlackListEntry entry = metadata.create(BlackListEntry.class);
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if (currentUser.getVoterProfile() == null || currentUser.getVoterProfile().getCode() == null) return;
        entry.setVoter(currentUser.getVoterProfile());
        entry.setStatus(EntryStatus.Voting);

        screenBuilders.editor(BlackListEntry.class, this)
                .newEntity(entry)
                .withScreenClass(BlackListEntryVote.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .withAfterCloseListener(e -> {
                    if(e.getCloseAction() == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                        notificationService.notifyNewEntry(e.getSource().getEditedEntity());
                    }
                })
                .build()
                .show();
    }

    @Subscribe("blackListEntriesTableVote.edit")
    public void onEditVoteEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableVoting.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if (entry == null) return;

        VoterProfile entryVoter = entry.getVoter();
        VoterProfile currentVoter = currentUser.getVoterProfile();
        ReviewerProfile currentReviewer = currentUser.getReviewerProfile();

        if (currentVoter != null && entryVoter.getId().equals(currentVoter.getId())) {
            screenBuilders.editor(BlackListEntry.class, this)
                    .withScreenClass(BlackListEntryVote.class)
                    .editEntity(entry)
                    .build()
                    .show();
        } else if (currentReviewer != null && currentReviewer.getCode() != null) {
            screenBuilders.editor(BlackListEntry.class, this)
                    .withScreenClass(BlackListEntryReview.class)
                    .editEntity(entry)
                    .build()
                    .show();
        }
    }

    @Subscribe("blackListEntriesTableReview.review")
    public void onEditReviewEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableReviewing.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if (entry == null) return;

        ReviewerProfile currentReviewer = currentUser.getReviewerProfile();
        if (currentReviewer == null) return;

        screenBuilders.editor(BlackListEntry.class, this)
                .withScreenClass(BlackListEntryReview.class)
                .editEntity(entry)
                .build()
                .show();
    }

    @Subscribe("blackListEntriesTableReject.revote")
    public void onRevoteRejectEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableReject.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if (entry == null) return;

        VoterProfile currentVoter = currentUser.getVoterProfile();
        if (currentVoter == null) return;

        entry.setStatus(EntryStatus.Voting);

        screenBuilders.editor(BlackListEntry.class, this)
                .withScreenClass(BlackListEntryVote.class)
                .editEntity(entry)
                .withAfterCloseListener(e -> {
                    if(e.getCloseAction() == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                        notificationService.notifyNewEntry(e.getSource().getEditedEntity());
                    }
                })
                .build()
                .show();
    }

    @Subscribe("blackListEntriesTableAccept.edit")
    public void onAcceptEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableAccept.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if (entry == null) return;

        AcceptorProfile currentAcceptor = currentUser.getAcceptorProfile();
        if (currentAcceptor == null) return;

        BlackListEntryAccept editor = screenBuilders.editor(BlackListEntry.class, this)
                .withScreenClass(BlackListEntryAccept.class)
                .editEntity(entry)
                .build();

        editor.setAcceptorProfile(currentAcceptor);
        editor.show();
    }

    @Subscribe("blackListEntriesTableAccepted.edit")
    public void onAcceptedEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableAccepted.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) userSession
                .getCurrentOrSubstitutedUser(), "extUser-full");
        if(entry == null) return;

        ReviewerProfile reviewerProfile = currentUser.getReviewerProfile();
        if(reviewerProfile == null) {
            screenBuilders.editor(BlackListEntry.class, this)
                    .withScreenClass(BlackListEntryView.class)
                    .editEntity(entry)
                    .build()
                    .show();
        } else {
            BlackListEntryReview editScreen = screenBuilders.editor(BlackListEntry.class, this)
                    .withScreenClass(BlackListEntryReview.class)
                    .editEntity(entry)
                    .build();

            editScreen.setAccepted(true);

            editScreen.show();
        }
    }
}