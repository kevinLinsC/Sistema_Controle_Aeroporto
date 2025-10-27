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
        Queue<String[]> reservas = new LinkedList<>();
        
        // VARIAVEIS DO ADM
        String adm[] = {"adm123", "adm123"}; // Usuário e senha do adm.
        boolean isAdm = false; // Variavel que controla se é adm ou não. 
        String login, senha;
        int opcaoAdm; // Opções de navegação do adm.
        
        // VARIAVEIS DO PASSAGEIRO
        int opcaoPassageiro; // Opções de navegação do passageiro.
        String nome, cpf, email, idade;
        
        while (true) {
            // Escolha de usuário.
            Linha();
            opcaoUsuario = PegaNum("Escolha o tipo de usuario: \n1 - Passageiro\n2 - Administrador\n-> ");
            Linha();

            if (opcaoUsuario == 1) {
                // Faz enquanto "opcaoPassageiro" for diferente de zero.
                do {
                    // Escolha de opções do passageiro.
                    opcaoPassageiro = PegaNum("1 - Reserva de voos \n2 - Check-in\n3 - Informacoes de voos\n0 - Escolher outro usuario\n-> ");
                    
                    switch (opcaoPassageiro) {
                        case 1:
                            Linha();
                            for (String[] vetor : voos) {
                                System.out.println(Arrays.toString(vetor));
                            }
                            System.out.print("Digite seu nome: ");
                            nome = dado.readLine();

                            idade = Integer.toString(PegaNum("Digite sua idade: "));

                            System.out.print("Digite seu CPF: ");
                            cpf = dado.readLine();

                            System.out.print("Digite seu e-mail: ");
                            email = dado.readLine();

                            reservas.add(new String[]{nome, idade, cpf, email});
                            break;
                        case 2:
                            Linha();
                            System.out.println("Check-in");
                            break;
                        case 3:
                            System.out.println("Informacoes de voos");
                            int opcaoTemp;
                            do {
                                // Escolha do passageiro entre as opções.
                                Linha();
                                opcaoTemp = PegaNum("1 - Voos disponiveis\n2 - Voos com reservas pendentes\n3 - Voos cheios\n0 - Voltar\n-> ");
                                Linha();
                                
                                switch (opcaoTemp) {
                                    case 1: // Voos disponiveis.
                                        voos.add(new String[]{"9999", "Cariacica", "Vitoria", "12:00", "5", "0"});
                                        voos.add(new String[]{"9999", "A", "B", "12:00", "5", "5"});
                                        for (String[] voo : voos) {
                                            if (Integer.parseInt(voo[4]) > Integer.parseInt(voo[5])) {
                                                System.out.println(Arrays.toString(voo));
                                            }
                                        }
                                        break;
                                    case 2: // Voos com reservas pendentes.
                                        System.out.println("Voos com reservas pendentes");
                                        break;
                                    case 3: // Voos cheios.
                                        voos.add(new String[]{"9999", "Cariacica", "Vitoria", "12:00", "5", "0"});
                                        voos.add(new String[]{"9999", "A", "B", "12:00", "5", "5"});
                                        for (String[] voo : voos) {
                                            if (Integer.parseInt(voo[4]) == Integer.parseInt(voo[5])) {
                                                System.out.println(Arrays.toString(voo));
                                            }
                                        }
                                        break;
                                }
                            } while (opcaoTemp != 0);
                            
                            break;
                        default:
                            break;
                    }
                } while (opcaoPassageiro != 0); 
            }
            else if (opcaoUsuario == 2) {
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
                        opcaoAdm = PegaNum("1 - Processamento de reservas\n2 - Agendar novo voo\n0 - Escolher outro usuario\n-> ");
                            
                        if (opcaoAdm == 0) {
                            break;
                        }

                        // Escolhe qual opção será mostrada ao ADM com base na opcaoAdm.
                        switch (opcaoAdm){
                            case 1: // PROCESSAMENTO DE RESERVAS.
                                System.out.println("Processamento de reservas");

                            case 2: // CRIAÇÃO DE VOOS.
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
