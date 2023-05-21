create database Gestion_emploi_du_temps;
use Gestion_emploi_du_temps;

create table admin(
id int primary key auto_increment,
username varchar(45),
password varchar(30)
)ENGINE=INNODB;

create table Enseignant(
id int primary key auto_increment,
nom_complet varchar(45),
genre varchar(10),
email varchar(50),
telephone varchar(30),
matricule varchar(45),
nationalite varchar(50),
addresse varchar(50),
pays varchar(30)
)ENGINE=INNODB;