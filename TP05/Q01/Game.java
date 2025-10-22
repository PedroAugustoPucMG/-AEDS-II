
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

    public Game(){

    }
    // Construtor
    public Game(int id, String nome, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages, int metacriticScore, float userScore, int achievements, String[] publishers, String[] developers, String[] categories, String[] genres, String[] tags){
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
    public int getID(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public String getReleaseDate(){
        return releaseDate;
    }
    public int getEstimatedOwners(){
        return estimatedOwners;
    }
    public float getPrice(){
        return price;
    }
    public String[] getSupportedLanguages(){
        return supportedLanguages;
    }
    public int getMetacriticScore(){
        return metacriticScore;
    }
    public float getUserScore(){
        return userScore;
    }
    public int getAchievements(){
        return achievements;
    }
    public String[] getPublishers(){
        return publishers;
    }
    public String[] getDevelopers(){
        return developers;
    }
    public String[] getCategories(){
        return categories;
    }
    public String[] getGenres(){
        return genres;
    }
    public String[] getTags(){
        return tags;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public void setEstimatedOwners(int estimatedOwners){
        this.estimatedOwners = estimatedOwners;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public void setSupportedLanguages(String[] supportedLanguages){
        this.supportedLanguages = supportedLanguages;
    }
    public void setMetacriticScore(int metacriticScore){
        this.metacriticScore = metacriticScore;
    }
    public void setUserScore(float userScore){
        this.userScore = userScore;
    }
    public void setAchievements(int achievements){
        this.achievements = achievements;
    }
    public void setPublishers(String[] publishers){
        this.publishers = publishers;
    }
    public void setDevelopers(String[] developers){
        this.developers = developers;
    }
    public void setCategories(String[] categories){
        this.categories = categories;
    }
    public void setGenres(String[] genres){
        this.genres = genres;
    }
    public void setTags(String[] tags){
        this.tags = tags;
    }

    // Método toString para exibir as informações do jogo
    @Override
    public String toString(){
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
                    arrayToString(publishers) + " ## "+
                    arrayToString(developers) + " ## " +
                    arrayToString(categories) + " ## " +
                    arrayToString(genres) + " ## " +
                    arrayToString(tags) + " ##";
        return resultado;
    }
    // Método auxiliar para converter arrays em strings formatadas
    public static String arrayToString(String[] array){
        String resultado = "[";
        for(int i = 0; i < array.length; i++){
            resultado += array[i];
            if(i < array.length - 1){
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }
    // Método para ler uma linha do CSV e retornar um objeto Game preenchido
    public static Game lerCsv (String linha){

        //Atributos é um array de strings que vai receber os valores da linha, nele vamos separar os valores por vírgula, ou seja, cada valor separado por vírgula vai ser um elemento do array
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Expressão regular para considerar vírgulas dentro de aspas
        Game g = new Game(); // Cria um novo objeto Game
        // Preenche os atributos do objeto Game com os valores do array atributos
        // ID
        g.setId(Integer.parseInt(atributos[0])); // Converte o ID para inteiro e define no objeto
        // Nome
        g.setNome(atributos[1]); // Define o nome do jogo
        // Data de lançamento
        g.setReleaseDate(verificarData(atributos[2])); // Preciso verificar se o dia e o mes estão vazios, se estiverem, colocar 01
        // Estimated Owners
        g.setEstimatedOwners(normalizarEstimatedOwners(atributos[3])); // Número estimado de usuários: remover vírgulas e caracteres não numéricos
        // Preço
        g.setPrice(normalizarPrice(atributos[4])); // Verifica se o preço é "Free to Play", se for retorna 0.0, se não apenas converte para float e retorna
        // Supported Languages
        g.setSupportedLanguages(extrairLinguagens(atributos[5])); // Método para extrair listas de strings entre colchetes e virgulas
        // Metacritic Score sem isEmpty
        if(atributos[6].equals("")){ // Se estiver vazio
            g.setMetacriticScore(-1); // Se estiver vazio, define como -1
        } else {
            g.setMetacriticScore(Integer.parseInt(atributos[6])); // Converte para inteiro e define no objeto
        }
        // User Score sem isEmpty e sem tbd
        if(atributos[7].equals("") || atributos[7].equals("tbd")){ // Se estiver vazio ou for "tbd"
            g.setUserScore(-1.0f); // Se estiver vazio ou for "tbd", define como -1.0
        } else {
            g.setUserScore(Float.parseFloat(atributos[7])); // Converte para float e define no objeto
        }
        // Achievements 
        if(atributos[8].equals("")){ // Se estiver vazio
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
        mes = mesParaNumero(partes[0]);        // converte mês por extenso em número
        dia = partes[1];                       // pega o dia (sem vírgula porque já removemos antes)
        ano = partes[2];                       // pega o ano
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
        case "jan": case "january": return "01";
        case "feb": case "february": return "02";
        case "mar": case "march": return "03";
        case "apr": case "april": return "04";
        case "may": return "05";
        case "jun": case "june": return "06";
        case "jul": case "july": return "07";
        case "aug": case "august": return "08";
        case "sep": case "september": return "09";
        case "oct": case "october": return "10";
        case "nov": case "november": return "11";
        case "dec": case "december": return "12";
        default: return "01"; // fallback
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
    // Separa uma lista de strings que estão no formato ["str1", "str2", "str3"] em um array de strings
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



    // Número estimado de usuários: remover vírgulas e caracteres não numéricos, faça sem isEmpty, sem replaceAll, sem isDigit
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
    // Verifica se o preço é "Free to Play", se for retorna 0.0, se não apenas converte para float e retorna
    public static float normalizarPrice(String priceStr) {
        if (priceStr.equals("Free to Play")) {
            return 0.0f;
        }
        if (priceStr.isEmpty()) {
            return 0.0f; // Retorna 0.0 se estiver vazio
        }
        return Float.parseFloat(priceStr); // Converte para float
    }



    public static void selecaoPorNome(Game[] array, int n){

        for (int i = 0; i < n - 1; i++) {
        int menor = i;
            for (int j = i + 1; j < n; j++) {
               if (compareStrings(array[j].getNome(), array[menor].getNome()) < 0) {
                    menor = j;
            }
        }
        Game tmp = array[i];
        array[i] = array[menor];
        array[menor] = tmp;
        }
    }

    public static int compareStrings(String a, String b) {
    int i = 0;

    // Percorre enquanto as duas strings ainda tiverem caracteres
    while (i < a.length() && i < b.length()) {
        char ca = a.charAt(i);
        char cb = b.charAt(i);

        if (ca != cb) {
            // retorna diferença de valores ASCII 
            return ca - cb;
        }

        i++; // vai pro próximo caractere
    }

    // Se todos os caracteres comparados são iguais até uma das strings acabar,
    // a menor string (em tamanho) é considerada menor.
    if (a.length() < b.length()) return -1;
    else if (a.length() > b.length()) return 1;
    else return 0;
}

public static int pesquisaBinariaNome(Game[] array, int n, String nomeBuscado, int[] comparacoes) {
    nomeBuscado = nomeBuscado.trim(); // remove espaços em branco
    int esq = 0;
    int dir = n - 1;

    while (esq <= dir) {
        int meio = (esq + dir) / 2;

        // comparação de nomes usando sua função manual
        int comparacao = compareStrings(array[meio].getNome(), nomeBuscado);
        comparacoes[0]++; // incrementa comparações

        if (comparacao == 0) {
            return meio; // nome encontrado
        } else if (comparacao > 0) {
            dir = meio - 1; // o nome procurado vem antes
        } else {
            esq = meio + 1; // o nome procurado vem depois
        }
    }
    return -1; // não encontrado
}




 public static void main(String[] args){
        
        String arquivo = "/tmp/games.csv";
        Game[] jogos = new Game[10000];
        
        int count = 0;
        int numeroComparacoes = 0;
        long inicioBusca = 0;
        long fimBusca = 0;

        try {
            // Usa InputStreamReader com UTF-8 para LER o CSV corretamente
            BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream(arquivo), "UTF-8")
        );
            String linha;
            br.readLine(); // Pula o cabeçalho
            while ((linha = br.readLine()) != null) {
                jogos[count++] = lerCsv(linha);
            }
            br.close();
        } catch (IOException e) { 
            e.printStackTrace();
        }
        Scanner scanf = new Scanner(System.in);

        Game[] selecionados = new Game[10000];
        int n = 0;
        while (true) {
            String entrada = scanf.nextLine().trim();
            if (entrada.equals("FIM")) break;

            int idProcurado = Integer.parseInt(entrada);

            Game encontrado = null;
            for (int i = 0; i < count; i++) {
                if (jogos[i] != null && jogos[i].getID() == idProcurado) {
                    encontrado = jogos[i];
                    break;
                }
            }
        if (encontrado != null) {
            selecionados[n++] = encontrado; // insere no fim
        }
        // Se não encontrar, simplesmente ignora (não insere)
        }
        
       selecaoPorNome(selecionados, n);

       int[] contadorComparacao = {0}; // É um array para simular passagem por referência, ou seja, para não perder o valor ao sair do método
       inicioBusca = new Date().getTime(); // Marca o início da busca

        while (true) {
        String nomeBusca = scanf.nextLine();
        if (nomeBusca.equals("FIM")) break;

        int pos = pesquisaBinariaNome(selecionados, n, nomeBusca, contadorComparacao);
        System.out.println(pos >= 0 ? " SIM" : " NAO");  // usa System.out
    }

    fimBusca = new Date().getTime(); // Marca o fim da busca
    numeroComparacoes = contadorComparacao[0];

     // Criação do arquivo de log

        String matricula = "887920";

        long tempoExecucao = fimBusca - inicioBusca;
        
        try (PrintWriter log = new PrintWriter(new FileWriter(matricula + "_binaria.txt"))) { 
            // Matrícula \t Tempo \t Número de comparações
            log.println(matricula + "\t" + tempoExecucao + "\t" + numeroComparacoes);
        } catch (IOException e) {
            System.out.println("Erro");
        }

        scanf.close();
        
}
}

