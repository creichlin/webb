CREATE TABLE in_base (
  identifier integer primary key auto_increment,
  value1 text
);

CREATE TABLE in_child (
  identifier integer primary key auto_increment,
  value1 text,
  value2 text
);