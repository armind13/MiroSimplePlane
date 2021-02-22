package com.example.demo.storage;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public interface IDateTimeProvider {

    public OffsetDateTime GetNow();
}

