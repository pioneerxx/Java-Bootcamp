INSERT INTO Chat.Users(login, password) VALUES
    ('Ali', '3229882'),
    ('Timur', '14012002'),
    ('Aynur', '16041975'),
    ('Dias', '09072001'),
    ('Emil', '20032001');

INSERT INTO Chat.Chatrooms(name, owner) VALUES
    ('Нормальное название', 4),
    ('Делаем 3двьюер', 2),
    ('Др Али', 5),
    ('Атанасян геометрия 7 класс гдз', 1),
    ('Топ сикрет', 3);

INSERT INTO Chat.Messages(author, room, text) VALUES
    (1, 1, 'Го в лол'),
    (5, 3, 'Что дарить будем?'),
    (3, 4, 'Как решать 5 задание'),
    (2, 5, 'кавооо'),
    (2, 5, 'Кто-нибудь, ответьте....');