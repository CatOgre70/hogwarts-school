\c homework_4.2
alter table faculty add constraint name_color_unique unique (name, color);
alter table student add constraint name_not_null check (name is not null);
alter table student add constraint name_unique unique (name);
alter table student add constraint age_larger_than_15 check (age > 15);
alter table student alter age set default 20;