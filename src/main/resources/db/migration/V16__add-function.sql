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
