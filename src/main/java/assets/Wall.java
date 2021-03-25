package assets;

import actor.Player;
import map.GameMap;

public class Wall {

//The lasercannon is attached to a wall and is therefore defined as a wall instead of a laser. The laser it self will
//deal damage while a wall is to only stop a players movement.

    /**
     * Brukerhistorier:
     * Som spiller vil jeg at spillere ikke skal kunne gå gjennom vegger, slik at spillebrettet blir mer variert.
     *
     * Akseptansekriterier:
     * Når en robot står inntil en vegg og beveger seg i veggens retning skal den ikke forflytte seg.
     *
     * Arbeidsoppgaver:
     * - Identifisere hvor veggene er på brettet og lagre disse som koordinater.
     * - Lage en metode som sjekker om en spiller er ved en vegg.
     * - En spiller skal ikke kunne bevege seg over en vegg.
     *
     *
     * - Skal spiller sjekke om den er nær en vegg, eller skal veggen sjekke om det er
     *   en spiller i retningen North, South, West og East?
     */

    public static final int wallDownRight       =  8;
    public static final int wallUpRight       = 16;
    public static final int wallUpLeft          = 26;
    public static final int wallDownLeft       = 32;
    public static final int wallRight = 23;
    public static final int wallDown          = 29;
    public static final int wallLeft            = 30;
    public static final int wallUp             = 31;
    public static final int laserWallDown       = 37;
    public static final int laserWallUp       = 45;
    public static final int laserWallRight      = 46;
    public static final int laserWallLeft      = 38;
    public static final int doubleLaserWallDown = 87;
    public static final int doubleLaserWallUp = 94;
    public static final int doubleLaserWallRight= 95;
    public static final int doubleLaserWallLeft= 93;

}
