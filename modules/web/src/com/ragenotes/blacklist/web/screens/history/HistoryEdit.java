package com.ragenotes.blacklist.web.screens.history;

import com.haulmont.cuba.gui.components.Form;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.History;

import javax.inject.Named;

@UiController("bl_History.edit")
@UiDescriptor("history-edit.xml")
@EditedEntityContainer("historyDc")
@LoadDataBeforeShow
public class HistoryEdit extends StandardEditor<History> {

    @Named("form")
    private Form form;

    private boolean readOnly = false;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if(readOnly) {
            form.setEditable(false);
        }
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}