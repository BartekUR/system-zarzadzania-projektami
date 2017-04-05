CREATE DATABASE IF NOT EXISTS `szp`;
USE `szp`;

CREATE TABLE IF NOT EXISTS `pracownicy` ( `ID_Pracownik` int(11) NOT NULL, `Login` varchar(50) NOT NULL, `Haslo` varchar(50) NOT NULL, `Imie` text NOT NULL, `Nazwisko` text NOT NULL, `Stanowisko` varchar(50) NOT NULL, PRIMARY KEY (`ID_Pracownik`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `projekty` ( `ID_Projekt` int(11) NOT NULL, `Nazwa_projektu` varchar(50) NOT NULL, `Head` text NOT NULL, `Status` varchar(50) NOT NULL, `Progress` varchar(50) NOT NULL, `Termin` date NOT NULL, PRIMARY KEY (`ID_Projekt`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `pracownicy_i_projekty` ( `ID_Pracownik_FK` int(11) DEFAULT NULL, `ID_Projekt_FK` int(11) DEFAULT NULL, KEY `ID_Pracownik_FKpp` (`ID_Pracownik_FK`), KEY `ID_Projekt_FKpp` (`ID_Projekt_FK`), CONSTRAINT `ID_Pracownik_FKpp` FOREIGN KEY (`ID_Pracownik_FK`) REFERENCES `pracownicy` (`ID_Pracownik`) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT `ID_Projekt_FKpp` FOREIGN KEY (`ID_Projekt_FK`) REFERENCES `projekty` (`ID_Projekt`) ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `taski` ( `ID_Task` int(11) NOT NULL, `ID_Projekt_FK` int(11) NOT NULL, `Nazwa_tasku` varchar(50) NOT NULL, `Status` varchar(50) NOT NULL, `Progress` varchar(50) NOT NULL, `Termin` date NOT NULL, PRIMARY KEY (`ID_Task`), KEY `ID_Projekt_FK` (`ID_Projekt_FK`), CONSTRAINT `ID_Projekt_FK` FOREIGN KEY (`ID_Projekt_FK`) REFERENCES `projekty` (`ID_Projekt`) ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `pracownicy_i_taski` ( `ID_Pracownik_FK` int(11) DEFAULT NULL, `ID_Taski_FK` int(11) DEFAULT NULL, KEY `ID_Pracownik_FKpt` (`ID_Pracownik_FK`), KEY `ID_Taski_FK2pt` (`ID_Taski_FK`), CONSTRAINT `ID_Pracownik_FKpt` FOREIGN KEY (`ID_Pracownik_FK`) REFERENCES `pracownicy` (`ID_Pracownik`) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT `ID_Taski_FK2pt` FOREIGN KEY (`ID_Taski_FK`) REFERENCES `taski` (`ID_Task`) ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=latin1;
