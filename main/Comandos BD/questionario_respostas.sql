use projeto20192s;

create table questionario_respostas (
    id int auto_increment primary key,
    questionario_item int,
    usuario int,
    obs varchar (200),
    valor int

)