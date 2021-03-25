package assets;

import actor.Player;
import map.GameMap;

public class Wall {

    /**
     * Brukerhistorier:
     * Som spiller vil jeg at spillere ikke skal kunne gå gjennom vegger, slik at spillebrettet blir mer variert.
     * <p>
     * Akseptansekriterier:
     * Når en robot står inntil en vegg og beveger seg i veggens retning skal den ikke forflytte seg.
     * <p>
     * Arbeidsoppgaver:
     * - Identifisere hvor veggene er på brettet og lagre disse som koordinater.
     * - Lage en metode som sjekker om en spiller er ved en vegg.
     * - En spiller skal ikke kunne bevege seg over en vegg.
     * <p>
     * <p>
     * - Skal spiller sjekke om den er nær en vegg, eller skal veggen sjekke om det er
     * en spiller i retningen North, South, West og East?
     */

    public static final int wallRight = 23, wallDown = 29, wallLeft = 30, wallUp = 31,
            wallDownRight = 8, wallUpRight = 16, wallUpLeft = 26, wallDownLeft = 32,
            laserWallDown = 37, laserWallUp = 45, laserWallRight = 46, laserWallLeft = 38,
            doubleLaserWallDown = 87, doubleLaserWallUp = 94, doubleLaserWallRight = 95, doubleLaserWallLeft = 93;


}
