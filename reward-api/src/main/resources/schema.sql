create table if not exists reward (
    id bigint not null auto_increment,
    created_at datetime(6),
    deleted_at datetime(6),
    updated_at datetime(6),
    title varchar(255),
    description longtext,
    count integer not null comment '하루에 발행할 보상금 갯수',
    reward_amount integer not null comment '보상금 지급액',
    start_date_time datetime(6) comment '이벤트 시작일',
    end_date_time datetime(6) comment '이벤트 종료일',
    primary key (id)
) engine=InnoDB;

create table if not exists reward_publish (
    id bigint not null auto_increment,
    created_at datetime(6),
    deleted_at datetime(6),
    updated_at datetime(6),
    reward_id bigint not null '수령받은 보상 이벤트 id',
    member_id bigint not null comment '보상금 수령자',
    amount integer not null comment '보상금 수령액',
    published_at date comment '보상금 지급일',
    winning_count integer not null comment '보상금을 연속으로 몇 번 받았는지 횟수',
    primary key (id),
    constraint FKfa4i49s1yewadw9ajcjfa06dy
    foreign key (reward_id)
    references reward (id),
    constraint uk_reward_id_member_id_published_at unique (member_id, reward_id, published_at)
) engine=InnoDB;
