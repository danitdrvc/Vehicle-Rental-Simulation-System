package org.etf.unibl.danilo_todorovic_1156_22_pj.util;

import java.time.LocalDateTime;

/**
 * Represents a malfunction or breakdown event for a vehicle or equipment.
 */
public class Kvar {
    private final String type;            // Type of malfunction
    private final String id;              // Identifier of the vehicle or equipment
    private final LocalDateTime dateTime; // Date and time of the malfunction
    private final String description;     // Description of the malfunction

    /**
     * Constructs a Kvar (malfunction) object with the specified details.
     *
     * @param type        The type of malfunction (e.g., mechanical, electrical).
     * @param id          The identifier of the vehicle or equipment associated with the malfunction.
     * @param dateTime    The date and time when the malfunction occurred.
     * @param description A description of the malfunction.
     */
    public Kvar(String type, String id, LocalDateTime dateTime, String description) {
        this.type = type;
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
    }

    /**
     * Returns the type of malfunction.
     *
     * @return The type of malfunction.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the identifier of the vehicle or equipment associated with the malfunction.
     *
     * @return The identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the date and time when the malfunction occurred.
     *
     * @return The date and time of the malfunction.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the description of the malfunction.
     *
     * @return The description of the malfunction.
     */
    public String getDescription() {
        return description;
    }
}
