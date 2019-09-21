// User table population
INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Shan', 'Shahid', 'sshahid@gmail.com', 'PKR', 'PAK');

INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Sharukh', 'Khan', 'skhan@gmail.com', 'INR', 'IND');

INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Bruce', 'Lee', 'blee@gmail.com', 'YEN', 'JPN');

INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Thomas', 'Shelby', 'tshelby@gmail.com', 'GBP', 'GBR');

INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Morgan', 'Freeman', 'mfreeman@gmail.com', 'USD', 'USA');

INSERT INTO user
  (first_name, last_name, email_address, currency, country)
VALUES ( 'Álvaro', 'Morte', 'amorte@gmail.com', 'EUR', 'ESP');


// Account table population
INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 1, 1, 34632789.0);

INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 2, 1, 123432454.0);

INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 3, 2, 32423434.0);

INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 4, 2, 3500.0);

INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 5, 1, 10000.0);

INSERT INTO account
  (user_id, account_type, account_balance)
VALUES ( 6, 1, 473.0);


// Transaction table population
INSERT INTO transaction
  (from_account, to_account, amount)
VALUES ( 1, 2, 33);

INSERT INTO transaction
  (from_account, to_account, amount)
VALUES ( 1, 4, 71);

INSERT INTO transaction
  (from_account, to_account, amount)
VALUES ( 6, 2, 11);

INSERT INTO transaction
  (from_account, to_account, amount)
VALUES ( 3, 5, 100);
