package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botao_entrar;
    TextView mensagem_inicial, usuario, senha;

    String email_login, usuario_login;
    int id_login, empresa_login, status_login, departamento_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao_entrar = (Button) findViewById(R.id.btn_acessar);
        botao_entrar.setOnClickListener(this);

        mensagem_inicial = (TextView) findViewById(R.id.mensagem);
        mensagem_inicial.setOnClickListener(this);

        usuario = (TextView) findViewById(R.id.txt_usuario);
        senha = (TextView) findViewById(R.id.txt_senha);
    }


    public MainActivity() {
    }


    //                       Metodo que verifica o login                                             ///
    public int login() {

        Connection cn = Conecta_bd.getConnection();

        int proxima_tela = 0;

        String dado_usuario = usuario.getText().toString();
        String dado_senha = senha.getText().toString();
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM usuario WHERE email = '" + dado_usuario + "' and senha= '" + dado_senha + "'");
            if (rs.next()) {
                proxima_tela = 1;
                id_login = rs.getInt("id");
                empresa_login = rs.getInt("Empresa");
                email_login = rs.getString("email");
                status_login = rs.getInt("status");
                if (status_login == 1) {
                    Toast.makeText(getApplicationContext(), "Usuário desativado.", Toast.LENGTH_SHORT).show();
                    proxima_tela = 0;
                }
                departamento_login = rs.getInt("departamento");
                usuario_login = rs.getString("nome");

            } else {
                Toast.makeText(getApplicationContext(), "E-mail e senha incorretos.", Toast.LENGTH_SHORT).show();
                proxima_tela = 0;
            }
            rs.close();
            stm.close();
            cn.close();
        } catch (Exception e) {
            Log.e("BANCO", "GRAVAR=" + e.getMessage());
            Toast.makeText(getApplicationContext(), "Erro no login.", Toast.LENGTH_SHORT).show();
            proxima_tela = 0;
        }
        return proxima_tela;
    }
    //                                              FIM                                             ///


    //                       Metodo que valida os campos do login                                     ///
    public int realizalogin() {

        String dado_usuario = usuario.getText().toString();
        String dado_senha = senha.getText().toString();

        int validacao = 1;


        if (dado_usuario.equals("") && dado_senha.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
            validacao = 0;
        } else if (dado_usuario.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o campo: E-mail.", Toast.LENGTH_SHORT).show();
            validacao = 0;
        } else if (dado_senha.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o campo: Senha.", Toast.LENGTH_SHORT).show();
            validacao = 0;
        }

        return validacao;
    }
//                                       FIM                                                       ///


    //    Metodo que encaminha para as açoes dos botoes e efetiva o login     ///
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_acessar) {
            int validacao;
            validacao = realizalogin();
            if (validacao == 1) {
                int proxima_tela;
                proxima_tela = login();
                if (proxima_tela == 1) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MYPREF", 0);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("usuario_nome", usuario_login);
                    editor.putInt("usuario_empresa", empresa_login);
                    editor.putInt("usuario_id", id_login);
                    editor.putInt("usuario_departamento", departamento_login);
                    editor.putInt("usuario_status", status_login);
                    editor.putString("usuario_email", email_login);
                    editor.commit();
                    usuario.setText("");
                    senha.setText("");
                    Intent intencao = new Intent(this, Consulta_questoes.class);
                    startActivity(intencao);
                }
            }
        }

        if (v.getId() == R.id.mensagem) {
            Toast.makeText(getApplicationContext(), "Função em desenvolvimento.", Toast.LENGTH_SHORT).show();
        }

    }

    //                                  FIM                                    ///

    //                             Bloqueia o botão de voltar                      ///
    @Override
    public void onBackPressed() {
    }
//
}
