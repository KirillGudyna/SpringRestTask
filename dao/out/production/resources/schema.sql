CREATE TABLE gift_certificate (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    name              VARCHAR(255) NOT NULL,
    description       VARCHAR(255) NOT NULL,
    price             INTEGER NOT NULL,
    duration          INTEGER NOT NULL,
    create_date       VARCHAR(50) NOT NULL,
    last_update_date  VARCHAR(50) NOT NULL
);

ALTER TABLE gift_certificate ADD CONSTRAINT gift_certificate_pk PRIMARY KEY ( id );

CREATE TABLE gift_to_tag (
    gift_certificate_id  BIGINT NOT NULL,
    tag_id               BIGINT NOT NULL
);

ALTER TABLE gift_to_tag ADD CONSTRAINT gift_to_tag_pk PRIMARY KEY ( gift_certificate_id,
                                                                      tag_id );

CREATE TABLE tag (
    id    BIGINT NOT NULL AUTO_INCREMENT,
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
