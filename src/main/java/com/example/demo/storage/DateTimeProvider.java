package com.example.demo.storage;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class DateTimeProvider implements IDateTimeProvider {

    @Override
    public OffsetDateTime GetNow() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
