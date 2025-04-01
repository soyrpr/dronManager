package com.indra.dronmanager.model;

/**
 * Enum que representa las órdenes que un dron puede ejecutar.
 */
public enum Ordenes {
    /** Avanza una posición en la orientación actual. */
    MOVE_FORWARD,

    /** Gira 90 grados a la izquierda. */
    TURN_LEFT,

    /** Gira 90 grados a la derecha. */
    TURN_RIGHT
}
