#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <locale.h>

#define MAX 801

typedef struct {
    int identificacao;
    int geracao;
    char nome[50];
    char descricao[200];
    char tipos[2][20];
    char habilidades[200];
    double peso;
    double altura;
    int taxaCaptura;
    bool ehLendario;
    struct tm dataCaptura;
} Pokemon;

// Vetores globais
Pokemon pokedex[MAX];
Pokemon selecionados[MAX];
int qtdSelecionados = 0;
int comparacoes = 0;
clock_t tempoExecucao;

// Função para trocar dois elementos do array
void trocar(int a, int b) {
    Pokemon tmp = selecionados[a];
    selecionados[a] = selecionados[b];
    selecionados[b] = tmp;
}

// Função recursiva para encontrar o índice do menor elemento
int encontrarMenor(int inicio, int n) {
    if (inicio == n - 1) {
        return inicio;
    }

    int menor = encontrarMenor(inicio + 1, n);

    if (strcmp(selecionados[inicio].nome, selecionados[menor].nome) < 0) {
        comparacoes++;
        return inicio;
    } else {
        comparacoes++;
        return menor;
    }
}

// Função recursiva de ordenação por seleção
void ordenarRecursivo(int inicio, int n) {
    if (inicio < n - 1) {
        int menor = encontrarMenor(inicio, n);
        trocar(inicio, menor);
        ordenarRecursivo(inicio + 1, n);
    }
}

// Função para preencher a pokedex com os dados dos pokemons
void carregarPokedex() {
    FILE *arquivo = fopen("/tmp/pokemon.csv", "r");
    if (!arquivo) {
        printf("Erro ao abrir o arquivo\n");
        return;
    }

    char linha[500];
    int i = 0;

    // Ignorar cabeçalho
    fgets(linha, sizeof(linha), arquivo);

    while (fgets(linha, sizeof(linha), arquivo) && i < MAX) {
        char *token;

        // ID
        token = strtok(linha, ",");
        if (token == NULL) continue; // Verifica se o token é válido
        pokedex[i].identificacao = atoi(token);

        // Geração
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        pokedex[i].geracao = atoi(token);

        // Nome
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        strcpy(pokedex[i].nome, token);

        // Descrição
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        strcpy(pokedex[i].descricao, token);

        // Tipos
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        strcpy(pokedex[i].tipos[0], token);
        token = strtok(NULL, ",");
        if (token != NULL) {
            strcpy(pokedex[i].tipos[1], token);
        } else {
            strcpy(pokedex[i].tipos[1], "");
        }

        // Habilidades
        token = strtok(NULL, "\"");
        if (token == NULL) continue;
        strcpy(pokedex[i].habilidades, token);

        // Peso
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        pokedex[i].peso = atof(token);

        // Altura
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        pokedex[i].altura = atof(token);

        // Taxa de captura
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        pokedex[i].taxaCaptura = atoi(token);

        // Lendário
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        pokedex[i].ehLendario = token[0] == '1';

        // Data de captura
        token = strtok(NULL, ",");
        if (token == NULL) continue;
        strptime(token, "%d/%m/%Y", &pokedex[i].dataCaptura);

        i++;
    }

    fclose(arquivo);
}

// Função para criar o arquivo de log
void criarArquivoLog() {
    FILE *arquivo = fopen("matricula_selecao.txt", "w");
    if (arquivo) {
        fprintf(arquivo, "857867\t%ldns\t%d\n", tempoExecucao, comparacoes);
        fclose(arquivo);
    } else {
        printf("Erro ao criar arquivo de log.\n");
    }
}

// Função para verificar se a string é "FIM"
bool verificarFim(char *str) {
    return strcmp(str, "FIM") == 0;
}

int main() {
    setlocale(LC_ALL, "pt_BR.utf8");
    carregarPokedex();

    char entrada[10];

    // Lendo os IDs dos pokémons e clonando-os no vetor de selecionados
    while (true) {
        scanf("%s", entrada);

        if (verificarFim(entrada)) {
            break;
        }

        int id = atoi(entrada);
        // Clonagem direta, sem necessidade da função clonarPokemon
        selecionados[qtdSelecionados] = pokedex[id - 1]; 
        qtdSelecionados++;
    }

    // Ordenação recursiva
    clock_t inicio = clock();
    ordenarRecursivo(0, qtdSelecionados);
    clock_t fim = clock();
    tempoExecucao = (fim - inicio);

    // Imprimir os pokémons ordenados
    for (int i = 0; i < qtdSelecionados; i++) {
        printf("[#%d -> %s: %s - [%s, %s] - %s - %.2fkg - %.2fm - %d%% - %s -%d gen] - %02d/%02d/%d\n",
               selecionados[i].identificacao, selecionados[i].nome, selecionados[i].descricao,
               selecionados[i].tipos[0], selecionados[i].tipos[1], selecionados[i].habilidades,
               selecionados[i].peso, selecionados[i].altura, selecionados[i].taxaCaptura,
               selecionados[i].ehLendario ? "true" : "false", selecionados[i].geracao,
               selecionados[i].dataCaptura.tm_mday, selecionados[i].dataCaptura.tm_mon + 1, selecionados[i].dataCaptura.tm_year + 1900);
    }

    // Criar arquivo de log
    criarArquivoLog();

    return 0;
}