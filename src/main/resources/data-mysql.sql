INSERT INTO pays(id, name) VALUES (1, 'France'), (2, 'The Netherlands'), (3, 'Australia'), (4, 'England');
INSERT INTO emploi(id, name) VALUES (1, 'Developpeur'), (2, 'Testeur'), (3, 'Chef de projet');
INSERT INTO role(id, name) VALUES (1, 'ROLE_UTILISATEUR'), (2, 'ROLE_ADMINISTRATEUR');
INSERT INTO entreprise(nom) VALUES ('Amazon'), ('Google'), ('Red hat');
INSERT INTO utilisateur(firstname, lastname, pays_id, entreprise_id, email, password, role_id, created_at, updated_at)
VALUES ("John", "DOE", 4, 1, "john@doe.com", "$2a$10$Bp7tWq.sklkq8jRa4cCCFuzAzmFAzyrsjr.lQQOQaUnI0yQxkP2Gu", 1, UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ("Steeve", "SMITH", 4, 2, "steeve@smith.com", "$2a$10$2AzxBEnTTVe.iJfiUZKFB.owbjawiitf.Ixh/AEo8pNTpZO2tTptu", 1, UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ("Alizée", "SCHWARTZ", 1, 3, "alizee@schwartz.com", "$2a$10$Vz5tZyU/r9/Fvkv97mfr3OHGCIIh1HNXbMQlXmLBFMg5gDLoo9d6G", 2, UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ("Frédéric", "CARON", 1, 1, "frederic@caron.com", "$2a$10$RGfP7v3kJEA/pJjGDqEnFeCgYYJ600tCM4hENehCLSWo521ootOLi", 2, UTC_TIMESTAMP(), UTC_TIMESTAMP());
INSERT INTO `recherche_emploi_utilisateur` (`utilisateur_id`, `emploi_id`) VALUES (1, 1), (1, 2), (2, 2);

INSERT INTO `contrat` (`id`, `date_de_creation`, `date_de_retour`) VALUES
                                                                       (1, '2023-05-09', '2023-05-11'),
                                                                       (2, '2023-05-10', NULL);

INSERT INTO `materiel` (`id`, `nom`, `numero`) VALUES
                                                   (1, 'Ecran 30\"', 123),
                                                   (2, 'Ecran 30\"', 456),
                                                   (3, 'Clavier', 789);

INSERT INTO `ligne_de_contrat` (`contrat_id`, `materiel_id`, `date_de_retour_anticipe`) VALUES
                                                                                            (1, 1, NULL),
                                                                                            (1, 3, '2023-05-10'),
                                                                                            (2, 2, NULL);