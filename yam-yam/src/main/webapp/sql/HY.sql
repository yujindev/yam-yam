--식당 정보
create table fplace(
fp_num number not null,
fp_name	varchar2(30) not null,
fp_phone varchar2(15) not null,
fp_time varchar2(30) not null,
fp_storeimg varchar2(400) not null,
fp_loc varchar2(50)	not null,
mem_num number not null,
constraint pk_fp_num primary key (fp_num)
constraint mem_num_fk foreign key (mem_num) references member(mem_num)
);
create sequence fplace_seq;
--오늘의 메뉴
CREATE TABLE tmenu (
    tm_num NUMBER NOT NULL,
    tm_menu VARCHAR2(50) NOT NULL,
    mem_num NUMBER NOT NULL,
    CONSTRAINT tmenu_tm_num_pk PRIMARY KEY (tm_num),
    CONSTRAINT tmenu_tm_menu_uk UNIQUE (tm_menu),
    CONSTRAINT tmenu_mem_num_fk FOREIGN KEY (mem_num) REFERENCES member(mem_num)
);
create sequence TMENU_seq;

create table fpmenu(
fpmenu_num number not null,
fpmenu_img varchar2(400) not null,
fpmenu_name varchar2(100) not null,
fpmenu_price number not null,
fp_num number not null,
mem_num number not null,
constraint fpmenu_num_pk primary key(fpmenu_num),
constraint  fp_num_fk foreign key (fp_num) references fplace(fp_num)
constraint mem_num_fk foreign key (mem_num) references member(mem_num)
);
create sequence fpmenu_seq;