package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RunWith(SpringRunner.class)    // <1>
@SpringBootTest                 // <2>
public class TacoCloudApplicationTests {

  @Test                         // <3>
  public void contextLoads() {
  }


  /*@Test
  public void f1(){
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;
      try {
        connection = dataSource.getConnection();
        statement = connection.prepareStatement(
                "select id, name, type from Ingredient where id=?");
        statement.setString(1, id);
        resultSet = statement.executeQuery();
        Ingredient ingredient = null;
        if(resultSet.next()) {
          ingredient = new Ingredient(
                  resultSet.getString("id"),
                  resultSet.getString("name"),
                  Ingredient.Type.valueOf(resultSet.getString("type")));
        }
        return ingredient;
      } catch (SQLException e) {
        // ??? What should be done here ???
      } finally {
        if (resultSet != null) {
          try {
            resultSet.close();
          } catch (SQLException e) {}
        }
        if (statement != null) {
          try {
            statement.close();
          } catch (SQLException e) {}
        }
        if (connection != null) {
          try {
            connection.close();
          } catch (SQLException e) {}
        }
      }
      return null;
    }*/


}
