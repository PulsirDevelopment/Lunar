package net.pulsir.lunar.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import net.pulsir.lunar.utils.base64.Base64;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
            statement.execute("CREATE TABLE IF NOT EXISTS inventories(uuid varchar(36) primary key, inventory blob)");
            statement.execute("CREATE TABLE IF NOT EXISTS notes(noteId varchar(36) primary key, uuid varchar(36), staffUUID varchar(36), createdAt bigint, note varchar(256))");
            statement.execute("CREATE TABLE IF NOT EXISTS maintenances(name varchar(36), reason tinytext, duration int, endDate bigint)");
            statement.execute("CREATE TABLE IF NOT EXISTS offline(uuid varchar(36) primary key, playerInventory tinytext, enderChestInventory tinytext)");
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
            preparedStatement.setString(2, Base64.toBase64(Lunar.getInstance().getInventoryPlayerManager().getInventories().get(uuid).getInventoryString()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInventory(UUID uuid) {
        try {
            PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("UPDATE inventories SET inventory = ? WHERE uuid = ?");
            preparedStatement.setString(1, Base64.toBase64(Lunar.getInstance().getInventoryPlayerManager().getInventories().get(uuid).getInventoryString()));
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearInventory(UUID uuid) {
        try {
            PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("DELETE FROM inventories WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMaintenance(Maintenance maintenance) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("INSERT INTO maintenances(name, reason, duration, endDate) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, maintenance.getName());
            preparedStatement.setString(2, maintenance.getReason());
            preparedStatement.setInt(3, maintenance.getDuration());
            preparedStatement.setLong(4, (maintenance.getEndDate() != null) ? maintenance.getEndDate().getTime() : -1L);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMaintenance(Maintenance maintenance) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("UPDATE maintenances SET reason = ?, duration = ?, endDate = ? WHERE name = ?");
            preparedStatement.setString(1, maintenance.getReason());
            preparedStatement.setInt(2, maintenance.getDuration());
            preparedStatement.setLong(3, (maintenance.getEndDate() != null) ? maintenance.getEndDate().getTime() : -1L);
            preparedStatement.setString(4, maintenance.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeMaintenance(Maintenance maintenance) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("DELETE FROM maintenances WHERE name = ?");
            preparedStatement.setString(1, maintenance.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Maintenance> loadMaintenances() {
        Set<Maintenance> maintenances = new HashSet<>();
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("SELECT * FROM maintenances");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String reason = resultSet.getString(2);
                int duration = resultSet.getInt(3);
                Date endDate = resultSet.getLong(4) == -1 ? null : new Date(resultSet.getLong(4));

                maintenances.add(new Maintenance(name, reason, duration, endDate));
            }

            preparedStatement.close();
            return maintenances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Maintenance findMaintenance(String name) {
        try {
            PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement("SELECT * FROM maintenances WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            Maintenance maintenance;

            if (resultSet.next()) {
                maintenance = new Maintenance(name, resultSet.getString("reason"),
                        resultSet.getInt("duration"), new Date(resultSet.getLong("endDate")));
                preparedStatement.close();

                return maintenance;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteMaintenance(String name) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("DELETE FROM maintenances WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOfflineInventory(UUID uuid, String playerInventoryString, String enderChestInventoryString) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("INSERT INTO offline VALUES (?,?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, playerInventoryString);
            preparedStatement.setString(3, enderChestInventoryString);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOfflineInventory(UUID uuid) {
        try {
            PreparedStatement preparedStatement = this.hikariDataSource.getConnection().prepareStatement("DELETE FROM offline WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OfflinePlayerInventory findOfflineInventory(UUID uuid) throws IOException {
        OfflinePlayerInventory offlinePlayerInventory;
        try {
            PreparedStatement preparedStatement = preparedStatement = this.hikariDataSource.getConnection().prepareStatement("SELECT * FROM offline WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String playerInventoryString = resultSet.getString("playerInventory");
                String enderChestInventoryString = resultSet.getString("enderChestInventory");
                ItemStack[] playerInventory = Base64.fromBase64(playerInventoryString).getContents();
                ItemStack[] enderChestInventory = Base64.fromBase64(enderChestInventoryString).getContents();

                offlinePlayerInventory = new OfflinePlayerInventory(playerInventory, enderChestInventory);
                preparedStatement.close();

                return offlinePlayerInventory;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
