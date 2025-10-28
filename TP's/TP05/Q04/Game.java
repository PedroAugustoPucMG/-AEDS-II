
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.nio.charset.StandardCharsets;

class Game {
    private int id;
    private String nome;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private String[] supportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;

    public Game() {

    }

    // Construtor
    public Game(int id, String nome, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages,
            int metacriticScore, float userScore, int achievements, String[] publishers, String[] developers,
            String[] categories, String[] genres, String[] tags) {
        this.id = id;
        this.nome = nome;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Getters e Setters
    public int getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public String[] getDevelopers() {
        return developers;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public void setDevelopers(String[] developers) {
        this.developers = developers;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    // Método toString para exibir as informações do jogo
    @Override
    public String toString() {
        String resultado;
        resultado = "=> " +
                id + " ## " +
                nome + " ## " +
                releaseDate + " ## " +
                estimatedOwners + " ## " +
                price + " ## " +
                arrayToString(supportedLanguages) + " ## " +
                metacriticScore + " ## " +
                userScore + " ## " +
                achievements + " ## " +
                arrayToString(publishers) + " ## " +
                arrayToString(developers) + " ## " +
                arrayToString(categories) + " ## " +
                arrayToString(genres) + " ## " +
                arrayToString(tags) + " ##";
        return resultado;
    }

    // Método auxiliar para converter arrays em strings formatadas
    public static String arrayToString(String[] array) {
        String resultado = "[";
        for (int i = 0; i < array.length; i++) {
            resultado += array[i];
            if (i < array.length - 1) {
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }

    // Método para ler uma linha do CSV e retornar um objeto Game preenchido
    public static Game lerCsv(String linha) {

        // Atributos é um array de strings que vai receber os valores da linha, nele
        // vamos separar os valores por vírgula, ou seja, cada valor separado por
        // vírgula vai ser um elemento do array
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Expressão regular para considerar
                                                                                   // vírgulas dentro de aspas
        Game g = new Game(); // Cria um novo objeto Game
        // Preenche os atributos do objeto Game com os valores do array atributos
        // ID
        g.setId(Integer.parseInt(atributos[0])); // Converte o ID para inteiro e define no objeto
        // Nome
        g.setNome(atributos[1]); // Define o nome do jogo
        // Data de lançamento
        g.setReleaseDate(verificarData(atributos[2])); // Preciso verificar se o dia e o mes estão vazios, se estiverem,
                                                       // colocar 01
        // Estimated Owners
        g.setEstimatedOwners(normalizarEstimatedOwners(atributos[3])); // Número estimado de usuários: remover vírgulas
                                                                       // e caracteres não numéricos
        // Preço
        g.setPrice(normalizarPrice(atributos[4])); // Verifica se o preço é "Free to Play", se for retorna 0.0, se não
                                                   // apenas converte para float e retorna
        // Supported Languages
        g.setSupportedLanguages(extrairLinguagens(atributos[5])); // Método para extrair listas de strings entre
                                                                  // colchetes e virgulas
        // Metacritic Score sem isEmpty
        if (atributos[6].equals("")) { // Se estiver vazio
            g.setMetacriticScore(-1); // Se estiver vazio, define como -1
        } else {
            g.setMetacriticScore(Integer.parseInt(atributos[6])); // Converte para inteiro e define no objeto
        }
        // User Score sem isEmpty e sem tbd
        if (atributos[7].equals("") || atributos[7].equals("tbd")) { // Se estiver vazio ou for "tbd"
            g.setUserScore(-1.0f); // Se estiver vazio ou for "tbd", define como -1.0
        } else {
            g.setUserScore(Float.parseFloat(atributos[7])); // Converte para float e define no objeto
        }
        // Achievements
        if (atributos[8].equals("")) { // Se estiver vazio
            g.setAchievements(0); // Se estiver vazio, define como 0
        } else {
            g.setAchievements(Integer.parseInt(atributos[8])); // Converte para inteiro e define no objeto
        }

        // Publishers
        g.setPublishers(extrairLista(atributos[9]));
        // Developers
        g.setDevelopers(extrairLista(atributos[10]));
        // Categories
        g.setCategories(extrairLista(atributos[11]));
        // Genres
        g.setGenres(extrairLista(atributos[12]));
        // Tags
        g.setTags(extrairLista(atributos[13]));

        return g; // Retorna o objeto Game preenchido
    }

    // Verifica e normaliza a data de lançamento
    public static String verificarData(String data) {
        // Remove aspas e vírgulas que podem vir do CSV
        data = data.replace("\"", "").replace(",", "");

        // Divide a data em partes (pode vir "Oct 18 2018", "May 2010" ou "2008")
        String[] partes = data.split(" ");

        // Valores padrão caso algum campo falte
        String dia = "01";
        String mes = "01";
        String ano = "0000";

        if (partes.length == 3) {
            // Exemplo: "Oct 18 2018"
            mes = mesParaNumero(partes[0]); // converte mês por extenso em número
            dia = partes[1]; // pega o dia (sem vírgula porque já removemos antes)
            ano = partes[2]; // pega o ano
        } else if (partes.length == 2) {
            // Exemplo: "May 2010"
            mes = mesParaNumero(partes[0]);
            ano = partes[1];
        } else if (partes.length == 1) {
            // Exemplo: "2008"
            ano = partes[0];
        }

        // Ajusta para 2 dígitos no dia e no mês
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        return dia + "/" + mes + "/" + ano;
    }

    // Converte o nome do mês para número
    public static String mesParaNumero(String mes) {
        mes = mes.toLowerCase();
        switch (mes) {
            case "jan":
            case "january":
                return "01";
            case "feb":
            case "february":
                return "02";
            case "mar":
            case "march":
                return "03";
            case "apr":
            case "april":
                return "04";
            case "may":
                return "05";
            case "jun":
            case "june":
                return "06";
            case "jul":
            case "july":
                return "07";
            case "aug":
            case "august":
                return "08";
            case "sep":
            case "september":
                return "09";
            case "oct":
            case "october":
                return "10";
            case "nov":
            case "november":
                return "11";
            case "dec":
            case "december":
                return "12";
            default:
                return "01"; // fallback
        }
    }

    public static String[] extrairLinguagens(String campo) {
        if (campo == null || campo.equals(""))
            return new String[0];

        // Remove colchetes e aspas simples/duplas
        campo = campo.replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("'", "")
                .trim();

        if (campo.equals(""))
            return new String[0];

        // Divide por vírgula e limpa espaços extras
        String[] partes = campo.split(",");
        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim(); // remove espaços no início/fim
        }

        return partes;
    }

    // Método para extrair listas de strings entre colchetes e virgulas
    // Transforma campo com colchetes ou vírgulas em lista de strings
    // Separa uma lista de strings que estão no formato ["str1", "str2", "str3"] em
    // um array de strings
    public static String[] extrairLista(String campo) {
        if (campo == null || campo.equals(""))
            return new String[0];

        // Remove colchetes e aspas simples/duplas
        campo = campo.replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .trim();

        if (campo.equals(""))
            return new String[0];

        // Divide por vírgula e limpa espaços extras
        String[] partes = campo.split(",");
        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim(); // remove espaços no início/fim
        }

        return partes;
    }

    // Número estimado de usuários: remover vírgulas e caracteres não numéricos,
    // faça sem isEmpty, sem replaceAll, sem isDigit
    public static int normalizarEstimatedOwners(String ownersStr) {
        String numeros = "";
        for (int i = 0; i < ownersStr.length(); i++) {
            char c = ownersStr.charAt(i);
            if (c >= '0' && c <= '9') { // Verifica se o caractere é um dígito
                numeros += c; // Concatena o dígito à string numeros
            }
        }
        if (numeros.equals("")) {
            return 0; // Retorna 0 se não houver números
        }
        return Integer.parseInt(numeros); // Converte a string de números para inteiro e retorna

    }

    // Verifica se o preço é "Free to Play", se for retorna 0.0, se não apenas
    // converte para float e retorna
    public static float normalizarPrice(String priceStr) {
        if (priceStr.equals("Free to Play")) {
            return 0.0f;
        }
        if (priceStr.isEmpty()) {
            return 0.0f; // Retorna 0.0 se estiver vazio
        }
        return Float.parseFloat(priceStr); // Converte para float
    }

    public static void mergeSortByPrice(Game[] arr, int n, int[] comps, int[] movs) {
        if (n <= 1)
            return;
        if (comps != null)
            comps[0] = 0;
        if (movs != null)
            movs[0] = 0;
        mergesort(arr, 0, n - 1, comps, movs);
    }

    private static void mergesort(Game[] arr, int esq, int dir, int[] comps, int[] movs) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(arr, esq, meio, comps, movs);
            mergesort(arr, meio + 1, dir, comps, movs);
            intercalar(arr, esq, meio, dir, comps, movs);
        }
    }

    private static int cmpPrice(Game a, Game b, int[] comps) {
        if (comps != null)
            comps[0]++;
        double pa = a.getPrice();
        double pb = b.getPrice();
        if (pa < pb)
            return -1;
        if (pa > pb)
            return 1;
        // desempate por AppID (crescente)
        int ia = a.getID();
        int ib = b.getID();
        // se quiser contar também essa comparação extra:
        if (comps != null)
            comps[0]++;
        if (ia < ib)
            return -1;
        if (ia > ib)
            return 1;
        return 0;
    }


    // intercalar SEM sentinela (fiel ao seu loop e sem operador ternário se quiser)
    private static void intercalar(Game[] arr, int esq, int meio, int dir, int[] comps, int[] movs) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Game[] L = new Game[n1];
        Game[] R = new Game[n2];

        // copia subarrays
        for (int i = 0; i < n1; i++)
            L[i] = arr[esq + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[meio + 1 + j];

        int i = 0, j = 0, k = esq;

        // intercala enquanto ambos têm elementos
        while (i < n1 && j < n2) {
            if (cmpPrice(L[i], R[j], comps) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
            if (movs != null)
                movs[0]++; // 1 atribuição no array principal
        }

        // copia o resto do L (se sobrar)
        while (i < n1) {
            arr[k++] = L[i++];
            if (movs != null)
                movs[0]++;
        }

        // copia o resto do R (se sobrar)
        while (j < n2) {
            arr[k++] = R[j++];
            if (movs != null)
                movs[0]++;
        }
    }

public static void main(String[] args) throws IOException {


    

    String arquivo = "/tmp/games.csv";
    String matricula = "887920";

    Game[] jogos = new Game[10000];
    Scanner scanf = new Scanner(System.in, "UTF-8");

    int total = 0;

    // 1) Carregar o CSV
    BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8)
    );

    String linha = br.readLine(); // pula cabeçalho
    while ((linha = br.readLine()) != null) {
        jogos[total++] = lerCsv(linha);
    }
    br.close();

    // 2) Ler IDs até FIM e montar vetor 'selecionados'
    Game[] selecionados = new Game[10000];
    int n = 0;

    while (scanf.hasNextLine()) {
        String s = scanf.nextLine().trim();

        if (s.equals("FIM")) break;
        if (s.length() == 0) continue;

        int id = Integer.parseInt(s);

        // busca linear pelo ID dentro do vetor de jogos
        for (int i = 0; i < total; i++) {
            if (jogos[i] != null && jogos[i].getID() == id) {
                selecionados[n++] = jogos[i];
                break;
            }
        }
    }

    // 3) Ordenar por preço com MergeSort
    int[] comps = {0};
    int[] movs  = {0};

    long inicio = new Date().getTime();
    mergeSortByPrice(selecionados, n, comps, movs);
    long fim = new Date().getTime();

    long tempoExecucao = (fim - inicio); // segundos

    // 4) Imprimir os 5 mais caros e 5 mais baratos
    System.out.println("| 5 pre\u00E7os mais caros |");
    for (int i = n - 1; i >= Math.max(0, n - 5); i--) {
        System.out.println(selecionados[i].toString());
        // ou imprimirJogo(selecionados[i]);
    }

    System.out.println("\n| 5 pre\u00E7os mais baratos |");
    for (int i = 0; i < Math.min(5, n); i++) {
        System.out.println(selecionados[i].toString());
    }
    int numeroComparacoes = comps[0];
    int numeroMovimentacoes = movs[0];
    // 5) Criar o arquivo de log
    PrintWriter log = new PrintWriter(matricula + "_mergesort.txt", "UTF-8");
    log.println(matricula + "\t" + numeroComparacoes + "\t" + numeroMovimentacoes + "\t" + tempoExecucao);
    log.close();

    scanf.close();
}

}
