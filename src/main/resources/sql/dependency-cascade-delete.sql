/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Darkus Nightmare
 * Created: 29/09/2017
 */

delete from dependency_has_abp WHERE id_dependency IN (SELECT id FROM dependency WHERE operation_year = 2018);
delete from budget_ceiling WHERE id_dependency IN (SELECT id FROM dependency WHERE operation_year = 2018);
delete from poi_schedule WHERE id_poi_activity IN (SELECT id FROM poi_activity WHERE id_poi IN (SELECT id FROM poi WHERE id_dependency IN ( SELECT id FROM dependency WHERE operation_year = 2018)));
delete from poi_activity WHERE id_poi IN (SELECT id FROM poi WHERE id_dependency IN ( SELECT id FROM dependency WHERE operation_year = 2018));
delete from poi WHERE id_dependency IN (SELECT id FROM dependency WHERE operation_year = 2018);
delete from dependency WHERE operation_year = 2018;