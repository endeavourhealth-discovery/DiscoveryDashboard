drop database if exists discovery_dashboard;

create database discovery_dashboard;

create table discovery_dashboard.dashboard_type (
	id tinyint not null comment 'Unique Id for the dashboard type',
    dashboard_type varchar(50) not null comment 'Type of the dashboard',
    
    constraint discovery_dashboard_dashboard_type_pk primary key (id)
);

create table discovery_dashboard.dashboard_items (
	id int not null auto_increment comment 'Unique Id for the dashboard item',
    title varchar(50) not null comment 'Title of the dashboard',
    dashboard_type tinyint not null comment 'Type of the dashboard',
    api_url varchar(200) not null comment 'Url of the API the supplies the dashboard',
    dashboard_url varchar(200) null comment 'Url of the full dashboard for the application',
    
    constraint discovery_dashboard_dashboard_items_pk primary key (id),    
    foreign key discovery_dashboard_dashboard_items_type_fk (dashboard_type) references discovery_dashboard.dashboard_type(id)
);

create table discovery_dashboard.layout_items (
	id int not null auto_increment comment 'Unique Id for the layout',
    username varchar(50) not null comment 'User this layout belongs to',
    title varchar(50) not null comment 'Title of the layout item',
    position tinyint not null comment 'the position of the layout item',
    size tinyint not null comment 'the size of the dashboard',
    dashboard_item int not null comment 'The Dashboard item used by the layout item',
    graph_days int null comment 'Number of days to go back for the graph',
    graph_period varchar(10) comment 'Period for the graph breakdown',
    
    constraint discovery_dashboard_layout_items_pk primary key (id),    
    foreign key discovery_dashboard_layout_items_type_fk (dashboard_item) references discovery_dashboard.dashboard_items(id)
);



