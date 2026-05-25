package com.finance.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.MonthlyReportDto;
import com.finance.dashboard.dto.YearlyReportDto;
import com.finance.dashboard.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<MonthlyReportDto> getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month,
            Authentication authentication) {
        String username = authentication.getName();
        MonthlyReportDto report = reportService.generateMonthlyReport(year, month, username);
        return ResponseEntity.ok(report); 
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyReportDto> getYearlyReport(
            @PathVariable int year,
            Authentication authentication) {
        String username = authentication.getName();
        YearlyReportDto report = reportService.generateYearlyReport(year, username);
        return ResponseEntity.ok(report); 
    }
}