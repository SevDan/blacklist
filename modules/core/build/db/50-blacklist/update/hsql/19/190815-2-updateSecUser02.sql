alter table SEC_USER add constraint FK_SEC_USER_ON_VOTER_PROFILE foreign key (VOTER_PROFILE_ID) references BLACKLIST_VOTER_PROFILE(ID);
create index IDX_SEC_USER_ON_VOTER_PROFILE on SEC_USER (VOTER_PROFILE_ID);
