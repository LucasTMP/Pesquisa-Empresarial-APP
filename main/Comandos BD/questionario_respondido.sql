use projeto20192s;

create table questionario_respondido (
    id int auto_increment primary key,
    questionario int,
    usuario int,
    dataresposta date

)