INSERT INTO users(id, dni, email, enabled, full_name, password, phone_number) VALUES
(1, '00000000', 'test.user@email.com', true, 'Test User', '$2a$10$KGY8XLFNN0tmiE8n007xf.afciJ1DELvc4TK8x1kijo9pUNZdSI5u', '00000000');

INSERT INTO users(id, dni, email, enabled, full_name, password, phone_number) VALUES
(2, '22222222', 'juan.perez@email.com', true, 'Juan Perez', '$2a$10$KGY8XLFNN0tmiE8n007xf.afciJ1DELvc4TK8x1kijo9pUNZdSI5u', '11111111');

INSERT INTO accounts(id, alias, balance, cvu, user_id) VALUES
(1, 'TEST.USER.ALIAS', 5000.0, '11111111111', 1);

INSERT INTO accounts(id, alias, balance, cvu, user_id) VALUES
(2, 'JUAN.PEREZ.ALIAS', 0.0, '33333333333', 2);

INSERT INTO cards(id, card_holder, card_number, cvv, expiration_date, account_id) VALUES
(1, 'Test User', '1111111111111111', '111', '2030-01-01', 1);

INSERT INTO transactions(id, amount, transaction_date, transaction_type, from_account_id, to_account_id) VALUES
(1, 250.0, '2024-09-11 14:27:34.015687', 'CARD_DEPOSIT', 1, 1);