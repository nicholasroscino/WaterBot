package com.ncoding.infrastructure.mariadb;

import com.ncoding.core.models.Report;
import com.ncoding.core.ports.ReportRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class MariaDbReportRepository implements ReportRepository {

    private final DataSource dataSource;

    public MariaDbReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Report report) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement("INSERT INTO waterbot.Report(userId, message, timestamp) VALUE (?,?,?)");

            stmt.setString(1, report.getUserId().getValue());
            stmt.setString(2, report.getMessage());
            stmt.setTimestamp(3, Timestamp.from(Instant.parse(report.getTimestamp())));

            stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
