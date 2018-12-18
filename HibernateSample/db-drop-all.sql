alter table catalog_version drop constraint if exists fk_catalog_version_catalog_id;
drop index if exists ix_catalog_version_catalog_id;

drop table if exists catalog;

drop table if exists catalog_version;

