package com.ragenotes.blacklist.web.screens.history;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.History;

@UiController("bl_History.edit")
@UiDescriptor("history-edit.xml")
@EditedEntityContainer("historyDc")
@LoadDataBeforeShow
public class HistoryEdit extends StandardEditor<History> {
}