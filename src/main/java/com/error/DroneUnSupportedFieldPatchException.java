package com.error;

import java.util.Set;

public class DroneUnSupportedFieldPatchException extends RuntimeException {

    public DroneUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}
