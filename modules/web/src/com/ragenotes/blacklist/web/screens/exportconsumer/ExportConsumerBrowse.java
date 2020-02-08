package com.ragenotes.blacklist.web.screens.exportconsumer;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.ExportConsumer;
import com.ragenotes.blacklist.entity.ExportParam;
import com.ragenotes.blacklist.service.NotificationService;
import com.ragenotes.blacklist.web.screens.exportparam.ExportParamEdit;
import jdk.jfr.Name;
import org.springframework.security.core.parameters.P;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@UiController("bl_ExportConsumer.browse")
@UiDescriptor("export-consumer-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class ExportConsumerBrowse extends MasterDetailScreen<ExportConsumer> {

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private Dialogs dialogs;
    @Inject
    private NotificationService notificationService;
    @Inject
    private MessageBundle messageBundle;

    @Named("paramsDc")
    private CollectionContainer<ExportParam> exportParamDc;
    @Named("table")
    private GroupTable<ExportConsumer> consumersTable;

    @Subscribe("paramsTable.create")
    public void onCreateParameter(Action.ActionPerformedEvent event) {
        if (!creating) {
            ExportParam exportParam = metadata.create(ExportParam.class);
            exportParam.setConsumer(getEditContainer().getItem());

            screenBuilders.editor(ExportParam.class, this)
                    .newEntity(exportParam)
                    .withScreenClass(ExportParamEdit.class)
                    .withLaunchMode(OpenMode.DIALOG)
                    .withAfterCloseListener(e -> {
                        if (e.getCloseAction().equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                            exportParamDc.getMutableItems().add(e.getSource().getEditedEntity());
                        }
                    })
                    .show();
        }
    }

    @Subscribe("table.vkDialogs")
    public void printVkDialogs(Action.ActionPerformedEvent event) {
        ExportConsumer exportConsumer = consumersTable.getSingleSelected();
        if (exportConsumer == null) return;

        List<Integer> vkConversations = notificationService.getVkConversations(exportConsumer);


        if (!vkConversations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for(Integer dialog : vkConversations) {
                String dialogString = String.valueOf(dialog);
                message.append(dialogString.startsWith("20000") ? "Group: " : "User: ")
                        .append(dialogString)
                        .append("\n");
            }

            dialogs.createMessageDialog()
                    .withCaption(messageBundle.getMessage("caption.dialogs"))
                    .withMessage(message.toString())
                    .show();
        }
    }
}