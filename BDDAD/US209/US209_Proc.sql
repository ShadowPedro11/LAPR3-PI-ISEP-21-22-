create or replace function sum_encomendas_Naopagas(id_cliente Cliente.id%type) return integer
is
    soma integer;

begin
    select sum(preço) into soma from cabaz
    inner join encomenda on
    cabaz.encomenda_id = encomenda.id and encomenda.cliente_id = id_cliente
    where encomenda.ESTADO_ENCOMENDA like 'registada';
    return soma;
end;


//==============================================================
    DECLARE
c integer;
BEGIN
   c := sum_encomendas_Naopagas(2);
   dbms_output.put_line('Valor Encomendas não pagas: ' || c);
END;

//==============================================================

CREATE OR REPLACE PROCEDURE us209Proc(id_cliente Cliente.id%type,
                                        cabaz_id Cabaz.id%type,
                                        Preço Cabaz.Preço%type,
                                        Data_De_Registo Cabaz.Data_De_Registo%type,
                                        Rede_De_Distribuição_id Cabaz.Rede_De_Distribuição_id%type,
                                        Instalação_Agricola_id Cabaz.Instalação_Agricola_id%type,
                                        Encomenda_id Cabaz.Encomenda_id%type) AS
    Npago integer;
    Plafond float;
BEGIN
    Npago := sum_encomendas_Naopagas(id_cliente);
select Cliente.Plafond into Plafond from Cliente where id_cliente = Cliente.id;
dbms_output.put_line('Valor a Pagar ' || Npago);
    dbms_output.put_line('Plafond ' || Plafond);

    if Plafond-Npago > Preço then insert into Cabaz values (cabaz_id,Preço,Data_De_Registo,Rede_De_Distribuição_id,Instalação_Agricola_id,Encomenda_id);
else dbms_output.put_line('Plafond Insuficiente');
end if;
end;

//==============================================================

exec us209Proc(2,55,9999.5,DATE '2022-11-22',4,2,15);
exec us209Proc(2,56,1.5,DATE '2022-11-22',4,2,15);