package com.example.appblocodenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    //instanciando o fileName para poder seguir o caminho de gravação dos arquivos
    private String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //para evitar que o keyboard leve os botões para cima
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //0º: estipular onde o texto escrito vai ser guardado...
        //...para isso temos que obter o caminho de gravação dos arquivos...
        //...o caminho: ApplicattionContext>FilesDir>Path>toString
        this.fileName = getApplicationContext().getFilesDir().getPath().toString() + "/";

        //1º: chamar os elementos da tela em forma de objetos
        final EditText contentBox = findViewById(R.id.contextBox);
        final EditText fileNameToOpen = findViewById(R.id.txtNomeArquivo);
        final Button btSalvar = findViewById(R.id.btSalvar);
        final Button btLimpar = findViewById(R.id.btLimpar);
        final Button btRecuperar = findViewById(R.id.btRecuperar);

        //2º: estipular os listeners de tocar nos botões para fazer eles funcionarem quando forem "clicked"

        //listener do botão Salvar:
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para salvar, você coloca o que foi escrito no "content"
                    //se não tiver nada,
                if(fileNameToOpen.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Nome de arquivo vazio", Toast.LENGTH_SHORT).show();
                }else{
                    //se tiver algo,
                    String completeFN = fileName + fileNameToOpen.getText().toString();
                    String content = contentBox.getText().toString();
                    //você chama o método para gravar, pega o que está dentro do content, e coloca dentro do fileName completo
                    MainActivity.this.gravaDadosArquivo(completeFN, content);
                }
            }
        });

        //listener do botão Limpar:
        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*método do que acontece quando o botão é clicado -> transforma o que
                 *estiver dentro do conentBox em nada: "" */
                contentBox.setText("");
            }
        });

        //listener do botão Recuperar:
        btRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se não tiver nada,
                if(fileNameToOpen.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Nome de arquivo vazio", Toast.LENGTH_SHORT).show();
                }else{
                    //se tiver algo,
                    String completeFN = fileName + fileNameToOpen.getText().toString();
                    String content = recuperarDadosArquivo(completeFN);
                    contentBox.setText(content);
                }
            }
        });

    }

    //3º: criar métodos para implementar nos listeners

    //método para gravar
    public void gravaDadosArquivo(String fileName, String data) {
        //gravar os dados pode gerar erros quando você quiser recuperar eles
        //solução: usar as exceptions try/catch
        try {
            OutputStreamWriter bufferSaida = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            bufferSaida.write(data);
            bufferSaida.close();
            Toast.makeText(this,"Salvo!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Falha da abertura do arquivo", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Falha na codifiação", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Falha na escrita", Toast.LENGTH_SHORT).show();

        }

    }

    //método para recuperar o arquivo
    public String recuperarDadosArquivo(String fileName) {
        //para recuperar o arquivo, temos que ler o arquivo
        //para ler o arquivo tipo texto, podemos usar várias técnicas
        //podemos chamar certas classes: BufferedReader
        //podem ter erros gerados
        //solução: usar as exceptions try/catch
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            //temos que ter algo que acumule o que for sendo coletado linha a linha
            StringBuilder sb = new StringBuilder();
            String linha = bufferedReader.readLine();
            //não sabemos quantas linhas são... vamos usar uma estrutura de repetição: while
            while (linha != null) {
                //acumular o que for pego do stringBuffer
                sb.append(linha);
                //temos que tratar a linha para uma linha ficar em baixo da outra
                sb.append("\n");
                //temos que saber se existe uma nova linha
                linha = bufferedReader.readLine();
            }
            Toast.makeText(this,"Recuperado!", Toast.LENGTH_SHORT).show();
            return sb.toString();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Falha da abertura do arquivo", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Falha na codifiação", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Falha na escrita", Toast.LENGTH_SHORT).show();

        }

            return "";

    }


}
