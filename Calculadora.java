import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSwing extends JFrame {
    private final JTextField display;
    private String operador = "";
    private double valor1 = 0;
    private boolean novaEntrada = true;

    public CalculadoraSwing() {
        setTitle("Calculadora Estilo Celular");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField("0");
        display.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 12, 12));
        painelBotoes.setBackground(Color.BLACK);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] textos = {
            "C", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "−",
            "1", "2", "3", "+",
            "0", "0", ".", "="
        };

        for (String txt : textos) {
            JButton btn = new JButton(txt);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 28));
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);

            if ("÷×−+=".contains(txt)) {
                btn.setBackground(new Color(255, 149, 0));
            } else if ("C+-%".contains(txt)) {
                btn.setBackground(new Color(85, 85, 85));
            } else {
                btn.setBackground(new Color(46, 46, 46));
            }

            if (txt.equals("0")) {
                btn.setHorizontalAlignment(SwingConstants.LEFT);
                btn.setMargin(new Insets(0, 30, 0, 0));
            }

            btn.addActionListener(this::acaoBotao);
            painelBotoes.add(btn);
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void acaoBotao(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();

        if ("0123456789.".contains(cmd)) {
            inserirNumero(cmd);
        } else if ("C".equals(cmd)) {
            limpar();
        } else if ("+/-".equals(cmd)) {
            inverterSinal();
        } else if ("%".equals(cmd)) {
            porcentagem();
        } else if ("÷×−+".contains(cmd)) {
            operadorPressionado(cmd);
        } else if ("=".equals(cmd)) {
            calcular();
        }
    }

    private void inserirNumero(String num) {
        if (novaEntrada) {
            if (num.equals(".")) {
                display.setText("0.");
            } else {
                display.setText(num);
            }
            novaEntrada = false;
        } else {
            if (num.equals(".") && display.getText().contains(".")) {
                return;
            }
            display.setText(display.getText() + num);
        }
    }

    private void limpar() {
        display.setText("0");
        valor1 = 0;
        operador = "";
        novaEntrada = true;
    }

    private void inverterSinal() {
        try {
            double val = Double.parseDouble(display.getText());
            val = -val;
            display.setText(formatarResultado(val));
        } catch (NumberFormatException ignored) {}
    }

    private void porcentagem() {
        try {
            double val = Double.parseDouble(display.getText());
            val = val / 100;
            display.setText(formatarResultado(val));
            novaEntrada = true;
        } catch (NumberFormatException ignored) {}
    }

    private void operadorPressionado(String op) {
        try {
            valor1 = Double.parseDouble(display.getText());
            operador = op;
            novaEntrada = true;
        } catch (NumberFormatException ignored) {}
    }

    private void calcular() {
        if (operador.isEmpty()) return;

        try {
            double valor2 = Double.parseDouble(display.getText());
            double resultado = 0;

            switch (operador) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "−":
                    resultado = valor1 - valor2;
                    break;
                case "×":
                    resultado = valor1 * valor2;
                    break;
                case "÷":
                    if (valor2 == 0) {
                        JOptionPane.showMessageDialog(this, "Erro: divisão por zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                        limpar();
                        return;
                    }
                    resultado = valor1 / valor2;
                    break;
            }

            display.setText(formatarResultado(resultado));
            operador = "";
            novaEntrada = true;
        } catch (NumberFormatException ignored) {}
    }

    private String formatarResultado(double val) {
        if (val == (long) val)
            return String.format("%d", (long) val);
        else
            return String.format("%s", val);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraSwing calc = new CalculadoraSwing();
            calc.setVisible(true);
        });
    }
}
