package com.ragenotes.blacklist.web.screens.contact;

import com.haulmont.cuba.gui.components.Form;
import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;

import javax.inject.Named;

@UiController("bl_Contact.edit")
@UiDescriptor("contact-edit.xml")
@EditedEntityContainer("contactDc")
@LoadDataBeforeShow
public class ContactEdit extends StandardEditor<Contact> {

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