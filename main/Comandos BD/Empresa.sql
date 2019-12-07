use projeto20192s;

create table empresa (
	nome varchar (60),
    endereco varchar (180),
    CNPJ varchar (18) unique,
    cidade varchar (60),
    uf varchar (2),
    id int auto_increment primary key,
    telefone varchar(15),
    email varchar(6)

)