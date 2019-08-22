-- begin BLACKLIST_BLACK_LIST_ENTRY
create table BLACKLIST_BLACK_LIST_ENTRY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NICK_NAME varchar(255),
    FULL_NAME varchar(255),
    FIRST_NAME varchar(255),
    SECOND_NAME varchar(255),
    LAST_NAME varchar(255),
    CODE varchar(100) not null,
    DESCRIPTION varchar(255),
    ACCEPTOR_ID varchar(36),
    VOTER_ID varchar(36),
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end BLACKLIST_BLACK_LIST_ENTRY
-- begin BLACKLIST_REVIEWER_PROFILE
create table BLACKLIST_REVIEWER_PROFILE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    CODE varchar(100) not null,
    USER_ID varchar(36),
    --
    primary key (ID)
)^
-- end BLACKLIST_REVIEWER_PROFILE
-- begin BLACKLIST_REVIEW
create table BLACKLIST_REVIEW (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ENTRY_ID varchar(36),
    DATE_ date,
    CODE varchar(100) not null,
    MARK integer not null,
    --
    primary key (ID)
)^
-- end BLACKLIST_REVIEW
-- begin BLACKLIST_ACCEPTOR_PROFILE
create table BLACKLIST_ACCEPTOR_PROFILE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    CODE varchar(100) not null,
    USER_ID varchar(36),
    --
    primary key (ID)
)^
-- end BLACKLIST_ACCEPTOR_PROFILE
-- begin BLACKLIST_VOTER_PROFILE
create table BLACKLIST_VOTER_PROFILE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    CODE varchar(100) not null,
    USER_ID varchar(36),
    --
    primary key (ID)
)^
-- end BLACKLIST_VOTER_PROFILE

-- begin BLACKLIST_HISTORY
create table BLACKLIST_HISTORY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    CODE varchar(100) not null,
    DESCRIPTION varchar(255),
    DATE_ date,
    --
    primary key (ID)
)^
-- end BLACKLIST_HISTORY
-- begin BLACKLIST_PLAYER_IP
create table BLACKLIST_PLAYER_IP (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IP varchar(255),
    CODE varchar(100) not null,
    FIXATION_DATE timestamp,
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end BLACKLIST_PLAYER_IP
-- begin BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK
create table BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK (
    BLACKLISTENTRY_ID varchar(36) not null,
    CONTACT_ID varchar(36) not null,
    primary key (BLACKLISTENTRY_ID, CONTACT_ID)
)^
-- end BLACKLIST_BLACK_LIST_ENTRY_CONTACT_LINK
-- begin BLACKLIST_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK
create table BLACKLIST_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK (
    PROFILE_ID varchar(36) not null,
    ENTRY_ID varchar(36) not null,
    primary key (PROFILE_ID, ENTRY_ID)
)^
-- end BLACKLIST_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK
-- begin BLACKLIST_VOTER_PROFILE_CONTACT_LINK
create table BLACKLIST_VOTER_PROFILE_CONTACT_LINK (
    ROFILE_ID varchar(36) not null,
    CONTACT_ID varchar(36) not null,
    primary key (ROFILE_ID, CONTACT_ID)
)^
-- end BLACKLIST_VOTER_PROFILE_CONTACT_LINK
-- begin BLACKLIST_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK
create table BLACKLIST_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK (
    PROFILE_ID varchar(36) not null,
    ENTRY_ID varchar(36) not null,
    primary key (PROFILE_ID, ENTRY_ID)
)^
-- end BLACKLIST_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK
-- begin BLACKLIST_BLACK_LIST_ENTRY_HISTORY_LINK
create table BLACKLIST_BLACK_LIST_ENTRY_HISTORY_LINK (
    BLACKLISTENTRY_ID varchar(36) not null,
    HISTORY_ID varchar(36) not null,
    primary key (BLACKLISTENTRY_ID, HISTORY_ID)
)^
-- end BLACKLIST_BLACK_LIST_ENTRY_HISTORY_LINK
-- begin BLACKLIST_REVIEWER_PROFILE_REVIEW_LINK
create table BLACKLIST_REVIEWER_PROFILE_REVIEW_LINK (
    PROFILE_ID varchar(36) not null,
    REVIEW_ID varchar(36) not null,
    primary key (PROFILE_ID, REVIEW_ID)
)^
-- end BLACKLIST_REVIEWER_PROFILE_REVIEW_LINK
-- begin BLACKLIST_BLACK_LIST_ENTRY_PLAYER_IP_LINK
create table BLACKLIST_BLACK_LIST_ENTRY_PLAYER_IP_LINK (
    BLACKLISTENTRY_ID varchar(36) not null,
    PLAYERIP_ID varchar(36) not null,
    primary key (BLACKLISTENTRY_ID, PLAYERIP_ID)
)^
-- end BLACKLIST_BLACK_LIST_ENTRY_PLAYER_IP_LINK

-- begin BLACKLIST_REVIEWER_PROFILE_HISTORY_LINK
create table BLACKLIST_REVIEWER_PROFILE_HISTORY_LINK (
    PROFILE_ID varchar(36) not null,
    HISTORY_ID varchar(36) not null,
    primary key (PROFILE_ID, HISTORY_ID)
)^
-- end BLACKLIST_REVIEWER_PROFILE_HISTORY_LINK
-- begin BLACKLIST_ACCEPTOR_PROFILE_CONTACT_LINK
create table BLACKLIST_ACCEPTOR_PROFILE_CONTACT_LINK (
    PROFILE_ID varchar(36) not null,
    CONTACT_ID varchar(36) not null,
    primary key (PROFILE_ID, CONTACT_ID)
)^
-- end BLACKLIST_ACCEPTOR_PROFILE_CONTACT_LINK
-- begin BLACKLIST_REVIEWER_PROFILE_CONTACT_LINK
create table BLACKLIST_REVIEWER_PROFILE_CONTACT_LINK (
    PROFILE_ID varchar(36) not null,
    CONTACT_ID varchar(36) not null,
    primary key (PROFILE_ID, CONTACT_ID)
)^
-- end BLACKLIST_REVIEWER_PROFILE_CONTACT_LINK
-- begin SEC_USER
alter table SEC_USER add column ACCEPTOR_PROFILE_ID varchar(36) ^
alter table SEC_USER add column REVIEWER_PROFILE_ID varchar(36) ^
alter table SEC_USER add column VOTER_PROFILE_ID varchar(36) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'blacklist_ExtUser' where DTYPE is null ^
-- end SEC_USER
-- begin BLACKLIST_CONTACT
create table BLACKLIST_CONTACT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    VALUE varchar(255),
    TYPE_ varchar(50),
    CODE varchar(100) not null,
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end BLACKLIST_CONTACT
