package com.ragenotes.blacklist.web.screens.review;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Review;

@UiController("blacklist_Review.browse")
@UiDescriptor("review-browse.xml")
@LookupComponent("reviewsTable")
@LoadDataBeforeShow
public class ReviewBrowse extends StandardLookup<Review> {
}