create table if not exists reward (
    id bigint not null auto_increment,
    created_at datetime(6),
    deleted_at datetime(6),
    updated_at datetime(6),
    count integer not null,
    description longtext,
    end_date_time datetime(6),
    reward_amount integer not null,
    start_date_time datetime(6),
    title varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists reward_publish (
    id bigint not null auto_increment,
    created_at datetime(6),
    deleted_at datetime(6),
    updated_at datetime(6),
    amount integer not null,
    member_id bigint not null,
    published_at date,
    winning_count integer not null,
    reward_id bigint not null,
    primary key (id),
    constraint FKfa4i49s1yewadw9ajcjfa06dy
    foreign key (reward_id)
    references reward (id),
    constraint uk_reward_id_member_id_published_at unique (member_id, reward_id, published_at)
) engine=InnoDB;
