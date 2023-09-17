-- 1 ----------------------------------------------------------------------------------------------
CREATE OR REPLACE procedure US215PROCEDUREADDELEMENTSTOHUB
IS str string(5);id_strstring string(5); lon float; lat float;

CURSOR c_input_hub IS
SELECT * FROM input_hub;
v_input_hub c_input_hub%ROWTYPE;
BEGIN
OPEN c_input_hub;
LOOP
FETCH c_input_hub INTO v_input_hub;
    EXIT WHEN c_input_hub%NOTFOUND;

    str := SUBSTR(v_input_hub.input_string,-2);

    IF str LIKE 'P%' OR  str LIKE 'E%' THEN

        id_strstring := SUBSTR(v_input_hub.input_string,1,4);
        lon := TO_NUMBER(SUBSTR(v_input_hub.input_string, 6, 7));
        lat := TO_NUMBER(SUBSTR(v_input_hub.input_string, 14, 7));
Insert into Hub values (null,id_strstring,lon,lat);

END IF;

END LOOP;
CLOSE c_input_hub;
END;

exec US215PROCEDUREADDELEMENTSTOHUB;
-- 2 ----------------------------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER US215ClienteTrigger
BEFORE INSERT OR UPDATE ON Cliente
                            FOR EACH ROW
DECLARE
c integer;
BEGIN
SELECT COUNT(*) INTO c FROM Hub where Morada_id = :NEW.MORADA_ENTREGA_ID;
IF c <= 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'ERROR: RESTRICTION FOUND - INVALID "MORADA_ENTREGA_ID"');
END IF;
END;


create or replace procedure replace_default_hub(id_cliente Cliente.id%type,id_entrega Cliente.Morada_Entrega_id%type)
is
begin
update cliente set Morada_Entrega_id = id_entrega where cliente.id = id_cliente;
end;


-- 3 ----------------------------------------------------------------------------------------------

create or replace procedure makeDelivery (Rede_De_Distribuição_id Rede_De_Distribuição.id%type, Estado_Encomenda Encomenda.Estado_Encomenda%type,
                                            Data_Registo Encomenda.Data_Registo%type, Data_Pagamento Encomenda.Data_Pagamento%type,
                                            Data_Entrega Encomenda.Data_Entrega%type, Cliente_id Cliente.id%type, Cliente_Morada_Correspondencia_id Morada.id%type,
                                            Cliente_Morada_Entrega_id Morada.id%type)
is
    last_encomenda_id integer;

begin
select count(id) into last_encomenda_id from Encomenda;
last_encomenda_id := last_encomenda_id + 1;
insert into Encomenda values (last_encomenda_id, Rede_De_Distribuição_id, Estado_Encomenda, Data_Registo, Data_Pagamento, Data_Entrega, Cliente_id, Cliente_Morada_Correspondencia_id, Cliente_Morada_Entrega_id);

end;

create or replace procedure makeDelivery_default_hub (Rede_De_Distribuição_id Rede_De_Distribuição.id%type, Estado_Encomenda Encomenda.Estado_Encomenda%type,
                                            Data_Registo Encomenda.Data_Registo%type, Data_Pagamento Encomenda.Data_Pagamento%type,
                                            Data_Entrega Encomenda.Data_Entrega%type, Cliente_id Cliente.id%type)
is
    last_encomenda_id integer;
    Cliente_Morada_Correspondencia_id integer;
    Cliente_Morada_Entrega_id integer;

begin
select count(id) into last_encomenda_id from Encomenda;
last_encomenda_id := last_encomenda_id + 1;

select Morada_Correspondencia_id into Cliente_Morada_Correspondencia_id from Cliente where CLiente.id = Cliente_id;
select Morada_Entrega_id into Cliente_Morada_Entrega_id from Cliente where CLiente.id = Cliente_id;

insert into Encomenda values (last_encomenda_id, Rede_De_Distribuição_id, Estado_Encomenda, Data_Registo, Data_Pagamento, Data_Entrega, Cliente_id, Cliente_Morada_Correspondencia_id, Cliente_Morada_Entrega_id);

end;