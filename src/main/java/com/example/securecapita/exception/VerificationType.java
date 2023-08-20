package com.example.securecapita.exception;

public enum VerificationType
{
        ACCOUNT("ACCOUNT"),
        PASSWORD("PASSWORD");

    private String type;

    VerificationType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }
}
