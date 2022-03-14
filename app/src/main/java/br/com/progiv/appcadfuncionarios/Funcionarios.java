package br.com.progiv.appcadfuncionarios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.progiv.appcadfuncionarios.DAO.FuncionariosDAO;
import br.com.progiv.appcadfuncionarios.Model.FuncionariosModel;

public class Funcionarios extends AppCompatActivity {
    EditText txtNome;
    EditText txtFuncao;
    EditText txtSalario;
    EditText txtData;
    Button btnModificar;
    FuncionariosModel editarFuncionarios;
    FuncionariosModel funcionario;
    FuncionariosDAO funcionariosDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        funcionario = new FuncionariosModel();
        funcionariosDAO = new FuncionariosDAO(Funcionarios.this);

        Intent intent = getIntent();
        editarFuncionarios = (FuncionariosModel) intent.getSerializableExtra("selectFuncionario");

        txtNome = (EditText) findViewById(R.id.nomeFuncionario);
        txtFuncao = (EditText) findViewById(R.id.funcaoFuncionario);
        txtSalario = (EditText) findViewById(R.id.salarioFuncionario);
        txtData = (EditText) findViewById(R.id.dataFuncionario);
        btnModificar = (Button) findViewById(R.id.btnModificar);

        if(editarFuncionarios != null){
            btnModificar.setText("Modificar");
            txtNome.setText(editarFuncionarios.getNome());
            txtFuncao.setText(editarFuncionarios.getFuncao());
            txtSalario.setText(String.valueOf(editarFuncionarios.getSalario()));
            txtData.setText(editarFuncionarios.getData());
            funcionario.setId(editarFuncionarios.getId());
        }else{
            btnModificar.setText("Cadastrar");
        }

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionario.setNome(txtNome.getText().toString());
                funcionario.setFuncao(txtFuncao.getText().toString());
                funcionario.setSalario(Integer.parseInt(txtSalario.getText().toString()));
                funcionario.setData(txtData.getText().toString());
                if(btnModificar.getText().toString().toUpperCase().equals("CADASTRAR")){
                    funcionariosDAO.salvarFuncionario(funcionario);
                }else{
                    funcionariosDAO.alterarFuncionario(funcionario);
                }
                funcionariosDAO.close();
                finish();
            }
        });
    }
}
