
/*Ponto 1, criar tabela*/
CREATE TABLE audit_tables(
    new_data_id number(10),
    old_data_id number(10),
    use_name VARCHAR2(30),
    entry_date VARCHAR2(30),
    operation VARCHAR2(30)
)

/*Ponto 2  triggers*/

/*Trigger para tabela de Encomenda*/
CREATE OR REPLACE TRIGGER Encomenda_audit
BEFORE INSERT OR DELETE OR UPDATE ON Encomenda
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,NULL,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Insert');
    ELSIF DELETING THEN
        INSERT INTO auditoria
    VALUES (NULL,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
    ELSIF UPDATING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
END IF;
END;

/*Trigger para tabela de cliente*/

CREATE OR REPLACE TRIGGER client_auditoria
BEFORE INSERT OR DELETE OR UPDATE ON Cliente
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,NULL,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Insert');
    ELSIF DELETING THEN
        INSERT INTO auditoria
    VALUES (NULL,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
    ELSIF UPDATING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
END IF;
END;

/*Trigger para tablea Cabaz*/
CREATE OR REPLACE TRIGGER Cabaz_audit
BEFORE INSERT OR DELETE OR UPDATE ON Cabaz
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,NULL,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Insert');
    ELSIF DELETING THEN
        INSERT INTO auditoria
    VALUES (NULL,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
    ELSIF UPDATING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
END IF;
END;

/*Trigger para Caderno de Campo*/
CREATE OR REPLACE TRIGGER Caderno_De_Campo_audit
BEFORE INSERT OR DELETE OR UPDATE ON Caderno_De_Campo
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,NULL,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Insert');
    ELSIF DELETING THEN
        INSERT INTO auditoria
    VALUES (NULL,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
    ELSIF UPDATING THEN
        INSERT INTO auditoria
    VALUES (:NEW.id,:OLD.id,'Cliente',TO_CHAR(sysdate,'DD/MON/YYYY HH24:MI:SS'),'Delete');
END IF;
END;

/* Listar tabela*/
SELECT * FROM audit