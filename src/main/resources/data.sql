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
