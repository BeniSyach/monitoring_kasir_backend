/*
 Navicat Premium Data Transfer

 Source Server         : DB LOCAL POSTGRE
 Source Server Type    : PostgreSQL
 Source Server Version : 160004 (160004)
 Source Host           : localhost:5432
 Source Catalog        : sync_db
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160004 (160004)
 File Encoding         : 65001

 Date: 18/05/2026 12:15:43
*/


-- ----------------------------
-- Sequence structure for activity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."activity_id_seq";
CREATE SEQUENCE "public"."activity_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for raw_request_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."raw_request_log_id_seq";
CREATE SEQUENCE "public"."raw_request_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for store_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."store_id_seq";
CREATE SEQUENCE "public"."store_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for vendor_config_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."vendor_config_id_seq";
CREATE SEQUENCE "public"."vendor_config_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for vendor_endpoint_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."vendor_endpoint_id_seq";
CREATE SEQUENCE "public"."vendor_endpoint_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for vendor_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."vendor_id_seq";
CREATE SEQUENCE "public"."vendor_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for vendor_store_mapping_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."vendor_store_mapping_id_seq";
CREATE SEQUENCE "public"."vendor_store_mapping_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS "public"."activity";
CREATE TABLE "public"."activity" (
  "id" int8 NOT NULL DEFAULT nextval('activity_id_seq'::regclass),
  "activity_date" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "source_id" varchar(255) COLLATE "pg_catalog"."default",
  "store_id" int8,
  "vendor_id" int8
)
;

-- ----------------------------
-- Records of activity
-- ----------------------------

-- ----------------------------
-- Table structure for raw_request_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."raw_request_log";
CREATE TABLE "public"."raw_request_log" (
  "id" int8 NOT NULL DEFAULT nextval('raw_request_log_id_seq'::regclass),
  "created_at" timestamp(6),
  "endpoint_name" varchar(255) COLLATE "pg_catalog"."default",
  "error_message" varchar(255) COLLATE "pg_catalog"."default",
  "request_payload" oid,
  "response_payload" oid,
  "retry_count" int4,
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "vendor_id" int8
)
;

-- ----------------------------
-- Records of raw_request_log
-- ----------------------------

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS "public"."store";
CREATE TABLE "public"."store" (
  "id" int8 NOT NULL DEFAULT nextval('store_id_seq'::regclass),
  "alias" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO "public"."store" VALUES (1, 'A24', 'HOKBEN SUZUYA TANJUNG MORAWA');

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS "public"."vendor";
CREATE TABLE "public"."vendor" (
  "id" int8 NOT NULL DEFAULT nextval('vendor_id_seq'::regclass),
  "active" bool NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of vendor
-- ----------------------------
INSERT INTO "public"."vendor" VALUES (3, 't', NULL, 'PAJAK');

-- ----------------------------
-- Table structure for vendor_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."vendor_config";
CREATE TABLE "public"."vendor_config" (
  "id" int8 NOT NULL DEFAULT nextval('vendor_config_id_seq'::regclass),
  "auth_type" varchar(255) COLLATE "pg_catalog"."default",
  "auth_value" text COLLATE "pg_catalog"."default",
  "base_url" varchar(255) COLLATE "pg_catalog"."default",
  "content_type" varchar(255) COLLATE "pg_catalog"."default",
  "timeout_ms" int4,
  "vendor_id" int8
)
;

-- ----------------------------
-- Records of vendor_config
-- ----------------------------
INSERT INTO "public"."vendor_config" VALUES (1, 'BASIC', 'RGlzcGVuZGE6KiNEaXNwZW5kYSojMjAyNEA=', 'https://ess-api.ebi-ict.my.id', 'application/json', 5000, NULL);

-- ----------------------------
-- Table structure for vendor_endpoint
-- ----------------------------
DROP TABLE IF EXISTS "public"."vendor_endpoint";
CREATE TABLE "public"."vendor_endpoint" (
  "id" int8 NOT NULL DEFAULT nextval('vendor_endpoint_id_seq'::regclass),
  "body_template" text COLLATE "pg_catalog"."default",
  "http_method" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "query_template" text COLLATE "pg_catalog"."default",
  "url_path" varchar(255) COLLATE "pg_catalog"."default",
  "vendor_id" int8
)
;

-- ----------------------------
-- Records of vendor_endpoint
-- ----------------------------
INSERT INTO "public"."vendor_endpoint" VALUES (1, NULL, 'GET', NULL, 'branch_id={{branchId}}&trans_date={{date}}', '/api/pajak', 3);

-- ----------------------------
-- Table structure for vendor_store_mapping
-- ----------------------------
DROP TABLE IF EXISTS "public"."vendor_store_mapping";
CREATE TABLE "public"."vendor_store_mapping" (
  "id" int8 NOT NULL DEFAULT nextval('vendor_store_mapping_id_seq'::regclass),
  "branch_id" varchar(255) COLLATE "pg_catalog"."default",
  "store_id" int8,
  "vendor_id" int8
)
;

-- ----------------------------
-- Records of vendor_store_mapping
-- ----------------------------
INSERT INTO "public"."vendor_store_mapping" VALUES (1, '1', 1, 3);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."activity_id_seq"
OWNED BY "public"."activity"."id";
SELECT setval('"public"."activity_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."raw_request_log_id_seq"
OWNED BY "public"."raw_request_log"."id";
SELECT setval('"public"."raw_request_log_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."store_id_seq"
OWNED BY "public"."store"."id";
SELECT setval('"public"."store_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."vendor_config_id_seq"
OWNED BY "public"."vendor_config"."id";
SELECT setval('"public"."vendor_config_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."vendor_endpoint_id_seq"
OWNED BY "public"."vendor_endpoint"."id";
SELECT setval('"public"."vendor_endpoint_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."vendor_id_seq"
OWNED BY "public"."vendor"."id";
SELECT setval('"public"."vendor_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."vendor_store_mapping_id_seq"
OWNED BY "public"."vendor_store_mapping"."id";
SELECT setval('"public"."vendor_store_mapping_id_seq"', 1, true);

-- ----------------------------
-- Primary Key structure for table activity
-- ----------------------------
ALTER TABLE "public"."activity" ADD CONSTRAINT "activity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table raw_request_log
-- ----------------------------
ALTER TABLE "public"."raw_request_log" ADD CONSTRAINT "raw_request_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table store
-- ----------------------------
ALTER TABLE "public"."store" ADD CONSTRAINT "store_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table vendor
-- ----------------------------
ALTER TABLE "public"."vendor" ADD CONSTRAINT "uk_bylgbejpglm23j5mkuwfcwd5o" UNIQUE ("code");

-- ----------------------------
-- Primary Key structure for table vendor
-- ----------------------------
ALTER TABLE "public"."vendor" ADD CONSTRAINT "vendor_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table vendor_config
-- ----------------------------
ALTER TABLE "public"."vendor_config" ADD CONSTRAINT "uk_669pyibw0d86eai8q6h3ukt9q" UNIQUE ("vendor_id");

-- ----------------------------
-- Primary Key structure for table vendor_config
-- ----------------------------
ALTER TABLE "public"."vendor_config" ADD CONSTRAINT "vendor_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table vendor_endpoint
-- ----------------------------
ALTER TABLE "public"."vendor_endpoint" ADD CONSTRAINT "vendor_endpoint_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table vendor_store_mapping
-- ----------------------------
ALTER TABLE "public"."vendor_store_mapping" ADD CONSTRAINT "vendor_store_mapping_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table activity
-- ----------------------------
ALTER TABLE "public"."activity" ADD CONSTRAINT "fkcqp8ymcu9syw31sddd76tk7un" FOREIGN KEY ("vendor_id") REFERENCES "public"."vendor" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."activity" ADD CONSTRAINT "fkrycovs534e1rc6srf71qnnd3c" FOREIGN KEY ("store_id") REFERENCES "public"."store" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table raw_request_log
-- ----------------------------
ALTER TABLE "public"."raw_request_log" ADD CONSTRAINT "fkj7qbdu1ac4rswyitb8v2ehsdb" FOREIGN KEY ("vendor_id") REFERENCES "public"."vendor" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table vendor_config
-- ----------------------------
ALTER TABLE "public"."vendor_config" ADD CONSTRAINT "fko2opdenqr5tokjkdcx6sreqcm" FOREIGN KEY ("vendor_id") REFERENCES "public"."vendor" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table vendor_endpoint
-- ----------------------------
ALTER TABLE "public"."vendor_endpoint" ADD CONSTRAINT "fk88opqcjt18kyvpj0877hsunwb" FOREIGN KEY ("vendor_id") REFERENCES "public"."vendor" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table vendor_store_mapping
-- ----------------------------
ALTER TABLE "public"."vendor_store_mapping" ADD CONSTRAINT "fk1kvpaamnmjqsusyyqp63t6lrs" FOREIGN KEY ("store_id") REFERENCES "public"."store" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."vendor_store_mapping" ADD CONSTRAINT "fk5fw6lh3dg7xmokpaltsxsj8gw" FOREIGN KEY ("vendor_id") REFERENCES "public"."vendor" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
