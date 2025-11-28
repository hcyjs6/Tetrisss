package com.comp2042.logic.speed;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DropSpeedControllerTest {

    private DropSpeedController dropSpeedController;

    @BeforeEach
    void setUp() {
        dropSpeedController = new DropSpeedController(null);
    }

    @Test
    void getTimeline() {
        assertNotNull(dropSpeedController.getTimeline());
    }
}
