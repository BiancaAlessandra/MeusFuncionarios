package br.com.progiv.appcadfuncionarios.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import br.com.progiv.appcadfuncionarios.Model.FuncionariosModel;

public class FuncionariosDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "cadfuncionarios";
    private static final int VERSION = 1;

    public String msgErro = "";

    public FuncionariosDAO(Context context){
        super(context, DATABASE, null, VERSION);
        msgErro = "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE funcionarios(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nome TEXT NOT NULL, " +
                "funcao TEXT NOT NULL, " +
                "salario INTEGER NOT NULL DEFAULT 0, " +
                "data TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            String sql = "DROP TABLE IF EXISTS produtos";
            db.execSQL(sql);
        }
    }

    //Método de inserir funcionários:
    public boolean salvarFuncionario(FuncionariosModel funcionario){
        try {
            ContentValues values = new ContentValues();
            values.put("nome", funcionario.getNome());
            values.put("funcao", funcionario.getFuncao());
            values.put("salario", funcionario.getSalario());
            values.put("data", funcionario.getData());
            getWritableDatabase().insert("funcionarios", null, values);
            return true;
        }catch(Exception ex){
            msgErro = ex.getMessage();
            return false;
        }
    }

    //Método alterar funcionário
    public boolean alterarFuncionario(FuncionariosModel funcionario){
        try{
            ContentValues values = new ContentValues();
            values.put("nome", funcionario.getNome());
            values.put("funcao", funcionario.getFuncao());
            values.put("salario", funcionario.getSalario());
            values.put("data", funcionario.getData());

            String[] args = {funcionario.getId().toString()};
            getWritableDatabase().update("funcionarios", values, "id=?", args);
            return true;
        }catch(Exception ex){
            msgErro = ex.getMessage();
            return false;
        }
    }

    //Método deletar funcionário
    public boolean deletarFuncionario(FuncionariosModel funcionario){
        try{
            String[] args = {funcionario.getId().toString()};
            getWritableDatabase().delete("funcionarios",  "id=?", args);
            return true;
        }catch(Exception ex){
            msgErro = ex.getMessage();
            return false;
        }
    }

    //Método listar funcionários
    public ArrayList<FuncionariosModel> listaFuncionarios(){
        ArrayList<FuncionariosModel> lstFuncionarios = new ArrayList<>();
        String[] colums = {"id", "nome", "funcao", "salario", "data"};
        Cursor cursor = getWritableDatabase().query("funcionarios", colums, null,null,null,null,null);
        while(cursor.moveToNext()){
            FuncionariosModel funcionariosModel = new FuncionariosModel();
            funcionariosModel.setId(cursor.getLong(0));
            funcionariosModel.setNome(cursor.getString(1));
            funcionariosModel.setFuncao(cursor.getString(2));
            funcionariosModel.setSalario(cursor.getInt(3));
            funcionariosModel.setData(cursor.getString(4));
            lstFuncionarios.add(funcionariosModel);
        }
        return lstFuncionarios;
    }
}
