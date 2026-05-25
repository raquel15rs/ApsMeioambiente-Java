package ApsMeioambiente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TelaJogo extends JFrame {

    public TelaJogo() {
        setTitle("Trabalho sobre o meio ambiente");
        setSize(1120, 760);
        setMinimumSize(new Dimension(1000, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setContentPane(new TelaIntroducao(this));
        setVisible(true);
    }

    public void iniciarJogo() {
        setContentPane(new PainelCidadeCartas(this));
        revalidate();
        repaint();
    }

    public void voltarHome() {
        dispose();
        new Home();
    }
}

class TelaIntroducao extends JPanel {

    private final TelaJogo janela;

    public TelaIntroducao(TelaJogo janela) {
        this.janela = janela;
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 74, 62));

        JPanel container = new JPanel(new BorderLayout(0, 28));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(35, 45, 35, 45));

        JPanel topo = criarTopo();
        JPanel conteudo = criarConteudo();
        JPanel botoes = criarBotoes();

        container.add(topo, BorderLayout.NORTH);
        container.add(conteudo, BorderLayout.CENTER);
        container.add(botoes, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(container, gbc);
    }

    private JPanel criarTopo() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 0, 8));
        painel.setOpaque(false);

        JLabel titulo = new JLabel("Trabalho sobre o meio ambiente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 52));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Salve uma cidade em colapso ambiental", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 23));
        subtitulo.setForeground(new Color(210, 245, 230));

        painel.add(titulo);
        painel.add(subtitulo);

        return painel;
    }

    private JPanel criarConteudo() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        RoundedPanel card = new RoundedPanel(new Color(248, 252, 249), 28);
        card.setLayout(new BorderLayout(25, 25));
        card.setBorder(new EmptyBorder(35, 40, 35, 40));

        JLabel tituloCard = new JLabel("Cidade em Colapso", SwingConstants.LEFT);
        tituloCard.setFont(new Font("Arial", Font.BOLD, 28));
        tituloCard.setForeground(new Color(25, 85, 62));

        JTextArea texto = new JTextArea(
                "Você assumirá o controle de uma metrópole em crise.\n\n" +
                "A cada rodada, escolha cartas de ações ambientais. Cada decisão consome recursos, " +
                "mas pode reduzir poluição, diminuir lixo urbano, ampliar áreas verdes, melhorar a energia limpa " +
                "e aumentar a felicidade da população.\n\n" +
                "Objetivo do jogo:\n" +
                "• Evitar que a cidade entre em colapso\n" +
                "• Administrar bem os recursos disponíveis\n" +
                "• Transformar a metrópole em uma cidade sustentável\n\n" +
                "Você perde se a poluição ou o lixo chegarem a 100%, se a felicidade chegar a 0% " +
                "ou se nenhuma carta puder ser comprada."
        );

        texto.setFont(new Font("Arial", Font.PLAIN, 18));
        texto.setForeground(new Color(45, 70, 58));
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setOpaque(false);

        card.add(tituloCard, BorderLayout.NORTH);
        card.add(texto, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.85;
        gbc.weighty = 1;
        wrapper.add(card, gbc);

        return wrapper;
    }

    private JPanel criarBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        painel.setOpaque(false);

        JButton btnComecar = criarBotao("Começar Jogo", new Color(238, 255, 246), new Color(20, 92, 60), 260);
        JButton btnVoltar = criarBotao("Voltar", Color.WHITE, new Color(45, 90, 70), 130);

        btnComecar.addActionListener(e -> janela.iniciarJogo());
        btnVoltar.addActionListener(e -> janela.voltarHome());

        painel.add(btnComecar);
        painel.add(btnVoltar);

        return painel;
    }

    private JButton criarBotao(String texto, Color fundo, Color letra, int largura) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(largura, 54));
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(fundo);
        botao.setForeground(letra);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
}

class PainelCidadeCartas extends JPanel {

    private int poluicao = 65;
    private int lixo = 60;
    private int arvores = 25;
    private int energiaLimpa = 20;
    private int felicidade = 50;
    private int recursos = 90;
    private int rodada = 1;
    private int pontuacao = 0;

    private boolean jogoFinalizado = false;

    private JLabel lblRodada;
    private JLabel lblRecursos;
    private JLabel lblPontuacao;
    private JLabel lblStatus;

    private JProgressBar barraPoluicao;
    private JProgressBar barraLixo;
    private JProgressBar barraArvores;
    private JProgressBar barraEnergia;
    private JProgressBar barraFelicidade;

    private JButton carta1;
    private JButton carta2;
    private JButton carta3;

    private CidadeCanvas cidadeCanvas;

    private final TelaJogo janela;
    private final Random random = new Random();

    private final ArrayList<AcaoAmbiental> todasAcoes = new ArrayList<>();
    private final ArrayList<AcaoAmbiental> cartasAtuais = new ArrayList<>();

    public PainelCidadeCartas(TelaJogo janela) {
        this.janela = janela;
        setLayout(new BorderLayout());
        criarAcoes();
        criarInterface();
        gerarCartas();
        atualizarTudo();
    }

    private void criarInterface() {
        JPanel tela = new JPanel(new BorderLayout(20, 20));
        tela.setBackground(new Color(18, 70, 58));
        tela.setBorder(new EmptyBorder(22, 28, 22, 28));

        tela.add(criarTopo(), BorderLayout.NORTH);
        tela.add(criarCentro(), BorderLayout.CENTER);
        tela.add(criarInferior(), BorderLayout.SOUTH);

        add(tela, BorderLayout.CENTER);
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        JLabel titulo = new JLabel("Salve a cidade");
        titulo.setFont(new Font("Arial", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setOpaque(false);

        lblRodada = criarBadge();
        lblRecursos = criarBadge();
        lblPontuacao = criarBadge();

        stats.add(lblRodada);
        stats.add(lblRecursos);
        stats.add(lblPontuacao);

        topo.add(titulo, BorderLayout.WEST);
        topo.add(stats, BorderLayout.EAST);

        return topo;
    }

    private JLabel criarBadge() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(145, 42));
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(20, 85, 62));
        label.setOpaque(true);
        label.setBackground(new Color(238, 252, 245));
        return label;
    }

    private JPanel criarCentro() {
        JPanel centro = new JPanel(new BorderLayout(22, 0));
        centro.setOpaque(false);

        centro.add(criarPainelIndicadores(), BorderLayout.WEST);

        cidadeCanvas = new CidadeCanvas(this);
        centro.add(cidadeCanvas, BorderLayout.CENTER);

        return centro;
    }

    private JPanel criarPainelIndicadores() {
        RoundedPanel painel = new RoundedPanel(new Color(255, 255, 255, 35), 24);
        painel.setLayout(new GridLayout(5, 1, 0, 13));
        painel.setPreferredSize(new Dimension(340, 350));
        painel.setBorder(new EmptyBorder(22, 22, 22, 22));

        barraPoluicao = criarBarra(false);
        barraLixo = criarBarra(false);
        barraArvores = criarBarra(true);
        barraEnergia = criarBarra(true);
        barraFelicidade = criarBarra(true);

        painel.add(criarIndicador("Poluição", barraPoluicao));
        painel.add(criarIndicador("Lixo urbano", barraLixo));
        painel.add(criarIndicador("Áreas verdes", barraArvores));
        painel.add(criarIndicador("Energia limpa", barraEnergia));
        painel.add(criarIndicador("Felicidade", barraFelicidade));

        return painel;
    }

    private JPanel criarIndicador(String nome, JProgressBar barra) {
        JPanel painel = new JPanel(new BorderLayout(0, 5));
        painel.setOpaque(false);

        JLabel label = new JLabel(nome);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);

        painel.add(label, BorderLayout.NORTH);
        painel.add(barra, BorderLayout.CENTER);

        return painel;
    }

    private JProgressBar criarBarra(boolean positiva) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setStringPainted(true);
        barra.setFont(new Font("Arial", Font.BOLD, 13));
        barra.setForeground(positiva ? new Color(38, 172, 96) : new Color(215, 85, 70));
        barra.setBackground(new Color(230, 235, 232));
        return barra;
    }

    private JPanel criarInferior() {
        JPanel inferior = new JPanel(new BorderLayout(0, 12));
        inferior.setOpaque(false);

        JPanel cartas = new JPanel(new GridLayout(1, 3, 22, 0));
        cartas.setOpaque(false);
        cartas.setPreferredSize(new Dimension(900, 155));

        carta1 = criarCarta();
        carta2 = criarCarta();
        carta3 = criarCarta();

        cartas.add(carta1);
        cartas.add(carta2);
        cartas.add(carta3);

        carta1.addActionListener(e -> executarCarta(0));
        carta2.addActionListener(e -> executarCarta(1));
        carta3.addActionListener(e -> executarCarta(2));

        JPanel rodape = new JPanel(new BorderLayout(15, 0));
        rodape.setOpaque(false);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(105, 36));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(35, 85, 65));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> janela.voltarHome());

        lblStatus = new JLabel("Escolha uma carta para iniciar sua estratégia ambiental.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 15));
        lblStatus.setForeground(Color.WHITE);

        rodape.add(btnVoltar, BorderLayout.WEST);
        rodape.add(lblStatus, BorderLayout.CENTER);

        inferior.add(cartas, BorderLayout.CENTER);
        inferior.add(rodape, BorderLayout.SOUTH);

        return inferior;
    }

    private JButton criarCarta() {
        JButton botao = new JButton();
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBackground(new Color(248, 252, 248));
        botao.setForeground(new Color(25, 85, 60));
        botao.setMargin(new Insets(10, 12, 10, 12));
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(185, 220, 200), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        return botao;
    }

    private void criarAcoes() {
        todasAcoes.add(new AcaoAmbiental("Plantar árvores", 25, -8, 0, 14, 0, 6,
                "Árvores reduzem calor, melhoram o ar e aumentam o bem-estar."));

        todasAcoes.add(new AcaoAmbiental("Criar reciclagem", 30, -4, -15, 0, 0, 5,
                "A reciclagem reduz resíduos e reaproveita materiais."));

        todasAcoes.add(new AcaoAmbiental("Transporte público", 35, -12, 0, 0, 0, 8,
                "Menos carros nas ruas significa menos emissão de poluentes."));

        todasAcoes.add(new AcaoAmbiental("Energia limpa", 40, -9, 0, 0, 18, 4,
                "Energia limpa reduz o impacto ambiental da cidade."));

        todasAcoes.add(new AcaoAmbiental("Mutirão de limpeza", 25, -3, -12, 0, 0, 9,
                "Mutirões recuperam espaços públicos e melhoram a cidade."));

        todasAcoes.add(new AcaoAmbiental("Campanha educativa", 20, -3, -4, 0, 0, 10,
                "Educação ambiental muda hábitos da população."));

        todasAcoes.add(new AcaoAmbiental("Parque urbano", 45, -6, -3, 20, 0, 12,
                "Parques aumentam áreas verdes e qualidade de vida."));

        todasAcoes.add(new AcaoAmbiental("Coleta seletiva", 28, -2, -18, 0, 0, 6,
                "A coleta seletiva organiza resíduos e diminui lixo urbano."));
    }

    private void gerarCartas() {
        cartasAtuais.clear();

        ArrayList<AcaoAmbiental> copia = new ArrayList<>(todasAcoes);
        Collections.shuffle(copia);

        cartasAtuais.add(copia.get(0));
        cartasAtuais.add(copia.get(1));
        cartasAtuais.add(copia.get(2));

        atualizarCartas();
        verificarSeHaCartaCompravel();
    }

    private void atualizarCartas() {
        if (cartasAtuais.size() < 3) return;

        JButton[] botoes = {carta1, carta2, carta3};

        for (int i = 0; i < botoes.length; i++) {
            AcaoAmbiental acao = cartasAtuais.get(i);

            botoes[i].setText(formatarCarta(acao));
            botoes[i].setEnabled(recursos >= acao.custo);

            if (recursos >= acao.custo) {
                botoes[i].setBackground(new Color(248, 252, 248));
                botoes[i].setForeground(new Color(25, 85, 60));
            } else {
                botoes[i].setBackground(new Color(215, 215, 215));
                botoes[i].setForeground(new Color(120, 120, 120));
            }
        }
    }

    private String formatarCarta(AcaoAmbiental acao) {
        return "<html><center>" +
                "<b>" + acao.nome + "</b><br><br>" +
                "Custo: <b>" + acao.custo + "</b><br><br>" +
                efeitoTexto("Poluição", acao.efeitoPoluicao) +
                efeitoTexto("Lixo", acao.efeitoLixo) +
                efeitoTexto("Verde", acao.efeitoArvores) +
                efeitoTexto("Energia", acao.efeitoEnergia) +
                efeitoTexto("Felicidade", acao.efeitoFelicidade) +
                "</center></html>";
    }

    private String efeitoTexto(String nome, int valor) {
        if (valor == 0) return "";
        return nome + ": " + mostrarEfeito(valor) + "<br>";
    }

    private void executarCarta(int indice) {
        if (jogoFinalizado) return;

        AcaoAmbiental acao = cartasAtuais.get(indice);

        if (recursos < acao.custo) {
            lblStatus.setText("Recursos insuficientes para usar esta carta.");
            return;
        }

        int polAnt = poluicao;
        int lixoAnt = lixo;
        int arvAnt = arvores;
        int eneAnt = energiaLimpa;
        int felAnt = felicidade;

        recursos -= acao.custo;

        poluicao = limitar(poluicao + acao.efeitoPoluicao);
        lixo = limitar(lixo + acao.efeitoLixo);
        arvores = limitar(arvores + acao.efeitoArvores);
        energiaLimpa = limitar(energiaLimpa + acao.efeitoEnergia);
        felicidade = limitar(felicidade + acao.efeitoFelicidade);

        pontuacao += calcularPontos(polAnt, lixoAnt, arvAnt, eneAnt, felAnt);

        lblStatus.setText(acao.explicacao);

        passarRodada();
    }

    private int calcularPontos(int polAnt, int lixoAnt, int arvAnt, int eneAnt, int felAnt) {
        int pontos = 10;

        pontos += Math.max(0, polAnt - poluicao) * 5;
        pontos += Math.max(0, lixoAnt - lixo) * 5;
        pontos += Math.max(0, arvores - arvAnt) * 4;
        pontos += Math.max(0, energiaLimpa - eneAnt) * 4;
        pontos += Math.max(0, felicidade - felAnt) * 3;

        return pontos;
    }

    private void passarRodada() {
        rodada++;

        recursos += 18;

        poluicao += random.nextInt(7) + 3;
        lixo += random.nextInt(6) + 3;
        felicidade -= random.nextInt(5) + 2;

        if (arvores >= 60) {
            poluicao -= 3;
            felicidade += 1;
        }

        if (energiaLimpa >= 60) {
            poluicao -= 3;
        }

        if (rodada > 7) {
            poluicao += 2;
            lixo += 1;
        }

        if (rodada > 12) {
            felicidade -= 2;
            poluicao += 1;
        }

        eventoAleatorio();
        atualizarTudo();

        if (!verificarFim()) {
            gerarCartas();
        }
    }

    private void eventoAleatorio() {
        int evento = random.nextInt(7);

        if (evento == 0) {
            poluicao += 6;
            lblStatus.setText("Evento: aumento de veículos elevou a poluição.");
        } else if (evento == 1) {
            lixo += 7;
            lblStatus.setText("Evento: descarte irregular aumentou o lixo urbano.");
        } else if (evento == 2) {
            felicidade -= 7;
            lblStatus.setText("Evento: a população ficou insatisfeita.");
        } else if (evento == 3) {
            recursos += 20;
            lblStatus.setText("Bônus: verba extra para projetos ambientais.");
        }
    }

    private void atualizarTudo() {
        poluicao = limitar(poluicao);
        lixo = limitar(lixo);
        arvores = limitar(arvores);
        energiaLimpa = limitar(energiaLimpa);
        felicidade = limitar(felicidade);

        barraPoluicao.setValue(poluicao);
        barraLixo.setValue(lixo);
        barraArvores.setValue(arvores);
        barraEnergia.setValue(energiaLimpa);
        barraFelicidade.setValue(felicidade);

        lblRodada.setText("Rodada " + rodada);
        lblRecursos.setText("Recursos " + recursos);
        lblPontuacao.setText("Pontos " + pontuacao);

        atualizarCartas();

        if (cidadeCanvas != null) {
            cidadeCanvas.repaint();
        }
    }

    private boolean verificarFim() {
        if (poluicao >= 100 || lixo >= 100 || felicidade <= 0) {
            jogoFinalizado = true;

            JOptionPane.showMessageDialog(
                    this,
                    "A cidade entrou em colapso.\n\nPontuação final: " + pontuacao,
                    "Derrota",
                    JOptionPane.ERROR_MESSAGE
            );

            janela.voltarHome();
            return true;
        }

        if (poluicao <= 10 && lixo <= 10 && arvores >= 75 && energiaLimpa >= 85 && felicidade >= 85) {
            jogoFinalizado = true;
            pontuacao += 300;

            JOptionPane.showMessageDialog(
                    this,
                    "Você transformou a cidade em uma metrópole sustentável!\n\nPontuação final: " + pontuacao,
                    "Vitória",
                    JOptionPane.INFORMATION_MESSAGE
            );

            janela.voltarHome();
            return true;
        }

        return false;
    }

    private void verificarSeHaCartaCompravel() {
        if (jogoFinalizado) return;

        for (AcaoAmbiental acao : cartasAtuais) {
            if (recursos >= acao.custo) return;
        }

        jogoFinalizado = true;

        JOptionPane.showMessageDialog(
                this,
                "Nenhuma carta pode ser comprada.\n\nPontuação final: " + pontuacao,
                "Colapso financeiro",
                JOptionPane.ERROR_MESSAGE
        );

        janela.voltarHome();
    }

    private int limitar(int valor) {
        return Math.max(0, Math.min(100, valor));
    }

    private String mostrarEfeito(int valor) {
        if (valor > 0) return "+" + valor;
        if (valor < 0) return String.valueOf(valor);
        return "0";
    }

    public int getPoluicao() {
        return poluicao;
    }

    public int getLixo() {
        return lixo;
    }

    public int getArvores() {
        return arvores;
    }

    public int getFelicidade() {
        return felicidade;
    }
}

class CidadeCanvas extends JPanel {

    private final PainelCidadeCartas jogo;

    public CidadeCanvas(PainelCidadeCartas jogo) {
        this.jogo = jogo;
        setOpaque(false);
        setPreferredSize(new Dimension(520, 350));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int poluicao = jogo.getPoluicao();
        int lixo = jogo.getLixo();
        int arvores = jogo.getArvores();
        int felicidade = jogo.getFelicidade();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color topo = poluicao < 35 ? new Color(78, 185, 230) :
                poluicao < 70 ? new Color(150, 155, 140) :
                        new Color(60, 65, 70);

        Color baixo = poluicao < 35 ? new Color(95, 200, 145) :
                poluicao < 70 ? new Color(105, 130, 115) :
                        new Color(42, 52, 48);

        g2.setPaint(new GradientPaint(0, 0, topo, 0, getHeight(), baixo));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        int cx = getWidth() / 2;
        int baseY = getHeight() - 100;

        desenharPredio(g2, cx - 115, baseY - 215, 78, 215, new Color(52, 63, 82));
        desenharPredio(g2, cx - 25, baseY - 175, 72, 175, new Color(64, 75, 95));
        desenharPredio(g2, cx + 65, baseY - 135, 60, 135, new Color(50, 62, 82));

        g2.setColor(new Color(55, 55, 55));
        g2.fillRoundRect(cx - 205, baseY, 410, 60, 24, 24);

        g2.setColor(Color.WHITE);
        for (int i = cx - 180; i < cx + 165; i += 72) {
            g2.fillRect(i, baseY + 28, 38, 5);
        }

        if (arvores > 20) {
            desenharArvore(g2, cx - 230, baseY - 35);
            desenharArvore(g2, cx + 190, baseY - 35);
        }

        if (arvores > 50) {
            desenharArvore(g2, cx - 275, baseY - 18);
            desenharArvore(g2, cx + 235, baseY - 18);
        }

        if (poluicao > 60) {
            desenharFumaca(g2, cx - 115, baseY - 255);
            desenharFumaca(g2, cx - 25, baseY - 205);
        }

        String status = poluicao < 35 && lixo < 35 && felicidade > 60
                ? "Cidade sustentável"
                : poluicao < 70 && lixo < 70
                ? "Cidade em alerta"
                : "Cidade em colapso";

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 21));
        g2.drawString(status, cx - 95, baseY + 92);
    }

    private void desenharPredio(Graphics2D g2, int x, int y, int largura, int altura, Color cor) {
        g2.setColor(cor);
        g2.fillRoundRect(x, y, largura, altura, 10, 10);

        g2.setColor(new Color(255, 220, 95));

        for (int linha = y + 20; linha < y + altura - 20; linha += 34) {
            for (int coluna = x + 15; coluna < x + largura - 15; coluna += 24) {
                g2.fillRoundRect(coluna, linha, 10, 15, 4, 4);
            }
        }
    }

    private void desenharArvore(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(90, 55, 25));
        g2.fillRoundRect(x + 16, y + 25, 12, 35, 5, 5);

        g2.setColor(new Color(25, 140, 65));
        g2.fillOval(x, y, 46, 46);

        g2.setColor(new Color(40, 175, 85));
        g2.fillOval(x + 10, y - 8, 36, 36);
    }

    private void desenharFumaca(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(35, 35, 35, 145));
        g2.fillOval(x, y, 45, 35);
        g2.fillOval(x + 30, y - 15, 55, 45);
        g2.fillOval(x + 70, y, 45, 35);
    }
}

class RoundedPanel extends JPanel {

    private final Color cor;
    private final int arco;

    public RoundedPanel(Color cor, int arco) {
        this.cor = cor;
        this.arco = arco;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(cor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);

        g2.dispose();
        super.paintComponent(g);
    }
}

class AcaoAmbiental {

    String nome;
    int custo;
    int efeitoPoluicao;
    int efeitoLixo;
    int efeitoArvores;
    int efeitoEnergia;
    int efeitoFelicidade;
    String explicacao;

    public AcaoAmbiental(
            String nome,
            int custo,
            int efeitoPoluicao,
            int efeitoLixo,
            int efeitoArvores,
            int efeitoEnergia,
            int efeitoFelicidade,
            String explicacao
    ) {
        this.nome = nome;
        this.custo = custo;
        this.efeitoPoluicao = efeitoPoluicao;
        this.efeitoLixo = efeitoLixo;
        this.efeitoArvores = efeitoArvores;
        this.efeitoEnergia = efeitoEnergia;
        this.efeitoFelicidade = efeitoFelicidade;
        this.explicacao = explicacao;
    }
}