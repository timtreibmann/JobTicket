

drop table JOBTICKET.jobbearbeiter;
drop table JOBTICKET.kosten; 
drop table JOBTICKET.angestellte;
drop table JOBTICKET.angestelltenbezeichnungen;
drop table JOBTICKET.produkteigenschaften;
drop table JOBTICKET.jobs;
drop table JOBTICKET.kunde; 
 
CREATE TABLE JOBTICKET.kunde (
       id INTEGER NOT NULL generated always as identity
     , name VARCHAR(50) NOT NULL
     , adresse VARCHAR(50)
     , telefon VARCHAR(50)
     , kundenkuerzel VARCHAR(10)
     , PRIMARY KEY (id)
);


CREATE TABLE JOBTICKET.angestelltenbezeichnungen (
       id INTEGER NOT NULL generated always as identity
     , bezeichnung VARCHAR(50)
     , PRIMARY KEY (id)
);

CREATE TABLE JOBTICKET.jobs (
       budget_in_std DECIMAL(10, 2)
     , budget_in_euro DECIMAL(10, 2)
     , vorlage DATE
     , id INTEGER NOT NULL generated always as identity
     , name VARCHAR(20) NOT NULL
     , alte_jobnummer INTEGER
     , empfaenger VARCHAR(10)
     , print VARCHAR(10)
     , jobbeschreibung VARCHAR(200)
     , kunde_id INTEGER NOT NULL
     , PRIMARY KEY (id)
     , CONSTRAINT FK_jobs_1 FOREIGN KEY (kunde_id)
                  REFERENCES JOBTICKET.kunde (id)
);

CREATE TABLE JOBTICKET.angestellte (
       id INTEGER NOT NULL generated always as identity
     , vorname VARCHAR(50) NOT NULL
     , nachname VARCHAR(50) NOT NULL
     , angestelltenbezeichnung_id INTEGER
     , stundenlohn DECIMAL(10, 2)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_angestellte_1 FOREIGN KEY (angestelltenbezeichnung_id)
                  REFERENCES JOBTICKET.angestelltenbezeichnungen (id)
);

CREATE TABLE JOBTICKET.produkteigenschaften (
       id INTEGER NOT NULL generated always as identity
     , eingangsdatum DATE NOT NULL
     , ausgangsdatum DATE
     , vorlagedatum DATE
     , erledigt BOOLEAN
     , fomat VARCHAR(50)
     , beschnitt VARCHAR(50)
     , seitenzahl INTEGER
     , falzung VARCHAR(50)
     , farbe_4c VARCHAR(50)
     , farbe_sw VARCHAR(50)
     , sonderfarbe VARCHAR(50)
     , bindung VARCHAR(50)
     , proof VARCHAR(50)
     , dummy VARCHAR(50)
     , produktbeschreibung VARCHAR(200)
     , jobs_id INTEGER NOT NULL
     , PRIMARY KEY (id)
     , CONSTRAINT FK_produkteigenschaften_1 FOREIGN KEY (jobs_id)
                  REFERENCES JOBTICKET.jobs (id)
);

CREATE TABLE JOBTICKET.jobbearbeiter (
       jobs_id INTEGER NOT NULL generated always as identity
     , angestellte_id INTEGER NOT NULL
     , id INTEGER NOT NULL
     , PRIMARY KEY (id)
     , CONSTRAINT FK_jobbearbeiter_1 FOREIGN KEY (jobs_id)
                  REFERENCES JOBTICKET.jobs (id)
     , CONSTRAINT FK_jobbearbeiter_2 FOREIGN KEY (angestellte_id)
                  REFERENCES JOBTICKET.angestellte (id)
);

CREATE TABLE JOBTICKET.kosten (
       id INTEGER NOT NULL generated always as identity
     , angestellte_id INTEGER NOT NULL
     , arbeitsaufwand_in_euro DECIMAL(10, 2)
     , arbeitsaufwand_in_std DECIMAL(10, 2)
     , kommentar CHAR(200)
     , jobs_id INTEGER NOT NULL
     , PRIMARY KEY (id)
     , CONSTRAINT FK_kosten_2 FOREIGN KEY (jobs_id)
                  REFERENCES JOBTICKET.jobs (id)
     , CONSTRAINT FK_kosten_1 FOREIGN KEY (angestellte_id)
                  REFERENCES JOBTICKET.angestellte (id)
);

select * from JOBTICKET.jobs
select * from JOBTICKET.KUNDE
insert into JOBTICKET.jobs(name) values ('test')
select * from JOBTICKET.jobs
select * from JOBTICKET.ANGESTELLTE
select * from JOBTICKET.PRODUKTEIGENSCHAFTEN
select * from JOBTICKET.kosten
insert into tablename(col1,col2) values(val1,val2)