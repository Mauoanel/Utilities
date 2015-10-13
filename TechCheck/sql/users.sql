use mysql;

DELETE FROM user WHERE user = 'techcheck';
FLUSH PRIVILEGES;

CREATE USER 'techcheck'@'localhost' IDENTIFIED BY 's3cr3t';
GRANT ALL PRIVILEGES ON *.* TO 'techcheck'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;


