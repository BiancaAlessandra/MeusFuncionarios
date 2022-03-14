package br.com.progiv.appcadfuncionarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.progiv.appcadfuncionarios.DAO.FuncionariosDAO;
import br.com.progiv.appcadfuncionarios.Model.FuncionariosModel;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnCadastrar;
    FuncionariosDAO funcionariosDAO;
    ArrayList<FuncionariosModel> listaFuncionarios;
    ArrayAdapter adapter;
    FuncionariosModel funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listaFuncionarios);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Funcionarios.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                funcionario = (FuncionariosModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, Funcionarios.class);
               intent.putExtra("selectFuncionario", funcionario);
               startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                funcionario = (FuncionariosModel) parent.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar funcionário");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                funcionariosDAO = new FuncionariosDAO(MainActivity.this);
                funcionariosDAO.deletarFuncionario(funcionario);
                funcionariosDAO.close();
                carregarListaFuncionarios();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaFuncionarios();
    }

    public void carregarListaFuncionarios(){
        funcionariosDAO = new FuncionariosDAO(MainActivity.this);
        listaFuncionarios = funcionariosDAO.listaFuncionarios();
        funcionariosDAO.close();
        if(listaFuncionarios != null){
            adapter = new ArrayAdapter<FuncionariosModel>(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    listaFuncionarios);
            listView.setAdapter(adapter);
        }
    }
}