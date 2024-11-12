create table member(
mem_num number not null,
mem_id varchar2(12) not null,
mem_auth number(1) default 2 not null,
mem_nickname varchar2(30) not null,
constraint member_pk primary key (mem_num)
);

Create table member_detail(
mem_pw varchar2(12) not null,
mem_phone varchar2(15) not null,
mem_img varchar2(400),
mem_date date default sysdate not null,
mem_mdate date,
constraint member_detail_fk foreign key (mem_num) references member (mem_num)
);
Create sequence member_seq;

Create table dopamine(
dp_num number not null,
dp_category number not null,
dp_title varchar2(60) not null,
dp_content clob not null,
constraint dopamine_pk primary key (dp_num)
constraint dopamine_fk foreign key (mem_num) references member (mem_num)
);
Create sequence dopamine_seq;

Create table reserv(
rs_num number not null,
rs_cnt number not null,
rs_time varchar2(15) not null,
rs_status number not null,
constraint reserv_pk primary key (rs_num),
constraint reserv_fk1 foreign key (mem_num) references member (mem_num),
constraint reserv_fk2 foreign key (fp_num) references fplace (fp_num)
);
Create sequence reserv_seq;