INSERT IGNORE INTO TBL_SERVICOS (id, nome, descricao, duracao_minutos, preco, ativo, data_cadastro, data_atualizacao) 
VALUES 
(1, 'Manicure Simples', 'Limpeza, corte e esmaltação das unhas das mãos', 30, 25.00, true, NOW(), NOW()),
(2, 'Pedicure Simples', 'Limpeza, corte e esmaltação das unhas dos pés', 45, 30.00, true, NOW(), NOW()),
(3, 'Manicure com Alongamento', 'Alongamento de unhas em gel ou acrílico', 90, 80.00, true, NOW(), NOW()),
(4, 'Pedicure Especial', 'Pedicure com hidratação, esfoliação e spa dos pés', 60, 50.00, true, NOW(), NOW()),
(5, 'Manicure Spa', 'Manicure com hidratação e massagem das mãos', 45, 40.00, true, NOW(), NOW()),
(6, 'Design de Unhas', 'Design artístico e personalizado nas unhas', 60, 60.00, true, NOW(), NOW()),
(7, 'Manutenção de Alongamento', 'Manutenção de unhas alongadas', 60, 50.00, true, NOW(), NOW()),
(8, 'Remoção de Alongamento', 'Remoção segura de alongamento', 30, 20.00, true, NOW(), NOW()),
(9, 'Cutícula Profunda', 'Tratamento especializado de cutícula', 40, 35.00, true, NOW(), NOW()),
(10, 'Blindagem', 'Aplicação de camada protetora nas unhas naturais', 50, 45.00, true, NOW(), NOW());

INSERT IGNORE INTO TBL_PROFISSIONAIS (id, nome, telefone, email, hora_inicio, hora_fim, ativo, data_cadastro, data_atualizacao) 
VALUES 
(1, 'Ana Souza', '(11) 98888-7777', 'ana@manicure.com', '08:00:00', '18:00:00', true, NOW(), NOW()),
(2, 'Carlos Mendes', '(11) 97777-6666', 'carlos@manicure.com', '09:00:00', '19:00:00', true, NOW(), NOW()),
(3, 'Beatriz Lima', '(11) 96666-5555', 'beatriz@manicure.com', '10:00:00', '20:00:00', true, NOW(), NOW());

INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (1, 1);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (1, 2);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (1, 3);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (1, 4);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (1, 5);

INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (2, 2);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (2, 3);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (2, 4);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (2, 5);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (2, 6);

INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (3, 3);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (3, 4);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (3, 5);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (3, 6);
INSERT IGNORE INTO TBL_PROFISSIONAL_DIAS_DISPONIVEIS (profissional_id, dia_semana) VALUES (3, 7);

INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (1, 1);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (1, 2);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (1, 3);

INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 1);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 2);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 3);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 4);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 5);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (2, 6);

INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (3, 3);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (3, 6);
INSERT IGNORE INTO TBL_PROFISSIONAL_SERVICOS (profissional_id, servico_id) VALUES (3, 7);

INSERT IGNORE INTO TBL_CLIENTES (nome, telefone, email, data_cadastro) 
VALUES 
('Maria Santos', '(11) 95555-4444', 'maria@email.com', NOW()),
('João Silva', '(11) 94444-3333', 'joao@email.com', NOW());

-- Agendamento 1: Maria com Ana para Manicure Simples (amanhã às 10:00)
INSERT INTO TBL_AGENDAMENTOS 
(cliente_id, profissional_id, servico_id, data_hora_inicio, data_hora_fim, status, valor_cobrado, observacoes, data_criacao, data_atualizacao)
SELECT 
    (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com'),
    (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'ana@manicure.com'),
    (SELECT id FROM TBL_SERVICOS WHERE nome = 'Manicure Simples'),
    DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 10 HOUR,
    DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 10 HOUR + INTERVAL 30 MINUTE,
    'AGENDADO',
    25.00,
    'Prefere esmalte vermelho',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM TBL_AGENDAMENTOS a 
    WHERE a.cliente_id = (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com')
    AND a.profissional_id = (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'ana@manicure.com')
    AND DATE(a.data_hora_inicio) = DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
    AND TIME(a.data_hora_inicio) = '10:00:00'
);

-- Agendamento 2: João com Carlos para Pedicure Simples (depois de amanhã às 14:00)
INSERT INTO TBL_AGENDAMENTOS 
(cliente_id, profissional_id, servico_id, data_hora_inicio, data_hora_fim, status, valor_cobrado, observacoes, data_criacao, data_atualizacao)
SELECT 
    (SELECT id FROM TBL_CLIENTES WHERE email = 'joao@email.com'),
    (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'carlos@manicure.com'),
    (SELECT id FROM TBL_SERVICOS WHERE nome = 'Pedicure Simples'),
    DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 14 HOUR,
    DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 14 HOUR + INTERVAL 45 MINUTE,
    'CONFIRMADO',
    30.00,
    'Pé com calosidade',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM TBL_AGENDAMENTOS a 
    WHERE a.cliente_id = (SELECT id FROM TBL_CLIENTES WHERE email = 'joao@email.com')
    AND a.profissional_id = (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'carlos@manicure.com')
    AND DATE(a.data_hora_inicio) = DATE(DATE_ADD(NOW(), INTERVAL 2 DAY))
    AND TIME(a.data_hora_inicio) = '14:00:00'
);

-- Agendamento 3: Maria com Beatriz para Alongamento (daqui a 3 dias às 11:00)
INSERT INTO TBL_AGENDAMENTOS 
(cliente_id, profissional_id, servico_id, data_hora_inicio, data_hora_fim, status, valor_cobrado, observacoes, data_criacao, data_atualizacao)
SELECT 
    (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com'),
    (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'beatriz@manicure.com'),
    (SELECT id FROM TBL_SERVICOS WHERE nome = 'Manicure com Alongamento'),
    DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 11 HOUR,
    DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 11 HOUR + INTERVAL 90 MINUTE,
    'AGENDADO',
    80.00,
    'Alongamento em gel, cor nude',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM TBL_AGENDAMENTOS a 
    WHERE a.cliente_id = (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com')
    AND a.profissional_id = (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'beatriz@manicure.com')
    AND DATE(a.data_hora_inicio) = DATE(DATE_ADD(NOW(), INTERVAL 3 DAY))
    AND TIME(a.data_hora_inicio) = '11:00:00'
);

-- Agendamento 4: Cancelado para teste
INSERT INTO TBL_AGENDAMENTOS 
(cliente_id, profissional_id, servico_id, data_hora_inicio, data_hora_fim, status, valor_cobrado, observacoes, data_cancelamento, motivo_cancelamento, data_criacao, data_atualizacao)
SELECT 
    (SELECT id FROM TBL_CLIENTES WHERE email = 'joao@email.com'),
    (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'ana@manicure.com'),
    (SELECT id FROM TBL_SERVICOS WHERE nome = 'Manicure Spa'),
    DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 15 HOUR,
    DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 15 HOUR + INTERVAL 45 MINUTE,
    'CANCELADO',
    40.00,
    'Cliente solicitou massagem extra',
    DATE_SUB(NOW(), INTERVAL 1 DAY),
    'Cliente mudou de ideia',
    DATE_SUB(NOW(), INTERVAL 3 DAY),
    DATE_SUB(NOW(), INTERVAL 1 DAY)
WHERE NOT EXISTS (
    SELECT 1 FROM TBL_AGENDAMENTOS a 
    WHERE a.cliente_id = (SELECT id FROM TBL_CLIENTES WHERE email = 'joao@email.com')
    AND a.profissional_id = (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'ana@manicure.com')
    AND DATE(a.data_hora_inicio) = DATE(DATE_SUB(NOW(), INTERVAL 2 DAY))
    AND TIME(a.data_hora_inicio) = '15:00:00'
);

-- Agendamento 5: Realizado para teste
INSERT INTO TBL_AGENDAMENTOS 
(cliente_id, profissional_id, servico_id, data_hora_inicio, data_hora_fim, status, valor_cobrado, observacoes, data_criacao, data_atualizacao)
SELECT 
    (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com'),
    (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'carlos@manicure.com'),
    (SELECT id FROM TBL_SERVICOS WHERE nome = 'Design de Unhas'),
    DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 16 HOUR,
    DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 16 HOUR + INTERVAL 60 MINUTE,
    'REALIZADO',
    60.00,
    'Design floral azul',
    DATE_SUB(NOW(), INTERVAL 2 DAY),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM TBL_AGENDAMENTOS a 
    WHERE a.cliente_id = (SELECT id FROM TBL_CLIENTES WHERE email = 'maria@email.com')
    AND a.profissional_id = (SELECT id FROM TBL_PROFISSIONAIS WHERE email = 'carlos@manicure.com')
    AND DATE(a.data_hora_inicio) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY))
    AND TIME(a.data_hora_inicio) = '16:00:00'
);
