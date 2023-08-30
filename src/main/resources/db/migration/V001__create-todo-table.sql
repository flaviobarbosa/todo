CREATE TABLE todo (
    id INTEGER PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(500),
    done BOOLEAN DEFAULT false
);