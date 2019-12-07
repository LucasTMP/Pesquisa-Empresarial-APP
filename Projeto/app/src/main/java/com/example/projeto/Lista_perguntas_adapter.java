package com.example.projeto;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Lista_perguntas_adapter extends ArrayAdapter<Lista_perguntas> {

    private Context context;
    private List<Lista_perguntas> lista_perguntas = null;

    SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MYPREF", 0);
    SharedPreferences.Editor editor = pref.edit();
    public int numero_de_questoes = Integer.parseInt(pref.getString("numero_de_questoes", "0"));

    public static int valor_questoes[];
    public static int id_questoes[];


    //  Metodo para definição da lista(objeto) e do layout(Context) que tem os itens para preencher a listview    ///
    public Lista_perguntas_adapter(Context context, List<Lista_perguntas> lista_perguntas) {
        super(context, 0, lista_perguntas);
        this.lista_perguntas = lista_perguntas;
        this.context = context;
        valor_questoes = new int[numero_de_questoes];
        id_questoes = new int[numero_de_questoes];

    }
    //                       FIM                                                ///


    //  Metodo para definição dos componentes que o adaptador vai preencher    ///
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Lista_perguntas perguntas = lista_perguntas.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item_lista_responde_questionario, null);
        }


        TextView descricao = (TextView) view.findViewById(R.id.titulo_questao);
        descricao.setText(String.valueOf(perguntas.getDescricao()));

        TextView sequencia = (TextView) view.findViewById(R.id.numero_questao);
        String texto = String.valueOf(perguntas.getSequencia());
        sequencia.setText("Pergunta " + texto);

//        TextView id = (TextView) view.findViewById(R.id.id_questao);
//        id.setText(String.valueOf(perguntas.getId()));

        eventos_barra(view, position);

        return view;
    }
    //                       FIM                                                ///


    //  Metodo para definição dos valores das respostas e dos ID's das perguntas    ///
    private void eventos_barra(View view, final int posicao) {

        Lista_perguntas perguntas = lista_perguntas.get(posicao);
        id_questoes[posicao] = Integer.parseInt(perguntas.getId());

        final SeekBar sk = (SeekBar) view.findViewById(R.id.resposta_questao);
        sk.getContext();
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                valor_questoes[posicao] = progress;

                View view = (View) sk.getParent();

                final TextView nota0 = (TextView) view.findViewById(R.id.n0);
                final TextView nota1 = (TextView) view.findViewById(R.id.n1);
                final TextView nota2 = (TextView) view.findViewById(R.id.n2);
                final TextView nota3 = (TextView) view.findViewById(R.id.n3);
                final TextView nota4 = (TextView) view.findViewById(R.id.n4);
                final TextView nota5 = (TextView) view.findViewById(R.id.n5);
                final TextView nota6 = (TextView) view.findViewById(R.id.n6);
                final TextView nota7 = (TextView) view.findViewById(R.id.n7);
                final TextView nota8 = (TextView) view.findViewById(R.id.n8);
                final TextView nota9 = (TextView) view.findViewById(R.id.n9);
                final TextView nota10 = (TextView) view.findViewById(R.id.n10);

                nota0.setTextColor((Color.parseColor("#808080")));
                nota1.setTextColor((Color.parseColor("#808080")));
                nota2.setTextColor((Color.parseColor("#808080")));
                nota3.setTextColor((Color.parseColor("#808080")));
                nota4.setTextColor((Color.parseColor("#808080")));
                nota5.setTextColor((Color.parseColor("#808080")));
                nota6.setTextColor((Color.parseColor("#808080")));
                nota7.setTextColor((Color.parseColor("#808080")));
                nota8.setTextColor((Color.parseColor("#808080")));
                nota9.setTextColor((Color.parseColor("#808080")));
                nota10.setTextColor((Color.parseColor("#808080")));


                switch (progress) {

                    default:
                        nota0.setTextColor((Color.parseColor("#e01b35")));
                        nota1.setTextColor((Color.parseColor("#e01b35")));
                        nota2.setTextColor((Color.parseColor("#e01b35")));
                        nota3.setTextColor((Color.parseColor("#e01b35")));
                        nota4.setTextColor((Color.parseColor("#e01b35")));
                        nota5.setTextColor((Color.parseColor("#e01b35")));
                        nota6.setTextColor((Color.parseColor("#e01b35")));
                        nota7.setTextColor((Color.parseColor("#e01b35")));
                        nota8.setTextColor((Color.parseColor("#e01b35")));
                        nota9.setTextColor((Color.parseColor("#e01b35")));
                        nota10.setTextColor((Color.parseColor("#e01b35")));

                    case 0:
                        nota0.setTextColor((Color.parseColor("#e01b35")));
                        break;

                    case 1:
                        nota1.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 2:
                        nota2.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 3:
                        nota3.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 4:
                        nota4.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 5:
                        nota5.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 6:
                        nota6.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 7:
                        nota7.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 8:
                        nota8.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 9:
                        nota9.setTextColor(Color.parseColor("#e01b35"));
                        break;

                    case 10:
                        nota10.setTextColor(Color.parseColor("#e01b35"));
                        break;

                }


//                Toast.makeText(context.getApplicationContext(), Arrays.toString(valor_questoes), Toast.LENGTH_SHORT).show();


            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    //                       FIM                                                ///
}
