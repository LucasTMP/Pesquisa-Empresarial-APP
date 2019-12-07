package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Lista_questionario_adapter extends ArrayAdapter<Lista_questionario> {

    private Context context;
    private List<Lista_questionario> lista_questoes = null;


    //  Metodo para definição da lista(objeto) e do layout(Context) que tem os itens para preencher a listview    ///
    public Lista_questionario_adapter(Context context, List<Lista_questionario> lista_questoes) {
        super(context, 0, lista_questoes);
        this.lista_questoes = lista_questoes;
        this.context = context;
    }
    //                                                     FIM                                                 ///


    //  Metodo para definição dos componentes que o adaptador vai preencher    ///
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Lista_questionario questoes = lista_questoes.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item_lista_questionarios, null);
        }

        TextView titulo = (TextView) view.findViewById(R.id.questionario_titulo);
        titulo.setText(String.valueOf(questoes.getTitulo()));

        TextView quantidade = (TextView) view.findViewById(R.id.numero_questoes);
        quantidade.setText(String.valueOf(questoes.getQuantidade()));

        TextView status = (TextView) view.findViewById(R.id.status_questionario);
        status.setText(String.valueOf(questoes.getStatus()));

        TextView id_quest = (TextView) view.findViewById((R.id.id_questionario));
        id_quest.setText(String.valueOf(questoes.getId()));

        Button entrar = (Button) view.findViewById(R.id.btn_entrar);


        return view;
    }
    //                       FIM                                                ///

}
