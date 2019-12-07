use projeto20192s;

alter table departamento add constraint FK_departamento_empresa foreign key (empresa) references empresa (id);
alter table usuario add constraint FK_usuario_empresa foreign key (empresa) references empresa(id);
alter table questionario_dados add constraint FK_quest_dpt foreign key (departamento) references departamento (id);
alter table usuario add constraint FK_usuario_departamento foreign key (departamento) references departamento(id);
alter table questionario_dados add constraint FK_quest_user foreign key (usuariosolicitante) references usuario (id);
alter table questionario_itens add constraint FK_quest_Itens_dados foreign key (questionario) references questionario_dados (id);
alter table questionario_respostas add constraint FK_quest_resposta_itens foreign key (questionario_item) references questionario_itens (id);
alter table questionario_respostas add constraint FK_quest_resposta_user foreign key (usuario) references usuario (id);
alter table questionario_respondido add constraint FK_quest_respondido_dados foreign key (questionario) references questionario_dados (id);
alter table questionario_respondido add constraint FK_quest_respondido_user foreign key (usuario) references usuario(id);
