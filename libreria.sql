CREATE TABLE Categoria(
    id SERIAL NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);
ALTER TABLE
    Categoria ADD PRIMARY KEY("id");
CREATE TABLE Prestamo_libro(
    idPrestamo INTEGER NOT NULL,
    idLibro INTEGER NOT NULL
);
ALTER TABLE
    Prestamo_libro ADD PRIMARY KEY("idPrestamo");
CREATE TABLE Libro(
    id SERIAL NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    anioPublicacion INTEGER NOT NULL,
    disponibilidad BOOLEAN NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    idCategoria INTEGER NOT NULL,
    idAutor INTEGER NOT NULL
);
ALTER TABLE
    Libro ADD PRIMARY KEY("id");
CREATE TABLE Prestamo(
    id SERIAL NOT NULL,
    idCliente INTEGER NOT NULL,
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL
);
ALTER TABLE
    Prestamo ADD PRIMARY KEY("id");
CREATE TABLE Cliente(
    id SERIAL NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    telefono INTEGER NOT NULL,
    estadoCuenta BOOLEAN NOT NULL
);
ALTER TABLE
    Cliente ADD PRIMARY KEY("id");
CREATE TABLE Autor(
    id SERIAL NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    paisOrigen VARCHAR(255) NOT NULL
);
ALTER TABLE
    Autor ADD PRIMARY KEY("id");
ALTER TABLE
    Prestamo_libro ADD CONSTRAINT prestamo_libro_idprestamo_foreign FOREIGN KEY(idPrestamo) REFERENCES Prestamo(id);
ALTER TABLE
    Prestamo_libro ADD CONSTRAINT prestamo_libro_idlibro_foreign FOREIGN KEY(idLibro) REFERENCES Libro(id);
ALTER TABLE
    Prestamo ADD CONSTRAINT prestamo_idclientevarchar_foreign FOREIGN KEY(idClientevarchar) REFERENCES Cliente(id);
ALTER TABLE
    Libro ADD CONSTRAINT libro_idcategoria_foreign FOREIGN KEY(idCategoria) REFERENCES Categoria(id);
ALTER TABLE
    Libro ADD CONSTRAINT libro_idautor_foreign FOREIGN KEY(idAutor) REFERENCES Autor(id);