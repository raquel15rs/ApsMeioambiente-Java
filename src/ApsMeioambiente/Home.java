package ApsMeioambiente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home extends JFrame {

    public Home() {
        setTitle("Trabalho sobre o meio ambiente");
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setContentPane(new PainelHome());
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}

class PainelHome extends JPanel {

    private final Color verdeEscuro = new Color(10, 55, 48);
    private final Color verdeMedio = new Color(24, 118, 82);
    private final Color verdeClaro = new Color(239, 253, 246);
    private final Color verdeHover = new Color(220, 246, 232);
    private final Color verdeTexto = new Color(30, 76, 58);

    public PainelHome() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(24, 32, 24, 32));

        JPanel container = new JPanel(new BorderLayout(0, 22));
        container.setOpaque(false);

        container.add(criarTopo(), BorderLayout.NORTH);
        container.add(criarCentro(), BorderLayout.CENTER);
        container.add(criarRodape(), BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(container, gbc);
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout(20, 10));
        topo.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Trabalho sobre o meio ambiente");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Educação ambiental, tecnologia e sustentabilidade urbana");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitulo.setForeground(new Color(215, 245, 230));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel integrantes = new JLabel("Raquel Ramos • Samuel Batista • Vitor Moreira • Brenda Melo");
        integrantes.setFont(new Font("Arial", Font.BOLD, 14));
        integrantes.setForeground(new Color(195, 235, 215));
        integrantes.setAlignmentX(Component.LEFT_ALIGNMENT);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);
        textos.add(Box.createVerticalStrut(8));
        textos.add(integrantes);

        JLabel tag = new JLabel("Projeto APS", SwingConstants.CENTER);
        tag.setFont(new Font("Arial", Font.BOLD, 14));
        tag.setForeground(new Color(20, 90, 62));
        tag.setOpaque(true);
        tag.setBackground(new Color(238, 252, 244));
        tag.setBorder(new EmptyBorder(10, 18, 10, 18));

        topo.add(textos, BorderLayout.CENTER);
        topo.add(tag, BorderLayout.EAST);

        return topo;
    }

    private JPanel criarCentro() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        HomeRoundedPanel card = new HomeRoundedPanel(new Color(250, 255, 252), 32);
        card.setLayout(new BorderLayout(24, 26));
        card.setBorder(new EmptyBorder(34, 38, 34, 38));

        JPanel textoTopo = new JPanel(new BorderLayout(0, 12));
        textoTopo.setOpaque(false);

        JLabel chamada = new JLabel("Escolha sua experiência ambiental");
        chamada.setFont(new Font("Arial", Font.BOLD, 30));
        chamada.setForeground(new Color(20, 85, 60));

        JTextArea descricao = new JTextArea(
                "Explore conteúdos sobre sustentabilidade, teste seus conhecimentos e enfrente " +
                "o desafio de salvar uma cidade em colapso ambiental."
        );
        descricao.setFont(new Font("Arial", Font.PLAIN, 17));
        descricao.setForeground(verdeTexto);
        descricao.setEditable(false);
        descricao.setFocusable(false);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setOpaque(false);

        textoTopo.add(chamada, BorderLayout.NORTH);
        textoTopo.add(descricao, BorderLayout.CENTER);

        JPanel opcoes = new JPanel(new GridBagLayout());
        opcoes.setOpaque(false);

        JButton btnJogo = criarCardBotao(
                "Salve a cidade",
                "Jogo estratégico",
                "Administre recursos, reduza impactos ambientais e salve a metrópole."
        );

        JButton btnQuiz = criarCardBotao(
                "Quiz Ambiental",
                "Teste seus conhecimentos",
                "Responda perguntas sobre meio ambiente, cidades e sustentabilidade."
        );

        JButton btnSobre = criarCardBotao(
                "Sobre o Meio Ambiente",
                "Conteúdo educativo",
                "Entenda os principais desafios ambientais das grandes cidades."
        );

        adicionarOpcao(opcoes, btnJogo, 0);
        adicionarOpcao(opcoes, btnQuiz, 1);
        adicionarOpcao(opcoes, btnSobre, 2);

        btnJogo.addActionListener(e -> abrirTela(new TelaJogo()));
        btnQuiz.addActionListener(e -> abrirTela(new TelaQuiz()));
        btnSobre.addActionListener(e -> abrirTela(new TelaSobre()));

        card.add(textoTopo, BorderLayout.NORTH);
        card.add(opcoes, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 20, 5, 20);

        centro.add(card, gbc);

        return centro;
    }

    private void adicionarOpcao(JPanel painel, JButton botao, int coluna) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = coluna;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, coluna == 0 ? 0 : 10, 0, coluna == 2 ? 0 : 10);

        painel.add(botao, gbc);
    }

    private JButton criarCardBotao(String titulo, String categoria, String descricao) {
        JButton botao = new JButton(
                "<html><center>" +
                "<span style='font-size:17px;'><b>" + titulo + "</b></span><br><br>" +
                "<span style='font-size:12px; color:#2f7d5c;'><b>" + categoria + "</b></span><br><br>" +
                "<span style='font-size:12px;'>" + descricao + "</span>" +
                "</center></html>"
        );

        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.setMinimumSize(new Dimension(180, 150));
        botao.setPreferredSize(new Dimension(240, 180));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBackground(verdeClaro);
        botao.setForeground(new Color(25, 82, 60));
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setVerticalAlignment(SwingConstants.CENTER);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 225, 205), 2),
                new EmptyBorder(18, 18, 18, 18)
        ));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(verdeHover);
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(80, 170, 120), 2),
                        new EmptyBorder(18, 18, 18, 18)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(verdeClaro);
                botao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190, 225, 205), 2),
                        new EmptyBorder(18, 18, 18, 18)
                ));
            }
        });

        return botao;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(16, 0));
        rodape.setOpaque(false);

        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(90, 36));
        btnSair.setFont(new Font("Arial", Font.BOLD, 13));
        btnSair.setBackground(Color.WHITE);
        btnSair.setForeground(new Color(30, 90, 60));
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> System.exit(0));

        JLabel texto = new JLabel("Java Swing • Educação Ambiental nas Metrópoles", SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.BOLD, 14));
        texto.setForeground(new Color(225, 255, 235));

        rodape.add(btnSair, BorderLayout.WEST);
        rodape.add(texto, BorderLayout.CENTER);

        return rodape;
    }

    private void abrirTela(JFrame novaTela) {
        JFrame janelaAtual = (JFrame) SwingUtilities.getWindowAncestor(this);
        janelaAtual.dispose();
        novaTela.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint fundo = new GradientPaint(
                0, 0, verdeEscuro,
                getWidth(), getHeight(), verdeMedio
        );

        g2.setPaint(fundo);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(255, 255, 255, 24));
        g2.fillOval(-130, -100, 330, 330);
        g2.fillOval(getWidth() - 230, 75, 160, 160);
        g2.fillOval(getWidth() - 270, getHeight() - 240, 360, 360);

        g2.setColor(new Color(255, 255, 255, 15));
        g2.fillRoundRect(70, getHeight() - 150, 260, 80, 40, 40);
        g2.fillRoundRect(getWidth() - 390, 145, 280, 70, 35, 35);

        g2.dispose();
    }
}

class HomeRoundedPanel extends JPanel {

    private final Color cor;
    private final int arco;

    public HomeRoundedPanel(Color cor, int arco) {
        this.cor = cor;
        this.arco = arco;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 35));
        g2.fillRoundRect(8, 10, getWidth() - 16, getHeight() - 16, arco, arco);

        g2.setColor(cor);
        g2.fillRoundRect(0, 0, getWidth() - 16, getHeight() - 16, arco, arco);

        g2.dispose();

        super.paintComponent(g);
    }
}