package com.vitech.moodfeed;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class SmallTest {
}
