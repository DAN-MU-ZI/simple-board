INSERT INTO user (id, email)
VALUES (1, 'testuser@example.com');

INSERT INTO post (id, title, content, user_id)
VALUES (1, 'title1', 'content1', 1),
       (2, 'title2', 'content2', 1),
       (3, 'title3', 'content3', 1),
       (4, 'title4', 'content4', 1),
       (5, 'title5', 'content5', 1),
       (6, 'title6', 'content6', 1),
       (7, 'title7', 'content7', 1),
       (8, 'title8', 'content8', 1),
       (9, 'title9', 'content9', 1),
       (10, 'title10', 'content10', 1);

insert into comment (id, content, user_id, post_id)
values (1, 'comment1', 1, 1),
       (2, 'comment2', 1, 1),
       (3, 'comment3', 1, 1),
       (4, 'comment4', 1, 1),
       (5, 'comment5', 1, 1),
       (6, 'comment6', 1, 1),
       (7, 'comment7', 1, 1),
       (8, 'comment8', 1, 1),
       (9, 'comment9', 1, 1),
       (10, 'comment10', 1, 1);
