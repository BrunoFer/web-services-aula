package tsi.aula03.webservices;

import static javax.swing.JOptionPane.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CriaJson {

	public void Gerando() throws JSONException, FileNotFoundException {

		JSONArray arrayTurma = new JSONArray(), arrayAluno;
		JSONObject aluno, turma, turmas;

		String nome, DesTurma, curso;
		int idAuto, periodo;
		float nota;
		boolean cadT;

		cadT= true;

		turmas = new JSONObject();
		while (cadT)
		{
			DesTurma = showInputDialog(null, "Descrição da Turma:", "Gerar JSON", QUESTION_MESSAGE);
			periodo = Integer.parseInt(showInputDialog(null, "Período:", "Gerar JSON", QUESTION_MESSAGE));

			turma = new JSONObject();

			try {
				turma.put("turma", DesTurma);
				turma.put("periodo", periodo);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			idAuto = 1;
			aluno = new JSONObject();
			arrayAluno = new JSONArray();
			while (true)
			{
				nome = showInputDialog(null, "Forneça o Nome do Aluno", "Gerar JSON", QUESTION_MESSAGE);
				try {
					nota = Float.parseFloat(showInputDialog(null, "Forneça a nota do\n"+nome, "Gerar JSON", QUESTION_MESSAGE));
				}catch (Exception ex)
				{
					break;
				}
				aluno = new JSONObject();
				aluno.put("nome", nome);
				aluno.put("nota", nota);
				aluno.put("id", idAuto);
				arrayAluno.put(aluno);
				idAuto++;
			}

			turma.put("alunos", arrayAluno);
			arrayTurma.put(turma);
			turmas = new JSONObject();
			turmas.put("turmas", arrayTurma);

			if (showConfirmDialog(null, "Deseja Cadastrar Turma!", "Gerar JSON", YES_NO_OPTION) == 1)
				cadT = false;
		}
		curso = showInputDialog(null, "Curso:", "Gerar JSON", QUESTION_MESSAGE);

		turmas.put("curso", curso);

		try {
			FileWriter arquivo = new FileWriter("/var/www/Turma.json");
			arquivo.write(turmas.toString());
			arquivo.flush();
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		CriaJson criaJson = new CriaJson();
		try {
			criaJson.Gerando();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
