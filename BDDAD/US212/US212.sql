--1 Existe uma função que devolve o n-ésimo elemento de cada tuplo de input_sensor
create or replace function get_nth_element(n integer) return SYS_REFCURSOR
as
    result SYS_REFCURSOR;
    identificador_sensor_tamanho constant integer := 5;
    tipo_sensor_tamanho constant integer := 2;
    valor_lido_tamanho constant integer := 3;
    valor_referencia_tamanho constant integer := 10;
    instante_leitura_tamanho constant integer := 5;

    identificador_sensor_inicio constant integer := 1;
    tipo_sensor_inicio constant integer := 6;
    valor_lido_inicio constant integer := 8;
    valor_referencia_inicio constant integer := 11;
    instante_leitura_inicio constant integer := 21;

    inicio integer;
    tamanho integer;

begin
    if n = 1 then
        inicio := identificador_sensor_inicio;
        tamanho := identificador_sensor_tamanho;
    elsif n = 2 then
        inicio := tipo_sensor_inicio;
        tamanho := tipo_sensor_tamanho;
    elsif n = 3 then
        inicio := valor_lido_inicio;
        tamanho := valor_lido_tamanho;
    elsif n = 4 then
        inicio := valor_referencia_inicio;
        tamanho := valor_referencia_tamanho;
    elsif n = 5 then
        inicio := instante_leitura_inicio;
        tamanho := instante_leitura_tamanho;
else
        RETURN result;
end if;

open result for select substr(INPUT_STRING, inicio, tamanho) from input_sensor;

return result;
end;

-----------------------------------------------------------------------------------------------------------------------------------------------------

declare
l_cursor SYS_REFCURSOR;
    element varchar2(25);

begin
   l_cursor := get_nth_element(5);

   loop
FETCH l_cursor INTO element;
        EXIT WHEN l_cursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(element);
end loop;
end;

-- 2 - 6

create or replace function save_reading(identificador_sensor varchar, tipo_sensor varchar, valor_lido integer, valor_referencia integer,
                                        instante_leitura timestamp) return integer
is
   last_id_valor_sensor integer;
begin

    if tipo_sensor in ('HS', 'Pl', 'TS', 'VV', 'TA', 'HA', 'PA') then
        if valor_lido between 0 and 100 then
            insert into Sensor values (identificador_sensor, tipo_sensor, valor_referencia, 1);
select count(id) into last_id_valor_sensor from Valor_Sensor;
last_id_valor_sensor := last_id_valor_sensor + 1;
insert into Valor_Sensor values (last_id_valor_sensor, valor_lido, instante_leitura, identificador_sensor, 1, tipo_sensor, valor_referencia);
return 0;
else
            DBMS_OUTPUT.PUT_LINE('Read value ' || valor_lido || ' is not between 0 and 100');
end if;
else
        DBMS_OUTPUT.PUT_LINE('Unkown sensor type: ' || tipo_sensor);
end if;
return 1;
end;

-----------------------------------------------------------------------------------------------------------------------------------------------------

create or replace function numero_erros(identificador_sensor varchar2) return integer
is
    numero_erros integer;
begin
select count(Numero_De_Erros) into numero_erros from Registo_Não_Inserido where Sensor_id like identificador_sensor;
numero_erros := 1;
return numero_erros;
end;

-----------------------------------------------------------------------------------------------------------------------------------------------------


create or replace procedure transfer_sensor_data
is
    r_cursor SYS_REFCURSOR;
    identificador_sensor varchar2(5);
    tipo_sensor varchar2(2);
    valor_lido integer;
    valor_referencia integer;
    instante_leitura timestamp;
    leitura varchar2(25);

    op integer;
    valores_lidos integer := 0;
    valores_aceites integer := 0;
    valores_negados integer := 0;
    last_id integer;

begin
select count(Data_Hora) into last_id from Registo_Sensor;
last_id := last_id + 1;
insert into Registo_Sensor values (last_id, sysdate, 0, 0, 0);

open r_cursor for select INPUT_STRING from input_sensor;

loop
FETCH r_cursor INTO leitura;
        EXIT WHEN r_cursor%NOTFOUND;

        identificador_sensor := substr(leitura, 1, 5);
        tipo_sensor := substr(leitura, 6, 2);
        valor_lido := to_number(substr(leitura, 8, 3));
        valor_referencia := to_number(substr(leitura, 11, 10));
        instante_leitura := to_timestamp(to_char(sysdate, 'YYYY-MM-DD') || ' ' || substr(leitura, -5), 'YYYY-MM-DD HH24:MI');

        valores_lidos := valores_lidos + 1;

        op := save_reading(identificador_sensor, tipo_sensor, valor_lido, valor_referencia, instante_leitura);

        if op = 0 then
            valores_aceites := valores_aceites + 1;
delete from input_sensor where INPUT_STRING like leitura;
else
            valores_negados := valores_negados + 1;
insert into Registo_Não_Inserido values (last_id, identificador_sensor, numero_erros(identificador_sensor));
end if;

end loop;

update Registo_Sensor set Registos_Lidos = valores_lidos, Registos_Inseridos = valores_aceites, Registos_Não_Inseridos = valores_negados where id = last_id;
DBMS_OUTPUT.PUT_LINE('Registos lidos: ' || valores_lidos);
    DBMS_OUTPUT.PUT_LINE('Registos transferidos: ' || valores_aceites);
    DBMS_OUTPUT.PUT_LINE('Registos com erros: ' || valores_negados);

end;

--==============================================================================================================================================================--

begin
    transfer_sensor_data;
end;

--setup
begin
delete from input_sensor;

    insert into input_sensor values ('62943HS078783897638710:35');
    insert into input_sensor values ('12345zS078783897323711:34');
    insert into input_sensor values ('62323HA101783897348712:35');


    delete from Valor_sensor where SENSOR_ID like 62943;
    delete from Sensor where id like 62943;

    delete from Registo_Não_Inserido;
    delete from Registo_Sensor;

end;