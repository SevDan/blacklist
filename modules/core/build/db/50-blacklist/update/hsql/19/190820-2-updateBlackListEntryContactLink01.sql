alter table BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK add constraint FK_BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK_ON_CONTACT foreign key (CONTACT_ID) references BLACKLIST_CONTACT(ID);