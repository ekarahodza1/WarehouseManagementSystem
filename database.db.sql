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
INSERT INTO "product" VALUES (4,'Wood board',5,3,60.0,180.0,7,'2017-09-01',NULL);
INSERT INTO "product" VALUES (5,'Dove shampoo',3,50,5.0,250.0,5,'2018-01-07','2020-06-25');
INSERT INTO "product" VALUES (6,'Crackers',2,30,3.0,90.0,6,'2018-12-01','2020-12-01');
INSERT INTO "product" VALUES (7,'Drill',5,10,93.0,930.0,2,'2019-11-05',NULL);
INSERT INTO "product" VALUES (8,'Shoes MC',1,23,100.0,2300.0,4,'2020-03-09',NULL);
INSERT INTO "product" VALUES (9,'Kids jumper',1,10,56.0,560.0,1,'2018-11-01',NULL);
INSERT INTO "product" VALUES (10,'Universal Shirt',1,40,20.0,8000.0,1,'2016-01-01',NULL);
INSERT INTO "product" VALUES (11,'Gummy bears',2,60,2.0,120.0,4,'2020-05-07','2020-12-27');
INSERT INTO "product" VALUES (12,'Notebook MAC',4,10,860.0,8600.0,6,'2019-12-01',NULL);
COMMIT;
