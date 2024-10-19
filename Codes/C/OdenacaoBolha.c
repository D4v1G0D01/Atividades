#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_POKEMONS 1000
#define MAX_TEXT 256
#define MAX_HABILIDADES 6
#define MAX_ELEMENTOS 3

typedef struct 
{
    char id[MAX_TEXT];
    int geracao;
    char *titulo;             
    char *detalhe;  
    char elementos[MAX_ELEMENTOS][MAX_TEXT];
    char habilidades[MAX_HABILIDADES][MAX_TEXT];
    double massa;
    double altura;
    int taxaCaptura;
    bool ehLegendario;
    struct tm dataCaptura;
} Pokemon;

// Declarações (prototipação) das funções antes de usá-las
const char* obterId(Pokemon *pokemon);
const char* obterTitulo(Pokemon *pokemon);
const char* obterDetalhe(Pokemon *pokemon);
const char* obterElemento(Pokemon *pokemon, int indice);
const char* obterHabilidade(Pokemon *pokemon, int indice);
double obterMassa(Pokemon *pokemon);
double obterAltura(Pokemon *pokemon);
bool obterEhLegendario(Pokemon *pokemon);
int obterGeracao(Pokemon *pokemon);
int obterTaxaCaptura(Pokemon *pokemon);
struct tm obterDataCaptura(Pokemon *pokemon);

// Função de trim
void limpar(char *str) 
{
    char *fim;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    fim = str + strlen(str) - 1;
    while (fim > str && isspace((unsigned char)*fim)) fim--;
    fim[1] = '\0';
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
int dividirLinhaCSV(char *linha, char **campos, int max_campos) 
{
    int contagem_campos = 0;
    char *ptr = linha;
    int dentroAspas = 0;
    char *inicioCampo = ptr;

    while (*ptr && contagem_campos < max_campos) 
    {
        if (*ptr == '"') 
        {
            dentroAspas = !dentroAspas;
        } else if (*ptr == ',' && !dentroAspas) 
        {
            *ptr = '\0';
            campos[contagem_campos++] = inicioCampo;
            inicioCampo = ptr + 1;
        }
        ptr++;
    }
    // Adiciona o último campo
    if (contagem_campos < max_campos) 
    {
        campos[contagem_campos++] = inicioCampo;
    }

    return contagem_campos;
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

const char* obterTitulo(Pokemon *pokemon) 
{
    return pokemon->titulo;
}

const char* obterDetalhe(Pokemon *pokemon) 
{
    return pokemon->detalhe;
}

const char* obterElemento(Pokemon *pokemon, int indice) 
{
    if (indice >= 0 && indice < MAX_ELEMENTOS) 
    {
        return pokemon->elementos[indice];
    }
    return "";
}

const char* obterHabilidade(Pokemon *pokemon, int indice) 
{
    if (indice >= 0 && indice < MAX_HABILIDADES) 
    {
        return pokemon->habilidades[indice];
    }
    return "";
}

double obterMassa(Pokemon *pokemon) 
{
    return pokemon->massa;
}

double obterAltura(Pokemon *pokemon) 
{
    return pokemon->altura;
}

bool obterEhLegendario(Pokemon *pokemon) 
{
    return pokemon->ehLegendario;
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
void lerPokemons(FILE *arquivo, Pokemon *pokedex, int *n) 
{
    char linha[1024];

    fgets(linha, sizeof(linha), arquivo); // Lê o cabeçalho do CSV

    while (fgets(linha, sizeof(linha), arquivo) != NULL) 
    {
        linha[strcspn(linha, "\n")] = '\0'; // Remove a nova linha

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); // Zera a memória da estrutura Pokémon

        char *campos[12]; // Ajuste se houver mais campos
        int contagem_campos = dividirLinhaCSV(linha, campos, 12);

        // id
        strncpy(p.id, campos[0], MAX_TEXT);

        // geracao
        p.geracao = atoi(campos[1]);

        // titulo
        p.titulo = minha_strdup(campos[2]);        

        // detalhe
        p.detalhe = minha_strdup(campos[3]); 

        // elementos
        strncpy(p.elementos[0], campos[4], MAX_TEXT);
        if (strlen(campos[5]) > 0) 
        {
            strncpy(p.elementos[1], campos[5], MAX_TEXT);
        }

        // habilidades
        char *campoHabilidades = campos[6];
        // Remove aspas duplas
        if (campoHabilidades[0] == '"' && campoHabilidades[strlen(campoHabilidades) - 1] == '"') 
        {
            campoHabilidades[strlen(campoHabilidades) - 1] = '\0';
            campoHabilidades++;
        }
        // Remove colchetes
        if (campoHabilidades[0] == '[' && campoHabilidades[strlen(campoHabilidades) - 1] == ']') 
        {
            campoHabilidades[strlen(campoHabilidades) - 1] = '\0';
            campoHabilidades++;
        }

        // Divide as habilidades
        char *tokenHabilidade;
        char *restoHabilidades = campoHabilidades;
        int indiceHabilidade = 0;
        while ((tokenHabilidade = strtok_r(restoHabilidades, ",", &restoHabilidades)) && indiceHabilidade < MAX_HABILIDADES) 
        {
            // Remove espaços e aspas simples
            while (*tokenHabilidade == ' ' || *tokenHabilidade == '\'') tokenHabilidade++;
            char *tempFim = tokenHabilidade + strlen(tokenHabilidade) - 1;
            while (tempFim > tokenHabilidade && (*tempFim == ' ' || *tempFim == '\'')) 
            {
                *tempFim = '\0';
                tempFim--;
            }
            if (strlen(tokenHabilidade) > 0 && indiceHabilidade < MAX_HABILIDADES) 
            {
                strncpy(p.habilidades[indiceHabilidade], tokenHabilidade, MAX_TEXT);
                indiceHabilidade++;
            }
        }

        // massa
        p.massa = atof(campos[7]);

        // altura
        p.altura = atof(campos[8]);

        // taxaCaptura
        p.taxaCaptura = atoi(campos[9]);

        // ehLegendario
        p.ehLegendario = atoi(campos[10]);

        // dataCaptura
        struct tm dataCaptura = {0};
        analisarData(campos[11], &dataCaptura);
        p.dataCaptura = dataCaptura;

        pokedex[*n] = p;
        (*n)++;
    }
}

void exibir(Pokemon *pokemon){
    char dataStr[11]; // Formato "dd/mm/aaaa"
    
    // Formatar a data de captura em string
    strftime(dataStr, sizeof(dataStr), "%d/%m/%Y", &pokemon->dataCaptura);

    printf(
        "[#%s -> %s: %s - [", 
        obterId(pokemon), 
        obterTitulo(pokemon), 
        obterDetalhe(pokemon)
    );

    // Elementos
    for (int j = 0; j < MAX_ELEMENTOS && strlen(obterElemento(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", obterElemento(pokemon, j));
    }
    printf("] - [");

    // Habilidades
    for (int j = 0; j < MAX_HABILIDADES && strlen(obterHabilidade(pokemon, j)) > 0; j++) 
    {
        if (j > 0) printf(", ");
        printf("'%s'", obterHabilidade(pokemon, j));
    }
    printf("] - ");

    // Massa, altura, taxa de captura, se é lendário, geração e data de captura
       printf("%.1fm - %d%% - %s - %d gen] - %s\n", 
        obterMassa(pokemon), 
        obterAltura(pokemon), 
        obterTaxaCaptura(pokemon), 
        obterEhLegendario(pokemon) ? "true" : "false", 
        obterGeracao(pokemon), 
        dataStr 
    );
}

int compararPorIdETitulo(const Pokemon *p1, const Pokemon *p2) 
{
    int id1 = atoi(p1->id);
    int id2 = atoi(p2->id);
    
    if (id1 != id2) return id1 - id2;
    return strcmp(p1->titulo, p2->titulo);
}

void ordenacaoBolha(Pokemon *pokedex, int n) 
{
    for (int i = 0; i < n - 1; i++) 
    {
        for (int j = 0; j < n - i - 1; j++) 
        {
            if (compararPorIdETitulo(&pokedex[j], &pokedex[j + 1]) > 0) 
            {
                Pokemon temp = pokedex[j];
                pokedex[j] = pokedex[j + 1];
                pokedex[j + 1] = temp;
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

    Pokemon pokedex[MAX_POKEMONS];
    Pokemon encontrados[MAX_POKEMONS]; 

    int n = 0;

    lerPokemons(arquivo, pokedex, &n);

    fclose(arquivo);

    char entradaId[MAX_TEXT];
    scanf("%s", entradaId);

    int comparacoes = 0;
    int j = 0;

    while (true) 
    {
        if (strcmp(entradaId, "FIM") == 0) break; 
        for (int i = 0; i < n; i++) 
        {
            comparacoes++;
            if (strcmp(pokedex[i].id, entradaId) == 0) 
            { 
                encontrados[j++] = pokedex[i];
                break;
            }
        }
        scanf("%s", entradaId); 
    }

    ordenacaoBolha(encontrados, j); 

    if (j > 0) 
    {
        for (int k = 0; k < j; k++) 
        {
            exibir(&encontrados[k]); 
        }
    } 
    else 
    {
        printf("NAO\n"); 
    }

    // Liberação de memória
    for (int i = 0; i < n; i++) 
    {
        free(pokedex[i].titulo);
        free(pokedex[i].detalhe);
    }

    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0;

    FILE *resultadoArquivo = fopen("848324_bubblesort.txt", "w");
    if (resultadoArquivo == NULL) {
        printf("Erro ao abrir o arquivo para salvar os dados!\n");
        return 1;
    }

    fprintf(resultadoArquivo, "848324\t%.2f\t%d\n", tempoExecucao, comparacoes);
    fclose(resultadoArquivo);

    return 0;
}

