CREATE TABLE signature_criteria
(
    id            SERIAL PRIMARY KEY,
    nationality   VARCHAR(255),
    commercialact VARCHAR(255),
    documenttype  VARCHAR(255),
    segment       VARCHAR(255)
);

CREATE TABLE nationalities
(
    nationality VARCHAR(255) PRIMARY KEY
);
