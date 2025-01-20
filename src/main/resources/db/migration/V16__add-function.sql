CREATE OR REPLACE FUNCTION filter_games(
    p_user_id INT,
    p_category_name TEXT DEFAULT NULL,
    p_publisher_name TEXT DEFAULT NULL,
    p_status game_status DEFAULT NULL
)
RETURNS TABLE (
    game_name TEXT,
    category TEXT,
    publisher TEXT,
    status TEXT,
    game_status TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        g."no_name"::TEXT AS "Game Name",
        c."no_name"::TEXT AS "Category",
        d."no_name"::TEXT AS "Publisher",
        ug."st_status"::TEXT AS "Status",
        CASE
            WHEN ug."id_user" IS NOT NULL THEN 'Obtido'
            ELSE 'Desejado'
        END AS "Game Status"
    FROM
        "gh_game" g
    LEFT JOIN "gh_game_category" gc ON g."id_game" = gc."id_game"
    LEFT JOIN "gh_category" c ON gc."id_category" = c."id_category"
    LEFT JOIN "gh_developer" d ON g."id_developer" = d."id_developer"
    LEFT JOIN "gh_user_obtained_game" ug ON g."id_game" = ug."id_game"
    LEFT JOIN "gh_user_wishlist_game" uw ON g."id_game" = uw."id_game"
    WHERE
        (p_category_name IS NULL OR c."no_name" = p_category_name)
        AND (p_publisher_name IS NULL OR d."no_name" = p_publisher_name)
        AND (p_status IS NULL OR ug."st_status" = p_status::game_status)
        AND (ug."id_user" = p_user_id OR uw."id_user" = p_user_id);
END;
$$ LANGUAGE plpgsql;

