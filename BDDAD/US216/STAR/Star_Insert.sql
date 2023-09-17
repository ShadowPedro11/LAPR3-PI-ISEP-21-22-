/*====Tempo======================*/
declare
    tempo_id       NUMBER(8, 0)  := 1;
    venda_cont     NUMBER(10, 0) := 0;
    prod_cont      NUMBER(10, 0) := 0;

begin
for ano in 2010..2022
        loop
            for mes in 1..12
                loop
                    insert into Tempo values (tempo_id, ano, mes);
                    tempo_id := tempo_id + 1;
                end loop;
        end loop;
end;

/*====Cliente====================*/
insert into Cliente values (1, 249876451, 'cliente1@gmail.com', 'Porto');
insert into Cliente values (2, 283098432, 'cliente2@gmail.com', 'Braga');
insert into Cliente values (3, 123046783, 'cliente3@gmail.com', 'Lisboa');
insert into Cliente values (4, 364819482, 'cliente4@gmail.com', 'Coimbra');
insert into Cliente values (5, 421548382, 'cliente5@gmail.com', 'Porto');

/*====Hub======================*/
insert into Hub values (1, 'E', 'Porto');
insert into Hub values (2, 'P', 'Braga');
insert into Hub values (3, 'P', 'Lisboa');
insert into Hub values (4, 'E', 'Coimbra');
insert into Hub values (5, 'E', 'Porto');

/*====Produto======================*/
insert into Produto values (1, 'Batata', 1);
insert into Produto values (2, 'Tomate', 2);
insert into Produto values (3, 'Cebola', 3);
insert into Produto values (4, 'Cenoura', 4);
insert into Produto values (5, 'Nabo', 5);

/*====Setor======================*/
insert into Setor values (1, 'Setor1');
insert into Setor values (2, 'Setor2');
insert into Setor values (3, 'Setor3');
insert into Setor values (4, 'Setor4');
insert into Setor values (5, 'Setor5');

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

FOR Cultura_id IN 1..5
        LOOP
            FOR Tempo_id IN 1..156
                LOOP
                    FOR Produto_id IN 1..5
                        LOOP
                            INSERT INTO Produção values (Produção_id,ROUND(DBMS_RANDOM.VALUE(1, 100)),Tempo_id,Cultura_id,Produto_id);
                            Produção_id := Produção_id + 1;
                        end loop;
                end loop;
        end loop;
end;

