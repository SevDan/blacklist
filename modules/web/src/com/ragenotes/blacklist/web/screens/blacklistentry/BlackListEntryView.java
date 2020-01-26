package com.ragenotes.blacklist.web.screens.blacklistentry;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExtUser;
import com.ragenotes.blacklist.entity.Review;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@UiController("bl_BlackListEntryView.edit")
@UiDescriptor("black-list-entry-view.xml")
@EditedEntityContainer("blackListEntryDc")
@LoadDataBeforeShow
public class BlackListEntryView extends StandardEditor<BlackListEntry> {

    @Named("reviewsDl")
    private CollectionLoader<Review> reviewsDl;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        reviewsDl.setParameter("entry", getEditedEntity());
        reviewsDl.load();
    }
}