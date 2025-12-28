package com.minecraft.launcher;

/**
 * Launcher wrapper pour JavaFX dans un JAR fat.
 * Cette classe contourne les vérifications de module JavaFX.
 */
public class Launcher {
    public static void main(String[] args) {
        // Lancer l'application JavaFX via la classe Main
        Main.main(args);
    }
}
