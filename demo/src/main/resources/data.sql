insert into course(id, name, created_date, last_updated_date, is_deleted) values (10001, 'TestCourse1', sysdate(), sysdate(), false);
insert into course(id, name, created_date, last_updated_date, is_deleted) values (10002, 'TestCourse2', sysdate(), sysdate(), false);
insert into course(id, name, created_date, last_updated_date, is_deleted) values (10003, 'TestCourse3', sysdate(), sysdate(), false);

insert into passport(id, number) values (40001, '111');
insert into passport(id, number) values (40002, '222');
insert into passport(id, number) values (40003, '333');

insert into student(id, name, passport_id) values (20001, 'Student1', '40001');
insert into student(id, name, passport_id) values (20002, 'Student2', '40002');
insert into student(id, name, passport_id) values (20003, 'Student3', '40003');

insert into review(id, rating, description, course_id) values (50001, 'ONE', 'Description1', 10001);
insert into review(id, rating, description, course_id) values (50002, 'TWO', 'Description2', 10001);
insert into review(id, rating, description, course_id) values (50003, 'THREE', 'Description3', 10003);

insert into student_course(student_id, course_id) values(20001, 10001);
insert into student_course(student_id, course_id) values(20002, 10001);
insert into student_course(student_id, course_id) values(20003, 10001);
insert into student_course(student_id, course_id) values(20001, 10003);
