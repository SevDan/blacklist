package com.ragenotes.blacklist.web.screens.review;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.service.ReviewsService;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("bl_Review.edit")
@UiDescriptor("review-edit.xml")
@EditedEntityContainer("reviewDc")
@LoadDataBeforeShow
public class ReviewEdit extends StandardEditor<Review> {

    @Inject
    private ReviewsService reviewsService;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;

    @Named("form.entryField")
    private PickerField<BlackListEntry> entryField;

    private ReviewerProfile reviewer;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if (getEditedEntity().getEntry() != null) {
            entryField.setEditable(false);
        }

        entryField.addValueChangeListener(e -> {
            if (reviewsService.getExistsReview(reviewer, e.getValue()) != null) {
                entryField.setValue(null);
                alreadyReviewedNotification();
            }
        });

        initBeforeShowPreconditions();
    }

    @Subscribe
    private void onBeforeCommit(BeforeCommitChangesEvent event) {
        getEditedEntity().setAuthor(reviewer);
    }

    protected void initBeforeShowPreconditions() {
        if ((reviewer = reviewsService.getCurrentProfile()) == null) {
            this.closeWithDiscard();
        }

        BlackListEntry currentEntry = entryField.getValue();

        if (currentEntry != null &&
                reviewsService.getExistsReview(reviewer, currentEntry) != null) {
            alreadyReviewedNotification();
            this.closeWithDiscard();
        }
    }

    private void alreadyReviewedNotification() {
        notifications.create(Notifications.NotificationType.WARNING)
                .withCaption(messageBundle.getMessage("notification.alreadyReviewed"))
                .show();
    }
}