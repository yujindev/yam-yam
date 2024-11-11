--other테이블 생성
CREATE TABLE other (
    other_num NUMBER PRIMARY KEY,  -- other_num을 주키로 지정
    fp_num NUMBER,
    mem_num NUMBER,
    CONSTRAINT fk_other_num FOREIGN KEY (other_num) REFERENCES MEMBER(mem_num),  -- other_num이 MEMBER 테이블의 mem_num을 참조
    CONSTRAINT fk_mem_num FOREIGN KEY (mem_num) REFERENCES MEMBER(mem_num),     -- mem_num도 MEMBER 테이블의 mem_num을 참조
    CONSTRAINT fk_fp_num FOREIGN KEY (fp_num) REFERENCES FPLACE(fp_num)
); 

CREATE TABLE save_store(
  save_num NUMBER PRIMARY KEY, --save_num을 주키로 지정
  save_name VARCHAR2(60),
  other_num NUMBER,
  CONSTRAINT fk_save_store_other_num FOREIGN KEY (other_num) REFERENCES OTHER(other_num)    -- other_num이 OTHER 테이블의 other_num을 참조
);

CREATE TABLE reviews_store(
  rest_num NUMBER PRIMARY KEY, -- rest_num을 주키로 지정
  rest_name VARCHAR2(60),
  rest_hit NUMBER(9),
  rest_con CLOB,
  rest_score INT,
  other_num NUMBER,
  CONSTRAINT fk_reviews_store_other_num FOREIGN KEY (other_num) REFERENCES OTHER(other_num) --other_num이 other테이블의 other_num을 참조
);

--chat 테이블 생성
CREATE TABLE chat(
chat_num NUMBER PRIMARY KEY, -- chat_num을 주키로 지정
chat_sender_id VARCHAR2(30),
chat_receiver_id VARCHAR2(30),
chat_message VARCHAR2(300),
chat_sent_at DATE,
chat_read NUMBER,
mem_num NUMBER,
CONSTRAINT fk_chat_mem_num FOREIGN KEY (mem_num) REFERENCES MEMBER(mem_num) --mem_num이 member 테이블의 mem_num을 참조
);
