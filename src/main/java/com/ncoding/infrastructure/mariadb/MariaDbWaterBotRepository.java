package com.ncoding.infrastructure.mariadb;

import com.ncoding.core.models.User;
import com.ncoding.core.models.UserId;
import com.ncoding.core.ports.UserRepository;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.HashSet;

public class MariaDbWaterBotRepository implements UserRepository {
    private final DataSource dataSource;

    public MariaDbWaterBotRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void register(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO waterbot.Users(userId, name, tag, timeOffsetInSecond) VALUE (?,?,?,?)");

            stmt.setString(1, user.getUserId().getValue());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getTag());
            stmt.setInt(4, user.getOffset().get(ChronoField.OFFSET_SECONDS));

            stmt.executeQuery();
        } catch(SQLIntegrityConstraintViolationException e) {
            update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(stmt);
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "UPDATE waterbot.Users SET userId = ?, name = ?, tag = ?, timeOffsetInSecond =? WHERE userId = ?;"
            );

            stmt.setString(1, user.getUserId().getValue());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getTag());
            stmt.setInt(4, user.getOffset().get(ChronoField.OFFSET_SECONDS));
            stmt.setString(5, user.getUserId().getValue());

            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(stmt);
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getOne(UserId userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        User user = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "SELECT name, tag, timeOffsetInSecond FROM waterbot.Users WHERE userId = ?;"
            );

            stmt.setString(1, userId.getValue());
            rs = stmt.executeQuery();

            if(rs.next()) {
                var name = rs.getString("name");
                var tag = rs.getString("tag");
                var zoneOffset = ZoneOffset.ofTotalSeconds(rs.getInt("timeOffsetInSecond"));

                user = new User(userId,name,tag,zoneOffset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(stmt);
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public HashSet<User> getAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        HashSet<User> userSet = new HashSet<>();

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "SELECT userId, name, tag, timeOffsetInSecond FROM waterbot.Users;"
            );

            rs = stmt.executeQuery();

            while(rs.next()) {
                var userId = new UserId(rs.getString("userId"));
                var name = rs.getString("name");
                var tag = rs.getString("tag");
                var zoneOffset = ZoneOffset.ofTotalSeconds(rs.getInt("timeOffsetInSeconds"));

                userSet.add(new User(userId,name,tag,zoneOffset));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(stmt);
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userSet;
    }
}
