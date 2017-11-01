drop database if exists information_model;

create database information_model;

/*
Not sure we need the numbers after numerical column definitions as that only affect zero fill as far as I can tell.  
ie do we want concepts to always be 20 digits?  000000000000000001 or 1?
https://stackoverflow.com/questions/3135804/types-in-mysql-bigint20-vs-int20

*/

create table information_model.concept (
	id bigint(20) not null comment 'A unique human readable concept id. In General external taxonomy identifier such as Snomed-CT will be retained to avoid mapping. Discovery identifiers will be prefixed for categorisation',
    name varchar(250) not null comment 'Name for the term',
    status tinyint(4) null comment 'Status of the concept',
    short_name varchar(125) null comment 'Short familiar name for the term',
    structure_type varchar(3) null comment 'Types include clinical concepts (such as Snomed), event types (tables), fields, relationships, range concepts',
    structure_id bigint null comment 'A link to a concept detailing the structure',
    count bigint(20) not null comment 'Rough count indicator for frequency presentation order',
    
    constraint information_model_concept_pk primary key (id),
    constraint information_model_concept_structure_id_fk foreign key (structure_id) references information_model.concept(id)
);

create table information_model.concept_relationship (
	id int(11) not null comment 'Relationship Id',
    source_concept bigint(20) not null comment 'The Source concept in concept table',
    target_concept bigint(20) not null comment 'The Target concept in concept table',
    target_label varchar(45) null comment 'An abbreviated version of the target concept name (may be the same as the short name or even shorter)',
    relationship_order int(4) null comment 'In the context of the expression, the order in which the relationships are presented',
    relationship_type bigint(20) null comment 'Concept identifier of the relationship e.g “has subtype”',
    context_id int(11) null comment 'Grouping construct for dependent concept relationships',
    count bigint(20) null comment 'Rough count indicator for frequency presentation order',
    
    constraint information_model_concept_relationship_pk primary key (id),
    constraint information_model_concept_relationship_source_concept_fk foreign key (source_concept) references information_model.concept(id),
    constraint information_model_concept_relationship_target_concept_fk foreign key (target_concept) references information_model.concept(id)
    /*constraint information_model_concept_relationship_relationship_type_fk foreign key (relationship_type) references information_model.concept(id)*/
);

create table information_model.expression (
	id bigint(20) not null comment 'Id of concept that is represented by expression',
    expression varchar(1024) not null comment 'Expression text represented in Snomed expression language grammar',
    count bigint(20) not null comment 'Rough count indicator for frequency presentation order',
    
    constraint information_model_expression_pk primary key (id),
    constraint information_model_expression_fk foreign key (id) references information_model.concept(id) 
);

create table information_model.range (
	id bigint(20) not null comment 'Id of concept that is represented by range',
    operator char(4) not null comment 'The operator of the range',
    low float(12,4) not null comment 'The low range',
    high float(12,4) not null comment 'The high range',
    range_type bigint(20) not null comment 'Id of concept that is represented by the range type',
    
    constraint information_model_range_pk primary key (id),
    constraint information_model_range_fk foreign key (id) references information_model.concept(id),
    constraint information_model_range_type_fk foreign key (range_type) references information_model.concept(id) 
)