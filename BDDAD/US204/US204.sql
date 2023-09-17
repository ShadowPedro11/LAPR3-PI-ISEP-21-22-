/*======Morada======================================================================*/
insert into morada values (1, '4480-123', 'Porto');
insert into morada values (2, '4470-120', 'Lisboa');
insert into morada values (3, '4454-300', 'Modivas');
insert into morada values (4, '4492-257', 'Vila Do Conde');
insert into morada values (5, '4488-172', 'Aveiro');

/*======Cliente======================================================================*/

insert into cliente values (1, 'Pedro', 290088763, 'pedro@myspace.com', 1500.15, 1, 1,1,1);
insert into cliente values (2, 'José', 235006789, 'jose@yahoo.com', 300.50, 1, 2,0,0);
insert into cliente values (3, 'Tiago', 290088761, 'tiago@msn.com', 2000.10, 3, 4,1,2);
insert into cliente values (4, 'Alexandre', 158679872, 'alexandre@hotmail.com', 10000, 4, 4,1,4);
insert into cliente values (5, 'Ricardo', 198789123, 'ricardo@gmail.com', 500, 4, 4,5,70);

/*======Instalação_Agricola======================================================================*/

insert into instalação_agricola values (1);
insert into instalação_agricola values (2);

/*======Armazem_Colheita======================================================================*/

insert into Armazem_Colheita values (1, 1);
insert into Armazem_Colheita values (2, 1);
insert into Armazem_Colheita values (3, 2);
insert into Armazem_Colheita values (4, 2);
insert into Armazem_Colheita values (5 ,2);

/*======Armazem_Factor_De_Produção======================================================================*/

insert into Armazem_Factor_De_Produção values (1, 1);
insert into Armazem_Factor_De_Produção values (2, 1);
insert into Armazem_Factor_De_Produção values (3, 2);
insert into Armazem_Factor_De_Produção values (4, 2);

/*======Armazem_Ração_Animal======================================================================*/

insert into Armazem_Ração_Animal values (1, 1);
insert into Armazem_Ração_Animal values (2, 1);
insert into Armazem_Ração_Animal values (3, 2);
insert into Armazem_Ração_Animal values (4, 2);

/*======Estabulo_Animal======================================================================*/

insert into Estabulo_Animal values (1, 1);
insert into Estabulo_Animal values (2, 1);
insert into Estabulo_Animal values (3, 2);
insert into Estabulo_Animal values (4, 2);

/*======Garagem_Maquinas_Alfaias======================================================================*/

insert into Garagem_Maquinas_Alfaias values (1, 1);
insert into Garagem_Maquinas_Alfaias values (2, 1);
insert into Garagem_Maquinas_Alfaias values (3, 2);
insert into Garagem_Maquinas_Alfaias values (4, 2);

/*======Cultura======================================================================*/

insert into Cultura values (1,'Milho','Permanente');
insert into Cultura values (2,'Soja','Permanente');
insert into Cultura values (3,'Trigo','Permanente');
insert into Cultura values (4,'Cevada','Permanente');
insert into Cultura values (5,'Beterraba','Permanente');
insert into Cultura values (6,'Feno ','Temporário');
insert into Cultura values (7,'Bambu','Temporário');
insert into Cultura values (8,'Algodão','Temporário');
insert into Cultura values (9,'Girassol','Temporário');
insert into Cultura values (10,'Azeitonas','Temporário');

/*======Ração_Animal======================================================================*/

insert into Ração_Animal values (1);
insert into Ração_Animal values (2);
insert into Ração_Animal values (3);
insert into Ração_Animal values (4);


/*======Armazem_Ração_Animal_Ração_Animal======================================================================*/

insert into Armazem_Ração_Animal_Ração_Animal values (1,1);
insert into Armazem_Ração_Animal_Ração_Animal values (1,2);
insert into Armazem_Ração_Animal_Ração_Animal values (1,3);
insert into Armazem_Ração_Animal_Ração_Animal values (1,4);
insert into Armazem_Ração_Animal_Ração_Animal values (2,2);
insert into Armazem_Ração_Animal_Ração_Animal values (2,3);
insert into Armazem_Ração_Animal_Ração_Animal values (2,4);
insert into Armazem_Ração_Animal_Ração_Animal values (3,3);
insert into Armazem_Ração_Animal_Ração_Animal values (3,4);
insert into Armazem_Ração_Animal_Ração_Animal values (4,4);

/*======Gestor_De_Distribuição======================================================================*/

insert into Gestor_De_Distribuição values (1,'Cipriano Coimbra','CiprianoCoimbra@gmail.com');
insert into Gestor_De_Distribuição values (2,'Casimiro Cavaco','CasimiroCavaco@gmail.com');
insert into Gestor_De_Distribuição values (3,'Silvio Florencio','SilvioFlorencio@gmail.com');
insert into Gestor_De_Distribuição values (4,'Leandro Ribeiro','LeandroRibeiro@gmail.com');
insert into Gestor_De_Distribuição values (5,'Viriato Carvalho','ViriatoCarvalho@gmail.com');
insert into Gestor_De_Distribuição values (6,'Rubinho Abreu','RubinhoAbreu@gmail.com');

/*======Condutor======================================================================*/

insert into Condutor values (1,'Teodoro Coimbra','TeodoroCoimbra@gmail.com');
insert into Condutor values (2,'Cruz Fernandes','CruzFernandes@gmail.com');
insert into Condutor values (3,'Valério Caetano','ValérioCaetano@gmail.com');
insert into Condutor values (4,'Érico Monteiro','ÉricoMonteiro@gmail.com');
insert into Condutor values (5,'Zé Baptista','ZéBaptista@gmail.com');
insert into Condutor values (6,'Félix Bettencourt','FélixBettencourt@gmail.com');

/*======Rede_De_Distribuição======================================================================*/

insert into Rede_De_Distribuição values (1,'Mercado Local',1);
insert into Rede_De_Distribuição values (2,'AMAP',2);
insert into Rede_De_Distribuição values (3,'Venda Direta',3);
insert into Rede_De_Distribuição values (4,'Grupo de Consumo',4);

/*======Rede_De_Distribuição_Condutor======================================================================*/

insert into Rede_De_Distribuição_Condutor values (1,1);
insert into Rede_De_Distribuição_Condutor values (1,2);
insert into Rede_De_Distribuição_Condutor values (1,3);
insert into Rede_De_Distribuição_Condutor values (1,4);
insert into Rede_De_Distribuição_Condutor values (1,5);
insert into Rede_De_Distribuição_Condutor values (1,6);
insert into Rede_De_Distribuição_Condutor values (2,2);
insert into Rede_De_Distribuição_Condutor values (2,3);
insert into Rede_De_Distribuição_Condutor values (2,4);
insert into Rede_De_Distribuição_Condutor values (2,5);
insert into Rede_De_Distribuição_Condutor values (2,6);
insert into Rede_De_Distribuição_Condutor values (3,3);
insert into Rede_De_Distribuição_Condutor values (3,4);
insert into Rede_De_Distribuição_Condutor values (3,5);
insert into Rede_De_Distribuição_Condutor values (3,6);
insert into Rede_De_Distribuição_Condutor values (4,4);
insert into Rede_De_Distribuição_Condutor values (4,5);
insert into Rede_De_Distribuição_Condutor values (4,6);


/*======Instalação_Agricola_Rede_De_Distribuição======================================================================*/

insert into Instalação_Agricola_Rede_De_Distribuição values (1,1);
insert into Instalação_Agricola_Rede_De_Distribuição values (1,2);
insert into Instalação_Agricola_Rede_De_Distribuição values (1,3);
insert into Instalação_Agricola_Rede_De_Distribuição values (1,4);
insert into Instalação_Agricola_Rede_De_Distribuição values (2,3);
insert into Instalação_Agricola_Rede_De_Distribuição values (2,4);

/*======Produto======================================================================*/

insert into Produto values (1,'Milho',1.47);
insert into Produto values (2,'Soja',1.0);
insert into Produto values (3,'Trigo',2.34);
insert into Produto values (4,'Cevada',4.05);
insert into Produto values (5,'Beterraba',1.23);
insert into Produto values (6,'Feno ',0.56);
insert into Produto values (7,'Bambu',7.14);
insert into Produto values (8,'Algodão',2.26);
insert into Produto values (9,'Girassol',1.07);
insert into Produto values (10,'Azeitonas',3.55);

/*======Encomenda======================================================================*/

insert into Encomenda values (1,1,'entregue',DATE '2022-11-01',DATE '2022-11-01',DATE '2022-11-02',1,1,1);
insert into Encomenda values (2,1,'entregue',DATE '2022-11-01',DATE '2022-11-02',DATE '2022-11-03',1,1,1);
insert into Encomenda values (3,1,'paga',DATE '2022-11-01',DATE '2022-11-04',DATE '2022-11-07',1,1,1);
insert into Encomenda values (4,2,'entregue',DATE '2022-11-02',DATE '2022-11-02',DATE '2022-11-05',1,1,1);
insert into Encomenda values (5,2,'paga',DATE '2022-11-10',DATE '2022-11-11',DATE '2022-11-11',1,1,1);
insert into Encomenda values (6,2,'registada',DATE '2022-11-11',NULL,DATE '2022-11-01',2,1,2);
insert into Encomenda values (7,2,'entregue',DATE '2022-11-01',DATE '2022-11-07',DATE '2022-11-08',2,1,2);
insert into Encomenda values (8,2,'registada',DATE '2022-11-02',NULL,DATE '2022-11-10',2,1,2);
insert into Encomenda values (9,2,'registada',DATE '2022-11-05',NULL,DATE '2022-11-11',3,3,4);
insert into Encomenda values (10,1,'registada',DATE '2022-11-01',NULL,DATE '2022-11-04',3,3,4);
insert into Encomenda values (11,3,'entregue',DATE '2022-11-01',DATE '2022-11-01',DATE '2022-11-01',3,3,4);
insert into Encomenda values (12,3,'registada',DATE '2022-11-01',NULL,DATE '2022-11-05',3,3,4);
insert into Encomenda values (13,4,'entregue',DATE '2022-11-16',DATE '2022-11-16',DATE '2022-11-20',4,4,4);
insert into Encomenda values (14,4,'paga',DATE '2022-11-01',DATE '2022-11-02',DATE '2022-11-07',4,4,4);
insert into Encomenda values (15,4,'registada',DATE '2022-11-22',NULL,DATE '2022-11-29',4,4,4);

/*======Cabaz======================================================================*/

insert into Cabaz values (1,12.0,DATE '2022-11-01',1,1,1);
insert into Cabaz values (2,11.0,DATE '2022-11-01',1,1,2);
insert into Cabaz values (3,12.0,DATE '2022-11-01',2,1,3);
insert into Cabaz values (4,13.0,DATE '2022-11-02',2,1,4);
insert into Cabaz values (5,14.0,DATE '2022-11-10',3,1,5);
insert into Cabaz values (6,12.0,DATE '2022-11-11',3,1,6);
insert into Cabaz values (7,15.0,DATE '2022-11-01',4,1,7);
insert into Cabaz values (8,12.0,DATE '2022-11-02',4,1,8);
insert into Cabaz values (9,17.0,DATE '2022-11-05',4,1,9);
insert into Cabaz values (10,21.0,DATE '2022-11-01',3,2,10);
insert into Cabaz values (11,12.5,DATE '2022-11-01',3,2,11);
insert into Cabaz values (12,12.6,DATE '2022-11-01',4,2,12);
insert into Cabaz values (13,11.2,DATE '2022-11-16',4,2,13);
insert into Cabaz values (14,18.0,DATE '2022-11-01',4,2,14);
insert into Cabaz values (15,9.5,DATE '2022-11-22',4,2,15);


/*======Cabaz_Produto======================================================================*/

insert into Cabaz_Produto values (1,1);
insert into Cabaz_Produto values (2,1);
insert into Cabaz_Produto values (3,2);
insert into Cabaz_Produto values (4,2);
insert into Cabaz_Produto values (5,3);
insert into Cabaz_Produto values (6,4);
insert into Cabaz_Produto values (7,5);
insert into Cabaz_Produto values (8,6);
insert into Cabaz_Produto values (9,7);
insert into Cabaz_Produto values (10,8);
insert into Cabaz_Produto values (11,9);
insert into Cabaz_Produto values (12,10);
insert into Cabaz_Produto values (13,10);
insert into Cabaz_Produto values (14,9);
insert into Cabaz_Produto values (15,8);

/*======Incidente======================================================================*/

insert into Incidente values (1, 1, 5000, DATE '2021-05-02', DATE '2022-07-14', 1, 1);
insert into Incidente values (2, 2, 15000, DATE '2022-06-02', DATE '2022-08-04', 1, 2);
insert into Incidente values (3, 3, 20000, DATE '2020-01-01', DATE '2020-10-27', 3, 4);
insert into Incidente values (4, 3, 3000, DATE '2019-09-05', DATE '2020-02-09', 3, 4);
insert into Incidente values (5, 3, 7000, DATE '2021-04-03', DATE '2021-08-28', 3, 4);

/*======Gestor_Agricola======================================================================*/

insert into Gestor_Agricola values (1, 'Antonio', 'antonio@yahoo.com');
insert into Gestor_Agricola values (2, 'Pedro', 'pedro@outlook.com');
insert into Gestor_Agricola values (3, 'Tiago', 'tiago@gmail.com');
insert into Gestor_Agricola values (4, 'Jose', 'jose@msn.com');
insert into Gestor_Agricola values (5, 'Ricardo', 'ricardo@myspace.com');

/*======Caderno_De_Campo======================================================================*/

insert into Caderno_De_Campo values (1,1);
insert into Caderno_De_Campo values (2,2);
insert into Caderno_De_Campo values (3,3);
insert into Caderno_De_Campo values (4,4);
insert into Caderno_De_Campo values (5,5);

/*======Elemento_Nutritivo_Organico======================================================================*/

insert into Elemento_Nutritivo_Organico values (1,'Azoto','6%');
insert into Elemento_Nutritivo_Organico values (2,'Óxido de Cálcio','15%');
insert into Elemento_Nutritivo_Organico values (3,'Pentóxido de fósforo','2%');
insert into Elemento_Nutritivo_Organico values (4,'Óxido de cálcio','10%');
insert into Elemento_Nutritivo_Organico values (5,'Óxido de magnésio','2%');
insert into Elemento_Nutritivo_Organico values (6,'Vitamina C','20%');
insert into Elemento_Nutritivo_Organico values (7,'Folato','1%');
insert into Elemento_Nutritivo_Organico values (8,'Magnésio','3%');
insert into Elemento_Nutritivo_Organico values (9,'Ferro','5%');
insert into Elemento_Nutritivo_Organico values (10,'Sais Minerais','0.2%');

/*======Substancia_Organica======================================================================*/

insert into Substancia_Organica values (1,'Carbono de origebiológica','35%');
insert into Substancia_Organica values (2,'Matéria Orgânica','60%');
insert into Substancia_Organica values (3,'Ácidos húmicos','6.0%');
insert into Substancia_Organica values (4,'Ácidos fúlicos','3.5%');
insert into Substancia_Organica values (5,'Humidade','7.5%');
insert into Substancia_Organica values (6,'pH','7');
insert into Substancia_Organica values (7,'Peso específico','0.8 Kg/L');
insert into Substancia_Organica values (8,'Formulação peletizada','0.3mm');


/*======Ficha_Tecnica======================================================================*/

insert into Ficha_Tecnica values (1);
insert into Ficha_Tecnica values (2);
insert into Ficha_Tecnica values (3);
insert into Ficha_Tecnica values (4);
insert into Ficha_Tecnica values (5);


/*======Ficha_Tecnica_Elemento_Nutritivo_Organico======================================================================*/

insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (1,1);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (1,2);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (1,3);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (1,4);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (1,5);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (2,6);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (2,7);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (2,8);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (3,9);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (3,10);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (4,2);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (4,3);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (4,6);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (4,7);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (5,10);

/*======Ficha_Tecnica_Substancia_Organica======================================================================*/

insert into Ficha_Tecnica_Substancia_Organica values (1,1);
insert into Ficha_Tecnica_Substancia_Organica values (1,2);
insert into Ficha_Tecnica_Substancia_Organica values (1,3);
insert into Ficha_Tecnica_Substancia_Organica values (1,4);
insert into Ficha_Tecnica_Substancia_Organica values (1,5);
insert into Ficha_Tecnica_Substancia_Organica values (1,6);
insert into Ficha_Tecnica_Substancia_Organica values (1,7);
insert into Ficha_Tecnica_Substancia_Organica values (1,8);
insert into Ficha_Tecnica_Substancia_Organica values (2,1);
insert into Ficha_Tecnica_Substancia_Organica values (2,3);
insert into Ficha_Tecnica_Substancia_Organica values (2,5);
insert into Ficha_Tecnica_Substancia_Organica values (3,2);
insert into Ficha_Tecnica_Substancia_Organica values (3,4);
insert into Ficha_Tecnica_Substancia_Organica values (4,7);
insert into Ficha_Tecnica_Substancia_Organica values (4,8);
insert into Ficha_Tecnica_Substancia_Organica values (4,1);
insert into Ficha_Tecnica_Substancia_Organica values (4,5);
insert into Ficha_Tecnica_Substancia_Organica values (5,7);

/*======Factor_De_Produção======================================================================*/

insert into Factor_De_Produção values (1,'Minoxidil','adubos','granulado','Maped',1);
insert into Factor_De_Produção values (2,'Gra-Fa','correctivos','pó','Maped',2);
insert into Factor_De_Produção values (3,'Feritlizante Al','fertilizantes','líquido','Maped',3);
insert into Factor_De_Produção values (4,'Sementes Mágicas','produtos fitofármacos','granulado','Maped',4);
insert into Factor_De_Produção values (5,'Pó azul','fertilizantes','pó','Maped',5);


/*======Factor_De_Produção_Armazem_Factor_De_Produção======================================================================*/

insert into Factor_De_Produção_Armazem_Factor_De_Produção values (1,1);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (2,1);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (3,1);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (4,1);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (5,1);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (2,2);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (3,2);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (4,2);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (5,2);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (3,3);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (4,3);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (5,3);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (4,4);
insert into Factor_De_Produção_Armazem_Factor_De_Produção values (5,4);

/*======Estação_Meteorologica======================================================================*/

insert into Estação_Meteorologica values (1,1);
insert into Estação_Meteorologica values (2,1);
insert into Estação_Meteorologica values (3,1);
insert into Estação_Meteorologica values (4,1);
insert into Estação_Meteorologica values (5,1);
insert into Estação_Meteorologica values (6,2);
insert into Estação_Meteorologica values (7,2);
insert into Estação_Meteorologica values (8,2);
insert into Estação_Meteorologica values (9,2);
insert into Estação_Meteorologica values (10,2);

/*======Sensor======================================================================*/

insert into Sensor values (1,1,'Temperatura');
insert into Sensor values (11,1,'Velocidade Vento');
insert into Sensor values (111,1,'Direção Vento');
insert into Sensor values (2,2,'Humidade Atmosférica');
insert into Sensor values (3,3,'Humidade Solo');
insert into Sensor values (4,4,'Pluviosidade');
insert into Sensor values (5,5,'Direção Vento');
insert into Sensor values (6,6,'Temperatura');
insert into Sensor values (7,7,'Velocidade Vento');
insert into Sensor values (8,8,'Pluviosidade');
insert into Sensor values (9,9,'Temperatura');
insert into Sensor values (10,10,'Pluviosidade');

/*======Valor_Sensor======================================================================*/

insert into Valor_Sensor values (1,12,CURRENT_TIMESTAMP,1,1);
insert into Valor_Sensor values (2,1,CURRENT_TIMESTAMP,11,1);
insert into Valor_Sensor values (3,12,CURRENT_TIMESTAMP,111,1);
insert into Valor_Sensor values (4,132,CURRENT_TIMESTAMP,1,1);
insert into Valor_Sensor values (5,123,CURRENT_TIMESTAMP,1,1);
insert into Valor_Sensor values (6,421,CURRENT_TIMESTAMP,1,1);
insert into Valor_Sensor values (7,1,CURRENT_TIMESTAMP,11,1);
insert into Valor_Sensor values (8,21,CURRENT_TIMESTAMP,11,1);
insert into Valor_Sensor values (9,44,CURRENT_TIMESTAMP,11,1);
insert into Valor_Sensor values (10,453,CURRENT_TIMESTAMP,111,1);
insert into Valor_Sensor values (11,12,CURRENT_TIMESTAMP,111,1);
insert into Valor_Sensor values (12,3,CURRENT_TIMESTAMP,1,1);
insert into Valor_Sensor values (13,3,CURRENT_TIMESTAMP,3,1);
insert into Valor_Sensor values (14,5,CURRENT_TIMESTAMP,4,1);
insert into Valor_Sensor values (15,7,CURRENT_TIMESTAMP,5,1);
insert into Valor_Sensor values (16,7,CURRENT_TIMESTAMP,6,1);
insert into Valor_Sensor values (17,4,CURRENT_TIMESTAMP,7,1);
insert into Valor_Sensor values (18,657,CURRENT_TIMESTAMP,8,1);
insert into Valor_Sensor values (19,123,CURRENT_TIMESTAMP,9,1);
insert into Valor_Sensor  values(20,13,CURRENT_TIMESTAMP,10,1);

/*======Sistema_De_Rega======================================================================*/

insert into Sistema_De_Rega values (1,5,'Bombeada','Aspersão');
insert into Sistema_De_Rega values (2,5,'Bombeada','Gotejamento');
insert into Sistema_De_Rega values (3,5,'Bombeada','Pulverização');
insert into Sistema_De_Rega values (4,5,'Gravidade','Aspersão');
insert into Sistema_De_Rega values (5,5,'Gravidade','Gotejamento');
insert into Sistema_De_Rega values (6,5,'Gravidade','Pulverização');

/*======Parcela_Agricola======================================================================*/

insert into Parcela_Agricola values (1,'Milho','42m^2',1,1,1,1);
insert into Parcela_Agricola values (2,'Soja','46m^2',2,1,1,1);
insert into Parcela_Agricola values (3,'Trigo','52m^2',3,1,2,1);
insert into Parcela_Agricola values (4,'Cevada','40m^2',4,1,2,1);
insert into Parcela_Agricola values (5,'Beterraba','35m^2',5,1,3,1);
insert into Parcela_Agricola values (6,'Feno','50m^2',6,2,3,1);
insert into Parcela_Agricola values (7,'Bambu','39m^2',7,2,4,1);
insert into Parcela_Agricola values (8,'Algodão','44m^2',8,2,4,1);
insert into Parcela_Agricola values (9,'Girassol','44m^2',9,2,5,1);
insert into Parcela_Agricola values (10,'Azeitonas','41m^2',10,2,6,1);

/*======Controlador======================================================================*/

insert into Controlador values (1,1);
insert into Controlador values (2,2);
insert into Controlador values (3,3);
insert into Controlador values (4,4);
insert into Controlador values (5,5);
insert into Controlador values (6,6);

/*======Electrovalvulas======================================================================*/

insert into Electrovalvulas values (1,1);
insert into Electrovalvulas values (2,1);
insert into Electrovalvulas values (3,2);
insert into Electrovalvulas values (4,2);
insert into Electrovalvulas values (5,3);
insert into Electrovalvulas values (6,3);
insert into Electrovalvulas values (7,4);
insert into Electrovalvulas values (8,4);
insert into Electrovalvulas values (9,5);
insert into Electrovalvulas values (10,5);
insert into Electrovalvulas values (11,6);
insert into Electrovalvulas values (12,6);

/*======Colheita======================================================================*/

insert into Colheita values (1,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (2,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (3,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (4,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (5,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (6,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (7,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (8,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (9,Date '2022-11-10',123,1,1,1,1);
insert into Colheita values (10,Date '2022-11-10',123,1,2,2,1);
insert into Colheita values (11,Date '2022-11-10',123,2,2,2,1);
insert into Colheita values (12,Date '2022-11-10',123,2,2,2,1);
insert into Colheita values (13,Date '2022-11-10',123,2,2,2,1);
insert into Colheita values (14,Date '2022-11-10',123,2,2,2,1);
insert into Colheita values (15,Date '2022-11-10',123,2,2,2,1);
insert into Colheita values (16,Date '2022-11-10',123,2,3,3,1);
insert into Colheita values (17,Date '2022-11-10',123,2,3,3,1);
insert into Colheita values (18,Date '2022-11-10',123,2,3,3,1);
insert into Colheita values (19,Date '2022-11-10',123,2,3,3,1);
insert into Colheita values (20,Date '2022-11-10',123,2,3,3,1);


/*======Filtro======================================================================*/

insert into Filtro values (1,'Shima');
insert into Filtro values (2,'Dakar');
insert into Filtro values (3,'Isep');
insert into Filtro values (4,'Nestlé');
insert into Filtro values (5,'Mr.World');

/*======Sistema_De_Rega_Filtro======================================================================*/

insert into Sistema_De_Rega_Filtro values (1,1);
insert into Sistema_De_Rega_Filtro values (1,2);
insert into Sistema_De_Rega_Filtro values (1,3);
insert into Sistema_De_Rega_Filtro values (1,4);
insert into Sistema_De_Rega_Filtro values (1,5);
insert into Sistema_De_Rega_Filtro values (2,2);
insert into Sistema_De_Rega_Filtro values (2,3);
insert into Sistema_De_Rega_Filtro values (2,4);
insert into Sistema_De_Rega_Filtro values (2,5);
insert into Sistema_De_Rega_Filtro values (3,3);
insert into Sistema_De_Rega_Filtro values (3,4);
insert into Sistema_De_Rega_Filtro values (3,5);
insert into Sistema_De_Rega_Filtro values (4,4);
insert into Sistema_De_Rega_Filtro values (4,5);
insert into Sistema_De_Rega_Filtro values (5,5);
insert into Sistema_De_Rega_Filtro values (6,5);


/*======Plano_De_Rega======================================================================*/

insert into Plano_De_Rega values (1, 1, 1, 2);
insert into Plano_De_Rega values (2, 1, 1, 1);
insert into Plano_De_Rega values (3, 2, 2, 1);
insert into Plano_De_Rega values (4, 3, 3, 1);
insert into Plano_De_Rega values (5, 4, 4, 2);
insert into Plano_De_Rega values (6, 4, 4, 3);
insert into Plano_De_Rega values (7, 5, 4, 1);
insert into Plano_De_Rega values (8, 6, 5, 1);
insert into Plano_De_Rega values (9, 6, 5, 3);
insert into Plano_De_Rega values (10, 6, 5, 2);

/*======Rega======================================================================*/

insert into Rega values (1, DATE '2022-11-27', 1, 1, 9, 1);
insert into Rega values (2, DATE '2022-11-28', 1, 1, 9, 1);
insert into Rega values (3, DATE '2022-11-23', 1, 2, 10, 2);
insert into Rega values (4, DATE '2022-11-20', 1, 2, 5, 3);
insert into Rega values (5, DATE '2022-11-24', 1, 3, 5, 4);
insert into Rega values (6, DATE '2022-11-25', 1, 1, 5, 5);
insert into Rega values (7, DATE '2022-11-15', 1, 3, 6, 6);
insert into Rega values (8, DATE '2022-11-27', 1, 3, 7, 7);
insert into Rega values (9, DATE '2022-11-20', 1, 2, 7, 8);
insert into Rega values (10, DATE '2022-11-20', 1, 3, 10, 9);
insert into Rega values (11, DATE '2022-11-14', 1, 1, 10, 10);
insert into Rega values (12, DATE '2022-11-15', 1, 2, 8, 10);

/*======Fertilização======================================================================*/

insert into Fertilização values (1,1,1,1,'Via Foliar',Date '2022-11-10',5);
insert into Fertilização values (2,1,1,2,'Fertirega',Date '2022-11-10',5);
insert into Fertilização values (3,1,2,3,'Aplicação Direta',Date '2022-11-10',5);
insert into Fertilização values (4,1,2,4,'Via Foliar',Date '2022-11-10',5);
insert into Fertilização values (5,1,3,5,'Fertirega',Date '2022-11-10',5);
insert into Fertilização values (6,1,3,6,'Aplicação Direta',Date '2022-11-10',5);
insert into Fertilização values (7,1,4,7,'Via Foliar',Date '2022-11-10',5);
insert into Fertilização values (8,1,4,8,'Fertirega',Date '2022-11-10',5);
insert into Fertilização values (9,1,5,9,'Aplicação Direta',Date '2022-11-10',5);
insert into Fertilização values (10,1,5,10,'Fertirega',Date '2022-11-10',5);

/*============================================================================*/
