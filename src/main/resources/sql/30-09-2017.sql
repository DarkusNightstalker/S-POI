/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Darkus Nightmare
 * Created: 30/09/2017
 */

ALTER TABLE public.strategic_plan
  DROP COLUMN end_year;
ALTER TABLE public.strategic_plan RENAME start_year  TO year;
UPDATE strategic_plan SET year = 2017;

/**
 * Estrato del Plan estratégico institucional
 */
DROP TABLE IF EXISTS public.institutional_strategic_plan_stratum;
CREATE TABLE public.institutional_strategic_plan_stratum
(
   id serial NOT NULL, 
   system_name character varying(250) NOT NULL, 
   name character varying(250) NOT NULL, 
   code_prefix character varying(10), 
   hierarchy_level smallint NOT NULL DEFAULT 1, 
   case_sensitive boolean NOT NULL DEFAULT FALSE, 
   id_institutional_strategic_plan integer NOT NULL, 
   id_parent integer, 
   create_uid integer NOT NULL, 
   create_date timestamp with time zone NOT NULL DEFAULT current_timestamp, 
   edit_uid integer, 
   edit_date timestamp with time zone, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_institutional_strategic_plan) REFERENCES public.strategic_plan (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (create_uid) REFERENCES public."user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (edit_uid) REFERENCES public."user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   FOREIGN KEY (id_parent) REFERENCES public."institutional_strategic_plan_stratum" (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
);
ALTER TABLE public.institutional_strategic_plan_stratum
  ADD UNIQUE (id_institutional_strategic_plan, hierarchy_level);
ALTER TABLE public.institutional_strategic_plan_stratum
  ADD CHECK (hierarchy_level> 0);

INSERT INTO institutional_strategic_plan_stratum
(system_name,name,code_prefix,hierarchy_level,id_institutional_strategic_plan,id_parent,create_uid,case_sensitive) VALUES
('strategic-axis','Eje estratégico','E',1,1,null,1,true);
INSERT INTO institutional_strategic_plan_stratum
(system_name,name,code_prefix,hierarchy_level,id_institutional_strategic_plan,id_parent,create_uid,case_sensitive) VALUES
('strategic-goal','Objetivo estratégico','',2,1,1,1,true);
INSERT INTO institutional_strategic_plan_stratum
(system_name,name,code_prefix,hierarchy_level,id_institutional_strategic_plan,id_parent,create_uid,case_sensitive) VALUES
('specific-goal','Objetivo especifico','',3,1,2,1,true);
INSERT INTO institutional_strategic_plan_stratum
(system_name,name,code_prefix,hierarchy_level,id_institutional_strategic_plan,id_parent,create_uid,case_sensitive) VALUES
('strategic-activity','Actividad estratégica','',4,1,3,1,false);

/**
 * Estrato del Item del plan estratégico institucional
 */
DROP TABLE IF EXISTS public.institutional_strategic_plan_stratum_item;
CREATE TABLE public.institutional_strategic_plan_stratum_item
(
   id serial NOT NULL, 
   code character varying(250) NOT NULL, 
   name character varying(250) NOT NULL, 
   id_institutional_strategic_plan_stratum integer NOT NULL, 
   id_parent integer, 
   create_uid integer NOT NULL, 
   create_date timestamp with time zone NOT NULL DEFAULT current_timestamp, 
   edit_uid integer, 
   edit_date timestamp with time zone, 
   active boolean NOT NULL DEFAULT TRUE, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_institutional_strategic_plan_stratum) REFERENCES public.institutional_strategic_plan_stratum (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (create_uid) REFERENCES public."user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (edit_uid) REFERENCES public."user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   FOREIGN KEY (id_parent) REFERENCES public."institutional_strategic_plan_stratum_item" (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
);
ALTER TABLE public.institutional_strategic_plan_stratum_item
  ADD UNIQUE (code);

INSERT INTO institutional_strategic_plan_stratum_item 
(code,name,id_institutional_strategic_plan_stratum,id_parent,create_uid,active)
( SELECT code,name,1,null,1,active from strategic_axis );

INSERT INTO institutional_strategic_plan_stratum_item 
(code,name,id_institutional_strategic_plan_stratum,id_parent,create_uid,active)
( 
    SELECT 
        sg.code,
        sg.name,
        2,
        (
            SELECT id from institutional_strategic_plan_stratum_item WHERE code LIKE sa.code
        ) ,
        1,
        sg.active 
    from strategic_goal sg join strategic_axis sa 
    on sg.id_strategic_axis = sa.id
);
INSERT INTO institutional_strategic_plan_stratum_item 
(code,name,id_institutional_strategic_plan_stratum,id_parent,create_uid,active)
( 
    SELECT 
        sg.code,
        sg.name,
        3,
        (
            SELECT id from institutional_strategic_plan_stratum_item WHERE code LIKE sa.code
        ),
        1,
        sg.active 
    from specific_goal sg join strategic_goal sa 
    on sg.id_strategic_goal = sa.id
);
INSERT INTO institutional_strategic_plan_stratum_item 
(code,name,id_institutional_strategic_plan_stratum,id_parent,create_uid,active)
( 
    SELECT 
        sg.code,
        sg.name,
        4,
        (
            SELECT id from institutional_strategic_plan_stratum_item WHERE code LIKE sa.code
        ) ,
        1,
        sg.active 
    from strategic_activity sg join specific_goal sa 
    on sg.id_specific_goal = sa.id
);

ALTER TABLE public.poi_activity
  ADD COLUMN priority smallint NOT NULL DEFAULT 3;
ALTER TABLE public.poi_activity
  ADD CHECK (priority BETWEEN 1 AND 5);

ALTER TABLE public.strategic_plan RENAME year  TO start_year;
ALTER TABLE public.strategic_plan
  ADD COLUMN end_year integer NOT NULL DEFAULT 2017;
