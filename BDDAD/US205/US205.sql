create or replace procedure inserir_novo_cliente(nome Cliente.nome%type,
                                            numero_fiscal Cliente.Numero_Fiscal%type,
                                            email Cliente.Email%type,
                                            plafond Cliente.Plafond%type,
                                            m_corres Cliente.Morada_Correspondencia_id%type,
                                            m_entrega Cliente.Morada_Entrega_id%type)
is
    last_id integer;

begin
select max(id) into last_id from CLiente;
last_id := last_id + 1;
insert into Cliente values(last_id, nome, numero_fiscal, email, plafond, m_corres, m_entrega);
DBMS_OUTPUT.PUT_LINE('id: ' || last_id || ' morada correspondencia: ' || m_corres || ' morada entrega: ' || m_entrega);
end;

/*==========================================================*/

create or replace procedure inserir_novo_cliente_morada (nome Cliente.nome%type,
                                            numero_fiscal Cliente.Numero_Fiscal%type,
                                            email Cliente.Email%type,
                                            plafond Cliente.Plafond%type,
                                            cod_postal Morada.Codigo_postal%type,
                                            localidade Morada.Localidade%type)
is
 id_morada integer;

begin
    id_morada := inserir_nova_morada_com_return(cod_postal, localidade);
    inserir_novo_cliente(nome, numero_fiscal, email, plafond, id_morada, id_morada);
end;

/*==========================================================*/

create or replace function inserir_nova_morada_com_return (cod_postal Morada.Codigo_postal%type,
                                                localidade Morada.Localidade%type) return integer
is
 last_id integer;

begin
select max(id) into last_id from Morada;
last_id := last_id + 1;
insert into Morada values (last_id, cod_postal, cod_postal);
return last_id;
end;

/*==========================================================*/

begin
    inserir_novo_cliente_morada('joao', 123456789, 'joao@gmail.com', 5012, '4418-123', 'Azurara');
end;

select * from encomenda

/* 3 ==========================================================*/

create or replace procedure atualizar_encomendas(id_cliente Cliente.id%type)
is
    numero_encomendas integer;
    acumulado_encomendas float;

begin
select count(*) into acumulado_encomendas from encomenda where encomenda.Cliente_id = id_cliente;
select count(*) into numero_encomendas from encomenda where encomenda.Cliente_id = id_cliente
                                                        and sysdate - encomenda.DATA_REGISTO < 365;
update Cliente set QUANTIDADE_ENCOMENDAS_ULTIMO_ANO = numero_encomendas where Cliente.id = id_cliente;
update CLiente set ACUMULADO_ENCOMENDAS_ULTIMO_ANO = acumulado_encomendas where Cliente.id = id_cliente;
end;


/*  4 ==========================================================*/


create or replace function calc_nivel (id_cliente Cliente.id%type) return varchar
is
    numero_inc integer;
    date_diff integer;
    soma_encomendas_pagas integer;
begin
    numero_inc := tem_incidentes(id_cliente);
    if numero_inc > 0 then
select SYSDATE - max(data_ocorrencia) into date_diff from incidente where cliente_id = id_cliente;
if date_diff < 365 then
            return 'C';
        elsif date_diff > 365 then
            soma_encomendas_pagas := sum_encomendas_pagas(id_cliente);
            if soma_encomendas_pagas > 10000 then
                return 'A';
            elsif soma_encomendas_pagas > 5000 then
                return 'B';
end if;
end if;
end if;
return NULL;
end;

/*===============================================================================*/


create or replace function sum_encomendas_pagas(id_cliente Cliente.id%type) return integer
is
    soma integer;

begin
select sum(preço) into soma from cabaz
                                     inner join encomenda on
            cabaz.encomenda_id = encomenda.id and encomenda.cliente_id = id_cliente
where encomenda.ESTADO_ENCOMENDA like 'paga';
return soma;
end;

/*===============================================================================*/

create or replace function tem_incidentes (id_cliente Cliente.id%type) return integer
is
    c_count integer;

begin
select count(*) into c_count from incidente where incidente.cliente_id = id_cliente;
return c_count;
end;

select count(*) from incidente where incidente.cliente_id = 5;

/*===============================================================================*/

create or replace function ultimo_incidente (id_cliente Cliente.id%type) return date
is
    ult_incidente Date;

begin
select max(DATA_OCORRENCIA)  into ult_incidente from incidente where incidente.cliente_id = id_cliente;
return ult_incidente;
end;

/*===============================================================================*/

create or replace function encomendas_ainda_nao_pagas(id_cliente Cliente.id%type) return integer
is
    qtd_encomendas integer;

begin
select count(*) into qtd_encomendas from Encomenda where Cliente_id = id_cliente and Estado_Encomenda like 'entregue'
                                                     and DATA_PAGAMENTO is null;
return qtd_encomendas;

end;


create view nivel_cliente as select id as cliente_id, calc_nivel(cliente.id) as incidencia_nivel,
                                    ultimo_incidente(id) as ultimo_incidente, sum_encomendas_pagas(id) as volume_encomendas_pagas,
                                    encomendas_ainda_nao_pagas(id) as encomendas_entregues_nao_pagas from cliente;


/* 5 ===============================================================================*/



create or replace function fator_risco(id_cliente Cliente.id%type) return float
is
    fator_risc float;
    valor_incidente_ultimo_ano integer;
    numero_encomendas_desde_ult_incidente integer;
    ultima_ocorrencia DATE;

begin
    valor_incidente_ultimo_ano := valor_incidente(id_cliente);
select max(DATA_OCORRENCIA) into ultima_ocorrencia from incidente where cliente_id = id_cliente;
numero_encomendas_desde_ult_incidente := numero_encomendas(id_cliente, ultima_ocorrencia);
    if valor_incidente_ultimo_ano > 0 then
        if numero_encomendas_desde_ult_incidente > 0 then
            fator_risc := valor_incidente_ultimo_ano/numero_encomendas_desde_ult_incidente;
end if;
end if;
return fator_risc;
end;

create or replace function valor_incidente(id_cliente Cliente.id%type) return integer
is
    valor_incidencias integer;

begin
select sum(Valor) into valor_incidencias from incidente where CLIENTE_ID = id_cliente and
            sysdate - incidente.DATA_OCORRENCIA < 365;
return valor_incidencias;
end;

create or replace function numero_encomendas(id_cliente Cliente.id%type,
                                            ultima_ocorrencia Incidente.DATA_OCORRENCIA%type) return integer
is
    numero_encomendas integer;
begin
select count(*) into numero_encomendas from encomenda
where encomenda.DATA_REGISTO > ultima_ocorrencia;
return numero_encomendas;
end;