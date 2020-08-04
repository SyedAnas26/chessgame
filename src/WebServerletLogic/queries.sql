create table gamemoves
(
    GameID            int         not null,
    MoveNo            int         not null,
    Moves             varchar(45) not null,
    TimeTaken         int         not null,
    DrawClaimedStatus int         null,
    GameMoveId        int auto_increment
        primary key
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

create table gamelog
(
    idGameLog   int     not null
        primary key,
    GameType    tinyint not null,
    UserID1     int     null,
    UserID2     int     null,
    GameFormat  int     not null,
    GameStatus  int     not null,
    MatchResult int     not null,
    constraint UserID1_FK
        foreign key (UserID1) references login (UserId),
    constraint UserID2_FK
        foreign key (UserID2) references login (UserId)
);

create index UserID1_FK_idx
    on gamelog (UserID1);

create index UserID2FK_idx
    on gamelog (UserID2);

create table challengetable
(
    idChallengeTable int         not null
        primary key,
    ChallengeToken   varchar(10) not null,
    CreatedByUserID  int         not null,
    CreatedByPlayAs  tinyint(1)  not null,
    ChallengeType    tinyint     not null,
    Status           tinyint     null
);



//TO change big int GameID

ALTER TABLE `chessgame_database`.`gamelog`
CHANGE COLUMN `idGameLog` `idGameLog` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `chessgame_database`.`gamemoves`
CHANGE COLUMN `DrawClaimedStatus` `GameStatus` INT NULL DEFAULT NULL

