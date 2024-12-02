CREATE TABLE REVIEWS (
    REVIEWS_NUM NUMBER NOT NULL,
    REVIEWS_SCORE NUMBER NOT NULL,
    REVIEWS_CON CLOB NOT NULL,
    REVIEWS_IMG1 VARCHAR2(400) NOT NULL,
    REVIEWS_DATE DATE DEFAULT SYSDATE NOT NULL,
    MEM_NUM NUMBER NOT NULL,
    FP_NUM NUMBER NOT NULL,
    
    CONSTRAINT REVIEWS_PK PRIMARY KEY (reviews_num),
    CONSTRAINT REVIEWS_FK FOREIGN KEY (MEM_num) REFERENCES MEMBER(mem_num),
    constraint REVIEWS_FK2 foreign key (fp_num) references fplace (fp_num)
);
Create sequence reviews_num;

CREATE TABLE BMSTORE (
    mem_num NUMBER NOT NULL,  
    fp_num NUMBER NOT NULL,        
    
    CONSTRAINT BMSTORE_FK FOREIGN KEY (mem_num) REFERENCES MEMBER(mem_num),
    constraint BMSTORE_FK2 foreign key (fp_num) references fplace (fp_num)
);

-- Create BMREVIEWS Table
CREATE TABLE BMREVIEWS (
    mem_num NUMBER NOT NULL,      
    reviews_num NUMBER NOT NULL, 
    reg_date date default SYSDATE NOT NULL,
    
    CONSTRAINT BMREVIEWS_FK FOREIGN KEY (mem_num) REFERENCES MEMBER(mem_num),
    CONSTRAINT BMREVIEWS_FK2 FOREIGN KEY (reviews_num) REFERENCES REVIEWS (reviews_num) ON DELETE CASCADE
);
/*CREATE TABLE bmreviews (
	mem_num NUMBER NOT NULL,        -- 회원 번호 (외래 키)
	reviews_num NUMBER NOT NULL,                   -- 리뷰 번호 (외래 키)
                        
    CONSTRAINT bmreviews_fk_reviews FOREIGN KEY (reviews_num) REFERENCES reviews (reviews_num) ON DELETE CASCADE,
    CONSTRAINT bmreviews_fk_mem FOREIGN KEY (mem_num) REFERENCES member (mem_num)
);*/