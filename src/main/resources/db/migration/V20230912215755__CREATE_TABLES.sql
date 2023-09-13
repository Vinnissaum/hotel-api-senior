CREATE TABLE guest (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE checkin (
    id UUID NOT NULL PRIMARY KEY,
    arrival_at TIMESTAMP NOT NULL,
    left_at TIMESTAMP,
    guest_id UUID,
    FOREIGN KEY (guest_id) REFERENCES guest (id) ON DELETE SET NULL
);