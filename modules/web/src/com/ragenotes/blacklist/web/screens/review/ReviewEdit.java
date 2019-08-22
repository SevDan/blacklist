package com.ragenotes.blacklist.web.screens.review;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Review;

@UiController("blacklist_Review.edit")
@UiDescriptor("review-edit.xml")
@EditedEntityContainer("reviewDc")
@LoadDataBeforeShow
public class ReviewEdit extends StandardEditor<Review> {
}