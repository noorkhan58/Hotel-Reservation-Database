DROP DATABASE IF EXISTS HOTEL;
CREATE DATABASE HOTEL;
USE HOTEL; 

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    uID INT AUTO_INCREMENT,
    uNAME VARCHAR(50),
    uStars int DEFAULT 5,
    memberSince data CURRENT_TIMESTAMP,
    BANNED BOOLEAN DEFAULT FALSE,
    Days int DEFAULT 0,
    Referrals int DEFAULT 0,
    refrence int DEFAULT null,
	PRIMARY KEY (uID)
);
set AUTO_INCREMENT 1;

DROP TABLE IF EXISTS ROOMS;
CREATE TABLE ROOMS
(
    rNumber int,
    rStatus VARCHAR(20),
    price INT,
    rType VARCHAR(20),
    HANDICAP BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (rNumber)
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
    uID int,
    rNumber int,
    reservationID INT AUTO_INCREMENT,
    CheckedIn boolean default false,
    Checkedout boolean default false,
    payed boolean default false,
    startDate date,
	endDate date,
	PRIMARY KEY (reservationID),
	FOREIGN KEY (uID) references USER (uID),
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
	pID int,
	uID int,
	pStatus VARCHAR(20),
	pType VARCHAR(20),
	startDate date,
	endDate date,
	PRIMARY KEY (pID),
);


drop view IF EXISTS BanV;
create view banV as select uID, uName as value from user where banned is false;
 
drop view IF EXISTS DiscountDay;
create view DiscountDay as select uID, uname as value from USER where Days > 9;

drop view IF EXISTS DiscountRef;
create view DiscountRef as select uID, uname as value from USER where Referrals > 4;

drop view IF EXISTS OpenRooms;
create view OpenRooms AS select rNumber as value from ROOMS where rNumber not in (select rNumber from ROOMS) and not rType = 'Facilities' and not rStatus = 'Taken';

drop view FacilitiesAvaliable;
create view IF EXISTS FacilitiesAvaliable AS select fName, rNumber as value from facilities where fStatus = 'Avaliable';

drop view IF EXISTS RoomTypes;
create view RoomTypes AS select rtype, count(rtype) as amount from rooms group by rtype;

drop view IF EXISTS ReservationDays;
create view ReservationDays as select reservationID, DATEDIFF(reservation.endDate, reservation.startDate) as daycount from reservation; 

drop view IF EXISTS OpenParking;
create view OpenParking as select pID, uID as value from Parking where pStatus = 'Open';

drop view IF EXISTS OpenParkingNumber;
create view OpenParkingNumber as select pType, count(pType) as amount from Parking group by pType;

drop trigger IF EXISTS checkingout;
delimiter //
create trigger checkingout
After Update on reservation.checkout
for each row 
begin 
	if(reservation.checkout is True and reservation.payed is True) then
		update USER set Days = Days + daycount where USER.uID = reservation.uID and #need to fix logic
        (select daycount from daysofReservation where rID = reservation.rID); # not sure if right
        update USER set u1.Referrals = u1.Referrals + 1 and u2.refrence = null where #not right
        (select uID from User u1, User u2 where u1.uID = u2.refrence);
        update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
    elseif(reservation.checkout is True and reservation.payed is False) then
		update USER set user.BANNED = true where user.uid = reservation.uID; 
        update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
    end if;
end;//
delimiter ;


drop trigger IF EXISTS setReservation;
delimiter //
create trigger setReservation BEFORE UPDATE on reservation for each row begin if(CheckedIn = false and new.Checkedin = true) THEN update ROOMS set rStatus = 'Taken' where reservation.rNumber = Rooms.rNumber; end if; end;//
delimiter ;

drop trigger IF EXISTS banUser;
delimiter //
CREATE TRIGGER banUser BEFORE UPDATE ON USER FOR EACH ROW BEGIN IF new.uStars <= 1 THEN set new.Banned = true; END IF; END;//

delimiter ;










