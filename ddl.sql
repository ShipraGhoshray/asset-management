-- ==============================
-- Static Reference Tables
CREATE SCHEMA asset_schema;
SET search_path TO asset_schema;
-- ==============================
CREATE TABLE portfolio (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE custodian (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE broker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- ==============================
-- Asset Table (static catalog)
-- ==============================
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

-- ==============================
-- Trade Table (dynamic transactions)
-- ==============================
CREATE TABLE trade (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    portfolio_id BIGINT NOT NULL,
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

-- ==============================
-- Bucket Table (netting rules)
-- ==============================
CREATE TABLE bucket (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
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

-- ==============================
-- Supporting Tables
-- ==============================
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

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'Viewer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_asset_watchlist (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES app_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_asset_watch FOREIGN KEY(asset_id) REFERENCES asset(id) ON DELETE CASCADE
);

-- ==============================
-- Seed Data
-- ==============================
INSERT INTO portfolio (name) VALUES 
    ('Fixed Income Portfolio'), 
    ('Equity Portfolio'), 
    ('Derivatives Portfolio');

INSERT INTO custodian (name) VALUES 
    ('State Street'), 
    ('BNY Mellon'), 
    ('HSBC Custody');

INSERT INTO broker (name) VALUES 
    ('Goldman Sachs'), 
    ('Morgan Stanley'), 
    ('JP Morgan');
	
	
	
-- Portfolio table
CREATE TABLE portfolio (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
-- Custodian table
CREATE TABLE custodian (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
-- Broker table
CREATE TABLE broker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
-- Asset table
CREATE TABLE asset (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    threshold NUMERIC(12,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Trade table
CREATE TABLE trade (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    portfolio_id BIGINT NOT NULL,
    broker_id BIGINT NOT NULL,
    custodian_id BIGINT NOT NULL,
    trade_date DATE NOT NULL,
    settlement_date DATE,
    quantity NUMERIC(18,4) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_trade FOREIGN KEY(asset_id) REFERENCES asset(id),
    CONSTRAINT fk_portfolio_trade FOREIGN KEY(portfolio_id) REFERENCES portfolio(id),
    CONSTRAINT fk_broker_trade FOREIGN KEY(broker_id) REFERENCES broker(id),
    CONSTRAINT fk_custodian_trade FOREIGN KEY(custodian_id) REFERENCES custodian(id)
);
-- Bucket table
CREATE TABLE bucket (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
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
-- Price history table
CREATE TABLE price_history (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    source VARCHAR(50),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_price FOREIGN KEY(asset_id) REFERENCES asset(id) ON DELETE CASCADE
);
-- Notification table
CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    severity VARCHAR(20) DEFAULT 'INFO',
    channel VARCHAR(20) DEFAULT 'Push',
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_notification FOREIGN KEY(asset_id) REFERENCES asset(id) ON DELETE CASCADE
);

-- Portfolios
INSERT INTO portfolio (name) VALUES ('Fixed Income Portfolio'), ('Equity Portfolio'), ('Derivatives Portfolio');

-- Custodians
INSERT INTO custodian (name) VALUES ('State Street'), ('BNY Mellon'), ('HSBC Custody');

-- Brokers
INSERT INTO broker (name) VALUES ('Goldman Sachs'), ('Morgan Stanley'), ('JP Morgan');