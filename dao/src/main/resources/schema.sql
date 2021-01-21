CREATE TABLE gift_certificate (
    id                INTEGER NOT NULL,
    name              VARCHAR(255) NOT NULL,
    desription        VARCHAR(255) NOT NULL,
    price             INTEGER NOT NULL,
    duratiom          INTEGER NOT NULL,
    create_date       TIMESTAMP NOT NULL,
    last_update_date  TIMESTAMP NOT NULL
);

ALTER TABLE gift_certificate ADD CONSTRAINT gift_certificate_pk PRIMARY KEY ( id );

CREATE TABLE gift_to_tag (
    gift_certificate_id  INTEGER NOT NULL,
    tag_id               INTEGER NOT NULL
);

ALTER TABLE gitt_to_tag ADD CONSTRAINT gift_to_tag_pk PRIMARY KEY ( gift_certificate_id,
                                                                      tag_id );

CREATE TABLE tag (
    id    INTEGER NOT NULL,
    name  VARCHAR(255) NOT NULL
);

ALTER TABLE tag ADD CONSTRAINT tag_pk PRIMARY KEY ( id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE gift_to_tag
    ADD CONSTRAINT gift_to_tag_gift_certificate_fk FOREIGN KEY ( gift_certificate_id )
        REFERENCES gift_certificate ( id );

ALTER TABLE gift_to_tag
    ADD CONSTRAINT gift_to_tag_tag_fk FOREIGN KEY ( tag_id )
        REFERENCES tag ( id );
