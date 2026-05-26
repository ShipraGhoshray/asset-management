-- ==============================
-- Static Reference Tables
CREATE SCHEMA asset_schema;
SET search_path TO asset_schema;
-- ==============================
CREATE TABLE portfolio (
    id BIGSERIAL PRIMARY KEY,              -- internal numeric key
    --portfolio_code VARCHAR(20) NOT NULL UNIQUE,  -- external business ID like PORT123
    name VARCHAR(100) NOT NULL UNIQUE,
	account_id VARCHAR(50) NOT NULL UNIQUE,
    created_at  TIMESTAMP NOT NULL
);
CREATE TABLE custodian (
    id BIGSERIAL PRIMARY KEY,
    custodian_code VARCHAR(20) UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE broker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
); 
CREATE TABLE asset_mgmt.user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Asset Table (static catalog)
CREATE TABLE asset (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    threshold NUMERIC(12,2),   -- asset-specific threshold
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trade Table (dynamic transactions)
CREATE TABLE trade (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    portfolio_id VARCHAR(50) NOT NULL,
    broker_id BIGINT NOT NULL,
    custodian_id BIGINT NOT NULL,
    trade_date DATE NOT NULL,
    settlement_date DATE,
    quantity NUMERIC(18,4) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    direction VARCHAR(10) CHECK (direction IN ('BUY','SELL')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_trade FOREIGN KEY(asset_id) REFERENCES asset(id),
    CONSTRAINT fk_portfolio_trade FOREIGN KEY(portfolio_id) REFERENCES portfolio(id),
    CONSTRAINT fk_broker_trade FOREIGN KEY(broker_id) REFERENCES broker(id),
    CONSTRAINT fk_custodian_trade FOREIGN KEY(custodian_id) REFERENCES custodian(id)
);

-- Bucket Table (netting rules)
CREATE TABLE bucket (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id VARCHAR(50) NOT NULL,
    broker_id BIGINT NOT NULL,
    custodian_id BIGINT NOT NULL,
    netting_group VARCHAR(50) NOT NULL,
    effective_date DATE NOT NULL,
    expiry_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_portfolio_bucket FOREIGN KEY(portfolio_id) REFERENCES portfolio(id) ON DELETE CASCADE,
    CONSTRAINT fk_broker_bucket FOREIGN KEY(broker_id) REFERENCES broker(id) ON DELETE CASCADE,
    CONSTRAINT fk_custodian_bucket FOREIGN KEY(custodian_id) REFERENCES custodian(id) ON DELETE CASCADE
);

CREATE TABLE price_history (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    source VARCHAR(50),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_price FOREIGN KEY(asset_id) REFERENCES asset(id) ON DELETE CASCADE
);

CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    severity VARCHAR(20) DEFAULT 'INFO',
    channel VARCHAR(20) DEFAULT 'Push',
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_notification FOREIGN KEY(asset_id) REFERENCES asset(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id              VARCHAR(50) PRIMARY KEY,
    account_id      VARCHAR(50) NOT NULL,
    symbol          VARCHAR(20) NOT NULL,
    side 			VARCHAR(10) NOT NULL,
	quantity        DECIMAL(18,4) NOT NULL,
    price           DECIMAL(18,4),
    filled_quantity DECIMAL(18,4) DEFAULT 0,
    avg_price       DECIMAL(18,8) DEFAULT 0,
    status          VARCHAR(30) NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP
);
CREATE TABLE order_executions (
    id                VARCHAR(50) PRIMARY KEY,
    order_id          VARCHAR(50) NOT NULL,
    executed_quantity DECIMAL(18,4) NOT NULL,
    executed_price    DECIMAL(18,8) NOT NULL,
    execution_time    TIMESTAMP NOT NULL,
    CONSTRAINT fk_execution_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE executions (
    id VARCHAR(50) PRIMARY KEY,
    order_id VARCHAR(50) NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    quantity DECIMAL(19,4) NOT NULL,
    price DECIMAL(19,4) NOT NULL,
    status VARCHAR(20) NOT NULL,
    execution_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reported BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE holding (
    id            VARCHAR(50) PRIMARY KEY,
    portfolio_id  VARCHAR(50) NOT NULL,
    symbol        VARCHAR(20) NOT NULL,
    quantity      DECIMAL(18,4) NOT NULL,
    avg_price     DECIMAL(18,8) NOT NULL,
    CONSTRAINT fk_holding_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio(id)
);
-- Seed Data
-- Portfolios
INSERT INTO portfolio (name) VALUES ('Fixed Income Portfolio'), ('Equity Portfolio'), ('Derivatives Portfolio');
INSERT INTO portfolio (id, name, account_id, created_at) VALUES
('PF-ACC001', 'ACC001 Portfolio', 'ACC001', NOW()),
('PF-ACC002', 'ACC002 Portfolio', 'ACC002', NOW()),
('PF-ACC003', 'ACC003 Portfolio', 'ACC003', NOW());

-- Custodians
INSERT INTO custodian (name) VALUES ('State Street'), ('BNY Mellon'), ('HSBC Custody');
-- Brokers
INSERT INTO broker (name) VALUES ('Goldman Sachs'), ('Morgan Stanley'), ('JP Morgan');
	-- users
INSERT INTO asset_mgmt.user (username, password, role) VALUES ('shipra', '$2a$10$hashedPasswordHere', 'ROLE_ADMIN');
INSERT INTO asset_mgmt.user (username, password, role) VALUES ('joey', '$2a$10$hashedPasswordHere', 'ROLE_USER');
