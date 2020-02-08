package com.ragenotes.blacklist.web.screens;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.service.NotificationService;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("bl_CustomNotification")
@UiDescriptor("custom-notification.xml")
public class CustomNotification extends Screen {

    @Inject
    private NotificationService notificationService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private MessageBundle messageBundle;

    @Named("sendBtn")
    private Button sendBtn;
    @Named("cancelBtn")
    private Button cancelBtn;
    @Named("textArea")
    private TextArea<String> textArea;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        cancelBtn.addClickListener(e -> {
            closeWithDefaultAction();
        });
        sendBtn.addClickListener(e -> {
            dialogs.createOptionDialog(Dialogs.MessageType.CONFIRMATION)
                    .withCaption(messageBundle.getMessage("caption.notificationConfirmation"))
                    .withMessage(messageBundle.getMessage("message.notificationConfirmation"))
                    .withActions(
                            new DialogAction(DialogAction.Type.YES, true).withHandler(ev -> {
                                notificationService.notifyCustomMessage(textArea.getValue());
                            }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        });
    }
}