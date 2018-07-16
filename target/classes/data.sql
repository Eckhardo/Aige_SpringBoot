DROP TABLE IF EXISTS country;

CREATE TABLE IF NOT EXISTS country (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    code VARCHAR(2) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

INSERT  INTO country (name, code) VALUES ('Germany', 'DE');
INSERT  INTO country (name, code) VALUES ('Danmark', 'DK');
INSERT  INTO country (name, code) VALUES ('Italy', 'IT');
INSERT  INTO country (name, code) VALUES ('France', 'FR');
INSERT  INTO country (name, code) VALUES ('Finland', 'FI');
INSERT  INTO country (name, code) VALUES ('Norway', 'NO');
INSERT  INTO country (name, code) VALUES ('Sweden', 'SE');
INSERT  INTO country (name, code) VALUES ('Switzerland', 'CH');
INSERT  INTO country (name, code) VALUES ('Portugal', 'PT');
INSERT  INTO country (name, code) VALUES ('Spain', 'ES');
INSERT  INTO country (name, code) VALUES ('Brazil', 'BR');
INSERT  INTO country (name, code) VALUES ('Argentina', 'AR');
INSERT  INTO country (name, code) VALUES ('Japan', 'JP');
INSERT  INTO country (name, code) VALUES ('China', 'CN');
INSERT  INTO country (name, code) VALUES ('Canada', 'CA');
INSERT  INTO country (name, code) VALUES ('Mexico', 'MX');

