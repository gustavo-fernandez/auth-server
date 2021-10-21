INSERT INTO user (username, password) VALUES ('hugo', '123456');
INSERT INTO user (username, password) VALUES ('gustavo', 'abcdef');

INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'hugo'), 'listar-cuentas');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'hugo'), 'listar-una-cuenta');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'hugo'), 'crear-cuenta');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'hugo'), 'transferencias');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'hugo'), 'productos');

INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'gustavo'), 'listar-una-cuenta');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'gustavo'), 'transferencias');
INSERT INTO roles (user_id, name) VALUES ((SELECT id FROM user WHERE username = 'gustavo'), 'productos');
