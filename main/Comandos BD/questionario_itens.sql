use projeto20192s;

create table questionario_itens (
    id int auto_increment primary key,
    questionario int,
    sequencia int,
    descricao varchar (200),
    tipo int

)