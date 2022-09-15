select student.name, student.age, faculty.name from student inner join faculty on faculty.id = student.faculty_id;
select student.name, student.age, faculty.name, avatar.file_path, avatar.file_size from student
         inner join faculty on faculty.id = student.faculty_id
         inner join avatar on student.id = avatar.student_id;