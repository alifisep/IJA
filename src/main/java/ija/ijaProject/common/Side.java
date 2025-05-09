/**
 * Soubor: src/main/java/ija.ijaProject/common/Side.java
 *
 * Popis:
 * Výčtový typ reprezentující strany v políčku GameNode.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package ija.ijaProject.common;

 /**Východní strana políčka.*/
 public enum Side {
     NORTH, EAST, SOUTH, WEST;
    /** Vraci Side otočeny o 90° po směru hodinových ručiček.*/
     public Side rotate() {
         return switch(this) {
             case NORTH -> EAST;
             case EAST  -> SOUTH;
             case SOUTH -> WEST;
             case WEST  -> NORTH;
         };
     }
 }
