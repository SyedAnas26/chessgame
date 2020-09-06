create table challengetable
(
    idChallengeTable int         not null
        primary key,
    ChallengeToken   varchar(10) not null,
    CreatedByUserID  int         not null,
    CreatedByPlayAs  varchar(1)  not null,
    ChallengeType    tinyint     not null,
    Status           tinyint     null,
    gameID           bigint      not null
);

create table login
(
    username varchar(45)  null,
    fullname varchar(150) null,
    password varchar(45)  null,
    email_id varchar(150) null,
    gender   varchar(6)   null,
    UserId   int auto_increment
        primary key
);



create table gamemoves
(
    GameID      bigint      not null,
    MoveNo      int         not null,
    Moves       varchar(45) not null,
    TimeTaken   int         not null,
    GameStatus  int         null,
    GameMoveId  int auto_increment
        primary key,
    GameTillNow longtext    null
);

create index fk_gameid
    on gamemoves (GameID);


create table gamelog
(
    idGameLog   int auto_increment
        primary key,
    GameType    tinyint  not null,
    UserID1     int      null,
    UserID2     int      null,
    GameFormat  int      not null,
    MatchResult int      not null,
    GameId      bigint   null,
    GameinPgn   longtext null,
    constraint UserID1_FK
        foreign key (UserID1) references login (UserId),
    constraint UserID2_FK
        foreign key (UserID2) references login (UserId),
    constraint fk_gameid
        foreign key (GameId) references gamemoves (GameID)
);

create index UserID1_FK_idx
    on gamelog (UserID1);

create index UserID2FK_idx
    on gamelog (UserID2);


create table pgnlog
(
    idPgnLog    int auto_increment
        primary key,
    createdBy   int         null,
    createdTime bigint      null,
    fileName    varchar(45) not null,
    GameinPgn   longtext    not null
);




# updated Queries


ALTER TABLE `chessgame_database`.`challengetable`
CHANGE COLUMN `idChallengeTable` `idChallengeTable` INT NOT NULL AUTO_INCREMENT ;


ALTER TABLE `chessgame_database`.`challengetable`
CHANGE COLUMN `ChallengeType` `ChallengeType` INT NOT NULL ;


ALTER TABLE `chessgame_database`.`challengetable`
CHANGE COLUMN `ChallengeType` `ChallengeType` VARCHAR(20) NOT NULL ;


ALTER TABLE `chessgame_database`.`gamelog`
DROP FOREIGN KEY `fk_gameid`;
ALTER TABLE `chessgame_database`.`gamelog`
DROP INDEX `fk_gameid` ;


ALTER TABLE `chessgame_database`.`gamelog`
CHANGE COLUMN `GameFormat` `GameFormat` VARCHAR(20) NOT NULL ;
