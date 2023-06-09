CREATE TABLE IF NOT EXISTS cliente (
  id_cliente INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(255),
  plano_exclusive TINYINT,
  saldo DECIMAL(10,2),
  numero_conta VARCHAR(6),
  data_nascimento DATE
);

INSERT INTO cliente (nome, plano_exclusive, saldo, numero_conta, data_nascimento) VALUES
  ('João', 1, 10000.00, '123456', '2022-01-01'),
  ('Renato', 1, 5100.00, '789012', '2000-06-18'),
  ('Maria', 0, 2310.00, '783412', '2000-01-15'),
  ('Pedro', 1, 400.00, '012789', '2002-09-23'),
  ('Joana', 0, 600.00, '017892', '2003-05-15'),
  ('Beatriz', 1, 1650.00, '986312', '1998-06-12'),
  ('Carlos', 0, 410.00, '002312', '1991-07-07'),
  ('Ezequiel', 1, 10000.00, '124421', '2002-10-21'),
  ('Izabel', 1, 2350.00, '655463', '1996-12-05');

CREATE TABLE IF NOT EXISTS transacao (
  id_transacao INT PRIMARY KEY AUTO_INCREMENT,
  id_cliente INT,
  tipo_movimentacao VARCHAR(255),
  valor_transacao DECIMAL(10,2),
  numero_conta VARCHAR(6),
  horario_transacao TIME,
  dia_transacao DATE,
  conta_destino VARCHAR(255) NULL,
  FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

INSERT INTO transacao (id_cliente, tipo_movimentacao, valor_transacao, numero_conta, horario_transacao, dia_transacao, conta_destino) VALUES
((SELECT id_cliente FROM cliente WHERE nome = 'João'), 'DEPOSITO', 20000.00, '123456', '10:00', '2022-01-05', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'João'), 'DEPOSITO', 5000.00, '123456', '16:00', '2022-01-05', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'João'), 'SAQUE', 10000.00, '123456', '14:30', '2022-02-07', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Renato'), 'DEPOSITO', 3000.00, '789012', '11:15', '2022-06-18', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Renato'), 'DEPOSITO', 2100.00, '789012', '11:15', '2022-07-20', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Maria'), 'DEPOSITO', 3000.00, '783412', '15:15', '2022-05-10', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Maria'), 'SAQUE', 690.00, '783412', '15:15', '2022-05-20', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Pedro'), 'DEPOSITO', 1000.00, '012789', '19:15', '2022-08-18', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Pedro'), 'SAQUE', 600.00, '012789', '12:15', '2022-10-20', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Joana'), 'DEPOSITO', 1000.00, '017892', '11:23', '2022-03-18', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Joana'), 'SAQUE', 400.00, '017892', '21:15', '2022-04-20', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Beatriz'), 'DEPOSITO', 2000.00, '986312', '08:23', '2022-07-20', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Beatriz'), 'SAQUE', 350.00, '986312', '22:20', '2022-11-13', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Carlos'), 'DEPOSITO', 500.00, '002312', '11:23', '2015-03-18', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Carlos'), 'SAQUE', 90.00, '002312', '21:15', '2016-04-20', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Izabel'), 'DEPOSITO', 5000.00, '655463', '11:23', '2021-03-18', ''),
((SELECT id_cliente FROM cliente WHERE nome = 'Izabel'), 'SAQUE', 2650.00, '655463', '16:45', '2022-01-15', ''),

((SELECT id_cliente FROM cliente WHERE nome = 'Ezequiel'), 'DEPOSITO', 10000.00, '124421', '16:45', '2020-02-15', '');
