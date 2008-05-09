--call sp_update_db();
CREATE PROCEDURE `sp_update_db`()
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

SET @IMPORT_TIME:=UNIX_TIMESTAMP();
SET @APPROVED='APPROVED';
SET @TEAM_ID=24; /* THIS IS VIPFE TEAM */
SET @ACTIVITY_CREATOR=92;  /* THIS IS ATL MEMBER */
SET @ACTIVITY_CODE='AMP-BOLIVIA';
SET @COMMITMENT=0;
SET @DISBURSMENT=1;
SET @FUNDING_ADJUSMENT_PLANNED=0;
SET @FUNDING_ADJUSMENT_ACTUAL=1;
SET @ORG_ROLE_CODE='MA';
SET @FUNDING_PERSPECTIVE=2;
SET @FUNDING_CURRENCY_ID=21;
SET @MAX_ORDER_NO=0;
SET @MAX_LEVEL_ORDER_NO=0;
SET @FUNDING_MODALITY=3;
SET @FINANCING_INSTRUMENT=116;
 /*  THIS SHOULD BE REMOVED BECASE NOW WE HAVE CORRECT QUERY */
SET @STATUS_CLASS_ID=6;
SELECT @STATUS_CLASS_ID := C.ID
FROM AMP_CATEGORY_CLASS C
WHERE C.KEYNAME = 'ACTIVITY_STATUS';
SET @LEVEL_CLASS_ID=7;
SELECT @LEVEL_CLASS_ID := C.ID
FROM AMP_CATEGORY_CLASS C
WHERE C.KEYNAME = 'IMPLEMENTATION_LEVEL';
SELECT @MAX_ORDER_NO := MAX(A.INDEX_COLUMN)
FROM AMP_CATEGORY_VALUE A
WHERE A.AMP_CATEGORY_CLASS_ID = @STATUS_CLASS_ID;
SELECT @MAX_LEVEL_ORDER_NO := MAX(A.INDEX_COLUMN)
FROM AMP_CATEGORY_VALUE A
WHERE A.AMP_CATEGORY_CLASS_ID = @LEVEL_CLASS_ID;
SET @TIMESTMP:=UNIX_TIMESTAMP();
SET @AVTIVITY_DESC='AIM-DESC-IMPORT-';
SET @AVTIVITY_OBJ='AIM-OBJ-IMPORT-';
SET @EDITOR_ORDER=0;
 /*  THIS IS NEEDED FOR KALOSHA'S IMPORT TOOL, JUST SHOULD NOT BE EMPTY COS IT IS PRIMITIVE INT */
SET @AMP_SITE_ID = 'AMP';
SET @SUBPROG_PREFIX='EBRP';


SELECT '............ UPDATING FROM SIS FIN ...............' AS MSG;

SELECT CONCAT('UPDATE STARTED DATABASE AT ', CURRENT_TIME) AS MSG;
SELECT @AMP_SEC_SCHEME_ID := AMP_SEC_SCHEME_ID
FROM AMP_SECTOR_SCHEME
WHERE upper(SEC_SCHEME_CODE) = 'BOL_IMP';
INSERT INTO AMP_SECTOR(AMP_SEC_SCHEME_ID, SECTOR_CODE, NAME, OLD_ID)
SELECT @AMP_SEC_SCHEME_ID,
       C.CODSEC,
       C.DESCSEC,
       C.CODSEC
FROM SISFIN_DB.SEC C
WHERE C.CODSEC NOT IN (SELECT OLD_ID FROM AMP_SECTOR);
SELECT CONCAT(ROW_COUNT(), ' SECTORS WERE ADDED') AS MSG;


INSERT INTO AMP_SECTOR(AMP_SEC_SCHEME_ID, SECTOR_CODE, NAME)
SELECT SCH.AMP_SEC_SCHEME_ID,
       C.VALDATO,
       C.INTERP
FROM SISFIN_DB.CLAVES AS C,
     AMP_SECTOR_SCHEME AS SCH
WHERE upper(SCH.SEC_SCHEME_CODE) = 'BOL_COMPO_IMP' AND
      upper(C.NOMDATO) = 'CVETIPCOMP' AND
      (SELECT COUNT(*) FROM AMP_SECTOR WHERE AMP_SEC_SCHEME_ID =
      SCH.AMP_SEC_SCHEME_ID AND SECTOR_CODE = C.VALDATO AND NAME = C.INTERP) =
      0;
SELECT CONCAT(ROW_COUNT(), ' COMPONENTS WERE ADDED') AS MSG;


INSERT INTO AMP_ORGANISATION(OLD_ID, NAME, ORG_CODE, ACRONYM)
SELECT CODAGE,
       NOMAGE,
       CODAGE,
       CODAGE
FROM SISFIN_DB.`AGE` AS O
WHERE TRIM(CODAGE) NOT IN (SELECT TRIM(OLD_ID) FROM AMP_ORGANISATION);
SELECT CONCAT(ROW_COUNT(), ' ORGANISATIONS WERE ADDED') AS MSG;


UPDATE AMP_ORGANISATION AS ORG,
       SISFIN_DB.`AGE` AS O,
       AMP_ORG_TYPE AS T
SET ORG.ORG_TYPE_ID = T.AMP_ORG_TYPE_ID
WHERE ORG.OLD_ID = O.CODAGE AND
      upper(O.CVEBIMULTI) = 'B' AND
      upper(T.ORG_TYPE_CODE) = 'BIL';
SELECT CONCAT(ROW_COUNT(), ' AMP_ORGANISATION BIL WERE UPDATED') AS MSG;


UPDATE AMP_ORGANISATION AS ORG,
       SISFIN_DB.`AGE` AS O,
       AMP_ORG_GROUP AS AOG
SET ORG.ORG_GRP_ID = AOG.AMP_ORG_GRP_ID
WHERE ORG.OLD_ID = O.CODAGE AND
      AOG.ORG_GRP_CODE = O.CVEORG;
SELECT CONCAT(ROW_COUNT(), ' AMP_ORGANISATION GROUP WERE UPDATED') AS MSG;


UPDATE AMP_ORGANISATION AS ORG,
       SISFIN_DB.`AGE` AS O,
       AMP_ORG_GROUP AS AOG
SET ORG.ORG_GRP_ID = AOG.AMP_ORG_GRP_ID
WHERE ORG.OLD_ID = O.CODAGE AND
      upper(AOG.ORG_GRP_CODE) = 'ALEMA' AND
      upper(O.CVEORG) = 'ALEM';
SELECT CONCAT(ROW_COUNT(), ' MULTIPLE CODES  WERE UPDATED') AS MSG;


INSERT INTO AMP_ORGANISATION(OLD_ID, NAME, ORG_CODE, ACRONYM, ORG_TYPE_ID)
SELECT CODENT,
       NOMENT,
       CODENT,
       CODENT,
       TT.AMP_ORG_TYPE_ID
FROM SISFIN_DB.`ENT` AS E,
     AMP_ORG_TYPE AS TT
WHERE upper(TT.ORG_TYPE_CODE) = 'OTHER' AND
      NOT EXISTS (SELECT A.CODAGE FROM SISFIN_DB.`AGE` AS A WHERE A.CODAGE =
      E.CODENT AND UPPER(A.NOMAGE) = UPPER(E.NOMENT)) AND
      E.CODENT NOT IN (SELECT OLD_ID FROM AMP_ORGANISATION);
SELECT CONCAT(ROW_COUNT(), ' EXECUTING AGENCIES  WERE UPDATED') AS MSG;


INSERT INTO AMP_TERMS_ASSIST(TERMS_ASSIST_CODE, TERMS_ASSIST_NAME, OLD_ID)
SELECT LVL.VALDATO,
       LVL.INTERP,
       LVL.VALDATO
FROM SISFIN_DB.`CLAVES` LVL
WHERE upper(LVL.NOMDATO) = 'CVECOOP' AND
      LVL.VALDATO NOT IN (SELECT TERMS_ASSIST_CODE FROM AMP_TERMS_ASSIST);
SELECT CONCAT(ROW_COUNT(), ' AMP_TERMS_ASSIST WERE UPDATED') AS MSG;


INSERT INTO CLASI_PND(CODE, DESCRIPTION)
SELECT COD_PND,
       DESCRIPCION
FROM SISFIN_DB.CLASIF_PND
WHERE COD_PND NOT IN (SELECT CODE FROM CLASI_PND);
SELECT CONCAT(ROW_COUNT(), ' CLASI_PND WERE ADDED') AS MSG;

SELECT ' UPDATING ALL CONVENIOS' AS MSG;

UPDATE AMP_ACTIVITY A, (SELECT * FROM SISFIN_DB.`CONV`) AS C
	SET A.NAME = C.NOMCONV,
    A.PROPOSED_APPROVAL_DATE = C.FECHPROGEFEC,
    A.PROPOSED_START_DATE = C.FECHPROGPRDES,
    A.ACTUAL_COMPLETION_DATE = C.FECHPROGULDES,
    A.CONVENIO_DATE_FILTER = C.FECHPROGULDES,
    A.CONVENIO_NUMCONT = NUMCONT,
    A.ACTUAL_START_DATE = C.FECHCONT,
    A.APPROVAL_STATUS = @APPROVED,
    A.PROJ_COST_AMOUNT = MONTORIG,
    A.TOTALCOST = MONTOUS,
    A.OLD_STATUS_ID = CVEALC,
    A.DRAFT = 0,
    A.CLASSI_CODE = COD_PND
WHERE C.STATCONV != 'C' AND
      C.STATCONV != 'A' AND
      C.NUMCONV = A.OLD_ID;

SELECT CASE ROW_COUNT() WHEN 0 THEN ' THERE ARE NO CHANGES ON CONVENIOS' else CONCAT( ROW_COUNT(),' CONVENIOS UPDATED') END 	 AS MSG;


INSERT INTO AMP_ACTIVITY
(
OLD_ID,
AMP_ID,
NAME,
DESCRIPTION,
OBJECTIVES,
CONTRACTORS,
PROGRAM_DESCRIPTION,
`CONDITION`,
STATUS_REASON,
PROPOSED_APPROVAL_DATE,
PROPOSED_START_DATE,
ACTUAL_COMPLETION_DATE,
CONVENIO_DATE_FILTER,
CONVENIO_NUMCONT,
ACTUAL_START_DATE,
AMP_TEAM_ID,
APPROVAL_STATUS,
PROJ_COST_AMOUNT,
ACTIVITY_CREATOR,
TOTALCOST,
OLD_STATUS_ID,
DRAFT,
CLASSI_CODE
)
SELECT
C.NUMCONV,
C.NUMCONV,
C.NOMCONV,
CONCAT(@AVTIVITY_DESC, @TIMESTMP:=@TIMESTMP+1),
CONCAT(@AVTIVITY_OBJ, @TIMESTMP:=@TIMESTMP+1),
' ',
' ',
' ',
' ',
C.FECHPROGEFEC,
C.FECHPROGPRDES,
C.FECHPROGULDES,
C.FECHPROGULDES, -- AMP-2387 I DON'T WANT TO REWRITE ACTUAL_COMPLETION_DATE
NUMCONT,
C.FECHCONT,
@TEAM_ID,
@APPROVED,
MONTORIG,
@ACTIVITY_CREATOR,
MONTOUS,
CVEALC,
0,
COD_PND
FROM  SISFIN_DB.`CONV` AS C WHERE C.STATCONV!='C' AND C.STATCONV!='A' AND C.NUMCONV NOT IN
(SELECT OLD_ID FROM AMP_ACTIVITY);

SELECT CONCAT(ROW_COUNT(), ' ACTIVITY ADDED') AS MSG;

/* BEGIN - AMP-2387 */

DROP TEMPORARY TABLE IF EXISTS `TMP_TBL_CONVENIO_DATE_FILTER`;
CREATE TEMPORARY TABLE `TMP_TBL_CONVENIO_DATE_FILTER` (
  `NUMCONV` VARCHAR(5) NOT NULL,
  `MAXFECHVIGENM` DATETIME NOT NULL,
  PRIMARY KEY (`NUMCONV`)
)ENGINE = INNODB;


INSERT INTO TMP_TBL_CONVENIO_DATE_FILTER
SELECT E.NUMCONV, MAX(E.FECHVIGENM)
FROM SISFIN_DB.ENM E
WHERE  upper(E.TIPENM) = 'PU' AND E.FECHVIGENM IS NOT NULL
GROUP BY E.NUMCONV;

UPDATE AMP_ACTIVITY AS A, TMP_TBL_CONVENIO_DATE_FILTER TMP
SET A.CONVENIO_DATE_FILTER = TMP.MAXFECHVIGENM
WHERE A.OLD_ID = TMP.NUMCONV;


SELECT CONCAT(ROW_COUNT(), ' CONVENIO_DATE_FILTER WERE UPDATED') AS MSG;

DROP TEMPORARY TABLE `TMP_TBL_CONVENIO_DATE_FILTER`;
/* END - AMP-2387 */

UPDATE AMP_ACTIVITY AS A,
       SISFIN_DB.`USU` AS U,
       SISFIN_DB.`CONV` C
SET A.MOFED_CNT_LAST_NAME = U.NOMBREUSUARIO
WHERE A.OLD_ID = C.NUMCONV AND
      C.CODUSU = U.CODUSU;

SELECT CONCAT(ROW_COUNT(), ' CONTACTS WERE UPDATED') AS MSG;


SELECT 'DELETING ALL ORG RELATED TO ACTIVITIES'  AS MSG;
TRUNCATE TABLE AMP_ORG_ROLE;

INSERT INTO AMP_ORG_ROLE(ACTIVITY, ORGANISATION, ROLE, PERCENTAGE)
SELECT A.AMP_ACTIVITY_ID,
       O.AMP_ORG_ID,
       AR.AMP_ROLE_ID,
       M.PORCPART
FROM SISFIN_DB.`CONV_ENTEJEC` AS M,
     AMP_ACTIVITY AS A,
     AMP_ORGANISATION AS O,
     AMP_ROLE AS AR,
     SISFIN_DB.`ENT` AS ENT
WHERE upper(AR.ROLE_CODE) = 'EA' AND
      A.OLD_ID = M.NUMCONV AND
      ENT.CODENT = O.OLD_ID AND
      O.OLD_ID = M.CODENTEJEC AND
      UPPER(ENT.NOMENT) = UPPER(O.NAME);

SELECT CONCAT(ROW_COUNT(), ' ORG ROLE WERE ADDED') AS MSG;

SELECT 'ADDING DONNORS TO ACTIVITIES'  AS MSG;
INSERT INTO AMP_ORG_ROLE(ACTIVITY, ORGANISATION, ROLE, PERCENTAGE)
SELECT A.AMP_ACTIVITY_ID,
       O.AMP_ORG_ID,
       1,
       NULL
FROM AMP_FUNDING A
     INNER JOIN AMP_ORGANISATION O ON A.AMP_DONOR_ORG_ID = O.AMP_ORG_ID;

SELECT CONCAT(ROW_COUNT(), ' DONNORS WERE ADDED') AS MSG;

SELECT 'DELETING ISSUES'  AS MSG;
TRUNCATE TABLE AMP_ISSUES;

INSERT INTO AMP_ISSUES(NAME, AMP_ACTIVITY_ID)
SELECT C.SIT_ACTUAL,
       A.AMP_ACTIVITY_ID
FROM SISFIN_DB.`CONV` AS C,
     AMP_ACTIVITY AS A
WHERE (C.SIT_ACTUAL IS NOT NULL) AND
      C.NUMCONV = A.OLD_ID;

SELECT CONCAT(ROW_COUNT(), ' ISSUES 1 WERE ADDED ') AS MSG;


INSERT INTO AMP_ISSUES(NAME, AMP_ACTIVITY_ID)
SELECT C.TRAMITE_ACTUAL,
       A.AMP_ACTIVITY_ID
FROM SISFIN_DB.`CONV` C,
     AMP_ACTIVITY A
WHERE (C.TRAMITE_ACTUAL IS NOT NULL) AND
      C.NUMCONV = A.OLD_ID;

SELECT CONCAT(ROW_COUNT(), ' ISSUES 2 WERE ADDED ') AS MSG;

INSERT INTO AMP_ISSUES(NAME, AMP_ACTIVITY_ID)
SELECT C.TIP_EJECUCION,
       A.AMP_ACTIVITY_ID
FROM SISFIN_DB.`CONV` C,
     AMP_ACTIVITY A
WHERE (C.TIP_EJECUCION IS NOT NULL) AND
      C.NUMCONV = A.OLD_ID;

SELECT CONCAT(ROW_COUNT(), ' ISSUES 3 WERE ADDED ') AS MSG;

INSERT INTO AMP_ISSUES(NAME, AMP_ACTIVITY_ID)
SELECT C.MARCA,
       A.AMP_ACTIVITY_ID
FROM SISFIN_DB.`CONV` C,
     AMP_ACTIVITY A
WHERE (C.MARCA IS NOT NULL) AND
      C.NUMCONV = A.OLD_ID;
SELECT CONCAT(ROW_COUNT(), ' ISSUES 4 WERE ADDED ') AS MSG;


SELECT 'DELETING ALL SECTOR RELATED TO ACTIVITIES'  AS MSG;
TRUNCATE TABLE AMP_ACTIVITY_SECTOR;

INSERT INTO AMP_ACTIVITY_SECTOR(AMP_ACTIVITY_ID, AMP_SECTOR_ID,
 SECTOR_PERCENTAGE)
SELECT ACT.AMP_ACTIVITY_ID,
       SEC.AMP_SECTOR_ID,
       AC.PORCSEC
FROM AMP_ACTIVITY AS ACT,
     AMP_SECTOR AS SEC,
     SISFIN_DB.CONV_SEC AS AC
WHERE ACT.OLD_ID = AC.NUMCONV AND
      SEC.OLD_ID = AC.CODSEC;

SELECT CONCAT(ROW_COUNT(), ' SECTOR RELATED TO ACTIVITIES WERE ADDED ') AS MSG;


SELECT 'DELETING ALL AMP_ACTIVITIES_CATEGORYVALUES'  AS MSG;
TRUNCATE TABLE AMP_ACTIVITIES_CATEGORYVALUES;

INSERT INTO AMP_ACTIVITIES_CATEGORYVALUES (AMP_ACTIVITY_ID, AMP_CATEGORYVALUE_ID)
SELECT ACT.AMP_ACTIVITY_ID,
       CAT.ID
FROM AMP_ACTIVITY AS ACT,
     AMP_CATEGORY_VALUE AS CAT,
     SISFIN_DB.`CONV` ACTO,
     SISFIN_DB.`CLAVES` AS CLA
WHERE CAT.AMP_CATEGORY_CLASS_ID = @STATUS_CLASS_ID AND
      CAT.CATEGORY_VALUE = CLA.INTERP AND
      ACTO.NUMCONV = ACT.OLD_ID AND
      ACTO.STATCONV = CLA.VALDATO AND
      upper(CLA.NOMDATO) = 'STATCONV';
SELECT CONCAT(ROW_COUNT(), ' STATUSES WERE MAPPED ') AS MSG;



INSERT INTO AMP_ACTIVITIES_CATEGORYVALUES(AMP_ACTIVITY_ID, AMP_CATEGORYVALUE_ID
)
SELECT ACT.AMP_ACTIVITY_ID,
       CAT.ID
FROM AMP_ACTIVITY AS ACT,
     AMP_CATEGORY_VALUE AS CAT,
     SISFIN_DB.`CONV` ACTO,
     SISFIN_DB.`CLAVES` AS CLA
WHERE CAT.AMP_CATEGORY_CLASS_ID = @LEVEL_CLASS_ID AND
      CAT.CATEGORY_VALUE = CLA.INTERP AND
      ACTO.NUMCONV = ACT.OLD_ID AND
      ACTO.CVEALC = CLA.VALDATO AND
      upper(CLA.NOMDATO) = 'CVEALC';

SELECT CONCAT(ROW_COUNT(), '  IMPLEMENTATION LEVELS  WERE MAPPED ') AS MSG;



SELECT 'DELETING DESCRIPTIONS '  AS MSG;
TRUNCATE TABLE DG_EDITOR;
INSERT INTO DG_EDITOR(EDITOR_KEY, LANGUAGE, SITE_ID, BODY, LAST_MOD_DATE,
 CREATION_IP, ORDER_INDEX)
SELECT A.DESCRIPTION,
       'EN',
       @AMP_SITE_ID,
       C.DESCCONV,
       NOW(),
       '127.0.0.1',
       @EDITOR_ORDER
FROM SISFIN_DB.`CONV` AS C,
     AMP_ACTIVITY AS A
WHERE C.NUMCONV = A.OLD_ID;
SELECT CONCAT(ROW_COUNT(), ' EN DESCRIPTIONS WERE MAPPED ') AS MSG;


INSERT INTO DG_EDITOR(EDITOR_KEY, LANGUAGE, SITE_ID, BODY, LAST_MOD_DATE,
 CREATION_IP, ORDER_INDEX)
SELECT A.DESCRIPTION,
       'ES',
       @AMP_SITE_ID,
       C.DESCCONV,
       NOW(),
       '127.0.0.1',
       @EDITOR_ORDER
FROM SISFIN_DB.`CONV` AS C,
     AMP_ACTIVITY AS A
WHERE C.NUMCONV = A.OLD_ID;

SELECT CONCAT(ROW_COUNT(), ' ES DESCRIPTIONS WERE MAPPED ') AS MSG;

SELECT 'DELETING ALL FUNDING ' AS MSG;
TRUNCATE TABLE AMP_FUNDING;

INSERT INTO AMP_FUNDING(FINANCING_ID, AMP_DONOR_ORG_ID, AMP_ACTIVITY_ID,
 TYPE_OF_ASSISTANCE_CATEGORY_VALUE_ID, AMP_MODALITY_ID,
 FINANCING_INSTR_CATEGORY_VALUE_ID)
SELECT O.CODAGE,
       ORG.AMP_ORG_ID,
       A.AMP_ACTIVITY_ID,
       CATVAL.ID,
       @FUNDING_MODALITY,
       111
FROM SISFIN_DB.`CONV` AS C,
     AMP_ACTIVITY AS A,
     AMP_ORGANISATION AS ORG,
     SISFIN_DB.`AGE` AS O,
     SISFIN_DB.`CLAVES` AS TA,
     AMP_CATEGORY_VALUE AS CATVAL
WHERE C.NUMCONV = A.OLD_ID AND
      ORG.OLD_ID = O.CODAGE AND
      UPPER(ORG.NAME) = UPPER(O.NOMAGE) AND
      C.CODAGE = ORG.OLD_ID AND
      UPPER(TA.NOMDATO) = 'CVECOOP' AND
      TA.VALDATO = C.CVECOOP AND
      CATVAL.CATEGORY_VALUE = TA.INTERP AND
      CATVAL.AMP_CATEGORY_CLASS_ID = 10;

SELECT CONCAT(ROW_COUNT(), ' FUNDING MAPPED ') AS MSG;



INSERT INTO AMP_CURRENCY(CURRENCY_CODE, COUNTRY_NAME, CURRENCY_NAME,
 ACTIVE_FLAG)
SELECT SIGLA_MDA,
       MONEDA,
       PAIS,
       1
FROM SISFIN_DB.CLASIF_MONEDA
WHERE SIGLA_MDA NOT IN (SELECT CURRENCY_CODE FROM AMP_CURRENCY);

SELECT CONCAT(ROW_COUNT(), ' CURRENCY ADDED  1 of 3') AS MSG;



/*some currencies could be deleted  so we need check if there is old currencies used on the  transactions*/
INSERT INTO AMP_CURRENCY(CURRENCY_CODE, COUNTRY_NAME, CURRENCY_NAME,
 ACTIVE_FLAG)
SELECT CVEMONORIG,
       NULL,
       CVEMONORIG,
       1
FROM SISFIN_DB.DESEM
WHERE CVEMONORIG NOT IN (SELECT SIGLA_MDA FROM SISFIN_DB.CLASIF_MONEDA) AND
      CVEMONORIG NOT IN (SELECT CURRENCY_CODE FROM AMP_CURRENCY)
GROUP BY CVEMONORIG;

SELECT CONCAT(ROW_COUNT(), ' OLD CURRENCY ADDED  2 of 3') AS MSG;



INSERT INTO AMP_CURRENCY(CURRENCY_CODE, COUNTRY_NAME, CURRENCY_NAME,
 ACTIVE_FLAG)
SELECT CVEMONORIG,
       NULL,
       CVEMONORIG,
       1
FROM SISFIN_DB.ENM
WHERE CVEMONORIG NOT IN (SELECT SIGLA_MDA FROM SISFIN_DB.CLASIF_MONEDA) AND
      CVEMONORIG NOT IN (SELECT CURRENCY_CODE FROM AMP_CURRENCY)
GROUP BY CVEMONORIG;

SELECT CONCAT(ROW_COUNT(), ' OLD CURRENCY ADDED 3 of 3') AS MSG;


SELECT '! ........................  IMPORTING FUNDING  ..... ...................!' AS MSG;

SELECT 'DELTETING ALL FUNDING '  AS MSG;

TRUNCATE TABLE AMP_FUNDING_DETAIL;

INSERT INTO AMP_FUNDING_DETAIL(ADJUSTMENT_TYPE, TRANSACTION_TYPE,
 TRANSACTION_DATE, TRANSACTION_AMOUNT, ORG_ROLE_CODE, PERSPECTIVE_ID,
 AMP_FUNDING_ID, AMP_CURRENCY_ID, FIXED_EXCHANGE_RATE)
SELECT @FUNDING_ADJUSMENT_PLANNED,
       @COMMITMENT,
       ENM.FECHVIGENM,
       ENM.MONTORIG,
       @ORG_ROLE_CODE,
       @FUNDING_PERSPECTIVE,
       F.AMP_FUNDING_ID,
       CU.AMP_CURRENCY_ID,
       ENM.TIPCAM
FROM SISFIN_DB.`ENM` AS ENM,
     AMP_ACTIVITY AS A,
     AMP_FUNDING AS F,
     AMP_CURRENCY AS CU

WHERE
      A.OLD_ID = ENM.NUMCONV AND
      A.AMP_ACTIVITY_ID = F.AMP_ACTIVITY_ID AND
      ENM.CVEMONORIG = CU.CURRENCY_CODE AND
      ENM.NUMENM = 0;
SELECT CONCAT(ROW_COUNT(), ' INITIAL FUNDING ADDED ') AS MSG;

/* mapping additional commintments from ENM table.*/
INSERT INTO AMP_FUNDING_DETAIL(ADJUSTMENT_TYPE, TRANSACTION_TYPE,
 TRANSACTION_DATE, TRANSACTION_AMOUNT, ORG_ROLE_CODE, PERSPECTIVE_ID,
 AMP_FUNDING_ID, AMP_CURRENCY_ID, FIXED_EXCHANGE_RATE)
SELECT @FUNDING_ADJUSMENT_ACTUAL,
       @COMMITMENT,
       ENM.FECHVIGENM,
       ENM.MONTORIG,
       @ORG_ROLE_CODE,
       @FUNDING_PERSPECTIVE,
       F.AMP_FUNDING_ID,
       CU.AMP_CURRENCY_ID,
       ENM.TIPCAM
FROM SISFIN_DB.`ENM` AS ENM,
     AMP_ACTIVITY AS A,
     AMP_FUNDING AS F,
     AMP_CURRENCY AS CU
WHERE A.OLD_ID = ENM.NUMCONV AND
      A.AMP_ACTIVITY_ID = F.AMP_ACTIVITY_ID AND
      ENM.CVEMONORIG = CU.CURRENCY_CODE AND
      ENM.NUMENM > 0 AND
      ENM.MONTORIG != 0;
SELECT CONCAT(ROW_COUNT(), ' ADITIONAL FUNDING ADDED ') AS MSG;


INSERT INTO AMP_FUNDING_DETAIL(ADJUSTMENT_TYPE, TRANSACTION_TYPE,
 TRANSACTION_DATE, TRANSACTION_AMOUNT, ORG_ROLE_CODE, PERSPECTIVE_ID,
 AMP_FUNDING_ID, AMP_CURRENCY_ID, FIXED_EXCHANGE_RATE)
SELECT @FUNDING_ADJUSMENT_ACTUAL,
       @DISBURSMENT,
       DSM.FECHDESEM,
       DSM.MONTORIG,
       @ORG_ROLE_CODE,
       @FUNDING_PERSPECTIVE,
       F.AMP_FUNDING_ID,
       CU.AMP_CURRENCY_ID,
       DSM.TIPCAM
FROM SISFIN_DB.`DESEM` AS DSM,
     AMP_ACTIVITY AS A,
     AMP_FUNDING AS F,
     AMP_CURRENCY AS CU
WHERE A.OLD_ID = DSM.NUMCONV AND
      A.AMP_ACTIVITY_ID = F.AMP_ACTIVITY_ID AND
      DSM.CVEMONORIG = CU.CURRENCY_CODE AND
      UPPER(DSM.TIPDESEM) = 'E';

SELECT CONCAT(ROW_COUNT(), ' ACTUAL DISBURSMENTS ADDED ') AS MSG;


INSERT INTO AMP_FUNDING_DETAIL(ADJUSTMENT_TYPE, TRANSACTION_TYPE,
 TRANSACTION_DATE, TRANSACTION_AMOUNT, ORG_ROLE_CODE, PERSPECTIVE_ID,
 AMP_FUNDING_ID, AMP_CURRENCY_ID, FIXED_EXCHANGE_RATE)
SELECT @FUNDING_ADJUSMENT_PLANNED,
       @DISBURSMENT,
       DSM.FECHDESEM,
       DSM.MONTORIG,
       @ORG_ROLE_CODE,
       @FUNDING_PERSPECTIVE,
       F.AMP_FUNDING_ID,
       CU.AMP_CURRENCY_ID,
       DSM.TIPCAM
FROM SISFIN_DB.`DESEM` AS DSM,
     AMP_ACTIVITY AS A,
     AMP_FUNDING AS F,
     AMP_CURRENCY AS CU
WHERE A.OLD_ID = DSM.NUMCONV AND
      A.AMP_ACTIVITY_ID = F.AMP_ACTIVITY_ID AND
      DSM.CVEMONORIG = CU.CURRENCY_CODE AND
      UPPER(DSM.TIPDESEM) = 'P';

SELECT CONCAT(ROW_COUNT(), 'PLANNED DISBURSMENTS ADDED ') AS MSG;


SELECT 'CORRECTING INVALID DATES IN FUNDINGS' AS MSG;

SET @TRANSACTION_DATE=CAST('2011-01-01 01:01:01' AS DATE);
UPDATE AMP_FUNDING_DETAIL SET TRANSACTION_DATE=@TRANSACTION_DATE
WHERE TRANSACTION_DATE IS NULL OR TRANSACTION_DATE LIKE '0000-00-00%';

SELECT '! ........................  END IMPORTING FUNDING ..... ...................!' AS MSG;

/* CORRECTING ACTIVITY DATES */
SELECT 'CORRECTING 0000-00-00 DATES IN ACTIVITIES' AS MSG;

UPDATE AMP_ACTIVITY
SET ACTUAL_START_DATE=NULL
WHERE ACTUAL_START_DATE='0000-00-00 00:00:00';

UPDATE AMP_ACTIVITY
SET PROPOSED_START_DATE=NULL
WHERE PROPOSED_START_DATE='0000-00-00 00:00:00';

UPDATE AMP_ACTIVITY
SET PROPOSED_APPROVAL_DATE=NULL
WHERE PROPOSED_APPROVAL_DATE='0000-00-00 00:00:00';

UPDATE AMP_ACTIVITY
SET ACTUAL_APPROVAL_DATE=NULL
WHERE ACTUAL_APPROVAL_DATE='0000-00-00 00:00:00';

UPDATE AMP_ACTIVITY
SET ACTUAL_COMPLETION_DATE=NULL
WHERE ACTUAL_COMPLETION_DATE='0000-00-00 00:00:00';



/* REMOVING OLD VALUES, WE ARE GOING TO REPLACE FINANCING INSTRUMENT MEANING, IT WILL BE CALLED TYPE OF CREDIT FOR BOLIVIA */
SELECT 'REMOVE PREVIOUS CATEGORY VALUES FOR FINANCING INSTRUMENT' MSG;

DELETE CATVAL
FROM AMP_CATEGORY_VALUE AS CATVAL,
     AMP_CATEGORY_CLASS AS CATCLASS
WHERE CATVAL.AMP_CATEGORY_CLASS_ID = CATCLASS.ID AND
      upper(CATCLASS.KEYNAME) = 'FINANCING_INSTRUMENT' and
      upper(CATVAL.category_value) not in (SELECT upper(CLA.INTERP) FROM
      SISFIN_DB.`CLAVES` AS CLA WHERE UPPER(CLA.NOMDATO = 'CVECRED'));


/*  IMPORTING NEW VALUES: THERE ARE JUST 3 RECORDS */
SELECT 'IMPORTING NEW CATEGORY VALUES FOR TYPE OF CREDIT' as MSG;

SET @TEMP_CAT_VAL=-1;
INSERT INTO AMP_CATEGORY_VALUE(CATEGORY_VALUE, AMP_CATEGORY_CLASS_ID,
 INDEX_COLUMN)
SELECT CLA.INTERP,
       CATCLASS.ID,
       @TEMP_CAT_VAL:= @TEMP_CAT_VAL + 1
FROM AMP_CATEGORY_CLASS AS CATCLASS,
     SISFIN_DB.`CLAVES` AS CLA
WHERE UPPER(CLA.NOMDATO = 'CVECRED') AND
      UPPER(CATCLASS.KEYNAME) = 'FINANCING_INSTRUMENT' and
      upper(CLA.INTERP) not in (select CATVAL.category_value FROM
      AMP_CATEGORY_VALUE AS CATVAL, AMP_CATEGORY_CLASS AS CATCLASS WHERE
      CATVAL.AMP_CATEGORY_CLASS_ID = CATCLASS.ID AND upper(CATCLASS.KEYNAME) =
      'FINANCING_INSTRUMENT');

/* MAPPING CREDIT TYPES TO ACTIVITIES */
SELECT 'MAPPING FOUNDING CREDIT TYPES TO ACTIVITY FUNDINGS' AS MSG;

UPDATE AMP_ACTIVITY AS ACT,
       SISFIN_DB.`CONV` AS CON,
       AMP_CATEGORY_VALUE AS CATVAL,
       AMP_CATEGORY_CLASS AS CATCLASS,
       SISFIN_DB.`CLAVES` AS CLA,
       AMP_FUNDING AS FND
SET ACT.CREDIT_TYPE_ID = CATVAL.ID,
    FND.FINANCING_INSTR_CATEGORY_VALUE_ID = CATVAL.ID
WHERE ACT.OLD_ID = CON.NUMCONV AND
      FND.AMP_ACTIVITY_ID = ACT.AMP_ACTIVITY_ID AND
      UPPER(CATCLASS.KEYNAME) = 'FINANCING_INSTRUMENT' AND
      CATVAL.AMP_CATEGORY_CLASS_ID = CATCLASS.ID AND
      UPPER(CLA.NOMDATO) = 'CVECRED' AND
      CLA.INTERP = CATVAL.CATEGORY_VALUE AND
      CON.CVECRED = CLA.VALDATO;


/* ==REGIONS== */
SELECT 'IMPORTIN REGIONS' as MSG;

INSERT INTO AMP_REGION(NAME, COUNTRY_ID, REGION_CODE)
SELECT INTERP,
       'bo',
       VALDATO
FROM SISFIN_DB.`CLAVES` AS C
WHERE UPPER(C.NOMDATO) = 'CVEDEP' and
      trim(upper(INTERP)) not in (select trim(upper(NAME)) from AMP_REGION);
SELECT CONCAT(ROW_COUNT(), ' REGIONS  ADDED ') AS MSG;


SELECT 'INSERTING LOCATIONS' as MSG;

INSERT INTO AMP_LOCATION(NAME, COUNTRY, REGION, COUNTRY_ID, REGION_ID)
SELECT R.NAME,
       'Bolivia',
       R.NAME,
       'bo',
       R.AMP_REGION_ID
FROM AMP_REGION R,
     SISFIN_DB.`CLAVES` AS C
WHERE UPPER(C.NOMDATO) = 'CVEDEP' AND
      R.REGION_CODE = C.VALDATO and
      (select count(*) from AMP_LOCATION where trim(upper(NAME)) = trim(upper(
      R.NAME)) and upper(COUNTRY) = 'BOLIVIA' and upper(COUNTRY_ID) = 'BO' and
      REGION_ID = R.AMP_REGION_ID) = 0;

SELECT CONCAT(ROW_COUNT(), ' LOCATIONS  ADDED ') AS MSG;




SELECT 'CLEANING ALL LOCATIONS OF ACTIVITIES' AS MSG;
TRUNCATE TABLE AMP_ACTIVITY_LOCATION;

INSERT INTO AMP_ACTIVITY_LOCATION(AMP_ACTIVITY_ID, AMP_LOCATION_ID,
 LOCATION_PERCENTAGE)
SELECT ACT.AMP_ACTIVITY_ID,
       LOC.AMP_LOCATION_ID,
       CONDEP.PORCDEP
FROM AMP_ACTIVITY AS ACT,
     AMP_LOCATION AS LOC,
     AMP_REGION AS REG,
     SISFIN_DB.`CLAVES` AS C,
     SISFIN_DB.`CONV_DEP` AS CONDEP
WHERE ACT.AMP_ID = CONDEP.NUMCONV AND
      LOC.REGION_ID = REG.AMP_REGION_ID AND
      REG.REGION_CODE = C.VALDATO AND
      UPPER(C.NOMDATO) = 'CVEDEP' AND
      CONDEP.CVEDEP = C.VALDATO;

SELECT CONCAT(ROW_COUNT(), ' AMP_ACTIVITY_LOCATION  MAPPED ') AS MSG;



/*  mapping components (sectors) */
SELECT ' CLEANING ALL AMP_ACTIVITY_COMPONENTE' AS MSG;

TRUNCATE table AMP_ACTIVITY_COMPONENTE;

INSERT INTO AMP_ACTIVITY_COMPONENTE(AMP_ACTIVITY_ID, AMP_SECTOR_ID, PERCENTAGE)
SELECT ACT.AMP_ACTIVITY_ID,
       SEC.AMP_SECTOR_ID,
       BCOMP.PORCCOMP
FROM AMP_SECTOR AS SEC,
     AMP_SECTOR_SCHEME AS SCH,
     SISFIN_DB.COMP AS BCOMP,
     SISFIN_DB.`CONV` AS CON,
     AMP_ACTIVITY AS ACT
WHERE SEC.AMP_SEC_SCHEME_ID = SCH.AMP_SEC_SCHEME_ID AND
      SCH.SEC_SCHEME_CODE = 'BOL_COMPO_IMP' AND
      ACT.AMP_ID = CON.NUMCONV AND
      BCOMP.NUMCONV = CON.NUMCONV AND
      BCOMP.CVETIPCOMP = SEC.SECTOR_CODE;

SELECT CONCAT(ROW_COUNT(), ' COMPONENTS  MAPPED ') AS MSG;

/* mapping activities and themes(programs) */

SELECT ' CLEANING ALL  AMP_ACTIVITY_THEME (PROGRAMS)' as MSG;

TRUNCATE TABLE AMP_ACTIVITY_THEME;

INSERT INTO AMP_ACTIVITY_THEME(AMP_ACTIVITY_ID, AMP_THEME_ID)
SELECT ACT.AMP_ACTIVITY_ID,
       PROG.AMP_THEME_ID
FROM AMP_ACTIVITY AS ACT,
     AMP_THEME AS PROG,
     SISFIN_DB.`CONV` AS ACTO
WHERE ACT.OLD_ID = ACTO.NUMCONV AND
      PROG.THEME_CODE = CONCAT('EBRP', SUBSTRING(ACTO.COD_EBRP, 2));

SELECT CONCAT(ROW_COUNT(), ' AMP_ACTIVITY_THEME  MAPPED ') AS MSG;

SELECT ' CLEANING ALL  AMP_ACTIVITY_PROGRAM' AS MSG;
TRUNCATE TABLE AMP_ACTIVITY_PROGRAM;

INSERT INTO AMP_ACTIVITY_PROGRAM(AMP_ACTIVITY_ID, AMP_PROGRAM_ID,
 PROGRAM_PERCENTAGE, PROGRAM_SETTING)
SELECT ACT.AMP_ACTIVITY_ID,
       PROG.AMP_THEME_ID,
       100,
       PROGSET.AMP_PROGRAM_SETTINGS_ID
FROM AMP_ACTIVITY AS ACT,
     AMP_THEME AS PROG,
     AMP_PROGRAM_SETTINGS AS PROGSET,
     SISFIN_DB.`CONV` AS CON
WHERE ACT.AMP_ID = CON.NUMCONV AND
      PROG.THEME_CODE = CONCAT('EBRP', SUBSTRING(CON.COD_EBRP, 2)) AND
      UPPER(TRIM(PROGSET.NAME)) LIKE 'NATIONAL PLAN OBJECTIVE';

SELECT CONCAT(ROW_COUNT(), ' AMP_ACTIVITY_PROGRAM  MAPPED ') AS MSG;


/*Adding donor to activities*/


INSERT INTO AMP_ORG_ROLE(ACTIVITY, ORGANISATION, ROLE, PERCENTAGE)
SELECT A.AMP_ACTIVITY_ID,
       O.AMP_ORG_ID,
       1,
       NULL
FROM AMP_FUNDING A
     INNER JOIN AMP_ORGANISATION O ON A.AMP_DONOR_ORG_ID = O.AMP_ORG_ID
WHERE A.AMP_ACTIVITY_ID NOT IN (SELECT X.ACTIVITY FROM AMP_ORG_ROLE X WHERE
 X.ACTIVITY = A.AMP_ACTIVITY_ID AND X.ROLE = 1 AND X.ORGANISATION =
 O.AMP_ORG_ID);



SELECT '............ UPDATING FROM SIS SIN ...............' AS MSG;
-- Sebas: Why we are using these ID?
SET @usd_currency_id=48;

SET @mofed_perspective_id=2;
SET @base_url_sisin='http://www.google.com?sisinCode=';


SELECT 'INSERTING PROYECTOS REFERENCES INTO COMPONENT TABLE' AS MSG;

INSERT INTO AMP_COMPONENTS(CODIGOSISIN)
SELECT DISTINCT CODIGOSISIN
FROM SISIN_DB.SEGUIMIENTO_FINANCIERO S
WHERE CODCONVEXT != '00000' AND
      CODCONVEXT != '99999' and
      CODIGOSISIN not in (select CODIGOSISIN from AMP_COMPONENTS);
SELECT CONCAT(ROW_COUNT(), ' SISIN CODES ADDED') AS MSG;


SELECT 'UPDATING COMPONENTS WITH PROYECTO DATA' AS MSG;

UPDATE AMP_COMPONENTS A,
       SISIN_DB.PROYECTO P
SET A.TITLE = P.NOMBREPROYECTO,
    A.DESCRIPTION = P.DESCRIPCIONPROYECTO,
    A.CODE = P.CODIGOSISIN,
    A.URL = CONCAT(@BASE_URL_SISIN, P.CODIGOSISIN)
WHERE A.CODIGOSISIN = P.CODIGOSISIN;

SELECT CONCAT(ROW_COUNT(), ' SISIN PROYECTOS UPDATE') AS MSG;


SELECT 'UPDATING REFERENCES FROM PROYECTOS TO ACTIVITIES' AS MSG;
INSERT INTO amp_activity_components(amp_activity_id, amp_component_id)
SELECT A.AMP_ACTIVITY_ID,
       C.AMP_COMPONENT_ID
FROM AMP_COMPONENTS C
     JOIN SISIN_DB.SEGUIMIENTO_FINANCIERO SF ON C.CODIGOSISIN = SF.CODIGOSISIN
     JOIN AMP_ACTIVITY A ON A.AMP_ID = SF.CODCONVEXT
WHERE (SELECT COUNT(*) FROM AMP_ACTIVITY_COMPONENTS WHERE AMP_COMPONENT_ID =
 C.AMP_COMPONENT_ID AND AMP_ACTIVITY_ID = A.AMP_ACTIVITY_ID) = 0
GROUP BY A.AMP_ACTIVITY_ID,
         C.AMP_COMPONENT_ID;

SELECT CONCAT(ROW_COUNT(), '  REFERENCES FROM PROYECTOS TO ACTIVITIES ADDED') AS MSG;

SELECT 'CLEANING ALL COMPONENTS FUNDING ...' AS MSG;

TRUNCATE TABLE AMP_COMPONENT_FUNDING;

SELECT 'INSERTING MONTO PROGRAMADO FUNDING DATA' AS MSG;

INSERT INTO AMP_COMPONENT_FUNDING (TRANSACTION_TYPE,ADJUSTMENT_TYPE,CURRENCY_ID,PERSPECTIVE_ID,AMP_COMPONENT_ID,ACTIVITY_ID,TRANSACTION_AMOUNT,TRANSACTION_DATE,REPORTING_DATE,EXCHANGE_RATE)
SELECT
@COMMITMENT AS TRANSACTION_TYPE,
@FUNDING_ADJUSMENT_PLANNED AS ADJUSTMENT_TYPE,
@USD_CURRENCY_ID AS CURRENCY_ID,
@MOFED_PERSPECTIVE_ID AS PERSPECTIVE_ID,
C.AMP_COMPONENT_ID,
A.AMP_ACTIVITY_ID,
SF.MONTOPROGRAMADO AS TRANSACTION_AMOUNT,
STR_TO_DATE(CONCAT(SF.MES, '/01/', SF.ANO),'%m/%d/%Y') AS TRANSACTION_DATE,
FECHAREGISTRO AS REPORTING_DATE,
TC.TIPODECAMBIO AS EXCHANGE_RATE
FROM AMP_COMPONENTS C
JOIN SISIN_DB.SEGUIMIENTO_FINANCIERO SF ON C.CODIGOSISIN = SF.CODIGOSISIN
JOIN AMP_ACTIVITY A ON A.AMP_ID = SF.CODCONVEXT AND SF.MONTOPROGRAMADO != 0
JOIN SISIN_DB.TABLA_TIPOCAMBIOGESTION TC ON TC.ANO = SF.ANO;

SELECT CONCAT(ROW_COUNT(), '  MONTO PROGRAMADO COMMITMENT sADDED') AS MSG;


SELECT 'INSERTING MONTO REPROGRAMADO FUNDING DATA' as MSG;
INSERT INTO AMP_COMPONENT_FUNDING (TRANSACTION_TYPE,ADJUSTMENT_TYPE,CURRENCY_ID,PERSPECTIVE_ID,AMP_COMPONENT_ID,ACTIVITY_ID,TRANSACTION_AMOUNT,TRANSACTION_DATE,REPORTING_DATE,EXCHANGE_RATE)
SELECT
@COMMITMENT AS TRANSACTION_TYPE,
@FUNDING_ADJUSMENT_ACTUAL AS ADJUSTMENT_TYPE,
@USD_CURRENCY_ID AS CURRENCY_ID,
@MOFED_PERSPECTIVE_ID AS PERSPECTIVE_ID,
C.AMP_COMPONENT_ID,
A.AMP_ACTIVITY_ID,
SF.MONTOREPROGRAMADO AS TRANSACTION_AMOUNT,
STR_TO_DATE(CONCAT(SF.MES, '/01/', SF.ANO),'%m/%d/%Y') AS TRANSACTION_DATE,
FECHAREGISTRO AS REPORTING_DATE,
TC.TIPODECAMBIO AS EXCHANGE_RATE
FROM AMP_COMPONENTS C
JOIN SISIN_DB.SEGUIMIENTO_FINANCIERO SF ON C.CODIGOSISIN = SF.CODIGOSISIN
JOIN AMP_ACTIVITY A ON A.AMP_ID = SF.CODCONVEXT AND SF.MONTOREPROGRAMADO != 0
JOIN SISIN_DB.TABLA_TIPOCAMBIOGESTION TC ON TC.ANO = SF.ANO;

SELECT CONCAT(ROW_COUNT(), '  MONTO PROGRAMADO ADDED COMMITMENT') AS MSG;


SELECT 'INSERTING MONTO EJECUTADO FUNDING DATA' as MSG;
INSERT INTO AMP_COMPONENT_FUNDING (TRANSACTION_TYPE,ADJUSTMENT_TYPE,CURRENCY_ID,PERSPECTIVE_ID,AMP_COMPONENT_ID,ACTIVITY_ID,TRANSACTION_AMOUNT,TRANSACTION_DATE,REPORTING_DATE,EXCHANGE_RATE)
SELECT
@EXPENDITURE,
@FUNDING_ADJUSMENT_ACTUAL,
@USD_CURRENCY_ID,
@MOFED_PERSPECTIVE_ID,
C.AMP_COMPONENT_ID,
A.AMP_ACTIVITY_ID,
SF.MONTOEJECUTADO AS TRANSACTION_AMOUNT,
STR_TO_DATE(CONCAT(SF.MES, '/01/', SF.ANO),'%m/%d/%Y') AS TRANSACTION_DATE,
FECHAREGISTRO AS REPORTING_DATE,
TC.TIPODECAMBIO AS EXCHANGE_RATE
FROM AMP_COMPONENTS C
JOIN SISIN_DB.SEGUIMIENTO_FINANCIERO SF ON C.CODIGOSISIN = SF.CODIGOSISIN
JOIN AMP_ACTIVITY A ON A.AMP_ID = SF.CODCONVEXT AND SF.MONTOEJECUTADO != 0
JOIN SISIN_DB.TABLA_TIPOCAMBIOGESTION TC ON TC.ANO = SF.ANO;
SELECT CONCAT(ROW_COUNT(), '  MONTO PROGRAMADO ADDED EXPENDITURE') AS MSG;



UPDATE AMP_COMPONENTS
SET CODE = CONCAT(LEFT(CODE,3), '-', MID(CODE,4,5))
WHERE LENGTH(CODE)= 13;
COMMIT;



END;