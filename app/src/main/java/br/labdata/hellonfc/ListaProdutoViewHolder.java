package br.carlos.nupeds.hellonfc;

import android.view.View;
import android.widget.TextView;

/**
 * Created by CARLOS.VILARINHO on 08/02/2016 15:32.
 */
public class ListaProdutoViewHolder {

    private TextView descricao;

    public ListaProdutoViewHolder(View convertView) {
        this.descricao = (TextView) convertView.findViewById(R.id.id_produto_nome);
    }

    public void objetoParaComponente(Produto p){
        this.descricao.setText(p.getDescricao());
    }

}
