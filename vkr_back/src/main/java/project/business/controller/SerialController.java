package project.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.business.service.SerialService;
import project.dto.common.StatusImpl;
import project.dto.serial.SerialAddDto;
import project.dto.serial.SerialResponseInfoImpl;
import project.dto.serial.api.SerialResponseInfo;

@Slf4j
@RestController
@RequestMapping("/series")
@CrossOrigin("http://localhost:3000")
public class SerialController {

    private final SerialService serialService;

    @Autowired
    public SerialController(SerialService serialService) {
        this.serialService = serialService;
    }

    @PostMapping("/add")
    public SerialResponseInfo add(@RequestBody SerialAddDto serialAddDto) {
        try {
            return serialService.save(serialAddDto);
        } catch (Throwable e) {
            log.error(String.format("Ошибка сохранения сериала %s", serialAddDto));
            log.error(e.getMessage(), e.getCause());
            return SerialResponseInfoImpl.builder()
                    .status(StatusImpl.builder().success(false).build())
                    .build();
        }
    }
}
