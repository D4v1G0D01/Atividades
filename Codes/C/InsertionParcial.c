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

typedef struct {
    char id[MAX_STRING];
    int generation;
    char *name;              
    char *description;  
    char types[MAX_TYPES][MAX_STRING];
    char abilities[MAX_ABILITIES][MAX_STRING];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    struct tm captureDate;
} Pokemon;

// Prototipação das funções
const char* getId(Pokemon *pokemon);
const char* getName(Pokemon *pokemon);
const char* getDescription(Pokemon *pokemon);
const char* getType(Pokemon *pokemon, int index);
const char* getAbility(Pokemon *pokemon, int index);
double getWeight(Pokemon *pokemon);
double getHeight(Pokemon *pokemon);
bool getIsLegendary(Pokemon *pokemon);
int getGeneration(Pokemon *pokemon);
int getCaptureRate(Pokemon *pokemon);
struct tm getCaptureDate(Pokemon *pokemon);

// Função de trim
void trim(char *str) {
    char *end;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
}

// Função de parsing da data
void parseDate(char *dateStr, struct tm *date) {
    if (sscanf(dateStr, "%d/%d/%d", &date->tm_mday, &date->tm_mon, &date->tm_year) != 3) {
        return;
    }
    date->tm_mon -= 1;
    date->tm_year -= 1900;
}

// Função auxiliar para dividir a linha do CSV
int split_csv_line(char *line, char **fields, int max_fields) {
    int field_count = 0;
    char *ptr = line;
    int in_quotes = 0;
    char *field_start = ptr;

    while (*ptr && field_count < max_fields) {
        if (*ptr == '"') {
            in_quotes = !in_quotes;
        } else if (*ptr == ',' && !in_quotes) {
            *ptr = '\0';
            fields[field_count++] = field_start;
            field_start = ptr + 1;
        }
        ptr++;
    }
    if (field_count < max_fields) {
        fields[field_count++] = field_start;
    }

    return field_count;
}

// Função que duplica strings
char *my_strdup(const char *str) {
    char *dup = malloc(strlen(str) + 1);
    if (dup) {
        strcpy(dup, str);
    }
    return dup;
}

// Funções Getters
const char* getId(Pokemon *pokemon) {
    return pokemon->id;
}

const char* getName(Pokemon *pokemon) {
    return pokemon->name;
}

const char* getDescription(Pokemon *pokemon) {
    return pokemon->description;
}

const char* getType(Pokemon *pokemon, int index) {
    if (index >= 0 && index < MAX_TYPES) {
        return pokemon->types[index];
    }
    return "";
}

const char* getAbility(Pokemon *pokemon, int index) {
    if (index >= 0 && index < MAX_ABILITIES) {
        return pokemon->abilities[index];
    }
    return "";
}

double getWeight(Pokemon *pokemon) {
    return pokemon->weight;
}

double getHeight(Pokemon *pokemon) {
    return pokemon->height;
}

bool getIsLegendary(Pokemon *pokemon) {
    return pokemon->isLegendary;
}

int getGeneration(Pokemon *pokemon) {
    return pokemon->generation;
}

int getCaptureRate(Pokemon *pokemon) {
    return pokemon->captureRate;
}

struct tm getCaptureDate(Pokemon *pokemon) {
    return pokemon->captureDate;
}

// Função para ler Pokémon do CSV
void lerPokemon(FILE *file, Pokemon *pokedex, int *n) {
    char line[1024];

    fgets(line, sizeof(line), file); // Lê o cabeçalho do CSV

    while (fgets(line, sizeof(line), file) != NULL) {
        line[strcspn(line, "\n")] = '\0'; // Remove a nova linha

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); // Zera a memória da estrutura Pokémon

        char *fields[12]; // Ajuste se houver mais campos
        int field_count = split_csv_line(line, fields, 12);

        // id
        strncpy(p.id, fields[0], MAX_STRING);

        // generation
        p.generation = atoi(fields[1]);

        // name
        p.name = my_strdup(fields[2]);

        // description
        p.description = my_strdup(fields[3]);

        // types
        strncpy(p.types[0], fields[4], MAX_STRING);
        if (strlen(fields[5]) > 0) {
            strncpy(p.types[1], fields[5], MAX_STRING);
        }

        // abilities
        char *abilities_field = fields[6];
        // Remove aspas duplas
        if (abilities_field[0] == '"' && abilities_field[strlen(abilities_field) - 1] == '"') {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }
        // Remove colchetes
        if (abilities_field[0] == '[' && abilities_field[strlen(abilities_field) - 1] == ']') {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }

        // Divide as habilidades
        char *abilityToken;
        char *restAbilities = abilities_field;
        int abilityIndex = 0;
        while ((abilityToken = strtok_r(restAbilities, ",", &restAbilities)) && abilityIndex < MAX_ABILITIES) {
            // Remove espaços e aspas simples
            while (*abilityToken == ' ' || *abilityToken == '\'') abilityToken++;
            char *tempEnd = abilityToken + strlen(abilityToken) - 1;
            while (tempEnd > abilityToken && (*tempEnd == ' ' || *tempEnd == '\'')) {
                *tempEnd = '\0';
                tempEnd--;
            }
            if (strlen(abilityToken) > 0 && abilityIndex < MAX_ABILITIES) {
                strncpy(p.abilities[abilityIndex], abilityToken, MAX_STRING);
                abilityIndex++;
            }
        }

        // weight
        p.weight = atof(fields[7]);

        // height
        p.height = atof(fields[8]);

        // captureRate
        p.captureRate = atoi(fields[9]);

        // isLegendary
        p.isLegendary = atoi(fields[10]);

        // captureDate
        struct tm captureDate = {0};
        parseDate(fields[11], &captureDate);
        p.captureDate = captureDate;

        pokedex[*n] = p;
        (*n)++;
    }
}

void imprimir(Pokemon *pokemon) {
    char dateStr[11]; // Formato "dd/mm/aaaa"
    
    // Formatar a data de captura em string
    strftime(dateStr, sizeof(dateStr), "%d/%m/%Y", &pokemon->captureDate);

    printf(
        "[#%s -> %s: %s - [", 
        getId(pokemon), 
        getName(pokemon), 
        getDescription(pokemon)
    );

    // Tipos
    for (int j = 0; j < MAX_TYPES && strlen(getType(pokemon, j)) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", getType(pokemon, j));
    }
    printf("] - [");

    // Habilidades
    for (int j = 0; j < MAX_ABILITIES && strlen(getAbility(pokemon, j)) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", getAbility(pokemon, j));
    }
    printf("] - ");

    // Peso, altura, taxa de captura, se é lendário, geração e data de captura
    printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", 
        getWeight(pokemon), 
        getHeight(pokemon), 
        getCaptureRate(pokemon), 
        getIsLegendary(pokemon) ? "true" : "false", 
        getGeneration(pokemon), 
        dateStr 
    );
}

int compareByCaptureDateAndName(const Pokemon *p1, const Pokemon *p2) {
    if (difftime(mktime((struct tm *)&p1->captureDate), mktime((struct tm *)&p2->captureDate)) < 0) return -1;
    if (difftime(mktime((struct tm *)&p1->captureDate), mktime((struct tm *)&p2->captureDate)) > 0) return 1;
    return strcmp(p1->name, p2->name);
}

void insertionSortPartial(Pokemon *pokedex, int n, int k) {
    for (int i = 1; i < n; i++) {
        Pokemon temp = pokedex[i];
        int j;

        for (j = i; j > 0 && j < i + k && compareByCaptureDateAndName(&temp, &pokedex[j - 1]) < 0; j--) {
            pokedex[j] = pokedex[j - 1]; // Mover para a direita
        }
        pokedex[j] = temp; 
    }
}

int main () {
    char *csvPath = "/tmp/pokemon.csv";
    clock_t start = clock();

    FILE *file = fopen(csvPath, "r");   

    if (file == NULL) {
        printf("Erro ao abrir o arquivo CSV.\n");
        return 1;
    } 

    Pokemon pokedex[MAX_POKEMON];
    Pokemon findPokemon[MAX_POKEMON]; 

    int n = 0;

    lerPokemon(file, pokedex, &n);

    fclose(file);

    char inputId[MAX_STRING];
    scanf("%s", inputId);

    int comp = 0;
    int j = 0;

    while (true) {
        if (strcmp(inputId, "FIM") == 0) break; 
        for (int i = 0; i < n; i++) {
            comp++;
            if (strcmp(pokedex[i].id, inputId) == 0) { 
                findPokemon[j++] = pokedex[i];
                break;
            }
        }
        scanf("%s", inputId); 
    }

    insertionSortPartial(findPokemon, j, 10); 

    if (j > 0) {
        for (int k = 0; k < j && k < 10; k++) {
            imprimir(&findPokemon[k]); 
        } 
    } else {
        printf("NAO\n"); 
    }

    // Liberação de memória
    for (int i = 0; i < n; i++) {
        free(pokedex[i].name);
        free(pokedex[i].description);
    }

    clock_t end = clock();
    double executionTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000.0;

    FILE *arquivo = fopen("848324_insercaoparcial.txt", "w");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para salvar os dados!\n");
        return 1;
    }

    fprintf(arquivo, "848324\t%.2f\t%d\n", executionTime, comp);
    fclose(arquivo);

    return 0;
}
