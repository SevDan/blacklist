package com.ragenotes.blacklist.web.screens.playerip;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.PlayerIP;

@UiController("blacklist_PlayerIP.edit")
@UiDescriptor("player-ip-edit.xml")
@EditedEntityContainer("playerIPDc")
@LoadDataBeforeShow
public class PlayerIPEdit extends StandardEditor<PlayerIP> {
}