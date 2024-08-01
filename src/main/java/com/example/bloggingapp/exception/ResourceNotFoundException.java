package com.example.bloggingapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String findName;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String findName, long fieldValue) {
        super(String.format("% not found with %s : %l",resourceName,findName,fieldValue));
        this.resourceName=resourceName;
        this.fieldValue=fieldValue;
        this.findName=findName;
    }
}
