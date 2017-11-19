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
    memberSince timestamp,
    BANNED BOOLEAN DEFAULT FALSE,
    Days int DEFAULT 0,
    Referrals int DEFAULT 0,
    reference VARCHAR(50) DEFAULT null,
	PRIMARY KEY (uNAME),
	FOREIGN KEY (reference) references USER (uNAME)
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
<<<<<<< Updated upstream
	pStatus VARCHAR(20) DEFAULT 'Available',
	pType VARCHAR(20),
=======
	pStatus VARCHAR(20) DEFAULT 'Avaliable',
>>>>>>> Stashed changes
	startDate date,
	endDate date,
	PRIMARY KEY (pNumber),
    UNIQUE (roomNumber),
	FOREIGN KEY (uNAME) references USER (uNAME),
    FOREIGN KEY (rNumber) references ROOMS (rNumber)
);

<<<<<<< Updated upstream
=======

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
ELSEIF(old.CheckedOut = false and new.CheckedOut = true and old.rNumber = new.rNumber)
THEN
UPDATE Parking SET uNAME = NULL, 
Parking.startDate = NULL, Parking.endDate = NULL, pStatus = 'Avaliable'
WHERE old.rNumber = Parking.roomNumber;
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
create view banV as select uNAME, uName as value from user where banned is false;
 
>>>>>>> Stashed changes
drop view IF EXISTS DiscountDay;
create view DiscountDay as select uNAME, uname as value from USER where Days > 9;

drop view IF EXISTS DiscountRef;
create view DiscountRef as select uNAME, uname as value from USER where Referrals > 4;

drop view IF EXISTS OpenRooms;
create view OpenRooms AS select rNumber, rType as value from ROOMS where rNumber not in 
    (select rNumber from ROOMS) and not rType = 'Facilities' and rStatus = 'Available';

drop view IF EXISTS FacilitiesAvaliable;
create view FacilitiesAvaliable AS select fName, rNumber as value from facilities where fStatus = 'Available';

drop view IF EXISTS RoomTypes;
create view RoomTypes AS select rtype, count(rtype) as amount from rooms group by rtype;

drop view IF EXISTS ReservationDays;
create view ReservationDays as select reservationID, DATEDIFF(reservation.endDate, reservation.startDate) as daycount 
    from reservation; 

drop view IF EXISTS OpenParking;
create view OpenParking as select pNumber, uNAME as value from Parking where pStatus = 'Available';

drop view IF EXISTS OpenParkingNumber;
create view OpenParkingNumber as select pType, count(pType) as amount from Parking group by pType;


<<<<<<< Updated upstream
Drop PROCEDURE IF EXISTS CHECKBANNED;
delimiter //
CREATE PROCEDURE CHECKBANNED(IN USERNAME VARCHAR(50), OUT bool BOOLEAN)
begin 
Select BANNED 
INTO bool
from user
where uNAME = USERNAME;
end//
delimiter ;

=======
>>>>>>> Stashed changes
#still working on commented out stuff
#drop trigger IF EXISTS CheckOutGoods;
#delimiter //
#create trigger CheckOutGoods
#After Update on reservation
#for each row 
#begin 
#	if(old.checkout= false and new.checkout = True and payed = True) then
#		 update USER set Days = Days + daycount where USER.uNAME = reservation.uNAME and #need to fix logic
#        (select daycount from daysofReservation where rNumber = reservation.rNumber); # not sure if right
#        update USER set u1.Referrals = u1.Referrals + 1 and u2.reference = null where #not right
#        (select uNAME from User u1, User u2 where u1.uNAME = u2.reference);
#        update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
#    elseif(old.checkout = false and new.checkout = True and reservation.payed is False) then
#		update USER set user.stars = 1 where user.uNAME = reservation.uNAME; 
#       update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
#    end if;
#end;//
#delimiter ;


drop trigger IF EXISTS CheckInReservation;
delimiter //
create trigger CheckInReservation AFTER UPDATE on reservation 
for each row 
begin 
if(old.CheckedIn = false and new.Checkedin = true and old.rNumber = new.rNumber) 
THEN update ROOMS set rStatus = 'Taken' where new.rNumber = Rooms.rNumber; 
update Parking set pStatus = 'Taken' where new.uNAME = Parking.uNAME and new.startDate = Parking.startDate; 
    ELSEIF(old.CheckedOut = false and new.CheckedOut = true and old.rNumber = new.rNumber) 
    THEN update ROOMS set rStatus = 'Available' where new.rNumber = Rooms.rNumber; 
    update Parking set pStatus = 'Available' where new.uNAME = Parking.uNAME and new.startDate = Parking.startDate; 
        END IF; END;//
delimiter ;

drop trigger IF EXISTS banUser;
delimiter //
CREATE TRIGGER banUser BEFORE UPDATE ON USER FOR EACH ROW 
BEGIN IF new.uStars <= 1 THEN set new.Banned = true; END IF; END;//
delimiter ;








