INSERT INTO regiones (nombre) VALUES ("Asturias");
INSERT INTO regiones (nombre) VALUES ("Cantabria");

INSERT INTO regiones (nombre) VALUES ("Sevilla");

INSERT INTO regiones (nombre) VALUES ("Betis");

INSERT INTO regiones (nombre) VALUES ("Oviedo");

INSERT INTO regiones (nombre) VALUES ("Gijon");


INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (1,"David","Martinez","david@gmail.com",63555555,"2022-03-01");
INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (2,"Paco","Martinez","paco@gmail.com",63555555,"2022-03-01");
INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (3,"Alfonso","Martinez","alfonso@gmail.com",63555555,"2022-03-01");
INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (4,"Lorena","Martinez","lorena@gmail.com",63555555,"2022-03-01");
INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (5,"Alba","Martinez","alba@gmail.com",63555555,"2022-03-01");
INSERT INTO clientes (region_id,nombre,apellido,email,telefono,create_at) VALUES (6,"Santiago","Martinez","santiago@gmail.com",63555555,"2022-03-01");

INSERT INTO productos (codigo,cantidad,precio,tipo,marca,descripcion,fecha_ingreso) VALUES (4566,333,3333.4,"mercancia","fff","loremlorem","2022-03-01");

INSERT INTO usuarios (username,password,enabled) VALUES ("David","$2a$10$/P4np4JXZik5oUTcxMLuKuRU2XJs02N0hMY/RNMrJLisU38tv.hii",1);
INSERT INTO usuarios (username,password,enabled) VALUES ("admin","$2a$10$KAeFOm4XoQQCMTSPtgiDU.azCUpJZthq/fohSey8slT9EiPoOVO.G",1);

INSERT INTO roles (nombre) VALUES ("ROLE_USER");
INSERT INTO roles (nombre) VALUES ("ROLE_ADMIN");

INSERT INTO usuarios_roles (usuario_id,role_id)VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id,role_id)VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id,role_id)VALUES (2,1);