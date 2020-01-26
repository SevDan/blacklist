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
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.service.ReviewsService;
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

        if((reviewer = reviewsService.getCurrentProfile()) == null) {
            this.closeWithDiscard();
        }

        checkAlreadyReviewed();
        reviewDc.addCollectionChangeListener(e -> {
            checkAlreadyReviewed();
        });

        checkReviewStatus();
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
                    reviewsDl.load();
                    checkAlreadyReviewed();
                    checkReviewStatus();
                })
                .build()
                .show();
    }

    private void checkReviewStatus() {
        List<EntryStatus> availableStatuses = new ArrayList<>(Arrays.asList(EntryStatus.Voting, EntryStatus.Reviewing));
        acceptanceAvailableCheckBox.setValue(false);

        if (reviewsService.availableToAcceptance(getEditedEntity())){
            availableStatuses.add(EntryStatus.Accepting);
            acceptanceAvailableCheckBox.setValue(true);
        }

        statusField.setOptionsList(availableStatuses);
    }

    private void checkAlreadyReviewed() {
        if(reviewsService.getExistsReview(reviewer, getEditedEntity()) != null) {
            createBtn.setEnabled(false);
        }
    }
}