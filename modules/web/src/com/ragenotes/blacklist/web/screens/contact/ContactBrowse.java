package com.ragenotes.blacklist.web.screens.contact;

import com.haulmont.cuba.gui.screen.*;
import com.ragenotes.blacklist.entity.Contact;

@UiController("blacklist_Contact.browse")
@UiDescriptor("contact-browse.xml")
@LookupComponent("contactsTable")
@LoadDataBeforeShow
public class ContactBrowse extends StandardLookup<Contact> {
}