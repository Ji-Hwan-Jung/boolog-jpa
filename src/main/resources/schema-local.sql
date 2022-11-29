drop table if exists member cascade;
drop table if exists post cascade;
drop table if exists liked_post cascade;
drop table if exists tag cascade;
drop table if exists post_tag;

create table member
(
    id bigint generated by default as identity,
    email varchar(255) not null,
    password varchar(255) not null,
    name varchar(255) not null,
    picture varchar(255),
    introduction varchar(100),
    provider varchar(255),
    provider_id varchar(255),
    role varchar(255) not null,
    created_date timestamp not null,
    primary key (id)
);

create table post
(
    id bigint generated by default as identity,
    member_id bigint not null,
    thumbnail varchar(255),
    description varchar(255),
    title varchar(255) not null,
    content clob not null,
    liked integer default 0,
    created_date timestamp not null,
    modified_date timestamp not null,
    primary key (id),
    foreign key (member_id) references member(id) on delete cascade
);

create table liked_post
(
    id bigint generated by default as identity,
    member_id bigint not null,
    post_id bigint not null,
    created_date timestamp not null,
    foreign key (member_id) references member(id) on delete cascade,
    foreign key (post_id) references post(id) on delete cascade,
    primary key (id)
);

create table tag
(
    id bigint generated by default as identity,
    tag_name varchar(255) not null,
    created_date timestamp not null,
    primary key (id)
);

create table post_tag
(
    id bigint generated by default as identity,
    post_id bigint not null,
    tag_id bigint not null,
    created_date timestamp not null,
    foreign key (post_id) references post(id) on delete cascade,
    foreign key (tag_id) references tag(id) on delete cascade,
    primary key (id)
);

alter table liked_post add constraint LIKED_POST_UNIQUE unique (member_id, post_id);
alter table member add constraint EMAIL_UNIQUE unique (email);
alter table member add constraint NAME_UNIQUE unique (name);
alter table post_tag add constraint POST_TAG_UNIQUE unique (post_id, tag_id);
alter table tag add constraint TAG_UNIQUE unique (tag_name);


-- H2 Database Trigger --
create trigger liked_plus
after insert on liked_post for each row
call "com.stoph.boolog.trigger.LikedPlus";

create trigger liked_minus
after delete on liked_post for each row
call "com.stoph.boolog.trigger.LikedMinus";