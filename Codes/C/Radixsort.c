#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_POKEMON 1000
#define MAX_STRING 256
#define MAX_ABILITIES 6
#define MAX_TYPES 3

typedef struct 
{
    char id[MAX_STRING];
    int geracao;
    char *nome;             
    char *descricao;  
    char tipos[MAX_TYPES][MAX_STRING];
    char habilidades[MAX_ABILITIES][MAX_STRING];
    double peso;
    double altura;
    int taxaCaptura;
    bool ehLendario;
    struct tm dataCaptura;
} Pokemon;

// Prototipação das funções
const char* obterId(Pokemon *pokemon);
const char* obterNome(Pokemon *pokemon);
const char* obterDescricao(Pokemon *pokemon);
const char* obterTipo(Pokemon *pokemon, int index);
const char* obterHabilidade(Pokemon *pokemon, int index);
double obterPeso(Pokemon *pokemon);
double obterAltura(Pokemon *pokemon);
bool obterEhLendario(Pokemon *pokemon);
int obterGeracao(Pokemon *pokemon);
int obterTaxaCaptura(Pokemon *pokemon);
struct tm obterDataCaptura(Pokemon *pokemon);

// Função de trim
void trim(char *str) 
{
    char *end;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
}

// Função de parsing da data
void parseData(char *dataStr, struct tm *data) 
{
    if (sscanf(dataStr, "%d/%d/%d", &data->tm_mday, &data->tm_mon, &data->tm_year) != 3) 
    {
        return;
    }
    data->tm_mon -= 1;
    data->tm_year -= 1900;
}

// Função auxiliar para dividir a linha do CSV
int dividirLinhaCSV(char *linha, char **campos, int max_campos) 
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
const char* obterId(Pokemon *pokemon) 
{
    return pokemon->id;
}

const char* obterNome(Pokemon *pokemon) 
{
    return pokemon->nome;
}

const char* obterDescricao(Pokemon *pokemon) 
{
    return pokemon->descricao;
}

const char* obterTipo(Pokemon *pokemon, int index) 
{
    if (index >= 0 && index < MAX_TYPES) 
    {
        return pokemon->tipos[index];
    }
    return "";
}

const char* obterHabilidade(Pokemon *pokemon, int index) 
{
    if (index >= 0 && index < MAX_ABILITIES) 
    {
        return pokemon->habilidades[index];
    }
    return "";
}

double obterPeso(Pokemon *pokemon) 
{
    return pokemon->peso;
}

double obterAltura(Pokemon *pokemon) 
{
    return pokemon->altura;
}

bool obterEhLendario(Pokemon *pokemon) 
{
    return pokemon->ehLendario;
}

int obterGeracao(Pokemon *pokemon) 
{
    return pokemon->geracao;
}

int obterTaxaCaptura(Pokemon *pokemon) 
{
    return pokemon->taxaCaptura;
}

struct tm obterDataCaptura(Pokemon *pokemon) 
{
    return pokemon->dataCaptura;
}

// Função para ler Pokémon do CSV
void lerPokemon(FILE *file, Pokemon *pokedex, int *n) 
{
    char linha[1024];

    fgets(linha, sizeof(linha), file); // Lê o cabeçalho do CSV

    while (fgets(linha, sizeof(linha), file) != NULL) 
    {
        linha[strcspn(linha, "\n")] = '\0'; // Remove a nova linha

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); // Zera a memória da estrutura Pokémon

        char *campos[12]; // Ajuste se houver mais campos
        int contador_campos = dividirLinhaCSV(linha, campos, 12);

        // id
        strncpy(p.id, campos[0], MAX_STRING);

        // geracao
        p.geracao = atoi(campos[1]);

        // nome
        p.nome = minha_strdup(campos[2]);        

        // descricao
        p.descricao = minha_strdup(campos[3]); 

        // tipos
        strncpy(p.tipos[0], campos[4], MAX_STRING);
        if (strlen(campos[5]) > 0) 
        {
            strncpy(p.tipos[1], campos[5], MAX_STRING);
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
        while ((habilidadeToken = strtok_r(restoHabilidades, ",", &restoHabilidades)) && indiceHabilidade < MAX_ABILITIES) 
        {
            // Remove espaços e aspas simples
            while (*habilidadeToken == ' ' || *habilidadeToken == '\'') habilidadeToken++;
            char *tempEnd = habilidadeToken + strlen(habilidadeToken) - 1;
            while (tempEnd > habilidadeToken && (*tempEnd == ' ' || *tempEnd == '\'')) 
            {
                *tempEnd = '\0';
                tempEnd--;
            }
            if (strlen(habilidadeToken) > 0 && indiceHabilidade < MAX_ABILITIES) 
            {
                strncpy(p.habilidades[indiceHabilidade], habilidadeToken, MAX_STRING);
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
        parseData(campos[11], &dataCaptura);
        p.dataCaptura = dataCaptura;

        pokedex[*n] = p;
        (*n)++;
    }
}

void imprimir(Pokemon *pokemon) {
    char dataStr[11]; // Formato "dd/mm/aaaa"
    
    // Formatar a data de captura em string
    strftime(dataStr, sizeof(dataStr), "%d/%m/%Y", &pokemon->dataCaptura);

    printf(
        "[#%s -> %s: %s - [", 
        obterId(pokemon), 
        obterNome(pokemon), 
        obterDescricao(pokemon)
    );

    // Tipos
    for (int j = 0; j < MAX_TYPES && strlen(obterTipo(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", obterTipo(pokemon, j));
    }
    printf("] - [");

    // Habilidades
    for (int j = 0; j < MAX_ABILITIES && strlen(obterHabilidade(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", obterHabilidade(pokemon, j));
    }
    printf("] - ");

    // Peso, altura, taxa de captura, se é lendário, geração e data de captura
    printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", 
        obterPeso(pokemon), 
        obterAltura(pokemon), 
        obterTaxaCaptura(pokemon), 
        obterEhLendario(pokemon) ? "true" : "false", 
        obterGeracao(pokemon), 
        dataStr 
    );
}

int obterCharAt(const char *str, int charPos) 
{
    if (charPos < strlen(str)) {
        return str[charPos];
    }
    return 0; 
}

// Função auxiliar: Counting Sort por um caractere específico
void countingSortPorHabilidade(Pokemon *arr, int n, int charPos) 
{
    Pokemon output[n]; // Array temporário para armazenar resultado
    int count[256] = {0}; // Para 256 caracteres ASCII

    // Contagem das ocorrências de cada caractere no charPos
    for (int i = 0; i < n; i++) {
        int charVal = obterCharAt(arr[i].habilidades[0], charPos); // Ordena pela primeira habilidade
        count[charVal]++;
    }

    // Atualiza count[i] para que contenha a posição de cada caractere na saída
    for (int i = 1; i < 256; i++) {
        count[i] += count[i - 1];
    }

    // Construção do array de saída ordenado por charPos
    for (int i = n - 1; i >= 0; i--) 
    {
        int charVal = obterCharAt(arr[i].habilidades[0], charPos);
        output[count[charVal] - 1] = arr[i];
        count[charVal]--;
    }

    // Copia o array de saída para arr[], agora arr está ordenado por charPos
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Função Radix Sort para habilidades
void radixSortPorHabilidade(Pokemon *arr, int n) {
    int maxLen = 0;

    // Encontra a habilidade mais longa
    for (int i = 0; i < n; i++) {
        int len = strlen(arr[i].habilidades[0]);
        if (len > maxLen) {
            maxLen = len;
        }
    }

    // Aplica Counting Sort para cada posição de caractere (radix sort)
    for (int charPos = maxLen - 1; charPos >= 0; charPos--) 
    {
        countingSortPorHabilidade(arr, n, charPos);
    }

    // Desempate por nome caso habilidades sejam iguais
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (strcmp(arr[j].habilidades[0], arr[j + 1].habilidades[0]) == 0) 
            {
                if (strcmp(arr[j].nome, arr[j + 1].nome) > 0) {
                    Pokemon temp = arr[j];
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

    FILE *file = fopen(caminhoCSV, "r");   

    if (file == NULL) {
        printf("Erro ao abrir o arquivo CSV.\n");
        return 1;
    } 

    Pokemon pokedex[MAX_POKEMON];
    Pokemon pokemonEncontrado[MAX_POKEMON]; 

    int n = 0;

    lerPokemon(file, pokedex, &n);

    fclose(file);

    char inputId[MAX_STRING];
    scanf("%s", inputId);

    int comparacoes = 0;
    int j = 0;

    while (true) 
    {
        if (strcmp(inputId, "FIM") == 0) break; 
        for (int i = 0 ; i < n ; i++) 
        {
            comparacoes++;
            if (strcmp(pokedex[i].id, inputId) == 0) 
            { 
                pokemonEncontrado[j++] = pokedex[i];
                break;
            }
        }
        scanf("%s", inputId); 
    }

    radixSortPorHabilidade(pokemonEncontrado, j);

    if (j > 0) 
    {
        for (int k = 0; k < j; k++) 
        {
            imprimir(&pokemonEncontrado[k]); 
        }
    } 
    else 
    {
        printf("NAO\n"); 
    }

    // Liberação de memória
    for (int i = 0; i < n; i++) 
    {
        free(pokedex[i].nome);
        free(pokedex[i].descricao);
    }

    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0;

    FILE *arquivo = fopen("843474_radixsort.txt", "w");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para salvar os dados!\n");
        return 1;
    }

    fprintf(arquivo, "843474\t%.2f\t%d\n", tempoExecucao, comparacoes);
    fclose(arquivo);

    return 0;
}
