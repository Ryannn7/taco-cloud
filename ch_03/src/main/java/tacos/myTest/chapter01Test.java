package tacos.myTest;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import tacos.Ingredient;

import javax.activation.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class chapter01Test {

    private JdbcTemplate jdbc;

    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id);
    }


    public Ingredient findOne2(String id){
        return jdbc.queryForObject("select  id ,name,type from Ingredient where id=?",
                this::mapRowToIngredient,id);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
            throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }


}
