CREATE TABLE user_table(id_user serial primary key, 
                    first_name varchar(20), 
                    last_name varchar(20), 
                    login varchar(20), 
                    password varchar(20), 
                    email varchar(50), 
                    role varchar(20));

CREATE TABLE mentor(id_mentor serial primary key, 
                    id_user int, 
                    foreign key (id_user) references user_table(id_user));

CREATE TABLE creep(id_creep serial primary key,
                    id_user int, 
                    foreign key (id_user) references user_table(id_user));

CREATE TABLE room(id_room serial primary key,
                    room_name varchar(20));

CREATE TABLE codecooler(id_codecooler serial primary key,
                    id_user int references user_table(id_user),
                    id_room int references room(id_room), 
                    experience int, coins int);

CREATE TABLE quest(id_quest serial primary key,
                    quest_name varchar(30), 
                    description text,
                    coins int, 
                    isExtra boolean);

CREATE TABLE artefact(id_artefact serial primary key,
                    artefact_name varchar(30), 
                    price int, 
                    description text, 
                    isGroup boolean);

CREATE TABLE mentor_class(id_mentor int references mentor(id_mentor),
                    id_room int references room(id_room),
                    constraint id_mentor_class primary key (id_mentor, id_room));

CREATE TABLE codecooler_quest(id_codecooler int references codecooler(id_codecooler),
                    id_quest int references quest(id_quest),
                    constraint id_codecooler_quest primary key (id_codecooler, id_quest));

CREATE TABLE codecooler_artefact(id_codecooler int references codecooler(id_codecooler),
                    id_artefact int references artefact(id_artefact),
                    constraint id_codecooler_artefact primary key (id_codecooler, id_artefact));

create table group_table(id_group serial primary key,
                    id_artefact int references artefact(id_artefact),
                    status varchar(20)); 

create table codecooler_group(id_codecooler int references codecooler(id_codecooler),
                    id_group int references group_table(id_group), 
                    constraint id_codecooler_group primary key (id_codecooler, id_group));
