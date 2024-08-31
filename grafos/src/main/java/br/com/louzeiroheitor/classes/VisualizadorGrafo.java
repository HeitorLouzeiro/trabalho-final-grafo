package br.com.louzeiroheitor.classes;

import java.util.ArrayList;

// Classes para acessa os valores do map.

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Importando as classes necessárias para a visualização do grafo
import javax.swing.JFrame;

// Classes do JGraphX para mostrar o grafo de forma visual circular
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class VisualizadorGrafo {
    private static Grafo grafo; // Referenciando o grafo

    public VisualizadorGrafo(Grafo grafo) {
        VisualizadorGrafo.grafo = grafo; // Inicializando o grafo no construtor
    }

    public void visualizarGrafo() {
        mxGraph graficoDoGrafo = new mxGraph(); // Criando um novo gráfico

        /* 
         * getDefaultParent(): retorna o objeto pai do gráfico, 
         * que é o objeto que contém todos os outros objetos do gráfico.
         */
        Object graficoPai = graficoDoGrafo.getDefaultParent(); // Obtendo o objeto pai do gráfico,aqui será onde os vértices e arestas serão adicionados

        /* 
         * getModel(): retorna o modelo do gráfico,
         * beginUpdate(): inicia a atualização do modelo
         */
        graficoDoGrafo.getModel().beginUpdate();

        try {
            // Mapeamento de vértices para os objetos visuais
            Map<Integer, Object> vertexMap = new HashMap<>();

            /* 
             * adjacencias.keySet(): retorna um conjunto de chaves (vértices) no mapa de adjacências
             * List<Integer> vertices: lista de vértices
             * keys(): retorna um conjunto de chaves no mapa de adjacências
             */
            List<Integer> vertices = new ArrayList<>(grafo.adjacencias.keySet());


            // Adicionar vértices ao gráfico
            for (int i = 0; i < vertices.size(); i++) {
                Integer vertice = vertices.get(i);

                // insertVertex(): adiciona um vértice ao gráfico
                Object referenciaDovalor = graficoDoGrafo.insertVertex(graficoPai, null, vertice, 0, 0, 50, 50);
                
                // put(): associa o vértice ao objeto visual
                vertexMap.put(vertice, referenciaDovalor);
            }

            // Adicionar arestas ao gráfico
            for (Map.Entry<Integer, List<Aresta>> ValorEntrada : grafo.adjacencias.entrySet()) {
            Integer origem = ValorEntrada.getKey();  // Obtém o vértice de origem
            List<Aresta> arestas = ValorEntrada.getValue(); // Obtém a lista de arestas
            for (int j = 0; j < arestas.size(); j++) {
                Aresta aresta = arestas.get(j); // Obtém a aresta
                Integer destino = aresta.destino; // Obtém o vértice de destino

                /* 
                 * insertEdge(): adiciona uma aresta ao gráfico
                 * graficoPai: objeto pai do gráfico
                 * null: objeto de referência para a aresta
                 * aresta.peso: peso da aresta
                 * vertexMap.get(origem): vértice de origem
                 * vertexMap.get(destino): vértice de destino
                 */
                graficoDoGrafo.insertEdge(graficoPai, null, aresta.peso, vertexMap.get(origem), vertexMap.get(destino));
            }
        } 
    }finally {
            graficoDoGrafo.getModel().endUpdate(); // Encerra a atualização do modelo
        }

        // Organizar os vértices em um layout circular
        mxCircleLayout formatoDoGrafico = new mxCircleLayout(graficoDoGrafo);
        formatoDoGrafico.execute(graficoDoGrafo.getDefaultParent());

        // Exibir o gráfico em um JFrame
        JFrame frame = new JFrame("Visualização do Grafo");
        mxGraphComponent componenteDoGrafico = new mxGraphComponent(graficoDoGrafo); // Componente do gráfico do JGraphX para exibir o gráfico
        frame.getContentPane().add(componenteDoGrafico);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
