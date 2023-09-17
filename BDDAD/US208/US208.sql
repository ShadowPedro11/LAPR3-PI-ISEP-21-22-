--Criar um novo factor de produção com uma ficha técnica previamente criada
create or replace procedure create_factor_prod(
    Nome_Comercial_param Factor_De_Produção.Nome_Comercial%TYPE,
    Tipo_param Factor_De_Produção.Tipo%TYPE,
    Formulação_param Factor_De_Produção.Formulação%TYPE,
    Fornecedor_param Factor_De_Produção.Fornecedor%TYPE,
    Ficha_Tecnica_id Factor_De_Produção.Ficha_Tecnica_id%TYPE)

is
    ultimo_id_factor_producao integer;
    id_factor_producao_ins integer;

begin
select max(id) into ultimo_id_factor_producao from Factor_De_Produção ;
id_factor_producao_ins := ultimo_id_factor_producao + 1;

insert into Factor_De_Produção values (id_factor_producao_ins, Nome_Comercial_param, Tipo_param, Formulação_param, Fornecedor_param, Ficha_Tecnica_id);

dbms_output.put_line('Factor de Produção criada com o id: ' || id_factor_producao_ins);
exception
    when others then
        dbms_output.put_line('Não foi possível criar um novo Factor de Produção!');
end;

begin
    create_factor_prod(
    'TESTE',
    'fertilizantes',
    'granulado',
    'Continente',
    1);
end;

--Criar uma nova Ficha Técnica
create or replace procedure create_ficha_tecnica(
    DesignaçãoElem_param Elemento_Nutritivo_Organico.Designação%TYPE,
    QuantidadeElem_param Elemento_Nutritivo_Organico.Quantidade%TYPE,
    DesignaçãoSubs_param Substancia_Organica.Designação%TYPE,
    QuantidadeSubs_param Substancia_Organica.Quantidade%TYPE)

is
    ultimo_id_ficha_tecnica integer;
    id_ficha_tecnica_ins integer;

    ultimo_id_elem_nut integer;
    id_elem_nut_ins integer;

    ultimo_id_subst_org integer;
    id_subst_org_ins integer;

begin
select max(id) into ultimo_id_ficha_tecnica from Factor_De_Produção ;
id_ficha_tecnica_ins := ultimo_id_ficha_tecnica + 1;
insert into Ficha_Tecnica values (id_ficha_tecnica_ins);

select max(id) into ultimo_id_elem_nut from Elemento_Nutritivo_Organico;
id_elem_nut_ins := ultimo_id_elem_nut + 1;
insert into Elemento_Nutritivo_Organico values (id_elem_nut_ins,DesignaçãoElem_param, QuantidadeElem_param);
insert into Ficha_Tecnica_Elemento_Nutritivo_Organico values (id_ficha_tecnica_ins, id_elem_nut_ins);

select max(id) into ultimo_id_subst_org from Substancia_Organica;
id_subst_org_ins := ultimo_id_subst_org + 1;
insert into Substancia_Organica values (id_subst_org_ins, DesignaçãoSubs_param, QuantidadeSubs_param);
insert into Ficha_Tecnica_Substancia_Organica values (id_ficha_tecnica_ins, id_subst_org_ins);

dbms_output.put_line('Ficha Tecnica criada com o id: ' || id_ficha_tecnica_ins);
exception
    when others then
        dbms_output.put_line('Não foi possível criar uma nova Ficha Técnica!');
end;

begin
    create_ficha_tecnica(
    'des1',
    'quan1',
    'des2',
    'quan2');
end;