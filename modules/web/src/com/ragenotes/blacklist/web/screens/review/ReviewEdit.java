package com.ragenotes.blacklist.web.screens.review;

import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Form;
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
    @Inject
    private TimeSource timeSource;

    @Named("form.entryField")
    private PickerField<BlackListEntry> entryField;
    @Named("form")
    private Form form;

    private ReviewerProfile reviewer;

    private boolean readOnly;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if (readOnly) {
            form.setEditable(false);
            return;
        }

        if (getEditedEntity().getEntry() != null) {
            entryField.setEditable(false);
        }

        entryField.addValueChangeListener(e -> {
            if (reviewsService.getExistsReview(reviewer, e.getValue()) != null) {
                entryField.setValue(null);
                alreadyReviewedNotification();
            }
        });

        getEditedEntity().setDate(timeSource.currentTimestamp());

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

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}