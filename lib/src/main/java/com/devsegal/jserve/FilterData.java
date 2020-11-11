package com.devsegal.jserve;

import java.io.BufferedReader;

public interface FilterData<T> {
    public boolean badData(T data, BufferedReader reader);
}
