CREATE OR REPLACE FUNCTION calculate_date_difference (date1 Fertilização.Data%type, date2 Fertilização.Data%type) RETURN integer AS
    result integer;
BEGIN
result := date2-date1;
return result;
END;


DECLARE
c integer;
BEGIN
   c := calculate_date_difference(current_date,Date '2023-01-04');
   dbms_output.put_line('Date Difference:: ' || c);
END;


//=========================1=====================

CREATE OR REPLACE TRIGGER US211DeleteTrigger
BEFORE DELETE ON Fertilização
                            FOR EACH ROW
DECLARE
c integer;
BEGIN
  c := calculate_date_difference(current_date,:OLD.Data);
  IF c < 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'ERROR: INVALID DATE');
END IF;
END;


//==========================2=====================

CREATE OR REPLACE TRIGGER US211UpdateTrigger
BEFORE UPDATE ON Fertilização
                  FOR EACH ROW
DECLARE
c integer;
BEGIN
  c := calculate_date_difference(current_date,:OLD.Data);
  IF c < 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'ERROR: INVALID DATE');
END IF;
END;



//===================================================

UPDATE Fertilização set METODO_APLICAÇÃO = 'Foliar' where id = 5;

DELETE from Fertilização where id = 4;

select *from Fertilização;