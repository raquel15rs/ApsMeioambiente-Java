package ApsMeioambiente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaSobre extends JFrame {

    public TelaSobre() {
        setTitle("EcoMetrópole - Sobre o Meio Ambiente");
        setSize(1100, 720);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setContentPane(new PainelSobre());
        setVisible(true);
    }
}

class PainelSobre extends JPanel {

    private final Color verdeEscuro = new Color(10, 55, 48);
    private final Color verdeMedio = new Color(24, 118, 82);
    private final Color verdeClaro = new Color(242, 253, 247);
    private final Color verdeTexto = new Color(42, 72, 58);
    private final Color verdeTitulo = new Color(20, 88, 62);
    private final Color verdeBorda = new Color(190, 225, 205);

    public PainelSobre() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(24, 32, 24, 32));

        JPanel container = new JPanel(new BorderLayout(0, 20));
        container.setOpaque(false);

        container.add(criarTopo(), BorderLayout.NORTH);
        container.add(criarConteudoPrincipal(), BorderLayout.CENTER);
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

        JLabel titulo = new JLabel("Educação Ambiental");
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sustentabilidade, cidadania, tecnologia e qualidade de vida urbana");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 19));
        subtitulo.setForeground(new Color(215, 245, 230));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descricao = new JLabel("Conteúdo educativo do projeto EcoMetrópole");
        descricao.setFont(new Font("Arial", Font.BOLD, 14));
        descricao.setForeground(new Color(195, 235, 215));
        descricao.setAlignmentX(Component.LEFT_ALIGNMENT);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);
        textos.add(Box.createVerticalStrut(8));
        textos.add(descricao);

        JLabel tag = new JLabel("Sobre", SwingConstants.CENTER);
        tag.setFont(new Font("Arial", Font.BOLD, 14));
        tag.setForeground(new Color(20, 90, 62));
        tag.setOpaque(true);
        tag.setBackground(new Color(238, 252, 244));
        tag.setBorder(new EmptyBorder(10, 20, 10, 20));

        topo.add(textos, BorderLayout.CENTER);
        topo.add(tag, BorderLayout.EAST);

        return topo;
    }

    private JPanel criarConteudoPrincipal() {
        SobreRoundedPanel cardPrincipal = new SobreRoundedPanel(new Color(250, 255, 252), 32);
        cardPrincipal.setLayout(new BorderLayout(0, 18));
        cardPrincipal.setBorder(new EmptyBorder(26, 28, 26, 28));

        JLabel chamada = new JLabel("Entenda os principais desafios ambientais das metrópoles");
        chamada.setFont(new Font("Arial", Font.BOLD, 25));
        chamada.setForeground(verdeTitulo);

        JTabbedPane abas = criarAbas();

        cardPrincipal.add(chamada, BorderLayout.NORTH);
        cardPrincipal.add(abas, BorderLayout.CENTER);

        return cardPrincipal;
    }

    private JTabbedPane criarAbas() {
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Arial", Font.BOLD, 14));
        abas.setBackground(Color.WHITE);
        abas.setForeground(verdeTitulo);
        abas.setBorder(new EmptyBorder(6, 0, 0, 0));

        abas.addTab("Contexto", criarScroll(criarAbaContexto()));
        abas.addTab("Problemas Urbanos", criarScroll(criarAbaProblemas()));
        abas.addTab("Soluções", criarScroll(criarAbaSolucoes()));
        abas.addTab("Projeto", criarScroll(criarAbaProjeto()));

        return abas;
    }

    private JPanel criarAbaContexto() {
        JPanel painel = criarPainelConteudo();

        painel.add(criarBlocoTexto(
                "O que é educação ambiental?",
                "Educação ambiental é o processo de conscientização sobre a relação entre sociedade, natureza e qualidade de vida. Ela ajuda as pessoas a entenderem como pequenas decisões do dia a dia, como economizar água, separar lixo e usar transporte coletivo, podem reduzir impactos ambientais."
        ));

        painel.add(criarBlocoTexto(
                "Por que falar sobre metrópoles?",
                "As grandes cidades concentram pessoas, veículos, indústrias, consumo de energia, produção de lixo e serviços. Por isso, os problemas ambientais aparecem com mais intensidade e afetam diretamente a saúde, a mobilidade, o conforto térmico e o bem-estar da população."
        ));

        painel.add(criarBlocoTexto(
                "Relação com a cidadania",
                "Cuidar do meio ambiente urbano não depende apenas do governo. Também envolve participação da população, consumo consciente, descarte correto de resíduos e cobrança por políticas públicas sustentáveis."
        ));

        painel.add(Box.createVerticalGlue());
        return painel;
    }

    private JPanel criarAbaProblemas() {
        JPanel painel = criarPainelConteudo();

        painel.add(criarCard("Poluição do ar",
                "Causada principalmente por veículos, indústrias e queima de combustíveis. Prejudica a saúde respiratória, piora a qualidade de vida e contribui para mudanças climáticas."));

        painel.add(criarCard("Lixo urbano",
                "O descarte incorreto polui ruas, rios e solos. Também pode entupir bueiros, causar enchentes, atrair animais transmissores de doenças e aumentar custos públicos."));

        painel.add(criarCard("Ilhas de calor",
                "O excesso de concreto, asfalto e prédios, somado à falta de árvores, eleva a temperatura da cidade e aumenta o consumo de energia."));

        painel.add(criarCard("Falta de áreas verdes",
                "A redução de árvores e parques diminui a qualidade do ar, reduz espaços de lazer, prejudica a drenagem da chuva e torna a cidade menos saudável."));

        painel.add(criarCard("Mobilidade urbana",
                "O excesso de carros aumenta congestionamentos, poluição sonora e emissão de gases. Transporte público eficiente, ciclovias e caminhabilidade ajudam a reduzir esse impacto."));

        painel.add(Box.createVerticalGlue());
        return painel;
    }

    private JPanel criarAbaSolucoes() {
        JPanel painel = criarPainelConteudo();

        painel.add(criarCard("Reciclagem e coleta seletiva",
                "Separar resíduos recicláveis reduz a quantidade de lixo enviada aos aterros, economiza recursos naturais e incentiva o reaproveitamento de materiais."));

        painel.add(criarCard("Transporte público de qualidade",
                "Ônibus, metrô, trens e ciclovias reduzem a quantidade de veículos particulares nas ruas, diminuindo congestionamentos e poluição."));

        painel.add(criarCard("Energia limpa",
                "Fontes como energia solar e eólica reduzem a dependência de combustíveis fósseis e ajudam no combate às mudanças climáticas."));

        painel.add(criarCard("Áreas verdes urbanas",
                "Árvores, parques e jardins reduzem calor, melhoram o ar, absorvem água da chuva, favorecem o lazer e tornam a cidade mais agradável."));

        painel.add(criarCard("Consumo consciente",
                "Economizar água, evitar desperdício de energia, reutilizar materiais e comprar apenas o necessário são atitudes simples com grande impacto coletivo."));

        painel.add(Box.createVerticalGlue());
        return painel;
    }

    private JPanel criarAbaProjeto() {
        JPanel painel = criarPainelConteudo();

        painel.add(criarBlocoTexto(
                "Como o jogo se relaciona com o tema?",
                "No jogo Cidade em Colapso, o jogador administra uma metrópole em crise ambiental. Cada carta representa uma decisão sustentável, como plantar árvores, investir em reciclagem, melhorar o transporte público ou utilizar energia limpa."
        ));

        painel.add(criarBlocoTexto(
                "O que o jogador aprende?",
                "As decisões alteram indicadores como poluição, lixo urbano, áreas verdes, energia limpa, felicidade da população e recursos. Assim, o jogo mostra que sustentabilidade exige equilíbrio entre economia, sociedade e natureza."
        ));

        painel.add(criarTituloSecao("Interdisciplinaridade"));

        painel.add(criarCard("Programação",
                "Uso da linguagem Java, interface gráfica Swing, eventos de clique, classes, objetos, lógica de jogo e tratamento de ações."));

        painel.add(criarCard("Meio ambiente",
                "Discussão sobre poluição, reciclagem, energia limpa, mobilidade urbana, áreas verdes e sustentabilidade."));

        painel.add(criarCard("Matemática",
                "Uso de pontuação, porcentagens, indicadores, recursos, custos e efeitos numéricos das decisões."));

        painel.add(criarCard("Cidadania",
                "Reflexão sobre responsabilidade social, participação coletiva e escolhas conscientes para melhorar a cidade."));

        painel.add(Box.createVerticalGlue());
        return painel;
    }

    private JPanel criarPainelConteudo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(verdeClaro);
        painel.setBorder(new EmptyBorder(24, 26, 24, 26));
        return painel;
    }

    private JScrollPane criarScroll(JPanel painel) {
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 240, 230), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.setBackground(verdeClaro);
        scroll.getViewport().setBackground(verdeClaro);
        return scroll;
    }

    private JPanel criarBlocoTexto(String titulo, String texto) {
        JPanel bloco = new JPanel(new BorderLayout(0, 10));
        bloco.setOpaque(false);
        bloco.setBorder(new EmptyBorder(4, 0, 22, 0));
        bloco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = criarTituloSecao(titulo);
        JTextArea areaTexto = criarTexto(texto);

        bloco.add(lblTitulo, BorderLayout.NORTH);
        bloco.add(areaTexto, BorderLayout.CENTER);

        return bloco;
    }

    private JLabel criarTituloSecao(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 21));
        label.setForeground(verdeTitulo);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextArea criarTexto(String conteudo) {
        JTextArea texto = new JTextArea(conteudo);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setForeground(verdeTexto);
        texto.setEditable(false);
        texto.setFocusable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setOpaque(false);
        texto.setBorder(null);
        texto.setAlignmentX(Component.LEFT_ALIGNMENT);
        return texto;
    }

    private JPanel criarCard(String titulo, String descricao) {
        SobreRoundedPanel card = new SobreRoundedPanel(Color.WHITE, 20);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(verdeTitulo);

        JTextArea txtDescricao = criarTexto(descricao);
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 15));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(txtDescricao, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 0, 14, 0));
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(card, BorderLayout.CENTER);

        return container;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(16, 0));
        rodape.setOpaque(false);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(100, 36));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(30, 90, 60));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnVoltar.setBackground(new Color(220, 246, 232));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnVoltar.setBackground(Color.WHITE);
            }
        });

        btnVoltar.addActionListener(e -> {
            JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
            janela.dispose();
            new Home();
        });

        JLabel textoRodape = new JLabel(
                "EcoMetrópole • Educação Ambiental • Java Swing • APS",
                SwingConstants.CENTER
        );
        textoRodape.setFont(new Font("Arial", Font.BOLD, 14));
        textoRodape.setForeground(new Color(225, 255, 235));

        rodape.add(btnVoltar, BorderLayout.WEST);
        rodape.add(textoRodape, BorderLayout.CENTER);

        return rodape;
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

        g2.setColor(new Color(255, 255, 255, 22));
        g2.fillOval(-120, -90, 320, 320);
        g2.fillOval(getWidth() - 250, getHeight() - 230, 360, 360);
        g2.fillOval(getWidth() - 210, 80, 150, 150);

        g2.dispose();
    }
}

class SobreRoundedPanel extends JPanel {

    private final Color cor;
    private final int arco;

    public SobreRoundedPanel(Color cor, int arco) {
        this.cor = cor;
        this.arco = arco;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 28));
        g2.fillRoundRect(5, 7, getWidth() - 10, getHeight() - 10, arco, arco);

        g2.setColor(cor);
        g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, arco, arco);

        g2.dispose();
        super.paintComponent(g);
    }
}