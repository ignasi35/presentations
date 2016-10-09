DROP TABLE IF EXISTS ORDERS;

CREATE TABLE ORDERS (
  id        INTEGER,
  name      VARCHAR(120),
  username  VARCHAR(50)
);

INSERT INTO ORDERS (id, name, username ) values ( 23, 'lemons', 'ignasi35');
INSERT INTO ORDERS (id, name, username ) values ( 24, 'oranges', 'ignasi35');
INSERT INTO ORDERS (id, name, username ) values ( 25, 'apples', 'ignasi35');
INSERT INTO ORDERS (id, name, username ) values ( 26, 'milk', 'ignasi35');