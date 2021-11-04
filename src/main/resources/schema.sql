create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists resource
(
    id          bigint       not null
        primary key,
    icon        varchar(255) null,
    item_id     bigint       null,
    name        varchar(255) null,
    unique_name varchar(255) null
);

create table if not exists server
(
    id              bigint       not null
        primary key,
    server_fraction varchar(255) null
);

create table if not exists employee
(
    id           bigint       not null
        primary key,
    discord_name varchar(255) null,
    gold         bigint       null,
    server_id    bigint       null,
    constraint UK_o713vnu3h8p41rw7bjybtqbm
        unique (discord_name),
    constraint FKtkbe1prv4vo6xdlpl7sgw35ne
        foreign key (server_id) references server (id)
);

create table if not exists for_sale
(
    id               bigint not null
        primary key,
    dreamingGlory   int    null,
    nightmareVine   int    null,
    manaThistle     int    null,
    nightmareSeed   int    null,
    felLotus        int    null,
    netherbloom     int    null,
    terocone        int    null,
    felIronOre      int    null,
    eterniumOre     int    null,
    adamantiteOre   int    null,
    khoriumOree     int    null,
    bloodGarnet     int    null,
    flameSpessarite int    null,
    goldenDraenite  int    null,
    shadowDraenite  int    null,
    deepPeridot     int    null,
    azureMoonstone  int    null,
    dawnstone       int    null,
    nightseye       int    null,
    starOfElune     int    null,
    talasite        int    null,
    livingRuby      int    null,
    nobleTopaz      int    null,
    primalEarth     int    null,
    primalFire      int    null,
    primalMana      int    null,
    primalAir       int    null,
    primalLife      int    null,
    price           int    null,
    date            date   null,
    employee_id     bigint null,
    constraint FKeifkwgr7qi4q8r31voqrgfrww
        foreign key (employee_id) references employee (id)
);

create table if not exists resource_price
(
    id          bigint      not null
        primary key,
    price       bigint      null,
    scanned_on  datetime(6) null,
    resource_id bigint      null,
    server_id   bigint      null,
    constraint FK531yudr7vqdp2qx4shh463x9p
        foreign key (server_id) references server (id),
    constraint FKfae49bhrh8n5yjtsgu6i74t3l
        foreign key (resource_id) references resource (id)
);

