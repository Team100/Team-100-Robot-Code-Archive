package org.usfirst.frc100.Mk3.subsystems;

/**
 * Allows us to enable/disable all subsystems at once.
 */
public interface SubsystemControl {

    void disable();

    void enable();
}