package com.valoria.backend.dto;

import java.util.Map;

public record ErrorResponse(int status, String mensaje, Map<String, String> errores) {
}