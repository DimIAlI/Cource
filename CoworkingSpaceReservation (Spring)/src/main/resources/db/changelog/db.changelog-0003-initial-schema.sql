--liquibase formatted sql

--changeset DimAl:1
ALTER TABLE workspaces DROP COLUMN IF EXISTS available;