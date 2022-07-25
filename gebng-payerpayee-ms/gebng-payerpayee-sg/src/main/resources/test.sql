

DROP table x.TableA;
CREATE TABLE x.TableA (
  Id BIGINT IDENTITY(1,1) NOT NULL,
  col1 varchar(30) NOT NULL,
  CONSTRAINT PK_col1 PRIMARY KEY(col1)
)

DROP table x.TableB;
CREATE TABLE x.TableB (
    Id BIGINT IDENTITY(1,1) NOT NULL,
    col1 varchar(30) NOT NULL,
    col2 varchar(10) NULL
)

ALTER TABLE x.TableB
ADD CONSTRAINT FK_TableB_col1 FOREIGN KEY (col1)
REFERENCES x.TableA(col1)