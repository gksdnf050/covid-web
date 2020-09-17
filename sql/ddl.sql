CREATE TABLE hospital(
	id int AUTO_INCREMENT PRIMARY KEY,
	hospital varchar(50) NOT NULL COMMENT '기관명',
    sido varchar(30) DEFAULT NULL COMMENT '시도명',
    sggu varchar(30) DEFAULT NULL COMMENT '시군구명',
    selection_type varchar(10) DEFAULT NULL COMMENT '선정유형',
    tel varchar(20) DEFAULT NULL COMMENT '전화번호',
    operable_date datetime DEFAULT NULL COMMENT '운영가능일자',
    type_code varchar(10) DEFAULT NULL COMMENT '구분코드',
    x varchar(20) COMMENT 'x 좌표',
    y varchar(20) COMMENT 'y 좌표'
)ENGINE = InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE restaurant(
	id int AUTO_INCREMENT PRIMARY KEY,
	restaurant varchar(50) COMMENT '사업자명',
    representative varchar(30) COMMENT '대표자명',
	zipcode varchar(20) COMMENT '시도코드', 
	sido varchar(30) COMMENT '시도명',
	sggu varchar(30) COMMENT '시군구명',
	category varchar(20) COMMENT '업종',
	category_detail varchar(20) COMMENT '업종 상세',
	tel varchar(20) COMMENT '전화번호',
	etc text COMMENT '비고',
	selected varchar(5) COMMENT '선정여부',
	reg_date datetime COMMENT '안심식당 지정일',
	cancel_date datetime COMMENT '안심식당취소일',
	update_date datetime COMMENT '수정일',
	seq int COMMENT '안심식당 seq',
    add1 varchar(80) COMMENT '주소1',
    add2 varchar(80) COMMENT '주소2',
    x varchar(20) COMMENT 'x 좌표',
    y varchar(20) COMMENT 'y 좌표'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `domestic` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 database에 삽입한 시간',
    
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
	insert_date datetime NOT NULL COMMENT '해당 row를 database에 삽입한 시간',
    
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


CREATE TABLE `age` (
	id int NOT NULL AUTO_INCREMENT, 
	insert_date datetime NOT NULL COMMENT '해당 row를 database에 삽입한 시간',
    
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
	insert_date datetime NOT NULL COMMENT '해당 row를 database에 삽입한 시간',
    
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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `name` VARCHAR(255) NOT NULL COMMENT 'member name',
  `password` VARCHAR(255) NOT NULL COMMENT '암호회된 password',
  `email` VARCHAR(255) NOT NULL UNIQUE COMMENT 'login id, email',
  `create_date` DATETIME NULL DEFAULT NULL COMMENT '등록일',
  `modify_date` DATETIME NULL DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  
  CREATE TABLE `user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `user_id` INT(11) NOT NULL COMMENT 'user id fk',
  `role_name` VARCHAR(100) NOT NULL COMMENT 'role 이름 ROLE_ 로 시작하는 값이어야 한다.',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8