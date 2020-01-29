package com.ragenotes.blacklist.web.screens.playerip;

import com.haulmont.cuba.gui.components.Form;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.PlayerIP;

import javax.inject.Named;

@UiController("bl_PlayerIP.edit")
@UiDescriptor("player-ip-edit.xml")
@EditedEntityContainer("playerIPDc")
@LoadDataBeforeShow
public class PlayerIPEdit extends StandardEditor<PlayerIP> {

    private boolean readOnly;

    @Named("form")
    private Form form;

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