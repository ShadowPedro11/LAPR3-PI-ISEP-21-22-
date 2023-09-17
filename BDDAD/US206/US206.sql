/*======================================3===========================================*/

create or replace view Setores_Por_Exploração_Ordenado_Por_Ordem_Alfabética as

select * from Parcela_Agricola order by Designação asc;


/*=====================================4============================================*/

create or replace view Setores_Por_Exploração_Ordenado_Por_Tamanho_Crescente as

select * from Parcela_Agricola order by Area asc;

create or replace view Setores_Por_Exploração_Ordenado_Por_Tamanho_Decrescente as

select * from Parcela_Agricola order by Area desc;


/*======================================5==========================================*/

create or replace view Setores_Por_Cultura_E_Tipo as

select Parcela_Agricola.*, Cultura.Tipo
from Parcela_Agricola
         join Cultura
              on Parcela_Agricola.cultura_id = Cultura.id
order by Cultura.Tipo, Cultura.id;

/*================================================================================*/


select * from Setores_Por_Exploração_Ordenado_Por_Ordem_Alfabética;
select * from Setores_Por_Exploração_Ordenado_Por_Tamanho_Crescente;
select * from Setores_Por_Exploração_Ordenado_Por_Tamanho_Decrescente;
select * from Setores_Por_Cultura_E_Tipo;