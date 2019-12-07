package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Responde_questoes extends AppCompatActivity {

    TextView titulo;
    ListView lista;
    EditText obs;


    Connection cn = Conecta_bd.getConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responde_questoes);
        obs = (EditText) findViewById(R.id.obs_questao);


        //       Define o texto do botão quando entrar na tela ///
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        String status_questionario = pref.getString("questionario_status", "a");
        Button enviar = (Button) findViewById(R.id.btn_salvar_respostas);
        if (status_questionario.equals("Liberar")) {
            enviar.setText("Liberar Questionário");
        } else {
            enviar.setText("Enviar Respostas");
        }
        //                                 FIM                   ///


        //       Define o titulo do questionario quando entrar na tela ///
        String titulo_questionario = pref.getString("questionario_titulo", "a");
        titulo = (TextView) findViewById(R.id.titulo_questionario);
        titulo.setText(titulo_questionario);
        //                                 FIM                   ///


        //       Define a lista, puxa os dados, define o adaptador e aplica o adaptador na lista///
        List<Lista_perguntas> lista_perguntas;
        lista_perguntas = consultarTodos();
        Lista_perguntas_adapter adaptador = new Lista_perguntas_adapter(this, lista_perguntas);
        lista = (ListView) findViewById(R.id.listadeperguntas);
        lista.setAdapter(adaptador);
        //                                 FIM                   ///


    }


    //metodo para puxar os dados e criar o objeto, para adicionar na lista     ///
    public List<Lista_perguntas> consultarTodos() {


        List<Lista_perguntas> lista = new LinkedList<Lista_perguntas>();

        // Cursor c = null ;
        ResultSet rs = null;

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        String id_questionario = pref.getString("questionario_id", "a");

        try {

            Statement stm = cn.createStatement();

            rs = stm.executeQuery("select * from questionario_itens where questionario = " + id_questionario + ";");

            while (rs.next()) {

                Lista_perguntas item = new Lista_perguntas();

                item.setDescricao(rs.getString("descricao"));

                item.setId(rs.getString("id"));

                item.setSequencia(rs.getString("sequencia"));

                lista.add(item);
            }

        } catch (Exception e) {
            Log.e("BANCO", "Listar=" + e.getMessage());
        }
        return lista;
    }
    //                             FIM                                        ///

    //metodo para salvar e validar as questoes     ///
    public void salva_respostas(View view) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        int usuario_id = pref.getInt("usuario_id", 0);
        int questionario_id = Integer.parseInt(pref.getString("questionario_id", "0"));
        Button enviar = (Button) findViewById(R.id.btn_salvar_respostas);


//        Toast.makeText(getApplicationContext(), Arrays.toString(Lista_perguntas_adapter.id_questoes), Toast.LENGTH_SHORT).show();
//       Toast.makeText(getApplicationContext(), Arrays.toString(Lista_perguntas_adapter.valor_questoes), Toast.LENGTH_SHORT).show();


        if (enviar.getText().equals("Enviar Respostas")) {

            int ok = 1;
            int i = 0;

            int tamanho = Lista_perguntas_adapter.id_questoes.length;

            int sigilo = verificar_sigilo(questionario_id);

            if (ok == 1) {
                try {
                    Statement stm2 = cn.createStatement();
                    ok = stm2.executeUpdate("INSERT INTO questionario_respondido(`questionario`, `usuario`, `dataresposta`, `obs`) VALUES (" + questionario_id + "," + usuario_id + ",NOW(), '" + obs.getText() + "');");
                    if (ok == 0) {
                        Toast.makeText(getApplicationContext(), "Houve um erro ao tentar registrar a resposta.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    ok = 0;
                    if (ok == 0) {
                        Toast.makeText(getApplicationContext(), "Houve um erro ao tentar registrar a resposta.", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("BANCO", "GRAVAR_QUESTIONARIO_RESPONDIDOS=" + e.getMessage());
                    return;
                }
            }

            if (tamanho == 0) {

            }
            {
                if (ok == 1) {
                    if (sigilo == 0) {
                        while (i < tamanho) {

//           Toast.makeText(getApplicationContext(), "Entrou", Toast.LENGTH_SHORT).show();

                            try {
                                Statement stm = cn.createStatement();
                                ok = stm.executeUpdate("INSERT INTO questionario_respostas(`questionario_item`, `usuario`, `valor`) VALUES (" + Lista_perguntas_adapter.id_questoes[i] + "," + usuario_id + "," + Lista_perguntas_adapter.valor_questoes[i] + ");");
                                stm.close();
                                if (ok == 0) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar salvar as repostas.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception e) {
                                ok = 0;
                                Log.e("BANCO", "GRAVAR_QUESTIONARIO_RESPOSTAS=" + e.getMessage());
                                if (ok == 0) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar salvar as repostas.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            i++;
                        }
                    } else {
                        while (i < tamanho) {

//           Toast.makeText(getApplicationContext(), "Entrou", Toast.LENGTH_SHORT).show();

                            try {
                                Statement stm = cn.createStatement();
                                ok = stm.executeUpdate("INSERT INTO questionario_respostas(`questionario_item`, `usuario`, `valor`) VALUES (" + Lista_perguntas_adapter.id_questoes[i] + ",0," + Lista_perguntas_adapter.valor_questoes[i] + ");");
                                stm.close();
                                if (ok == 0) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar salvar as repostas.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception e) {
                                ok = 0;
                                Log.e("BANCO", "GRAVAR_QUESTIONARIO_RESPOSTAS=" + e.getMessage());
                                if (ok == 0) {
                                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar salvar as repostas.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            i++;
                        }
                    }

                }

            }

            if (ok == 1) {
                Toast.makeText(getApplicationContext(), "Pronto, as respostas já foram salvas.", Toast.LENGTH_SHORT).show();
                Intent intencao = new Intent(this, Consulta_questoes.class);
                startActivity(intencao);
            } else {
                Toast.makeText(getApplicationContext(), "Houve um erro desconhecido.", Toast.LENGTH_SHORT).show();
                Intent intencao = new Intent(this, Consulta_questoes.class);
                startActivity(intencao);
            }

        } else {

            int ok = 0;

            try {
                Statement stm2 = cn.createStatement();
                ok = stm2.executeUpdate("UPDATE questionario_dados SET status = 1 WHERE id =" + questionario_id + ";");
                if (ok == 0) {
                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar liberar o questionario.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Pronto, o questionario já foi liberado.", Toast.LENGTH_SHORT).show();
                Intent intencao = new Intent(this, Consulta_questoes.class);
                startActivity(intencao);
            } catch (Exception e) {
                ok = 0;
                if (ok == 0) {
                    Toast.makeText(getApplicationContext(), "Houve um erro ao tentar liberar o questionario.", Toast.LENGTH_SHORT).show();
                }
                Log.e("BANCO", "GRAVAR_QUESTIONARIO_RESPONDIDOS=" + e.getMessage());
                return;
            }

        }
    }
//                             FIM                                        ///

    //        metodo para verificar o sigilo              ///
    public int verificar_sigilo(int questionario_id) {
        int sigilo = 0;

        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT sigilo FROM questionario_dados WHERE id =" + questionario_id);

            if (rs.next()) {
                sigilo = rs.getInt("sigilo");
            } else {
                Toast.makeText(getApplicationContext(), "Houve um erro ao localizar o questionario.", Toast.LENGTH_SHORT).show();
                Intent intencao = new Intent(this, Consulta_questoes.class);
                startActivity(intencao);
            }

        } catch (SQLException e) {
            Log.e("BANCO", "GRAVAR_VER_SIGILO=" + e.getMessage());
        }

        return sigilo;
    }
//                             FIM                                        ///


}