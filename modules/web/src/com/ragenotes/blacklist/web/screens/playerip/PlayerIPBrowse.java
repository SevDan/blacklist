package com.ragenotes.blacklist.web.screens.playerip;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.entries.PlayerIP;

@UiController("blacklist_PlayerIP.browse")
@UiDescriptor("player-ip-browse.xml")
@LookupComponent("playerIPsTable")
@LoadDataBeforeShow
public class PlayerIPBrowse extends StandardLookup<PlayerIP> {
}