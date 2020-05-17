package com.poliplanner.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="params.horario")
@Data
public class HorarioProps {
    private String url;
    private String seccionesUrl;
}
