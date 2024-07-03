create table if not exists role
(
    id   int auto_increment
        primary key,
    role enum ('ROLE_ADMIN', 'ROLE_USER') null
);

create table if not exists user
(
    id                  int auto_increment
        primary key,
    credentials_expired bit          not null,
    disabled            bit          not null,
    expired             bit          not null,
    locked              bit          not null,
    password            varchar(255) null,
    username            varchar(255) null,
    email               varchar(255) null,
    first_name          varchar(255) null,
    last_name           varchar(255) null
);

create table if not exists user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FK859n2jvi8ivhui0rl0esws6o
        foreign key (user_id) references user (id),
    constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id) references role (id)
);

INSERT INTO role (role) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO user (credentials_expired,disabled,expired,locked, password, username, email, first_name, last_name) VALUES (false,false, false, false, '$2a$12$osh88cvVZWuFIFtlMqJED.S.GpYJS2st/JJ8gtw3kCSMXDI14g0Im','dacm', 'dacm.dev@mail.com','Daniel','Contreras');
INSERT INTO user_role(user_id, role_id) value (1,1);
INSERT INTO user_role(user_id, role_id) value (1,2);