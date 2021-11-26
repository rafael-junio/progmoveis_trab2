package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.VagaDAO;
import com.example.duoperfeito.model.Endereco;
import com.example.duoperfeito.model.Vaga;

public class EmpVagasCadastroActivity extends AppCompatActivity {

    private EditText nome, endereco, cep;
    private Button cadastrarVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_vagas_cadastro);

        nome = findViewById(R.id.edtTxtNome);
        endereco = findViewById(R.id.edtTxtEndereco);
        cep = findViewById(R.id.edtTxtCep);
        cadastrarVaga = findViewById(R.id.btnCadastrarVaga);

        startCadastrarButton();
    }

    private void startCadastrarButton() {
        cadastrarVaga.setOnClickListener(view -> {
            Vaga vagaEntity = new Vaga();
            Endereco enderecoEntity = new Endereco();

            vagaEntity.setNome(nome.getText().toString());
            vagaEntity.setCep(cep.getText().toString());

            enderecoEntity.setCep(cep.getText().toString());
            enderecoEntity.setLogradouro(endereco.getText().toString());

            vagaEntity.setEndereco(enderecoEntity);
            if (validaInput(vagaEntity)) {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final VagaDAO vagaDAO = database.getVagaDao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        vagaDAO.salvar(vagaEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Vaga cadastrado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
            else {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validaInput (Vaga vagasEntity) {
        return !vagasEntity.getNome().isEmpty() &&
                !vagasEntity.getCep().isEmpty();
    }
}