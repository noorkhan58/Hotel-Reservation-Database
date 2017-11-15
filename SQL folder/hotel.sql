DROP DATABASE IF EXISTS HOTEL;
CREATE DATABASE HOTEL;
USE HOTEL; 

DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
    uNAME VARCHAR(50),
    uStars int DEFAULT 5,
    memberSince timestamp,
    BANNED BOOLEAN DEFAULT FALSE,
    Days int DEFAULT 0,
    Referrals int DEFAULT 0,
    refrence VARCHAR(50) DEFAULT null,
	PRIMARY KEY (uNAME),
	FOREIGN KEY (refrence) references USER (uNAME)
);

DROP TABLE IF EXISTS ROOMS;
CREATE TABLE ROOMS
(
    rNumber int,
    rStatus VARCHAR(20) DEFAULT 'Avaliable',
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
set AUTO_INCREMENT = 1;

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
	uNAME VARCHAR(50),
	pStatus VARCHAR(20) DEFAULT 'Avaliable',
	pType VARCHAR(20),
	startDate date,
	endDate date,
	PRIMARY KEY (pNumber),
	FOREIGN KEY (uNAME) references USER (uNAME)
);


drop view IF EXISTS BanV;
create view banV as select uNAME, uName as value from user where banned is false;
 
drop view IF EXISTS DiscountDay;
create view DiscountDay as select uNAME, uname as value from USER where Days > 9;

drop view IF EXISTS DiscountRef;
create view DiscountRef as select uNAME, uname as value from USER where Referrals > 4;

drop view IF EXISTS OpenRooms;
create view OpenRooms AS select rNumber as value from ROOMS where rNumber not in 
    (select rNumber from ROOMS) and not rType = 'Facilities' and not rStatus = 'Taken';

drop view IF EXISTS FacilitiesAvaliable;
create view FacilitiesAvaliable AS select fName, rNumber as value from facilities where fStatus = 'Avaliable';

drop view IF EXISTS RoomTypes;
create view RoomTypes AS select rtype, count(rtype) as amount from rooms group by rtype;

drop view IF EXISTS ReservationDays;
create view ReservationDays as select reservationID, DATEDIFF(reservation.endDate, reservation.startDate) as daycount 
    from reservation; 

drop view IF EXISTS OpenParking;
create view OpenParking as select pID, uNAME as value from Parking where pStatus = 'Avaliable';

drop view IF EXISTS OpenParkingNumber;
create view OpenParkingNumber as select pType, count(pType) as amount from Parking group by pType;

#still working on commented out stuff
#drop trigger IF EXISTS CheckOutGoods;
#delimiter //
#create trigger CheckOutGoods
#After Update on reservation
#for each row 
#begin 
#	if(old.checkout= false and new.checkout = True and payed = True) then
#		 update USER set Days = Days + daycount where USER.uNAME = reservation.uNAME and #need to fix logic
#        (select daycount from daysofReservation where rID = reservation.rID); # not sure if right
#        update USER set u1.Referrals = u1.Referrals + 1 and u2.refrence = null where #not right
#        (select uNAME from User u1, User u2 where u1.uNAME = u2.refrence);
#        update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
#    elseif(old.checkout = false and new.checkout = True and reservation.payed is False) then
#		update USER set user.stars = 1 where user.uNAME = reservation.uNAME; 
#       update Rooms set Rooms.rstatus = 'Avaliable' where Rooms.rNumber = reservation.rNumber;
#    end if;
#end;//
#delimiter ;
#

drop trigger IF EXISTS CheckInReservation;
delimiter //
create trigger CheckInReservation AFTER UPDATE on reservation 
for each row 
begin 
if(old.CheckedIn = false and new.Checkedin = true and old.rNumber = new.rNumber) 
THEN update ROOMS set rStatus = 'Taken' where new.rNumber = Rooms.rNumber; 
update Parking set pStatus = 'Taken' where new.uNAME = Parking.uNAME and new.startDate = Parking.startDate; 
    ELSEIF(old.CheckedOut = false and new.CheckedOut = true and old.rNumber = new.rNumber) 
    THEN update ROOMS set rStatus = 'Avaliable' where new.rNumber = Rooms.rNumber; 
    update Parking set pStatus = 'Avaliable' where new.uNAME = Parking.uNAME and new.startDate = Parking.startDate; 
        END IF; END;//
delimiter ;

drop trigger IF EXISTS banUser;
delimiter //
CREATE TRIGGER banUser BEFORE UPDATE ON USER FOR EACH ROW 
BEGIN IF new.uStars <= 1 THEN set new.Banned = true; END IF; END;//
delimiter ;










