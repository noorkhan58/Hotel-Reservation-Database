DROP DATABASE IF EXISTS HOTEL;
CREATE DATABASE HOTEL;
USE HOTEL; 

DROP TABLE IF EXISTS ADMIN;
CREATE TABLE ADMIN
(
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    uNAME VARCHAR(50),
    uStars INT DEFAULT 5,
    memberSince timestamp DEFAULT CURRENT_TIMESTAMP,
    BANNED BOOLEAN DEFAULT FALSE,
    Days INT DEFAULT 0,
    Referrals INT DEFAULT 0,
    reference VARCHAR(50) DEFAULT NULL,
	PRIMARY KEY (uNAME)
);

DROP TABLE IF EXISTS ROOMS;
CREATE TABLE ROOMS
(
    rNumber INT,
    rStatus VARCHAR(20) DEFAULT 'Available',
    price INT,
    rType VARCHAR(20),
    HANDICAP BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (rNumber)
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
	reservationID INT AUTO_INCREMENT,
    uNAME VARCHAR(50),
    rNumber INT,
    CheckedIn BOOLEAN DEFAULT false,
    CheckedOut BOOLEAN DEFAULT false,
    paid BOOLEAN DEFAULT false,
    startDate date,
	endDate date,
	PRIMARY KEY (reservationID),
	FOREIGN KEY (uNAME) references USER (uNAME),
    FOREIGN KEY (rNumber) references ROOMS (rNumber)
);

DROP TABLE IF EXISTS facilities;
CREATE TABLE facilities(
    fName VARCHAR(30),
    rNumber INT,
    fType VARCHAR(20),
    fStatus VARCHAR(20),
    HANDICAP BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (fName),
    FOREIGN KEY (rNumber) references rooms(rNumber)
);

	DROP TABLE IF EXISTS Parking;
	CREATE TABLE Parking(
		pNumber INT,
		roomNumber INT NOT NULL,
		uNAME VARCHAR(50) DEFAULT NULL,
		pStatus VARCHAR(20) DEFAULT 'Available',
		startDate date DEFAULT NULL,
		endDate date DEFAULT NULL,
		PRIMARY KEY (pNumber),
		FOREIGN KEY (uNAME) references USER (uNAME),
		FOREIGN KEY (roomNumber) references ROOMS (rNumber)
	);

DROP TABLE IF EXISTS archiveReservation; 
CREATE TABLE archiveReservation
(
    updatedAt timestamp,
    reservationID INT,
    uNAME VARCHAR(50),
    rNumber INT,
    CheckedIn BOOLEAN DEFAULT false,
    CheckedOut BOOLEAN DEFAULT false,
    paid BOOLEAN DEFAULT false,
    startDate date,
	endDate date,
	FOREIGN KEY (uNAME) references USER (uNAME),
    FOREIGN KEY (rNumber) references ROOMS (rNumber)
);

DROP TABLE IF EXISTS archiveUser;
CREATE TABLE archiveUser
(
    updatedAt DATETIME,
    uNAME VARCHAR(50),
    uStars INT DEFAULT 5,
    memberSince timestamp,
    BANNED BOOLEAN DEFAULT FALSE,
    Days INT DEFAULT 0,
    Referrals INT DEFAULT 0,
    reference VARCHAR(50) DEFAULT NULL,
	FOREIGN KEY (reference) references USER (uNAME)
);



DROP TRIGGER IF EXISTS updateArchiveReservation;
DELIMITER //
CREATE TRIGGER updateArchiveReservation AFTER UPDATE ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    VALUES(CURRENT_TIMESTAMP, new.reservationID, new.uName, new.rNumber, 
    new.CheckedIn, new.CheckedOut, new.paid, new.startDate, new.endDate);
END; //
DELIMITER ;

DROP TRIGGER IF EXISTS addArchiveReservation;
DELIMITER //
CREATE TRIGGER addArchiveReservation AFTER INSERT ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    VALUES(CURRENT_TIMESTAMP, new.reservationID, new.uName, new.rNumber, 
    new.CheckedIn, new.CheckedOut, new.paid, new.startDate, new.endDate);
END;//
DELIMITER ;

DROP TRIGGER IF EXISTS removedArchiveReservation;
DELIMITER //
CREATE TRIGGER removedArchiveReservation AFTER DELETE ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    VALUES(CURRENT_TIMESTAMP, old.reservationID, old.uName, old.rNumber, 
    old.CheckedIn, old.CheckedOut, old.paid, old.startDate, old.endDate);
END;//
DELIMITER ;

DROP TRIGGER IF EXISTS updateArchiveUser;
DELIMITER //
CREATE TRIGGER updateArchiveUser AFTER UPDATE ON User FOR EACH ROW
BEGIN
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, new.uName, new.uStars, new.memberSince,
    new.Banned, new.Days, new.Referrals, new.reference);
END; //
DELIMITER ;

DROP TRIGGER IF EXISTS addArchiveUser;
DELIMITER //
CREATE TRIGGER addArchiveUser AFTER INSERT ON User FOR EACH ROW
BEGIN 
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, new.uName, new.uStars, new.memberSince,
    new.Banned, new.Days, new.Referrals, new.reference);
END; //
DELIMITER ;

DROP TRIGGER IF EXISTS removedArchiveUser;
DELIMITER //
CREATE TRIGGER removedArchiveUser AFTER DELETE ON User FOR EACH ROW
BEGIN 
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, old.uName, old.uStars, old.memberSince,
    old.Banned, old.Days, old.Referrals, old.reference);
END;//
DELIMITER ;


DROP TRIGGER IF EXISTS cancelReservation;
DELIMITER //
CREATE TRIGGER cancelReservation
AFTER DELETE ON reservation
FOR EACH ROW
BEGIN
UPDATE ROOMS SET rStatus = 'Available' WHERE old.rNumber = ROOMS.rNumber and old.CheckedIn = true and old.CheckedOut = false;
END //
DELIMITER ;

DROP TRIGGER IF EXISTS banUser;
DELIMITER //
CREATE TRIGGER banUser BEFORE UPDATE ON USER FOR EACH ROW 
BEGIN IF (new.uStars <= 1) THEN 
SET new.Banned = true;
ELSEIF(new.banned = true) THEN
SET new.uStars = 0; 
END IF; END;//
DELIMITER ;

DROP TRIGGER IF EXISTS checkIn;
DELIMITER //
CREATE TRIGGER checkIn
AFTER UPDATE ON reservation
FOR EACH ROW
BEGIN
IF(old.CheckedIn = false AND new.Checkedin = true AND old.rNumber = new.rNumber)
THEN
UPDATE Parking SET uNAME = new.uNAME, 
Parking.startDate = new.startDate, Parking.endDate = new.endDate, pStatus = 'Taken'
WHERE old.rNumber = Parking.roomNumber;
UPDATE rooms SET rStatus = 'Taken' WHERE Rooms.rNumber = old.rNumber;
ELSEIF(old.CheckedOut = false AND new.CheckedOut = true AND old.rNumber = new.rNumber)
THEN
UPDATE Parking SET uNAME = NULL, 
Parking.startDate = NULL, Parking.endDate = NULL, pStatus = 'Available'
WHERE old.rNumber = Parking.roomNumber;
UPDATE rooms SET rStatus = 'Taken' WHERE Rooms.rNumber = old.rNumber;
END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addAdmin;
DELIMITER //
CREATE PROCEDURE addAdmin(
IN username VARCHAR(50),
IN password VARCHAR(50)
)
BEGIN
INSERT INTO ADMIN VALUES(username, password);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS clearReference;
DELIMITER //
CREATE PROCEDURE clearReference(
IN username VARCHAR(50)
)
BEGIN 
SELECT reference FROM USER WHERE uNAME = username;
UPDATE USER SET reference = NULL WHERE uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addDays;
DELIMITER //
CREATE PROCEDURE addDays(
IN username VARCHAR(50),
IN Dcount INT
)
BEGIN
UPDATE USER SET days = days + Dcount WHERE uNAME = username;
SELECT days FROM user WHERE uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addRefDays;
DELIMITER //
CREATE PROCEDURE addRefDays(
IN username VARCHAR(50)
)
BEGIN
UPDATE user SET Referrals = Referrals + 1 WHERE uNAME = username;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS RemoveDays;
DELIMITER //
CREATE PROCEDURE RemoveDays(
IN username VARCHAR(50),
IN Dcount INT
)
BEGIN
UPDATE user SET days = days - Dcount WHERE uNAME = username;
SELECT days FROM user WHERE uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS removeRefDays;
DELIMITER //
CREATE PROCEDURE removeRefDays(
IN username VARCHAR(50),
IN Dcount INT
)
BEGIN
UPDATE user SET Referrals = Referrals - Dcount WHERE uNAME = username;
SELECT Referrals FROM user WHERE uNAME = username;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS GetPrices;
DELIMITER //
CREATE PROCEDURE GetPrices(
IN inRID INT
)
BEGIN
SELECT * FROM ROOMS RIGHT JOIN reservation ON ROOMS.rNumber = reservation.rNumber WHERE inRID = reservation.reservationID;
END //
DELIMITER ;	

DROP PROCEDURE IF EXISTS checkBanUser;
DELIMITER //
CREATE PROCEDURE checkBanUser(
IN username VARCHAR(50)
)
BEGIN
SELECT uNAME FROM USER WHERE uNAME = username AND BANNED = true;
END //
DELIMITER ;

-- Procedure that returns number to check the availability of the reservation date
DROP PROCEDURE IF EXISTS getAvailabledates;
DELIMITER //
CREATE PROCEDURE getAvailabledates(
IN date1 VARCHAR(20),
IN date2 VARCHAR(20),
IN roomNumber INT
)
BEGIN
SELECT COUNT(uName) AS total FROM reservation WHERE (date1 < startDate AND (date2 < endDate AND date2 <=startDate)
AND rNumber = roomNumber)
OR(((date1 > startDate AND date1 >= endDate) AND date2 > endDate)
AND rNumber = roomNumber);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getTotalDates;
DELIMITER //
CREATE PROCEDURE getTotalDates(
IN roomNumber INT
)
BEGIN
SELECT COUNT(*) FROM reservation WHERE rNumber = roomNumber;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS displayUnavailableDate;
DELIMITER //
CREATE PROCEDURE displayUnavailableDate(
IN roomNumber INT
)
BEGIN
SELECT startDate, endDate FROM reservation WHERE rNumber = roomNumber;
END //
DELIMITER ;

DROP VIEW IF EXISTS BanV;
CREATE VIEW banV AS SELECT uNAME FROM user WHERE banned is false;
 
DROP VIEW IF EXISTS DiscountDay;
CREATE VIEW DiscountDay AS SELECT uNAME FROM USER WHERE Days > 9;

DROP VIEW IF EXISTS DiscountRef;
CREATE VIEW DiscountRef AS SELECT uNAME FROM USER WHERE Referrals > 2;

DROP VIEW IF EXISTS OpenRooms;
CREATE VIEW OpenRooms AS SELECT rNumber, rType AS value FROM ROOMS WHERE rNumber NOT IN 
(SELECT rNumber FROM ROOMS) AND NOT rType = 'Facilities' AND rStatus = 'Available';

DROP VIEW IF EXISTS FacilitiesStatus;
CREATE VIEW FacilitiesStatus AS SELECT fName, fStatus FROM facilities;

DROP VIEW IF EXISTS RoomTypes;
CREATE VIEW RoomTypes AS SELECT rtype, COUNT(rtype) AS amount FROM rooms WHERE NOT rType = 'Facilities' AND
rStatus = 'Available' GROUP BY rtype HAVING amount > 0;

DROP VIEW IF EXISTS ReservationDays;
CREATE VIEW ReservationDays AS SELECT reservationID, DATEDIFF(reservation.endDate, reservation.startDate) AS daycount 
FROM reservation; 

DROP VIEW IF EXISTS OpenParking;
CREATE VIEW OpenParking AS SELECT pNumber, uNAME AS value FROM Parking WHERE pStatus = 'Available';


DROP VIEW IF EXISTS allAvailableroomsFacilities;
CREATE VIEW allAvailableroomsFacilities AS
SELECT * FROM
(SELECT rNumber, rType FROM rooms WHERE rStatus = 'Available'
UNION
SELECT rNumber, fName FROM facilities WHERE fStatus = 'Available') AS allRooms;










