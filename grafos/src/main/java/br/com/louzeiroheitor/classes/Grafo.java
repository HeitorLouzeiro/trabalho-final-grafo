package br.com.louzeiroheitor.classes;

import java.util.*;

class Grafo {
    private Map<Integer, List<Aresta>> adjacencias;
    
    @SuppressWarnings("unused")
    private int numVertices;

    public Grafo() {
        adjacencias = new HashMap<>();
        numVertices = 0;
    }

    public void inserirVertice(int id) {
        if (!adjacencias.containsKey(id)) {
            adjacencias.put(id, new ArrayList<>());
            numVertices++;
        }
    }

    public void inserirAresta(int origem, int destino, int peso) {
        adjacencias.get(origem).add(new Aresta(destino, peso));
        adjacencias.get(destino).add(new Aresta(origem, peso)); // Como o grafo é não direcionado
    }

    public void removerVertice(int id) {
        adjacencias.values().forEach(list -> list.removeIf(aresta -> aresta.destino == id));
        adjacencias.remove(id);
        numVertices--;
    }

    public void removerAresta(int origem, int destino) {
        adjacencias.get(origem).removeIf(aresta -> aresta.destino == destino);
        adjacencias.get(destino).removeIf(aresta -> aresta.destino == origem);
    }

    // Outros métodos para as funcionalidades restantes...
}
