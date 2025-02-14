CREATE OR REPLACE FUNCTION update_game_rating()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE gh_game
    SET
        nu_rating = (SELECT COALESCE(AVG(a.nu_rating), 0) FROM gh_assessment a WHERE a.id_game = NEW.id_game),
        nu_total_evaluation = (SELECT COUNT(*) FROM gh_assessment a WHERE a.id_game = NEW.id_game)
    WHERE id_game = NEW.id_game;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Criação do gatilho
CREATE TRIGGER assessment_insert_update
AFTER INSERT OR UPDATE ON gh_assessment
FOR EACH ROW
EXECUTE FUNCTION update_game_rating();


INSERT INTO GH_PROFILE (no_name, dt_birth_date, gender)
VALUES
('Gabriel Victor', '1990-05-20', 'MALE'),
('Pedro Lucas', '1985-03-15', 'MALE');

INSERT INTO GH_USER (no_email, no_password_hash, user_type, id_profile)
VALUES
('gabriel.victor@example.com', 'hashed_password_1', 'ADMIN', 1),
('pedro.lucas@example.com', 'hashed_password_2', 'ADMIN', 2);



INSERT INTO "gh_developer" (no_name, nu_year_of_foundation, no_host_country)
VALUES
    ('Naughty Dog', 1984, 'United States'),
    ('Rockstar Games', 1998, 'United States'),
    ('Ubisoft', 1986, 'France'),
    ('CD Projekt Red', 1994, 'Poland'),
    ('Bethesda Game Studios', 1986, 'United States'),
    ('Square Enix', 1986, 'Japan');


INSERT INTO "gh_platform" (no_name)
VALUES
('PC'),
('PlayStation 5'),
('Xbox Series X'),
('Xbox One'),
('PlayStation 4'),
('Android');

-- Inserção de categorias de jogos
INSERT INTO "gh_category" ("no_name")
VALUES
('Ação'),         -- Categoria 1
('Aventura'),     -- Categoria 2
('RPG'),          -- Categoria 3
('Esportes'),     -- Categoria 4
('Estratégia'),   -- Categoria 5
('Simulação'),    -- Categoria 6
('Multijogador'), -- Categoria 7
('Indie'),        -- Categoria 8
('Terror'),       -- Categoria 9
('Casual');       -- Categoria 10


INSERT INTO "gh_game" (id_developer, no_name, dt_release, nu_rating, nu_total_evaluation)
VALUES
    -- Naughty Dog
    (1, 'The Last of Us', '2013-06-14', 0.0, 0),
    (1, 'Uncharted: Drakes Fortune', '2007-11-19', 0.0, 0),
    (1, 'Crash Bandicoot', '1996-08-31', 0.0, 0),
    (1, 'Jak and Daxter: The Precursor Legacy', '2001-12-03', 0.0, 0),
    (1, 'The Last of Us Part II', '2020-06-19', 0.0, 0),

    -- Rockstar Games
    (2, 'Grand Theft Auto V', '2013-09-17', 0.0, 0),
    (2, 'Red Dead Redemption 2', '2018-10-26', 0.0, 0),
    (2, 'Bully', '2006-10-17', 0.0, 0),
    (2, 'Max Payne 3', '2012-05-15', 0.0, 0),
    (2, 'L.A. Noire', '2011-05-17', 0.0, 0),

    -- Ubisoft
    (3, 'Assassins Creed Valhalla', '2020-11-10', 0.0, 0),
    (3, 'Far Cry 5', '2018-03-27', 0.0, 0),
    (3, 'Tom Clancys Rainbow Six Siege', '2015-12-01', 0.0, 0),
    (3, 'Watch Dogs', '2014-05-27', 0.0, 0),
    (3, 'The Division 2', '2019-03-15', 0.0, 0),

    -- CD Projekt Red
    (4, 'The Witcher 3: Wild Hunt', '2015-05-19', 0.0, 0),
    (4, 'Cyberpunk 2077', '2020-12-10', 0.0, 0),
    (4, 'The Witcher 2: Assassins of Kings', '2011-05-17', 0.0, 0),
    (4, 'Gwent: The Witcher Card Game', '2017-05-24', 0.0, 0),
    (4, 'Thronebreaker: The Witcher Tales', '2018-10-23', 0.0, 0),

    -- Bethesda Game Studios
    (5, 'The Elder Scrolls V: Skyrim', '2011-11-11', 0.0, 0),
    (5, 'Fallout 4', '2015-11-10', 0.0, 0),
    (5, 'The Elder Scrolls IV: Oblivion', '2006-03-20', 0.0, 0),
    (5, 'Fallout 76', '2018-11-14', 0.0, 0),
    (5, 'The Elder Scrolls Online', '2014-04-04', 0.0, 0),

    -- Square Enix
    (6, 'Final Fantasy XV', '2016-11-29', 0.0, 0),
    (6, 'Kingdom Hearts III', '2019-01-29', 0.0, 0),
    (6, 'Final Fantasy VII Remake', '2020-04-10', 0.0, 0),
    (6, 'Dragon Quest XI', '2017-07-29', 0.0, 0),
    (6, 'Tomb Raider', '2013-03-05', 0.0, 0);


INSERT INTO "gh_store" (no_name, no_url)
VALUES
    ('Steam', 'https://store.steampowered.com'),
    ('Epic Games', 'https://www.epicgames.com/store'),
    ('GOG', 'https://www.gog.com'),
    ('Nuuvem', 'https://www.nuuvem.com'),
    ('Green Man Gaming', 'https://www.greenmangaming.com'),
    ('Microsoft Store', 'https://apps.microsoft.com');


-- Inserir jogos obtidos pelos 2 usuários
INSERT INTO GH_USER_OBTAINED (id_user, id_game, id_store, status)
VALUES
    -- Usuário 1
    (1, 16, 4, 'FINISHED'),
    (1, 17, 1, 'NOT_PLAYED'),
    (1, 18, 4, 'PLAYING'),
    (1, 19, 2, 'ON_PAUSE'),
    (1, 20, 2, 'FINISHED'),
    (1, 21, 2, 'PLAYING'),
    (1, 22, 4, 'NOT_PLAYED'),
    (1, 23, 1, 'FINISHED'),
    (1, 24, 3, 'ON_PAUSE'),
    (1, 25, 2, 'PLAYING'),
    (1, 26, 4, 'NOT_PLAYED'),
    (1, 27, 1, 'FINISHED'),
    (1, 28, 3, 'PLAYING'),
    (1, 29, 2, 'ON_PAUSE'),
    (1, 30, 4, 'NOT_PLAYED'),

    -- Usuário 2
    (2, 1, 1, 'NOT_PLAYED'),
    (2, 2, 1, 'PLAYING'),
    (2, 3, 2, 'FINISHED'),
    (2, 4, 3, 'NOT_PLAYED'),
    (2, 5, 4, 'ON_PAUSE'),
    (2, 6, 4, 'PLAYING'),
    (2, 7, 2, 'NOT_PLAYED'),
    (2, 8, 1, 'FINISHED'),
    (2, 9, 2, 'ON_PAUSE'),
    (2, 10, 3, 'NOT_PLAYED'),
    (2, 11, 3, 'FINISHED'),
    (2, 12, 4, 'PLAYING'),
    (2, 13, 2, 'NOT_PLAYED'),
    (2, 14, 1, 'ON_PAUSE'),
    (2, 15, 3, 'PLAYING');


INSERT INTO "gh_game_platform" ("id_game", "id_platform")
VALUES
(1, 2),  -- Jogo 1 disponível no PlayStation 5
(2, 2),  -- Jogo 2 disponível no PlayStation 5
(3, 2),  -- Jogo 3 disponível no PlayStation 5
(4, 3),  -- Jogo 4 disponível no Xbox Series X
(5, 2),  -- Jogo 5 disponível no PlayStation 5
(6, 4);  -- Jogo 6  disponível no Xbox One


INSERT INTO "gh_game_category" ("id_game", "id_category")
VALUES
(1, 1),  -- Jogo 1 na categoria Ação
(1, 2),  -- Jogo 1 na categoria Aventura
(2, 3),  -- Jogo 2 na categoria RPG
(3, 4),  -- Jogo 3 na categoria Esportes
(4, 5),  -- Jogo 4 na categoria Estratégia
(5, 6),  -- Jogo 5 na categoria Simulação
(6, 7),  -- Jogo 6 na categoria Multijogador
(7, 8),  -- Jogo 7 na categoria Indie
(8, 9);  -- Jogo 8 na categoria Terror


INSERT INTO "gh_user_wishlist_game" ("id_user", "id_game")
VALUES
(1, 25),
(1, 20),
(1,  4),
(1,  5),
(1,  9),
(1, 21),
(1,  2),
(1, 18),
(2,  6),
(2, 30),
(2, 20),
(2, 22),
(2,  3),
(2,  5),
(2, 18),
(2,  8),
(2, 15),
(2, 23);

INSERT INTO "gh_assessment" ("id_user", "id_game", "nu_rating", "ds_comment")
VALUES
    -- Avaliações para 'The Last of Us'
    (1, 1, 9, 'Excelente jogo, uma obra-prima!'),
    (2, 1, 6, 'Muito bom, mas poderia ser mais longo.'),

    -- Avaliações para 'Grand Theft Auto V'
    (1, 6, 10, 'Ótima jogabilidade e história incrível.'),
    (2, 6, 9, 'Jogo sensacional, mas alguns bugs às vezes atrapalham.'),

    -- Avaliações para 'Assassins Creed Valhalla'
    (1, 11, 8, 'Uma das melhores da franquia, adorei a jogabilidade.'),
    (2, 11, 4, 'Muito bom, mas poderia ter mais opções de personalização.'),

    -- Avaliações para 'The Witcher 3: Wild Hunt'
    (1, 16, 10, 'Um dos melhores RPGs de todos os tempos!'),
    (2, 16, 8, 'Muito bom, mas os controles poderiam ser mais fluidos.'),

    -- Avaliações para 'The Elder Scrolls V: Skyrim'
    (1, 21, 10, 'O melhor RPG que já joguei, com muita liberdade!'),
    (2, 21, 9, 'Muito bom, mas a história poderia ser mais interessante.'),

    -- Avaliações para 'Final Fantasy XV'
    (1, 26, 8, 'Maravilhoso! A história é envolvente e os gráficos são incríveis.'),
    (2, 26, 7, 'Ótimo jogo, mas algumas missões secundárias são repetitivas.');


