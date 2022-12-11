package gg.nils.minecraftstatusapi.controller;

import gg.nils.minecraftstatusapi.dto.PingSubmitDto;
import gg.nils.minecraftstatusapi.dto.PingTargetsDto;
import gg.nils.minecraftstatusapi.model.DataCollector;
import gg.nils.minecraftstatusapi.model.Ping;
import gg.nils.minecraftstatusapi.model.Server;
import gg.nils.minecraftstatusapi.repository.DataCollectorRepository;
import gg.nils.minecraftstatusapi.repository.PingRepository;
import gg.nils.minecraftstatusapi.repository.ServerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PingController {

    private final DataCollectorRepository dataCollectorRepository;
    private final PingRepository pingRepository;
    private final ServerRepository serverRepository;

    public PingController(DataCollectorRepository dataCollectorRepository, PingRepository pingRepository, ServerRepository serverRepository) {
        this.dataCollectorRepository = dataCollectorRepository;
        this.pingRepository = pingRepository;
        this.serverRepository = serverRepository;
    }

    @GetMapping("/v1/ping/targets")
    public ResponseEntity<Map<Long, String>> targets(@Valid @RequestBody PingTargetsDto data) {
        DataCollector dataCollector = this.dataCollectorRepository.findByTokenLike(data.getToken());

        if (dataCollector == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<Long, String> result = this.serverRepository.findAll().stream()
                .collect(Collectors.toMap(Server::getId, Server::getAddress));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/ping/submit")
    public ResponseEntity<?> submit(@Valid @RequestBody PingSubmitDto data) {
        DataCollector dataCollector = this.dataCollectorRepository.findByTokenLike(data.getToken());

        if (dataCollector == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Server> optionalServer = this.serverRepository.findById(data.getServerId());

        if (optionalServer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Server server = optionalServer.get();

        Ping ping = new Ping();
        ping.setServer(server);
        ping.setDataCollector(dataCollector);
        ping.setCount(data.getCount());
        this.pingRepository.save(ping);

        return ResponseEntity.ok().build();
    }
}
