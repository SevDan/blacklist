alter table BLACKLIST_VOTER_PROFILE_CONTACT_LINK add constraint FK_BLACKLIST_VOTER_PROFILE_CONTACT_LINK_ON_CONTACT foreign key (CONTACT_ID) references BLACKLIST_CONTACT(ID);