package tsi.webservices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManipulaJSON {

	public static void main(String[] args) throws JSONException {

		String str="";
		try {
			BufferedReader buffer = new BufferedReader(new FileReader("Turma.json"));
			while (buffer.ready()){
				str+=buffer.readLine();
			}
		} catch (IOException e){
			System.out.println("Erro ao manipular arquivo");
		}
		
		JSONObject turmas = new JSONObject(str);

		//Nome do curso
		System.out.println("\n\t\tNome do curso: "+turmas.getString("curso"));
		
		//obtém o objeto "devmovies"
		JSONArray arrayTurma = turmas.getJSONArray("turmas");

		for (int i=0; i< arrayTurma.length();i++){
			JSONObject t = arrayTurma.getJSONObject(i);
			System.out.println("\n");
			System.out.println("Turma: "+t.getString("turma"));
			System.out.println("Período: "+t.getString("periodo"));
			System.out.println("\n\tAlunos:\n");
			JSONArray arrayAlunos = t.getJSONArray("alunos");
			for (int k=0; k< arrayAlunos.length();k++){
				JSONObject a = arrayAlunos.getJSONObject(k);
				System.out.println("Nome do Aluno: "+a.getString("nome"));
				System.out.println("Nota do Aluno: "+a.getInt("nota"));
				System.out.println("Id do Aluno: "+a.getInt("id")+"\n");
			}
		}
		
		System.exit(0);
	}
}
