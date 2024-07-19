package net.pulsir.lunar.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.utils.base64.Base64;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@Getter
public class MySQLManager {

    private final HikariDataSource hikariDataSource;

    public MySQLManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + Lunar.getInstance().getConfiguration().getConfiguration().getString("mysql.host") +
                ":" + Lunar.getInstance().getConfiguration().getConfiguration().getInt("mysql.port") + "/" +
                Lunar.getInstance().getConfiguration().getConfiguration().getString("mysql.database"));

        config.setUsername(Lunar.getInstance().getConfiguration().getConfiguration().getString("mysql.auth.username"));
        config.setPassword(Lunar.getInstance().getConfiguration().getConfiguration().getString("mysql.auth.password"));
        hikariDataSource = new HikariDataSource(config);

        init();
    }

    private void init() {
        try {
            Statement statement = hikariDataSource.getConnection().createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS inventories(uuid varchar(36) primary key, inventory varchar(128))");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InventoryPlayer findInventory(UUID uuid) throws SQLException {
        PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("SELECT * FROM inventories WHERE uuid = ?");
        preparedStatement.setString(1, uuid.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        InventoryPlayer inventoryPlayer;

        if (resultSet.next()) {
            inventoryPlayer = new InventoryPlayer(UUID.fromString(resultSet.getString("uuid")),
                    Base64.toInventory(resultSet.getString("inventory")));
            preparedStatement.close();
            return inventoryPlayer;
        }

        return null;
    }

    public void createInventory(UUID uuid) {
        try {
            PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("INSERT INTO inventories(uuid, inventory) VALUES (?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, c);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInventory(UUID uuid) {
        try {
            PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("UPDATE inventories SET inventory = ? WHERE uuid = ?");
            preparedStatement.setString(1, Base64.toBase64(Lunar.getInstance().getInventoryManager().getInventories().get(uuid).getInventoryString()));
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
