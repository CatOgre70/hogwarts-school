create table car (
                     id serial primary key,
                     manufacturer text,
                     model text,
                     price integer
);
create table driver (
                        id serial primary key,
                        name text unique,
                        age integer check (age > 0) default 18,
                        driver_license boolean,
                        car_id serial references car (id)
);
alter table driver add constraint age_check check ( NOT (age < 18 and driver_license = false) );
alter table driver add constraint license_and_car_check check (NOT (driver_license = false and car_id = null));
