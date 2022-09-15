\c homework_4.2
create table faculty (
                         id serial primary key,
                         name text,
                         color text
);
alter table faculty add constraint name_color_unique unique (name, color);
create table student (
                         id serial primary key,
                         name text not null unique,
                         age integer check ( age > 15 ) default 20,
                         faculty_id serial references faculty (id)
);