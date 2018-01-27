package controller;

// Class that controls the arenas

import view.Arenas;

public class ArenasController {

    private Arenas arena;

    public ArenasController(Arenas arena) {
        this.arena = arena;
    }

    public Arenas getArena() { return this.arena; }
}
