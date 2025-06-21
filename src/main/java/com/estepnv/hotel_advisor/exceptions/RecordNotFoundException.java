package com.estepnv.hotel_advisor.exceptions;

public class RecordNotFoundException extends ApplicationException {
    public RecordNotFoundException(Class klass, String id) {
        super("%s record not found id=%s".formatted(klass.toString(), id));
    }
}
