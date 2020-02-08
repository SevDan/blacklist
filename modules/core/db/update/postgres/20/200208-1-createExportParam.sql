create table BL_EXPORT_PARAM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CONSUMER_ID uuid,
    NAME varchar(255),
    KEY varchar(255),
    VALUE varchar(255),
    --
    primary key (ID)
);