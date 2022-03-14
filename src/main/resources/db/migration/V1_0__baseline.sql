CREATE TABLE "co2reading"
(
    "id"             UUID NOT NULL,
    "city_id"        VARCHAR NOT NULL,
    "district_id"    VARCHAR NOT NULL,
    "created_at"     TIMESTAMP,
    "concentration"  INTEGER
);