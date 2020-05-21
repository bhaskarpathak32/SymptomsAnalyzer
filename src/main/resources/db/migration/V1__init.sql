-----------------------------------------------------------------------------------------------------------------
-- Tables
-----------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE seq START 1;
CREATE TABLE usertable (
  id                    BIGINT PRIMARY KEY,
  country               BIGINT,
  name                  TEXT,
  age	                  BIGINT,
  cough	                BIGINT,
  fever	                BIGINT,
  headache	            BIGINT,
  chest_pain	          BIGINT,
  runny_nose	          BIGINT,
  sneeze	              BIGINT,
  breath_difficulty	    BIGINT,
  status                BIGINT,
  bleeding_gum          BIGINT,
	gum_pain	            BIGINT,
	nasal_congestion      BIGINT,
	clogged_ear           BIGINT,
	throat_pain           BIGINT,
	ear_pain              BIGINT,
	wheezing              BIGINT,
	rash                  BIGINT,
	nausea                BIGINT

);

