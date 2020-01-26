package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("bl_BlackListEntry.browse")
@UiDescriptor("black-list-entry-browse.xml")
@LoadDataBeforeShow
public class BlackListEntryBrowse extends StandardLookup<BlackListEntry> {

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSessionSource sessionSource;
    @Inject
    private DataManager dataManager;

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

        ExtUser user = dataManager.reload((ExtUser) sessionSource.getUserSession().getCurrentOrSubstitutedUser(),
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

    @Subscribe("blackListEntriesTableVote.create")
    public void onCreateVoteEntry(CreateAction.ActionPerformedEvent event) {
        BlackListEntry entry = metadata.create(BlackListEntry.class);
        ExtUser currentUser = dataManager.reload((ExtUser) sessionSource.getUserSession().getUser(), "extUser-full");
        if (currentUser.getVoterProfile() == null || currentUser.getVoterProfile().getCode() == null) return;
        entry.setVoter(currentUser.getVoterProfile());
        entry.setStatus(EntryStatus.Voting);

        screenBuilders.editor(BlackListEntry.class, this)
                .newEntity(entry)
                .withScreenClass(BlackListEntryVote.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build()
                .show();
    }

    @Subscribe("blackListEntriesTableVote.edit")
    public void onEditVoteEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableVoting.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) sessionSource.getUserSession().getUser(), "extUser-full");
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
        ExtUser currentUser = dataManager.reload((ExtUser) sessionSource.getUserSession().getUser(), "extUser-full");
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
        ExtUser currentUser = dataManager.reload((ExtUser) sessionSource.getUserSession().getUser(), "extUser-full");
        if (entry == null) return;

        VoterProfile currentVoter = currentUser.getVoterProfile();
        if (currentVoter == null) return;

        entry.setStatus(EntryStatus.Voting);

        screenBuilders.editor(BlackListEntry.class, this)
                .withScreenClass(BlackListEntryVote.class)
                .editEntity(entry)
                .build()
                .show();
    }

    @Subscribe("blackListEntriesTableAccept.edit")
    public void onAcceptEntry(EditAction.ActionPerformedEvent event) {
        BlackListEntry entry = blackListEntriesTableAccept.getSingleSelected();
        ExtUser currentUser = dataManager.reload((ExtUser) sessionSource.getUserSession().getUser(), "extUser-full");
        if (entry == null) return;

        AcceptorProfile currentAcceptor = currentUser.getAcceptorProfile();
        if (currentAcceptor == null) return;

        screenBuilders.editor(BlackListEntry.class, this)
                .withScreenClass(BlackListEntryAccept.class)
                .editEntity(entry)
                .build()
                .show();
    }
}