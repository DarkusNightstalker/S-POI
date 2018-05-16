/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Darkus Nightmare
 * Created: 28/09/2017
 */

ALTER TABLE public.dependency
  ADD COLUMN operation_year integer NOT NULL DEFAULT 2017;
ALTER TABLE public.dependency
   ALTER COLUMN operation_year DROP DEFAULT;
ALTER TABLE public.dependency
  ADD COLUMN id_previous_year_dependency integer;
ALTER TABLE public.dependency
  ADD FOREIGN KEY (id_previous_year_dependency) REFERENCES public.dependency (id) ON UPDATE NO ACTION ON DELETE NO ACTION;