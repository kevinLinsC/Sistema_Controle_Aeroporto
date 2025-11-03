/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_controle_aeroporto;

import java.io.DataInputStream; // Usado para receber dados do usuário.
import java.io.IOException; // Tratamento de erros.
import java.util.ArrayList; 
import java.util.Queue; // Lista.
import java.util.LinkedList; // Lista.
import java.util.Stack; // Pilha
import java.util.Random; // Usado para gerar números aleatórios.

/**
 *
 * @author kevin
 */
public class Sistema_Controle_Aeroporto {
    public static void main(String[] args) throws IOException {
        // VARIAVEIS AUXILIARES
        Random random = new Random(); // Inicia a função Random.
        DataInputStream dado = new DataInputStream(System.in); // Inicia o DataInputStream.
        
        // VARIAVEIS DE CONTROLE GERAL
        int opcaoUsuario; // Guarda qual o usuário está usando o sistema.
        
        // VARIAVEIS DOS VOOS
        String numeroVoo, origem, destino, horarioPartida, qtdMaxPessoa;
        ArrayList<String[]> voos = new ArrayList<>(); // Lista que guarda vetores com as informações dos voos.
        
        // VARIAVEIS DE RESERVAS DE VOOS
        Queue<String[]> reservasPendentes = new LinkedList<>();
        Queue<String[]> reservasConfirmadas = new LinkedList<>();

        // VARIAVEIS DO ADM
        String adm[] = {"adm123", "adm123"}; // Usuário e senha do adm.
        String login, senha;
        int opcaoAdm; // Opções de navegação do adm.
        
        // VARIAVEIS DO PASSAGEIRO
        int opcaoPassageiro; // Opções de navegação do passageiro.
        int opcaoVooPassageiro;
        String nome, cpf, email, idade;
        
        // VARIAVEIS DO CHECK-IN
        Stack<String[]> checkin = new Stack<>();
        
        // ADICIONANDO VOOS ALEATORIOS.
        voos.add(new String[]{"1234", "Algum lugar", "Outro lugar", "12:00", "5", "0"});
        voos.add(new String[]{"4321", "Outro lugar", "Algum lugar", "18:00", "10", "0"});
        voos.add(new String[]{"9999", "WRLD", "Another WRLD", "19:00", "99", "97"});
        
        // ADICIONANDO RESERVAS ALEATORIAS.
        reservasPendentes.add(new String[]{"kevin", "19", "15015015015", "kevin@gmail.com", "9999"});
        reservasPendentes.add(new String[]{"lyandra", "18", "15015015015", "lyandra@gmail.com", "9999"});
        reservasPendentes.add(new String[]{"trinta", "34", "15015015015", "trinta@gmail.com", "4321"});
        
        OUTER_1:
        while (true) {
            Linha();
            opcaoUsuario = PegaNum("Escolha o tipo de usuario: \n1 - Passageiro\n2 - Administrador\n3 - Sair\n-> ", 1, 3, "Opcao invalida.");
            Linha();
            // Usuário escolhido foi o passageiro.
            switch (opcaoUsuario) {
                // PASSAGEIRO
                case 1 -> {
                    // Faz enquanto "opcaoPassageiro" for diferente de zero.
                    do {
                        // Escolha de opções do passageiro.
                        opcaoPassageiro = PegaNum("1 - Reserva de voos \n2 - Check-in\n3 - Informacoes de voos\n0 - Escolher outro usuario\n-> ", 0, 3, "Opcao invalida.");
                        
                        switch (opcaoPassageiro) {
                            // RESERVA DE VOO
                            case 1 -> {
                                Linha();
                                System.out.println("VOOS DISPONIVEIS");
                                Linha();
                                // Escreve todos os voos disponiveis.
                                for (String[] voo : voos) {
                                    if (Integer.parseInt(voo[4]) > Integer.parseInt(voo[5])) {
                                        System.out.println(voo[0] + " - Saindo de: " + voo[1] + ", Destino: " + voo[2] + ", Horario: " + voo[3]);
                                    }
                                }
                                
                                Linha();
                                // Usuário escolhe o voo.
                                opcaoVooPassageiro = PegaNum("Escolha um dos voos acima: ", 1000, 9999, "Opcao invalida.");
                                
                                String[] vooSelecionado = null; // vooSelecionado é inicializado como nulo.
                                // Pega o voo com base no id passado pelo usuário.
                                for (String[] voo : voos) {
                                    if (Integer.toString(opcaoVooPassageiro).equals(voo[0])) {
                                        vooSelecionado = voo;
                                        break;
                                    }
                                }
                                
                                // Se o vooSelecionado for nulo, quer dizer que não foi selecionado nenhum voo.
                                if (vooSelecionado == null) {
                                    System.out.println("Voo não encontrado");
                                }
                                else {
                                    Linha();
                                    System.out.println("Codigo do voo selecionado: " + vooSelecionado[0]);
                                    Linha();
                                    
                                    // PEGA AS INFORMAÇÕES DO USUÁRIO.
                                    System.out.print("Digite seu nome: ");
                                    nome = dado.readLine();
                                    
                                    idade = Integer.toString(PegaNum("Digite sua idade: ", 0, 150, "Digite sua idade real."));
                                    
                                    cpf = PegaCpf("Digite seu CPF: ");
                                    
                                    System.out.print("Digite seu e-mail: ");
                                    email = dado.readLine();
                                    
                                    // Adiciona a reserva na fila.
                                    reservasPendentes.add(new String[]{nome, idade, cpf, email, vooSelecionado[0]});
                                    
                                    Linha();
                                    System.out.println("RESERVA CRIADA COM SUCESSO.");
                                    Linha();
                                }
                                break;
                            }
                            // CHECK-IN
                            case 2 -> {
                                Linha();
                                System.out.println("REALIZAR CHECK-IN");
                                int confirmacaoCheckin; // 1 = Confirmado; 2 = Não confirmado.
                                String[] reservaCheckin = null; // Proxima reserva confirmada na fila.
                                String[] vooReserva = null; // Voo relacionado a reserva confirmada.
                                
                                // Se existir check-in faz executa o código abaixo, se não mostra uma mensagem de erro.
                                if (!reservasConfirmadas.isEmpty()) {
                                    reservaCheckin = reservasConfirmadas.peek(); // Pega a proxima reserva confirmada na fila.
                                
                                    // Pega o voo relacionado a reserva confirmada.
                                    for (String[] voo : voos) {
                                        if (reservaCheckin[4].equals(voo[0])) {
                                            vooReserva = voo;
                                            break;
                                        }
                                    }

                                    // Mostras as informações da reserva e do voo.
                                    Linha();
                                    System.out.println("REALIZE O CHECK-IN");
                                    Linha();
                                    System.out.println("Nome: " + reservaCheckin[0]);
                                    System.out.println("Idade: " + reservaCheckin[1]);
                                    System.out.println("CPF: " + reservaCheckin[2]);
                                    System.out.println("E-mail: " + reservaCheckin[3]);
                                    System.out.println("Voo: " + vooReserva[0] + " - Saindo de: " + vooReserva[1] + ", Destino: " + vooReserva[2] + ", Horario: " + vooReserva[3]);
                                    Linha();

                                    // Confirmação da reserva.
                                    confirmacaoCheckin = PegaNum("1 - Confirmar Check-in\n2 - Deixar pendente\n-> ", 1, 2, "Opcao invalida.");

                                    // Se houver confirmação.
                                    if (confirmacaoCheckin == 1) {
                                        // Remove o primeiro item da fila.
                                        reservasConfirmadas.poll();
                                        // Adiciona a reserva confirmada na lista de reservas confirmadas.
                                        checkin.push(new String[]{reservaCheckin[0], reservaCheckin[1], reservaCheckin[2], reservaCheckin[3], reservaCheckin[4]});
                                    }
                                    else if (confirmacaoCheckin == 2) {
                                        Linha();
                                        System.out.println("RESERVA DEIXADA COMO PENDENTE.");
                                    }
                                }
                                else {
                                    Linha();
                                    System.out.println("NENHUM CHECK-IN CONFIRMADO");
                                    Linha();
                                }
                                Linha();
                                break;
                            }
                            // INFORMACOES DE VOO
                            case 3 -> {
                                Linha();
                                System.out.println("Informacoes de voos");
                                int opcaoTemp;
                                do {
                                    Linha();
                                    // Escolha do passageiro entre as opções.
                                    opcaoTemp = PegaNum("1 - Voos disponiveis\n2 - Voos com reservas pendentes\n3 - Voos cheios\n0 - Voltar\n-> ", 0, 3, "Opcao invalida.");
                                    Linha();
                                    
                                    switch (opcaoTemp) {
                                        // VOOS DISPONIVEIS.
                                        case 1 -> { 
                                            System.out.println("VOOS COM RESERVAS DISPONIVEIS");
                                            Linha();
                                            
                                            int c = 0;
                                            
                                            // Se existir voos executa o código, se não mostra uma mensagem de erro.
                                            if (!voos.isEmpty()) {
                                                for (String[] voo : voos) {
                                                    if (Integer.parseInt(voo[4]) > Integer.parseInt(voo[5])) {
                                                        System.out.println("Partida: " + voo[1] + " | Destino: " + voo[2] + " | Horario: " + voo[3] + " | Maximo de passageiros: " + voo[4] + " | Presentes: " + voo[5]);
                                                        
                                                        c++;
                                                    }
                                                }
                                                
                                                if (c == 0) {
                                                    System.out.println("NENHUM VOO DISPONIVEL");
                                                }
                                            }
                                            else {
                                                System.out.println("NENHUM VOO DISPONIVEL");
                                            }
                                            
                                        } 
                                        // VOOS COM RESERVAS PENDENTES.
                                        case 2 -> {
                                            System.out.println("VOOS COM RESERVAS PENDENTES");
                                            Linha();
                                            int c = 0;
                                            
                                            if (!reservasPendentes.isEmpty()) {
                                               // Escreve todos as reservas pendentes.
                                                for (String[] reserva : reservasPendentes) {
                                                    System.out.println("Nome: " + reserva[0] + " | Idade: " + reserva[1] + " | CPF: " + reserva[2] + " | E-mail: " + reserva[3] + " | Codigo voo: " + reserva[4]);
                                                    c++;
                                                } 
                                                
                                                if (c == 0) {
                                                    System.out.println("NENHUMA RESERVA PENDENTE.");
                                                }
                                            }
                                            else {
                                                System.out.println("NENHUMA RESERVA PENDENTE.");
                                            }
                                            
                                        }
                                        // VOOS CHEIOS.
                                        case 3 -> { 
                                            System.out.println("VOOS CHEIOS");
                                            Linha();
                                            int c = 0;
                                            
                                            // Se não existir voos mostra uma mensagem de erro.
                                            if (!voos.isEmpty()) {
                                                // Mostra os voos cheios.
                                                for (String[] voo : voos) {
                                                    if (Integer.parseInt(voo[4]) == Integer.parseInt(voo[5])) {
                                                        System.out.println("Partida: " + voo[1] + " | Destino: " + voo[2] + " | Horario: " + voo[3] + " | Maximo de passageiros: " + voo[4] + " | Presentes: " + voo[5]);
                                                        c++;
                                                    }
                                                }
                                                
                                                if (c == 0) {
                                                    System.out.println("NENHUM VOO LOTADO.");
                                                }
                                            }
                                            else {
                                                System.out.println("NENHUM VOO ENCONTRADO.");
                                            }
                                        }
                                    }
                                } while (opcaoTemp != 0);
                                break;
                            }
                        }
                    } while (opcaoPassageiro != 0);
                    break;
                }
                // ADMINISTRADOR
                case 2 -> {
                    // Usuário escolhido foi o administrador.
                    // Login do usuário.
                    System.out.print("Login: ");
                    login = dado.readLine();
                    // Senha do usuário.
                    System.out.print("Senha: ");
                    senha = dado.readLine();
                    // Se o usuário e senha forem os corretos, inicia a sessão.
                    if (login.equals(adm[0]) && senha.equals(adm[1])) {
                        while (true) {
                            Linha();
                            opcaoAdm = PegaNum("1 - Processamento de reservas\n2 - Agendar novo voo\n0 - Escolher outro usuario\n-> ", 0, 2, "Opcao invalida.");
                            
                            if (opcaoAdm == 0) {
                                break;
                            }
                            
                            // Escolhe qual opção será mostrada ao ADM com base na opcaoAdm.
                            switch (opcaoAdm){
                                // PROCESSAMENTO DE RESERVAS.
                                case 1 -> {
                                    int confirmacaoReserva; // 1 = Confirmado; 2 = Não confirmado.
                                    String[] reservaParaConfirmacao = null; // Proxima reserva na fila.
                                    String[] vooParaConfirmacao = null; // Voo relacionado a reserva.
                                    
                                    if (!reservasPendentes.isEmpty()) {
                                        reservaParaConfirmacao = reservasPendentes.peek(); // Pega a proxima reserva na fila.
                                        
                                        // Pega o voo relacionado a reserva.
                                        for (String[] voo : voos) {
                                            if (reservaParaConfirmacao[4].equals(voo[0])) {
                                                vooParaConfirmacao = voo;
                                                break;
                                            }
                                        }
                                        
                                        // Mostras as informações da reserva e do voo.
                                        Linha();
                                        System.out.println("INFORMACOES DA RESERVA");
                                        Linha();
                                        System.out.println("Nome: " + reservaParaConfirmacao[0]);
                                        System.out.println("Idade: " + reservaParaConfirmacao[1]);
                                        System.out.println("CPF: " + reservaParaConfirmacao[2]);
                                        System.out.println("E-mail: " + reservaParaConfirmacao[3]);
                                        System.out.println("Voo: " + vooParaConfirmacao[0] + " - Saindo de: " + vooParaConfirmacao[1] + ", Destino: " + vooParaConfirmacao[2] + ", Horario: " + vooParaConfirmacao[3]);
                                        System.out.println("Quantidade maxima de pessoas: " + vooParaConfirmacao[4] + "; Quantidade de pessoas presentes: " + vooParaConfirmacao[5]);
                                        Linha();
                                        
                                        OUTER:
                                        while (true) {
                                            confirmacaoReserva = PegaNum("1 - Confirmar\n2 - Deixar pendente\n3 - Excluir reserva\n-> ", 1, 3, "Opcao invalida.");
                                            // Se houver confirmação.
                                            switch (confirmacaoReserva) {
                                                // CONFIRMAR RESERVA.
                                                case 1 -> {
                                                    // Verifica se o voo está lotado. Se estiver volta para o ínicio.
                                                    if (Integer.parseInt(vooParaConfirmacao[4]) == Integer.parseInt(vooParaConfirmacao[5])) {
                                                        Linha();
                                                        System.out.println("VOO ESTA LOTADO.");
                                                        Linha();
                                                        continue;
                                                    }
                                                    
                                                    // Pega a quantidade de passageiros naquele voo e soma mais um.
                                                    int qtdVoo = Integer.parseInt(vooParaConfirmacao[5]) + 1;
                                                    // Substitui a quantidade antiga pela nova.
                                                    vooParaConfirmacao[5] = String.valueOf(qtdVoo);
                                                    // Remove o primeiro item da fila.
                                                    reservasPendentes.poll();
                                                    // Adiciona a reserva confirmada na lista de reservas confirmadas.
                                                    reservasConfirmadas.add(new String[]{reservaParaConfirmacao[0], reservaParaConfirmacao[1], reservaParaConfirmacao[2], reservaParaConfirmacao[3], reservaParaConfirmacao[4]});
                                                    Linha();
                                                    System.out.println("RESERVA CONFIRMADA COM SUCESSO.");
                                                    break OUTER;
                                                }
                                                case 2 -> {
                                                    Linha();
                                                    System.out.println("RESERVA DEIXADA COMO PENDENTE.");
                                                    break OUTER;
                                                }
                                                case 3 -> {
                                                    // Remove o primeiro item da fila.
                                                    reservasPendentes.poll();
                                                    Linha();
                                                    System.out.println("RESERVA EXCLUIDA COM SUCESSO.");
                                                    break OUTER;
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        Linha();
                                        System.out.println("NAO HA RESERVAS PARA PROCESSAR");
                                    }
                                }
                                // CRIAÇÃO DE VOOS.
                                case 2 -> {
                                    Linha();
                                    System.out.println("CRIACAO DE UM NOVO VOO");
                                    // Criando um número de 4 digitos para ser usado como código.
                                    numeroVoo = Integer.toString(random.nextInt(9000) + 1000);
                                    
                                    Linha();
                                    // Origim do voo.
                                    System.out.print("Digite a origim do voo: ");
                                    origem = dado.readLine();
                                    
                                    // Destino do voo.
                                    System.out.print("Digite o destino do voo: ");
                                    destino = dado.readLine();
                                    
                                    // Horário de partida.
                                    System.out.print("Digite o horario de partida: ");
                                    horarioPartida = dado.readLine();
                                    
                                    // Quantidade máxima de pessoas.
                                    qtdMaxPessoa = Integer.toString(PegaNum("Digite a quantidade maxima de pessoas: ", 0, 250, "Maximo de passageiros ultrapassa o maximo da aeronave."));
                                    
                                    // Adicionando o vetor com os dados do voo para a Array de voos.
                                    voos.add(new String[]{numeroVoo, origem, destino, horarioPartida, qtdMaxPessoa, "0"});
                                    
                                    Linha();
                                    System.out.println("VOO CRIADO COM SUCESSO.");
                                }
                                default -> {
                                    Linha();
                                    System.out.println("Digite uma opcao valida.");
                                }
                            }
                        }
                    }
                    break;
                }
                // SAIR
                case 3 -> {
                    System.out.println("SAINDO DO SISTEMA");
                    Linha();
                    break OUTER_1;
                }
                default -> {
                }
            }
        }
    }
    
    // Recebe um texto para ser usado como pergunta.
    // Retorna o cpf válido.
    public static String PegaCpf(String texto) throws IOException {
        DataInputStream dado; // Será usado para fazer pergunta ao usuário
        String cpf; // String com a resposta do usuário.
        
        // Repete até o usuário digitar uma String válida.
        while (true) {
            System.out.print(texto); // Texto que será usado como pergunta.
            dado = new DataInputStream(System.in); // Inicia o DataInputStream.
            cpf = dado.readLine(); // Faz a pergunta ao usuário, salvando uma String como resposta.
            
            char caracter; // Será usado para pegar cada caracter separado em cpf.
            int numCPF; // Será usado para converter o 'caracter' em número. Fazendo a verificação se todos os caracteres são números. 
            boolean cpfValido;
            
            // Se o cpf não tiver 11 números volta para o ínicio do loop.
            if (cpf.length() != 11) {
                Linha();
                System.out.println("CPF deve conter 11 digitos.");
                Linha();
                continue;
            }
            
            cpfValido = true;
            
            // Pega cade caracter do cpf e verifica se é um número.
            for (int i = 0; i < cpf.length(); i++) {
                caracter = cpf.charAt(i); // Cada caracter do cpf.
                numCPF = Character.getNumericValue(caracter); // Converte para seu valor número.
                
                // Se o valor número não estiver entre 0 e 9. Ele não é um número.
                if (numCPF < 0 || numCPF > 9) {
                    Linha();
                    System.out.println("CPF deve ser composto somente por numeros.");
                    Linha();
                    cpfValido = false;
                    break;
                }
            }
            
            // Se o cpf for válido sai do loop.
            if (cpfValido) {
               break; 
            }
        }
        
        // Retorna o returncpf.
        return cpf;
    }
    
    
    // Recebe um texto para ser usado como pergunta, numero minimo e maximo, e um texto de erro.
    // Retorna um número inteiro, filtrando o erro de conversão de String para Integer.
    public static int PegaNum(String texto, int min, int max, String erro) throws IOException {
        DataInputStream dado; // Será usado para fazer pergunta ao usuário
        String s; // String com a resposta do usuário.
        int num; // Número filtrado pelo sistema.
        
        // Repete até o usuário digitar um número válido.
        while (true) {
            System.out.print(texto); // Texto que será usado como pergunta.
            dado = new DataInputStream(System.in); // Inicia o DataInputStream.
            s = dado.readLine(); // Faz a pergunta ao usuário, salvando uma String como resposta.
            
            // Tenta converter a String para Integer.
            // Caso consiga converter, é sinal que o usuário digitou um número. Terminando o loop.
            // Caso haja algum erro, mostra a mensagem de erro e repete o loop. 
            try {
                num = Integer.parseInt(s);
                if (num >= min && num <= max) {
                    break;
                }
                else {
                    Linha();
                    System.out.println(erro);
                    Linha();
                }
            }
            catch (NumberFormatException e) {
                Linha();
                System.out.println("Digite somente numeros.");
                Linha();
            }
        }
        
        return num; // Retorna o número.
    }

    
    // Escreve uma linha para separação.
    public static void Linha() {
        System.out.println("----------------------------------------");
    }
}
