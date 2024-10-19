#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

// Estrutura para representar uma data
typedef struct Data {
    int dia;
    int mes;
    int ano;
} Data;

// Converte a estrutura Data para uma string no formato "DD/MM/AAAA"
char *dataParaString(Data data) {
    char *dataString = (char *)malloc(11 * sizeof(char)); // Aloca espaço para a string
    sprintf(dataString, "%02d/%02d/%04d", data.dia, data.mes, data.ano); // Formata a data
    return dataString;
}

// Converte uma string no formato "DD/MM/AAAA" para a estrutura Data
Data stringParaData(char *strData) {
    Data data;
    if (strData != NULL && strlen(strData) > 0) {
        sscanf(strData, "%d/%d/%d", &data.dia, &data.mes, &data.ano); // Faz o parsing da string
    } else {
        data.dia = 0;
        data.mes = 0;
        data.ano = 0; // Caso a string esteja vazia, inicializa com valores zero
    }
    return data;
}

// Estrutura para representar um Pokémon
typedef struct Pokemon {
    int id;
    int geracao;
    char *nome;
    char *descricao;
    char tipos[2][50]; // Até dois tipos por Pokémon
    char habilidades[6][50]; // Até seis habilidades
    double peso;
    double altura;
    int taxaCaptura;
    bool lendario;
    Data dataCaptura;
} Pokemon;

// Duplica uma string alocando memória dinamicamente
char* duplicarString(const char* original) {
    char* copia = (char*)malloc(strlen(original) + 1); // Aloca memória para a cópia
    if (copia != NULL) {
        strcpy(copia, original); // Copia o conteúdo da string original
    }
    return copia;
}

// Funções para obter e definir atributos do Pokémon
int obterId(Pokemon *p) {
    return p->id;
}

void definirId(Pokemon *p, int id) {
    p->id = id;
}

int obterGeracao(Pokemon *p) {
    return p->geracao;
}

void definirGeracao(Pokemon *p, int geracao) {
    p->geracao = geracao;
}

char* obterNome(Pokemon *p) {
    return p->nome;
}

void definirNome(Pokemon *p, char *nome) {
    p->nome = nome;
}

char* obterDescricao(Pokemon *p) {
    return p->descricao;
}

void definirDescricao(Pokemon *p, char *descricao) {
    p->descricao = descricao;
}

char* obterTipos(Pokemon *p, int indice) {
    return p->tipos[indice];
}

// Conta quantos tipos o Pokémon tem
int obterNumeroDeTipos(Pokemon *p) {
    int contador = 0;
    for (int i = 0; i < 2; i++) {
        if (strlen(p->tipos[i]) > 0) {
            contador++;
        }
    }
    return contador;
}

// Define o tipo do Pokémon
void definirTipos(Pokemon *p, int indice, char *tipo) {
    strncpy(p->tipos[indice], tipo, sizeof(p->tipos[indice]) - 1);
    p->tipos[indice][sizeof(p->tipos[indice]) - 1] = '\0';
}

char* obterHabilidades(Pokemon *p, int indice) {
    return p->habilidades[indice];
}

// Conta o número de habilidades do Pokémon
int obterNumeroDeHabilidades(Pokemon *p) {
    int contador = 0;
    for (int i = 0; i < 6; i++) {
        if (strlen(p->habilidades[i]) > 0) {
            contador++;
        }
    }
    return contador;
}

// Define as habilidades do Pokémon
void definirHabilidades(Pokemon *p, int indice, const char *habilidade) {
    strncpy(p->habilidades[indice], habilidade, sizeof(p->habilidades[indice]) - 1);
    p->habilidades[indice][sizeof(p->habilidades[indice]) - 1] = '\0';
}

double obterPeso(Pokemon *p) {
    return p->peso;
}

void definirPeso(Pokemon *p, double peso) {
    p->peso = peso;
}

double obterAltura(Pokemon *p) {
    return p->altura;
}

void definirAltura(Pokemon *p, double altura) {
    p->altura = altura;
}

int obterTaxaCaptura(Pokemon *p) {
    return p->taxaCaptura;
}

void definirTaxaCaptura(Pokemon *p, int taxaCaptura) {
    p->taxaCaptura = taxaCaptura;
}

bool obterLendario(Pokemon *p) {
    return p->lendario;
}

void definirLendario(Pokemon *p, bool lendario) {
    p->lendario = lendario;
}

Data obterDataCaptura(Pokemon *p) {
    return p->dataCaptura;
}

void definirDataCapturaData(Pokemon *p, Data dataCaptura) {
    p->dataCaptura = dataCaptura;
}

// Define a data de captura usando uma string
void definirDataCapturaString(Pokemon *p, char *dataCaptura) {
    p->dataCaptura = stringParaData(dataCaptura);
}

// Função para criar um Pokémon com todos os atributos
Pokemon criarPokemon(int id, int geracao, char *nome,
    char *descricao, char *tipo1, char *tipo2, char *habilidades[6], double peso,
    double altura, int taxaCaptura, bool lendario, Data dataCaptura) {

    Pokemon p;

    definirId(&p, id);
    definirGeracao(&p, geracao);

    char *nomeCopia = duplicarString(nome);
    char *descricaoCopia = duplicarString(descricao);

    definirNome(&p, nomeCopia);
    definirDescricao(&p, descricaoCopia);
    
    definirTipos(&p, 0, tipo1);
    if (tipo2 != NULL) {
        definirTipos(&p, 1, tipo2);
    }

    for (int i = 0; i < 6; i++) {
        if (habilidades[i] != NULL) {
            definirHabilidades(&p, i, habilidades[i]);
        } else {
            strcpy(p.habilidades[i], "");
        }
    }

    definirPeso(&p, peso);
    definirAltura(&p, altura);
    definirTaxaCaptura(&p, taxaCaptura);
    definirLendario(&p, lendario);
    definirDataCapturaData(&p, dataCaptura);

    return p;
}

// Função para separar uma linha CSV em campos
int separarLinhaCSV(char *linha, char **campos, int maxCampos) {
    int contadorCampos = 0;
    char *ptr = linha;
    int dentroAspas = 0;
    char *inicioCampo = ptr;

    while (*ptr && contadorCampos < maxCampos) {
        if (*ptr == '"') {
            dentroAspas = !dentroAspas;
        } else if (*ptr == ',' && !dentroAspas) {
            *ptr = '\0';
            campos[contadorCampos++] = inicioCampo;
            inicioCampo = ptr + 1;
        }
        ptr++;
    }
    if (contadorCampos < maxCampos) {
        campos[contadorCampos++] = inicioCampo;
    }

    return contadorCampos;
}

// Lê os Pokémons de um arquivo CSV
void lerPokemons(FILE *file, Pokemon *pokedex, int *n) {
    char linha[1024];

    fgets(linha, sizeof(linha), file); // Ignora a primeira linha (cabeçalho)

    while (fgets(linha, sizeof(linha), file) != NULL) {
        linha[strcspn(linha, "\n")] = '\0'; // Remove o caractere de nova linha

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); // Inicializa o Pokémon

        char *campos[12];
        int contadorCampos = separarLinhaCSV(linha, campos, 12);

        p.id = atoi(campos[0]);
        p.geracao = atoi(campos[1]);
        p.nome = duplicarString(campos[2]);
        p.descricao = duplicarString(campos[3]);

        definirTipos(&p, 0, campos[4]);
        if (strlen(campos[5]) > 0) {
            definirTipos(&p, 1, campos[5]);
        } else {
            strcpy(p.tipos[1], "");
        }

        // Processa as habilidades do Pokémon
        char *campoHabilidades = campos[6];
        if (campoHabilidades[0] == '"' && campoHabilidades[strlen(campoHabilidades) - 1] == '"') {
            campoHabilidades[strlen(campoHabilidades) - 1] = '\0';
            campoHabilidades++;
        }
        if (campoHabilidades[0] == '[' && campoHabilidades[strlen(campoHabilidades) - 1] == ']') {
            campoHabilidades[strlen(campoHabilidades) - 1] = '\0';
            campoHabilidades++;
        }

        char *tokenHabilidade;
        char *restoHabilidades = campoHabilidades;
        int indiceHabilidade = 0;
        while ((tokenHabilidade = strtok_r(restoHabilidades, ",", &restoHabilidades)) && indiceHabilidade < 6) {
            while (*tokenHabilidade == ' ' || *tokenHabilidade == '\'') tokenHabilidade++;
            char *fimTemp = tokenHabilidade + strlen(tokenHabilidade) - 1;
            while (fimTemp > tokenHabilidade && (*fimTemp == ' ' || *fimTemp == '\'')) {
                *fimTemp = '\0';
                fimTemp--;
            }
            if (strlen(tokenHabilidade) > 0) {
                definirHabilidades(&p, indiceHabilidade, tokenHabilidade);
                indiceHabilidade++;
            }
        }
        for (; indiceHabilidade < 6; indiceHabilidade++) {
            strcpy(p.habilidades[indiceHabilidade], "");
        }

        p.peso = atof(campos[7]);
        p.altura = atof(campos[8]);
        p.taxaCaptura = atoi(campos[9]);
        p.lendario = atoi(campos[10]);
        p.dataCaptura = stringParaData(campos[11]);

        pokedex[*n] = p;
        (*n)++;
    }
}

// Exibe as informações de um Pokémon
void exibirPokemon(Pokemon *p) {
    printf("[#%d -> %s: %s - ['", obterId(p), obterNome(p), obterDescricao(p));

    int numTipos = obterNumeroDeTipos(p);
    if (numTipos > 0) {
        printf("%s", obterTipos(p, 0));
    }
    if (numTipos > 1) {
        printf("', '%s", obterTipos(p, 1));
    }
    printf("'] - ");

    int numHabilidades = obterNumeroDeHabilidades(p);
    printf("[");
    for (int i = 0 ; i < numHabilidades ; i++) {
        printf("'%s'", obterHabilidades(p, i));
        if (i < numHabilidades - 1) {
            printf(", ");
        }
    }
    printf("] - ");

    printf("%.1fkg - ", obterPeso(p));
    printf("%.1fm - ", obterAltura(p));
    printf("%d%% - ", obterTaxaCaptura(p));
    printf("%s - ", obterLendario(p) ? "true" : "false");
    printf("%d gen] - ", obterGeracao(p));

    char *dataCaptura = dataParaString(obterDataCaptura(p));
    printf("%s", dataCaptura);
    free(dataCaptura);

    printf("\n");
}

int main () {
    char *caminhoCSV = "/tmp/pokemon.csv";
    FILE *arquivo = fopen(caminhoCSV, "r");

    if (arquivo == NULL) {
        return 1;
    }

    Pokemon pokedex[801]; // Limite de 801 Pokémons
    int totalPokemons = 0;

    lerPokemons(arquivo, pokedex, &totalPokemons);

    fclose(arquivo);

    // Loop para buscar Pokémon por ID
    char idEntrada[10];
    scanf("%s", idEntrada);
    while (strcmp(idEntrada, "FIM") != 0) {
        int id = atoi(idEntrada);
        bool encontrado = false;
        for (int i = 0 ; i < totalPokemons ; i++) {
            if (pokedex[i].id == id) {
                exibirPokemon(&pokedex[i]);
                encontrado = true;
                break;
            }
        }
        
        scanf("%s", idEntrada);
    }

    // Libera a memória alocada para nome e descrição dos Pokémons
    for (int i = 0; i < totalPokemons; i++) {
        free(pokedex[i].nome);
        free(pokedex[i].descricao);
    }

    return 0;
}
