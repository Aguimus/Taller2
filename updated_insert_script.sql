
-- Insertar registros en la tabla Categoria
INSERT INTO "Categoria" ("nombre", "descripcion") VALUES
('Ficción', 'Libros de ficción'),
('No ficción', 'Libros de no ficción'),
('Ciencia', 'Libros sobre temas científicos'),
('Historia', 'Libros históricos');

-- Insertar registros en la tabla Autor
INSERT INTO autor ("nombre", "pais_origen") VALUES
('Stephen Hawking', 'Reino Unido'),
('Yuval Noah Harari', 'Israel'),
('George Orwell', 'Reino Unido'),
('Gabriel García Márquez', 'Colombia');

-- Insertar registros en la tabla Cliente
INSERT INTO "Cliente" ("nombre", "correo", "telefono") VALUES
('Juan Pérez', 'juan.perez@example.com', 1234567890),
('Ana Gómez', 'ana.gomez@example.com', 9876543210),
('Luis Rodríguez', 'luis.rodriguez@example.com', 4561237890),
('María López', 'maria.lopez@example.com', 7891234560);

-- Insertar registros en la tabla Libro
INSERT INTO "Libro" ("titulo", "anioPublicacion", "disponibilidad", "descripcion", "idCategoria", "idAutor") VALUES
('El universo en una cáscara de nuez', 2001, TRUE, 'Un libro de Stephen Hawking', 3, 1),
('Sapiens', 2011, TRUE, 'Breve historia de la humanidad', 2, 2),
('1984', 1949, FALSE, 'Distopía escrita por George Orwell', 1, 3),
('Cien años de soledad', 1967, TRUE, 'Novela de Gabriel García Márquez', 1, 4);

-- Insertar registros en la tabla Prestamo
INSERT INTO "Prestamo" ("idCliente", "fechaInicio", "fechaFin") VALUES
(1, '2024-01-01', '2024-01-15'),
(2, '2024-02-01', '2024-02-10'),
(3, '2024-03-01', '2024-03-20'),
(4, '2024-04-01', '2024-04-15');

-- Insertar registros en la tabla Prestamo_libro
INSERT INTO "Prestamo_libro" ("idPrestamo", "idLibro", "id") VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4);
