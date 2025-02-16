package integration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationEnvironmentTest extends IntegrationEnvironment{
    @Test
    @SneakyThrows
    public void check_AllTables() {
        String query = "SELECT * FROM information_schema.tables WHERE table_schema='public'";
        ArrayList<String> names = new ArrayList<>();

        try(Connection connection = SQL_CONTAINER.createConnection("")) {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                names.add(resultSet.getString("table_name"));
            }
        }

        assertThat(names.contains("users")).isTrue();
        assertThat(names.contains("merch")).isTrue();
        assertThat(names.contains("transfers")).isTrue();
        assertThat(names.contains("inventory")).isTrue();
        assertThat(names.contains("databasechangelog")).isTrue();
        assertThat(names.contains("databasechangeloglock")).isTrue();
    }
}
