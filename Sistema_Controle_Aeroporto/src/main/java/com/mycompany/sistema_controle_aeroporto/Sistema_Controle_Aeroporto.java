/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_controle_aeroporto;

import java.io.DataInputStream; // Usado para receber dados do usuário.
import java.io.IOException; // Tratamento de erros.
import java.util.ArrayList; 
import java.util.Arrays;
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
        // ADICIONANDO VOOS ALEATORIOS. TIRAR DPS
        voos.add(new String[]{"1234", "Algum lugar", "Outro lugar", "12:00", "5", "0"});
        voos.add(new String[]{"4321", "Outro lugar", "Algum lugar", "18:00", "10", "0"});
        voos.add(new String[]{"9999", "WRLD", "Another WRLD", "99:99", "999", "997"});
        
        // VARIAVEIS DE RESERVAS DE VOOS
        Queue<String[]> reservasPendentes = new LinkedList<>();
        Queue<String[]> reservasConfirmadas = new LinkedList<>();
        reservasPendentes.add(new String[]{"kevin", "19", "15015015015", "kevin@gmail.com", "9999"});
        reservasPendentes.add(new String[]{"lyandra", "18", "15015015015", "lyandra@gmail.com", "9999"});
        reservasPendentes.add(new String[]{"trinta", "34", "15015015015", "trinta@gmail.com", "4321"});
        
        // VARIAVEIS DO ADM
        String adm[] = {"adm123", "adm123"}; // Usuário e senha do adm.
        boolean isAdm = false; // Variavel que controla se é adm ou não. 
        String login, senha;
        int opcaoAdm; // Opções de navegação do adm.
        
        // VARIAVEIS DO PASSAGEIRO
        int opcaoPassageiro; // Opções de navegação do passageiro.
        int opcaoVooPassageiro;
        String nome, cpf, email, idade;
        
        // VARIAVEIS DO CHECK-IN
        Stack<String[]> checkin = new Stack<>();
        
        while (true) {
            // Escolha de usuário.
            Linha();
            opcaoUsuario = PegaNum("Escolha o tipo de usuario: \n1 - Passageiro\n2 - Administrador\n-> ");
            Linha();
            
            // Usuário escolhido foi o passageiro.
            if (opcaoUsuario == 1) {
                // Faz enquanto "opcaoPassageiro" for diferente de zero.
                do {
                    // Escolha de opções do passageiro.
                    opcaoPassageiro = PegaNum("1 - Reserva de voos \n2 - Check-in\n3 - Informacoes de voos\n0 - Escolher outro usuario\n-> ");
                    
                    switch (opcaoPassageiro) {
                        case 1 -> { // Reserva de voos.
                            Linha();
                            // Escreve todos os voos disponiveis.
                            for (String[] voo : voos) {
                                if (Integer.parseInt(voo[4]) > Integer.parseInt(voo[5])) {
                                    System.out.println(voo[0] + " - Saindo de: " + voo[1] + ", Destino: " + voo[2] + ", Horario: " + voo[3]);
                                }
                            }
                            
                            Linha();
                            // Usuário escolhe o voo.
                            opcaoVooPassageiro = PegaNum("Escolha um dos voos acima: ");
                            
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
                                
                                // Pega as informações do usuário.
                                System.out.print("Digite seu nome: ");
                                nome = dado.readLine();

                                idade = Integer.toString(PegaNum("Digite sua idade: "));

                                System.out.print("Digite seu CPF: ");
                                cpf = dado.readLine();

                                System.out.print("Digite seu e-mail: ");
                                email = dado.readLine();
                                
                                // Adiciona a reserva na fila.
                                reservasPendentes.add(new String[]{nome, idade, cpf, email, vooSelecionado[0]});
                                System.out.println(Arrays.toString(reservasPendentes.peek()));
                                Linha();
                            }
                        }
                        case 2 -> {
                            Linha();
                            System.out.println("Check-in");
                            int confirmacaoCheckin; // 1 = Confirmado; 2 = Não confirmado.
                            String[] reservaCheckin = null; // Proxima reserva confirmada na fila.
                            String[] vooReserva = null; // Voo relacionado a reserva confirmada.

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
                            System.out.println("Nome: " + reservaCheckin[0]);
                            System.out.println("Idade: " + reservaCheckin[1]);
                            System.out.println("CPF: " + reservaCheckin[2]);
                            System.out.println("E-mail: " + reservaCheckin[3]);
                            System.out.println("Voo: " + vooReserva[0] + " - Saindo de: " + vooReserva[1] + ", Destino: " + vooReserva[2] + ", Horario: " + vooReserva[3]);
                            Linha();

                            // Confirmação da reserva.
                            confirmacaoCheckin = PegaNum("1 - Confirmar Check-in\n2 - Deixar pendente\n-> ");

                            // Se houver confirmação.
                            if (confirmacaoCheckin == 1) {
                                // Remove o primeiro item da fila.
                                reservasConfirmadas.poll();
                                // Adiciona a reserva confirmada na lista de reservas confirmadas.
                                checkin.push(new String[]{reservaCheckin[0], reservaCheckin[1], reservaCheckin[2], reservaCheckin[3], reservaCheckin[4]}); 
                            }
                            
                            System.out.println(Arrays.toString(checkin.peek()));
                        }
                        case 3 -> {
                            System.out.println("Informacoes de voos");
                            int opcaoTemp;
                            do {                 
                                Linha();
                                // Escolha do passageiro entre as opções.
                                opcaoTemp = PegaNum("1 - Voos disponiveis\n2 - Voos com reservas pendentes\n3 - Voos cheios\n0 - Voltar\n-> ");
                                Linha();
                                
                                switch (opcaoTemp) {
                                    case 1 -> { // VOOS DISPONIVEIS.
                                        voos.add(new String[]{"9999", "Cariacica", "Vitoria", "12:00", "5", "0"});
                                        voos.add(new String[]{"9999", "A", "B", "12:00", "5", "5"});
                                        for (String[] voo : voos) {
                                            if (Integer.parseInt(voo[4]) > Integer.parseInt(voo[5])) {
                                                System.out.println(Arrays.toString(voo));
                                            }
                                        }
                                    }
                                    case 2 -> { // VOOS COM RESERVAS PENDENTES.
                                        System.out.println("VOOS COM RESERVAS PENDENTES");
                                        Linha();
                                        
                                        for (String[] reserva : reservasPendentes) {
                                            System.out.println(Arrays.toString(reserva));
                                        }
                                    }
                                    case 3 -> { // VOOS CHEIOS.
                                        voos.add(new String[]{"9999", "Cariacica", "Vitoria", "12:00", "5", "0"});
                                        voos.add(new String[]{"9999", "A", "B", "12:00", "5", "5"});
                                        for (String[] voo : voos) {
                                            if (Integer.parseInt(voo[4]) == Integer.parseInt(voo[5])) {
                                                System.out.println(Arrays.toString(voo));
                                            }
                                        }
                                    }
                                }
                            } while (opcaoTemp != 0);
                        }
                        default -> {
                        }
                    }
                } while (opcaoPassageiro != 0); 
            }
            else if (opcaoUsuario == 2) { // Usuário escolhido foi o administrador.
                // Login do usuário.
                System.out.print("Login: ");
                login = dado.readLine();

                // Senha do usuário.
                System.out.print("Senha: ");
                senha = dado.readLine();       

                // Se o usuário e senha forem os corretos, inicia a sessão.
                if (login.equals(adm[0]) && senha.equals(adm[1])) {
                    isAdm = true;
                    while (true) {
                        Linha();
                        opcaoAdm = PegaNum("1 - Processamento de reservas\n2 - Agendar novo voo\n0 - Escolher outro usuario\n-> ");
                            
                        if (opcaoAdm == 0) {
                            break;
                        }

                        // Escolhe qual opção será mostrada ao ADM com base na opcaoAdm.
                        switch (opcaoAdm){
                            case 1 -> { // PROCESSAMENTO DE RESERVAS.
                                int confirmacaoReserva; // 1 = Confirmado; 2 = Não confirmado.
                                String[] reservaParaConfirmacao = null; // Proxima reserva na fila.
                                String[] vooParaConfirmacao = null; // Voo relacionado a reserva.
                                
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
                                System.out.println("Nome: " + reservaParaConfirmacao[0]);
                                System.out.println("Idade: " + reservaParaConfirmacao[1]);
                                System.out.println("CPF: " + reservaParaConfirmacao[2]);
                                System.out.println("E-mail: " + reservaParaConfirmacao[3]);
                                System.out.println("Voo: " + vooParaConfirmacao[0] + " - Saindo de: " + vooParaConfirmacao[1] + ", Destino: " + vooParaConfirmacao[2] + ", Horario: " + vooParaConfirmacao[3]);
                                Linha();
                                
                                // Confirmação da reserva.
                                confirmacaoReserva = PegaNum("1 - Confirmar\n2 - Deixar pendente\n-> ");
                                
                                // Se houver confirmação.
                                if (confirmacaoReserva == 1) {
                                    // Pega a quantidade de passageiros naquele voo e soma mais um.
                                    int qtdVoo = Integer.parseInt(vooParaConfirmacao[5]) + 1;
                                    // Substitui a quantidade antiga pela nova.
                                    vooParaConfirmacao[5] = String.valueOf(qtdVoo);
                                    
                                    // Remove o primeiro item da fila.
                                    reservasPendentes.poll();
                                    // Adiciona a reserva confirmada na lista de reservas confirmadas.
                                    reservasConfirmadas.add(new String[]{reservaParaConfirmacao[0], reservaParaConfirmacao[1], reservaParaConfirmacao[2], reservaParaConfirmacao[3], reservaParaConfirmacao[4]}); 
                                }
                            }
                            case 2 -> { // CRIAÇÃO DE VOOS.
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
                                qtdMaxPessoa = Integer.toString(PegaNum("Digite a quantidade maxima de pessoas: "));

                                // Adicionando o vetor com os dados do voo para a Array de voos.
                                voos.add(new String[]{numeroVoo, origem, destino, horarioPartida, qtdMaxPessoa, "0"});

                                // Escreve as informações do voo.
                                for (String[] voo : voos) {
                                    System.out.println(Arrays.toString(voo));
                                }
                            }
                        }
                    }  
                }
            }
        }
    }
    
    // Recebe um texto para ser usado como pergunta.
    // Retorna um número inteiro, filtrando o erro de conversão de String para Integer.
    public static int PegaNum(String texto) throws IOException {
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
                break;
            }
            catch (NumberFormatException e) {
                Linha();
                System.out.println("Digite somente numeros.");
                Linha();
            }
        }
        
        return num; // Retorna o número.
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    // Escreve uma linha para separação.
    public static void Linha() {
        System.out.println("----------------------------------------");
    }
}
