package br.carlos.nupeds.hellonfc;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NFCRecommenderActivity extends AppCompatActivity {

    private TextView mTextView;

    private SearchView search;
    private List<Produto> lista;
    private ListaProdutoAdapter adapter;
    private ListView cmpLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_produto);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cmpLista = (ListView) findViewById(R.id.listViewProduto);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fechar_pesquisar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        search = (SearchView) MenuItemCompat.getActionView(searchItem);
        search.setQueryHint("Pesquisar");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.action_search:
                botaoBuscar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        this.carregaListaProdutos();
        super.onResume();
    }

    private void carregaListaProdutos(){
        lista = new ArrayList<>();
        lista.add(new Produto(1,"Produto A"));
        lista.add(new Produto(1,"Produto B"));
        lista.add(new Produto(1,"Produto C"));
        lista.add(new Produto(1,"Produto D"));
        lista.add(new Produto(1,"Produto E"));
        adapter = new ListaProdutoAdapter(this,lista);
        cmpLista.setAdapter(adapter);
    }

    private void botaoBuscar(){
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(adapter!=null) {
                    adapter.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
}

