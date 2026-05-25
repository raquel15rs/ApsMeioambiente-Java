package ApsMeioambiente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class TelaQuiz extends JFrame {

    public TelaQuiz() {
        setTitle("EcoMetrópole - Quiz Ambiental");
        setSize(1100, 720);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setContentPane(new PainelQuiz());
        setVisible(true);
    }
}

class PainelQuiz extends JPanel {

    private final ArrayList<PerguntaQuiz> perguntas = new ArrayList<>();

    private int perguntaAtual = 0;
    private int pontos = 0;

    private JLabel lblProgresso;
    private JLabel lblPontuacao;
    private JLabel lblQuestao;
    private JLabel lblPergunta;
    private JButton[] botoes;
    private JProgressBar barraProgresso;

    private final Color verdeEscuro = new Color(10, 55, 48);
    private final Color verdeMedio = new Color(24, 118, 82);
    private final Color verdeClaro = new Color(239, 253, 246);
    private final Color verdeHover = new Color(220, 246, 232);
    private final Color verdeTitulo = new Color(20, 88, 62);
    private final Color verdeTexto = new Color(42, 72, 58);
    private final Color verdeBorda = new Color(185, 220, 200);

    public PainelQuiz() {
        carregarPerguntas();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(22, 32, 22, 32));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarCardCentral(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);

        mostrarPergunta();
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Quiz Ambiental");
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Teste seus conhecimentos sobre sustentabilidade urbana");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 19));
        subtitulo.setForeground(new Color(215, 245, 230));

        JLabel descricao = new JLabel("Cada resposta correta vale 1 ponto");
        descricao.setFont(new Font("Arial", Font.BOLD, 14));
        descricao.setForeground(new Color(195, 235, 215));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);
        textos.add(Box.createVerticalStrut(8));
        textos.add(descricao);

        JLabel tag = new JLabel("Quiz", SwingConstants.CENTER);
        tag.setFont(new Font("Arial", Font.BOLD, 14));
        tag.setForeground(verdeTitulo);
        tag.setOpaque(true);
        tag.setBackground(new Color(238, 252, 244));
        tag.setBorder(new EmptyBorder(12, 26, 12, 26));

        topo.add(textos, BorderLayout.CENTER);
        topo.add(tag, BorderLayout.EAST);

        return topo;
    }

    private JPanel criarCardCentral() {
        QuizRoundedPanel card = new QuizRoundedPanel(new Color(250, 255, 252), 32);
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 34, 24, 34));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 16, 0);
        card.add(criarAreaInformacoes(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.42;
        gbc.insets = new Insets(0, 0, 14, 0);
        card.add(criarAreaPergunta(), gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.58;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(criarAreaAlternativas(), gbc);

        return card;
    }

    private JPanel criarAreaInformacoes() {
        JPanel painel = new JPanel(new BorderLayout(18, 0));
        painel.setOpaque(false);

        lblProgresso = criarBadge();
        lblPontuacao = criarBadge();

        barraProgresso = new JProgressBar(0, perguntas.size());
        barraProgresso.setValue(0);
        barraProgresso.setPreferredSize(new Dimension(100, 14));
        barraProgresso.setStringPainted(false);
        barraProgresso.setBorderPainted(false);
        barraProgresso.setForeground(new Color(38, 172, 96));
        barraProgresso.setBackground(new Color(225, 235, 228));

        painel.add(lblProgresso, BorderLayout.WEST);
        painel.add(barraProgresso, BorderLayout.CENTER);
        painel.add(lblPontuacao, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarAreaPergunta() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(2, 0, 2, 0));

        lblQuestao = new JLabel();
        lblQuestao.setFont(new Font("Arial", Font.BOLD, 18));
        lblQuestao.setForeground(verdeTitulo);

        lblPergunta = new JLabel();
        lblPergunta.setFont(new Font("Arial", Font.BOLD, 22));
        lblPergunta.setForeground(verdeTexto);
        lblPergunta.setVerticalAlignment(SwingConstants.TOP);
        lblPergunta.setBorder(new EmptyBorder(8, 0, 8, 0));

        painel.add(lblQuestao, BorderLayout.NORTH);
        painel.add(lblPergunta, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarAreaAlternativas() {
        JPanel painel = new JPanel(new GridLayout(4, 1, 0, 10));
        painel.setOpaque(false);
        painel.setMinimumSize(new Dimension(100, 230));
        painel.setPreferredSize(new Dimension(100, 250));

        botoes = new JButton[4];

        for (int i = 0; i < botoes.length; i++) {
            botoes[i] = criarBotaoAlternativa();
            final int resposta = i;
            botoes[i].addActionListener(e -> responder(resposta));
            painel.add(botoes[i]);
        }

        return painel;
    }

    private JLabel criarBadge() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(165, 42));
        label.setOpaque(true);
        label.setBackground(verdeClaro);
        label.setForeground(verdeTitulo);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JButton criarBotaoAlternativa() {
        JButton botao = new JButton();
        botao.setFont(new Font("Arial", Font.BOLD, 15));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBackground(verdeClaro);
        botao.setForeground(new Color(30, 82, 60));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setVerticalAlignment(SwingConstants.CENTER);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(verdeBorda, 2),
                new EmptyBorder(12, 18, 12, 18)
        ));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (botao.isEnabled()) {
                    botao.setBackground(verdeHover);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (botao.isEnabled()) {
                    botao.setBackground(verdeClaro);
                }
            }
        });

        return botao;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(16, 0));
        rodape.setOpaque(false);
        rodape.setBorder(new EmptyBorder(18, 0, 0, 0));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(100, 36));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(30, 90, 60));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar.addActionListener(e -> {
            JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
            janela.dispose();
            new Home();
        });

        JLabel dica = new JLabel("Leia com atenção e escolha a alternativa mais sustentável.", SwingConstants.CENTER);
        dica.setFont(new Font("Arial", Font.BOLD, 14));
        dica.setForeground(new Color(225, 255, 235));

        rodape.add(btnVoltar, BorderLayout.WEST);
        rodape.add(dica, BorderLayout.CENTER);

        return rodape;
    }

    private void carregarPerguntas() {
        perguntas.add(new PerguntaQuiz(
                "Em uma grande metrópole, qual medida ajuda mais a reduzir a emissão de gases poluentes no transporte diário?",
                new String[]{
                        "Aumentar o uso de carros individuais",
                        "Priorizar transporte público e ciclovias",
                        "Construir mais estacionamentos no centro",
                        "Incentivar veículos antigos nas ruas"
                },
                1
        ));

        perguntas.add(new PerguntaQuiz(
                "Qual é uma consequência comum do descarte incorreto de lixo em áreas urbanas?",
                new String[]{
                        "Redução de enchentes",
                        "Melhoria da qualidade da água",
                        "Entupimento de bueiros e poluição de rios",
                        "Aumento automático da reciclagem"
                },
                2
        ));

        perguntas.add(new PerguntaQuiz(
                "Por que as áreas verdes são importantes para a qualidade de vida nas cidades?",
                new String[]{
                        "Aumentam o calor urbano",
                        "Reduzem temperatura, melhoram o ar e absorvem água da chuva",
                        "Impedem qualquer crescimento populacional",
                        "Eliminam totalmente a necessidade de transporte"
                },
                1
        ));

        perguntas.add(new PerguntaQuiz(
                "Qual alternativa representa uma fonte de energia limpa?",
                new String[]{
                        "Carvão mineral",
                        "Óleo diesel",
                        "Energia solar",
                        "Gasolina"
                },
                2
        ));

        perguntas.add(new PerguntaQuiz(
                "A reciclagem contribui para a sustentabilidade porque:",
                new String[]{
                        "Aumenta a quantidade de resíduos nos aterros",
                        "Evita qualquer necessidade de consumo",
                        "Reaproveita materiais e reduz a extração de recursos naturais",
                        "Impede a separação correta do lixo"
                },
                2
        ));

        perguntas.add(new PerguntaQuiz(
                "O que são ilhas de calor urbanas?",
                new String[]{
                        "Áreas da cidade mais quentes por excesso de concreto, asfalto e pouca vegetação",
                        "Regiões com excesso de rios e lagos",
                        "Locais onde a temperatura sempre diminui por causa dos prédios",
                        "Áreas rurais próximas a grandes florestas"
                },
                0
        ));

        perguntas.add(new PerguntaQuiz(
                "Qual atitude representa consumo consciente?",
                new String[]{
                        "Comprar sem necessidade para evitar economia",
                        "Desperdiçar água porque ela é infinita",
                        "Reutilizar materiais, economizar energia e evitar desperdícios",
                        "Trocar produtos novos todos os dias"
                },
                2
        ));

        perguntas.add(new PerguntaQuiz(
                "No contexto ambiental, sustentabilidade significa:",
                new String[]{
                        "Usar recursos naturais sem pensar nas próximas gerações",
                        "Equilibrar meio ambiente, sociedade e economia",
                        "Priorizar apenas o crescimento econômico",
                        "Eliminar toda atividade humana das cidades"
                },
                1
        ));
    }

    private void mostrarPergunta() {
        PerguntaQuiz p = perguntas.get(perguntaAtual);

        lblProgresso.setText("Pergunta " + (perguntaAtual + 1) + "/" + perguntas.size());
        lblPontuacao.setText("Pontos: " + pontos);
        lblQuestao.setText("Questão " + (perguntaAtual + 1));
        barraProgresso.setValue(perguntaAtual);

        lblPergunta.setText(formatarHtmlResponsivo(p.getEnunciado()));

        String[] alternativas = p.getAlternativas();

        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setText("<html><body>" +
                    "<b>" + (char) ('A' + i) + ")</b> " + alternativas[i] +
                    "</body></html>");

            botoes[i].setEnabled(true);
            botoes[i].setBackground(verdeClaro);
            botoes[i].setForeground(new Color(30, 82, 60));
        }

        revalidate();
        repaint();
    }

    private String formatarHtmlResponsivo(String texto) {
        return "<html><body style='width: 100%;'>" + texto + "</body></html>";
    }

    private void responder(int resposta) {
        PerguntaQuiz p = perguntas.get(perguntaAtual);
        int correta = p.getRespostaCorreta();

        for (JButton botao : botoes) {
            botao.setEnabled(false);
        }

        if (resposta == correta) {
            pontos++;
            botoes[resposta].setBackground(new Color(200, 245, 215));
            lblPontuacao.setText("Pontos: " + pontos);
        } else {
            botoes[resposta].setBackground(new Color(255, 210, 205));
            botoes[correta].setBackground(new Color(200, 245, 215));
        }

        Timer timer = new Timer(900, e -> {
            perguntaAtual++;

            if (perguntaAtual >= perguntas.size()) {
                barraProgresso.setValue(perguntas.size());
                mostrarResultadoFinal();
            } else {
                mostrarPergunta();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarResultadoFinal() {
        String mensagem;

        if (pontos == perguntas.size()) {
            mensagem = "Excelente! Você demonstrou domínio sobre sustentabilidade urbana.";
        } else if (pontos >= perguntas.size() * 0.7) {
            mensagem = "Muito bom! Você compreende bem os principais problemas ambientais das cidades.";
        } else if (pontos >= perguntas.size() * 0.5) {
            mensagem = "Bom resultado! Continue estudando educação ambiental.";
        } else {
            mensagem = "Continue praticando. Revise os temas sobre poluição, reciclagem e sustentabilidade.";
        }

        JOptionPane.showMessageDialog(
                this,
                "Quiz finalizado!\n\nPontuação final: " + pontos + " de " + perguntas.size() + "\n\n" + mensagem,
                "Resultado Final",
                JOptionPane.INFORMATION_MESSAGE
        );

        JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
        janela.dispose();
        new Home();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint fundo = new GradientPaint(0, 0, verdeEscuro, getWidth(), getHeight(), verdeMedio);
        g2.setPaint(fundo);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(255, 255, 255, 22));
        g2.fillOval(-120, -90, 320, 320);
        g2.fillOval(getWidth() - 250, getHeight() - 230, 360, 360);
        g2.fillOval(getWidth() - 210, 80, 150, 150);

        g2.dispose();
    }
}

class QuizRoundedPanel extends JPanel {

    private final Color cor;
    private final int arco;

    public QuizRoundedPanel(Color cor, int arco) {
        this.cor = cor;
        this.arco = arco;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 28));
        g2.fillRoundRect(6, 8, getWidth() - 12, getHeight() - 12, arco, arco);

        g2.setColor(cor);
        g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, arco, arco);

        g2.dispose();
    }
}

class PerguntaQuiz {

    private final String enunciado;
    private final String[] alternativas;
    private final int respostaCorreta;

    public PerguntaQuiz(String enunciado, String[] alternativas, int respostaCorreta) {
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.respostaCorreta = respostaCorreta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }
}