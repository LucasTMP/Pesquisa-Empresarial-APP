use projeto20192s;

create table usuario(
	id int auto_increment primary key,
    Empresa int,
    email varchar (60) unique,
    status int,
    senha varchar (20),
    tipo int,
    departamento int,
    nome varchar(60)

);
