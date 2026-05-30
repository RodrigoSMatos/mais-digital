package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de galeria de mídia do contato ("Todas as mídias").
 *
 * Usada na aula "Mídia, links e documentos". Tem 3 abas no topo —
 * Mídia, Docs e Links — e o conteúdo abaixo muda conforme a aba ativa.
 *
 * A aba ativa é controlada pelo simulador (parâmetro [abaAtiva]), que reage
 * aos passos do tutorial. Conteúdo é todo fictício e decorativo.
 *
 * Alvos:
 *  - "galeria_voltar"     → seta de voltar
 *  - "galeria_aba_midia"  → aba Mídia
 *  - "galeria_aba_docs"   → aba Docs
 *  - "galeria_aba_links"  → aba Links
 */
private val VerdeWhatsApp = Color(0xFF075E54)
private val CinzaIcone = Color(0xFF667781)

@Composable
fun TelaGaleriaMidia(
    abaAtiva: String = "midia",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Topo: seta voltar + título
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(56.dp)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .alvoTutorial("galeria_voltar")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
            Text(
                text = "Todas as mídias",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // 3 abas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Aba(
                texto = "Mídia",
                ativa = abaAtiva == "midia",
                modifier = Modifier
                    .weight(1f)
                    .alvoTutorial("galeria_aba_midia")
            )
            Aba(
                texto = "Docs",
                ativa = abaAtiva == "docs",
                modifier = Modifier
                    .weight(1f)
                    .alvoTutorial("galeria_aba_docs")
            )
            Aba(
                texto = "Links",
                ativa = abaAtiva == "links",
                modifier = Modifier
                    .weight(1f)
                    .alvoTutorial("galeria_aba_links")
            )
        }

        // Conteúdo da aba ativa
        when (abaAtiva) {
            "docs"  -> ConteudoDocs()
            "links" -> ConteudoLinks()
            else    -> ConteudoMidia()
        }
    }
}

@Composable
private fun Aba(
    texto: String,
    ativa: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = if (ativa) VerdeWhatsApp else CinzaIcone,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensoes.espacoPequeno)
        )
        // Sublinhado da aba ativa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(if (ativa) VerdeWhatsApp else Color.Transparent)
        )
    }
}

/**
 * Conteúdo da aba Mídia: grade de fotos/vídeos fictícios (quadradinhos).
 */
@Composable
private fun ConteudoMidia() {
    val cores = listOf(
        Color(0xFF90CAF9), Color(0xFFA5D6A7), Color(0xFFFFCC80),
        Color(0xFFCE93D8), Color(0xFF80CBC4), Color(0xFFEF9A9A),
        Color(0xFFB0BEC5), Color(0xFFFFF59D), Color(0xFF9FA8DA)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(Dimensoes.espacoPequeno)
    ) {
        // 3 linhas de 3 quadradinhos
        cores.chunked(3).forEachIndexed { linhaIndice, linha ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensoes.espacoPequeno),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensoes.espacoPequeno)
            ) {
                linha.forEachIndexed { colunaIndice, cor ->
                    val indiceGlobal = linhaIndice * 3 + colunaIndice
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(110.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(cor),
                        contentAlignment = Alignment.Center
                    ) {
                        // Alguns quadrados são "vídeos" (com ícone de play)
                        Icon(
                            imageVector = if (indiceGlobal % 4 == 0)
                                Icons.Filled.PlayCircle else Icons.Filled.Photo,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Conteúdo da aba Docs: lista de documentos fictícios.
 */
@Composable
private fun ConteudoDocs() {
    val docs = listOf(
        "Receita de bolo.pdf",
        "Comprovante.pdf",
        "Lista de compras.docx",
        "Fotos da viagem.zip"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        docs.forEach { nome ->
            ItemArquivo(icone = Icons.Filled.Description, titulo = nome, subtitulo = "Documento")
        }
    }
}

/**
 * Conteúdo da aba Links: lista de links fictícios.
 */
@Composable
private fun ConteudoLinks() {
    val links = listOf(
        "www.receitas.com.br",
        "www.noticias.com.br",
        "www.youtube.com"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        links.forEach { url ->
            ItemArquivo(icone = Icons.Filled.Link, titulo = url, subtitulo = "Link")
        }
    }
}

@Composable
private fun ItemArquivo(
    icone: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoMedio)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F2F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icone,
                contentDescription = null,
                tint = CinzaIcone,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}