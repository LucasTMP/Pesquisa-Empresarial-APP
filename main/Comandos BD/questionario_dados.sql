use projeto20192s;

create table questionario_dados (
    id int auto_increment primary key,
    usuario int,
    descricao varchar (200),
    status int,
    usuariosolicitante int,
    dataincio date,
    sigilo boolean,
    datafinal date,
    departamento int

)