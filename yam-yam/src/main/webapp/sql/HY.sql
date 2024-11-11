--식당 정보
create table fplace(
fp_num number not null,
fp_name	varchar2(30) not null,
fp_phone varchar2(15) not null,
fp_time varchar2(30) not null,
fp_storeimg varchar2(400) not null,
fp_menuimg1 varchar2(400) not null,
fp_menuimg2 varchar2(400) not null,
fp_menuimg3 varchar2(400) not null,
fp_menuimg4 varchar2(400) not null,
fp_loc varchar2(50)	not null,
constraint pk_fp_num primary key (fp_num)
);
create sequence fplace_seq;
--오늘의 메뉴
create table TMENU (
tm_num number not null, 
tm_menu varchar2(50) not null, 
constraint pk_tm_menu primary key (tm_num),
constraint uk_tm_menu unique (tm_num, tm_menu) 
);
create sequence TMENU_seq;
