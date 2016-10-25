-- sequence

drop sequence user_seq;

create sequence user_seq
start with 1
increment by 1
maxvalue 9999999999;

drop sequence guestbook_seq;
create sequence guestbook_seq
start with 1
increment by 1
maxvalue 9999999999;

drop sequence board_seq;
create sequence board_seq
start with 1
increment by 1
maxvalue 9999999999;

commit;
--guestbook 
insert 
into guestbook
values(guestbook_seq.nextval,'admin',sysdate,'ad','냉무');
select * from guestbook;

-- users

select * from USERS u;

-- insert
insert into users
values( user_seq.nextval, '안대혁', 'kickscar@gmail.com', '1234', 'male');

-- delete
delete from users;

commit;

-- select ( login )
select no, name 
  from users
 where email='kickscar@gmail.com'
   and password='1234';

-- board

-- view
select no,title, content from board where no =2;
-- 조회수 업뎃!!
update board set hit=hit+1 where no =2;

-- list
-- page 넘기는데 필요한 값.
-- for each문을 탈건데,  <12344>에서 화살표의 표시 여부 -> 처음인지 끝인지 값 받기.
-- last page :  ceil (전체 count/page size)을 세는 
select count(*) from board;
-- cureent page:
-- start page: ceil(현재 page / page size ) -1 ) *5 +1 ;
-- 	if( stat page > 6 이면 <(prvpage = start page -1)을 표시)
-- end page: start page+ 4;P
-- if( end page < last page ) 


select *
from
(select no, title, hit, reg_date, depth, name, users_no, rownum as rn 
   from(  select a.no,
                 a.title, 
	             a.hit, 
	             to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date, 
  	             b.name,
				 a.users_no,
				 a.depth 
            from board a, users b
           where a.users_no = b.no
      --     and title like '%kwd%' or content like '%kwd%'
        order by group_no desc, order_no asc ))
where (1-1)*5+1 <= rn  -- current page:1, page_size:2
  and rn <= 1*5;    
  
select *
from(
select rownum as rn , no, title, hit, reg_date, uno, uname
from( select b.no, b.title as title , b.hit as hit, to_char(b.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date, b.USERS_NO as uno, u.name as uname 
			from BOARD b, USERS u where b.USERS_NO = u.NO -- and title like '%kwd%' or content like '%kwd%' -- search
			order by b.GROUP_NO desc, b.ORDERNO asc))
where (1-1)*5+1<=rn and rn<=(1*5) ; -- page size 가 5인 경우.

select no,title,hit,reg_date,name, depth, user_no  
from ( select rownum as rn, no, title, hit, reg_date, name, depth, user_no 
 from ( select  a.no, a.title as title , a.hit as hit , to_char(a.REG_DATE,'yyyy-mm-dd hh:mi:ss') as reg_date, a.USERS_NO as user_no,a.DEPTH as depth , b.NAME as name 
	from board a, users b 
	where a.users_no = b.no
	order by a.group_no desc, a.ORDERNO asc))
	 where (1-1)*5+1 <= rn and rn <=1*5;
  
-- insert (새글)
insert
  into board
values( board_seq.nextval, '배고프다 그만!','냉무', sysdate, 0, nvl((select max(group_no) from board),0) + 1,	 1, 0, 2 ); 

insert 
into BOARD  
values( board_seq.nextval, '안녕', '안녕',sysdate, 0,nvl((select max(group_no) from board),0)+1, 1, 0, 7); 
insert 
into BOARD  
values( board_seq.nextval, '점심 뭐먹지', '아무것도 먹지마',sysdate, 0,nvl((select max(group_no) from board),0)+1, 1, 0, 7); 


-- insert2 (답글)
select max(group_no) from board;

update board
   set order_no = order_no + 1
 where group_no = 2  -- 부모 글 그룹
   and order_no > 1; -- 부모 글 순서
    
insert
  into board
values( board_seq.nextval, 
        '짬뽕','냉무', sysdate, 0, 
		2, -- 부모글의 그룹 
		2, -- 부모글 순서  + 1
		1, -- 부모글 깊이  + 1 
		2 ); 

insert into BOARD  values( board_seq.nextval, '나는 짜장면 싫어', '냉무',sysdate, 0,
2, --부모글의 그룹
2, --order: 부모글 순서(1)+1
2, -- depth : 부모글 깊이(0) +1
7); 

commit;

--삭제

 select b.no, b.title, b.hit, to_char(b.reg_date, 'yyyy-mm-dd hh:mi:ss'), u.name 
			from BOARD b, USERS u 
			where b.USERS_NO = u.NO  -- and title like '%kwd%' or content like '%kwd%' -- search
			order by b.GROUP_NO desc, b.ORDERNO asc;
			
select * from BOARD b;

commit;

-- List_Size : 5

-- PAGE_Size : page당 보여줄 내용의 갯수 : 10
-- a=list&p=5;
-- a=list&p=11;


