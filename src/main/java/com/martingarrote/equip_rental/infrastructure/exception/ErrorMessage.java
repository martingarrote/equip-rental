package com.martingarrote.equip_rental.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {

    OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "O recurso solicitado não foi encontrado. Verifique o ID ou parâmetros fornecidos."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "A requisição contém dados inválidos ou mal formatados. Verifique os campos obrigatórios e os tipos de dados."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Falha na validação dos dados fornecidos. Verifique os campos obrigatórios e as restrições de validação."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível processar a requisição. Verifique os dados fornecidos e tente novamente."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Acesso não autorizado. É necessário autenticação para acessar este recurso."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este recurso."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Método HTTP não permitido para este recurso."),
    CONFLICT(HttpStatus.CONFLICT, "Conflito de dados. O recurso que você está tentando criar ou modificar já existe ou está em um estado inconsistente."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de mídia não suportado. Verifique o cabeçalho 'Content-Type' da requisição."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "Muitas requisições. Por favor, tente novamente mais tarde."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde ou entre em contato com o suporte."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "Serviço temporariamente indisponível. Estamos enfrentando problemas técnicos. Por favor, tente novamente mais tarde."),

    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Credenciais de acesso estão incorretas"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Já existe um usuário cadastrado com este email"),
    USER_INVALID_ROLE(HttpStatus.BAD_REQUEST, "A role informada é inválida, deve ser ADMIN, EMPLOYEE ou CUSTOMER"),

    EQUIPMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Equipamento não encontrado"),
    EQUIPMENT_ALREADY_EXISTS(HttpStatus.CONFLICT, "Equipamento já cadastrado"),
    EQUIPMENT_NOT_AVAILABLE(HttpStatus.CONFLICT, "Equipamento não disponível"),
    EQUIPMENT_IN_USE(HttpStatus.CONFLICT, "Equipamento em uso");

    private final HttpStatus status;
    private final String defaultMessage;
}
