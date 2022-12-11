package gg.nils.minecraftstatusapi.repository;

import gg.nils.minecraftstatusapi.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {

}
