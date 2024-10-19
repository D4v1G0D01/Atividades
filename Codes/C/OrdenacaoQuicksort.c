#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_POKEMONS 1000
#define MAX_TEXTO 256
#define MAX_HABILIDADES 6
#define MAX_TIPOS 3

typedef struct 
{
    char id[MAX_TEXTO];
    int geracao;
    char *nome;             
    char *descricao;  
    char tipos[MAX_TIPOS][MAX_TEXTO];
    char habilidades[MAX_HABILIDADES][MAX_TEXTO];
    double peso;
    double altura;
    int taxaCaptura;
    bool ehLendario;
    struct tm dataCaptura;
} PokemonData;

// Prototipação das funções
const char* pegarId(PokemonData *pokemon);
const char* pegarNome(PokemonData *pokemon);
const char* pegarDescricao(PokemonData *pokemon);
const char* pegarTipo(PokemonData *pokemon, int index);
const char* pegarHabilidade(PokemonData *pokemon, int index);
double pegarPeso(PokemonData *pokemon);
double pegarAltura(PokemonData *pokemon);
bool pegarEhLendario(PokemonData *pokemon);
int pegarGeracao(PokemonData *pokemon);
int pegarTaxaCaptura(PokemonData *pokemon);
struct tm pegarDataCaptura(PokemonData *pokemon);

// Função de trim
void limparEspacos(char *str) 
{
    char *end;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
}

// Função de parsing da data
void analisarData(char *dataStr, struct tm *data) 
{
    if (sscanf(dataStr, "%d/%d/%d", &data->tm_mday, &data->tm_mon, &data->tm_year) != 3) 
    {
        return;
    }
    data->tm_mon -= 1;
    data->tm_year -= 1900;
}

// Função auxiliar para dividir a linha do CSV
int dividirCSV(char *linha, char **campos, int max_campos) 
{
    int contador_campos = 0;
    char *ptr = linha;
    int dentro_aspas = 0;
    char *inicio_campo = ptr;

    while (*ptr && contador_campos < max_campos) 
    {
        if (*ptr == '"') 
        {
            dentro_aspas = !dentro_aspas;
        } else if (*ptr == ',' && !dentro_aspas) 
        {
            *ptr = '\0';
            campos[contador_campos++] = inicio_campo;
            inicio_campo = ptr + 1;
        }
        ptr++;
    }
    // Adiciona o último campo
    if (contador_campos < max_campos) 
    {
        campos[contador_campos++] = inicio_campo;
    }

    return contador_campos;
}

// Função que duplica strings
char *minha_strdup(const char *str) 
{
    char *dup = malloc(strlen(str) + 1);
    if (dup) 
    {
        strcpy(dup, str);
    }
    return dup;
}

// Funções Getters
const char* pegarId(PokemonData *pokemon) 
{
    return pokemon->id;
}

const char* pegarNome(PokemonData *pokemon) 
{
    return pokemon->nome;
}

const char* pegarDescricao(PokemonData *pokemon) 
{
    return pokemon->descricao;
}

const char* pegarTipo(PokemonData *pokemon, int index) 
{
    if (index >= 0 && index < MAX_TIPOS) 
    {
        return pokemon->tipos[index];
    }
    return "";
}

const char* pegarHabilidade(PokemonData *pokemon, int index) 
{
    if (index >= 0 && index < MAX_HABILIDADES) 
    {
        return pokemon->habilidades[index];
    }
    return "";
}

double pegarPeso(PokemonData *pokemon) 
{
    return pokemon->peso;
}

double pegarAltura(PokemonData *pokemon) 
{
    return pokemon->altura;
}

bool pegarEhLendario(PokemonData *pokemon) 
{
    return pokemon->ehLendario;
}

int pegarGeracao(PokemonData *pokemon) 
{
    return pokemon->geracao;
}

int pegarTaxaCaptura(PokemonData *pokemon) 
{
    return pokemon->taxaCaptura;
}

struct tm pegarDataCaptura(PokemonData *pokemon) 
{
    return pokemon->dataCaptura;
}

// Função para ler Pokémon do CSV
void lerPokemons(FILE *arquivo, PokemonData *pokedex, int *quantidade) 
{
    char linha[1024];

    fgets(linha, sizeof(linha), arquivo); // Lê o cabeçalho do CSV

    while (fgets(linha, sizeof(linha), arquivo) != NULL) 
    {
        linha[strcspn(linha, "\n")] = '\0'; // Remove a nova linha

        PokemonData p;
        memset(&p, 0, sizeof(PokemonData)); // Zera a memória da estrutura Pokémon

        char *campos[12]; // Ajuste se houver mais campos
        int contador_campos = dividirCSV(linha, campos, 12);

        // id
        strncpy(p.id, campos[0], MAX_TEXTO);

        // geracao
        p.geracao = atoi(campos[1]);

        // nome
        p.nome = minha_strdup(campos[2]);        

        // descricao
        p.descricao = minha_strdup(campos[3]); 

        // tipos
        strncpy(p.tipos[0], campos[4], MAX_TEXTO);
        if (strlen(campos[5]) > 0) 
        {
            strncpy(p.tipos[1], campos[5], MAX_TEXTO);
        }

        // habilidades
        char *habilidades_campo = campos[6];
        // Remove aspas duplas
        if (habilidades_campo[0] == '"' && habilidades_campo[strlen(habilidades_campo) - 1] == '"') 
        {
            habilidades_campo[strlen(habilidades_campo) - 1] = '\0';
            habilidades_campo++;
        }
        // Remove colchetes
        if (habilidades_campo[0] == '[' && habilidades_campo[strlen(habilidades_campo) - 1] == ']') 
        {
            habilidades_campo[strlen(habilidades_campo) - 1] = '\0';
            habilidades_campo++;
        }

        // Divide as habilidades
        char *habilidadeToken;
        char *restoHabilidades = habilidades_campo;
        int indiceHabilidade = 0;
        while ((habilidadeToken = strtok_r(restoHabilidades, ",", &restoHabilidades)) && indiceHabilidade < MAX_HABILIDADES) 
        {
            // Remove espaços e aspas simples
            while (*habilidadeToken == ' ' || *habilidadeToken == '\'') habilidadeToken++;
            char *tempEnd = habilidadeToken + strlen(habilidadeToken) - 1;
            while (tempEnd > habilidadeToken && (*tempEnd == ' ' || *tempEnd == '\'')) 
            {
                *tempEnd = '\0';
                tempEnd--;
            }
            if (strlen(habilidadeToken) > 0 && indiceHabilidade < MAX_HABILIDADES) 
            {
                strncpy(p.habilidades[indiceHabilidade], habilidadeToken, MAX_TEXTO);
                indiceHabilidade++;
            }
        }

        // peso
        p.peso = atof(campos[7]);

        // altura
        p.altura = atof(campos[8]);

        // taxaCaptura
        p.taxaCaptura = atoi(campos[9]);

        // ehLendario
        p.ehLendario = atoi(campos[10]);

        // dataCaptura
        struct tm dataCaptura = {0};
        analisarData(campos[11], &dataCaptura);
        p.dataCaptura = dataCaptura;

        pokedex[*quantidade] = p;
        (*quantidade)++;
    }
}

void mostrar(PokemonData *pokemon) {
    char dataStr[11]; // Formato "dd/mm/aaaa"
    
    // Formatar a data de captura em string
    strftime(dataStr, sizeof(dataStr), "%d/%m/%Y", &pokemon->dataCaptura);

    printf(
        "[#%s -> %s: %s - [", 
        pegarId(pokemon), 
        pegarNome(pokemon), 
        pegarDescricao(pokemon)
    );

    // Tipos
    for (int j = 0; j < MAX_TIPOS && strlen(pegarTipo(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", pegarTipo(pokemon, j));
    }
    printf("] - [");

    // Habilidades
    for (int j = 0; j < MAX_HABILIDADES && strlen(pegarHabilidade(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", pegarHabilidade(pokemon, j));
    }
    printf("] - ");

    // Peso, altura, taxa de captura, se é lendário, geração e data de captura
    printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", 
        pegarPeso(pokemon), 
        pegarAltura(pokemon), 
        pegarTaxaCaptura(pokemon), 
        pegarEhLendario(pokemon) ? "true" : "false", 
        pegarGeracao(pokemon), 
        dataStr 
    );
}

int pegarCharAt(const char *str, int posicao) 
{
    if (posicao < strlen(str)) {
        return str[posicao];
    }
    return 0; 
}

// Função auxiliar: Counting Sort por um caractere específico
void ordenacaoContagemPorHabilidade(PokemonData *arr, int n, int posicao) 
{
    PokemonData output[n]; // Array temporário para armazenar resultado
    int contagem[256] = {0}; // Para 256 caracteres ASCII

    // Contagem das ocorrências de cada caractere no posicao
    for (int i = 0; i < n; i++) {
        int charVal = pegarCharAt(arr[i].habilidades[0], posicao); // Ordena pela primeira habilidade
        contagem[charVal]++;
    }

    // Atualiza contagem[i] para que contenha a posição de cada caractere na saída
    for (int i = 1; i < 256; i++) {
        contagem[i] += contagem[i - 1];
    }

    // Construção do array de saída ordenado por posicao
    for (int i = n - 1; i >= 0; i--) 
    {
        int charVal = pegarCharAt(arr[i].habilidades[0], posicao);
        output[contagem[charVal] - 1] = arr[i];
        contagem[charVal]--;
    }

    // Copia o array de saída para arr[], agora arr está ordenado por posicao
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Função Radix Sort para habilidades
void ordenacaoRadixPorHabilidade(PokemonData *arr, int n) {
    int maxLen = 0;

    // Encontra a habilidade mais longa
    for (int i = 0; i < n; i++) {
        int len = strlen(arr[i].habilidades[0]);
        if (len > maxLen) {
            maxLen = len;
        }
    }

    // Aplica Counting Sort para cada posição de caractere (radix sort)
    for (int posicao = maxLen - 1; posicao >= 0; posicao--) 
    {
        ordenacaoContagemPorHabilidade(arr, n, posicao);
    }

    // Desempate por nome caso habilidades sejam iguais
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (strcmp(arr[j].habilidades[0], arr[j + 1].habilidades[0]) == 0) 
            {
                if (strcmp(arr[j].nome, arr[j + 1].nome) > 0) {
                    PokemonData temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}

int main () 
{
    char *caminhoCSV = "/tmp/pokemon.csv";
    clock_t inicio = clock();

    FILE *arquivo = fopen(caminhoCSV, "r");   

    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo CSV.\n");
        return 1;
    } 

    PokemonData pokedex[MAX_POKEMONS];
    PokemonData pokemonsEncontrados[MAX_POKEMONS]; 

    int quantidade = 0;

    lerPokemons(arquivo, pokedex, &quantidade);

    fclose(arquivo);

    char inputId[MAX_TEXTO];
    scanf("%s", inputId);

    int comparacoes = 0;
    int j = 0;

    while (true) 
    {
        if (strcmp(inputId, "FIM") == 0) break; 
        for (int i = 0; i < quantidade; i++) 
        {
            comparacoes++;
            if (strcmp(pokedex[i].id, inputId) == 0) 
            { 
                pokemonsEncontrados[j++] = pokedex[i];
                break;
            }
        }
        scanf("%s", inputId); 
    }

    ordenacaoRadixPorHabilidade(pokemonsEncontrados, j);

    if (j > 0) 
    {
        for (int k = 0; k < j; k++) 
        {
            mostrar(&pokemonsEncontrados[k]); 
        }
    } 
    else 
    {
        printf("NAO\n"); 
    }

    // Liberação de memória
    for (int i = 0; i < quantidade; i++) 
    {
        free(pokedex[i].nome);
        free(pokedex[i].descricao);
    }

    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0;

    FILE *resultado = fopen("848324_radixsort.txt", "w");
    if (resultado == NULL) {
        printf("Erro ao abrir o arquivo para salvar os dados!\n");
        return 1;
    }

    fprintf(resultado, "848324\t%.2f\t%d\n", tempoExecucao, comparacoes);
    fclose(resultado);

    return 0;
}
