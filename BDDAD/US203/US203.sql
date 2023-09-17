/*Verificar Restrições da Tabela Morada*/
insert into Morada values (1, '2', 'Porto');            --Aprovado
insert into Morada values (1, '478-301', 'Porto');      --Aprovado
insert into Morada values (1, '47851-301', 'Porto');    --Aprovado
insert into Morada values (1, '4785-30', 'Porto');      --Aprovado
insert into Morada values (1, '4785-3011', 'Porto');    --Aprovado
insert into Morada values (1, '4785.301', 'Porto');     --Aprovado
insert into Morada values (1, '47855301', 'Porto');     --Aprovado
insert into Morada values (1, '4785-301', 'Porto');     --Aprovado

/*Verificar Restrições da Tabela Cliente*/
insert into Cliente values (1, 'Serafim Costa', -1, 'serafim@gmail.com', 21.1, 1, 1);           --Aprovado
insert into Cliente values (1, 'Serafim Costa', 132, 'serafim@gmail.com', 21.1, 1, 1);          --Aprovado
insert into Cliente values (1, 'Serafim Costa', 3426748931, 'serafim@gmail.com', 21.1, 1, 1);   --Aprovado
insert into Cliente values (1, 'Serafim Costa', 278105638, 'serafim', 21.1, 1, 1);              --Aprovado
insert into Cliente values (1, 'Serafim Costa', 278105638, 'serafim@gmail', 21.1, 1, 1);        --Aprovado
insert into Cliente values (1, 'Serafim Costa', 278105638, 'serafim.com', 21.1, 1, 1);          --Aprovado
insert into Cliente values (1, 'Serafim Costa', 278105638, 'serafim@gmail-com', 21.1, 1, 1);    --Aprovado
insert into Cliente values (1, 'Serafim Costa', 278105638, 'serafim*gmail.com', 21.1, 1, 1);    --Aprovado

/*Verificar Restrições da Tabela Condutor*/
insert into Condutor values (1, 'Custódio Guimarães', 'custodio');              --Aprovado
insert into Condutor values (1, 'Custódio Guimarães', 'custodio@gmail');        --Aprovado
insert into Condutor values (1, 'Custódio Guimarães', 'custodio.com');          --Aprovado
insert into Condutor values (1, 'Custódio Guimarães', 'custodio@gmal-com');     --Aprovado
insert into Condutor values (1, 'Custódio Guimarães', 'custodio@gmal,com');     --Aprovado
insert into Condutor values (1, 'Custódio Guimarães', 'custodio*gmal.com');     --Aprovado

/*Verificar Restrições da Tabela Gestor_Agricola*/
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo');               --Aprovado
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo@gmail');         --Aprovado
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo.com');           --Aprovado
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo@gmail-com');     --Aprovado
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo@gmail,com');     --Aprovado
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo*gmail.com');     --Aprovado

/*Verificar Restrições da Tabela Gestor_De_Distribuição*/
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes');              --Aprovado
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes@gmail');        --Aprovado
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes.com');          --Aprovado
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes@gmail-com');    --Aprovado
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes@gmail,com');    --Aprovado
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes*gmail.com');    --Aprovado

/*Verificar Restrições da Tabela Rede_De_Distribuição*/
insert into Gestor_De_Distribuição values (1, 'Gretrudes Sousa', 'gretrudes@gmail.com');    
insert into Rede_De_Distribuição values (1, 'online', 1);                                   --Aprovado
insert into Rede_De_Distribuição values (1, 'Mercado Local', 1);                            --Aprovado

/*Verificar Restrições da Tabela Factor_De_Produção*/
insert into Ficha_Tecnica values (1);                                               
insert into Factor_De_Produção values (1, 'teste', 'outro', 'líquido','Maped',1);           --Aprovado
insert into Factor_De_Produção values (1, 'teste', 'fertilizantes', 'outro','Maped',1);     --Aprovado

/*Verificar Restrições da Tabela Encomenda*/
insert into Encomenda values (1, 1, 'ola', date '2022-11-15', date '2022-11-15', date '2022-11-20', 1, 1, 1);   --Aprovado

/*Verificar Restrições da Tabela Sensor*/
insert into Instalação_Agricola values (1);         
insert into Estação_Meteorologica values (1, 1);    
insert into Sensor values (1, 1, 'Pressão');        --Aprovado

/*Verificar Restrições da Tabela Fertilização*/
insert into Gestor_Agricola values (1, 'Jerónimo Fernandes', 'jeronimo@gmail.com');     
insert into Caderno_De_Campo values (1, 1);     
insert into Factor_De_Produção values (1, 'teste', 'fertilizantes', 'líquido',1);
insert into Cultura values (1, 'teste', 'Permanente');
insert into Sistema_De_Rega values (1, 50, 'Bombeada', 'Aspersão');
insert into Parcela_Agricola values (1, 'parcela1', '200 m2', 1, 1, 1, 1);
insert into Fertilização values(1, 1, 1, 1, 'ola', date '2022-12-02', 2);       --Aprovado

/*Verificar Restrições da Tabela Sistema_De_Rega*/
insert into Sistema_De_Rega values(2, 50, 'ola', 'Aspersão');       --Aprovado
insert into Sistema_De_Rega values(2, 50, 'Bombeada', 'ola');       --Aprovado

/*Verificar Restrições da Tabela Cultura*/
insert into Cultura values (1, 'teste', 'ola');     --Aprovado