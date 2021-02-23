package com.example.demo.storage;

import java.time.OffsetDateTime;

public interface IDateTimeProvider {

    public OffsetDateTime GetNow();
}

