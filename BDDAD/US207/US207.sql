
/*===================================================""=============================================*/
SELECT parcela_agricola.id,
       parcela_agricola.designação,
       parcela_agricola.area,cultura_id,
       parcela_agricola.gestor_agricola_id,
       parcela_agricola.sistema_de_rega_id,
       parcela_agricola.instalação_agricola_id,
       colheita.quantidade

FROM parcela_agricola
         FULL OUTER JOIN colheita
                         ON colheita.parcela_agricola_id=parcela_agricola.id
ORDER BY  colheita.quantidade DESC;

/*===================================================""=============================================*/
/* devolve a tabela com o lucro de cada parcela*/
select distinct parcela_agricola.id,
                (select sum(Produto.preço) from Produto, Colheita where Colheita.parcela_agricola_id = parcela_agricola.id)
                    AS "lucro"
from parcela_agricola, Produto,colheita
order by "lucro"