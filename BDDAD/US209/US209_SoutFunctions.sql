/*======Estado======================================================================*/
DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date; 
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncListOrdersByStatus('paga');
            
  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Estado:' || Estado_Encomenda);
END LOOP;
CLOSE l_cursor;
END;

/*======Data De Registo======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncListOrdersByRegisterDate(DATE '2022-11-01');

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Data De Entrega:' || Data_Registo);
END LOOP;
CLOSE l_cursor;
END;

/*======Cliente======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncListOrdersByClient(1);

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Id Cliente:' || Cliente_id);
END LOOP;
CLOSE l_cursor;
END;

/*======OrderByData======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncUS209ListOrdersByDateOfOrder;

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Data De Entrega:' || Data_Registo);
END LOOP;
CLOSE l_cursor;
END;

/*======OrderByCliente======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncUS209ListOrdersByCliente;

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Id Cliente:' || Cliente_id);
END LOOP;
CLOSE l_cursor;
END;

/*======OrderByEncomenda======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncUS209ListOrdersByEncomenda;

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Id:' || l_id);
END LOOP;
CLOSE l_cursor;
END;

/*======OrderByEstado======================================================================*/

DECLARE
l_cursor       SYS_REFCURSOR;
  l_id                                number(10);
  Rede_De_Distribuição_id           number(10);
  Estado_Encomenda                  varchar2(50);
  Data_Registo                      date;
  Data_Pagamento                    date;
  Data_Entrega                      date;
  Cliente_id                        number(10);
  Cliente_Morada_Correspondencia_id number(10);
  Cliente_Morada_Entrega_id         number(10);
BEGIN
  l_cursor :=  fncUS209ListOrdersByEstado;

  LOOP
FETCH l_cursor
    INTO  l_id, Rede_De_Distribuição_id,Estado_Encomenda,Data_Registo,Data_Pagamento,Data_Entrega,Cliente_id,Cliente_Morada_Correspondencia_id,Cliente_Morada_Entrega_id;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || l_id || ' | Estado Encomenda:' || Estado_Encomenda);
END LOOP;
CLOSE l_cursor;
END;

/*============================================================================================*/