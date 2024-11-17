select * from prestamo;
-- Insertar registros en la tabla Categoria
INSERT INTO categoria ("nombre", "descripcion") VALUES
('Ficción', 'Libros de ficción'),
('No ficción', 'Libros de no ficción'),
('Ciencia', 'Libros sobre temas científicos'),
('Historia', 'Libros históricos');

-- Insertar registros en la tabla Cliente
INSERT INTO cliente ("nombre", "correo", "telefono") VALUES
('Juan Pérez', 'juan.perez@example.com', 1234),
('Ana Gómez', 'ana.gomez@example.com', 9876),
('Luis Rodríguez', 'luis.rodriguez@example.com', 4561),
('María López', 'maria.lopez@example.com', 7891);

-- Insertar registros en la tabla Libro
INSERT INTO libro ("titulo", "anio_publicacion", "disponibilidad", "descripcion", "id_categoria", "id_autor") VALUES
('El universo en una cáscara de nuez', 2001, TRUE, 'Un libro de Stephen Hawking', 3, 1),
('Sapiens', 2011, TRUE, 'Breve historia de la humanidad', 2, 2),
('1984', 1949, FALSE, 'Distopía escrita por George Orwell', 1, 3),
('Breve historia del tiempo', 1988, TRUE, 'Otro libro de Stephen Hawking', 3, 4);

-- Insertar registros en la tabla Prestamo
INSERT INTO prestamo ("id_cliente", "fecha_inicio", "fecha_final") VALUES
(1, '2024-01-01', '2024-01-15'),
(2, '2024-02-01', '2024-02-10'),
(3, '2024-03-01', '2024-03-20'),
(4, '2024-04-01', '2024-04-15');

-- Insertar registros en la tabla Prestamo_libro
INSERT INTO prestamo_libro ("id_prestamo", "id_libro") VALUES
(5, 4),
(6, 5),
(7, 6),
(8, 7);
