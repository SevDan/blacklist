-- begin BL_BLACK_LIST_ENTRY
create table BL_BLACK_LIST_ENTRY (
    ID uuid,
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
    ACCEPTOR_ID uuid,
    VOTER_ID uuid,
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end BL_BLACK_LIST_ENTRY
-- begin BL_REVIEWER_PROFILE
create table BL_REVIEWER_PROFILE (
    ID uuid,
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
    USER_ID uuid,
    --
    primary key (ID)
)^
-- end BL_REVIEWER_PROFILE
-- begin BL_REVIEW
create table BL_REVIEW (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ENTRY_ID uuid not null,
    DATE_ date,
    MARK integer not null,
    PROFILE_ID uuid,
    --
    primary key (ID)
)^
-- end BL_REVIEW
-- begin BL_ACCEPTOR_PROFILE
create table BL_ACCEPTOR_PROFILE (
    ID uuid,
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
    USER_ID uuid,
    --
    primary key (ID)
)^
-- end BL_ACCEPTOR_PROFILE
-- begin BL_VOTER_PROFILE
create table BL_VOTER_PROFILE (
    ID uuid,
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
    USER_ID uuid,
    --
    primary key (ID)
)^
-- end BL_VOTER_PROFILE
-- begin BL_CONTACT
create table BL_CONTACT (
    ID uuid,
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
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end BL_CONTACT
-- begin BL_HISTORY
create table BL_HISTORY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    DESCRIPTION varchar(255),
    DATE_ date,
    --
    primary key (ID)
)^
-- end BL_HISTORY
-- begin BL_PLAYER_IP
create table BL_PLAYER_IP (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IP varchar(255),
    FIXATION_DATE timestamp,
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end BL_PLAYER_IP
-- begin BL_BLACK_LIST_ENTRY_HISTORY_LINK
create table BL_BLACK_LIST_ENTRY_HISTORY_LINK (
    BLACKLISTENTRY_ID uuid,
    HISTORY_ID uuid,
    primary key (BLACKLISTENTRY_ID, HISTORY_ID)
)^
-- end BL_BLACK_LIST_ENTRY_HISTORY_LINK
-- begin BL_REVIEWER_PROFILE_HISTORY_LINK
create table BL_REVIEWER_PROFILE_HISTORY_LINK (
    PROFILE_ID uuid,
    HISTORY_ID uuid,
    primary key (PROFILE_ID, HISTORY_ID)
)^
-- end BL_REVIEWER_PROFILE_HISTORY_LINK
-- begin BL_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK
create table BL_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK (
    PROFILE_ID uuid,
    ENTRY_ID uuid,
    primary key (PROFILE_ID, ENTRY_ID)
)^
-- end BL_ACCEPTOR_PROFILE_BLACK_LIST_ENTRY_LINK
-- begin BL_REVIEWER_PROFILE_CONTACT_LINK
create table BL_REVIEWER_PROFILE_CONTACT_LINK (
    PROFILE_ID uuid,
    CONTACT_ID uuid,
    primary key (PROFILE_ID, CONTACT_ID)
)^
-- end BL_REVIEWER_PROFILE_CONTACT_LINK
-- begin BL_REVIEWER_PROFILE_REVIEW_LINK
create table BL_REVIEWER_PROFILE_REVIEW_LINK (
    PROFILE_ID uuid,
    REVIEW_ID uuid,
    primary key (PROFILE_ID, REVIEW_ID)
)^
-- end BL_REVIEWER_PROFILE_REVIEW_LINK
-- begin BL_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK
create table BL_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK (
    PROFILE_ID uuid,
    ENTRY_ID uuid,
    primary key (PROFILE_ID, ENTRY_ID)
)^
-- end BL_VOTER_PROFILE_BLACK_LIST_ENTRY_LINK
-- begin BL_BLACK_LIST_ENTRY_PLAYER_IP_LINK
create table BL_BLACK_LIST_ENTRY_PLAYER_IP_LINK (
    BLACKLISTENTRY_ID uuid,
    PLAYERIP_ID uuid,
    primary key (BLACKLISTENTRY_ID, PLAYERIP_ID)
)^
-- end BL_BLACK_LIST_ENTRY_PLAYER_IP_LINK
-- begin BL_ACCEPTOR_PROFILE_CONTACT_LINK
create table BL_ACCEPTOR_PROFILE_CONTACT_LINK (
    PROFILE_ID uuid,
    CONTACT_ID uuid,
    primary key (PROFILE_ID, CONTACT_ID)
)^
-- end BL_ACCEPTOR_PROFILE_CONTACT_LINK
-- begin BL_VOTER_PROFILE_CONTACT_LINK
create table BL_VOTER_PROFILE_CONTACT_LINK (
    ROFILE_ID uuid,
    CONTACT_ID uuid,
    primary key (ROFILE_ID, CONTACT_ID)
)^
-- end BL_VOTER_PROFILE_CONTACT_LINK
-- begin BL_BLACK_LIST_ENTRY_CONTACT_LINK
create table BL_BLACK_LIST_ENTRY_CONTACT_LINK (
    BLACKLISTENTRY_ID uuid,
    CONTACT_ID uuid,
    primary key (BLACKLISTENTRY_ID, CONTACT_ID)
)^
-- end BL_BLACK_LIST_ENTRY_CONTACT_LINK
-- begin SEC_USER
alter table SEC_USER add column ACCEPTOR_PROFILE_ID uuid ^
alter table SEC_USER add column REVIEWER_PROFILE_ID uuid ^
alter table SEC_USER add column VOTER_PROFILE_ID uuid ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'bl_ExtUser' where DTYPE is null ^
-- end SEC_USER
