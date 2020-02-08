create table BL_EXPORT_CONSUMER (
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
    URL varchar(512),
    TYPE varchar(50),
    TOKEN varchar(2048),
    IS_NEW_CONSUMER boolean,
    IS_REVIEWING_CONSUMER boolean,
    IS_READY_TO_ACCEPTING_CONSUMER boolean,
    IS_ACCEPTING_CONSUMER boolean,
    IS_ACCEPTED_CONSUMER boolean,
    IS_REJECTED_CONSUMER boolean,
    --
    primary key (ID)
);