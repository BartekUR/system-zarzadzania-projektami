USE `szp`;

INSERT INTO `pracownicy` (`ID_Pracownik`, `Login`, `Haslo`, `Imie`, `Nazwisko`, `Stanowisko`) VALUES (1, 'NowakJan', 'nowak1', 'Jan', 'Nowak', 'Szef'), (2, 'GlikKamil', 'glik2', 'Kamil', 'Glik', 'Head'), (3, 'KowalJacek', 'kowal3', 'Jacek', 'Kowal', 'Head'), (4, 'PazdanMichał', 'pazdan4', 'Michał', 'Pazdan', 'Head'), (5, 'PiszczekŁukasz', 'piszczek5', 'Łukasz', 'Piszczek', 'Head'), (6, 'WasilewskiMichał', 'wasilewski6', 'Michał', 'Wasilewski', 'Head'), (7, 'AdamekPaulina', 'adamek7', 'Paulina', 'Adamek', 'Pracownik'), (8, 'AdamskiKamil', 'adamski8', 'Kamil', 'Adamski', 'Pracownik'), (9, 'BatkoMarcin', 'batko9', 'Marcin', 'Batko', 'Pracownik'), (10, 'BeresteckiPaweł', 'berestecki10', 'Paweł', 'Berestecki', 'Pracownik'), (11, 'BidasAnna', 'bidas11', 'Anna', 'Bidas', 'Pracownik'), (12, 'BlujPatryk', 'bluj12', 'Patryk', 'Bluj', 'Pracownik'), (13, 'BrzyskiMateusz', 'brzyski13', 'Mateusz', 'Brzyski', 'Pracownik'), (14, 'BudaRobert', 'buda14', 'Robert', 'Buda', 'Pracownik'), (15, 'CybulskiMarcin', 'cybulski15', 'Marcin', 'Cybulski', 'Pracownik'), (16, 'DastykPrzemysław', 'dastyk16', 'Przemysław', 'Dastyk', 'Pracownik'), (17, 'FedunPatryk', 'fedun17', 'Patryk', 'Fedun', 'Pracownik'), (18, 'FilipekAnna', 'filipek18', 'Anna', 'Filipek', 'Pracownik'), (19, 'GalantyPatryk', 'galanty19', 'Patryk', 'Galanty', 'Pracownik'), (20, 'GazJacek', 'gaz20', 'Jacek', 'Gaz', 'Pracownik'), (21, 'GawełKamil', 'gaweł21', 'Kamil', 'Gaweł', 'Pracownik'), (22, 'GrosickaPatrycja', 'grosicka22', 'Patrycja', 'Grosicka', 'Pracownik'), (23, 'GuziorMaria', 'guzior23', 'Maria', 'Guzior', 'Pracownik'), (24, 'HancarzMonika', 'hancarz24', 'Monika', 'Hancarz', 'Pracownik'), (25, 'HaszDawid', 'hasz25', 'Dawid', 'Hasz', 'Pracownik'), (26, 'JanczykJakub', 'janczyk26', 'Jakub', 'Janczyk', 'Pracownik'), (27, 'JasekMaria', 'jasek27', 'Maria', 'Jasek', 'Pracownik'), (28, 'KaletaKinga', 'kaleta28', 'Kinga', 'Kaleta', 'Pracownik'), (29, 'KarasŁukasz', 'karas29', 'Łukasz', 'Karas', 'Pracownik'), (30, 'KijankaPatrycja', 'kijanka30', 'Patrycja', 'Kijanka', 'Pracownik'), (31, 'KonefałJadwiga', 'konefał31', 'Jadwiga', 'Konefał', 'Pracownik');

INSERT INTO `projekty` (`ID_Projekt`, `Nazwa_projektu`, `Head`, `Status`, `Progress`, `Termin`) VALUES (1, 'Aplikacja Android', 'Kamil Glik', 'Gotowy', '100%', '2017-05-01'), (2, 'Strona Internetowa', 'Jacek Kowal', 'W trakcie', '40%', '2017-04-24'), (3, 'Sklep Internetowy', 'Michał Pazdan', 'Testowanie', '90%', '2017-04-03'), (4, 'Aplikacja Internetowa', 'Łukasz Piszczek', 'W trakcie', '60%', '2017-05-08'), (5, 'System CRM', 'Michał Wasilprojektyewski', 'Gotowy', '100%', '2017-04-05');

INSERT INTO `pracownicy_i_projekty` (`ID_Pracownik_FK`, `ID_Projekt_FK`) VALUES (7, 1), (8, 1), (9, 1), (10, 1), (11, 1), (22, 2), (23, 2), (24, 2), (25, 2), (26, 2), (12, 3), (27, 4), (28, 4), (29, 4), (30, 4), (31, 4), (17, 5), (18, 5), (19, 5), (20, 5), (21, 5);

INSERT INTO `taski` (`ID_Task`, `ID_Projekt_FK`, `Nazwa_tasku`, `Status`, `Progress`, `Termin`) VALUES (1, 1, 'Projektowanie pod dotyk', 'Gotowy', '100%', '2017-03-14'), (2, 1, 'Interakcje i animacje', 'Gotowy', '100%', '2017-03-27'), (3, 1, 'GUI', 'W trakcie', '60%', '2017-04-14'), (4, 1, 'JAVA', 'Opozniony', '40%', '2017-03-29'), (5, 1, 'Testowanie', 'Oczekuje', '0%', '2017-04-30'), (6, 2, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'), (7, 2, 'Kodowanie HTML5', 'Opozniony', '20%', '2017-03-29'), (8, 2, 'Kodowanie PHP', 'Gotowy', '100%', '2017-04-05'), (9, 2, 'Arkusze stylów CSS', 'W trakcie', '70%', '2017-04-14'), (10, 2, 'Testowanie', 'Oczekuje', '0%', '2017-04-20'), (11, 3, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-10'), (12, 3, 'Kodowanie HTML5', 'Gotowy', '100%', '2017-03-14'), (13, 3, 'Kodowanie PHP', 'Gotowy', '100%', '2017-03-16'), (14, 3, 'Funkcjonalność sklepu', 'W trakcie', '50%', '2017-04-08'), (15, 3, 'Testowanie', 'Oczekuje', '0%', '2017-04-30'), (16, 4, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'), (17, 4, 'Kodowanie JAVA', 'Opozniony', '40%', '2017-03-27'), (18, 4, 'Arkusze stylow JavaFX', 'W trakcie', '60%', '2017-04-03'), (19, 4, 'Kodowanie HTML5', 'W trakcie', '40%', '2017-04-06'), (20, 4, 'Testowanie', 'Oczekuje', '0%', '2017-04-20'), (21, 5, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'), (22, 5, 'Baza danych', 'Gotowy', '100%', '2017-03-28'), (23, 5, 'GUI', 'Gotowy', '100%', '2017-03-28'), (24, 5, 'JAVA', 'Gotowy', '100%', '2017-03-28'), (25, 5, 'Testowanie', 'Gotowy', '100%', '2017-04-01');

INSERT INTO `pracownicy_i_taski` (`ID_Pracownik_FK`, `ID_Taski_FK`) VALUES (7, 1), (8, 2), (9, 3), (10, 4), (11, 5), (22, 6), (23, 7), (24, 8), (25, 9), (26, 10), (12, 11), (13, 12), (14, 13), (15, 14), (16, 15), (27, 16), (28, 17), (29, 18), (30, 19), (31, 20), (17, 21), (18, 22), (19, 23), (20, 24), (21, 25);


/*
INSERT INTO `pracownicy` (`ID_Pracownik`, `Login`, `Haslo`, `Imie`, `Nazwisko`, `Stanowisko`) VALUES
(null,'NowakJan', 'nowak1', 'Jan', 'Nowak', 'Szef'),
(null,'GlikKamil', 'glik2', 'Kamil', 'Glik', 'Head'),
(null,'KowalJacek', 'kowal3', 'Jacek', 'Kowal', 'Head'),
(null,'PazdanMichał', 'pazdan4', 'Michał', 'Pazdan', 'Head'),
(null,'PiszczekŁukasz', 'piszczek5', 'Łukasz', 'Piszczek', 'Head'),
(null,'WasilewskiMichał', 'wasilewski6', 'Michał', 'Wasilewski', 'Head'),
(null,'AdamekPaulina', 'adamek7', 'Paulina', 'Adamek', 'Pracownik'),
(null,'AdamskiKamil', 'adamski8', 'Kamil', 'Adamski', 'Pracownik'),
(null,'BatkoMarcin', 'batko9', 'Marcin', 'Batko', 'Pracownik'),
(null,'BeresteckiPaweł', 'berestecki10', 'Paweł', 'Berestecki', 'Pracownik'),
(null,'BidasAnna', 'bidas11', 'Anna', 'Bidas', 'Pracownik'),
(null,'BlujPatryk', 'bluj12', 'Patryk', 'Bluj', 'Pracownik'),
(null,'BrzyskiMateusz', 'brzyski13', 'Mateusz', 'Brzyski', 'Pracownik'),
(null,'BudaRobert', 'buda14', 'Robert', 'Buda', 'Pracownik'),
(null,'CybulskiMarcin', 'cybulski15', 'Marcin', 'Cybulski', 'Pracownik'),
(null,'DastykPrzemysław', 'dastyk16', 'Przemysław', 'Dastyk', 'Pracownik'),
(null,'FedunPatryk', 'fedun17', 'Patryk', 'Fedun', 'Pracownik'),
(null,'FilipekAnna', 'filipek18', 'Anna', 'Filipek', 'Pracownik'),
(null,'GalantyPatryk', 'galanty19', 'Patryk', 'Galanty', 'Pracownik'),
(null,'GazJacek', 'gaz20', 'Jacek', 'Gaz', 'Pracownik'),
(null,'GawełKamil', 'gaweł21', 'Kamil', 'Gaweł', 'Pracownik'),
(null,'GrosickaPatrycja', 'grosicka22', 'Patrycja', 'Grosicka', 'Pracownik'),
(null,'GuziorMaria', 'guzior23', 'Maria', 'Guzior', 'Pracownik'),
(null,'HancarzMonika', 'hancarz24', 'Monika', 'Hancarz', 'Pracownik'),
(null,'HaszDawid', 'hasz25', 'Dawid', 'Hasz', 'Pracownik'),
(null,'JanczykJakub', 'janczyk26', 'Jakub', 'Janczyk', 'Pracownik'),
(null,'JasekMaria', 'jasek27', 'Maria', 'Jasek', 'Pracownik'),
(null,'KaletaKinga', 'kaleta28', 'Kinga', 'Kaleta', 'Pracownik'),
(null,'KarasŁukasz', 'karas29', 'Łukasz', 'Karas', 'Pracownik'),
(null,'KijankaPatrycja', 'kijanka30', 'Patrycja', 'Kijanka', 'Pracownik'),
(null,'KonefałJadwiga', 'konefał31', 'Jadwiga', 'Konefał', 'Pracownik');

INSERT INTO `projekty` (`ID_Projekt`, `Nazwa_projektu`, `Head`, `Status`, `Progress`, `Termin`) VALUES
(null,'Aplikacja Android', 'Kamil Glik', 'Gotowy', '100%', '2017-05-01'),
(null,'Strona Internetowa', 'Jacek Kowal', 'W trakcie', '40%', '2017-04-24'),
(null,'Sklep Internetowy', 'Michał Pazdan', 'Testowanie', '90%', '2017-04-03'),
(null,'Aplikacja Internetowa', 'Łukasz Piszczek', 'W trakcie', '60%', '2017-05-08'),
(null,'System CRM', 'Michał Wasilprojektyewski', 'Gotowy', '100%', '2017-04-05');

INSERT INTO `pracownicy_i_projekty` (`ID_Pracownik_FK`, `ID_Projekt_FK`) VALUES (7, 1), (8, 1), (9, 1), (10, 1), (11, 1), (22, 2), (23, 2), (24, 2), (25, 2), (26, 2), (12, 3), (27, 4), (28, 4), (29, 4), (30, 4), (31, 4), (17, 5), (18, 5), (19, 5), (20, 5), (21, 5);

INSERT INTO `taski` (`ID_Task`, `ID_Projekt_FK`, `Nazwa_tasku`, `Status`, `Progress`, `Termin`) VALUES (null, 1, 'Projektowanie pod dotyk', 'Gotowy', '100%', '2017-03-14'),
(null, 1, 'Interakcje i animacje', 'Gotowy', '100%', '2017-03-27'),
(null, 1, 'GUI', 'W trakcie', '60%', '2017-04-14'),
(null,  1, 'JAVA', 'Opozniony', '40%', '2017-03-29'),
(null, 1, 'Testowanie', 'Oczekuje', '0%', '2017-04-30'),
(null,  2, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'),
(null, 2, 'Kodowanie HTML5', 'Opozniony', '20%', '2017-03-29'),
(null, 2, 'Kodowanie PHP', 'Gotowy', '100%', '2017-04-05'),
(null, 2, 'Arkusze stylów CSS', 'W trakcie', '70%', '2017-04-14'),
(null,  2, 'Testowanie', 'Oczekuje', '0%', '2017-04-20'),
(null,  3, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-10'),
(null,  3, 'Kodowanie HTML5', 'Gotowy', '100%', '2017-03-14'),
(null,  3, 'Kodowanie PHP', 'Gotowy', '100%', '2017-03-16'),
(null,  3, 'Funkcjonalność sklepu', 'W trakcie', '50%', '2017-04-08'),
(null,  3, 'Testowanie', 'Oczekuje', '0%', '2017-04-30'),
(null,  4, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'),
(null, 4, 'Kodowanie JAVA', 'Opozniony', '40%', '2017-03-27'),
(null,  4, 'Arkusze stylow JavaFX', 'W trakcie', '60%', '2017-04-03'),
(null,  4, 'Kodowanie HTML5', 'W trakcie', '40%', '2017-04-06'),
(null,  4, 'Testowanie', 'Oczekuje', '0%', '2017-04-20'),
(null,  5, 'Projekt graficzny', 'Gotowy', '100%', '2017-03-20'),
(null,  5, 'Baza danych', 'Gotowy', '100%', '2017-03-28'),
(null,  5, 'GUI', 'Gotowy', '100%', '2017-03-28'),
(null,  5, 'JAVA', 'Gotowy', '100%', '2017-03-28'),
(null, 5, 'Testowanie', 'Gotowy', '100%', '2017-04-01');

INSERT INTO `pracownicy_i_taski` (`ID_Pracownik_FK`, `ID_Taski_FK`) VALUES (7, 1), (8, 2), (9, 3), (10, 4), (11, 5), (22, 6), (23, 7), (24, 8), (25, 9), (26, 10), (12, 11), (13, 12), (14, 13), (15, 14), (16, 15), (27, 16), (28, 17), (29, 18), (30, 19), (31, 20), (17, 21), (18, 22), (19, 23), (20, 24), (21, 25);

 */