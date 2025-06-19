CREATE TABLE IF NOT EXISTS tarea (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     titulo TEXT NOT NULL,
                                     descripcion TEXT,
                                     fecha_vencimiento DATE,
                                     completada BOOLEAN
);

INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Leer capítulo 1', 'Lectura del libro de Álgebra', '2025-06-20 00:00:00.000', 0);
INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Resolver ejercicios', 'Ejercicios del capítulo 1', '2025-06-21 00:00:00.000', 0);
INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Preparar presentación', 'Exponer en clase de software', '2025-06-25 00:00:00.000', 1);
INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Revisar apuntes', 'Apuntes de cálculo diferencial', NULL, 0);
INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Enviar resumen', 'Resumen para revisión de pares', '2025-06-22 00:00:00.000', 1);
INSERT INTO tarea (titulo, descripcion, fecha_vencimiento, completada) VALUES ('Enviar resumen', 'Resumen para revisión de pares', '2025-06-22 00:00:00.000', 1);
