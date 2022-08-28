package com.ncoding.core.ports;

import com.ncoding.core.models.Report;

public interface ReportRepository {
    void save(Report report);
}
