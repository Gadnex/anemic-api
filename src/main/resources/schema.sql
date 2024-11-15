create table if not exists backlog_item (status tinyint check (status between 0 and 5), story_points smallint check ((story_points<=21) and (story_points>=1)), type tinyint check (type between 0 and 3), version integer, backlog_item_id uuid not null, product_id uuid not null, sprint_id uuid, name varchar(100) not null, summary varchar(255), story clob, primary key (backlog_item_id))
create table if not exists comment (backlog_item_id uuid not null, comment varchar(255))
create table if not exists product (version integer, product_id uuid not null, name varchar(100) not null, description clob, primary key (product_id))
create table if not exists sprint (end_date date, start_date date, version integer, product_id uuid not null, sprint_id uuid not null, name varchar(255), primary key (sprint_id))
alter table if exists backlog_item add constraint fk_backlog_item__product foreign key (product_id) references product
alter table if exists backlog_item add constraint fk_backlog_item__sprint foreign key (sprint_id) references sprint
alter table if exists comments add constraint fk_comment__backlog_item foreign key (backlog_item_id) references backlog_item
alter table if exists sprint add constraint fk_sprint__product foreign key (product_id) references product