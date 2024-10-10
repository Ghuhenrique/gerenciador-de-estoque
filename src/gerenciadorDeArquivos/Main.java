package gerenciadorDeArquivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {	
	/*Desenvolver um sistema para gerenciamento de estoque que permita o controle de entrada e saíde de  produtos. O sistema deverá permitir ao usuário cadastrar novos produtos, atualizar quantidades em estoque, registrar saídas (vendas ou usos) e gerar relatórios de estoque com base nos dados armazenados. 
	Deve haver um arquivo de texto ou CSV que armazene os dados dos produtos, incluído nome, código, quantidade, preço e data de validade (opcional);
	O sistema deve permitir a busca de produtos por nome ou código;
	Um relatório de inventário deve ser gerado mostrando o status atual do estoque (produtos disponíveis, em falta e etc.).
	O programa deverá ser desenvolvido sem o uso de conceitos de programação orientada a objetos e cada implementação deve envolver o uso de: estruturas de controle (condicionais, laços); funções (modularização); manipulação de arquivos (leitura e escrita); estrutura de dados simples (arrays, vetores, matrizes).
	*/
	
	/* Curso: 	Sistemas para Internet (1º Período)
	 * Matéria: Algoritmos e Lógica de Programação
	 * Professor: Ms. Natan Rodrigues
	 * Alunos: 	Gustavo Henrique Costa Pinto
	 * 			Bartolomeu SpegiorinGusella
	 * 			Luan Santos Cavalcante
	 */
	
	private static final String SEPARADOR = ";";
	private static final String NOME_ARQUIVO = "produto.csv";
	
	
	// Função para ler produtos de um arquivo CSV
    public static List<String[]> lerProdutos(String arquivo) {
        ArrayList<String[]> produtos = new ArrayList<String[]>(); // Uso de ArrayList para uma Array de tamanho variavel
        String linha;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            while ((linha = br.readLine()) != null) {
                String[] dadosProduto = linha.split(SEPARADOR); // Separação por vírgula
                produtos.add(dadosProduto);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return produtos;
    }

    // Função para salvar produtos no arquivo CSV
    public static void salvarProdutos(List<String[]> produtos, String arquivo) {
        try (FileWriter fw = new FileWriter(arquivo)) {
            for (String[] produto : produtos) {
                fw.write(String.join(SEPARADOR, produto) + "\n");    
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Função para adicionar novos produtos
    public static void adicionarProduto(List<String[]> produtos, Scanner scanner) {
        
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
        
        String[] dadosNovos = {nome, codigo, quantidade, preco, validade};
        produtos.add(dadosNovos);
    }
    
    // Função para atualizar a quantidade de um produto existente
    public static void registrarEntrada(List<String[]> produtos, String codigo, int novaQuantidade) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i)[1].equals(codigo)) {
                produtos.get(i)[2] = String.valueOf(Integer.parseInt(produtos.get(i)[2]) + novaQuantidade);
                break;
            }
        }
    }

    // Função para registrar saída de produtos (venda/uso)
    public static void registrarSaida(List<String[]> produtos, String codigo, int quantidade) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i)[1].equals(codigo)) {
                int estoqueAtual = Integer.parseInt(produtos.get(i)[2]);
                if (estoqueAtual >= quantidade) {
                    produtos.get(i)[2] = String.valueOf(estoqueAtual - quantidade);
                } else {
                    System.out.println("Estoque insuficiente.");
                }
                break;
            }
        }
    }

    // Função para buscar produtos por nome ou código
    public static void buscarProduto(List<String[]> produtos, String chave) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i)[0].equalsIgnoreCase(chave) || produtos.get(i)[1].equals(chave)) {
                System.out.println("Produto encontrado: ");
                System.out.println("Nome: " + produtos.get(i)[0]);
                System.out.println("Código: " + produtos.get(i)[1]);
                System.out.println("Quantidade: " + produtos.get(i)[2]);
                System.out.println("Preço: " + produtos.get(i)[3]);
                System.out.println("Data de Validade: " + produtos.get(i)[4]);
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    // Função para gerar o relatório do inventário
    public static void gerarRelatorio(List<String[]> produtos) {
        System.out.println("Relatório de Inventário:");
        System.out.println("Nome | Código | Quantidade | Preço | Data de Validade");

        for (int i = 0; i < produtos.size(); i++) {
        	
        	String status;
        	int quantidade = Integer.parseInt(produtos.get(i)[2]);
        	if(quantidade > 0) {
        		status = "disponivel";
        	}else {
        		status = "em falta";
        	}
        	
        	String validade = produtos.get(i).length < 5 ? "" : produtos.get(i)[4];
        	
            System.out.println(produtos.get(i)[0] + " | " + produtos.get(i)[1] + " | " + produtos.get(i)[2] + " | " + produtos.get(i)[3] + " | " + validade + " | " + status);    
        }
    }

    // Função principal (main)
    public static void main(String[] args) {
        List<String[]> produtos = lerProdutos(NOME_ARQUIVO);
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;
        while (continua) {
            System.out.println("-------ESCOLHA UMA OPÇÃO-------");
            System.out.println("Adicionar Produto ...........1;");
            System.out.println("Atualizar Estoque ...........2;");
            System.out.println("Registrar Saída .............3;");
            System.out.println("Buscar Produto ..............4;");
            System.out.println("Gerar Relatório de Estoque ..5;");
            System.out.println("Sair ........................6;");

            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    adicionarProduto(produtos, scanner);
                    salvarProdutos(produtos, NOME_ARQUIVO);
                    break;
                case 2:
                    System.out.print("Informe o código do produto: ");
                    String codigo = scanner.nextLine();
                    
                    System.out.println("O valor de codigo eh '"+codigo);
                    System.out.print("Quantidade a adicionar: ");
                    int qtd = Integer.parseInt(scanner.nextLine());
                    registrarEntrada(produtos, codigo, qtd);
                    salvarProdutos(produtos, NOME_ARQUIVO);
                    break;
                case 3:
                    System.out.print("Informe o código do produto: ");
                    String codigoSaida = scanner.nextLine();
                    System.out.print("Quantidade a retirar: ");
                    int qtdSaida = scanner.nextInt();
                    scanner.nextLine();
                    registrarSaida(produtos, codigoSaida, qtdSaida);
                    salvarProdutos(produtos, NOME_ARQUIVO);
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
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
            
        }
        
    }
		
}
