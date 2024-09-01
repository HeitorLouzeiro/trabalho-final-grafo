package br.com.louzeiroheitor.classes;

import java.util.*;

class Grafo {
    Map<Integer, List<Aresta>> adjacencias;

    @SuppressWarnings("unused")
    private int numVertices;

    public Grafo() {
        /*
         * adjacencias: mapeia um vértice para uma lista de arestas que estão conectadas
         * a ele.
         * numVertices: número de vértices no grafo.
         */
        adjacencias = new HashMap<>();
        numVertices = 0;
    }

    public void inserirVertice(int id) {
        if (!adjacencias.containsKey(id)) {
            adjacencias.put(id, new ArrayList<>()); // Cria uma nova lista de adjacências para o vértice.
            numVertices++;
        }
    }

    public void inserirAresta(int origem, int destino, int peso) {
        adjacencias.get(origem).add(new Aresta(destino, peso));
        adjacencias.get(destino).add(new Aresta(origem, peso));
    }

    public void removerVertice(int id) {
        adjacencias.values().forEach(list -> list.removeIf(aresta -> aresta.destino == id));
        adjacencias.remove(id);
        numVertices--;
    }

    /*
     * Remove a aresta entre os vértices origem e destino.
     * removeIf: remove todos os elementos da lista que satisfazem a condição
     * especificada.
     */
    public void removerAresta(int origem, int destino) {
        adjacencias.get(origem).removeIf(aresta -> aresta.destino == destino);
        adjacencias.get(destino).removeIf(aresta -> aresta.destino == origem);
    }

    // BSF (Busca em Largura || Breadth-First Search || Caminho em amplitude)
    public String buscaLargura(int verticeInicial) {
        /*
         * BFS (Busca em Largura) é um algoritmo de busca em grafos que começa pelo
         * vértice raiz e
         * explora todos os vértices vizinhos.
         * Depois, para cada um desses vértices mais próximos, explora os seus vértices
         * vizinhos inexplorados e assim por diante.
         * Set<Integer> visitados: conjunto de vértices visitados
         * Queue<Integer> fila: fila de vértices a serem visitados (primeiro a entrar,
         * primeiro a sair)
         * StringBuilder sb: string que armazenará a saída do BFS
         * linkedList: implementação de uma fila em Java, implementa uma lista que
         * permite a inserção e
         * remoção de elementos em ambas as extremidades da lista
         */
        Set<Integer> verticiesVisitados = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();

        /*
         * Adiciona o vértice inicial na fila e marca como visitado.
         */
        fila.add(verticeInicial);
        verticiesVisitados.add(verticeInicial);

        StringBuilder valoresConcatenado = new StringBuilder("Busca em Largura: ");

        // System.out.println("Busca em Largura: ");
        while (!fila.isEmpty()) {
            /*
             * Poll remove o primeiro elemento da fila e o retorna.
             * O vértice atual é o primeiro da fila.
             * Imprime o vértice atual.
             */
            int verticeAtual = fila.poll();
            // System.out.print(verticeAtual + " - ");
            valoresConcatenado.append(verticeAtual);
            valoresConcatenado.append(" - ");

            /*
             * Para cada vértice adjacente ao vértice atual, se ele não foi visitado,
             * adiciona na fila e marca como visitado.
             * O vértice adjacente é adicionado na fila e marcado como visitado.
             * O loop continua até que todos os vértices tenham sido visitados.
             */
            for (int i = 0; i < adjacencias.get(verticeAtual).size(); i++) {
                Aresta aresta = adjacencias.get(verticeAtual).get(i);
                if (!verticiesVisitados.contains(aresta.destino)) {
                    verticiesVisitados.add(aresta.destino);
                    fila.add(aresta.destino);
                }
            }
        }
        return valoresConcatenado.toString();
    }

    public boolean verificarSeConexo() {
        if (adjacencias.isEmpty()) {
            return true; // Grafo vazio é considerado conexo
        }

        int verticeInicial = adjacencias.keySet().iterator().next(); // Pega qualquer vértice inicial
        String resultadoBFS = buscaLargura(verticeInicial); // Realiza a BFS

        // Remove o prefixo "Busca em Largura: " da string
        String[] verticesVisitados = resultadoBFS.substring(16).split(" - "); // Começa a substring a partir do índice
                                                                              // 16, ignorando o prefixo

        // Verifica se todos os vértices foram visitados
        return verticesVisitados.length == adjacencias.size();
    }

    // Método para calcular o grau de um vértice específico
    /* 
     * O grau de um vértice é o número de arestas conectadas a ele.
     * Se o vértice não for encontrado, retorna -1.
     * Se o vértice for encontrado, retorna o número de arestas conectadas a ele.
     * Se o vértice for encontrado, mas não tiver arestas conectadas a ele, retorna 0.
     */
    public int grauDeVerticie(int vertice) {
        if (adjacencias.containsKey(vertice)) {
            return adjacencias.get(vertice).size();
        } else {
            System.out.println("Vértice não encontrado.");
            return -1;
        }
    }

    // DFS (Busca em Profundidade || Depth-First Search || Caminho em profundidade)
    public String buscaProfundidade(int verticeInicial){
        /* 
         * DFS (Busca em Profundidade) é um algoritmo de busca em grafos que começa pelo vértice raiz e 
         * explora todos os vértices vizinhos.
         * Depois, para cada um desses vértices mais próximos, explora os seus vértices vizinhos inexplorados e assim por diante.
         * Set<Integer> visitados: conjunto de vértices visitados
         * Stack<Integer> pilha: pilha de vértices a serem visitados (último a entrar, primeiro a sair)
         * StringBuilder sb: string que armazenará a saída do DFS
         * 
         */
        Set<Integer> verticesVisitados = new HashSet<>();
        Stack<Integer> pilha = new Stack<>();

        StringBuilder valoresConcatenado = new StringBuilder("Caminho em Profundidade: ");

        // push adiciona um elemento no topo da pilha
        pilha.push(verticeInicial);
        verticesVisitados.add(verticeInicial);

        while (!pilha.isEmpty()) {
            int verticeAtual = pilha.pop(); // pop remove o elemento no topo da pilha e o retorna
            valoresConcatenado.append(verticeAtual);
            valoresConcatenado.append(" - ");

            /* 
             * Para cada vértice adjacente ao vértice atual, se ele não foi visitado,
             * adiciona na pilha e marca como visitado.
             * O vértice adjacente é adicionado na pilha e marcado como visitado.
             */
            for (int i = 0; i < adjacencias.get(verticeAtual).size(); i++) {

                // Aresta é um objeto que contém o destino e o peso da aresta
                Aresta aresta = adjacencias.get(verticeAtual).get(i);

                // Se o vértice adjacente não foi visitado, adiciona na pilha e marca como visitado
                if (!verticesVisitados.contains(aresta.destino)) {
                    verticesVisitados.add(aresta.destino);
                    pilha.push(aresta.destino);// push adiciona um elemento no topo da pilha
                }
            }
        }
        return valoresConcatenado.toString();
    }

    // Método para converter para matriz de adjacências
    /* 
     * A matriz de adjacências é uma matriz quadrada que representa um grafo.
     * Cada linha e coluna da matriz representa um vértice.
     * O valor da matriz é 1 se houver uma aresta entre os vértices correspondentes.
     * Caso contrário, o valor é 0.
     * É representado por uma matriz de tamanho n x n, onde n é o número de vértices.
     * que vai de n = 0 a n-1.
     */
    public int[][] matrizAdjacencias() {
        int n = adjacencias.size();
        int[][] matrizAdjacencia = new int[n][n];

        List<Integer> vertices = new ArrayList<>(adjacencias.keySet());
        Map<Integer, Integer> indiceVertice = new HashMap<>();

        // Atribuir um índice a cada vértice
        for (int i = 0; i < vertices.size(); i++) {
            indiceVertice.put(vertices.get(i), i);
        }

        // Preencher a matriz de adjacência
        for (int origem : adjacencias.keySet()) {
            for (Aresta aresta : adjacencias.get(origem)) {
                int i = indiceVertice.get(origem);
                int j = indiceVertice.get(aresta.destino);
                matrizAdjacencia[i][j] = aresta.peso;
            }
        }

        return matrizAdjacencia;
    }


}
