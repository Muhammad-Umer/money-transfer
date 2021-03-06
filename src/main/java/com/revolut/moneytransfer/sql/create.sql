CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email_address VARCHAR(255) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  country VARCHAR(3) NOT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE account (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  account_type INT NOT NULL,
  account_balance number NOT NULL DEFAULT 0,
  open BOOLEAN NOT NULL DEFAULT TRUE,
  creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE transaction (
  id INT NOT NULL AUTO_INCREMENT,
  from_account INT NOT NULL,
  to_account INT NOT NULL,
  amount number NOT NULL,
  creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (from_account) REFERENCES account(id),
  FOREIGN KEY (to_account) REFERENCES account(id)
);
