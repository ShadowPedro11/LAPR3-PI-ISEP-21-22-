/*====Cliente====================*/
insert into Cliente values (1, 249876451, 'cliente1@gmail.com', 'Porto');
insert into Cliente values (2, 283098432, 'cliente2@gmail.com', 'Braga');
insert into Cliente values (3, 123046783, 'cliente3@gmail.com', 'Lisboa');
insert into Cliente values (4, 364819482, 'cliente4@gmail.com', 'Coimbra');
insert into Cliente values (5, 421548382, 'cliente5@gmail.com', 'Porto');

/*====Tipo_Hub======================*/
insert into Tipo_Hub values ('E');
insert into Tipo_Hub values ('P');

/*====Hub======================*/
insert into Hub values (1, 'E', 'Porto');
insert into Hub values (2, 'P', 'Braga');
insert into Hub values (3, 'P', 'Lisboa');
insert into Hub values (4, 'E', 'Coimbra');
insert into Hub values (5, 'E', 'Porto');

/*====Cultura======================*/

insert into Cultura values (1,'Na');
insert into Cultura values (2,'Ne');
insert into Cultura values (3,'Ni');
insert into Cultura values (4,'No');
insert into Cultura values (5,'Nu');

/*====Tipo_Cultura======================*/

insert into Tipo_Cultura values (1,'Batata');
insert into Tipo_Cultura values (2,'Tomate');
insert into Tipo_Cultura values (3,'Cebola');
insert into Tipo_Cultura values (4,'Cenoura');
insert into Tipo_Cultura values (5,'Nabo');

/*====Produto======================*/
insert into Produto values (1, 1, 1);
insert into Produto values (2, 2, 2);
insert into Produto values (3, 3, 3);
insert into Produto values (4, 4, 4);
insert into Produto values (5, 5, 5);

/*====Setor======================*/
insert into Setor values (1, 'Setor1');
insert into Setor values (2, 'Setor2');
insert into Setor values (3, 'Setor3');
insert into Setor values (4, 'Setor4');
insert into Setor values (5, 'Setor5');


/*====Tempo-Ano_Mes======================*/

DECLARE
Tempo_id NUMBER := 1;
flag NUMBER := 0;
BEGIN
    FOR ano IN 2010..2022
        LOOP
            insert into Ano values (ano);
                FOR mes IN 1..12
                    LOOP
                        if flag = 0 then insert into Mes values (mes);
                        end if;
                        insert into Tempo values (Tempo_id, ano, mes);
                        Tempo_id := Tempo_id + 1;
                    end loop;
                    flag := flag + 1;
        end loop;
end;


/*====Produção-Venda======================*/
DECLARE
    Venda_id       NUMBER := 1;
    Produção_id    NUMBER := 1;
BEGIN

FOR cliente_id IN 1..5
        LOOP
            FOR Tempo_id IN 1..156
                LOOP
                    FOR Produto_id IN 1..5
                        LOOP
                            INSERT INTO Venda VALUES (Venda_id,ROUND(DBMS_RANDOM.VALUE(1, 100)), Tempo_id,Produto_id,ROUND(DBMS_RANDOM.VALUE(1, 5)),cliente_id);
                            Venda_id := Venda_id + 1;
                        end loop;
                end loop;
        end loop;

FOR Setor_id IN 1..5
        LOOP
            FOR Tempo_id IN 1..156
                LOOP
                    FOR Produto_id IN 1..5
                        LOOP
                            INSERT INTO Produção values (Produção_id,ROUND(DBMS_RANDOM.VALUE(1, 100)),Tempo_id,Setor_id,Produto_id);
                            Produção_id := Produção_id + 1;
                        end loop;
                end loop;
        end loop;
end;