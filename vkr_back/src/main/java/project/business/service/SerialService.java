package project.business.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.entity.SerialImpl;
import project.dao.repoitory.SerialRepository;
import project.dao.repoitory.UserRepository;
import project.dto.common.StatusImpl;
import project.dto.serial.SerialAddDto;
import project.dto.serial.SerialResponseInfoImpl;
import project.dto.serial.api.SerialResponseInfo;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SerialService {

    private final SerialRepository serialRepository;

    private final UserRepository userRepository;

    @Autowired
    public SerialService(SerialRepository serialRepository,
                         UserRepository userRepository) {
        this.serialRepository = serialRepository;
        this.userRepository = userRepository;
    }

    public SerialResponseInfo save(SerialAddDto serialAddDto) {
        log.debug(String.format("Сохраняемый репозиторий: %s", serialAddDto));
        SerialImpl serialEntity = SerialImpl.builder()
                .completed(true)
                .episode(serialAddDto.getEpisode())
                .season(serialAddDto.getSeason())
                .title(serialAddDto.getTitle())
                .user(
                        StringUtils.isNotBlank(serialAddDto.getLogin()) ?
                                userRepository.findByLogin(serialAddDto.getLogin()).orElse(null) :
                                userRepository.findById(serialAddDto.getUserId()).orElse(null)
                )
                .build();
        SerialImpl serial = serialRepository.save(serialEntity);
        log.debug(String.format("Сохраненный сериал: %s", serial));
        return SerialResponseInfoImpl.builder()
                .status(StatusImpl.builder().success(true).build())
                .serialList(Collections.singletonList(serial))
                .build();
    }

    public SerialResponseInfo getAllByUserId(String userLogin) {
        List<SerialImpl> foundSerials = serialRepository.findByUserLogin(userLogin);
        log.debug(String.format("Найденный список сериалов: %s", foundSerials));
        return foundSerials.size() > 0 ?
                SerialResponseInfoImpl.builder()
                        .status(StatusImpl.builder().success(true).build())
                        .serialList(foundSerials)
                        .build() :
                SerialResponseInfoImpl.builder()
                        .status(StatusImpl.builder()
                                .success(false)
                                .description("У пользователя еще нет сохраненнх сериалов")
                                .build())
                        .build();
    }
}
