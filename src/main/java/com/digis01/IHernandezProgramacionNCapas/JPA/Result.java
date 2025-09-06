package com.digis01.IHernandezProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class Result 
{
    public boolean correct;
    public String errorMessage;
    public Exception ex;
    public Object object;
    public List<Object> objects;
    @JsonIgnore
    public int status;
}

