package br.com.louzeiroheitor.classes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Tela {

    private static Grafo grafo = new Grafo();
    private JFrame frame;

    public static void main(String[] args) {
        Tela main = new Tela();
        main.createAndShowGUI();
    }

    private void createAndShowGUI() {

        grafo.inserirVertice(1);
        grafo.inserirVertice(2);
        grafo.inserirVertice(3);
        grafo.inserirVertice(4);
        grafo.inserirVertice(5);
        grafo.inserirVertice(6);
        grafo.inserirVertice(7);
        grafo.inserirVertice(8);
        grafo.inserirVertice(9);
        grafo.inserirVertice(10);

        grafo.inserirAresta(1, 2, 2);
        grafo.inserirAresta(1, 3, 1);
        grafo.inserirAresta(1, 4, 4);
        grafo.inserirAresta(2, 5, 3);
        grafo.inserirAresta(2, 6, 1);
        grafo.inserirAresta(3, 7, 6);
        grafo.inserirAresta(3, 8, 1);
        grafo.inserirAresta(4, 9, 5);
        grafo.inserirAresta(4, 10, 9);

        frame = new JFrame("Menu de Opções do Grafo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        criarBotao("Inserir Vértice", KeyEvent.VK_F1, inserirVerticeMenu);
        criarBotao("Inserir Aresta", KeyEvent.VK_F2, inserirArestaMenu);
        criarBotao("Remover Vértice", KeyEvent.VK_F3, removerVerticeMenu);
        criarBotao("Remover Aresta", KeyEvent.VK_F4, removerArestaMenu);
        criarBotao("Visualizar Grafo", KeyEvent.VK_F5, visualizarGrafoMenu);
        criarBotao("Verificar Grafo é conexo", KeyEvent.VK_F6, verificarSeConexoMenu);
        criarBotao("Mostrar Grau de um Verticie.", KeyEvent.VK_F7, grauDeVerticieMenu);
        criarBotao("Busca em Largura (BFS)", KeyEvent.VK_F8, buscaLarguraMenu);
        criarBotao("Busca em Profundidade (DFS)", KeyEvent.VK_F9, buscaProfundidadeMenu);
        criarBotao("Matriz de adjacencia", KeyEvent.VK_F10, matrizAdjacenciasMenu);
        criarBotao("Sair", KeyEvent.VK_0, sair);
        frame.setLocationRelativeTo(null);

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void criarBotao(String texto, int tecla, Runnable acao) {
        JButton botao = new JButton(texto + " (" + KeyEvent.getKeyText(tecla) + ")");

        // Adiciona o ActionListener ao botão
        botao.addActionListener(e -> acao.run());

        // Adiciona o atalho de teclado
        botao.registerKeyboardAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acao.run();
            }
        }, KeyStroke.getKeyStroke(tecla, 0), JButton.WHEN_IN_FOCUSED_WINDOW);

        frame.add(botao);
    }

    // Runnables para cada ação do botão, que serão executadas ao clicar no botão
    private Runnable inserirVerticeMenu = () -> {
        int valor = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o numero do vértice:"));
        grafo.inserirVertice(valor);
        JOptionPane.showMessageDialog(frame, "Vértice inserido com sucesso!");
    };

    private Runnable inserirArestaMenu = () -> {
        int origem = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o numero da origem:"));
        int destino = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o numero do destino:"));
        int peso = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o peso da aresta:"));
        grafo.inserirAresta(origem, destino, peso);
        JOptionPane.showMessageDialog(frame, "Aresta inserida com sucesso!");
    };

    private Runnable removerVerticeMenu = () -> {
        int valor = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o numero do vértice a ser removido:"));
        grafo.removerVertice(valor);
        JOptionPane.showMessageDialog(frame, "Vértice removido com sucesso!");
    };

    private Runnable removerArestaMenu = () -> {
        int origem = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o ID da origem:"));
        int destino = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o ID do destino:"));
        grafo.removerAresta(origem, destino);
        JOptionPane.showMessageDialog(frame, "Aresta removida com sucesso!");
    };

    private Runnable visualizarGrafoMenu = () -> {
        VisualizadorGrafo visualizador = new VisualizadorGrafo(grafo);
        visualizador.visualizarGrafo();
    };

    private Runnable verificarSeConexoMenu = () -> {
        boolean conexo = grafo.verificarSeConexo();
        JOptionPane.showMessageDialog(frame, "O grafo é conexo? " + (conexo ? "Sim" : "Não"));

    };

    private Runnable grauDeVerticieMenu = () -> {
        int vertice = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o vértice para verificar o grau:"));
        int grau = grafo.grauDeVerticie(vertice);
        JOptionPane.showMessageDialog(frame, "O grau do vértice " + vertice + " é " + grau);
    };

    private Runnable buscaLarguraMenu = () -> {
        int verticeInicial = Integer
                .parseInt(JOptionPane.showInputDialog(frame, "Digite o vértice inicial para o BFS:"));
        String resultadoBFS = grafo.buscaLargura(verticeInicial);
        JOptionPane.showMessageDialog(frame, resultadoBFS);
    };

    private Runnable buscaProfundidadeMenu = () -> {
        int verticeInicial = Integer
                .parseInt(JOptionPane.showInputDialog(frame, "Digite o vértice inicial para o DFS:"));
        String resultadoDFS = grafo.buscaProfundidade(verticeInicial);
        JOptionPane.showMessageDialog(frame, resultadoDFS);
    };

    private Runnable matrizAdjacenciasMenu = () -> {
        int[][] matriz = grafo.matrizAdjacencias(); // Obtém a matriz de adjacências do grafo
        String msg = "Matriz de Adjacência:\n";

        // Lista de vértices para exibir na matriz
        List<Integer> vertices = new ArrayList<>(grafo.adjacencias.keySet());

        // Cabeçalho da matriz (números dos vértices)
        msg += "  | ";
        for (int j = 0; j < vertices.size(); j++) {
            msg += " " + vertices.get(j) + " ";
        }
        msg += "\n";

        // Linhas da matriz com valores dos vértices
        for (int i = 0; i < vertices.size(); i++) {
            msg += vertices.get(i) + "|"; // Exibe o vértice da linha atual
            for (int j = 0; j < vertices.size(); j++) {
                msg += " " + matriz[i][j] + " ";
            }
            msg += "\n";
        }
        JOptionPane.showMessageDialog(frame, msg); // Exibe a matriz de adjacências em uma caixa de diálogo
    };

    private Runnable sair = () -> System.exit(0);
}