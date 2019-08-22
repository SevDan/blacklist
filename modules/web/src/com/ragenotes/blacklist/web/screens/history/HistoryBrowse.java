package com.ragenotes.blacklist.web.screens.history;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.History;

@UiController("blacklist_History.browse")
@UiDescriptor("history-browse.xml")
@LookupComponent("historiesTable")
@LoadDataBeforeShow
public class HistoryBrowse extends StandardLookup<History> {
}