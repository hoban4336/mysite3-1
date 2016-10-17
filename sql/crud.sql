-- users

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
 
  

 