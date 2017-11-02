drop database if exists adastra_receiver;

create database adastra_receiver;

/*
Not sure we need the numbers after numerical column definitions as that only affect zero fill as far as I can tell.  
ie do we want concepts to always be 20 digits?  000000000000000001 or 1?
https://stackoverflow.com/questions/3135804/types-in-mysql-bigint20-vs-int20

*/

create table adastra_receiver.message_store (
	id bigint(20) not null auto_increment comment 'Unique Id for the message',
    source bigint(20) not null comment 'Source sending organisation',
    received_date_time datetime not null comment 'When the message was received',
    status tinyint not null comment 'Status of message (received, sent etc)',
    sent_date_time varchar(3) null comment 'When the message was sent to the messaging API',
    message_payload mediumtext not null comment 'The payload of the message',
    
    constraint adastra_receiver_message_store_pk primary key (id),
    index adastra_receiver_message_store_received_date_time_idx (received_date_time)
);
