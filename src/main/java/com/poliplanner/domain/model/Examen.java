package com.poliplanner.domain.model;

import com.poliplanner.domain.enums.TipoExamen;

import java.util.Date;

public class Examen {
    Date fecha;
    Aula aula;
    Date fechaRevision;

    TipoExamen tipo;
}
