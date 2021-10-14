package za.co.moitrack.data.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import za.co.moitrack.data.model.VehicleReport;

import java.util.Collection;

public interface VehicleReportRepository extends MongoRepository<VehicleReport, String> {
    @Query(value = "{'reportDate': {$gte: ?0}}")
    VehicleReport findByReportDate(DateTime ReportDate);

    @Query(value = "{'reportDate': {$gte: ?0, $lt: ?1}}")
    public Collection<VehicleReport> findByDateBetween(DateTime dateFrom, DateTime dateTo, Sort sort);
}
