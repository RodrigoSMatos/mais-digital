# +Digital

Aplicativo Android educativo que ensina pessoas com pouca familiaridade com tecnologia — principalmente idosos — a usar aplicativos do celular através de tutoriais guiados, seguros e simples.

O usuário aprende sem medo de errar, em um ambiente de simulação onde nada é enviado de verdade.

## Sumário

- [Sobre](#sobre)
- [Funcionalidades do MVP](#funcionalidades-do-mvp)
- [Tecnologias](#tecnologias)
- [Como rodar](#como-rodar)
- [Arquitetura](#arquitetura)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Como funciona o tutorial guiado](#como-funciona-o-tutorial-guiado)
- [Acessibilidade](#acessibilidade)
- [Próximos passos](#próximos-passos)
- [Equipe](#equipe)

## Sobre

O +Digital é um app de educação digital. Ele simula o uso de aplicativos comuns (começando pelo WhatsApp) e guia o usuário passo a passo, destacando visualmente onde tocar a cada momento. Se a pessoa toca no lugar errado, recebe uma mensagem amigável e tenta de novo — sem punição, sem ansiedade.

A versão atual implementa o módulo de WhatsApp com 5 aulas completas. Outros módulos (Gmail, Google Maps) aparecem na interface marcados como "Em breve" e serão adicionados em versões futuras.

## Funcionalidades do MVP

- Tela inicial (Splash) com identidade visual do app
- Menu de aplicativos a aprender
- Lista de aulas com indicação visual das já concluídas (✓ verde)
- Tela de introdução de cada aula com descrição clara
- Tutorial guiado com:
  - Tela escurecida (overlay com spotlight)
  - Destaque pulsante no elemento correto
  - Cartão flutuante com instrução do passo
  - Detecção de clique correto (avança automaticamente)
  - Detecção de clique errado (mensagem amigável + tremor sutil)
- Tela final de parabéns com opção de repetir, voltar ou ir para o menu
- Configurações com seletor de tamanho de fonte (Pequeno / Médio / Grande)
- Aviso na primeira vez explicando que é uma simulação educativa
- Persistência local: aulas concluídas e preferências ficam salvas mesmo fechando o app
- 100% offline. Sem coleta de dados. Sem login.

### Aulas implementadas (módulo WhatsApp)

1. Adicionar um contato
2. Enviar uma mensagem
3. Enviar um áudio
4. Fazer uma chamada de vídeo
5. Inverter a câmera durante uma chamada

## Tecnologias

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Arquitetura:** MVVM com camada de domínio enxuta
- **Navegação:** Navigation Compose
- **Persistência:** DataStore Preferences
- **Concorrência:** Kotlin Coroutines + Flow
- **Testes:** JUnit + MockK
- **IDE:** Android Studio
- **Build:** Gradle Kotlin DSL com Version Catalog (`libs.versions.toml`)
- **Min SDK:** 24 (Android 7.0) — cobre cerca de 98% dos aparelhos
- **Target SDK:** 35 (Android 15)

## Como rodar

### Pré-requisitos

- Android Studio Hedgehog (2023.1.1) ou mais recente
- JDK 17

### Passo a passo

1. Clone o repositório:
```bash
   git clone https://github.com/RodrigoSMatos/mais-digital.git
```
2. Abra a pasta no Android Studio (`File > Open`)
3. Aguarde o Gradle sincronizar (pode levar alguns minutos na primeira vez)
4. Conecte um dispositivo Android (com depuração USB ativada) ou use um emulador (API 24+)
5. Selecione o dispositivo no dropdown do topo e clique no botão ▶ Run

### Rodar os testes unitários

Os testes do `TutorialEngine` (núcleo do app) rodam sem emulador:

Ou pelo Android Studio: clique direito em `TutorialEngineTest.kt` → Run.

## Arquitetura

O app segue **MVVM** com uma camada de **domínio** enxuta. A decisão de não adotar Clean Architecture completa foi consciente: para um MVP de 5 aulas e 1 módulo, três módulos Gradle separados seriam overengineering. Mas mantemos a parte mais importante: o `TutorialEngine` é Kotlin puro, sem dependência de Android, totalmente testável.

UI (Compose)  ──observa──▶  ViewModel  ──usa──▶  Repository  ──persiste──▶  DataStore
│
└─usa──▶  TutorialEngine (Kotlin puro)

## Estrutura de pastas

com.maisdigital.app/
├── core/
│   ├── ui/theme/                  cores, tipografia, dimensões acessíveis
│   ├── ui/components/             BotaoGrande, BarraTopo, ModifierShake
│   └── accessibility/             TamanhoTextoProvider
├── data/
│   ├── local/                     PreferenciasDataStore
│   ├── repository/                ProgressoRepository, ConfigRepository
│   └── catalog/                   catálogo estático de apps e aulas
├── domain/
│   ├── model/                     App, Aula, Passo, TamanhoTexto, TutorialState
│   └── tutorial/                  TutorialEngine, RegistroAlvos, ModifierAlvoTutorial, TutorialLocals
├── feature/
│   ├── splash/
│   ├── menuapps/
│   ├── listaaulas/
│   ├── introaula/
│   ├── tutorial/                  hospedeira do tutorial + componentes
│   ├── parabens/
│   ├── configuracoes/
│   └── simuladores/
│       └── whatsapp/              telas e componentes do simulador
├── navigation/                    NavHost e rotas
├── MainActivity.kt
└── MaisDigitalApp.kt


## Como funciona o tutorial guiado

A peça central do projeto. Funciona assim:

1. Cada elemento clicável da simulação recebe um `Modifier.alvoTutorial(id = "conversa_maria")`. Esse modifier registra a posição do elemento e captura cliques.
2. Cada `Aula` é uma sequência de `Passo`s. Cada passo aponta para o `elementoAlvoId` que deve ser tocado.
3. O `TutorialEngine` (Kotlin puro) recebe os cliques e decide:
   - Clique no alvo correto → avança para o próximo passo
   - Clique errado → emite mensagem amigável + tremor de tela, sem avançar
4. O `SpotlightOverlay` lê a posição do alvo atual e desenha um "buraco" iluminado no overlay escuro, destacando visualmente onde tocar.

**Vantagem:** adicionar novas aulas no futuro = adicionar uma entrada no catálogo. Adicionar um app inteiro novo (Gmail, etc.) = criar uma pasta `feature/simuladores/gmail/` ao lado.

## Acessibilidade

Pensada para idosos desde o início:

- **Botões grandes:** mínimo 56dp, principais 64dp (Material recomenda 48dp)
- **Tipografia generosa:** padrão 20sp em vez dos 16sp do Material
- **Tamanho de fonte ajustável** dentro do app (Pequeno / Médio / Grande)
- **Cores de alto contraste:** texto principal cinza-escuro sobre branco
- **Erros amigáveis:** laranja em vez de vermelho, ícone de "ideia" em vez de "X"
- **Linguagem simples:** instruções curtas, sem jargão técnico
- **Tela em portrait:** evita confusão com rotação
- **Sem ironia ou pressa:** usuário pode tentar quantas vezes quiser, sem timer

## Próximos passos

- Aulas com narração em voz alta (já tem campo `audioInstrucao` reservado no modelo)
- Módulo Gmail
- Módulo Google Maps


## Equipe



- Rodrigo S. Matos — [@RodrigoSMatos](https://github.com/RodrigoSMatos)
