CREATE TABLE hospital(
	id int AUTO_INCREMENT PRIMARY KEY,
    insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
	yadm_nm varchar(50) NOT NULL COMMENT '기관명',
    sido_nm varchar(30) DEFAULT NULL COMMENT '시도명',
    sggu_nm varchar(30) DEFAULT NULL COMMENT '시군구명',
    hosp_ty_tp_Cd varchar(10) DEFAULT NULL COMMENT '선정유형',
    tel_no varchar(20) DEFAULT NULL COMMENT '전화번호',
    adt_fr_dd datetime DEFAULT NULL COMMENT '운영가능일자',
    spcl_adm_ty_cd varchar(10) DEFAULT NULL COMMENT '구분코드',
    x varchar(20) COMMENT 'x 좌표',
    y varchar(20) COMMENT 'y 좌표'
)ENGINE = InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE restaurant(
	id int AUTO_INCREMENT PRIMARY KEY,
    insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
	relax_rstrnt_nm varchar(50) COMMENT '사업자명',
    relax_rstrnt_represent varchar(30) COMMENT '대표자명',
	relax_zipcode varchar(20) COMMENT '시도코드', 
	relax_si_nm varchar(30) COMMENT '시도명',
	relax_sido_nm varchar(30) COMMENT '시군구명',
	relax_gubun varchar(20) COMMENT '업종',
	relax_gubun_detail varchar(20) COMMENT '업종 상세',
	relax_rstrnt_tel varchar(20) COMMENT '전화번호',
	relax_rstrnt_etc text COMMENT '비고',
	relax_use_yn varchar(5) COMMENT '선정여부',
	relax_rstrnt_reg_dt datetime COMMENT '안심식당 지정일',
	relax_rstrnt_cncl_dt datetime COMMENT '안심식당 취소일',
	relax_update_dt datetime COMMENT '수정일',
	relax_seq int COMMENT '안심식당 seq',
    relax_add1 varchar(80) COMMENT '주소1',
    relax_add2 varchar(80) COMMENT '주소2',
    x varchar(20) COMMENT 'x 좌표',
    y varchar(20) COMMENT 'y 좌표'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `domestic` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
    
    seq int NOT NULL COMMENT '게시글 번호(감염현황 고유값)',
    state_dt datetime NOT NULL COMMENT '기준일',
    state_time time NOT NULL COMMENT '기준 시간',
    decide_cnt int NOT NULL COMMENT '확진자 수',
    clear_cnt int NOT NULL COMMENT '격리 해제 수',
    exam_cnt int NOT NULL COMMENT '검사진행 수',
    death_cnt int NOT NULL COMMENT '사망자 수',
    care_cnt int NOT NULL COMMENT '치료중 환자 수',
    result_neg_cnt int NOT NULL COMMENT '결과 음성 수',
    acc_exam_cnt int NOT NULL COMMENT '누적 검사수',
    acc_exam_comp_cnt int NOT NULL COMMENT '누적 검사 완료 수',
    acc_def_rate double NOT NULL COMMENT '누적 확진율',
    create_dt datetime NOT NULL COMMENT '등록일시분초',
    update_dt datetime NULL COMMENT '수정일시분초',
    PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `city` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
    
	def_cnt int NOT NULL COMMENT '어떤 데이터인지 모름 (설명 없음)',
	local_occ_cnt int NOT NULL COMMENT '어떤 데이터인지 모름 (설명 없음)',
	over_flow_cnt int NOT NULL COMMENT '어떤 데이터인지 모름 (설명 없음)',
	isol_ing_cnt int NOT NULL COMMENT '어떤 데이터인지 모름 (설명 없음)',
    
	seq int NOT NULL COMMENT '게시글 번호(국내 시도별 발생현황 고유값)',
	death_cnt int NOT NULL COMMENT '사망자 수',
	gubun varchar(30) NOT NULL COMMENT '시도명(한글)',
    gubun_cn varchar(30) NOT NULL COMMENT '시도명(중국어)',
    gubun_en varchar(30) NOT NULL COMMENT '시도명(영어)',
	inc_dec int NOT NULL COMMENT '전일 대비 증감 수',
	isol_clear_cnt int NOT NULL COMMENT '격리 해제 수',
    qur_rate varchar(30) NOT NULL COMMENT '10만명당 발생률',
	create_dt datetime NOT NULL COMMENT '등록일시분초',
    std_day datetime NOT NULL COMMENT '기준 일시',
	update_dt datetime NULL COMMENT '수정일시분초',
    PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `gen_and_age` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
    
    gubun varchar(30) NOT NULL COMMENT '구분(성별, 연령별)',
	conf_case int NOT NULL COMMENT '확진자',
	conf_case_rate double NOT NULL COMMENT '확진률',
	death int NOT NULL COMMENT '사망자',
    death_rate double NOT NULL COMMENT '사망률',
    critical_rate double NOT NULL COMMENT '치명률',
    
	create_dt datetime NOT NULL COMMENT '등록일시분초',
	update_dt datetime NULL COMMENT '수정일시분초',
    PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `country` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 테이블에 삽입한 시간',
    
    seq int COMMENT '게시글 번호(국외 발생 현황 고유값)',
    area_nm varchar(50) COMMENT '지역명',
    area_nm_en varchar(50) COMMENT '지역명(영문)',
    area_nm_cn varchar(50) COMMENT '지역명(중문)',
    nation_nm varchar(50) COMMENT '국가명',
    nation_nm_en varchar(50) COMMENT '국가명(영문)',
    nation_nm_cn varchar(50) COMMENT '국가명(중문)',
    nat_def_cnt  int COMMENT '국가별 확진자 수',
    nat_death_cnt int COMMENT '국가별 사망자 수', 
    nat_death_rate double COMMENT '확진률 대비 사망률',
    
    std_day datetime COMMENT '기준 일시',
	create_dt datetime NOT NULL COMMENT '등록일시분초',
	update_dt datetime NULL COMMENT '수정일시분초',
    PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user` (
    id                      BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username                VARCHAR(255)        NOT NULL,
    password                VARCHAR(255)        NOT NULL,
    email                   VARCHAR(255)        NOT NULL,
    created_at              datetime            NOT NULL DEFAULT current_timestamp,
    updated_at              datetime            NOT NULL DEFAULT current_timestamp on update CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE Unique INDEX uidx_user_username ON user(username);