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
    uStars int DEFAULT 5,
    memberSince timestamp DEFAULT CURRENT_TIMESTAMP,
    BANNED BOOLEAN DEFAULT FALSE,
    Days int DEFAULT 0,
    Referrals int DEFAULT 0,
    reference VARCHAR(50) DEFAULT null,
	PRIMARY KEY (uNAME)
);

DROP TABLE IF EXISTS ROOMS;
CREATE TABLE ROOMS
(
    rNumber int,
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
    rNumber int,
    CheckedIn boolean default false,
    CheckedOut boolean default false,
    paid boolean default false,
    startDate date,
	endDate date,
	PRIMARY KEY (reservationID),
	FOREIGN KEY (uNAME) references USER (uNAME),
    FOREIGN KEY (rNumber) references ROOMS (rNumber)
);

drop table if exists facilities;
CREATE TABLE facilities(
    fName VARCHAR(30),
    rNumber int,
    fType VARCHAR(20),
    fStatus VARCHAR(20),
    HANDICAP BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (fName),
    FOREIGN KEY (rNumber) references rooms(rNumber)
);

	drop table if exists Parking;
	create table Parking(
		pNumber INT,
		roomNumber int NOT NULL,
		uNAME VARCHAR(50),
		pStatus VARCHAR(20) DEFAULT 'Available',
		startDate date,
		endDate date,
		UNIQUE (roomNumber),
		FOREIGN KEY (uNAME) references USER (uNAME),
		FOREIGN KEY (roomNumber) references ROOMS (rNumber)
	);

DROP TABLE IF EXISTS archiveReservation; 
CREATE TABLE archiveReservation
(
    updatedAt timestamp,
    reservationID INT,
    uNAME VARCHAR(50),
    rNumber int,
    CheckedIn boolean default false,
    CheckedOut boolean default false,
    paid boolean default false,
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
    uStars int DEFAULT 5,
    memberSince timestamp,
    BANNED BOOLEAN DEFAULT FALSE,
    Days int DEFAULT 0,
    Referrals int DEFAULT 0,
    reference VARCHAR(50) DEFAULT null,
	FOREIGN KEY (reference) references USER (uNAME)
);



drop trigger if exists updateArchiveReservation;
delimiter //
CREATE TRIGGER updateArchiveReservation AFTER UPDATE ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    values(CURRENT_TIMESTAMP, new.reservationID, new.uName, new.rNumber, 
    new.CheckedIn, new.CheckedOut, new.paid, new.startDate, new.endDate);
END; //
delimiter ;

drop trigger if exists addArchiveReservation;
delimiter //
CREATE TRIGGER addArchiveReservation AFTER INSERT ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    values(CURRENT_TIMESTAMP, new.reservationID, new.uName, new.rNumber, 
    new.CheckedIn, new.CheckedOut, new.paid, new.startDate, new.endDate);
END;//
delimiter ;

drop trigger if exists removedArchiveReservation;
delimiter //
CREATE TRIGGER removedArchiveReservation AFTER DELETE ON reservation FOR EACH ROW
BEGIN 
    INSERT INTO archiveReservation 
    values(CURRENT_TIMESTAMP, old.reservationID, old.uName, old.rNumber, 
    old.CheckedIn, old.CheckedOut, old.paid, old.startDate, old.endDate);
END;//
delimiter ;

DROP TRIGGER IF EXISTS updateArchiveUser;
delimiter //
CREATE TRIGGER updateArchiveUser AFTER UPDATE ON User FOR EACH ROW
BEGIN
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, new.uName, new.uStars, new.memberSince,
    new.Banned, new.Days, new.Referrals, new.reference);
END; //
delimiter ;

DROP TRIGGER IF EXISTS addArchiveUser;
delimiter //
CREATE TRIGGER addArchiveUser AFTER INSERT ON User FOR EACH ROW
BEGIN 
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, new.uName, new.uStars, new.memberSince,
    new.Banned, new.Days, new.Referrals, new.reference);
END; //
delimiter ;

DROP TRIGGER IF EXISTS removedArchiveUser;
delimiter //
CREATE TRIGGER removedArchiveUser AFTER DELETE ON User FOR EACH ROW
BEGIN 
    INSERT INTO archiveUser
    VALUES(CURRENT_TIMESTAMP, old.uName, old.uStars, old.memberSince,
    old.Banned, old.Days, old.Referrals, old.refrence);
END;//
delimiter ;


drop trigger IF EXISTS populateParking;
DELIMITER //
CREATE TRIGGER populateParking
AFTER insert ON ROOMS
FOR EACH ROW
BEGIN
INSERT INTO Parking(roomNumber, uName, pStatus, startDate, endDate)
VALUES(new.rNumber, NULL, 'Avaliable', NULL, NULL);
END //
DELIMITER ;

drop trigger IF EXISTS cancelReservation;
DELIMITER //
CREATE TRIGGER cancelReservation
AFTER DELETE ON reservation
FOR EACH ROW
BEGIN
UPDATE ROOMS SET rStatus = 'Avaliable' WHERE old.rNumber = ROOMS.rNumber;
END //
DELIMITER ;

drop trigger IF EXISTS banUser;
delimiter //
CREATE TRIGGER banUser BEFORE UPDATE ON USER FOR EACH ROW 
BEGIN IF new.uStars <= 1 THEN set new.Banned = true; END IF; END;//
delimiter ;

drop trigger IF EXISTS checkIn;
DELIMITER //
CREATE TRIGGER checkIn
AFTER UPDATE ON reservation
FOR EACH ROW
BEGIN
if(old.CheckedIn = false and new.Checkedin = true and old.rNumber = new.rNumber)
THEN
UPDATE Parking SET uNAME = new.uNAME, 
Parking.startDate = new.startDate, Parking.endDate = new.endDate, pStatus = 'Taken'
WHERE old.rNumber = Parking.roomNumber;
UPDATE rooms set rStatus = 'Taken' where Rooms.rNumber = old.rNumber;
ELSEIF(old.CheckedOut = false and new.CheckedOut = true and old.rNumber = new.rNumber)
THEN
UPDATE Parking SET uNAME = NULL, 
Parking.startDate = NULL, Parking.endDate = NULL, pStatus = 'Avaliable'
WHERE old.rNumber = Parking.roomNumber;
UPDATE rooms set rStatus = 'Taken' where Rooms.rNumber = old.rNumber;
 END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addAdmin;
DELIMITER //
CREATE PROCEDURE addAdmin(
IN username varchar(50),
IN password varchar(50)
)
BEGIN
INSERT INTO ADMIN VALUES(username, password);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS clearReference;
DELIMITER //
CREATE PROCEDURE clearReference(
IN username varchar(50)
)
BEGIN 
Select reference from USER where uNAME = username;
update USER set reference = null where uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addDays;
DELIMITER //
CREATE PROCEDURE addDays(
IN username varchar(50),
in Dcount int
)
BEGIN
update user set days = days + Dcount where uNAME = username;
select days from user where uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS addRefDays;
DELIMITER //
CREATE PROCEDURE addRefDays(
IN username varchar(50)
)
BEGIN
update user set Referrals = Referrals + 1 where uNAME = username;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS RemoveDays;
DELIMITER //
CREATE PROCEDURE RemoveDays(
IN username varchar(50),
in Dcount int
)
BEGIN
update user set days = days - Dcount where uNAME = username;
select days from user where uNAME = username;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS removeRefDays;
DELIMITER //
CREATE PROCEDURE removeRefDays(
IN username varchar(50),
in Dcount int
)
BEGIN
update user set Referrals = Referrals - Dcount where uNAME = username;
select Referrals from user where uNAME = username;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS GroupPrices;
DELIMITER //
CREATE PROCEDURE GroupPrices(
IN inRID int
)
BEGIN
select reservationID, price from ROOMS natural join reservation where inRID = reservation.reservationID;
END //
DELIMITER ;	

DROP PROCEDURE IF EXISTS checkBanUser;
DELIMITER //
CREATE PROCEDURE checkBanUser(
IN username varchar(50)
)
BEGIN
SELECT uNAME FROM USER WHERE uNAME = username and BANNED = true;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getAvailabledates;
DELIMITER //
CREATE PROCEDURE getAvailabledates(
IN date1 Varchar(20),
IN date2 varchar(20),
IN roomNumber int
)
BEGIN
SELECT count(uName) as total FROM reservation where (date1 < startDate and date2 < endDate
and rNumber = roomNumber)
 or(date1 > startDate and date2 > endDate
  and rNumber = roomNumber);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getTotalDates;
DELIMITER //
CREATE PROCEDURE getTotalDates(
IN roomNumber int
)
BEGIN
SELECT count(*) FROM reservation where rNumber = roomNumber;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS displayUnavailableDate;
DELIMITER //
CREATE PROCEDURE displayUnavailableDate(
IN roomNumber int
)
BEGIN
SELECT startDate, endDate FROM reservation where rNumber = roomNumber;
END //
DELIMITER ;

drop view IF EXISTS BanV;
create view banV as select uNAME from user where banned is false;
 
drop view IF EXISTS DiscountDay;
create view DiscountDay as select uNAME from USER where Days > 9;

drop view IF EXISTS DiscountRef;
create view DiscountRef as select uNAME from USER where Referrals > 2;

drop view IF EXISTS OpenRooms;
create view OpenRooms AS select rNumber, rType as value from ROOMS where rNumber not in 
    (select rNumber from ROOMS) and not rType = 'Facilities' and rStatus = 'Available';

drop view IF EXISTS FacilitiesStatus;
create view FacilitiesStatus AS select fName, fStatus from facilities;

drop view IF EXISTS RoomTypes;
create view RoomTypes AS select rtype, count(rtype) as amount from rooms where not rType = 'Facilities' and  rStatus = 'Avaliable' group by rtype;

drop view IF EXISTS ReservationDays;
create view ReservationDays as select reservationID, DATEDIFF(reservation.endDate, reservation.startDate) as daycount 
    from reservation; 

drop view IF EXISTS OpenParking;
create view OpenParking as select pNumber, uNAME as value from Parking where pStatus = 'Available';

drop view IF EXISTS OpenParkingNumber;
create view OpenParkingNumber as select pType, count(pType) as amount from Parking group by pType;










