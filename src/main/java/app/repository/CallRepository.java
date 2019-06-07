package app.repository;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {
    @Query(value =
            "select new app.dto.CallsPerCity(ct.name, count(c)) " +
                    "from Call c " +
                    "left join c.city ct " +
                    "group by ct.name ")
    List<CallsPerCity> findCallsPerCities();

    @Query(value =
            "select c " +
                    "from Call c " +
                    "where c.callDt between :#{#clientAndDateRange.dateFrom} and :#{#clientAndDateRange.dateTo} and " +
                    "(c.callerPhoneNumber.client.id = :#{#clientAndDateRange.clientId} or " +
                    "c.recipientPhoneNumber.client.id = :#{#clientAndDateRange.clientId}) " +
                    "order by c.duration desc")
    List<Call> findCallsByClientAndDataRange(@Param("clientAndDateRange") ClientAndDateRange clientAndDateRange,
                                             Pageable pageable);
}
