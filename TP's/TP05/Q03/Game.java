
import java.io.*;
import java.util.Scanner;
import java.util.Date;

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

    private static void swap(Game[] arr, int i, int j, int[] heapMovimentacoes) {
        if (i == j)
            return;
        Game tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        heapMovimentacoes[0] += 3;
    }

    // ======= Comparador: Estimated_owners (desempate por AppID) =======
    public static int compareEstimatedOwners(Game a, Game b, int[] heapComparacoes) {
        heapComparacoes[0]++; // conta comparação
        int oa = a.getEstimatedOwners();
        int ob = b.getEstimatedOwners();

        if (oa < ob)
            return -1;
        if (oa > ob)
            return 1;

        // Desempate por AppID
        int ia = a.getID();
        int ib = b.getID();
        heapComparacoes[0]++;
        if (ia < ib)
            return -1;
        if (ia > ib)
            return 1;
        return 0;
    }

    public static void heapSortByEstimatedOwners(int n, Game[] array, int[] heapComparacoes, int[] heapMovimentacoes) {
        if (n <= 1)
            return;

        heapComparacoes[0] = 0;
        heapMovimentacoes[0] = 0;

        // Guarda referência do vetor original (0-based)
        Game[] orig = array;

        Game[] heap = new Game[n + 1];
        for (int i = 0; i < n; i++) {
            heap[i + 1] = orig[i];
        }

        // Construção do max-heap
        for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
            construir(heap, tamHeap, heapMovimentacoes, heapComparacoes);
        }

        // Ordenação (extrai o maior para o fim e reheapifica)
        int tamHeap = n;
        while (tamHeap > 1) {
            swap(heap, 1, tamHeap--, heapMovimentacoes);
            reconstruir(tamHeap, heap, heapMovimentacoes, heapComparacoes);
        }

        // Copia de volta (1-based -> 0-based) no vetor original do chamador
        for (int i = 0; i < n; i++) {
            orig[i] = heap[i + 1];
        }
    }

    public static void construir(Game[] array, int tamHeap, int[] heapMovimentacoes, int[] heapComparacoes) {
        for (int i = tamHeap; i > 1 && compareEstimatedOwners(array[i], array[i / 2], heapComparacoes) > 0; i /= 2) {
            swap(array, i, i / 2, heapMovimentacoes);
        }
    }

    public static void reconstruir(int tamHeap, Game[] array, int[] heapMovimentacoes, int[] heapComparacoes) {
        int i = 1;
        while (i <= (tamHeap / 2)) { // enquanto tiver pelo menos 1 filho
            int filho = getMaiorFilho(i, tamHeap, array, heapComparacoes);
            if (compareEstimatedOwners(array[i], array[filho], heapComparacoes) < 0) {
                swap(array, i, filho, heapMovimentacoes);
                i = filho; // desce
            } else {
                break; // já está em posição
            }
        }
    }

    public static int getMaiorFilho(int i, int tamHeap, Game[] array, int[] heapComparacoes) {
        int filho;

        // se só existe o filho da esquerda (último nó com um filho)
        if (2 * i == tamHeap) {
            filho = 2 * i;
        } else {
            // existem dois filhos: compara qual é o maior
            int esq = 2 * i;
            int dir = 2 * i + 1;

            // usa o comparador personalizado (e conta comparação)
            if (compareEstimatedOwners(array[esq], array[dir], heapComparacoes) > 0) {
                filho = esq; // filho esquerdo é o maior
            } else {
                filho = dir; // filho direito é o maior
            }
        }

        return filho;
    }

    public static void main(String[] args) throws IOException {
        String caminho = "/tmp/games.csv"; // ou "/tmp/games.csv" no Linux
        String matricula = "887920";

        Game[] jogos = new Game[10000];
        int total = 0;

        // 1) Carregar o CSV
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), "UTF-8"));

        String linha = br.readLine(); // pula cabeçalho
        while ((linha = br.readLine()) != null) {
            jogos[total++] = lerCsv(linha); // seu método que converte CSV → Game
        }
        br.close();

        // 2) Ler IDs até FIM e montar vetor 'selecionados'
        Game[] selecionados = new Game[10000];
        int n = 0;

        Scanner sc = new Scanner(System.in, "UTF-8");
        while (sc.hasNextLine()) {
            String s = sc.nextLine().trim();

            if (s.equals("FIM"))
                break;
            if (s.length() == 0)
                continue;

            int id = Integer.parseInt(s);

            // busca linear pelo ID dentro do vetor de jogos
            for (int i = 0; i < total; i++) {
                if (jogos[i] != null && jogos[i].getID() == id) {
                    selecionados[n++] = jogos[i];
                    break;
                }
            }
        }

        // 3) Ordenação com Heapsort
        int[] heapComparacoes = { 0 };
        int[] heapMovimentacoes = { 0 };

        long inicio = new Date().getTime();
        heapSortByEstimatedOwners(n, selecionados, heapComparacoes, heapMovimentacoes);
        long fim = new Date().getTime();
        long tempoSeg = (fim - inicio);

        // 4) Imprimir em ordem
        for (int i = 0; i < n; i++) {
            System.out.println(selecionados[i].toString());
            // ou imprimirJogo(selecionados[i]); se tiver método próprio
        }
        int numeroComparacoes = heapComparacoes[0];
        int numeroMovimentacoes = heapMovimentacoes[0];
        // 5) Criar arquivo de log
        PrintWriter pw = new PrintWriter(matricula + "_heapsort.txt", "UTF-8");
        pw.println(matricula + "\t" + numeroComparacoes+ "\t" + numeroMovimentacoes + "\t" + tempoSeg);
        pw.close();
        sc.close();
    }
}
