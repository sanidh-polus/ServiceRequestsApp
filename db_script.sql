-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema service_request
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema service_request
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `service_request` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `service_request` ;

-- -----------------------------------------------------
-- Table `service_request`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`country` (
  `COUNTRY_CODE` VARCHAR(50) NOT NULL,
  `COUNTRY_NAME` VARCHAR(50) NULL DEFAULT NULL,
  `CURRENCY_CODE` VARCHAR(10) NULL DEFAULT NULL,
  `UPDATE_TIMESTAMP` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_USER` VARCHAR(50) NULL DEFAULT NULL,
  `COUNTRY_CODE_ISO2` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`COUNTRY_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`person` (
  `PERSON_ID` INT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(30) NOT NULL,
  `LAST_NAME` VARCHAR(30) NOT NULL,
  `DESIGNATION` VARCHAR(30) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(40) NULL DEFAULT NULL,
  `PASSWORD` VARCHAR(30) NULL DEFAULT NULL,
  `CREATED_AT` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `COUNTRY_CODE` VARCHAR(20) NULL DEFAULT NULL,
  `STATE` VARCHAR(20) NULL DEFAULT NULL,
  `ADDRESS` VARCHAR(100) NULL DEFAULT NULL,
  `PHONE_NO` VARCHAR(15) NULL DEFAULT NULL,
  `FULL_NAME` VARCHAR(100) NULL DEFAULT NULL,
  `UPDATED_TIME` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`PERSON_ID`),
  UNIQUE INDEX `EMAIL` (`EMAIL` ASC) VISIBLE,
  INDEX `COUNTRY_CODE` (`COUNTRY_CODE` ASC) VISIBLE,
  CONSTRAINT `person_ibfk_1`
    FOREIGN KEY (`COUNTRY_CODE`)
    REFERENCES `service_request`.`country` (`COUNTRY_CODE`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`sr_ticket_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`sr_ticket_category` (
  `CATEGORY_ID` INT NOT NULL AUTO_INCREMENT,
  `CATEGORY_NAME` VARCHAR(100) NULL DEFAULT NULL,
  `CATEGORY_DESCRIPTION` VARCHAR(255) NULL DEFAULT NULL,
  `CATEGORY_CREATED_AT` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `CATEGORY_CREATED_BY` INT NULL DEFAULT NULL,
  PRIMARY KEY (`CATEGORY_ID`),
  INDEX `CATEGORY_CREATED_BY` (`CATEGORY_CREATED_BY` ASC) VISIBLE,
  CONSTRAINT `sr_ticket_category_ibfk_1`
    FOREIGN KEY (`CATEGORY_CREATED_BY`)
    REFERENCES `service_request`.`person` (`PERSON_ID`),
  CONSTRAINT `sr_ticket_category_ibfk_2`
    FOREIGN KEY (`CATEGORY_CREATED_BY`)
    REFERENCES `service_request`.`person` (`PERSON_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`sr_ticket_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`sr_ticket_status` (
  `STATUS_ID` INT NOT NULL AUTO_INCREMENT,
  `STATUS_TYPE` VARCHAR(50) NULL DEFAULT NULL,
  `STATUS_DESCRIPTION` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`STATUS_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`sr_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`sr_ticket` (
  `TICKET_ID` INT NOT NULL AUTO_INCREMENT,
  `PERSON_ID` INT NULL DEFAULT NULL,
  `CATEGORY_ID` INT NULL DEFAULT NULL,
  `TICKET_DESCRIPTION` VARCHAR(255) NULL DEFAULT NULL,
  `STATUS_ID` INT NULL DEFAULT '1',
  `TICKET_ASSIGNED_TO` INT NULL DEFAULT NULL,
  `TICKET_CREATED_TIME` TIMESTAMP NULL DEFAULT NULL,
  `TICKET_UPDATED_AT` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TICKET_ID`),
  INDEX `PERSON_ID` (`PERSON_ID` ASC) VISIBLE,
  INDEX `CATEGORY_ID` (`CATEGORY_ID` ASC) VISIBLE,
  INDEX `STATUS_ID` (`STATUS_ID` ASC) VISIBLE,
  INDEX `TICKET_ASSIGNED_TO` (`TICKET_ASSIGNED_TO` ASC) VISIBLE,
  CONSTRAINT `sr_ticket_ibfk_1`
    FOREIGN KEY (`PERSON_ID`)
    REFERENCES `service_request`.`person` (`PERSON_ID`),
  CONSTRAINT `sr_ticket_ibfk_2`
    FOREIGN KEY (`CATEGORY_ID`)
    REFERENCES `service_request`.`sr_ticket_category` (`CATEGORY_ID`),
  CONSTRAINT `sr_ticket_ibfk_3`
    FOREIGN KEY (`STATUS_ID`)
    REFERENCES `service_request`.`sr_ticket_status` (`STATUS_ID`),
  CONSTRAINT `sr_ticket_ibfk_4`
    FOREIGN KEY (`TICKET_ASSIGNED_TO`)
    REFERENCES `service_request`.`person` (`PERSON_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 58
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`comments_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`comments_history` (
  `COMMENT_ID` INT NOT NULL AUTO_INCREMENT,
  `TICKET_ID` INT NULL DEFAULT NULL,
  `COMMENTS` VARCHAR(300) NULL DEFAULT NULL,
  `COMMENTED_BY` VARCHAR(50) NULL DEFAULT NULL,
  `COMMENTED_AT` TIMESTAMP NULL DEFAULT NULL,
  `STATUS_ID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`COMMENT_ID`),
  INDEX `TICKET_ID` (`TICKET_ID` ASC) VISIBLE,
  INDEX `STATUS_ID` (`STATUS_ID` ASC) VISIBLE,
  CONSTRAINT `comments_history_ibfk_1`
    FOREIGN KEY (`TICKET_ID`)
    REFERENCES `service_request`.`sr_ticket` (`TICKET_ID`),
  CONSTRAINT `comments_history_ibfk_2`
    FOREIGN KEY (`STATUS_ID`)
    REFERENCES `service_request`.`sr_ticket_status` (`STATUS_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`roles` (
  `ROLE_ID` INT NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` VARCHAR(40) NULL DEFAULT NULL,
  `ROLE_DESCRIPTION` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`person_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`person_roles` (
  `PERSON_ROLES_ID` INT NOT NULL AUTO_INCREMENT,
  `PERSON_ID` INT NULL DEFAULT NULL,
  `ROLE_ID` INT NULL DEFAULT '2',
  `UPDATED_TIME` TIMESTAMP NULL DEFAULT NULL,
  `UPDATED_BY` INT NULL DEFAULT NULL,
  PRIMARY KEY (`PERSON_ROLES_ID`),
  INDEX `ROLE_ID` (`ROLE_ID` ASC) VISIBLE,
  INDEX `PERSON_ID` (`PERSON_ID` ASC) VISIBLE,
  CONSTRAINT `person_roles_ibfk_1`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `service_request`.`roles` (`ROLE_ID`),
  CONSTRAINT `person_roles_ibfk_2`
    FOREIGN KEY (`PERSON_ID`)
    REFERENCES `service_request`.`person` (`PERSON_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `service_request`.`ticket_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service_request`.`ticket_history` (
  `TICKET_HISTORY_ID` INT NOT NULL AUTO_INCREMENT,
  `TICKET_ID` INT NULL DEFAULT NULL,
  `UPDATED_BY` VARCHAR(500) NULL DEFAULT NULL,
  `UPDATED_TIME` TIMESTAMP NULL DEFAULT NULL,
  `STATUS_ID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TICKET_HISTORY_ID`),
  INDEX `TICKET_ID` (`TICKET_ID` ASC) VISIBLE,
  INDEX `STATUS_ID` (`STATUS_ID` ASC) VISIBLE,
  CONSTRAINT `ticket_history_ibfk_1`
    FOREIGN KEY (`TICKET_ID`)
    REFERENCES `service_request`.`sr_ticket` (`TICKET_ID`),
  CONSTRAINT `ticket_history_ibfk_2`
    FOREIGN KEY (`STATUS_ID`)
    REFERENCES `service_request`.`sr_ticket_status` (`STATUS_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 72
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-------------------------------------------------------------------------------------------------------------------

INSERT INTO `country` VALUES ('AFG','Afghanistan','AFN','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AF'),('AGO','Angola','AOA','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AO'),('AIA','Anguilla','XCD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AI'),('ALB','Albania','ALL','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AL'),('AND','Andorra','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AD'),('ANT','Netherlands Antilles','ANG','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AN'),('ARE','United Arab Emirates','AED','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AE'),('ARG','Argentina','ARS','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AR'),('ARM','Armenia','AMD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AM'),('ASM','American Samoa','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AS'),('ATG','Antigua and Barbuda','XCD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AG'),('AUS','Australia','AUD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AU'),('AUT','Austria','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AT'),('AZE','Azerbaijan','AZN','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','AZ'),('BDI','Burundi','BIF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BI'),('BEL','Belgium','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BE'),('BEN','Benin','XOF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BJ'),('BFA','Burkina Faso','XOF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BF'),('BGD','Bangladesh','BDT','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BD'),('BGR','Bulgaria','BGN','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BG'),('BHR','Bahrain','BHD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BH'),('BHS','Bahamas','BSD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BS'),('BIH','Bosnia and Herzegovina','BAM','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BA'),('BLR','Belarus','BYR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BY'),('BLZ','Belize','BZD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BZ'),('BMU','Bermuda','BMD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BM'),('BOL','Bolivia','BOB','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BO'),('BRA','Brazil','BRL','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BR'),('BRB','Barbados','BBD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BB'),('BRN','Brunei Darussalam','BND','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BN'),('BTN','Bhutan','INR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BT'),('BWA','Botswana','BWP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','BW'),('CAF','Central African Republic','XAF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CF'),('CAN','Canada','CAD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CA'),('CHE','Switzerland','CHF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CH'),('CHL','Chile','CLP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CL'),('CHN','China','CNY','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CN'),('CIV','Cote d\'Ivoire','XOF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CI'),('CMR','Cameroon','XAF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CM'),('COD','Congo, the Democratic Republic of the','CDF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CD'),('COG','Congo','XAF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CG'),('COK','Cook Islands','NZD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CK'),('COL','Colombia','COP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CO'),('COM','Comoros','KMF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KM'),('CPV','Cape Verde','CVE','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CV'),('CRI','Costa Rica','CRC','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CR'),('CUB','Cuba','CUP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CU'),('CYM','Cayman Islands','KYD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KY'),('CYP','Cyprus','CYP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CY'),('CZE','Czech Republic','CZK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','CZ'),('DEU','Germany','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DE'),('DJI','Djibouti','DJF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DJ'),('DMA','Dominica','XCD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DM'),('DNK','Denmark','DKK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DK'),('DOM','Dominican Republic','DOP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DO'),('DZA','Algeria','DZD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','DZ'),('ECU','Ecuador','ECS','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','EC'),('EGY','Egypt','EGP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','EG'),('ERI','Eritrea','ETB','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','ER'),('ESH','Western Sahara','MAD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','EH'),('ESP','Spain','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','ES'),('EST','Estonia','EEK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','EE'),('ETH','Ethiopia','ETB','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','ET'),('FIN','Finland','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FI'),('FJI','Fiji','FJD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FJ'),('FLK','Falkland Islands (Malvinas)','FKP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FK'),('FRA','France','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FR'),('FRO','Faroe Islands','DKK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FO'),('FSM','Micronesia, Federated States of','USD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','FM'),('GAB','Gabon','XAF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GA'),('GBR','United Kingdom','GBP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GB'),('GEO','Georgia','GEL','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GE'),('GHA','Ghana','GHS','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GH'),('GIB','Gibraltar','GIP','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GI'),('GIN','Guinea','GNF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GN'),('GLP','Guadeloupe','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GP'),('GMB','Gambia','GMD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GM'),('GNB','Guinea-Bissau','XOF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GW'),('GNQ','Equatorial Guinea','XAF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GQ'),('GRC','Greece','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GR'),('GRD','Grenada','XCD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GD'),('GRL','Greenland','DKK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GL'),('GTM','Guatemala','GTQ','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GT'),('GUF','French Guiana','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GF'),('GUM','Guam','USD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GU'),('GUY','Guyana','GYD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','GY'),('HKG','Hong Kong','HKD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','HK'),('HND','Honduras','HNL','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','HN'),('HRV','Croatia','HRK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','HR'),('HTI','Haiti','HTG','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','HT'),('HUN','Hungary','HUF','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','HU'),('IDN','Indonesia','IDR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','ID'),('IND','India','INR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IN'),('IRL','Ireland','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IE'),('IRN','Iran, Islamic Republic of','IRR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IR'),('IRQ','Iraq','IQD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IQ'),('ISL','Iceland','ISK','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IS'),('ISR','Israel','ILS','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IL'),('ITA','Italy','EUR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','IT'),('JAM','Jamaica','JMD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','JM'),('JOR','Jordan','JOD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','JO'),('JPN','Japan','JPY','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','JP'),('KAZ','Kazakhstan','KZT','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KZ'),('KEN','Kenya','KES','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KE'),('KGZ','Kyrgyzstan','KGS','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KG'),('KHM','Cambodia','KHR','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KH'),('KIR','Kiribati','AUD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KI'),('KNA','Saint Kitts and Nevis','XCD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KN'),('KOR','Korea, Republic of','KRW','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KR'),('KWT','Kuwait','KWD','2024-07-11 09:26:38','APPLICATION_ADMINISTRATOR','KW'),('LAO','Lao People\'s Democratic Republic','LAK','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LA'),('LBN','Lebanon','LBP','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LB'),('LBR','Liberia','LRD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LR'),('LBY','Libyan Arab Jamahiriya','LYD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LY'),('LCA','Saint Lucia','XCD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LC'),('LIE','Liechtenstein','CHF','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LI'),('LKA','Sri Lanka','LKR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LK'),('LSO','Lesotho','LSL','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LS'),('LTU','Lithuania','LTL','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LT'),('LUX','Luxembourg','EUR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LU'),('LVA','Latvia','LVL','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','LV'),('MAC','Macao','MOP','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MO'),('MAR','Morocco','MAD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MA'),('MCO','Monaco','EUR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MC'),('MDA','Moldova, Republic of','MDL','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MD'),('MDG','Madagascar','MGA','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MG'),('MDV','Maldives','MVR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MV'),('MEX','Mexico','MXN','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MX'),('MHL','Marshall Islands','USD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MH'),('MKD','Macedonia, the Former Yugoslav Republic of','MKD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MK'),('MLI','Mali','XOF','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','ML'),('MLT','Malta','MTL','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MT'),('MMR','Myanmar','MMK','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MM'),('MNG','Mongolia','MNT','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MN'),('MNP','Northern Mariana Islands','USD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MP'),('MOZ','Mozambique','MZN','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MZ'),('MRT','Mauritania','MRO','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MR'),('MSR','Montserrat','XCD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MS'),('MTQ','Martinique','EUR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MQ'),('MUS','Mauritius','MUR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MU'),('MWI','Malawi','MWK','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MW'),('MYS','Malaysia','MYR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','MY'),('NAM','Namibia','NAD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NA'),('NCL','New Caledonia','XPF','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NC'),('NER','Niger','XOF','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NE'),('NFK','Norfolk Island','AUD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NF'),('NGA','Nigeria','NGN','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NG'),('NIC','Nicaragua','NIO','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NI'),('NIU','Niue','NZD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NU'),('NLD','Netherlands','EUR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NL'),('NOR','Norway','NOK','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NO'),('NPL','Nepal','NPR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NP'),('NRU','Nauru','AUD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NR'),('NZL','New Zealand','NZD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','NZ'),('OMN','Oman','OMR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','OM'),('PAK','Pakistan','PKR','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PK'),('PAN','Panama','PAB','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PA'),('PCN','Pitcairn','NZD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PN'),('PER','Peru','PEN','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PE'),('PHL','Philippines','PHP','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PH'),('PLW','Palau','USD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PW'),('PNG','Papua New Guinea','PGK','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PG'),('POL','Poland','PLN','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PL'),('PRI','Puerto Rico','USD','2024-07-11 09:27:52','APPLICATION_ADMINISTRATOR','PR'),('PRK','Korea, Democratic People\'s Republic of','KPW','2024-07-11 09:28:37','APPLICATION_ADMINISTRATOR','KP'),('PRT','Portugal','EUR','2024-07-11 09:28:37','APPLICATION_ADMINISTRATOR','PT'),('PRY','Paraguay','PYG','2024-07-11 09:28:37','APPLICATION_ADMINISTRATOR','PY'),('PYF','French Polynesia','XPF','2024-07-11 09:28:37','APPLICATION_ADMINISTRATOR','PF'),('QAT','Qatar','QAR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','QA'),('REU','Reunion','EUR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','RE'),('ROM','Romania','RON','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','RO'),('RUS','Russian Federation','RUB','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','RU'),('RWA','Rwanda','RWF','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','RW'),('SAU','Saudi Arabia','SAR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SA'),('SDN','Sudan','SDG','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SD'),('SEN','Senegal','XOF','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SN'),('SGP','Singapore','SGD','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SG'),('SHN','Saint Helena','GBP','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SH'),('SIN','Sint Maarten','EUR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SX'),('SJM','Svalbard and Jan Mayen','NOK','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SJ'),('SLB','Solomon Islands','SBD','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SB'),('SLE','Sierra Leone','SLL','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SL'),('SLV','El Salvador','SVC','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SV'),('SMR','San Marino','EUR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SM'),('SOM','Somalia','SOS','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','SO'),('SPM','Saint Pierre and Miquelon','EUR','2024-07-11 09:29:39','APPLICATION_ADMINISTRATOR','PM'),('SRB','Serbia','RSD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','RS'),('STP','Sao Tome and Principe','STD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','ST'),('SUR','Suriname','SRD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SR'),('SVK','Slovakia','SKK','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SK'),('SVN','Slovenia','EUR','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SI'),('SWE','Sweden','SEK','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SE'),('SWZ','Swaziland','SZL','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SZ'),('SYC','Seychelles','SCR','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SC'),('SYR','Syrian Arab Republic','SYP','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','SY'),('TCA','Turks and Caicos Islands','USD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TC'),('TCD','Chad','XAF','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TD'),('TGO','Togo','XOF','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TG'),('THA','Thailand','THB','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TH'),('TJK','Tajikistan','TJS','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TJ'),('TKL','Tokelau','NZD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TK'),('TKM','Turkmenistan','TMT','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TM'),('TON','Tonga','TOP','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TO'),('TTO','Trinidad and Tobago','TTD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TT'),('TUN','Tunisia','TND','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TN'),('TUR','Turkey','TRY','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TR'),('TUV','Tuvalu','AUD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TV'),('TWN','Taiwan, Province of China','TWD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TW'),('TZA','Tanzania, United Republic of','TZS','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','TZ'),('UGA','Uganda','UGX','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','UG'),('UKR','Ukraine','UAH','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','UA'),('URY','Uruguay','UYU','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','UY'),('USA','United States','USD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','US'),('UZB','Uzbekistan','UZS','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','UZ'),('VAT','Holy See (Vatican City State)','EUR','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VA'),('VCT','Saint Vincent and the Grenadines','XCD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VC'),('VEN','Venezuela','VEF','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VE'),('VGB','Virgin Islands, British','USD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VG'),('VIR','Virgin Islands, U.s.','USD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VI'),('VNM','Viet Nam','VND','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VN'),('VUT','Vanuatu','VUV','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','VU'),('WLF','Wallis and Futuna','XPF','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','WF'),('WSM','Samoa','EUR','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','WS'),('YEM','Yemen','YER','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','YE'),('ZAF','South Africa','ZAR','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','ZA'),('ZMB','Zambia','ZMK','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','ZM'),('ZWE','Zimbabwe','ZWD','2024-07-11 09:30:23','APPLICATION_ADMINISTRATOR','ZW');

INSERT INTO `person` VALUES (1,'Sasikumar','Chandran','Project Manager','sasikumarpolus@gmail.com','12345skc','2024-07-15 06:18:58','IND','Tamil Nadu','kanyakumari','24567178881',NULL,NULL),(2,'Arun','Raj','Project Manager','arunrajpolus@gmail.com','4567aj','2024-07-15 06:18:58','IND','Kerala','kollam','9687325141',NULL,NULL),(3,'John','Winsely','Project Manager','johnwinselypolus@gmail.com','8910jw','2024-07-15 06:18:58','IND','Kerala','Kochi','5672826216',NULL,NULL),(4,'Sheena','J','Software Engineer Trainee','sheenapolus@gmail.com','12345678','2024-07-15 06:39:31','IND','Kerala','1234 Trivandrum','9876543210',NULL,NULL),(5,'Adarsh','SM','Intern','adarsh@polus.com','12345678','2024-07-15 09:04:14','BHR','Kerala','1234 Trivandrum','1234567890',NULL,NULL),(6,'Prakash','Warrier','Software Engineer Trainee','prakash1@gmail.com','prakash1234','2024-07-16 06:14:08','IND','Kerala','trivandrum','8965975834',NULL,NULL),(7,'Manesh','M S','Software Engineer Trainee','maneshpolus@gmail.com','1234ms','2024-07-16 06:17:04','AFG','Kerala','trivandrum','23456781',NULL,NULL),(9,'Abin','Raj','Intern','abin@polus.com','12345678','2024-07-16 06:19:23','ISL','Kerala','1234 Trivandrum','9090909090',NULL,NULL),(10,'Jeni','Mathews','Software Engineer Trainee','jeni@gmail.com','jeni1234','2024-07-16 06:42:34','IND','Kerala','trivandrum','7892357899',NULL,NULL),(11,'Roy','James','Software Engineer','roy@gmail.com','9876544332','2024-07-22 06:44:16','IND','Kerala','Trivandrum','9876543210','RoyJames','2024-07-24 09:21:49'),(12,'Neeraja','Biju','Intern','neerajabiju@gmail.com','12345678','2024-07-23 10:10:22','IND','Kerala','1234 House, Alapuzha ','8012345678',NULL,NULL),(13,'Adarsh','SM','Intern','karthik@gmail.com','12345678','2024-07-24 10:53:34','BMU','Kerala','1234 Main St.','9191919191','AdarshSM','2024-07-24 10:53:34'),(14,'A','B','C','praveen@gmail.com','12345678','2024-07-25 05:59:10','AUT','Kerala','1234 Aust St','8989898989','A B','2024-07-25 05:59:10'),(15,'sanidh','M S','Software Engineer Trainee','snula@gmail.com','1234ms','2024-07-25 05:59:39','IND','Kerala','trivandrum','23456781','sanidh M S','2024-07-25 05:59:39'),(16,'a','b','c','hari@yahoo.com','12345678','2024-07-25 06:02:27','ARG','Kerala','1234 Argent St','8989898989','a b','2024-07-25 06:02:27');

INSERT INTO `person` VALUES (1,'Sasikumar','Chandran','Project Manager','sasikumarpolus@gmail.com','12345skc','2024-07-15 06:18:58','IND','Tamil Nadu','kanyakumari','24567178881',NULL,NULL),(2,'Arun','Raj','Project Manager','arunrajpolus@gmail.com','4567aj','2024-07-15 06:18:58','IND','Kerala','kollam','9687325141',NULL,NULL),(3,'John','Winsely','Project Manager','johnwinselypolus@gmail.com','8910jw','2024-07-15 06:18:58','IND','Kerala','Kochi','5672826216',NULL,NULL),(4,'Sheena','J','Software Engineer Trainee','sheenapolus@gmail.com','12345678','2024-07-15 06:39:31','IND','Kerala','1234 Trivandrum','9876543210',NULL,NULL),(5,'Adarsh','SM','Intern','adarsh@polus.com','12345678','2024-07-15 09:04:14','BHR','Kerala','1234 Trivandrum','1234567890',NULL,NULL),(6,'Prakash','Warrier','Software Engineer Trainee','prakash1@gmail.com','prakash1234','2024-07-16 06:14:08','IND','Kerala','trivandrum','8965975834',NULL,NULL),(7,'Manesh','M S','Software Engineer Trainee','maneshpolus@gmail.com','1234ms','2024-07-16 06:17:04','AFG','Kerala','trivandrum','23456781',NULL,NULL),(9,'Abin','Raj','Intern','abin@polus.com','12345678','2024-07-16 06:19:23','ISL','Kerala','1234 Trivandrum','9090909090',NULL,NULL),(10,'Jeni','Mathews','Software Engineer Trainee','jeni@gmail.com','jeni1234','2024-07-16 06:42:34','IND','Kerala','trivandrum','7892357899',NULL,NULL),(11,'Roy','James','Software Engineer','roy@gmail.com','9876544332','2024-07-22 06:44:16','IND','Kerala','Trivandrum','9876543210','RoyJames','2024-07-24 09:21:49'),(12,'Neeraja','Biju','Intern','neerajabiju@gmail.com','12345678','2024-07-23 10:10:22','IND','Kerala','1234 House, Alapuzha ','8012345678',NULL,NULL),(13,'Adarsh','SM','Intern','karthik@gmail.com','12345678','2024-07-24 10:53:34','BMU','Kerala','1234 Main St.','9191919191','AdarshSM','2024-07-24 10:53:34'),(14,'A','B','C','praveen@gmail.com','12345678','2024-07-25 05:59:10','AUT','Kerala','1234 Aust St','8989898989','A B','2024-07-25 05:59:10'),(15,'sanidh','M S','Software Engineer Trainee','snula@gmail.com','1234ms','2024-07-25 05:59:39','IND','Kerala','trivandrum','23456781','sanidh M S','2024-07-25 05:59:39'),(16,'a','b','c','hari@yahoo.com','12345678','2024-07-25 06:02:27','ARG','Kerala','1234 Argent St','8989898989','a b','2024-07-25 06:02:27');

INSERT INTO `roles` VALUES (1,'APPLICATION_ADMINISTRATOR','The administrator can approve or reject a service request. In addition to that he can add an admin or remove an existing admin'),(2,'PRINCIPAL INVESTIGATOR','The principal investigator can create a service ticket and assign it to an administrator');

INSERT INTO `person_roles` VALUES (1,1,1,NULL,NULL),(2,2,1,NULL,NULL),(3,3,1,NULL,NULL),(4,4,1,NULL,NULL),(5,5,2,NULL,NULL),(6,6,2,NULL,NULL),(7,7,2,NULL,NULL),(8,9,2,NULL,NULL),(9,10,2,NULL,NULL),(10,1,2,NULL,NULL),(11,2,2,NULL,NULL),(12,3,2,NULL,NULL),(13,4,1,'2024-07-19 12:19:13',NULL),(14,11,2,NULL,NULL),(17,12,2,NULL,NULL),(18,13,2,NULL,NULL),(19,14,2,NULL,NULL),(20,15,2,NULL,NULL),(21,16,2,NULL,NULL);

INSERT INTO `sr_ticket_category` VALUES (1,'System Repairs','Quick and efficient repair services for all company hardware and software issues.','2024-07-16 04:31:05',1),(2,'Security','Comprehensive security solutions to protect company assets and employee data.','2024-07-16 04:31:05',1),(3,'Internet problems','Internet connectivity and performance issues.','2024-07-16 04:31:05',1),(4,'Installation','Professional installation services for software, hardware, and office equipment.','2024-07-16 04:31:05',1),(5,'Leave Request','Streamlined process for employees to request and manage their leave.','2024-07-16 04:31:05',1),(6,'Safety Issue','Resolution of any workplace safety concerns.','2024-07-16 04:31:05',1),(7,'Temperature Control','Management of office climate settings to ensure a comfortable working environment.','2024-07-16 04:31:05',1),(8,'Others','Ask any other services you are expecting','2024-07-22 09:36:33',1),(9,'Financial Support','offers monetary assistance to help you to achieve financial stability and meet financial goals.','2024-07-22 11:37:32',1);

INSERT INTO `sr_ticket_status` VALUES (1,'IN_Progress','The ticket is not yet assigned to an administrator can delete the created ticket at this stage'),(2,'ASSIGNED','The ticket is assigned to an administrator can\'t delete the ticket at this stage'),(3,'APPROVED','The ticket has been approved by the administrator anf furthur actions will be taken on the request'),(4,'REJECTED','The ticket has been rejected by the administrator for some reason');

-------------------------------------------------------------------------------------------------------------------
