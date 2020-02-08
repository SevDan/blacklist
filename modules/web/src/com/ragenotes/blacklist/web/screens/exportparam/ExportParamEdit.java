package com.ragenotes.blacklist.web.screens.exportparam;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExportParam;

@UiController("bl_ExportParam.edit")
@UiDescriptor("export-param-edit.xml")
@EditedEntityContainer("exportParamDc")
@LoadDataBeforeShow
public class ExportParamEdit extends StandardEditor<ExportParam> {
}