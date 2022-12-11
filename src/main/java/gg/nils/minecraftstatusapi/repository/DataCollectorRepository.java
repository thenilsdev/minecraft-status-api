package gg.nils.minecraftstatusapi.repository;

import gg.nils.minecraftstatusapi.model.DataCollector;
import gg.nils.minecraftstatusapi.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface DataCollectorRepository extends JpaRepository<DataCollector, Long> {
    DataCollector findByTokenLike(@NonNull String token);

}
