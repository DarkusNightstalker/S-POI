/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Darkus Nightmare
 * Created: 29/09/2017
 */

CREATE TABLE public.periodicity
(
   id smallserial NOT NULL, 
   name character varying(120) NOT NULL, 
   maximum_anually smallint NOT NULL DEFAULT 0, 
   PRIMARY KEY (id)
) 
WITH (
  OIDS = FALSE
);

CREATE TABLE public.periodicity_item
(
   id serial NOT NULL, 
   ordinal smallint NOT NULL, 
   name character varying(80) NOT NULL, 
   abbreviation character varying(20) NOT NULL, 
   id_periodicity smallint NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_periodicity) REFERENCES public.periodicity (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
);

ALTER SEQUENCE periodicity_id_seq RESTART WITH 1;
ALTER SEQUENCE periodicity_item_id_seq RESTART WITH 1;

INSERT INTO periodicity(name,maximum_anually) VALUES
('Trimestral',4),
('Mensual',12);
INSERT INTO periodicity_item(ordinal,name,abbreviation,id_periodicity) VALUES
(1,'1<sup>er</sup> Trimestre','1T',1),
(2,'2<sup>do</sup> Trimestre','2T',1),
(3,'3<sup>er</sup> Trimestre','3T',1),
(4,'4<sup>to</sup> Trimestre','4T',1),
(1,'Enero','Ene',2),
(2,'Febrero','Feb',2),
(3,'Marzo','Mar',2),
(4,'Abril','Abr',2),
(5,'Mayo','May',2),
(6,'Junio','Jun',2),
(7,'Julio','Jul',2),
(8,'Agosto','Ago',2),
(9,'Septiembre','Sep',2),
(10,'Octubre','Oct',2),
(11,'Noviembre','Noviembre',2),
(12,'Diciembre','Dic',2);


/*
 * Configuración de poi
 */
ALTER TABLE public.poi_schedule
  ADD COLUMN id_periodicity_item integer;

ALTER TABLE public.poi_schedule
  ADD FOREIGN KEY (id_periodicity_item) REFERENCES public.periodicity_item (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

UPDATE poi_schedule SET
id_periodicity_item =
       CASE WHEN quarter='I' THEN 1
            WHEN quarter='II' THEN 2
            WHEN quarter='III' THEN 3
            WHEN quarter='IV' THEN 4
            ELSE null
       END;

ALTER TABLE public.poi_schedule
  DROP COLUMN quarter;

ALTER TABLE public.poi_schedule
   ALTER COLUMN id_periodicity_item SET NOT NULL;

ALTER TABLE public.poi
  ADD COLUMN id_periodicity smallint NOT NULL DEFAULT 1;

ALTER TABLE public.poi
  ADD FOREIGN KEY (id_periodicity) REFERENCES public.periodicity (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE public.poi
   ALTER COLUMN id_periodicity DROP DEFAULT;