//===========Calendário de Fertilizações====================
select * from Fertilização

//===========Registar uma operação==========================

CREATE OR REPLACE PROCEDURE US210RegistOperation(cdc Fertilização.Caderno_De_Campo_id%type,
                                                 fcd Fertilização.Factor_De_Produção_id%type,
                                                 paid Fertilização.Parcela_Agricola_id%type,
                                                 ma Fertilização.Metodo_Aplicação%type,
                                                 dataFert Fertilização.Data%type,
                                                 qntdFert Fertilização.Quantidade%type) IS
    max_id integer;
    new_id integer;

BEGIN
select max(id) into max_id from Fertilização;
new_id := max_id + 1;
insert into Fertilização values (new_id,cdc,fcd,paid,ma,dataFert,qntdFert);
end;


exec US210RegistOperation(1,1,1,'Via Foliar',Date '2022-11-10',5);


//===========Verificar restrições uma semana antes==========================

//===========Calcular diferença de datas=====================

CREATE OR REPLACE FUNCTION calculate_date_difference (date1 Fertilização.Data%type, date2 Fertilização.Data%type) RETURN integer AS
    result integer;
BEGIN
result := date2-date1;
return result;
END;


DECLARE
c integer;
BEGIN
   c := calculate_date_difference(Date '2022-12-28',Date '2023-01-04');
   dbms_output.put_line('Date Difference:: ' || c);
END;

//===========================================================

CREATE OR REPLACE procedure US210CHECKRESTRICTIONPROCEDURE
IS c integer;dateDiff integer ;
CURSOR c_fertilizacao IS
SELECT * FROM Fertilização;
v_fertilizacao c_fertilizacao%ROWTYPE;
BEGIN
OPEN c_fertilizacao;
LOOP
FETCH c_fertilizacao INTO v_fertilizacao;
    EXIT WHEN c_fertilizacao%NOTFOUND;
    dateDiff := calculate_date_difference(CURRENT_DATE,v_fertilizacao.Data);
    IF dateDiff = 7 THEN
        c := fncUS210RestrictionCount(v_fertilizacao.Data,v_fertilizacao.Parcela_Agricola_id,v_fertilizacao.Factor_De_Produção_id);
        IF c <> 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'ERROR:RESTRICTION FOUND');
            DELETE FROM Fertilização WHERE id = v_fertilizacao.id;
END IF;
END IF;
END LOOP;
CLOSE c_fertilizacao;
END;

exec US210CHECKRESTRICTIONPROCEDURE;

//===========Verificar restrições na sua criação==========================

CREATE OR REPLACE TRIGGER US210FertilizaçãoTRIGGER
BEFORE INSERT OR UPDATE ON Fertilização
                            FOR EACH ROW
DECLARE
c integer;
BEGIN
  c := fncUS210RestrictionCount(:NEW.Data,:NEW.Parcela_Agricola_id,:NEW.Factor_De_Produção_id);
  IF c <> 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'ERROR: RESTRICTION FOUND');
END IF;
END;

//===========Numero de  Restrições num determinado Setor, numa determinada data, para um determinado fator==========================

CREATE OR REPLACE FUNCTION fncUS210RestrictionCount (restrictionDate Fertilização.Data%type, parcelaId Parcela_Agricola.id%type, fator Fertilização.Factor_De_Produção_id%type) RETURN integer AS
    result integer;
BEGIN
SELECT COUNT(id) into result FROM Parcela_Agricola_Restrição par
                                      INNER JOIN  Restrição r on par.Restrição_id = r.id
WHERE par.Parcela_Agricola_id = parcelaId AND (restrictionDate BETWEEN r.dateInitial and r.dateEnd) AND (fator = r.Factor_De_Produçãoid);
return result;
end;


DECLARE
c integer;
BEGIN
   c := fncUS210RestrictionCount(Date '2022-11-11',1,2);
   dbms_output.put_line('NUM:: ' || c);
END;


//===========Listar Restrições num determinado Setor, numa determinada data==========================

CREATE OR REPLACE FUNCTION fncUS210ListRestrictions (restrictionDate Fertilização.Data%type, parcelaId Parcela_Agricola.id%type) RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT r.* FROM Parcela_Agricola_Restrição par
INNER JOIN  Restrição r on par.Restrição_id = r.id
WHERE par.Parcela_Agricola_id = parcelaId AND (restrictionDate BETWEEN r.dateInitial and r.dateEnd)
ORDER BY id;
return result;
end;



DECLARE
l_cursor       SYS_REFCURSOR;
  id                                number(10);
  Factor_De_Produçãoid              number(10);
  dateInitial                       date;
  dateEnd                           date;
  restrictionDate                   date;
  parcelaId                         number(10);

BEGIN
restrictionDate := Date '2022-11-10';
parcelaId := 1;
  l_cursor :=  fncUS210ListRestrictions(restrictionDate,parcelaId);

  LOOP
FETCH l_cursor
    INTO  id,Factor_De_Produçãoid,dateInitial,dateEnd;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || id || ' | Factor De Produção id:' || Factor_De_Produçãoid || ' |Date Initial:' || dateInitial || ' |Date end:' || dateEnd );
END LOOP;
CLOSE l_cursor;
END;


SELECT r.* FROM Parcela_Agricola_Restrição par
                    INNER JOIN  Restrição r on par.Restrição_id = r.id
WHERE par.Parcela_Agricola_id = parcelaId AND (restrictionDate BETWEEN r.dateInitial and r.dateEnd)
ORDER BY id;




//===========Listar operações planeadas num determinado período por setor, por ordem cronolódica==========================

CREATE OR REPLACE FUNCTION fncUS210ListPlanedOperations (dateInitial Fertilização.Data%type, dateEnd Fertilização.Data%type) RETURN SYS_REFCURSOR AS
    result Sys_Refcursor;
BEGIN
OPEN result FOR SELECT * FROM Fertilização
WHERE Data BETWEEN dateInitial and dateEnd
ORDER BY Parcela_Agricola_id,Data;
return result;
end;


DECLARE
l_cursor       SYS_REFCURSOR;
  id                                number(10);
  Caderno_De_Campo_id               number(10);
  Factor_De_Produção_id             number(10);
  Parcela_Agricola_id               number(10);
  Metodo_Aplicação                  varchar2(100);
  Data                              date;
  Quantidade                        number(10);
  Date1                              date;
  Date2                              date;
BEGIN
Date1 := Date '2022-11-01';
Date2 := Date '2022-11-12';
  l_cursor :=  fncUS210ListPlanedOperations(Date1,Date2);

  LOOP
FETCH l_cursor
    INTO  id,Caderno_De_Campo_id,Factor_De_Produção_id,Parcela_Agricola_id,Metodo_Aplicação,Data,Quantidade;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id:' || id || ' | Factor De Produção id:' || Factor_De_Produção_id || ' | Metodo Aplicação:' || Metodo_Aplicação || ' | Data:' || Data || ' | Quantidade:' || Quantidade);
END LOOP;
CLOSE l_cursor;
END;