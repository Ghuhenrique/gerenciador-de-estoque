package gerenciadorDeArquivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {	
	/*Desenvolver um sistema para gerenciamento de estoque que permita o controle de entrada e saíde de  produtos. O sistema deverá permitir ao usuário cadastrar novos produtos, atualizar quantidades em estoque, registrar saídas (vendas ou usos) e gerar relatórios de estoque com base nos dados armazenados. 
	Deve haver um arquivo de texto ou CSV que armazene os dados dos produtos, incluído nome, código, quantidade, preço e data de validade (opcional);
	O sistema deve permitir a busca de produtos por nome ou código;
	Um relatório de inventário deve ser gerado mostrando o status atual do estoque (produtos disponíveis, em falta e etc.).
	O programa deverá ser desenvolvido sem o uso de conceitos de programação orientada a objetos e cada implementação deve envolver o uso de: estruturas de controle (condicionais, laços); funções (modularização); manipulação de arquivos (leitura e escrita); estrutura de dados simples (arrays, vetores, matrizes).
	*/
	
	/* Prova de 
	 * 
	 * 
	 * Sistema gerenciador de estoque.
	 * Alunos: Gustavo Henrique Costa Pinto
	 * */
	
	
	// Função para ler produtos de um arquivo CSV
    public static String[][] lerProdutos(String arquivo) {
        String[][] produtos = new String[100][5]; // Supondo que teremos até 100 produtos
        String linha;
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            while ((linha = br.readLine()) != null) {
                String[] dadosProduto = linha.split(","); // Separação por vírgula
                for (int i = 0; i < dadosProduto.length; i++) {
                    produtos[contador][i] = dadosProduto[i];
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return produtos;
    }

    // Função para salvar produtos no arquivo CSV
    public static void salvarProdutos(String[][] produtos, String arquivo) {
        try (FileWriter fw = new FileWriter(arquivo)) {
            for (String[] produto : produtos) {
                if (produto[0] != null) {
                    fw.write(String.join(",", produto) + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Função para adicionar novos produtos
    public static void adicionarProduto(String[][] produtos) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        System.out.print("Quantidade: ");
        String quantidade = scanner.nextLine();
        System.out.print("Preço: ");
        String preco = scanner.nextLine();
        System.out.print("Data de validade (opcional): ");
        String validade = scanner.nextLine();

        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][0] == null) {
                produtos[i][0] = nome;
                produtos[i][1] = codigo;
                produtos[i][2] = quantidade;
                produtos[i][3] = preco;
                produtos[i][4] = validade;
                break;
            }
        }
    }
    
    // Função para atualizar a quantidade de um produto existente
    public static void atualizarEstoque(String[][] produtos, String codigo, int novaQuantidade) {
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][1] != null && produtos[i][1].equals(codigo)) {
                produtos[i][2] = String.valueOf(Integer.parseInt(produtos[i][2]) + novaQuantidade);
                break;
            }
        }
    }

    // Função para registrar saída de produtos (venda/uso)
    public static void registrarSaida(String[][] produtos, String codigo, int quantidade) {
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][1] != null && produtos[i][1].equals(codigo)) {
                int estoqueAtual = Integer.parseInt(produtos[i][2]);
                if (estoqueAtual >= quantidade) {
                    produtos[i][2] = String.valueOf(estoqueAtual - quantidade);
                } else {
                    System.out.println("Estoque insuficiente.");
                }
                break;
            }
        }
    }

    // Função para buscar produtos por nome ou código
    public static void buscarProduto(String[][] produtos, String chave) {
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][0] != null && (produtos[i][0].equalsIgnoreCase(chave) || produtos[i][1].equals(chave))) {
                System.out.println("Produto encontrado: ");
                System.out.println("Nome: " + produtos[i][0]);
                System.out.println("Código: " + produtos[i][1]);
                System.out.println("Quantidade: " + produtos[i][2]);
                System.out.println("Preço: " + produtos[i][3]);
                System.out.println("Data de Validade: " + produtos[i][4]);
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    // Função para gerar o relatório do inventário
    public static void gerarRelatorio(String[][] produtos) {
        System.out.println("Relatório de Inventário:");
        System.out.println("Nome | Código | Quantidade | Preço | Data de Validade");

        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][0] != null) {
                String status = Integer.parseInt(produtos[i][2]) > 0 ? "Disponível" : "Em falta";
                System.out.println(produtos[i][0] + " | " + produtos[i][1] + " | " + produtos[i][2] + " | " + produtos[i][3] + " | " + produtos[i][4] + " | " + status);
            }
        }
    }

    // Função principal (main)
    public static void main(String[] args) {
        String[][] produtos = lerProdutos("produtos.csv");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Atualizar Estoque");
            System.out.println("3. Registrar Saída");
            System.out.println("4. Buscar Produto");
            System.out.println("5. Gerar Relatório de Estoque");
            System.out.println("6. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    adicionarProduto(produtos);
                    salvarProdutos(produtos, "produtos.csv");
                    break;
                case 2:
                    System.out.print("Informe o código do produto: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Quantidade a adicionar: ");
                    int qtd = scanner.nextInt();
                    atualizarEstoque(produtos, codigo, qtd);
                    salvarProdutos(produtos, "produtos.csv");
                    break;
                case 3:
                    System.out.print("Informe o código do produto: ");
                    String codigoSaida = scanner.nextLine();
                    System.out.print("Quantidade a retirar: ");
                    int qtdSaida = scanner.nextInt();
                    registrarSaida(produtos, codigoSaida, qtdSaida);
                    salvarProdutos(produtos, "produtos.csv");
                    break;
                case 4:
                    System.out.print("Informe o nome ou código do produto: ");
                    String chave = scanner.nextLine();
                    buscarProduto(produtos, chave);
                    break;
                case 5:
                    gerarRelatorio(produtos);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
            
        }
        
    }
		
}
