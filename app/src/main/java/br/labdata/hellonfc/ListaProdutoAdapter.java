package br.labdata.hellonfc;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.carlos.nupeds.hellonfc.R;

/**
 * Created by Carlos Eduardo Vilarinho on 23/06/2015 13:35.
 */
public class ListaProdutoAdapter extends ArrayAdapter<Produto> implements Filterable {

    private List<Produto> listaAtiva;
    private List<Produto> listaOriginal;
    private Context context;

    public ListaProdutoAdapter(Context context, List<Produto> listaProdutos) {
        super(context,0,listaProdutos);
        this.listaAtiva = listaProdutos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.listaAtiva.size();
    }

    @Override
    public Produto getItem(int posicao) {
        return this.listaAtiva.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ListaProdutoViewHolder holder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.lista_produto_item, parent,false);
            holder = new ListaProdutoViewHolder(row);
            row.setTag(holder);
        }else{
            holder = (ListaProdutoViewHolder) row.getTag();
        }

        Produto produto = getItem(position);
        holder.objetoParaComponente(produto);
        eventoLinha(row, produto);

        return row;
    }

    private void eventoLinha(View row, final Produto p) {
        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Snackbar.make(v,"Produto Selecionado: "+p.getDescricao(),Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaAtiva = (List<Produto>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults resultadoFiltro = new FilterResults();
                List<Produto> filtroLista = new ArrayList<Produto>();
                if (listaOriginal == null) {
                    listaOriginal = new ArrayList <Produto> (listaAtiva);
                }
                if (constraint == null || constraint.length() == 0) {
                    resultadoFiltro.count = listaOriginal.size();
                    resultadoFiltro.values = listaOriginal;
                } else {
                    for (Produto pro : listaOriginal) {
                        String conv = removerAcentos(pro.getDescricao());
                        if (conv.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filtroLista.add(pro);
                        }
                    }
                    resultadoFiltro.count = filtroLista.size();
                    resultadoFiltro.values = filtroLista;
                }
                return resultadoFiltro;
            }
        };
    }

    private String removerAcentos(String str) {
            return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}