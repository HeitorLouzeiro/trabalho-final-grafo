package br.com.louzeiroheitor.classes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Main {

    private static Grafo grafo = new Grafo();
    private JFrame frame;

    public static void main(String[] args) {
        Main main = new Main();
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

        grafo.inserirAresta(1, 2, 1);
        grafo.inserirAresta(1, 3, 1);
        grafo.inserirAresta(1, 4, 1);
        grafo.inserirAresta(2, 5, 1);
        grafo.inserirAresta(2, 6, 1);
        grafo.inserirAresta(3, 7, 1);
        grafo.inserirAresta(3, 8, 1);
        grafo.inserirAresta(4, 9, 1);
        grafo.inserirAresta(4, 10, 1);

        frame = new JFrame("Menu de Opções do Grafo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        criarBotao("Inserir Vértice", KeyEvent.VK_F1, inserirVerticeMenu);
        criarBotao("Inserir Aresta", KeyEvent.VK_F2, inserirArestaMenu);
        criarBotao("Remover Vértice", KeyEvent.VK_F3, removerVerticeMenu);
        criarBotao("Remover Aresta", KeyEvent.VK_F4, removerArestaMenu);
        criarBotao("Visualizar Grafo", KeyEvent.VK_F5, visualizarGrafoMenu);
        criarBotao("Busca em Largura (BFS)", KeyEvent.VK_F6, buscaLarguraMenu);
        criarBotao("Verificar Grafo é conexo", KeyEvent.VK_F7, verificarSeConexoMenu);
        criarBotao("Mostrar Grau de um Verticie.", KeyEvent.VK_F8, grauDeVerticieMenu);
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

    private Runnable buscaLarguraMenu = () -> {
        int verticeInicial = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o vértice inicial para o BFS:"));
        String resultadoBFS = grafo.buscaLargura(verticeInicial); 
        JOptionPane.showMessageDialog(frame, resultadoBFS);
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


    private Runnable sair = () -> System.exit(0);
}