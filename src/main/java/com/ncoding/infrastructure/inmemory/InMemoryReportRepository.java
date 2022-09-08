package com.ncoding.infrastructure.inmemory;

import com.ncoding.core.models.Report;
import com.ncoding.core.ports.ReportRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReportRepository implements ReportRepository {
    private final List<Report> reports = new ArrayList<>();

    @Override
    public void save(Report report) {
        reports.add(report);
    }
}
