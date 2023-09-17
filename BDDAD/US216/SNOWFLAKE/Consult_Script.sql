create or replace view evolucaoMensalPorTipoCulturaEHub as
select T.Ano, T.Mes, H.Hub_id as "ID Hub", TC.Nome, sum(V.montante) as "MONTANTE"
from Venda V inner join Tempo T on T.Tempo_id = V.Tempo_id
             inner join Produto Pr on Pr.Produto_id = V.Produto_id
             inner join Hub H on H.Hub_id = V.Hub_id
             inner join Tipo_Cultura TC on TC.Tipo_Cultura_Id = Pr.Tipo_Cultura_Id
group by T.Ano, T.Mes, H.Hub_id, TC.Nome
order by TC.Nome, T.Ano, T.Mes, H.Hub_id

select * from evolucaoMensalPorTipoCulturaEHub