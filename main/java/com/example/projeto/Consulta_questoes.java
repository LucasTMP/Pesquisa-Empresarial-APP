package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Consulta_questoes extends AppCompatActivity {

    ListView lista;

    Connection cn = Conecta_bd.getConnection();

    Dialog sigilopopup;
    ImageView imgnaotemmais;
    TextView txtnaotemmais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_questoes);

        List<Lista_questionario> lista_questoes;
        lista_questoes = consultarTodos();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        String usuario_email = pref.getString("usuario_email", "a");
        int usuario_id = pref.getInt("usuario_id", 0);
        int usuario_empresa = pref.getInt("usuario_empresa", 0);
        int usuario_status = pref.getInt("usuario_status", 0);
        int usuario_departamento = pref.getInt("usuario_departamento", 0);

        String usuario_nome = pref.getString("usuario_nome", "a");
        Lista_questionario_adapter adaptador = new Lista_questionario_adapter(this, lista_questoes);


        lista = (ListView) findViewById(R.id.lista_de_questoes);
        lista.setAdapter(adaptador);


        TextView boasvindas = (TextView) findViewById(R.id.boasvindas);
        String nome = usuario_nome.split(" ")[0];
        boasvindas.setText("Olá " + nome);
    }


    //     Verifica o botão apertado na tela, e pega os valores de paramentros para armazenar       ///
    public void entarquestionario(View view) {

        ConstraintLayout parentRow = (ConstraintLayout) view.getParent();
        TextView id = (TextView) parentRow.findViewById(R.id.id_questionario);
        TextView titulo = (TextView) parentRow.findViewById(R.id.questionario_titulo);
        TextView status = (TextView) parentRow.findViewById(R.id.status_questionario);
        TextView numero = (TextView) parentRow.findViewById(R.id.numero_questoes);

        String[] n_questoes = numero.getText().toString().split(" ");

//        Toast.makeText(getApplicationContext(),n_questoes[0], Toast.LENGTH_SHORT).show();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("numero_de_questoes", n_questoes[0]);
        editor.putString("questionario_id", id.getText().toString());
        editor.putString("questionario_titulo", titulo.getText().toString());
        editor.putString("questionario_status", status.getText().toString());
        editor.commit();

        //Toast.makeText(getApplicationContext(),pref.getString("questionario_id","a"), Toast.LENGTH_SHORT).show();

        Responde_questoes responde_questoes = new Responde_questoes();

        int sigilo = responde_questoes.verificar_sigilo(Integer.parseInt(id.getText().toString()));


        if (sigilo != 1) {
            Intent intencao = new Intent(this, Responde_questoes.class);
            startActivity(intencao);
        } else {
            sigilopopup = new Dialog(this);
            sigilopopup.setContentView(R.layout.popupsigilo);
            Button cancelar = (Button) sigilopopup.findViewById(R.id.cancelar);
            Button aceitar = (Button) sigilopopup.findViewById(R.id.aceitar);
            aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intencao = new Intent(getApplicationContext(), Responde_questoes.class);
                    startActivity(intencao);
                }
            });

            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sigilopopup.dismiss();
                }
            });
            sigilopopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sigilopopup.show();
        }


    }
//                             FIM                                        ///


    //     Realiza um select para encontrar os questionarios e definir os paramentros da tela           ///
    public List<Lista_questionario> consultarTodos() {

        List<Lista_questionario> lista = new LinkedList<Lista_questionario>();

        // Cursor c = null ;
        ResultSet rs = null;

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        String usuario_email = pref.getString("usuario_email", "a");
        int usuario_id = pref.getInt("usuario_id", 0);
        int usuario_empresa = pref.getInt("usuario_empresa", 0);
        int usuario_status = pref.getInt("usuario_status", 0);
        int usuario_departamento = pref.getInt("usuario_departamento", 0);

        int existe_questionario = 0;

        try {

            Statement stm = cn.createStatement();


            rs = stm.executeQuery("select usa.empresa as empresa,quest_dados.* from questionario_dados as quest_dados inner join usuario usa on quest_dados.usuario=usa.id where usa.empresa=" + usuario_empresa);

            while (rs.next()) {

                loop:
                {

                    Date data = new Date();

                    if (rs.getDate("datafinal").equals(rs.getDate("datainicio"))) {
                    } else if (rs.getDate("datafinal").compareTo(data) == -1) {
                        break loop;
                    }


                    if (rs.getInt("departamento") != 0) {
                        if (usuario_departamento != rs.getInt("departamento")) {
                            break loop;
                        }
                    }

                    int resposta = 0;

                    resposta = Integer.valueOf(ja_respondeu(rs.getInt("id")));

                    if (resposta == 1) {
                        break loop;
                    }

                    if (rs.getInt("status") == 1) {
                    } else if (usuario_id == rs.getInt("usuariosolicitante")) {

                    } else {
                        break loop;
                    }


                    Lista_questionario item = new Lista_questionario();

                    item.setTitulo(rs.getString("descricao"));

                    String status = "";
                    status = String.valueOf(rs.getInt("status"));
                    if (status.equals("1")) {
                        status = "Responder";
                    } else {
                        status = "Liberar";
                    }
                    item.setStatus(status);

                    int numero = 0;
                    int id_questionario = rs.getInt("id");

                    item.setId(String.valueOf(id_questionario));

                    String select_quantidade = "SELECT * FROM questionario_itens where questionario = " + id_questionario + ";";
                    numero = pegar_quantida_de_depergunta(select_quantidade);

                    item.setQuantidade(numero + " Questões");
                    lista.add(item);

                    existe_questionario = 1;

                }

            }

        } catch (Exception e) {
            Log.e("BANCO", "Listar=" + e.getMessage());
        }

        if (existe_questionario == 0) {
            Toast.makeText(getApplicationContext(), "Não possui questionários disponíveis.", Toast.LENGTH_SHORT).show();
            imgnaotemmais = (ImageView)findViewById(R.id.imgnaotemmais);
            imgnaotemmais.setVisibility(View.VISIBLE);
            txtnaotemmais = (TextView)findViewById(R.id.txtnaotemmais);
            txtnaotemmais.setVisibility(View.VISIBLE);
        }
        return lista;
    }
//                             FIM                                        ///


    //                       Realiza um select para pegar a quantidade de perguntas                ///
    public int pegar_quantida_de_depergunta(String parametro) throws SQLException {

        int numero;

        ResultSet rs2 = null;

        Statement stm2 = cn.createStatement();
        rs2 = stm2.executeQuery(parametro);
        rs2.last();
        numero = rs2.getRow();

        return numero;
    }
//                             FIM                                        ///


    //                       Verifica se já respondeu o questionario                  ///
    public int ja_respondeu(int id) throws SQLException {

        int resposta = 0;
        // Cursor c = null ;
        ResultSet rs4 = null;
        Statement stm4 = cn.createStatement();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = pref.edit();
        int usuario_id = Integer.valueOf(pref.getInt("usuario_id", 0));
        rs4 = stm4.executeQuery("SELECT * FROM questionario_respondido where questionario = " + id + " and usuario = " + usuario_id);
        if (rs4.next()) {
            resposta = 1;
        }

        return resposta;
    }
//                             FIM                                        ///


    //                             Bloqueia o botão de voltar                      ///
    @Override
    public void onBackPressed() {
        try {
            cn.close();
            Intent intencao = new Intent(this, MainActivity.class);
            startActivity(intencao);
        } catch (SQLException e) {
            Log.e("BANCO", "FECHAR_BANCO=" + e.getMessage());
        }
    }
//                             FIM                                        ///
}
