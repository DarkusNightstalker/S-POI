--"Programas presupuestales"
UPDATE permission SET name='Crear programas presupuestales', code='C_BP' WHERE code='M_BP';
INSERT INTO permission (code, name, limited, active, id_permission_category) VALUES
('U_BP', 'Editar programas presupuestales', false, true, 1),
('D_BP', 'Borrar programas presupuestales', false, true, 1);

--"Productos/Proyectos presupuestales"
UPDATE permission SET name='Crear productos/proyectos presupuestales', code='C_PBP' WHERE code='M_PBP';
INSERT INTO permission (code, name, limited, active, id_permission_category) VALUES
('U_PBP', 'Editar productos/proyectos presupuestales', false, true, 2),
('D_PBP', 'Borrar productos/proyectos presupuestales', false, true, 2);

--"Actividades presupuestales"
UPDATE permission SET name='Crear actividades del programa presupuestal', code='C_ABP' WHERE code='M_ABP';
INSERT INTO permission (code, name, limited, active, id_permission_category) VALUES
('U_ABP', 'Editar actividades del programa presupuestal', false, true, 3),
('D_ABP', 'Borrar actividades del programa presupuestal', false, true, 3);

--"Unidades presupuestales"
UPDATE permission SET name='Crear unidades del programa presupuestal', code='C_UOM' WHERE code='M_UOM';
INSERT INTO permission (code, name, limited, active, id_permission_category) VALUES
('U_UOM', 'Editar unidades del programa presupuestal', false, true, 4),
('D_UOM', 'Borrar unidades del programa presupuestal', false, true, 4);

--"Fuentes de financiamiento"
UPDATE permission SET name='Crear fuentes de financiamiento', code='C_FS' WHERE code='M_FS';
INSERT INTO permission (code, name, limited, active, id_permission_category) VALUES
('U_FS', 'Editar fuentes de financiamiento', false, true, 5),
('D_FS', 'Borrar fuentes de financiamiento', false, true, 5);