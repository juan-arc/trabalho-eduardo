# Weather App - Aplicativo de Previsão do Tempo

## Informações do Projeto

**Aluno:** Juan Pablo
**Usuário GitHub:** juan-arc
**Curso:** Engenharia de Software
**Período:** 2025/1

## Descrição

Aplicativo Android de previsão do tempo desenvolvido com base no Capítulo 7 do livro "Android 6 for Programmers" (WeatherViewer App). O aplicativo consome uma API REST para buscar dados meteorológicos e exibir previsões de múltiplos dias.

### Funcionalidades

- 🌡️ Consulta de previsão do tempo por cidade
- 📅 Previsão para múltiplos dias (configurável)
- 🎨 Interface moderna com Material Design
- 📱 RecyclerView para exibição de lista de previsões
- ⚠️ Tratamento de erros de rede e API
- 🌐 Consumo de API REST com OkHttp
- 📊 Parse de JSON com Gson

## Especificações Técnicas

### Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **SDK Mínimo:** Android 7.0 (API 24)
- **SDK Alvo:** Android 14 (API 34)
- **Bibliotecas:**
  - AndroidX Core KTX
  - Material Components
  - RecyclerView
  - OkHttp (requisições HTTP)
  - Gson (parsing JSON)
  - Lifecycle Components (ViewModel, LiveData)

### API Utilizada

**URL Base:** `http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather`

**Chave da API:** `AgentWeather2024_a8f3b9c1d7e2f5g6h4i9j0k1l2m3n4o5p6`

**Parâmetros:**
- `city`: Nome da cidade no formato "Cidade,Estado,País" (ex: `Passos,MG,BR`)
- `days`: Número de dias de previsão (ex: `7`)
- `APPID`: Chave de autenticação da API

**Exemplo de URL:**
```
http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather?city=Passos,MG,BR&days=7&APPID=AgentWeather2024_a8f3b9c1d7e2f5g6h4i9j0k1l2m3n4o5p6
```

### Estrutura de Dados

**Resposta JSON:**
```json
{
  "city": "Passos, MG, BR",
  "days": [
    {
      "date": "2025-12-17",
      "minTempC": 20.5,
      "maxTempC": 30.2,
      "humidity": 0.7,
      "description": "Sol com algumas nuvens e calor à tarde",
      "icon": "⛅"
    }
  ]
}
```

**Campos Exibidos:**
- Data da previsão
- Temperatura mínima e máxima (Celsius)
- Umidade (convertida para porcentagem)
- Descrição textual do clima
- Emoji do ícone climático

## Como Executar

### Pré-requisitos

- Android Studio (versão Arctic Fox ou superior)
- JDK 8 ou superior
- Emulador Android ou dispositivo físico com Android 7.0+

### Passos para Execução

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd WeatherApp
   ```

2. **Abra o projeto no Android Studio:**
   - File > Open > Selecione a pasta `WeatherApp`

3. **Sincronize as dependências:**
   - Android Studio sincronizará automaticamente as dependências do Gradle
   - Aguarde a conclusão do processo

4. **Configure um emulador ou dispositivo:**
   - **Emulador:** Tools > Device Manager > Create Device
   - **Dispositivo físico:** Ative o modo desenvolvedor e USB debugging

5. **Execute o aplicativo:**
   - Clique no botão "Run" (▶️) ou pressione Shift+F10
   - Selecione o dispositivo/emulador de destino

6. **Teste o aplicativo:**
   - Digite uma cidade no formato `Cidade,Estado,País` (ex: `Passos,MG,BR`)
   - Escolha o número de dias (padrão: 7)
   - Clique no botão de busca (FAB com ícone de lupa)
   - Visualize a lista de previsões

## Estrutura do Projeto

```
WeatherApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/juanpablo/weatherapp/
│   │       │   ├── MainActivity.kt           # Activity principal
│   │       │   ├── WeatherAdapter.kt         # Adapter do RecyclerView
│   │       │   ├── WeatherService.kt         # Serviço de API
│   │       │   ├── WeatherData.kt            # Modelo de dados
│   │       │   └── WeatherResponse.kt        # Modelo de resposta
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml     # Layout principal
│   │       │   │   └── weather_item.xml      # Layout do item da lista
│   │       │   └── values/
│   │       │       ├── strings.xml           # Strings do app
│   │       │       ├── colors.xml            # Cores
│   │       │       └── themes.xml            # Temas
│   │       └── AndroidManifest.xml           # Manifest
│   └── build.gradle                          # Configuração do módulo
├── build.gradle                              # Configuração do projeto
├── settings.gradle                           # Configurações Gradle
└── README.md                                 # Este arquivo
```

## Tratamento de Erros

O aplicativo implementa tratamento robusto de erros:

- ✅ Validação de campos vazios
- ✅ Timeout de conexão (30 segundos)
- ✅ Tratamento de erros de rede
- ✅ Tratamento de respostas HTTP com erro
- ✅ Tratamento de JSON inválido
- ✅ Mensagens de erro amigáveis ao usuário

## Capturas de Tela

### Tela Principal
- Campo de entrada para cidade
- Campo para número de dias
- FAB para buscar previsão

### Lista de Previsões
- Cards com informações de cada dia
- Emoji do clima
- Data, descrição, temperatura e umidade

## Histórico de Desenvolvimento

Commits sequenciais seguindo as etapas de desenvolvimento:

1. **2025-12-08:** Estrutura inicial do projeto criada no Android Studio
2. **2025-12-09:** Layout básico com input de cidade e botão de consulta
3. **2025-12-09:** Integração com API de previsão e tratamento de erro de rede
4. **2025-12-10:** Parse de JSON com campos personalizados
5. **2025-12-10:** Exibição da lista com dados meteorológicos
6. **2025-12-11:** Finalização, testes, ajustes e criação do README

## Referências

- Livro: "Android 6 for Programmers" - Capítulo 7 (WeatherViewer App)
- [OkHttp Documentation](https://square.github.io/okhttp/)
- [Gson Documentation](https://github.com/google/gson)
- [Material Design Components](https://material.io/develop/android)
- [Android Developer Guide](https://developer.android.com/)

## Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Desenvolvido por Juan Pablo (juan-arc)**
