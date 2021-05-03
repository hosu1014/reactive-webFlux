
CREATE TABLE customer (
	id int8 NOT null primary key,
	name varchar(50) NOT NULL,
	reg_dttm timestamp NULL
); 


select *
  from customer;
  
 
create table om_cart( 
cart_sn int8 not null PRIMARY KEY, --장바구니순번
tr_no VARCHAR(16) not null, -- 거래처번호
lrtr_no VARCHAR(16) not null, -- 하위거래처번호 : 장바구니 상품의 그룹핑 조건이 됩니다.
mb_no VARCHAR(16) not null, -- 회원번호
spd_no VARCHAR(16) not null, -- 단품번호
od_qty integer, -- 주문수량
reg_dttm TIMESTAMP,
mod_dttm TIMESTAMP
);


select *
  from om_cart
 order by cart_sn  desc; 


CREATE TABLE public.me_member (
	mb_no int8 NOT NULL,
	user_id varchar(30) NOT NULL,
	passwd varchar(256) NOT NULL,
	mb_nm varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	rt_grp_no varchar(15) NULL,
	regr_id varchar(30) NOT NULL,
	reg_dttm timestamp NULL,
	modr_id varchar(30) NOT NULL,
	mod_dttm timestamp NULL,
	CONSTRAINT me_member_pkey PRIMARY KEY (mb_no)
);


INSERT INTO public.me_member
(mb_no, user_id, passwd, mb_nm, email, regr_id, reg_dttm, modr_id, mod_dttm, rt_grp_no)
VALUES(5, 'hosu1014', 'Me6/NnW1SnQOEfsb6MKnqMiz9tqamAPZbFv/2RyZ67g=', '정윤호', 'hosu1014@plateer.com', 'FRONT', '2021-04-23 17:22:46.958', 'FRONT', '2021-04-23 17:22:46.958', NULL);



select *
  from me_member;
 
 
CREATE TABLE public.st_rt_grp_base (
	rt_grp_no varchar(15) NOT NULL,
	sys_gb_cd varchar(10) NOT NULL,
	rt_grp_nm varchar(300) NOT NULL,
	aply_str_dt varchar(8) NOT NULL,
	aply_end_dt varchar(8) NOT NULL,
	use_yn varchar(1) NOT NULL,
	sys_reg_id varchar(30) NOT NULL,
	sys_reg_dtm timestamp NOT NULL,
	sys_mod_id varchar(30) NOT NULL,
	sys_mod_dtm timestamp NOT NULL,
	CONSTRAINT st_rt_grp_base_pk PRIMARY KEY (rt_grp_no)
);
 
INSERT INTO public.st_rt_grp_base
(rt_grp_no, sys_gb_cd, rt_grp_nm, aply_str_dt, aply_end_dt, use_yn, sys_reg_id, sys_reg_dtm, sys_mod_id, sys_mod_dtm)
VALUES('10000', '11', '시스템 관리자', '20210318', '20291231', 'Y', 'testadmin', '2021-03-05 14:03:59.837', 'kyungbok', '2021-03-22 13:47:33.537');

select *
  from st_rt_grp_base;

CREATE TABLE public.st_rt_info (
	rt_grp_no varchar(30) NOT NULL,
	rt_tgt_seq varchar(15) NOT NULL,
	rt_sub_gb_cd varchar(10) NOT NULL,
	use_yn varchar(1) NOT NULL,
	sys_reg_id varchar(30) NOT NULL,
	sys_reg_dtm timestamp NOT NULL,
	sys_mod_id varchar(30) NOT NULL,
	sys_mod_dtm timestamp NOT NULL,
	CONSTRAINT st_rt_info_pk PRIMARY KEY (rt_grp_no, rt_tgt_seq)
);

INSERT INTO public.st_rt_info
(rt_grp_no, rt_tgt_seq, rt_sub_gb_cd, use_yn, sys_reg_id, sys_reg_dtm, sys_mod_id, sys_mod_dtm)
VALUES('10000', '1000001', '01', 'Y', 'yoonho', '2021-03-22 17:30:59.832', 'yoonho', '2021-03-22 17:30:59.832');

select *
  from st_rt_info ;


CREATE TABLE public.st_rt_tgt_base (
	rt_tgt_seq varchar(15) NOT NULL,
	sys_gb_cd varchar(10) NOT NULL,
	rt_tgt_typ_cd varchar(10) NOT NULL,
	rt_tgt_nm varchar(200) NOT NULL,
	sort_seq numeric(5) NOT NULL DEFAULT 0,
	call_url varchar(2000) NULL,
	use_yn varchar(1) NOT NULL,
	cust_info_incl_yn varchar(1) NOT NULL,
	rmk_cont varchar(4000) NULL,
	popup_yn varchar(1) NOT NULL,
	btn_id varchar(30) NULL,
	upr_rt_tgt_seq varchar(15) NOT NULL,
	leaf_menu_yn varchar(1) NOT NULL,
	sys_reg_id varchar(30) NOT NULL,
	sys_reg_dtm timestamp NOT NULL,
	sys_mod_id varchar(30) NOT NULL,
	sys_mod_dtm timestamp NOT NULL,
	CONSTRAINT st_rt_tgt_base_pk PRIMARY KEY (rt_tgt_seq)
);
CREATE INDEX st_rt_tgt_base_idx01 ON public.st_rt_tgt_base USING btree (upr_rt_tgt_seq);


INSERT INTO public.st_rt_tgt_base
(rt_tgt_seq, sys_gb_cd, rt_tgt_typ_cd, rt_tgt_nm, sort_seq, call_url, use_yn, cust_info_incl_yn, rmk_cont, popup_yn, btn_id, upr_rt_tgt_seq, leaf_menu_yn, sys_reg_id, sys_reg_dtm, sys_mod_id, sys_mod_dtm)
VALUES('1000001', '11', '20', '장바구니목록조회-group', 1, 'cart/list2', 'Y', 'N', NULL, 'N', NULL, '1000000', 'Y', 'yoonho', '2021-03-11 16:18:56.304', 'yoonho', '2021-03-11 16:18:56.304');

INSERT INTO public.st_rt_tgt_base
(rt_tgt_seq, sys_gb_cd, rt_tgt_typ_cd, rt_tgt_nm, sort_seq, call_url, use_yn, cust_info_incl_yn, rmk_cont, popup_yn, btn_id, upr_rt_tgt_seq, leaf_menu_yn, sys_reg_id, sys_reg_dtm, sys_mod_id, sys_mod_dtm)
VALUES('1000002', '11', '20', '장바구니목록조회-group', 1, 'cart/list', 'Y', 'N', NULL, 'N', NULL, '1000000', 'Y', 'yoonho', '2021-03-11 16:18:56.304', 'yoonho', '2021-03-11 16:18:56.304');

select *
  from st_rt_tgt_base 
  ;

 
 
 select count(r.rt_grp_no) 
   from me_member m
      , st_rt_info r 
      , st_rt_tgt_base tb 
  where m.rt_grp_no = r.rt_grp_no 
    and r.rt_tgt_seq = tb.rt_tgt_seq
    and m.user_id = 'hosu1014'
    and r.use_yn = 'Y'
    and tb.call_url = '/cart/list2'
;    
 