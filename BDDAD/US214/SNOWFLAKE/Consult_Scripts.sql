/*======Evolução Últimos 5 Anos=========*/
create or replace view lastFiveYearsEvolution as
    select T.Ano, Pr.Cultura_id as "ID Cultura", Pr.Tipo_Cultura_id as "Tipo Cultura",S.Nome as "Setor Nome", sum(P.Quantidade) as quantidade
    from Produção P
         inner join Tempo T on T.Tempo_id = P.Tempo_id
         inner join Produto Pr on Pr.Produto_id = P.Produto_id
         inner join Setor S on S.Setor_id = P.Setor_id
    where P.Setor_id = 1  and P.Produto_id = 1 and (T.Ano >= to_number(to_char(sysdate, 'YYYY'), '9999') - 5)
    group by T.Ano, Pr.Cultura_id, Pr.Tipo_Cultura_id, S.Nome
    order by T.Ano asc;

select * from lastFiveYearsEvolution

/*======Comparar Vendas=========*/
create or replace view compararVendas as
select
    T1.Ano as "Ano 1",
    sum(V1.montante) as "Montante 1",
    (select Ano from Tempo where Ano = 2021 and Mes = 1) as "Ano 2",
    (select sum(V2.montante)
     from Venda V2
              inner join Tempo T2 on T2.Tempo_id = V2.Tempo_id
     where T2.Ano = 2021) as "Montante 2",
    (sum(V1.montante) - (select sum(V2.montante) as "Montante2"
                         from Venda V2
                                  inner join Tempo T2 on T2.Tempo_id = V2.Tempo_id
                         where T2.Ano = 2021)) as "Diferença Montante"

from Venda V1
         inner join Tempo T1 on T1.Tempo_id = V1.Tempo_id
where T1.Ano = 2020
group by T1.Ano

select * from compararVendas


/*======Analisar Vendas Mensais por Tipo de Cultura=========*/
create or replace view evolucaoMensalPorTipoCultura as
select T.Ano, T.Mes,TC.Nome, sum(V.montante) as "MONTANTE"
from Venda V inner join Tempo T on T.Tempo_id = V.Tempo_id
             inner join Produto Pr on Pr.Produto_id = V.Produto_id
             inner join Tipo_Cultura TC on TC.Tipo_Cultura_Id = Pr.Tipo_Cultura_Id
group by T.Ano, T.Mes, TC.Nome
order by TC.Nome, T.Ano, T.Mes

select * from evolucaoMensalPorTipoCultura;

