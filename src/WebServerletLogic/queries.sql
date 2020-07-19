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

