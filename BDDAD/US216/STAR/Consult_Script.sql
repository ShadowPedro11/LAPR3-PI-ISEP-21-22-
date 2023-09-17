create or replace view evolucaoMensalPorTipoCulturaEHub as
    select T.Ano, T.Mes, H.Hub_id as "ID Hub", Pr.Tipo_Cultura, sum(V.montante) as "MONTANTE"
    from Venda V inner join Tempo T on T.Tempo_id = V.Tempo_id
             inner join Produto Pr on Pr.Produto_id = V.Produto_id
             inner join Hub H on H.Hub_id = V.Hub_id
    group by T.Ano, T.Mes, H.Hub_id, Pr.Tipo_Cultura
    order by Pr.Tipo_Cultura, T.Ano, T.Mes, H.Hub_id

select * from evolucaoMensalPorTipoCulturaEHub