alter table SYS_CATEGORY_ATTR add DEFAULT_STR_ENTITY_VALUE varchar2(255)^
alter table SYS_CATEGORY_ATTR add DEFAULT_INT_ENTITY_VALUE integer^
alter table SYS_CATEGORY_ATTR add DEFAULT_LONG_ENTITY_VALUE number^

alter table SYS_ATTR_VALUE add STRING_ENTITY_ID varchar2(255)^
alter table SYS_ATTR_VALUE add INT_ENTITY_ID integer^
alter table SYS_ATTR_VALUE add LONG_ENTITY_ID number^

alter table SYS_ATTR_VALUE add STRING_ENTITY_VALUE varchar2(255)^
alter table SYS_ATTR_VALUE add INT_ENTITY_VALUE integer^
alter table SYS_ATTR_VALUE add LONG_ENTITY_VALUE number^

create index IDX_SYS_ATTR_VALUE_SENTITY on SYS_ATTR_VALUE(STRING_ENTITY_ID)^
create index IDX_SYS_ATTR_VALUE_IENTITY on SYS_ATTR_VALUE(INT_ENTITY_ID)^
create index IDX_SYS_ATTR_VALUE_LENTITY on SYS_ATTR_VALUE(LONG_ENTITY_ID)^