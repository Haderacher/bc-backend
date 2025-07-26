insert into students(username, password, email, full_name, phone_number, university, major,
                     degree, graduation_year, self_introduction, created_at, updated_at)
    VALUE ('user123', '{bcrypt}$2a$10$sA4LHZYVWT0ZnsyrJ2QEl.4KIcvBmQup2PKa0M.PAC1E5SQH1PbRK'
          , '123@gmail.com', '李四', '12312341234', '杭州电子科技大学', '计算机科学与技术',
           '本科', 2027, '我擅长前端开发，熟悉html，css和js', NOW(), NOW());