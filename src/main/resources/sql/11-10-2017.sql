/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Darkus Nightmare
 * Created: 11/10/2017
 */

ALTER TABLE public.rol
  DROP CONSTRAINT rol_code_uk;
UPDATE rol set create_uid = 1 , create_date = current_timestamp WHERE create_uid is null

ALTER TABLE public.rol
   ALTER COLUMN create_uid SET NOT NULL;
ALTER TABLE public.rol
   ALTER COLUMN create_date SET NOT NULL;
