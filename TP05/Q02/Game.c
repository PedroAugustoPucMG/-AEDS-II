//Aluno: Pedro Augusto Gomes de Araújo
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <locale.h>
#include <time.h>

#define TAMMAXLINHA 4096
#define TAMMAXCAMPO 1024

typedef struct {
    int id;
    char *nome;
    char *dataLanc;
    int donosEstimados;
    float preco;
    char **idiomas;        int qtdIdiomas;
    int notaMeta;
    float notaUsuarios;
    int conquistas;
    char **publicadoras;   int qtdPublicadoras;
    char **desenvolvedoras;int qtdDesenvolvedoras;
    char **categorias;     int qtdCategorias;
    char **generos;        int qtdGeneros;
    char **etiquetas;      int qtdEtiquetas;
} Jogo;



// Remove espaços do fim de uma string
void removerEspacosFim(char* s) {
    int i = strlen(s) - 1;
    while (i >= 0 && isspace((unsigned char)s[i])) {
        s[i] = '\0';
        i--;
    }
}

// Avança o ponteiro para pular espaços no início
void pularEspacos(char** s) {
    while (**s && isspace((unsigned char)**s)) (*s)++;
}

// Duplica uma string
char* copiarTexto(const char* s) {
    char* nova = malloc(strlen(s) + 1);
    if (nova) strcpy(nova, s);
    return nova;
}

// Compara duas strings ignorando maiúsculas/minúsculas
int compararSemCase(const char* a, const char* b) {
    while (*a && *b) {
        if (tolower(*a) != tolower(*b)) return 0;
        a++; b++;
    }
    return *a == *b;
}

// Limpa colchetes, aspas e formata vírgulas
void limparCampoLista(char* s) {
    char* w = s;
    for (char* r = s; *r; r++) {
        if (*r == '[' || *r == ']' || *r == '"' || *r == '\'') continue;
        *w++ = *r;
    }
    *w = '\0';

    char temp[TAMMAXCAMPO * 2];
    int j = 0;
    for (int i = 0; s[i] && j + 2 < sizeof(temp); i++) {
        if (s[i] == ',') {
            temp[j++] = ',';
            if (s[i + 1] && s[i + 1] != ' ') temp[j++] = ' ';
        } else temp[j++] = s[i];
    }
    temp[j] = '\0';
    strcpy(s, temp);
}

// Separa uma string em partes usando delimitador
char** separarTexto(const char* texto, const char* delim, int* qtd) {
    *qtd = 0;
    if (!texto || !*texto) return NULL;
    char* copia = copiarTexto(texto);
    char* token = strtok(copia, delim);
    char** lista = NULL;

    while (token) {
        char* p = token;
        pularEspacos(&p);
        removerEspacosFim(p);
        if (*p) {
            lista = realloc(lista, sizeof(char*) * (*qtd + 1));
            lista[*qtd] = copiarTexto(p);
            (*qtd)++;
        }
        token = strtok(NULL, delim);
    }
    free(copia);
    return lista;
}

// Libera memória de uma lista de strings
void liberarLista(char*** lista, int* qtd) {
    if (*lista) {
        for (int i = 0; i < *qtd; i++) free((*lista)[i]);
        free(*lista);
    }
    *lista = NULL;
    *qtd = 0;
}

// Imprime uma lista de strings
void imprimirLista(char** lista, int qtd) {
    printf("[");
    for (int i = 0; i < qtd; i++) {
        printf("%s%s", lista[i], (i == qtd - 1) ? "" : ", ");
    }
    printf("]");
}

// Formata datas do tipo "MMM d, yyyy" para "dd/MM/yyyy"
char* formatarData(const char* dataCsv) {
    char tmp[TAMMAXCAMPO];
    int j = 0;
    for (int i = 0; dataCsv[i] && j + 1 < sizeof(tmp); i++) {
        if (dataCsv[i] != '"') tmp[j++] = dataCsv[i];
    }
    tmp[j] = '\0';

    char buf[TAMMAXCAMPO];
    strcpy(buf, tmp);
    char *mes = NULL, *dia = NULL, *ano = NULL;
    char* p = buf;
    pularEspacos(&p);
    if (!*p) return copiarTexto("sem data");

    char* espaco = strchr(p, ' ');
    if (espaco) {
        *espaco = '\0';
        mes = p;
        char* resto = espaco + 1;
        pularEspacos(&resto);

        char* virgula = strchr(resto, ',');
        if (virgula) {
            *virgula = '\0';
            dia = resto;
            ano = virgula + 1;
            pularEspacos(&ano);
        } else {
            dia = "01";
            ano = resto;
        }
    } else {
        mes = "Jan";
        dia = "01";
        ano = p;
    }

    int m = 1;
    if (!strcmp(mes, "Jan")) m = 1; else if (!strcmp(mes, "Feb")) m = 2;
    else if (!strcmp(mes, "Mar")) m = 3; else if (!strcmp(mes, "Apr")) m = 4;
    else if (!strcmp(mes, "May")) m = 5; else if (!strcmp(mes, "Jun")) m = 6;
    else if (!strcmp(mes, "Jul")) m = 7; else if (!strcmp(mes, "Aug")) m = 8;
    else if (!strcmp(mes, "Sep")) m = 9; else if (!strcmp(mes, "Oct")) m = 10;
    else if (!strcmp(mes, "Nov")) m = 11; else if (!strcmp(mes, "Dec")) m = 12;

    int d = atoi(dia);
    int y = atoi(ano);
    char out[16];
    sprintf(out, "%02d/%02d/%04d", d, m, y);
    return copiarTexto(out);
}

// Lê uma linha CSV e preenche a struct Jogo
void lerJogo(Jogo* j, const char* linha) {
    char campo[TAMMAXCAMPO];
    int pos = 0, idx = 0;
    bool emAspas = false;

    memset(j, 0, sizeof(*j));
    j->notaMeta = -1; j->notaUsuarios = -1.0f; j->conquistas = 0;

    for (int i = 0;; i++) {
        char c = linha[i];
        bool fim = (c == '\0');

        if (!fim && c == '"') {
            emAspas = !emAspas;
            continue;
        }

        if (fim || (c == ',' && !emAspas)) {
            campo[pos] = '\0';
            switch (idx) {
                case 0: j->id = atoi(campo); break;
                case 1: j->nome = copiarTexto(campo); break;
                case 2: j->dataLanc = formatarData(campo); break;
                case 3: {
                    char temp[TAMMAXCAMPO]; strcpy(temp, campo);
                    char* h = strchr(temp, '-'); if (h) *h = '\0';
                    j->donosEstimados = atoi(temp);
                } break;
                case 4: j->preco = atof(campo); break;
                case 5: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->idiomas = separarTexto(temp, ",", &j->qtdIdiomas);
                } break;
                case 6: j->notaMeta = (campo[0] ? atoi(campo) : -1); break;
                case 7: j->notaUsuarios = (!campo[0] || compararSemCase(campo, "tbd")) ? -1.0f : atof(campo); break;
                case 8: j->conquistas = (campo[0] ? atoi(campo) : 0); break;
                case 9: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->publicadoras = separarTexto(temp, ",", &j->qtdPublicadoras);
                } break;
                case 10: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->desenvolvedoras = separarTexto(temp, ",", &j->qtdDesenvolvedoras);
                } break;
                case 11: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->categorias = separarTexto(temp, ",", &j->qtdCategorias);
                } break;
                case 12: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->generos = separarTexto(temp, ",", &j->qtdGeneros);
                } break;
                case 13: {
                    char temp[TAMMAXCAMPO]; strncpy(temp, campo, sizeof(temp)); temp[sizeof(temp)-1] = '\0';
                    limparCampoLista(temp);
                    j->etiquetas = separarTexto(temp, ",", &j->qtdEtiquetas);
                } break;
            }
            idx++; pos = 0;
            if (fim) break;
        } else if (pos + 1 < TAMMAXCAMPO) {
            campo[pos++] = c;
        }
    }
}

// Imprime um jogo no formato especificado
void imprimirJogo(const Jogo* j) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## ",
           j->id, j->nome, j->dataLanc, j->donosEstimados, j->preco);
    imprimirLista(j->idiomas, j->qtdIdiomas);
    printf(" ## %d ## %.1f ## %d ## ", j->notaMeta, j->notaUsuarios, j->conquistas);
    imprimirLista(j->publicadoras, j->qtdPublicadoras);
    printf(" ## ");
    imprimirLista(j->desenvolvedoras, j->qtdDesenvolvedoras);
    printf(" ## ");
    imprimirLista(j->categorias, j->qtdCategorias);
    printf(" ## ");
    imprimirLista(j->generos, j->qtdGeneros);
    printf(" ## ");
    imprimirLista(j->etiquetas, j->qtdEtiquetas);
    printf(" ##\n");
}

// Libera memória alocada de um jogo
void liberarJogo(Jogo* j) {
    free(j->nome);
    free(j->dataLanc);
    liberarLista(&j->idiomas, &j->qtdIdiomas);
    liberarLista(&j->publicadoras, &j->qtdPublicadoras);
    liberarLista(&j->desenvolvedoras, &j->qtdDesenvolvedoras);
    liberarLista(&j->categorias, &j->qtdCategorias);
    liberarLista(&j->generos, &j->qtdGeneros);
    liberarLista(&j->etiquetas, &j->qtdEtiquetas);
}


int compararNomes(const char *a, const char *b) {
    int i = 0;

    // percorre enquanto ambos tiverem caracteres
    while (a[i] != '\0' && b[i] != '\0') {
        if (a[i] != b[i]) {
            return a[i] - b[i]; // diferença ASCII determina ordem
        }
        i++;
    }

    // se chegou aqui, todos os caracteres até aqui são iguais
    // o menor nome (em tamanho) é considerado "menor"
    return strlen(a) - strlen(b);
}

void selecaoPorNome(Jogo *array, int n, int *comparacoes, int *movimentacoes) {
    *comparacoes = 0;
    *movimentacoes = 0;
    for (int i = 0; i < (n - 1); i++) {
      int menor = i;
      for (int j = (i + 1); j < n; j++){
        (*comparacoes)++;
         if (compararNomes(array[j].nome, array[menor].nome) < 0){
            menor = j;
         }
      }
      if (menor != i) {
            Jogo tmp = array[i];
            array[i] = array[menor];
            array[menor] = tmp;
            *movimentacoes += 3;
   }
}
}



/* ==========================================================
   Função principal
   ========================================================== */
int main() {
    setlocale(LC_NUMERIC, "C"); 
    const char* caminho =  "/tmp/games.csv";

    FILE* arq = fopen(caminho, "r");
    if (!arq) { perror("Erro ao abrir /tmp/games.csv"); return 1; }

    char linha[TAMMAXLINHA];
    int total = 0;

    if (fgets(linha, sizeof(linha), arq)) {
        while (fgets(linha, sizeof(linha), arq)) total++;
    }
    fclose(arq);

    Jogo* jogos = (Jogo*) malloc(sizeof(Jogo) * (total > 0 ? total : 1));
    int i = 0;

    arq = fopen(caminho, "r");
    if (!arq) { perror("Erro ao reabrir /tmp/games.csv"); free(jogos); return 1; }

    fgets(linha, sizeof(linha), arq);
    while (fgets(linha, sizeof(linha), arq)) {
        linha[strcspn(linha, "\r\n")] = 0;
        if (i < total) {
            lerJogo(&jogos[i], linha);
            i++;
        }
    }
    fclose(arq);

    Jogo selecionados[10000];
    int n = 0;

    // 1) IDs -> vetor selecionados
    char line[128];
    while (fgets(line, sizeof(line), stdin)) {
        line[strcspn(line, "\r\n")] = 0;
        if (strcmp(line, "FIM") == 0) break;
        if (*line == '\0') continue;
        int id = atoi(line);

        // busca linear no array de jogos
        for (int k = 0; k < total; k++) {
            if (jogos[k].id == id) { selecionados[n++] = jogos[k]; break; }
        }
    }
    
    int comparacoes = 0, movimentacoes = 0;

    clock_t inicioBusca = clock();
    selecaoPorNome(selecionados, n, &comparacoes, &movimentacoes);
    clock_t fimBusca = clock();
    double tempo = (double)(fimBusca - inicioBusca) / CLOCKS_PER_SEC;

    // 3) imprimir ordenado
    for (int i = 0; i < n; i++) {
        imprimirJogo(&selecionados[i]); // seu formato "=> id ## nome ## ..."
    }

    // 4) log
    const char* matricula = "887920"; // troque
    char nomeLog[64];
    sprintf(nomeLog, "%s_selecao.txt", matricula);
    FILE *log = fopen(nomeLog, "w");
    if (log) {
        fprintf(log, "%s\t%d\t%d\t%.6f\n", matricula, comparacoes, movimentacoes, tempo);
        fclose(log);
    }


    for (int k = 0; k < i; k++) liberarJogo(&jogos[k]);
    free(jogos);
    return 0;
}
