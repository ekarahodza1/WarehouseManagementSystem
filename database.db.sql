BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "warehouse" (
	"id"	INTEGER,
	"name"	TEXT,
	"location" TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "product" (
	"id"	INTEGER,
	"name"	TEXT,
	"type"	INTEGER,
	"amount"	INTEGER,
	"unitPrice"	REAL,
	"price"	REAL,
	"warehouse"	INTEGER,
	"dateAdded"	TEXT,
	"expirationDate"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("warehouse") REFERENCES "warehouse"("id")
);
INSERT INTO "warehouse" VALUES (1,'A1', 'Sarajevo');
INSERT INTO "warehouse" VALUES (2,'B1', 'Zenica');
INSERT INTO "warehouse" VALUES (3,'C1', 'Mostar');
INSERT INTO "warehouse" VALUES (4,'D1', 'Zenica' );
INSERT INTO "warehouse" VALUES (5,'E1', 'Sarajevo');
INSERT INTO "warehouse" VALUES (6,'F1', 'Sarajevo');
INSERT INTO "warehouse" VALUES (7,'G1', 'Mostar');
INSERT INTO "warehouse" VALUES (8,'H1', 'Zenica');
INSERT INTO "warehouse" VALUES (9,'I1', 'Sarajevo');
INSERT INTO "warehouse" VALUES (10,'J1', 'Sarajevo');
INSERT INTO "product" VALUES (1,'Zara pants',1,40,60.0,2400.0,1,'2016-01-01',NULL);
INSERT INTO "product" VALUES (2,'Oranges',2,60,2.0,120.0,4,'2020-01-07','2020-01-27');
INSERT INTO "product" VALUES (3,'HP Laptop',4,10,700.0,7000.0,6,'2019-12-01',NULL);
COMMIT;
