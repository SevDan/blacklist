package com.ragenotes.blacklist.web.screens.contact;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;

@UiController("bl_Contact.edit")
@UiDescriptor("contact-edit.xml")
@EditedEntityContainer("contactDc")
@LoadDataBeforeShow
public class ContactEdit extends StandardEditor<Contact> {
}