package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ONBA7293 on 11/01/2017.
 */
@Service
final class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report create(Report report) {
        Report persisted = reportRepository.save(report);
        return persisted;
    }

    public Report delete(String id) {
        Report deleted = findReportById(id);
        reportRepository.delete(deleted);
        return deleted;
    }

    public List<Report> findAll() {
        List<Report> reportEntries = reportRepository.findAll();
        return reportEntries;
    }

    public Report findById(String id) {
        Report result = reportRepository.findById(id);
        return result;
    }

    private Report findReportById(String id) {
        Report result = reportRepository.findById(id);
        return result;
    }

    public void deleteAll() {
        reportRepository.deleteAll();
    }

    public List<Report> findByDate(Date date) {
        List<Report> result = reportRepository.findByDate(date);
        return result;
    }
}

