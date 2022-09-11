package com.ncoding.infrastructure.mariadb;

import com.ncoding.core.models.DayPhases;
import com.ncoding.core.ports.MoodRepository;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MariaDbMoodRepository implements MoodRepository {

    private final DataSource dataSource;

    public MariaDbMoodRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<String> getMood(DayPhases dayPhases) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> strings = new ArrayList<>();
        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT phrase FROM Mood WHERE dayPhase = ?");

            stmt.setString(1, dayPhases.toString());

            rs = stmt.executeQuery();

            while (rs.next()) {
                strings.add(rs.getString("phrase"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(rs);
                DbUtils.close(stmt);
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return strings;
    }
}
