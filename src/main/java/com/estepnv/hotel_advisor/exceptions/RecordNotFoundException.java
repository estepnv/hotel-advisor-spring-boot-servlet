package com.estepnv.hotel_advisor.exceptions;

public class RecordNotFoundException extends ApplicationException {
    public RecordNotFoundException(String entity, String id) {
        super("%s record not found id=%s".formatted(entity, id));
    }
}
