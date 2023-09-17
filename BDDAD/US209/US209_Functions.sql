/*======Estado======================================================================*/
CREATE OR REPLACE FUNCTION fncListOrdersByStatus(orderStatus ENCOMENDA.ESTADO_ENCOMENDA%type) RETURN SYS_REFCURSOR AS resultado SYS_REFCURSOR;
BEGIN
OPEN resultado FOR SELECT * FROM ENCOMENDA WHERE ESTADO_ENCOMENDA = orderStatus;
return resultado;
end;

/*======Data De Registo======================================================================*/

CREATE OR REPLACE FUNCTION fncListOrdersByRegisterDate(dataRegisto ENCOMENDA.Data_Registo%type) RETURN SYS_REFCURSOR AS resultado SYS_REFCURSOR;
BEGIN
OPEN resultado FOR SELECT * FROM ENCOMENDA WHERE Data_Registo = dataRegisto;
return resultado;
end;

/*======Cliente======================================================================*/

CREATE OR REPLACE FUNCTION fncListOrdersByClient(idCliente ENCOMENDA.Cliente_id%type) RETURN SYS_REFCURSOR AS resultado SYS_REFCURSOR;
BEGIN
OPEN resultado FOR SELECT * FROM ENCOMENDA WHERE Cliente_id = idCliente;
return resultado;
end;

/*======OrderByData======================================================================*/

CREATE OR REPLACE FUNCTION fncUS209ListOrdersByDateOfOrder RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT * FROM ENCOMENDA ORDER BY Data_Registo;
return result;
end;

/*======OrderByCliente======================================================================*/

CREATE OR REPLACE FUNCTION fncUS209ListOrdersByCliente RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT * FROM ENCOMENDA ORDER BY Cliente_id;
return result;
end;

/*======OrderByEncomenda======================================================================*/

CREATE OR REPLACE FUNCTION fncUS209ListOrdersByEncomenda RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT * FROM ENCOMENDA ORDER BY id;
return result;
end;

/*======OrderByValor======================================================================*/

select distinct Encomenda.Cliente_id,Encomenda.id as "ENCOMENDA_ID",(select sum(Cabaz.Preço) from Cabaz where Cabaz.Encomenda_id = Encomenda.id) as "PREÇO_TOTAL"
from Encomenda, Cabaz order by PREÇO_TOTAL;

/*======OrderByEstado======================================================================*/

CREATE OR REPLACE FUNCTION fncUS209ListOrdersByEstado RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT * FROM ENCOMENDA ORDER BY Estado_Encomenda;
return result;
end;

/*============================================================================================*/