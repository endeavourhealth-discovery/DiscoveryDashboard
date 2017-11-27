drop database if exists adastra_receiver;

create database adastra_receiver;

create table adastra_receiver.message_status (
	id tinyint not null comment 'Unique Id for the message status',
    status_description varchar(50) not null comment 'Description of the status',
    
    constraint adastra_receiver_message_status_pk primary key (id)
);

create table adastra_receiver.message_store (
	id bigint(20) not null auto_increment comment 'Unique Id for the message',
    source bigint(20) not null comment 'Source sending organisation',
    received_date_time datetime not null comment 'When the message was received',
    status tinyint not null comment 'Status of message (received, sent etc)',
    sent_date_time varchar(3) null comment 'When the message was sent to the messaging API',
    message_payload mediumtext not null comment 'The payload of the message',
    error_message varchar(1000) null comment 'Error messages',
    
    constraint adastra_receiver_message_store_pk primary key (id),
    index adastra_receiver_message_store_received_date_time_idx (received_date_time),
    foreign key adastra_receiver_message_store_status_fk (status) references adastra_receiver.message_status(id)
);

create table adastra_receiver.graph_date_range (
	min_date date not null comment 'Minimum date',
	max_date date not null comment 'Maximum date'
    
);

