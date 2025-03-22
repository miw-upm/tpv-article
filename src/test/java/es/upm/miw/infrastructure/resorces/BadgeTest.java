package es.upm.miw.infrastructure.resorces;

import es.upm.miw.infrastructure.resources.Badge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BadgeTest {

    @Test
    void testGenerateBadge() {
        String badge = new Badge().generateBadge("Heroku", "v2.2.0-SNAPSHOT");
        assertNotNull(badge);
        assertEquals("<svg", badge.substring(0, 4));
    }
}
