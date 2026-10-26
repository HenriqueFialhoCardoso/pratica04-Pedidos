

import java.util.ArrayList;

import java.util.Iterator;

import java.util.Scanner;

// ENUMS

enum StatusPedido {

    PENDENTE,

    PROCESSANDO,

    ENVIADO,

    ENTREGUE,

    CANCELADO

}

enum Categoria {

    ELETRONICO,

    ROUPA,

    ALIMENTO

}

// EXCECOES

class PedidoInvalidoException extends Exception {

    public PedidoInvalidoException(String mensagem) {

        super(mensagem);

    }

}

class ValorInvalidoException extends Exception {

    public ValorInvalidoException(String mensagem) {

        super(mensagem);

    }

}

// INTERFACES

interface Processavel {

    void processarPedido() throws PedidoInvalidoException;

    double calcularValorFinal();

}

interface Entregavel {

    double calcularFrete();

}

// CLASSE ABSTRATA

abstract class Pedido implements Processavel {

    protected int id;

    protected String nomeCliente;

    protected double valor;

    protected StatusPedido status;

    protected Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor,

                 StatusPedido status, Categoria categoria)

            throws ValorInvalidoException {

        if (valor <= 0) {

            throw new ValorInvalidoException(

                "O valor do pedido deve ser maior que zero."

            );

        }

        this.id = id;

        this.nomeCliente = nomeCliente;

        this.valor = valor;

        this.status = status;

        this.categoria = categoria;

    }

    @Override

    public void processarPedido() throws PedidoInvalidoException {

        if (status == StatusPedido.CANCELADO) {

            throw new PedidoInvalidoException(

                "Pedido cancelado nao pode ser processado."

            );

        }

        if (status == StatusPedido.ENTREGUE) {

            throw new PedidoInvalidoException(

                "Pedido entregue nao pode ser processado."

            );

        }

        if (status != StatusPedido.PENDENTE) {

            throw new PedidoInvalidoException(

                "Somente pedidos pendentes podem ser processados."

            );

        }

        status = StatusPedido.PROCESSANDO;

        System.out.println(

            "Pedido " + id + " processado com sucesso!"

        );

    }

    public void alterarStatus(StatusPedido novoStatus)

            throws PedidoInvalidoException {

        if (status == StatusPedido.ENTREGUE) {

            throw new PedidoInvalidoException(

                "Pedido entregue nao pode mudar de status."

            );

        }

        if (status == StatusPedido.CANCELADO) {

            throw new PedidoInvalidoException(

                "Pedido cancelado nao pode mudar de status."

            );

        }

        status = novoStatus;

        System.out.println("Status alterado com sucesso!");

    }

    public int getId() {

        return id;

    }

    public String getNomeCliente() {

        return nomeCliente;

    }

    public double getValor() {

        return valor;

    }

    public StatusPedido getStatus() {

        return status;

    }

    public Categoria getCategoria() {

        return categoria;

    }

    public abstract double calcularValorFinal();

    public abstract void exibirPedido();

}

// PEDIDO ELETRONICO

class PedidoEletronico extends Pedido implements Entregavel {

    public PedidoEletronico(int id, String nomeCliente, double valor,

                          StatusPedido status)

            throws ValorInvalidoException {

        super(id, nomeCliente, valor, status, Categoria.ELETRONICO);

    }

    @Override

    public double calcularValorFinal() {

        if (valor > 100) {

            return valor - (valor * 0.10);

        }

        return valor;

    }

    @Override

    public double calcularFrete() {

        return 20 + (valor * 0.10);

    }

    @Override

    public void exibirPedido() {

        System.out.println("\nID: " + id);

        System.out.println("Cliente: " + nomeCliente);

        System.out.println("Categoria: " + categoria);

        System.out.println("Valor original: R$ " + valor);

        System.out.println("Valor final: R$ " + calcularValorFinal());

        System.out.println("Frete: R$ " + calcularFrete());

        System.out.println("Status: " + status);

    }

}

// PEDIDO ROUPA

class PedidoRoupa extends Pedido implements Entregavel {

    public PedidoRoupa(int id, String nomeCliente, double valor,

                      StatusPedido status)

            throws ValorInvalidoException {

        super(id, nomeCliente, valor, status, Categoria.ROPA);

    }

    @Override

    public double calcularValorFinal() {

        if (valor > 200) {

            return valor - (valor * 0.20);

        }

        return valor;

    }

    @Override

    public double calcularFrete() {

        return 10 + (valor * 0.05);

    }

    @Override

    public void exibirPedido() {

        System.out.println("\nID: " + id);

        System.out.println("Cliente: " + nomeCliente);

        System.out.println("Categoria: " + categoria);

        System.out.println("Valor original: R$ " + valor);

        System.out.println("Valor final: R$ " + calcularValorFinal());

        System.out.println("Frete: R$ " + calcularFrete());

        System.out.println("Status: " + status);

    }

}

// PEDIDO ALIMENTO

class PedidoAlimento extends Pedido {

    public PedidoAlimento(int id, String nomeCliente, double valor,

                         StatusPedido status)

            throws ValorInvalidoException {

        super(id, nomeCliente, valor, status, Categoria.ALIMENTO);

    }

    @Override

    public double calcularValorFinal() {

        return valor;

    }

    @Override

    public void exibirPedido() {

        System.out.println("\nID: " + id);

        System.out.println("Cliente: " + nomeCliente);

        System.out.println("Categoria: " + categoria);

        System.out.println("Valor original: R$ " + valor);

        System.out.println("Valor final: R$ " + calcularValorFinal());

        System.out.println("Frete: Nao aplicavel");

        System.out.println("Status: " + status);

    }

}

// CLASSE PRINCIPAL

public class Main {

    static ArrayList<Pedido> pedidos = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    static int proximoId = 1;

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n===== SISTEMA GERENCIADOR DE PEDIDOS =====");

            System.out.println("1 - Cadastrar pedido");

            System.out.println("2 - Alterar status de pedido");

            System.out.println("3 - Processar pedidos");

            System.out.println("4 - Remover pedidos cancelados");

            System.out.println("5 - Exibir lista de pedidos");

            System.out.println("6 - Exibir pedidos entregaveis");

            System.out.println("0 - Encerrar");

            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1:

                    cadastrarPedido();

                    break;

                case 2:

                    alterarStatus();

                    break;

                case 3:

                    processarPedidos();

                    break;

                case 4:

                    removerCancelados();

                    break;

                case 5:

                    exibirPedidos();

                    break;

                case 6:

                    exibirEntregaveis();

                    break;

                case 0:

                    System.out.println("Sistema encerrado.");

                    break;

                default:

                    System.out.println("Opcao invalida.");

            }

        } while (opcao != 0);

        scanner.close();

    }

    // CADASTRAR PEDIDO

    static void cadastrarPedido() {

        try {

            System.out.println("\n===== CADASTRAR PEDIDO =====");

            System.out.print("Nome do cliente: ");

            String nome = scanner.nextLine();

            System.out.print("Valor do pedido: ");

            double valor = lerDouble();

            System.out.println("\nCategoria:");

            System.out.println("1 - Eletronico");

            System.out.println("2 - Roupa");

            System.out.println("3 - Alimento");

            System.out.print("Escolha: ");

            int categoria = lerInteiro();

            Pedido pedido;

            switch (categoria) {

                case 1:

                    pedido = new PedidoEletronico(

                        proximoId++, nome, valor, StatusPedido.PENDENTE

                    );

                    break;

                case 2:

                    pedido = new PedidoRoupa(

                        proximoId++, nome, valor, StatusPedido.PENDENTE

                    );

                    break;

                case 3:

                    pedido = new PedidoAlimento(

                        proximoId++, nome, valor, StatusPedido.PENDENTE

                    );

                    break;

                default:

                    System.out.println("Categoria invalida.");

                    return;

            }

            pedidos.add(pedido);

            System.out.println("Pedido cadastrado com sucesso!");

            System.out.println("ID do pedido: " + pedido.getId());

        } catch (ValorInvalidoException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // ALTERAR STATUS

    static void alterarStatus() {

        System.out.println("\n===== ALTERAR STATUS =====");

        System.out.print("Digite o ID do pedido: ");

        int id = lerInteiro();

        Pedido pedido = buscarPedido(id);

        if (pedido == null) {

            System.out.println("Pedido nao encontrado.");

            return;

        }

        System.out.println("\nStatus disponiveis:");

        StatusPedido[] status = StatusPedido.values();

        for (int i = 0; i < status.length; i++) {

            System.out.println((i + 1) + " - " + status[i]);

        }

        System.out.print("Escolha o novo status: ");

        int escolha = lerInteiro();

        if (escolha < 1 || escolha > status.length) {

            System.out.println("Status invalido.");

            return;

        }

        try {

            pedido.alterarStatus(status[escolha - 1]);

        } catch (PedidoInvalidoException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // PROCESSAR PEDIDOS COM ITERATOR

    static void processarPedidos() {

        System.out.println("\n===== PROCESSAR PEDIDOS =====");

        Iterator<Pedido> iterator = pedidos.iterator();

        while (iterator.hasNext()) {

            Pedido pedido = iterator.next();

            try {

                pedido.processarPedido();

            } catch (PedidoInvalidoException e) {

                System.out.println(

                    "Erro no pedido " + pedido.getId()

                    + ": " + e.getMessage()

                );

            }

        }

    }

    // REMOVER CANCELADOS COM ITERATOR

    static void removerCancelados() {

        System.out.println("\n===== REMOVER CANCELADOS =====");

        Iterator<Pedido> iterator = pedidos.iterator();

        int removidos = 0;

        while (iterator.hasNext()) {

            Pedido pedido = iterator.next();

            if (pedido.getStatus() == StatusPedido.CANCELADO) {

                iterator.remove();

                removidos++;

            }

        }

        System.out.println(

            removidos + " pedido(s) cancelado(s) removido(s)."

        );

    }

    // EXIBIR PEDIDOS SEM CANCELADOS

    static void exibirPedidos() {

        System.out.println("\n===== LISTA DE PEDIDOS =====");

        boolean encontrou = false;

        for (Pedido pedido : pedidos) {

            if (pedido.getStatus() != StatusPedido.CANCELADO) {

                pedido.exibirPedido();

                encontrou = true;

            }

        }

        if (!encontrou) {

            System.out.println("Nenhum pedido encontrado.");

        }

    }

    // EXIBIR PEDIDOS ENTREGAVEIS

    static void exibirEntregaveis() {

        System.out.println("\n===== PEDIDOS ENTREGAVEIS =====");

        boolean encontrou = false;

        for (Pedido pedido : pedidos) {

            if (pedido instanceof Entregavel) {

                if (pedido.getStatus() != StatusPedido.CANCELADO) {

                    pedido.exibirPedido();

                    encontrou = true;

                }

            }

        }

        if (!encontrou) {

            System.out.println("Nenhum pedido entregavel encontrado.");

        }

    }

    // BUSCAR PEDIDO

    static Pedido buscarPedido(int id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == id) {

                return pedido;

            }

        }

        return null;

    }

    // LER INTEIRO COM VALIDACAO

    static int lerInteiro() {

        while (true) {

            try {

                int valor = Integer.parseInt(scanner.nextLine());

                return valor;

            } catch (NumberFormatException e) {

                System.out.print(

                    "Entrada invalida. Digite um numero inteiro: "

                );

            }

        }

    }

    // LER DOUBLE COM VALIDACAO

    static double lerDouble() {

        while (true) {

            try {

                String entrada = scanner.nextLine().replace(",", ".");

                double valor = Double.parseDouble(entrada);

                return valor;

            } catch (NumberFormatException e) {

                System.out.print(

                    "Valor invalido. Digite novamente: "

                );

            }

        }

    }

}