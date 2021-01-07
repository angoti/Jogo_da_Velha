package com.angoti.jogodavelha

import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val random = Random(1)
    lateinit var forcaImagem: ImageView
    val listaDePalavras = listOf<String>("Foto", "Garfo", "Geleia", "Girafa", "Janela", "Limonada", "Mãe", "Meia", "Noite", "Óculos", "ônibus", "Ovo",
            "Pai", "Pão", "Parque", "Passarinho", "Peixe", "Pijama", "Rato", "Umbigo")
    var palavraSorteada: String = ""
    lateinit var palavraSorteadaMostrada: TextView
    lateinit var btJogar: Button
    lateinit var etLetra: EditText
    lateinit var tvLetrasErradas: TextView
    var erros = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        palavraSorteadaMostrada = findViewById<TextView>(R.id.tv_palavra_sorteada)
        btJogar = findViewById(R.id.bt_jogar)
        etLetra = findViewById(R.id.et_letra)
        tvLetrasErradas = findViewById(R.id.tv_letras_erradas)
        animacao(R.drawable.lista_animacao_apresentacao)
    }

    private fun animacao(animacao: Int) {
        forcaImagem = findViewById<ImageView>(R.id.iv_forca)
        forcaImagem.setImageResource(animacao)
        val forcaAnimacao = forcaImagem.drawable
        if (forcaAnimacao is Animatable) forcaAnimacao.start()
    }

    fun jogar(view: View) {
        tvLetrasErradas.text = ""
        erros = 0
        // a aplicação sorteia uma palavra a partir de uma lista com 20 palavras
        palavraSorteada = "abcabaeae"//listaDePalavras[random.nextInt(20)]
        // interrompe a animação e mostra a forca sem o boneco
        val forcaAnimacao = forcaImagem.drawable
        if (forcaAnimacao is Animatable) forcaAnimacao.stop()
        forcaImagem.setImageResource(R.drawable.forca7)
        // forneça a palavra sorteada na forma de uma sequencia de __
        var palavraOculta = StringBuffer()
        for (i in 1..palavraSorteada.length) palavraOculta.append("_")
        palavraSorteadaMostrada.text = palavraOculta
        // mostrar o botão para jogar e demais campos invisíveis
        btJogar.visibility = View.VISIBLE
        etLetra.visibility = View.VISIBLE
        etLetra.requestFocus()
        palavraSorteadaMostrada.visibility = View.VISIBLE
        tvLetrasErradas.visibility = View.VISIBLE
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etLetra, InputMethodManager.SHOW_IMPLICIT)
    }

    fun chutar(view: View) {

        if (etLetra.text.toString().length != 0) {
            val letra = etLetra.text.toString()[0]

//************** aqui começa a prova *****************************//
            // Esta é a parte do código a ser implementada, a prova é basicamente montar este trecho da lógica do app e o layout da tela.
            // 1- obter as posições da palavra sorteada que contêm a letra "chutada" pelo jogador (tem uma função utilitária no fim deste códgio).
            // 2- se tiver uma ou mais posições, deve-se adicionar a letra nas posições corretas na palavra sorteada
            // (id do componente da tela é tv_palavra_sorteada).
            // 3- testar se o jogador ganhou  (usar um return no fim da lógica se o jogador acerta uma letra)
//************** aqui termina a prova *****************************//

            //jogador errou a letra, troca a imagem do boneco
            erros++
            tvLetrasErradas.append(letra.toString())
            when (erros) {
                1 -> forcaImagem.setImageResource(R.drawable.forca6)
                2 -> forcaImagem.setImageResource(R.drawable.forca5)
                3 -> forcaImagem.setImageResource(R.drawable.forca4)
                4 -> forcaImagem.setImageResource(R.drawable.forca3)
                5 -> forcaImagem.setImageResource(R.drawable.forca2)
                6 -> {
                    forcaImagem.setImageResource(R.drawable.forca1)
                    fimDeJogo()
                }
            }
        }
    }


    private fun ganhou() {
        var palavraOculta = palavraSorteadaMostrada.text.toString()
        if (palavraOculta.indexOf(char = '_') == -1)
            fimDeJogo(true)
    }

    private fun fimDeJogo(ganhou: Boolean = false) {
        Toast.makeText(this, "Fim de jogo", Toast.LENGTH_SHORT).show();
        btJogar.visibility = View.INVISIBLE
        animacao(R.drawable.lista_animacao)
        etLetra.visibility = View.INVISIBLE
        if (!ganhou) palavraSorteadaMostrada.visibility = View.INVISIBLE
        tvLetrasErradas.visibility = View.INVISIBLE
    }

    // "aaa".indexesOf("aa") retorna [0, 1]
    public fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
        return this?.let {
            val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
            regex.findAll(this).map { it.range.start }.toList()
        } ?: emptyList()
    }
}

